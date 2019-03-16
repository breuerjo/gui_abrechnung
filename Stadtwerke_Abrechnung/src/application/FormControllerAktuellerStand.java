package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class FormControllerAktuellerStand {
	
	@FXML
	private JFXDatePicker dp_aktuelles_datum;
	@FXML
	private JFXCheckBox cb_strom;
	@FXML
	private JFXCheckBox cb_erdgas;
	@FXML
	private JFXCheckBox cb_wasser;
	@FXML
	private JFXCheckBox cb_abwasser;
	
	@FXML
	private Label lb_anzahl_tage_aktuell;
	@FXML
	private Label lb_vergleichszeitraum_von;
	@FXML
	private Label lb_vergleichszeitraum_bis;
	
	
	@FXML
	private JFXTextField tf_zaehler_strom;
	@FXML
	private JFXTextField tf_zaehler_erdgas;
	@FXML
	private JFXTextField tf_zaehler_wasser;
	@FXML
	private JFXTextField tf_zaehler_abwasser;
	@FXML
	private Label lb_differenz_strom;
	@FXML
	private Label lb_differenz_erdgas;
	@FXML
	private Label lb_differenz_wasser;
	@FXML
	private Label lb_differenz_abwasser;
	@FXML
	private Label lb_vergleichswert_strom;
	@FXML
	private Label lb_vergleichswert_erdgas;
	@FXML
	private Label lb_vergleichswert_wasser;
	@FXML
	private Label lb_vergleichswert_abwasser;
	@FXML
	private Label lb_abweichung_strom;
	@FXML
	private Label lb_abweichung_erdgas;
	@FXML
	private Label lb_abweichung_wasser;
	@FXML
	private Label lb_abweichung_abwasser;
	
	@FXML
	private Label lb_abweichung_gesamt_prozent;
	@FXML
	private Label lb_aktuelle_abschagszahlungen;
	@FXML
	private Label lb_abweichung_gesamt_betrag;
	
	@FXML
	private Button bt_berechnen;
	
	public void initialize() {
		cb_strom.setSelected(true);
		cb_erdgas.setSelected(true);
		cb_wasser.setSelected(true);
		cb_abwasser.setSelected(true);
	}
	
	
	public void action_button_berechnen() {
		int zaehler_strom = 0;
		int zaehler_erdgas = 0;
		int zaehler_wasser = 0;
		int zaehler_abwasser= 0;
		
		
		//Eingabewerte speichern, falls leer => 0 bleibt drin stehen
		if(!tf_zaehler_strom.getText().equals("")) {
			zaehler_strom = Integer.parseInt(tf_zaehler_strom.getText());
		}
		if(!tf_zaehler_erdgas.getText().equals("")) {
			zaehler_erdgas = Integer.parseInt(tf_zaehler_erdgas.getText());
		}
		if(!tf_zaehler_wasser.getText().equals("")) {
			zaehler_wasser = Integer.parseInt(tf_zaehler_wasser.getText());
		}
		if(!tf_zaehler_abwasser.getText().equals("")) {
			zaehler_abwasser = Integer.parseInt(tf_zaehler_abwasser.getText());
		}

		
		int zaehler_alt_strom = 0;
		int zaehler_alt_erdgas = 0;
		int zaehler_alt_wasser = 0;
		int zaehler_alt_abwasser = 0;
		
		double aktueller_verbrauch_strom_pro_tag = 0;
		double aktueller_verbrauch_erdgas_pro_tag = 0;
		double aktueller_verbrauch_wasser_pro_tag = 0;
		double aktueller_verbrauch_abwasser_pro_tag = 0;
		
	
		
		//-----------------------------------------------aktuelle Verbrauchswerte-----------------------------------
		DB db = new DB();
		//den letzten Zeitraum zum Ermitteln des Verbrauchs seit dem letztem Zählerstandokumentation ermitteln zu können
		ResultSet rs_zeitraum = db.executeQueryWithResult("SELECT `id`, `zeitraum_von`, `zeitraum_bis` FROM `zeitraum` ORDER BY `zeitraum_bis` DESC LIMIT 1");
		
		
		int zeitraum_id = 0;
		long anz_tage_aktuell = 0;
		try {
			if (rs_zeitraum.next()) {
				
			
				zeitraum_id = rs_zeitraum.getInt("id");
				//java.sql.Date sql_date_zeitraum_von = rs_zeitraum.getDate("zeitraum_von");
				
				LocalDate zeitraum_bis = rs_zeitraum.getDate("zeitraum_bis").toLocalDate();
				
				anz_tage_aktuell = ChronoUnit.DAYS.between(zeitraum_bis, dp_aktuelles_datum.getValue()) + 1;	//da inkl. Bis Datum
				lb_anzahl_tage_aktuell.setText("Anzahl Tage: "+anz_tage_aktuell);
				//System.out.println(""+anz_tage);
				
				//Mittels der Zeitraum-ID die Rechnung --> Zählerstand-ID holen

				ResultSet rs_zaehlerstand_id = db.executeQueryWithResult("SELECT `zaehlerstand_id` FROM `rechnung` WHERE `zeitraum_id` = "+zeitraum_id+"");
				
				//Mit Hilfe der Rechnung und der Zählerstands ID die letzten Zählerstände holen => Berechnung bisheriger Verbrauch in der aktuellen Periode
				if(rs_zaehlerstand_id.next()) {
					ResultSet rs_zaehlerstand = db.executeQueryWithResult("SELECT * FROM `zaehlerstand` WHERE `id` = "+rs_zaehlerstand_id.getInt("zaehlerstand_id")+"");
					
					if(rs_zaehlerstand.next()) {
						
						ResultSet rs_letzte_einstellung = db.executeQueryWithResult("SELECT `erdgas_faktor_zustandszahl`, `erdgas_faktor_brennwert` FROM `einstellung` ORDER BY `id` DESC LIMIT 1");
						rs_letzte_einstellung.next();
						double faktor_umrechnung_erdgas_in_menge = rs_letzte_einstellung.getDouble("erdgas_faktor_zustandszahl") * rs_letzte_einstellung.getDouble("erdgas_faktor_brennwert");
						
						zaehler_alt_strom = rs_zaehlerstand.getInt("strom_neu");
						zaehler_alt_erdgas = rs_zaehlerstand.getInt("erdgas_neu");
						zaehler_alt_wasser = rs_zaehlerstand.getInt("wasser_neu");
						zaehler_alt_abwasser = rs_zaehlerstand.getInt("wasser_neu");	//ist der gleiche Wert in DB
						
						double aktueller_verbrauch_strom = (zaehler_strom - zaehler_alt_strom );	//aktueller Verbrauchswert = ( Aktueller Zählerstand - Endzählerstand der letzten Rechnung ) / Anzahl Tage
						double aktueller_verbrauch_erdgas = (zaehler_erdgas - zaehler_alt_erdgas) * faktor_umrechnung_erdgas_in_menge;	//Umrechnung in Menge
						double aktueller_verbrauch_wasser = (zaehler_wasser - zaehler_alt_wasser);
						double aktueller_verbrauch_abwasser = (zaehler_abwasser - zaehler_alt_abwasser);
						
						aktueller_verbrauch_strom_pro_tag = aktueller_verbrauch_strom / anz_tage_aktuell;
						aktueller_verbrauch_erdgas_pro_tag = aktueller_verbrauch_erdgas / anz_tage_aktuell;
						aktueller_verbrauch_wasser_pro_tag = aktueller_verbrauch_wasser / anz_tage_aktuell;
						aktueller_verbrauch_abwasser_pro_tag = aktueller_verbrauch_abwasser / anz_tage_aktuell;
						
						
						//Set Labels aktuelle Verbrauchswerte
						lb_differenz_strom.setText(""+BasicFunctions.roundDoubleNachkommastellen(aktueller_verbrauch_strom_pro_tag, 2)+" kWh");
						lb_differenz_erdgas.setText(""+BasicFunctions.roundDoubleNachkommastellen(aktueller_verbrauch_erdgas_pro_tag, 2)+" kWh");
						lb_differenz_wasser.setText(""+BasicFunctions.roundDoubleNachkommastellen(aktueller_verbrauch_wasser_pro_tag, 2)+" m³");
						lb_differenz_abwasser.setText(""+BasicFunctions.roundDoubleNachkommastellen(aktueller_verbrauch_abwasser_pro_tag, 2)+" m³");
					}
					
				}
				
			}	
		}catch(Exception e) {}
		
		//-----------------------------------------------Vergleichswerte-----------------------------------
		//Mit Hilfe des Zeitraumes 1 Jahr davor die Abweichung berechnen (mittels der Differenzen im Zählerstand, da laut der PDF die Schätzungen für neue Werte bereits Preisänderungen berücksichtigen)
		double vergleichswert_strom =0;
		double vergleichswert_erdgas =0;
		double vergleichswert_wasser =0;
		double vergleichswert_abwasser =0;
		
		double vergleichswert_strom_pro_tag = 0;
		double vergleichswert_erdgas_pro_tag = 0;
		double vergleichswert_wasser_pro_tag = 0;
		double vergleichswert_abwasser_pro_tag = 0;
		
		
		//Zeitraum vor 1 Jahr => Logik siehe SQL Statement
		LocalDate date_input = dp_aktuelles_datum.getValue();
		int month = Integer.parseInt(DateConversion.fillUpValue(date_input.getMonthValue())); //Eventuell 0 davor ergänzen
		int jahr = date_input.getYear();
		
		ResultSet rs_vergleichszeitraum = db.executeQueryWithResult("SELECT `id`, `differenz_tage`, `zeitraum_von`, `zeitraum_bis` FROM `zeitraum` \r\n" + 
				"WHERE `zeitraum_bis_jahr`< "+jahr+" \r\n" + 
				"AND `zeitraum_von_monat` < "+month+" \r\n" + 
				"AND `zeitraum_bis_monat` > "+month+"\r\n" + 
				" ORDER BY `zeitraum_bis` DESC\r\n" + 
				" LIMIT 1");
		int vergleichszeitraum_id = 0;
		int vergleichs_einstellung_id =0;
		try {
						
			if (rs_vergleichszeitraum.next()) {
				vergleichszeitraum_id = rs_vergleichszeitraum.getInt(1);	//Testfall = 34
				int differenz_tage_vergleichszeitraum = rs_vergleichszeitraum.getInt("differenz_tage");
				
				lb_vergleichszeitraum_von.setText("von: "+(DateConversion.dateFormating(rs_vergleichszeitraum.getString("zeitraum_von"))));
				lb_vergleichszeitraum_bis.setText("bis: "+(DateConversion.dateFormating(rs_vergleichszeitraum.getString("zeitraum_bis"))));
				
				//Mittels der Zeitraum-ID die Rechnung --> Zählerstand-ID holen
				ResultSet rs_vergleichs_zaehlerstand_id = db.executeQueryWithResult("SELECT `einstellung_id`, `zaehlerstand_id` FROM `rechnung` WHERE `zeitraum_id` = "+vergleichszeitraum_id+"");
				
				//Mit Hilfe der Rechnung und der Zählerstands ID die letzten Zählerstände holen => Berechnung bisheriger Verbrauch in der aktuellen Periode
				if(rs_vergleichs_zaehlerstand_id.next()) {
					vergleichs_einstellung_id = rs_vergleichs_zaehlerstand_id.getInt("einstellung_id");
					ResultSet rs_vergleichs_zaehlerstand = db.executeQueryWithResult("SELECT * FROM `zaehlerstand` WHERE `id` = "+rs_vergleichs_zaehlerstand_id.getInt("zaehlerstand_id")+"");
					
					if(rs_vergleichs_zaehlerstand.next()) {
						
						ResultSet rs_vergleichs_einstellungen = db.executeQueryWithResult("SELECT `erdgas_faktor_zustandszahl`, `erdgas_faktor_brennwert` FROM `einstellung` WHERE `id` = "+vergleichs_einstellung_id+"");
						rs_vergleichs_einstellungen.next();
						double faktor_umrechnung_erdgas_in_menge = rs_vergleichs_einstellungen.getDouble("erdgas_faktor_zustandszahl") * rs_vergleichs_einstellungen.getDouble("erdgas_faktor_brennwert");
						
						vergleichswert_strom = rs_vergleichs_zaehlerstand.getInt("differenz_strom") ;
						vergleichswert_erdgas = rs_vergleichs_zaehlerstand.getInt("differenz_erdgas") * faktor_umrechnung_erdgas_in_menge; //Umrechnung in Menge
						vergleichswert_wasser = rs_vergleichs_zaehlerstand.getInt("differenz_wasser");
						vergleichswert_abwasser = vergleichswert_wasser;
						
						vergleichswert_strom_pro_tag = vergleichswert_strom / differenz_tage_vergleichszeitraum; //Differenz(alt) / AnzTage  => Menge pro Tage
						vergleichswert_erdgas_pro_tag = vergleichswert_erdgas / differenz_tage_vergleichszeitraum;
						vergleichswert_wasser_pro_tag = vergleichswert_wasser / differenz_tage_vergleichszeitraum;
						vergleichswert_abwasser_pro_tag = vergleichswert_abwasser / differenz_tage_vergleichszeitraum;
						
						//Set Labels Vergleichswerte
						lb_vergleichswert_strom.setText(""+BasicFunctions.roundDoubleNachkommastellen(vergleichswert_strom_pro_tag, 2)+" kWh");
						lb_vergleichswert_erdgas.setText(""+BasicFunctions.roundDoubleNachkommastellen(vergleichswert_erdgas_pro_tag, 2)+" kWh");
						lb_vergleichswert_wasser.setText(""+BasicFunctions.roundDoubleNachkommastellen(vergleichswert_wasser_pro_tag, 2)+" m³");
						lb_vergleichswert_abwasser.setText(""+BasicFunctions.roundDoubleNachkommastellen(vergleichswert_abwasser_pro_tag, 2)+" m³");
					}
				}
			}
		} catch (Exception e) {}
		
		//---------------------------------------------Abweichung bestimmmen-----------------------
		double abweichung_strom = -1 *vergleichswert_strom_pro_tag + aktueller_verbrauch_strom_pro_tag;
		double abweichung_erdgas = -1 * vergleichswert_erdgas_pro_tag + aktueller_verbrauch_erdgas_pro_tag;
		double abweichung_wasser = -1 *vergleichswert_wasser_pro_tag + aktueller_verbrauch_wasser_pro_tag;
		double abweichung_abwasser = -1 * vergleichswert_abwasser_pro_tag + aktueller_verbrauch_abwasser_pro_tag;
		
		double abweichung_strom_prozent = abweichung_strom / vergleichswert_strom_pro_tag * 100;
		double abweichung_erdgas_prozent = abweichung_erdgas / vergleichswert_erdgas_pro_tag * 100;
		double abweichung_wasser_prozent = abweichung_wasser / vergleichswert_wasser_pro_tag * 100;
		double abweichung_abwasser_prozent = abweichung_abwasser / vergleichswert_abwasser_pro_tag * 100;
		
		//Set Labels Abweichung in % 
		lb_abweichung_strom.setText(""+ BasicFunctions.roundDoubleNachkommastellen(abweichung_strom_prozent,2));
		lb_abweichung_erdgas.setText(""+ BasicFunctions.roundDoubleNachkommastellen(abweichung_erdgas_prozent,2));
		lb_abweichung_wasser.setText(""+ BasicFunctions.roundDoubleNachkommastellen(abweichung_wasser_prozent,2));
		lb_abweichung_abwasser.setText(""+ BasicFunctions.roundDoubleNachkommastellen(abweichung_abwasser_prozent,2));
		
		//----------------------------------------------------------------Werte eingetragen? Haken gestezt?---------------------------
		if(zaehler_alt_strom == 0 || !cb_strom.isSelected()) {
			//Berechnete Werte 0 setzen und Labels ausblenden
			aktueller_verbrauch_strom_pro_tag = 0;
			abweichung_strom = 0;
			abweichung_strom_prozent = 0;
			lb_differenz_strom.setText("");
			lb_abweichung_strom.setText("");
			lb_vergleichswert_strom.setText("");
		}
		if(zaehler_alt_erdgas == 0 || !cb_erdgas.isSelected()) {
			//Berechnete Werte 0 setzen und Labels ausblenden
			aktueller_verbrauch_erdgas_pro_tag = 0;
			abweichung_erdgas = 0;
			abweichung_erdgas_prozent = 0;
			lb_differenz_erdgas.setText("");
			lb_abweichung_erdgas.setText("");
			lb_vergleichswert_erdgas.setText("");
		}
		if(zaehler_alt_wasser == 0 || !cb_wasser.isSelected()) {
			//Berechnete Werte 0 setzen und Labels ausblenden
			aktueller_verbrauch_wasser_pro_tag = 0;
			abweichung_wasser = 0;
			abweichung_wasser_prozent = 0;
			lb_differenz_wasser.setText("");
			lb_abweichung_wasser.setText("");
			lb_vergleichswert_wasser.setText("");
		}
		if(zaehler_alt_abwasser == 0 || !cb_abwasser.isSelected()) {
			//Berechnete Werte 0 setzen und Labels ausblenden
			aktueller_verbrauch_abwasser_pro_tag = 0;
			abweichung_abwasser = 0;
			abweichung_abwasser_prozent = 0;
			lb_differenz_abwasser.setText("");
			lb_abweichung_abwasser.setText("");
			lb_vergleichswert_abwasser.setText("");
		}
		
		abweichungGesamtBerechnen(db,vergleichszeitraum_id, abweichung_strom_prozent, abweichung_erdgas_prozent, abweichung_wasser_prozent, abweichung_abwasser_prozent);
		
	}
	
	public void abweichungGesamtBerechnen(DB db, int vergleichszeitraum_id, double abw_strom, double abw_erdgas, double abw_wasser, double abw_abwasser) {
		//Parameter abweichung sind in %!
		//An Hand der korrekten Berechnung der Gesamtabweichung in Prozent (Berücksichtigung der Anteile am Gesamtbetrag)
		//Mit Hilfe der zukünftigen Abschlagszahlungen den voraussichtlichen Nachzahlunsbetrag berechnen
		
		//Faktoren der Arten an Gesamtpreis berechnen
		ResultSet rs_vergleichszeitraum = db.executeQueryWithResult("SELECT `betrag_brutto_strom`,`betrag_brutto_erdgas`,`betrag_brutto_wasser`,`betrag_brutto_abwasser` FROM `rechnung` WHERE `zeitraum_id` = "+vergleichszeitraum_id+"");
		double faktor_strom =0;
		double faktor_erdgas=0;
		double faktor_wasser=0;
		double faktor_abwasser=0;
		try {
			if(rs_vergleichszeitraum.next()) {
				double gesambetrag = rs_vergleichszeitraum.getDouble("betrag_brutto_strom") + rs_vergleichszeitraum.getDouble("betrag_brutto_erdgas") + rs_vergleichszeitraum.getDouble("betrag_brutto_wasser")+ rs_vergleichszeitraum.getDouble("betrag_brutto_abwasser");
				faktor_strom = rs_vergleichszeitraum.getDouble("betrag_brutto_strom") / gesambetrag;
				faktor_erdgas = rs_vergleichszeitraum.getDouble("betrag_brutto_erdgas") / gesambetrag;
				faktor_wasser = rs_vergleichszeitraum.getDouble("betrag_brutto_wasser") / gesambetrag;
				faktor_abwasser = rs_vergleichszeitraum.getDouble("betrag_brutto_abwasser") / gesambetrag;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		double gesamtabweichung_prozent = faktor_strom * abw_strom + faktor_erdgas * abw_erdgas + faktor_wasser * abw_wasser + faktor_abwasser * abw_abwasser;
		
		//System.out.println("Gesamtabweichung: "+gesamtabweichung_prozent);		
		/* alte Berechnung anhand des Betrages des Vergleichzeitraumes
		 * double abweichung_strom = 0;
		double abweichung_erdgas = 0;
		double abweichung_wasser = 0;
		double abweichung_abwasser = 0;
		ResultSet rs_verleichzeitraum_rechnung = db.executeQueryWithResult("SELECT `betrag_brutto_strom`,`betrag_brutto_erdgas`,`betrag_brutto_wasser`,`betrag_brutto_abwasser` FROM `rechnung` WHERE `zeitraum_id` = "+vergleichszeitraum_id+"");
		try {
			if(rs_verleichzeitraum_rechnung.next()) {
				double betrag_strom = rs_verleichzeitraum_rechnung.getDouble("betrag_brutto_strom");
				double betrag_erdgas = rs_verleichzeitraum_rechnung.getDouble("betrag_brutto_erdgas");
				double betrag_wasser = rs_verleichzeitraum_rechnung.getDouble("betrag_brutto_wasser");
				double betrag_abwasser = rs_verleichzeitraum_rechnung.getDouble("betrag_brutto_abwasser");
				
				abweichung_strom = betrag_strom * abw_strom / 100; //da Abweichung in % => /100
				abweichung_erdgas = betrag_erdgas * abw_erdgas / 100;
				abweichung_wasser = betrag_wasser * abw_wasser / 100;
				abweichung_abwasser = betrag_abwasser * abw_abwasser / 100;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		double zukuenftige_abschlaege =0;
		ResultSet rs_letzte_rechnung = db.executeQueryWithResult("SELECT `betrag_zukuenftige_abschlaege` FROM `rechnung` ORDER BY `id` DESC LIMIT 1");
		try {
			if(rs_letzte_rechnung.next()) {
				zukuenftige_abschlaege = rs_letzte_rechnung.getDouble("betrag_zukuenftige_abschlaege");	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		double gesamtabweichung_betrag = zukuenftige_abschlaege * 12 * gesamtabweichung_prozent / 100; //Gesamtbetrag * Abweichung /100, da Abweichung in Prozent
		
		//Set Labels
		lb_abweichung_gesamt_prozent.setText(""+BasicFunctions.roundDoubleNachkommastellen(gesamtabweichung_prozent,2)+" %");
		lb_aktuelle_abschagszahlungen.setText(""+zukuenftige_abschlaege+"0 €");
		lb_abweichung_gesamt_betrag.setText(""+BasicFunctions.roundDoubleNachkommastellen(gesamtabweichung_betrag,2)+" €");
		
		if(gesamtabweichung_betrag >=0) {
			lb_abweichung_gesamt_betrag.setTextFill(Color.RED);
		}
		else {
			lb_abweichung_gesamt_betrag.setTextFill(Color.GREEN);
		}
		
	}
	
	public void action_menu_uebersicht() {
		Main.setStage("Uebersicht");
	}
	
	public void action_menu_neue_zahlung() {
		Main.setStage("Zahlung");
	}

	public void action_menu_auswertung() {
		Main.setStage("Auswertung");
	}

	public void action_menu_aufteilen() {
		Main.setStage("Aufteilen");
	}
	
	public void action_menu_aktueller_stand() {
		Main.setStage("AktuellerStand");
	}
	
	public void action_menu_settings() {
		Main.setStage("Einstellungen");
	}
}

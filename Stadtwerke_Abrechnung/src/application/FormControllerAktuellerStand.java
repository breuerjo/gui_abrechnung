package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FormControllerAktuellerStand {
	
	@FXML
	private JFXDatePicker dp_aktuelles_datum;
	
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
	private Button bt_berechnen;
	
	
	public void action_button_berechnen() {
		
		//Eingabewerte speichern
		int zaehler_strom = Integer.parseInt(tf_zaehler_strom.getText());
		int zaehler_erdgas = Integer.parseInt(tf_zaehler_erdgas.getText());
		int zaehler_wasser = Integer.parseInt(tf_zaehler_wasser.getText());
		int zaehler_abwasser = Integer.parseInt(tf_zaehler_abwasser.getText());
		
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
				//System.out.println(""+anz_tage);
				
				//Mittels der Zeitraum-ID die Rechnung --> Zählerstand-ID holen

				ResultSet rs_zaehlerstand_id = db.executeQueryWithResult("SELECT `zaehlerstand_id` FROM `rechnung` WHERE `zeitraum_id` = "+zeitraum_id+"");
				
				//Mit Hilfe der Rechnung und der Zählerstands ID die letzten Zählerstände holen => Berechnung bisheriger Verbrauch in der aktuellen Periode
				if(rs_zaehlerstand_id.next()) {
					ResultSet rs_zaehlerstand = db.executeQueryWithResult("SELECT * FROM `zaehlerstand` WHERE `id` = "+rs_zaehlerstand_id.getInt("zaehlerstand_id")+"");
					
					if(rs_zaehlerstand.next()) {
						zaehler_alt_strom = rs_zaehlerstand.getInt("strom_neu");
						zaehler_alt_erdgas = rs_zaehlerstand.getInt("erdgas_neu");
						zaehler_alt_wasser = rs_zaehlerstand.getInt("wasser_neu");
						zaehler_alt_abwasser = rs_zaehlerstand.getInt("wasser_neu");	//ist der gleiche Wert in DB
						
						double aktueller_verbrauch_strom = (zaehler_strom - zaehler_alt_strom );	//aktueller Verbrauchswert = ( Aktueller Zählerstand - Endzählerstand der letzten Rechnung ) / Anzahl Tage
						double aktueller_verbrauch_erdgas = (zaehler_erdgas - zaehler_alt_erdgas);
						double aktueller_verbrauch_wasser = (zaehler_wasser - zaehler_alt_wasser);
						double aktueller_verbrauch_abwasser = (zaehler_abwasser - zaehler_alt_abwasser);
						
						aktueller_verbrauch_strom_pro_tag = aktueller_verbrauch_strom / anz_tage_aktuell;
						aktueller_verbrauch_erdgas_pro_tag = aktueller_verbrauch_erdgas / anz_tage_aktuell;
						aktueller_verbrauch_wasser_pro_tag = aktueller_verbrauch_wasser / anz_tage_aktuell;
						aktueller_verbrauch_abwasser_pro_tag = aktueller_verbrauch_abwasser / anz_tage_aktuell;
						
						
						//Set Labels aktuelle Verbrauchswerte
						lb_differenz_strom.setText(""+BasicFunctions.roundDoubleNachkommastellen(aktueller_verbrauch_strom_pro_tag, 4));
						lb_differenz_erdgas.setText(""+BasicFunctions.roundDoubleNachkommastellen(aktueller_verbrauch_erdgas_pro_tag, 4));
						lb_differenz_wasser.setText(""+BasicFunctions.roundDoubleNachkommastellen(aktueller_verbrauch_wasser_pro_tag, 4));
						lb_differenz_abwasser.setText(""+BasicFunctions.roundDoubleNachkommastellen(aktueller_verbrauch_abwasser_pro_tag, 4));
					}
					
				}
				
			}	
		}catch(Exception e) {}
		
		//-----------------------------------------------Vergleichswerte-----------------------------------
		//Mit Hilfe des Zeitraumes 1 Jahr davor die Abweichung berechnen (mittels der Differenzen im Zählerstand, da laut der PDF die Schätzungen für neue Werte bereits Preisänderungen berücksichtigen)
		double vergleichswert_strom_pro_tag = 0;
		double vergleichswert_erdgas_pro_tag = 0;
		double vergleichswert_wasser_pro_tag = 0;
		double vergleichswert_abwasser_pro_tag = 0;
		
		//Zeitraum vor 1 Jahr => 
		
		ResultSet rs_vergleichszeitraum = db.executeQueryWithResult("SELECT MAX(`id`), `differenz_tage` FROM `zeitraum` WHERE `id`< "+zeitraum_id+" ORDER BY `zeitraum_bis` DESC");
		try {
			if (rs_vergleichszeitraum.next()) {
				int vergleichszeitraum_id = rs_vergleichszeitraum.getInt(1);	//Testfall = 34
				int differenz_tage_vergleichszeitraum = rs_vergleichszeitraum.getInt("differenz_tage");
				
				//Mittels der Zeitraum-ID die Rechnung --> Zählerstand-ID holen
				ResultSet rs_vergleichs_zaehlerstand_id = db.executeQueryWithResult("SELECT `zaehlerstand_id` FROM `rechnung` WHERE `zeitraum_id` = "+vergleichszeitraum_id+"");
				
				//Mit Hilfe der Rechnung und der Zählerstands ID die letzten Zählerstände holen => Berechnung bisheriger Verbrauch in der aktuellen Periode
				if(rs_vergleichs_zaehlerstand_id.next()) {
					ResultSet rs_vergleichs_zaehlerstand = db.executeQueryWithResult("SELECT * FROM `zaehlerstand` WHERE `id` = "+rs_vergleichs_zaehlerstand_id.getInt("zaehlerstand_id")+"");
					
					if(rs_vergleichs_zaehlerstand.next()) {
						double vergleichswert_strom = rs_vergleichs_zaehlerstand.getInt("differenz_strom") ;
						double vergleichswert_erdgas = rs_vergleichs_zaehlerstand.getInt("differenz_erdgas");
						double vergleichswert_wasser = rs_vergleichs_zaehlerstand.getInt("differenz_wasser");
						double vergleichswert_abwasser = vergleichswert_wasser;
						
						vergleichswert_strom_pro_tag = vergleichswert_strom / differenz_tage_vergleichszeitraum; //Differenz(alt) / AnzTage  => Menge pro Tage
						vergleichswert_erdgas_pro_tag = vergleichswert_erdgas / differenz_tage_vergleichszeitraum;
						vergleichswert_wasser_pro_tag = vergleichswert_wasser / differenz_tage_vergleichszeitraum;
						vergleichswert_abwasser_pro_tag = vergleichswert_abwasser / differenz_tage_vergleichszeitraum;
						
						//Set Labels Vergleichswerte
						lb_vergleichswert_strom.setText(""+BasicFunctions.roundDoubleNachkommastellen(vergleichswert_strom_pro_tag, 4));
						lb_vergleichswert_erdgas.setText(""+BasicFunctions.roundDoubleNachkommastellen(vergleichswert_erdgas_pro_tag, 4));
						lb_vergleichswert_wasser.setText(""+BasicFunctions.roundDoubleNachkommastellen(vergleichswert_wasser_pro_tag, 4));
						lb_vergleichswert_abwasser.setText(""+BasicFunctions.roundDoubleNachkommastellen(vergleichswert_abwasser_pro_tag, 4));
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

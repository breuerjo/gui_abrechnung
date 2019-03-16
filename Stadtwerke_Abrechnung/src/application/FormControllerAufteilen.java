package application;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.sun.javafx.scene.control.skin.ComboBoxPopupControl.FakeFocusTextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class FormControllerAufteilen {
	
	@FXML
	private Label lb_bezeichnung_wohnung_1;
	@FXML
	private Label lb_bezeichnung_wohnung_2;
	@FXML
	private Label lb_anzahl_tage;
	
	@FXML
	private Label lb_menge_strom_wohnung_1;
	@FXML
	private Label lb_kosten_strom_wohnung_1;
	@FXML
	private Label lb_menge_erdgas_wohnung_1;
	@FXML
	private Label lb_kosten_erdgas_wohnung_1;
	@FXML
	private Label lb_menge_wasser_wohnung_1;
	@FXML
	private Label lb_kosten_wasser_wohnung_1;
	@FXML
	private Label lb_menge_abwasser_wohnung_1;
	@FXML
	private Label lb_kosten_abwasser_wohnung_1;
	@FXML
	private Label lb_gesamte_kosten_wohnung_1;
	
	@FXML
	private Label lb_menge_strom_wohnung_2;
	@FXML
	private Label lb_kosten_strom_wohnung_2;
	@FXML
	private Label lb_menge_erdgas_wohnung_2;
	@FXML
	private Label lb_kosten_erdgas_wohnung_2;
	@FXML
	private Label lb_menge_wasser_wohnung_2;
	@FXML
	private Label lb_kosten_wasser_wohnung_2;
	@FXML
	private Label lb_menge_abwasser_wohnung_2;
	@FXML
	private Label lb_kosten_abwasser_wohnung_2;
	@FXML
	private Label lb_gesamte_kosten_wohnung_2;
	
	@FXML
	private Label lb_menge_strom_gesamt;
	@FXML
	private Label lb_kosten_strom_gesamt;
	@FXML
	private Label lb_menge_erdgas_gesamt;
	@FXML
	private Label lb_kosten_erdgas_gesamt;
	@FXML
	private Label lb_menge_wasser_gesamt;
	@FXML
	private Label lb_kosten_wasser_gesamt;
	@FXML
	private Label lb_menge_abwasser_gesamt;
	@FXML
	private Label lb_kosten_abwasser_gesamt;
	@FXML
	private Label lb_gesamte_kosten_gesamt;
	
	@FXML
	private JFXDatePicker dp_zeitraum_von;
	@FXML
	private JFXDatePicker dp_zeitraum_bis;
	@FXML
	private JFXButton bt_berechnen;
	
	public void initialize() {
		initLabelsWohnungsBezeichnungen();
	}
	
	public void initLabelsWohnungsBezeichnungen() {
		DB db = new DB();
		ResultSet rs_einstellung = db.executeQueryWithResult("SELECT `id`,`quadratmeter_wohnung_1`,`quadratmeter_wohnung_2` FROM `einstellung` ORDER BY `id` DESC LIMIT 1");
		try {
			if(rs_einstellung.next()) {
				lb_bezeichnung_wohnung_1.setText("Wohnung 1: ("+rs_einstellung.getInt("quadratmeter_wohnung_1")+" m²)");
				lb_bezeichnung_wohnung_2.setText("Wohnung 2: ("+rs_einstellung.getInt("quadratmeter_wohnung_2")+" m²)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void action_bt_berechnen() {
		
		DB db = new DB();
		ResultSet rs_zeitraum = db.executeQueryWithResult("SELECT `id`, `zeitraum_von`, `zeitraum_bis`, `differenz_tage` FROM `zeitraum` ORDER BY `zeitraum_von` ASC");
		
		LocalDate von_in = dp_zeitraum_von.getValue();
		LocalDate bis_in = dp_zeitraum_bis.getValue();
		long anz_tage_soll = ChronoUnit.DAYS.between(von_in, bis_in) + 1;
		//System.out.println("Soll: "+anz_tage_soll);
		lb_anzahl_tage.setText("Anzahl Tage: "+anz_tage_soll+" Tage");
		
		long anz_tage = 0;
		int zaehler =0;
		int speicher =0;
		boolean von_hat_begonnen = false;
		int anz_tage_zwischenspeicher;
		
		double betrag_brutto_strom =0;
		double betrag_brutto_erdgas =0;
		double betrag_brutto_wasser =0;
		double betrag_brutto_abwasser =0;
		
		double menge_strom =0;
		double menge_erdgas =0;
		double menge_wasser =0;
		double menge_abwasser =0;
		
		try {
			while(rs_zeitraum.next()) {
				zaehler++;
				anz_tage_zwischenspeicher = (int) anz_tage;	//Wert vor Schleifendurchlauf speichern
				
				LocalDate von_db = rs_zeitraum.getDate("zeitraum_von").toLocalDate();
				LocalDate bis_db = rs_zeitraum.getDate("zeitraum_bis").toLocalDate();
				//System.out.println("Vergleich_von: "+von_db);
				//System.out.println("Vergleich Bis : "+bis_db);
				
				if(von_in.isAfter(bis_db)) {	
					//hat nichts mit dem Vergleichszeitraum zu tun
					
				}
							
				
				else if (von_in.isAfter(von_db) && von_in.isBefore(bis_db) || von_in.isEqual(von_db) && von_in.isBefore(bis_db)) {
					//Anfang liegt im Vergleichszeitraum
					//System.out.println("Mindestens der Anfang liegt drin");
					
					if(bis_in.isBefore(bis_db)  || bis_in.isEqual(bis_db)) {
						//Liegt komplett in dem Vergleichszeitraum
						//System.out.println("Liegt komplett drin");
						//System.out.println("Komplett: "+anz_tage);
						anz_tage += ChronoUnit.DAYS.between(von_in, bis_in) + 1;
						//System.out.println("Komplett: "+anz_tage);
					}
					else if (!von_hat_begonnen) {
						//Fängt in diesem Zeitraum an, aber endet nicht innerhalb => nächster Zeitraum noch
						//System.out.println("Nur der Anfang liegt drin");
						//System.out.println("Anfang: "+anz_tage);
						anz_tage += ChronoUnit.DAYS.between(von_in, bis_db) + 1;
						//System.out.println("Anfang: "+anz_tage);
						von_hat_begonnen = true;
						speicher = zaehler;
					}
				}
				
				else if (von_db.isBefore(bis_in) && bis_in.isBefore(bis_db)) {
					//endet in diesem Zeitraum, aber insgesamt nur teilweise drin
					//System.out.println("Nur das Ende liegt drin");
					//System.out.println("Ende: "+anz_tage);
					anz_tage += ChronoUnit.DAYS.between(von_db, bis_in) + 1;
					//System.out.println("Ende:"+anz_tage);
					von_hat_begonnen = false; //Ende wurde gefunden
				}
				
				//Wenn er hier ankommt und der Startzeitpunkt schon gefunden wurde, ist liegt der aktuelle Zeitpunkt auch noch komplett drin
				else if(von_hat_begonnen && speicher != zaehler){
					//System.out.println("Zwischendrin: "+anz_tage);
					anz_tage += (ChronoUnit.DAYS.between(von_db, bis_db) + 1);	//kompletter aktueller Zeitraum dazu nehmen
					//System.out.println("Zwischendrin: "+anz_tage);
				}
				
				int anz_tage_zeitraum_ges = rs_zeitraum.getInt("differenz_tage");
				int anz_tage_durchlauf_neu = (int) (anz_tage - anz_tage_zwischenspeicher);	//Die anzahl an Tagen, die durch diesen Zeitraum dazugekommen ist	
				
				if (anz_tage_durchlauf_neu > 0) {	//Wenn mind. ein Teil in diesem Zeitraum liegt
					
					ResultSet rs_rechnung = db.executeQueryWithResult("SELECT `betrag_brutto_strom`, `betrag_brutto_erdgas`, `betrag_brutto_wasser`, `betrag_brutto_abwasser`, `menge_strom`, `menge_erdgas`, `menge_wasser`, `menge_abwasser` FROM `rechnung` WHERE `zeitraum_id` = "+rs_zeitraum.getInt("id")+"");
					if(rs_rechnung.next()) {
						
						betrag_brutto_strom += (rs_rechnung.getDouble("betrag_brutto_strom") / anz_tage_zeitraum_ges * anz_tage_durchlauf_neu);	//Gesamtbetrag / Gesamtanzahl Tage * Anz Tage die in dem Zeitraum liegen
						betrag_brutto_erdgas += (rs_rechnung.getDouble("betrag_brutto_erdgas") / anz_tage_zeitraum_ges * anz_tage_durchlauf_neu);
						betrag_brutto_wasser += (rs_rechnung.getDouble("betrag_brutto_wasser") / anz_tage_zeitraum_ges * anz_tage_durchlauf_neu);
						betrag_brutto_abwasser += (rs_rechnung.getDouble("betrag_brutto_abwasser") / anz_tage_zeitraum_ges * anz_tage_durchlauf_neu);
						
						menge_strom += (rs_rechnung.getDouble("menge_strom") / anz_tage_zeitraum_ges * anz_tage_durchlauf_neu);
						menge_erdgas += (rs_rechnung.getDouble("menge_erdgas") / anz_tage_zeitraum_ges * anz_tage_durchlauf_neu);
						menge_wasser += (rs_rechnung.getDouble("menge_wasser") / anz_tage_zeitraum_ges * anz_tage_durchlauf_neu);
						menge_abwasser += (rs_rechnung.getDouble("menge_abwasser") / anz_tage_zeitraum_ges * anz_tage_durchlauf_neu);
					}
				}
				
			}//while
			
						
			
			lb_menge_strom_gesamt.setText(""+BasicFunctions.roundDoubleNachkommastellen(menge_strom, 2)+" kWh");
			lb_kosten_strom_gesamt.setText(""+BasicFunctions.roundDoubleNachkommastellen(betrag_brutto_strom, 2)+" €");
			lb_menge_erdgas_gesamt.setText(""+BasicFunctions.roundDoubleNachkommastellen(menge_erdgas, 2)+" kWh");
			lb_kosten_erdgas_gesamt.setText(""+BasicFunctions.roundDoubleNachkommastellen(betrag_brutto_erdgas, 2)+" €");
			lb_menge_wasser_gesamt.setText(""+BasicFunctions.roundDoubleNachkommastellen(menge_wasser, 2)+" m³");
			lb_kosten_wasser_gesamt.setText(""+BasicFunctions.roundDoubleNachkommastellen(betrag_brutto_wasser, 2)+" €");
			lb_menge_abwasser_gesamt.setText(""+BasicFunctions.roundDoubleNachkommastellen(menge_abwasser, 2)+" m³");
			lb_kosten_abwasser_gesamt.setText(""+BasicFunctions.roundDoubleNachkommastellen(betrag_brutto_abwasser, 2)+" €");
			
			ResultSet rs_einstellung = db.executeQueryWithResult("SELECT `id`,`quadratmeter_wohnung_1`,`quadratmeter_wohnung_2` FROM `einstellung` ORDER BY `id` DESC LIMIT 1");
			double quadratmeter_wohnung_1 = 0;
			double quadratmeter_wohnung_2 = 0;
			double faktor_wohnung_1 = 0;
			double faktor_wohnung_2 = 0;
			
			if(rs_einstellung.next()) {
				quadratmeter_wohnung_1 = rs_einstellung.getInt("quadratmeter_wohnung_1");
				quadratmeter_wohnung_2 = rs_einstellung.getInt("quadratmeter_wohnung_2");
				double quadratmeter_gesamt = quadratmeter_wohnung_1 +quadratmeter_wohnung_2;
				
				faktor_wohnung_1 = quadratmeter_wohnung_1 / quadratmeter_gesamt;	//Quadratmeter Wohung / Gesamtquadratmeter
				faktor_wohnung_2 = quadratmeter_wohnung_2 / quadratmeter_gesamt;
			}
			//Set Labels Wohnung 1
			lb_menge_strom_wohnung_1.setText(""+BasicFunctions.roundDoubleNachkommastellen(menge_strom * faktor_wohnung_1, 2)+" kWh");
			lb_kosten_strom_wohnung_1.setText(""+BasicFunctions.roundDoubleNachkommastellen(betrag_brutto_strom * faktor_wohnung_1, 2)+" €");
			lb_menge_erdgas_wohnung_1.setText(""+BasicFunctions.roundDoubleNachkommastellen(menge_erdgas * faktor_wohnung_1, 2)+" kWh");
			lb_kosten_erdgas_wohnung_1.setText(""+BasicFunctions.roundDoubleNachkommastellen(betrag_brutto_erdgas * faktor_wohnung_1, 2)+" €");
			lb_menge_wasser_wohnung_1.setText(""+BasicFunctions.roundDoubleNachkommastellen(menge_wasser * faktor_wohnung_1, 2)+" m³");
			lb_kosten_wasser_wohnung_1.setText(""+BasicFunctions.roundDoubleNachkommastellen(betrag_brutto_wasser * faktor_wohnung_1, 2)+" €");
			lb_menge_abwasser_wohnung_1.setText(""+BasicFunctions.roundDoubleNachkommastellen(menge_abwasser * faktor_wohnung_1, 2)+" m³");
			lb_kosten_abwasser_wohnung_1.setText(""+BasicFunctions.roundDoubleNachkommastellen(betrag_brutto_abwasser * faktor_wohnung_1, 2)+" €");
			
			//Set Labels Wohnung 2
			lb_menge_strom_wohnung_2.setText(""+BasicFunctions.roundDoubleNachkommastellen(menge_strom * faktor_wohnung_2, 2)+" kWh");
			lb_kosten_strom_wohnung_2.setText(""+BasicFunctions.roundDoubleNachkommastellen(betrag_brutto_strom * faktor_wohnung_2, 2)+" €");
			lb_menge_erdgas_wohnung_2.setText(""+BasicFunctions.roundDoubleNachkommastellen(menge_erdgas * faktor_wohnung_2, 2)+" kWh");
			lb_kosten_erdgas_wohnung_2.setText(""+BasicFunctions.roundDoubleNachkommastellen(betrag_brutto_erdgas * faktor_wohnung_2, 2)+" €");
			lb_menge_wasser_wohnung_2.setText(""+BasicFunctions.roundDoubleNachkommastellen(menge_wasser * faktor_wohnung_2, 2)+" m³");
			lb_kosten_wasser_wohnung_2.setText(""+BasicFunctions.roundDoubleNachkommastellen(betrag_brutto_wasser * faktor_wohnung_2, 2)+" €");
			lb_menge_abwasser_wohnung_2.setText(""+BasicFunctions.roundDoubleNachkommastellen(menge_abwasser * faktor_wohnung_2, 2)+" m³");
			lb_kosten_abwasser_wohnung_2.setText(""+BasicFunctions.roundDoubleNachkommastellen(betrag_brutto_abwasser * faktor_wohnung_2, 2)+" €");
			
			//Set Labels Kosten Insgesamt
			double gesamtkosten_wohnung_1 = (betrag_brutto_strom + betrag_brutto_erdgas + betrag_brutto_wasser + betrag_brutto_abwasser) * faktor_wohnung_1;
			double gesamtkosten_wohnung_2 = (betrag_brutto_strom + betrag_brutto_erdgas + betrag_brutto_wasser + betrag_brutto_abwasser) * faktor_wohnung_2;
			
			double aufschlag_wohnung1 =0;
			double aufschlag_wohnung2 =0;
			double gesamtkosten_zusammen = 0;
			boolean aufschlag = false;
			
			//Wenn Ende nicht gefunden wurde => mittels der zukünftigen Abschläge der letzten Zahlung rechnen
			if(von_hat_begonnen==true) {
				aufschlag = true;
				ResultSet rs_rechnung = db.executeQueryWithResult("SELECT `rechnung`.`id`, `zeitraum_id`, `zeitraum`.`zeitraum_bis` ,`betrag_zukuenftige_abschlaege` FROM `rechnung`\r\n" + 
						"INNER JOIN `zeitraum` on `rechnung`.`zeitraum_id` = `zeitraum`.`id`\r\n" + 
						" ORDER BY `rechnung`.`id` DESC LIMIT 1");
				
				
				if(rs_rechnung.next()) {
					LocalDate zeitraum_bis = rs_rechnung.getDate("zeitraum_bis").toLocalDate();
					int anz_tage_rest = (int) (ChronoUnit.DAYS.between(zeitraum_bis, bis_in) + 1);
					
					double abschlaege = rs_rechnung.getDouble("betrag_zukuenftige_abschlaege");
					double gesamtbetrag = abschlaege * 12 / 365 * anz_tage_rest;
					
					aufschlag_wohnung1 = gesamtbetrag * faktor_wohnung_1;
					aufschlag_wohnung2 = gesamtbetrag * faktor_wohnung_2;
					
					gesamtkosten_zusammen = gesamtkosten_wohnung_1 + gesamtkosten_wohnung_2;
					
					gesamtkosten_wohnung_1 += aufschlag_wohnung1;
					gesamtkosten_wohnung_2 += aufschlag_wohnung2;
					
					
				}
			}
			
			lb_gesamte_kosten_wohnung_1.setText(""+BasicFunctions.roundDoubleNachkommastellen(gesamtkosten_wohnung_1, 2)+" €");
			lb_gesamte_kosten_wohnung_2.setText(""+BasicFunctions.roundDoubleNachkommastellen(gesamtkosten_wohnung_2, 2)+" €");
			lb_gesamte_kosten_gesamt.setText(""+BasicFunctions.roundDoubleNachkommastellen((gesamtkosten_wohnung_1 + gesamtkosten_wohnung_2), 2)+" €");
			
			if(aufschlag) {
				Alert alert_1 = new Alert(AlertType.INFORMATION, "Gesamtkosten Wohnung 1 ohne Aufschlag: "+BasicFunctions.roundDoubleNachkommastellen((gesamtkosten_wohnung_1 - aufschlag_wohnung1),2)+"€\nGesamtkosten Wohnung 2 ohne Aufschlag: "+BasicFunctions.roundDoubleNachkommastellen((gesamtkosten_wohnung_2 - aufschlag_wohnung2),2)+"€ \nGesamtkosten ohne Aufschlag: "+BasicFunctions.roundDoubleNachkommastellen(gesamtkosten_zusammen,2)+"€ \n\nAufschlag Wohnung 1: "+BasicFunctions.roundDoubleNachkommastellen(aufschlag_wohnung1,2)+" €\nAufschlag Wohnung 2: "+BasicFunctions.roundDoubleNachkommastellen(aufschlag_wohnung2,2)+"€ \n", ButtonType.FINISH);
				alert_1.setTitle("Berechnung der Kosten");
				alert_1.setHeaderText("Die berechneten Gesamtkosten wurde teilweise mittels der aktuellen Abschlagszahlungen berechnet");
				alert_1.showAndWait();
			}
			
			//es existiert keine Rechung für diesen Zeitraum => an Hand der zukünftigen abschlagszahlungen berechnen
			if (anz_tage == 0) {
				Alert alert = new Alert(AlertType.INFORMATION, "", ButtonType.FINISH);
				alert.setTitle("Berechnung der Kosten");
			    alert.setHeaderText("Die berechneten Gesamtkosten wurden mittels der aktuellen Abschlagszahlungen berechnet");
				alert.showAndWait();
				
				ResultSet rs_rechnung = db.executeQueryWithResult("SELECT `id`,`betrag_zukuenftige_abschlaege` FROM `rechnung` ORDER BY `id` DESC LIMIT 1");
				
				if(rs_rechnung.next()) {
					double abschlaege = rs_rechnung.getDouble("betrag_zukuenftige_abschlaege");
					double gesamtbetrag = abschlaege * 12 / 365 * anz_tage_soll;
					
					double kosten_wohnung_1 = gesamtbetrag * faktor_wohnung_1;
					double kosten_wohnung_2 = gesamtbetrag * faktor_wohnung_2;
					
					lb_gesamte_kosten_wohnung_1.setText(""+BasicFunctions.roundDoubleNachkommastellen(kosten_wohnung_1, 2)+" €");
					lb_gesamte_kosten_wohnung_2.setText(""+BasicFunctions.roundDoubleNachkommastellen(kosten_wohnung_2, 2)+" €");
					lb_gesamte_kosten_gesamt.setText(""+BasicFunctions.roundDoubleNachkommastellen((gesamtbetrag), 2)+" €");
				}
				//System.out.println("Bitte einen anderen Zeitraum wählen");
				
				//--------------------------------------------------------------DIALOG ANZEIGEN
			}
				
			//System.out.println("Ist: "+anz_tage);
		} catch (SQLException e) {e.printStackTrace();
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

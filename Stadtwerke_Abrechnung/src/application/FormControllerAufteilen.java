package application;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.fxml.FXML;

public class FormControllerAufteilen {
	
	@FXML
	private JFXDatePicker dp_zeitraum_von;
	@FXML
	private JFXDatePicker dp_zeitraum_bis;
	@FXML
	private JFXButton bt_berechnen;
	
	
	public void action_bt_berechnen() {
		
		DB db = new DB();
		ResultSet rs_zeitraum = db.executeQueryWithResult("SELECT `id`, `zeitraum_von`, `zeitraum_bis` FROM `zeitraum` ORDER BY `zeitraum_von` ASC");
		
		LocalDate von_in = dp_zeitraum_von.getValue();
		LocalDate bis_in = dp_zeitraum_bis.getValue();
		System.out.println("Soll: "+(ChronoUnit.DAYS.between(von_in, bis_in) + 1));
		
		long anz_tage = 0;
		int zaehler =0;
		int speicher =0;
		boolean von_hat_begonnen = false;
		
		try {
			while(rs_zeitraum.next()) {
				zaehler++;
				LocalDate von_db = rs_zeitraum.getDate("zeitraum_von").toLocalDate();
				LocalDate bis_db = rs_zeitraum.getDate("zeitraum_bis").toLocalDate();
				System.out.println("Vergleich_von: "+von_db);
				System.out.println("Vergleich Bis : "+bis_db);
				
				if(von_in.isAfter(bis_db)) {	
					//hat nichts mit dem Vergleichszeitraum zu tun
					
				}
				
				
				
				else if (von_in.isAfter(von_db) && von_in.isBefore(bis_db) || von_in.isEqual(von_db) && von_in.isBefore(bis_db)) {
					//Anfang liegt im Vergleichszeitraum
					System.out.println("Mindestens der Anfang liegt drin");
					
					if(bis_in.isBefore(bis_db)  || bis_in.isEqual(bis_db)) {
						//Liegt komplett in dem Vergleichszeitraum
						System.out.println("Liegt komplett drin");
						System.out.println("Komplett: "+anz_tage);
						anz_tage += ChronoUnit.DAYS.between(von_in, bis_in) + 1;
						System.out.println("Komplett: "+anz_tage);
					}
					else if (!von_hat_begonnen) {
						//F‰ngt in diesem Zeitraum an, aber endet nicht innerhalb => n‰chster Zeitraum noch
						System.out.println("Nur der Anfang liegt drin");
						System.out.println("Anfang: "+anz_tage);
						anz_tage += ChronoUnit.DAYS.between(von_in, bis_db) + 1;
						System.out.println("Anfang: "+anz_tage);
						von_hat_begonnen = true;
						speicher = zaehler;
					}
				}
				
				else if (von_db.isBefore(bis_in) && bis_in.isBefore(bis_db)) {
					//endet in diesem Zeitraum, aber insgesamt nur teilweise drin
					System.out.println("Nur das Ende liegt drin");
					System.out.println("Ende: "+anz_tage);
					anz_tage += ChronoUnit.DAYS.between(von_db, bis_in) + 1;
					System.out.println("Ende:"+anz_tage);
					von_hat_begonnen = false; //Ende wurde gefunden
				}
				
				//Wenn er hier ankommt und der Startzeitpunkt schon gefunden wurde, ist liegt der aktuelle Zeitpunkt auch noch komplett drin
				else if(von_hat_begonnen && speicher != zaehler){
					System.out.println("Zwischendrin: "+anz_tage);
					anz_tage += (ChronoUnit.DAYS.between(von_db, bis_db) + 1);	//kompletter aktueller Zeitraum dazu nehmen
					System.out.println("Zwischendrin: "+anz_tage);
				}
				
				
				
			}
			if (anz_tage == 0) {
				//liegt irgendwie dumm
				System.out.println("Scheiﬂe gelaufen");
			}
				
			System.out.println("Ist: "+anz_tage);
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

package application;

import java.sql.ResultSet;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FormControllerAktuellerStand {
	
	@FXML
	private JFXTextField tf_zaehler_strom;
	@FXML
	private JFXTextField tf_zaehler_erdgas;
	@FXML
	private JFXTextField tf_zaehler_wasser;
	@FXML
	private JFXTextField tf_zaehler_abwasser;
	@FXML
	private Label lb_menge_strom;
	@FXML
	private Label lb_menge_erdgas;
	@FXML
	private Label lb_menge_wasser;
	@FXML
	private Label lb_menge_abwasser;
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
		
		//Zeitraum finden
		DB db = new DB();
		String sql_zeitraum = "SELECT `id`, `zeitraum_von`, `zeitraum_bis` FROM `zeitraum` ORDER BY `zeitraum_bis` DESC LIMIT 1";

		ResultSet rs_zeitraum = db.executeQueryWithResult(sql_zeitraum);

		try {
			if (rs_zeitraum.next()) {
				
			
				int id = rs_zeitraum.getInt("id");
				java.sql.Date sql_date_zeitraum_von = rs_zeitraum.getDate("zeitraum_von");
				java.sql.Date sql_date_zeitraum_bis = rs_zeitraum.getDate("zeitraum_bis");
				System.out.println(""+id);
				System.out.println(sql_date_zeitraum_von);
				System.out.println(sql_date_zeitraum_bis);
				
			}	
		}catch(Exception e) {}
		
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

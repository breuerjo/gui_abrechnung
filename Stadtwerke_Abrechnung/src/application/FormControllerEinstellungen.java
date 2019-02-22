package application;


import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class FormControllerEinstellungen {
	
	@FXML
	private ComboBox<Integer> cb_einstellung_id;
	@FXML
	private Label faktor_strom;
	@FXML
	private Label faktor_erdgas;
	@FXML
	private Label faktor_wasser;
	@FXML
	private Label faktor_abwasser;
	
	
	public void initialize() {
		System.out.println("hi");
		
		DB db = new DB();
		initComboBox(db);
				
	}
	
	public void initComboBox(DB db) {
		ResultSet rs = db.executeQueryWithResult("SELECT `id` FROM `einstellung`");

		try {
			ObservableList<Integer> list_einstellungen_ids = FXCollections.observableArrayList();

			while (rs.next()) {

				list_einstellungen_ids.add(rs.getInt("id"));

			}

			cb_einstellung_id.setItems(list_einstellungen_ids);

		} catch (SQLException e) {}
	}
	
	
	public void action_einstellung_id_gewaehlt() {
		System.out.println(cb_einstellung_id.getValue());
		
		DB db = new DB();
		ResultSet rs = db.executeQueryWithResult("SELECT * FROM `einstellung` WHERE `id` = "+cb_einstellung_id.getValue()+"");

		try {
			
			if (rs.next()) {

				faktor_strom.setText(rs.getString("faktor_strom"));
				faktor_erdgas.setText(rs.getString("faktor_erdgas"));
				faktor_wasser.setText(rs.getString("faktor_wasser"));
				faktor_abwasser.setText(rs.getString("faktor_abwasser"));

			}


		} catch (SQLException e) {}
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
	
	public void action_menu_settings() {
		Main.setStage("Einstellungen");
	}
}

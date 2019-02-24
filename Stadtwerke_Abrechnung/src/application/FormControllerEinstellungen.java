package application;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

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
	
	//Neue Einstellungen:
	@FXML
	private ComboBox<Integer> cb_einstellung_neu_id;
	@FXML
	private JFXTextField tf_faktor_strom;
	@FXML
	private JFXTextField tf_faktor_erdgas;
	@FXML
	private JFXTextField tf_faktor_wasser;
	@FXML
	private JFXTextField tf_faktor_abwasser;
	@FXML
	private JFXTextField tf_steuersatz_strom;
	@FXML
	private JFXTextField tf_steuersatz_erdgas;
	@FXML
	private JFXTextField tf_umsatzsteuer_strom;
	@FXML
	private JFXTextField tf_umsatzsteuer_erdgas;
	@FXML
	private JFXTextField tf_umsatzsteuer_wasser;
	@FXML
	private JFXTextField tf_umsatzsteuer_abwasser;
	
	@FXML
	private JFXButton bt_einstellungen_speichern;
	
	
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
			cb_einstellung_neu_id.setItems(list_einstellungen_ids);

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
	
	public void action_einstellung_neu_id_gewaehlt() {
		System.out.println(cb_einstellung_id.getValue());
		
		DB db = new DB();
		ResultSet rs = db.executeQueryWithResult("SELECT * FROM `einstellung` WHERE `id` = "+cb_einstellung_neu_id.getValue()+"");

		try {
			
			if (rs.next()) {

				tf_faktor_strom.setText(rs.getString("faktor_strom"));
				tf_faktor_erdgas.setText(rs.getString("faktor_erdgas"));
				tf_faktor_wasser.setText(rs.getString("faktor_wasser"));
				tf_faktor_abwasser.setText(rs.getString("faktor_abwasser"));

			}


		} catch (SQLException e) {}
	}
	
	public void action_neue_einstellung_speichern() {
		DB db = new DB();
		Connection con = db.getConnection();
		String sql_zeitraum = "INSERT INTO `einstellung`(`steuersatz_strom`, `steuersatz_erdgas`, `faktor_strom`, `faktor_erdgas`, `faktor_wasser`, `faktor_abwasser`, `umsatzsteuer_strom`, `umsatzsteuer_erdgas`, `umsatzsteuer_wasser`, `umsatzsteuer_abwasser`) VALUES (?,?,?,?,?,?,?,?,?,?)";
		
		int gen_key_zeitraum = 0;
		try {
			PreparedStatement ps= con.prepareStatement(sql_zeitraum, Statement.RETURN_GENERATED_KEYS); 
			
			ps.setFloat(1, Float.parseFloat(tf_steuersatz_strom.getText()));
			ps.setFloat(2, Float.parseFloat(tf_steuersatz_erdgas.getText()));
			ps.setFloat(3, Float.parseFloat(tf_faktor_strom.getText()));
			ps.setFloat(4, Float.parseFloat(tf_faktor_erdgas.getText()));
			ps.setFloat(5, Float.parseFloat(tf_faktor_wasser.getText()));
			ps.setFloat(6, Float.parseFloat(tf_faktor_abwasser.getText()));
			ps.setFloat(7, Float.parseFloat(tf_umsatzsteuer_strom.getText()));
			ps.setFloat(8, Float.parseFloat(tf_umsatzsteuer_erdgas.getText()));
			ps.setFloat(9, Float.parseFloat(tf_umsatzsteuer_wasser.getText()));
			ps.setFloat(10, Float.parseFloat(tf_umsatzsteuer_abwasser.getText()));
			
		    
		    gen_key_zeitraum = db.executeUpdate(ps);
		    System.out.println(gen_key_zeitraum);
		    
		    
		    
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

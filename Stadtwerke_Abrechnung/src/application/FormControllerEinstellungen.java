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
	private Label lb_faktor_strom;
	@FXML
	private Label lb_faktor_erdgas;
	@FXML
	private Label lb_faktor_wasser;
	@FXML
	private Label lb_faktor_abwasser;
	@FXML
	private Label lb_steuersatz_strom;
	@FXML
	private Label lb_steuersatz_erdgas;
	@FXML
	private Label lb_umsatzsteuer_strom;
	@FXML
	private Label lb_umsatzsteuer_erdgas;
	@FXML
	private Label lb_umsatzsteuer_wasser;
	@FXML
	private Label lb_umsatzsteuer_abwasser;
	
	@FXML
	private Label lb_quadratmeter_gesamt;
	@FXML
	private Label lb_erdgas_zustandszahl;
	@FXML
	private Label lb_erdgas_brennwert;
	@FXML
	private Label lb_pauschale_abwasser_faktor;
	@FXML
	private Label lb_preis_strom;
	@FXML
	private Label lb_preis_erdgas;
	@FXML
	private Label lb_preis_wasser;
	
	@FXML
	private Label lb_preis_abwasser;
	@FXML
	private Label lb_pauschale_strom;
	@FXML
	private Label lb_pauschale_erdgas;
	@FXML
	private Label lb_pauschale_wasser;
	
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
	private JFXTextField tf_quadratmeter_gesamt;
	@FXML
	private JFXTextField tf_erdgas_zustandszahl;
	@FXML
	private JFXTextField tf_erdgas_brennwert;
	@FXML
	private JFXTextField tf_pauschale_abwasser_faktor;
	@FXML
	private JFXTextField tf_preis_strom;
	@FXML
	private JFXTextField tf_preis_erdgas;
	@FXML
	private JFXTextField tf_preis_wasser;
	
	@FXML
	private JFXTextField tf_preis_abwasser;
	@FXML
	private JFXTextField tf_pauschale_strom;
	@FXML
	private JFXTextField tf_pauschale_erdgas;
	@FXML
	private JFXTextField tf_pauschale_wasser;

	
	@FXML
	private JFXButton bt_einstellungen_speichern;
	
	//Neuer Zähler hinzufügen
	@FXML
	private ComboBox<String> cb_kategorie;
	@FXML
	private JFXTextField tf_zaehler_nummer;
	@FXML
	private JFXButton bt_zaehler_speichern;
	
	//Anzahl Quadratmeter ändern
		
	@FXML
	private JFXTextField tf_quadratmeter_wohnung_1;
	@FXML
	private JFXTextField tf_quadratmeter_wohnung_2;
	@FXML
	private JFXButton bt_quadratmeter_speichern;
	
	
	public void initialize() {
		initComboBox();
		
		initQuadratmeterTab();
	}
	
	public void initComboBox() {
		DB db = new DB();
		//EinstellungsComboBox
		ResultSet rs = db.executeQueryWithResult("SELECT `id` FROM `einstellung`");
		ResultSet rs_einstellung = db.executeQueryWithResult("SELECT `id` FROM `einstellung` ORDER BY `id`DESC LIMIT 1");
		
		try {
			ObservableList<Integer> list_einstellungen_ids = FXCollections.observableArrayList();

			while (rs.next()) {

				list_einstellungen_ids.add(rs.getInt("id"));

			}
			
			if(rs_einstellung.next()) {
				cb_einstellung_id.setValue(rs_einstellung.getInt("id"));
				cb_einstellung_neu_id.setValue(rs_einstellung.getInt("id"));
				action_einstellung_id_gewaehlt();
				action_einstellung_neu_id_gewaehlt();
			}

			cb_einstellung_id.setItems(list_einstellungen_ids);
			cb_einstellung_neu_id.setItems(list_einstellungen_ids);

		} catch (SQLException e) {}
		
		//CB Kategorie für neuer Zähler
		ResultSet rs_kategorie = db.executeQueryWithResult("SELECT DISTINCT `kategorie` FROM `zaehler`");
		
		try {
			ObservableList<String> list_kategorien = FXCollections.observableArrayList();

			while (rs_kategorie.next()) {

				list_kategorien.add(rs_kategorie.getString("kategorie"));

			}

			cb_kategorie.setItems(list_kategorien);

		} catch (SQLException e) {}
	}
	
	public void initQuadratmeterTab() {
		DB db = new DB();
		ResultSet rs_einstellung = db.executeQueryWithResult("SELECT `quadratmeter_wohnung_1`, `quadratmeter_wohnung_2` FROM `einstellung` ORDER BY `id` DESC LIMIT 1 ");
		try {
			if (rs_einstellung.next()) {
				
				tf_quadratmeter_wohnung_1.setText(""+rs_einstellung.getInt("quadratmeter_wohnung_1"));
				tf_quadratmeter_wohnung_2.setText(""+rs_einstellung.getInt("quadratmeter_wohnung_2"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
	
	public void action_bt_quadratmeter_speichern() {
		
		int quadratmeter_wohnung_1 = Integer.parseInt(tf_quadratmeter_wohnung_1.getText());
		int quadratmeter_wohnung_2 = Integer.parseInt(tf_quadratmeter_wohnung_2.getText());
		
		tf_quadratmeter_wohnung_1.setText(""+quadratmeter_wohnung_1+" m²");
		tf_quadratmeter_wohnung_2.setText(""+quadratmeter_wohnung_2+" m²");
		
		DB db = new DB();
		Connection con = db.getConnection();
		try {
			PreparedStatement ps_einstellung = con.prepareStatement("UPDATE `einstellung` SET `quadratmeter_wohnung_1`= "+quadratmeter_wohnung_1+" ,`quadratmeter_wohnung_2`="+quadratmeter_wohnung_2+"  ORDER BY `id` DESC LIMIT 1");
			db.executeUpdate(ps_einstellung);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void action_einstellung_id_gewaehlt() {
		
		DB db = new DB();
		ResultSet rs = db.executeQueryWithResult("SELECT * FROM `einstellung` WHERE `id` = "+cb_einstellung_id.getValue()+"");

		try {
			
			if (rs.next()) {

				lb_faktor_strom.setText(rs.getString("faktor_strom"));
				lb_faktor_erdgas.setText(rs.getString("faktor_erdgas"));
				lb_faktor_wasser.setText(rs.getString("faktor_wasser"));
				lb_faktor_abwasser.setText(rs.getString("faktor_wasser"));
				lb_erdgas_zustandszahl.setText(rs.getString("erdgas_faktor_zustandszahl"));
				lb_erdgas_brennwert.setText(rs.getString("erdgas_faktor_brennwert"));
				lb_preis_strom.setText(rs.getString("preis_strom"));
				lb_preis_erdgas.setText(rs.getString("preis_erdgas"));
				lb_preis_wasser.setText(rs.getString("preis_wasser"));
				lb_preis_abwasser.setText(rs.getString("preis_abwasser"));
				lb_pauschale_strom.setText(rs.getString("pauschale_strom"));
				lb_pauschale_erdgas.setText(rs.getString("pauschale_erdgas"));
				lb_pauschale_wasser.setText(rs.getString("pauschale_wasser"));
				lb_quadratmeter_gesamt.setText(rs.getString("quadratmeter_gesamt"));
				lb_pauschale_abwasser_faktor.setText(rs.getString("pauschale_abwasser_faktor"));
				lb_steuersatz_strom.setText(rs.getString("steuersatz_strom"));
				lb_steuersatz_erdgas.setText(rs.getString("steuersatz_erdgas"));
				lb_umsatzsteuer_strom.setText(rs.getString("umsatzsteuer_strom"));
				lb_umsatzsteuer_erdgas.setText(rs.getString("umsatzsteuer_erdgas"));
				lb_umsatzsteuer_wasser.setText(rs.getString("umsatzsteuer_wasser"));
				lb_umsatzsteuer_abwasser.setText(rs.getString("umsatzsteuer_abwasser"));

			}


		} catch (SQLException e) {}
	}
	
	public void action_einstellung_neu_id_gewaehlt() {
		
		DB db = new DB();
		ResultSet rs = db.executeQueryWithResult("SELECT * FROM `einstellung` WHERE `id` = "+cb_einstellung_neu_id.getValue()+"");

		try {
			
			if (rs.next()) {
				
				tf_faktor_strom.setText(rs.getString("faktor_strom"));
				tf_faktor_erdgas.setText(rs.getString("faktor_erdgas"));
				tf_faktor_wasser.setText(rs.getString("faktor_wasser"));
				tf_faktor_abwasser.setText(rs.getString("faktor_wasser"));
				tf_erdgas_zustandszahl.setText(rs.getString("erdgas_faktor_zustandszahl"));
				tf_erdgas_brennwert.setText(rs.getString("erdgas_faktor_brennwert"));
				tf_preis_strom.setText(rs.getString("preis_strom"));
				tf_preis_erdgas.setText(rs.getString("preis_erdgas"));
				tf_preis_wasser.setText(rs.getString("preis_wasser"));
				tf_preis_abwasser.setText(rs.getString("preis_abwasser"));
				tf_pauschale_strom.setText(rs.getString("pauschale_strom"));
				tf_pauschale_erdgas.setText(rs.getString("pauschale_erdgas"));
				tf_pauschale_wasser.setText(rs.getString("pauschale_wasser"));
				tf_quadratmeter_gesamt.setText(rs.getString("quadratmeter_gesamt"));
				tf_pauschale_abwasser_faktor.setText(rs.getString("pauschale_abwasser_faktor"));
				tf_steuersatz_strom.setText(rs.getString("steuersatz_strom"));
				tf_steuersatz_erdgas.setText(rs.getString("steuersatz_erdgas"));
				tf_umsatzsteuer_strom.setText(rs.getString("umsatzsteuer_strom"));
				tf_umsatzsteuer_erdgas.setText(rs.getString("umsatzsteuer_erdgas"));
				tf_umsatzsteuer_wasser.setText(rs.getString("umsatzsteuer_wasser"));
				tf_umsatzsteuer_abwasser.setText(rs.getString("umsatzsteuer_abwasser"));

			}


		} catch (SQLException e) {}
	}
	
	public void action_neue_einstellung_speichern() {
		DB db = new DB();
		Connection con = db.getConnection();
		String sql_zeitraum = "INSERT INTO `einstellung`(`steuersatz_strom`, `steuersatz_erdgas`, `faktor_strom`, `faktor_erdgas`, `faktor_wasser`, `faktor_abwasser`, `umsatzsteuer_strom`, `umsatzsteuer_erdgas`, `umsatzsteuer_wasser`, `umsatzsteuer_abwasser`, `erdgas_faktor_zustandszahl`, `erdgas_faktor_brennwert`, `preis_strom`, `preis_erdgas`, `preis_wasser`, `preis_abwasser`, `pauschale_strom`, `pauschale_erdgas`, `pauschale_wasser`, `quadratmeter_gesamt`, `pauschale_abwasser_faktor`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		int gen_key = 0;
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
			
			ps.setFloat(11, Float.parseFloat(tf_erdgas_zustandszahl.getText()));
			ps.setFloat(12, Float.parseFloat(tf_erdgas_brennwert.getText()));
			ps.setFloat(13, Float.parseFloat(tf_preis_strom.getText()));
			ps.setFloat(14, Float.parseFloat(tf_preis_erdgas.getText()));
			ps.setFloat(15, Float.parseFloat(tf_preis_wasser.getText()));
			ps.setFloat(16, Float.parseFloat(tf_preis_abwasser.getText()));
			ps.setFloat(17, Float.parseFloat(tf_pauschale_strom.getText()));
			ps.setFloat(18, Float.parseFloat(tf_pauschale_erdgas.getText()));
			ps.setFloat(19, Float.parseFloat(tf_pauschale_wasser.getText()));
			ps.setFloat(20, Float.parseFloat(tf_quadratmeter_gesamt.getText()));
			ps.setFloat(21, Float.parseFloat(tf_pauschale_abwasser_faktor.getText()));
			
		    gen_key = db.executeUpdate(ps);
		    //System.out.println(gen_key);
		    
			initComboBox(); //ComboBox aktualisieren
			cb_einstellung_neu_id.setValue(gen_key);	//Neuer Wert in ComboBox auswählen
		    
		    
		    
		    
		} catch (SQLException e) {}
	}
	
	public void action_neuer_zaehler_hinzufuegen() {
		DB db = new DB();
		Connection con = db.getConnection();
		
		String sql_zeitraum = "INSERT INTO `zaehler`(`kategorie`, `nummer`) VALUES (?,?)";
		
		try {
			PreparedStatement ps= con.prepareStatement(sql_zeitraum, Statement.RETURN_GENERATED_KEYS); 
			
			ps.setString(1, cb_kategorie.getValue());
			ps.setString(2, tf_zaehler_nummer.getText());
		
			
		    db.executeUpdate(ps);
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

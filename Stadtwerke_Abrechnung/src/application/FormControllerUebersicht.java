package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FormControllerUebersicht {

	@FXML
	private TableView<ZahlungData> uebersicht_table_zahlungen;
	@FXML
	public TableColumn<ZahlungData, String> table_clmn_kategorie;
	@FXML
	public TableColumn<ZahlungData, String> table_clmn_akt_ztrm;
	@FXML
	public TableColumn<ZahlungData, String> table_clmn_betrag;
	@FXML
	private ComboBox<Date> cb_zeitraum_von;
	@FXML
	private ComboBox<Date> cb_zeitraum_bis;

	public void initialize() {

		// Get Connection
		DB db = new DB();

		initComboBox(db);
		initTable(db);
	}

	public void initTable(DB db) {
		try {
			//table_clmn_kategorie.setCellValueFactory(new PropertyValueFactory<ZahlungData, String>("zeitraum"));	//Zeitraum noch mit den Spaltennamen aus ZahlungData ändern 
			//table_clmn_akt_ztrm.setCellValueFactory(new PropertyValueFactory<ZahlungData, String>("zeitraum")); 
			//table_clmn_betrag.setCellValueFactory(new PropertyValueFactory<ZahlungData, String>("zeitraum")); 
			
			ObservableList<ZahlungData> data;
			data = FXCollections.observableArrayList();
			ResultSet rs = db.executeQueryWithResult("SELECT * FROM `zahlung`");
			db.printResultSet(rs);
			/*
			 * while (rs.next()) { ZahlungData sd = new ZahlungData();
			 * sd.menge_strom.set(rs.getString(2)); sd.menge_erdgas.set(rs.getString(3));
			 * sd.menge_wasser.set(rs.getString(4)); sd.menge_abwasser.set(rs.getString(5));
			 * sd.zeitraum.set(rs.getString(6)); data.add(sd); }
			 */
			// uebersicht_table_zahlungen.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initComboBox(DB db) {

		ResultSet rs = db.executeQueryWithResult("SELECT `zeitraum_von`, `zeitraum_bis` FROM `zeitraum`");
		// db.printResultSet(rs);

		try {
			ObservableList<Date> list_zt_von = FXCollections.observableArrayList();
			ObservableList<Date> list_zt_bis = FXCollections.observableArrayList();

			while (rs.next()) {

				list_zt_von.add(rs.getDate("zeitraum_von"));
				list_zt_bis.add(rs.getDate("zeitraum_bis"));

				// Date zt_von = rs.getDate("zeitraum_von");
				// System.out.println(zt_von);

			}

			cb_zeitraum_von.setItems(list_zt_von);
			cb_zeitraum_bis.setItems(list_zt_bis);

		} catch (SQLException e) {
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

	public void action_menu_settings() {
		Main.setStage("Einstellungen");
	}
}

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
	public TableColumn<ZahlungData, String> table_clmn_ztrm_von;
	@FXML
	public TableColumn<ZahlungData, String> table_clmn_ztrm_bis;
	@FXML
	public TableColumn<ZahlungData, String> table_clmn_gesbetrag;
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
			table_clmn_ztrm_von.setCellValueFactory(new PropertyValueFactory<ZahlungData, String>("zeitraum_von")); 
			table_clmn_ztrm_bis.setCellValueFactory(new PropertyValueFactory<ZahlungData, String>("zeitraum_bis"));
			table_clmn_gesbetrag.setCellValueFactory(new PropertyValueFactory<ZahlungData, String>("gesamtbetrag"));

			ObservableList<ZahlungData> data;
			data = FXCollections.observableArrayList();
			ResultSet rs = db.executeQueryWithResult("SELECT `zeitraum_id` FROM `zahlung`");
			
			//alle Zahlungen
			while (rs.next()) {
				ZahlungData z_d = new ZahlungData();
				
				//Zeitraum der einzelnen Zahlungen
				ResultSet rs_zeitraum = db.executeQueryWithResult("SELECT `zeitraum_von`, `zeitraum_bis` FROM `zeitraum` WHERE `id` = "+rs.getString("zeitraum_id")+"");
				rs_zeitraum.next();
				z_d.zeitraum_von.set(rs_zeitraum.getString(1));
				z_d.zeitraum_bis.set(rs_zeitraum.getString(2));
				
				//Gesamtbeträge der einzelnen Zahlungen
				ResultSet rs_gesbetrag = db.executeQueryWithResult("SELECT ( SELECT SUM(`zahlung`.`menge_strom`) FROM `zahlung` WHERE `zahlung`.`zeitraum_id` = '"+rs.getString(1)+"' ) + ( SELECT SUM(`zahlung`.`menge_erdgas`) FROM `zahlung` WHERE `zahlung`.`zeitraum_id` = '"+rs.getString(1)+"' ) + ( SELECT SUM(`zahlung`.`menge_wasser`) FROM `zahlung` WHERE `zahlung`.`zeitraum_id` = '"+rs.getString(1)+"' ) + ( SELECT SUM(`zahlung`.`menge_abwasser`) FROM `zahlung` WHERE `zahlung`.`zeitraum_id` = '"+rs.getString(1)+"' )");
				rs_gesbetrag.next();
				z_d.gesamtbetrag.set(rs_gesbetrag.getString(1));
				
				data.add(z_d);
			}

			uebersicht_table_zahlungen.setItems(data);

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

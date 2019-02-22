package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FormControllerUebersicht {

	// Allgemein
	@FXML
	private TableView<RechnungData> uebersicht_table_zahlungen;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_nummer;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_ztrm_von;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_ztrm_bis;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_betrag_strom; // sind Mengen ! => Beträge ist der falsche
																		// Begriff
	@FXML
	public TableColumn<RechnungData, String> table_clmn_betrag_erdgas;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_betrag_wasser;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_betrag_abwasser;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_gesbetrag;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_einstellungen;
	@FXML
	private ComboBox<Date> cb_zeitraum_von;
	@FXML
	private ComboBox<Date> cb_zeitraum_bis;

	// Strom
	@FXML
	private TableView<RechnungDataStrom> uebersicht_table_strom;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_strom_zeitraum;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_strom_zaehler;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_strom_alt;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_strom_neu;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_strom_differenz;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_strom_faktor;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_strom_menge;

	// Labels Strom Mengenberechnung
	@FXML
	private Label strom_zeitraum;
	@FXML
	private Label strom_zaehler;
	@FXML
	private Label strom_alt;
	@FXML
	private Label strom_neu;
	@FXML
	private Label strom_differenz;
	@FXML
	private Label strom_faktor;
	@FXML
	private Label strom_menge;

	// Labels Strom Kostenberechnung
	@FXML
	private Label strom_kosten_zeitraum;
	//@FXML
	//private Label strom_kosten_bezeichnung;
	@FXML
	private Label strom_kosten_menge;
	@FXML
	private Label strom_kosten_preis;
	@FXML
	private Label strom_kosten_betrag;
	@FXML
	private Label strom_kosten_verbrauch_zeitraum;
	@FXML
	private Label strom_kosten_verbrauch_menge;
	@FXML
	private Label strom_kosten_verbrauch_preis;
	@FXML
	private Label strom_kosten_verbrauch_betrag;
	@FXML
	private Label strom_kosten_pauschale_menge;
	@FXML
	private Label strom_kosten_pauschale_preis;
	@FXML
	private Label strom_kosten_pauschale_betrag;
	@FXML
	private Label strom_kosten_steuer_menge;
	@FXML
	private Label strom_kosten_steuer_preis;
	@FXML
	private Label strom_kosten_steuer_betrag;
	@FXML
	private Label strom_kosten_netto_menge;
	@FXML
	private Label strom_kosten_netto_betrag;
	@FXML
	private Label strom_kosten_umsatzsteuer;
	@FXML
	private Label strom_kosten_umsatzsteuer_betrag;
	@FXML
	private Label strom_kosten_brutto_betrag;
	

	// Labels Erdgas Mengenberechnung
	@FXML
	private Label erdgas_zeitraum;
	@FXML
	private Label erdgas_zaehler;
	@FXML
	private Label erdgas_alt;
	@FXML
	private Label erdgas_neu;
	@FXML
	private Label erdgas_differenz;
	@FXML
	private Label erdgas_faktor;
	@FXML
	private Label erdgas_menge;
	
	// Labels Erdgas Kostenberechnung
	@FXML
	private Label erdgas_kosten_verbrauch_zeitraum;
	@FXML
	private Label erdgas_kosten_verbrauch_menge;
	@FXML
	private Label erdgas_kosten_verbrauch_preis;
	@FXML
	private Label erdgas_kosten_verbrauch_betrag;
	@FXML
	private Label erdgas_kosten_pauschale_menge;
	@FXML
	private Label erdgas_kosten_pauschale_preis;
	@FXML
	private Label erdgas_kosten_pauschale_betrag;
	@FXML
	private Label erdgas_kosten_steuer_menge;
	@FXML
	private Label erdgas_kosten_steuer_preis;
	@FXML
	private Label erdgas_kosten_steuer_betrag;
	@FXML
	private Label erdgas_kosten_netto_menge;
	@FXML
	private Label erdgas_kosten_netto_betrag;
	@FXML
	private Label erdgas_kosten_umsatzsteuer;
	@FXML
	private Label erdgas_kosten_umsatzsteuer_betrag;
	@FXML
	private Label erdgas_kosten_brutto_betrag;
	
	
	// Labels Wasser Mengenberechnung
		@FXML
		private Label wasser_zeitraum;
		@FXML
		private Label wasser_zaehler;
		@FXML
		private Label wasser_alt;
		@FXML
		private Label wasser_neu;
		@FXML
		private Label wasser_differenz;
		@FXML
		private Label wasser_faktor;
		@FXML
		private Label wasser_menge;
		
		// Labels Wasser Kostenberechnung
		@FXML
		private Label wasser_kosten_verbrauch_zeitraum;
		@FXML
		private Label wasser_kosten_verbrauch_menge;
		@FXML
		private Label wasser_kosten_verbrauch_preis;
		@FXML
		private Label wasser_kosten_verbrauch_betrag;
		@FXML
		private Label wasser_kosten_pauschale_menge;
		@FXML
		private Label wasser_kosten_pauschale_preis;
		@FXML
		private Label wasser_kosten_pauschale_betrag;
		@FXML
		private Label wasser_kosten_netto_menge;
		@FXML
		private Label wasser_kosten_netto_betrag;
		@FXML
		private Label wasser_kosten_umsatzsteuer;
		@FXML
		private Label wasser_kosten_umsatzsteuer_betrag;
		@FXML
		private Label wasser_kosten_brutto_betrag;

	public void initialize() {

		// Get Connection
		DB db = new DB();

		initComboBox(db);
		initTableUebersicht(db);
		initTableStrom(db);
	}

	public void initTableUebersicht(DB db) {
		try {
			table_clmn_nummer.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("nummer"));
			table_clmn_ztrm_von.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("zeitraum_von"));
			table_clmn_ztrm_bis.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("zeitraum_bis"));
			table_clmn_betrag_strom.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("menge_strom"));
			table_clmn_betrag_erdgas
					.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("menge_erdgas"));
			table_clmn_betrag_wasser
					.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("menge_wasser"));
			table_clmn_betrag_abwasser
					.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("menge_abwasser"));
			table_clmn_gesbetrag.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("gesamtbetrag"));
			table_clmn_einstellungen
					.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("einstellungen"));

			ObservableList<RechnungData> data;
			data = FXCollections.observableArrayList();
			ResultSet rs = db.executeQueryWithResult("SELECT `zeitraum_id` FROM `rechnung`");

			// allgemeine Rechnungsübersicht
			while (rs.next()) {
				RechnungData z_d = new RechnungData();

				// alle Zeiträume
				ResultSet rs_zeitraum = db
						.executeQueryWithResult("SELECT `zeitraum_von`, `zeitraum_bis` FROM `zeitraum` WHERE `id` = "
								+ rs.getString("zeitraum_id") + "");
				rs_zeitraum.next();
				z_d.zeitraum_von.set(rs_zeitraum.getString("zeitraum_von"));
				z_d.zeitraum_bis.set(rs_zeitraum.getString("zeitraum_bis"));

				// Daten zu den einzelnen Rechnungen
				ResultSet rs_gesbetrag = db.executeQueryWithResult(
						"SELECT `id`, `einstellung_id`, `menge_strom`, `menge_erdgas`, `menge_wasser`, `menge_abwasser`, ( SELECT SUM(`betrag_brutto_strom`) FROM `rechnung` WHERE `zeitraum_id` = '"
								+ rs.getString(1)
								+ "' ) + ( SELECT SUM(`betrag_brutto_erdgas`) FROM `rechnung` WHERE `zeitraum_id` = '"
								+ rs.getString(1)
								+ "' ) + ( SELECT SUM(`betrag_brutto_wasser`) FROM `rechnung` WHERE `zeitraum_id` = '"
								+ rs.getString(1)
								+ "' ) + ( SELECT SUM(`betrag_brutto_abwasser`) FROM `rechnung` WHERE `zeitraum_id` = '"
								+ rs.getString(1) + "' ) FROM `rechnung` WHERE `zeitraum_id` = '" + rs.getString(1)
								+ "'");
				rs_gesbetrag.next();
				z_d.nummer.set(rs_gesbetrag.getString("id"));
				z_d.menge_strom.set(rs_gesbetrag.getString("menge_strom"));
				z_d.menge_erdgas.set(rs_gesbetrag.getString("menge_erdgas"));
				z_d.menge_wasser.set(rs_gesbetrag.getString("menge_wasser"));
				z_d.menge_abwasser.set(rs_gesbetrag.getString("menge_abwasser"));
				z_d.gesamtbetrag.set(rs_gesbetrag.getString(7));
				z_d.einstellungen.set(rs_gesbetrag.getString("einstellung_id"));

				data.add(z_d);
			}

			uebersicht_table_zahlungen.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initTableStrom(DB db) {
		try {
			table_clmn_strom_zeitraum.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("zeitraum"));
			table_clmn_strom_zaehler.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("zaehler"));
			table_clmn_strom_alt.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("alt"));
			table_clmn_strom_neu.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("neu"));
			table_clmn_strom_differenz.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("differenz"));
			table_clmn_strom_faktor.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("faktor"));
			table_clmn_strom_menge.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("menge"));

			//ObservableList<RechnungDataStrom> data_strom;
			//data_strom = FXCollections.observableArrayList();
			// zeitraum_id holen
			String zeitraum_von = "2017-05-24";
			String zeitraum_bis = "2018-05-17";
			String zeitraum_ges = zeitraum_von + " bis " + zeitraum_bis;
			ResultSet rs_zeitraum = db.executeQueryWithResult(
					"SELECT MAX(id), MAX(differenz_tage) FROM `zeitraum` WHERE `zeitraum_von` = '" + zeitraum_von
							+ "' and  `zeitraum_bis` = '" + zeitraum_bis + "'");

			// Rechnung mit Zeitraum holen
			if (rs_zeitraum.next()) {
				//RechnungDataStrom z_d = new RechnungDataStrom();

				ResultSet rs_rechnung = db.executeQueryWithResult(
						"SELECT * FROM `rechnung` WHERE `zeitraum_id` = " + rs_zeitraum.getString(1) + "");
				rs_rechnung.next();

				ResultSet rs_zaehlerstand = db.executeQueryWithResult("SELECT * FROM `zaehlerstand` WHERE `id` = "
						+ rs_rechnung.getString("zaehlerstand_id") + "");
				
				
				ResultSet rs_einstellung = db
						.executeQueryWithResult("SELECT * FROM `einstellung` WHERE `id` = "
								+ rs_rechnung.getString("einstellung_id") + "");
				//data_strom.add(z_d);

				rs_zaehlerstand.next();
				
				ResultSet rs_zaehler_strom = db.executeQueryWithResult("SELECT * FROM `zaehler` WHERE `id` = "+rs_zaehlerstand.getString("zaehler_id_strom")+"");
				ResultSet rs_zaehler_erdgas = db.executeQueryWithResult("SELECT * FROM `zaehler` WHERE `id` = "+rs_zaehlerstand.getString("zaehler_id_erdgas")+"");
				ResultSet rs_zaehler_wasser = db.executeQueryWithResult("SELECT * FROM `zaehler` WHERE `id` = "+rs_zaehlerstand.getString("zaehler_id_wasser")+"");
				//Abwasser kommt von Wasser
				
				
				rs_zaehler_strom.next();
				rs_zaehler_erdgas.next();
				rs_zaehler_wasser.next();
				rs_einstellung.next();

				/*
				 * z_d.zeitraum.set(zeitraum_ges);
				 * z_d.zaehler.set(rs_strom_zaehlerstand.getString("zaehler_id_strom"));
				 * z_d.alt.set(rs_strom_zaehlerstand.getString("strom_alt"));
				 * z_d.neu.set(rs_strom_zaehlerstand.getString("strom_neu"));
				 * z_d.differenz.set(rs_strom_zaehlerstand.getString("differenz_strom"));
				 * z_d.faktor.set(rs_strom_einstellung.getString("faktor_strom"));
				 * z_d.menge.set(rs_strom_rechnung.getString("menge_strom"));
				 */

				// Set Labels Mengenberechnung Strom
				strom_zeitraum.setText(zeitraum_ges);
				strom_zaehler.setText(rs_zaehler_strom.getString("nummer"));
				strom_alt.setText(rs_zaehlerstand.getString("strom_alt"));
				strom_neu.setText(rs_zaehlerstand.getString("strom_neu"));
				strom_differenz.setText(rs_zaehlerstand.getString("differenz_strom"));
				strom_faktor.setText(rs_einstellung.getString("faktor_strom"));
				strom_menge.setText(rs_rechnung.getString("menge_strom")+" kWh");

				// Set Labels Kostenberechnung Strom
				strom_kosten_verbrauch_zeitraum.setText(zeitraum_ges);
				strom_kosten_verbrauch_menge.setText(rs_rechnung.getString("menge_strom")+" kWh");
				strom_kosten_verbrauch_preis.setText("Verbrauch");
				strom_kosten_verbrauch_betrag.setText(rs_rechnung.getString("betrag_menge_strom")+" €");
				strom_kosten_pauschale_menge.setText(rs_zeitraum.getString(2)+" Tage");
				strom_kosten_pauschale_preis.setText("Preis");
				strom_kosten_pauschale_betrag.setText(rs_rechnung.getString("pauschale_strom")+" €");
				strom_kosten_steuer_menge.setText(rs_rechnung.getString("menge_strom")+" kWh");
				strom_kosten_steuer_preis.setText("Preis");
				strom_kosten_steuer_betrag.setText(rs_rechnung.getString("steuer_strom")+" €");
				strom_kosten_netto_menge.setText(rs_rechnung.getString("menge_strom")+" kWh");
				strom_kosten_netto_betrag.setText(rs_rechnung.getString("betrag_netto_strom")+" €");
				strom_kosten_umsatzsteuer.setText("Umsatzsteuer: "+rs_einstellung.getString("umsatzsteuer_strom")+"%");
				strom_kosten_umsatzsteuer_betrag.setText(rs_rechnung.getString("umsatz_steuer_strom")+" €");
				strom_kosten_brutto_betrag.setText(rs_rechnung.getString("betrag_brutto_strom")+" €");
				
				// Set Labels Mengenberechnung Erdgas
				erdgas_zeitraum.setText(zeitraum_ges);
				erdgas_zaehler.setText(rs_zaehler_erdgas.getString("nummer"));
				erdgas_alt.setText(rs_zaehlerstand.getString("erdgas_alt"));
				erdgas_neu.setText(rs_zaehlerstand.getString("erdgas_neu"));
				erdgas_differenz.setText(rs_zaehlerstand.getString("differenz_erdgas"));
				erdgas_faktor.setText(rs_einstellung.getString("faktor_erdgas"));
				erdgas_menge.setText(rs_rechnung.getString("menge_erdgas")+" kWh");
				
				// Set Labels Kostenberechnung Erdgas
				erdgas_kosten_verbrauch_zeitraum.setText(zeitraum_ges);
				erdgas_kosten_verbrauch_menge.setText(rs_rechnung.getString("menge_erdgas")+" kWh");
				erdgas_kosten_verbrauch_preis.setText("Verbrauch");
				erdgas_kosten_verbrauch_betrag.setText(rs_rechnung.getString("betrag_menge_erdgas")+" €");
				erdgas_kosten_pauschale_menge.setText(rs_zeitraum.getString(2)+" Tage");
				erdgas_kosten_pauschale_preis.setText("Preis");
				erdgas_kosten_pauschale_betrag.setText(rs_rechnung.getString("pauschale_erdgas")+" €");
				erdgas_kosten_steuer_menge.setText(rs_rechnung.getString("menge_erdgas")+" kWh");
				erdgas_kosten_steuer_preis.setText("Preis");
				erdgas_kosten_steuer_betrag.setText(rs_rechnung.getString("steuer_erdgas")+" €");
				erdgas_kosten_netto_menge.setText(rs_rechnung.getString("menge_erdgas")+" kWh");
				erdgas_kosten_netto_betrag.setText(rs_rechnung.getString("betrag_netto_erdgas")+" €");
				erdgas_kosten_umsatzsteuer.setText("Umsatzsteuer: "+rs_einstellung.getString("umsatzsteuer_erdgas")+"%");
				erdgas_kosten_umsatzsteuer_betrag.setText(rs_rechnung.getString("umsatz_steuer_erdgas")+" €");
				erdgas_kosten_brutto_betrag.setText(rs_rechnung.getString("betrag_brutto_erdgas")+" €");
				
				// Set Labels Mengenberechnung Wasser
				wasser_zeitraum.setText(zeitraum_ges);
				wasser_zaehler.setText(rs_zaehler_erdgas.getString("nummer"));
				wasser_alt.setText(rs_zaehlerstand.getString("wasser_alt"));
				wasser_neu.setText(rs_zaehlerstand.getString("wasser_neu"));
				wasser_differenz.setText(rs_zaehlerstand.getString("differenz_wasser"));
				wasser_faktor.setText(rs_einstellung.getString("faktor_wasser"));
				wasser_menge.setText(rs_rechnung.getString("menge_wasser")+" m3");
				
				// Set Labels Kostenberechnung Wasser
				wasser_kosten_verbrauch_zeitraum.setText(zeitraum_ges);
				wasser_kosten_verbrauch_menge.setText(rs_rechnung.getString("menge_wasser")+" m3");
				wasser_kosten_verbrauch_preis.setText("Verbrauch");
				wasser_kosten_verbrauch_betrag.setText(rs_rechnung.getString("betrag_menge_wasser")+" €");
				wasser_kosten_pauschale_menge.setText(rs_zeitraum.getString(2)+" Tage");
				wasser_kosten_pauschale_preis.setText("Preis");
				wasser_kosten_pauschale_betrag.setText(rs_rechnung.getString("pauschale_wasser")+" €");
				wasser_kosten_netto_menge.setText(rs_rechnung.getString("menge_wasser")+" m3");
				wasser_kosten_netto_betrag.setText(rs_rechnung.getString("betrag_netto_wasser")+" €");
				wasser_kosten_umsatzsteuer.setText("Umsatzsteuer: "+rs_einstellung.getString("umsatzsteuer_wasser")+"%");
				wasser_kosten_umsatzsteuer_betrag.setText(rs_rechnung.getString("umsatz_steuer_wasser")+" €");
				wasser_kosten_brutto_betrag.setText(rs_rechnung.getString("betrag_brutto_wasser")+" €");
			}

			// uebersicht_table_strom.setItems(data_strom);

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

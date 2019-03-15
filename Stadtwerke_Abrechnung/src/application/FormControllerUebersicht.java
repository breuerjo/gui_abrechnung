package application;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
	public TableColumn<RechnungData, String> table_clmn_betrag_strom; 
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
	public TableColumn<RechnungData, String> table_clmn_nachzahlung;
	@FXML
	public TableColumn<RechnungData, String> table_clmn_abschlagszahlungen;
	/*@FXML
	private ComboBox<Date> cb_zeitraum_von;
	@FXML
	private ComboBox<Date> cb_zeitraum_bis;*/
	
	@FXML
	private JFXButton bt_zeitraum_geweahlt;

	

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
	// @FXML
	// private Label strom_kosten_bezeichnung;
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

	// Labels Abwasser Mengenberechnung
	@FXML
	private Label abwasser_zeitraum;
	@FXML
	private Label abwasser_zaehler;
	@FXML
	private Label abwasser_alt;
	@FXML
	private Label abwasser_neu;
	@FXML
	private Label abwasser_differenz;
	@FXML
	private Label abwasser_faktor;
	@FXML
	private Label abwasser_menge;

	// Labels Abwasser Kostenberechnung
	@FXML
	private Label abwasser_kosten_verbrauch_zeitraum;
	@FXML
	private Label abwasser_kosten_verbrauch_menge;
	@FXML
	private Label abwasser_kosten_verbrauch_preis;
	@FXML
	private Label abwasser_kosten_verbrauch_betrag;
	@FXML
	private Label abwasser_kosten_pauschale_menge;
	@FXML
	private Label abwasser_kosten_pauschale_preis;
	@FXML
	private Label abwasser_kosten_pauschale_betrag;
	@FXML
	private Label abwasser_kosten_netto_menge;
	@FXML
	private Label abwasser_kosten_netto_betrag;
	@FXML
	private Label abwasser_kosten_umsatzsteuer;
	@FXML
	private Label abwasser_kosten_umsatzsteuer_betrag;
	@FXML
	private Label abwasser_kosten_brutto_betrag;

	public void initialize() {

		// Get Connection
		DB db = new DB();
		initTableUebersicht(db);
		
		//Standardmäßig neueste Rechnung in einzelnen Kategorien anzeigen
		ResultSet rs_zeitraum = db.executeQueryWithResult("SELECT `zeitraum_von`, `zeitraum_bis` FROM `zeitraum` ORDER BY `zeitraum_bis` DESC LIMIT 1");	
		try {
			if(rs_zeitraum.next()) {
				initTables(db, rs_zeitraum.getString("zeitraum_von"), rs_zeitraum.getString("zeitraum_bis"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
			table_clmn_nachzahlung
			.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("nachzahlung"));
			table_clmn_abschlagszahlungen
			.setCellValueFactory(new PropertyValueFactory<RechnungData, String>("abschlagszahlungen"));

			ObservableList<RechnungData> data;
			data = FXCollections.observableArrayList();
			ResultSet rs = db.executeQueryWithResult("SELECT `zeitraum_id` FROM `rechnung` ORDER BY `id`");

			// allgemeine Rechnungsübersicht
			while (rs.next()) {
				RechnungData z_d = new RechnungData();

				// alle Zeiträume
				ResultSet rs_zeitraum = db
						.executeQueryWithResult("SELECT `zeitraum_von`, `zeitraum_bis` FROM `zeitraum` WHERE `id` = "
								+ rs.getString("zeitraum_id") + " ORDER BY `zeitraum_bis`");
				rs_zeitraum.next();
				z_d.zeitraum_von.set(rs_zeitraum.getString("zeitraum_von"));
				z_d.zeitraum_bis.set(rs_zeitraum.getString("zeitraum_bis"));

				// Daten zu den einzelnen Rechnungen
				ResultSet rs_gesbetrag = db.executeQueryWithResult(
						"SELECT `id`, `einstellung_id`, `menge_strom`, `menge_erdgas`, `menge_wasser`, `menge_abwasser`, `betrag_nachzahlung`, `betrag_gezahlte_abschlaege`, ( SELECT SUM(`betrag_brutto_strom`) FROM `rechnung` WHERE `zeitraum_id` = '"
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
				z_d.gesamtbetrag.set(rs_gesbetrag.getString(9));
				z_d.einstellungen.set(rs_gesbetrag.getString("einstellung_id"));
				z_d.nachzahlung.set(rs_gesbetrag.getString("betrag_nachzahlung"));
				z_d.abschlagszahlungen.set(rs_gesbetrag.getString("betrag_gezahlte_abschlaege"));

				data.add(z_d);
			}

			uebersicht_table_zahlungen.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initTables(DB db, String zeitraum_von, String zeitraum_bis) {
		//
		try {

			String zeitraum_ges = zeitraum_von + " bis " + zeitraum_bis;
			ResultSet rs_zeitraum = db.executeQueryWithResult(
					"SELECT MAX(id), MAX(differenz_tage) FROM `zeitraum` WHERE `zeitraum_von` = '" + zeitraum_von
							+ "' and  `zeitraum_bis` = '" + zeitraum_bis + "'");

			// Rechnung mit Zeitraum holen
			if (rs_zeitraum.next()) {
				// RechnungDataStrom z_d = new RechnungDataStrom();

				ResultSet rs_rechnung = db.executeQueryWithResult(
						"SELECT * FROM `rechnung` WHERE `zeitraum_id` = " + rs_zeitraum.getString(1) + "");
				rs_rechnung.next();

				ResultSet rs_zaehlerstand = db.executeQueryWithResult(
						"SELECT * FROM `zaehlerstand` WHERE `id` = " + rs_rechnung.getString("zaehlerstand_id") + "");

				ResultSet rs_einstellung = db.executeQueryWithResult(
						"SELECT * FROM `einstellung` WHERE `id` = " + rs_rechnung.getString("einstellung_id") + "");
				// data_strom.add(z_d);

				rs_zaehlerstand.next();

				ResultSet rs_zaehler_strom = db.executeQueryWithResult(
						"SELECT * FROM `zaehler` WHERE `id` = " + rs_zaehlerstand.getString("zaehler_id_strom") + "");
				ResultSet rs_zaehler_erdgas = db.executeQueryWithResult(
						"SELECT * FROM `zaehler` WHERE `id` = " + rs_zaehlerstand.getString("zaehler_id_erdgas") + "");
				ResultSet rs_zaehler_wasser = db.executeQueryWithResult(
						"SELECT * FROM `zaehler` WHERE `id` = " + rs_zaehlerstand.getString("zaehler_id_wasser") + "");
				// Abwasser kommt von Wasser

				rs_zaehler_strom.next();
				rs_zaehler_erdgas.next();
				rs_zaehler_wasser.next();
				rs_einstellung.next();

	

				// Set Labels Mengenberechnung Strom
				strom_zeitraum.setText(zeitraum_ges);
				strom_zaehler.setText(rs_zaehler_strom.getString("nummer"));
				strom_alt.setText(rs_zaehlerstand.getString("strom_alt"));
				strom_neu.setText(rs_zaehlerstand.getString("strom_neu"));
				strom_differenz.setText(rs_zaehlerstand.getString("differenz_strom"));
				strom_faktor.setText(rs_einstellung.getString("faktor_strom"));
				strom_menge.setText(rs_rechnung.getString("menge_strom") + " kWh");
				

				// Set Labels Kostenberechnung Strom
				strom_kosten_verbrauch_zeitraum.setText(zeitraum_ges);
				strom_kosten_verbrauch_menge.setText(rs_rechnung.getString("menge_strom") + " kWh");
				strom_kosten_verbrauch_preis.setText(""+rs_einstellung.getDouble("preis_strom")+" €");
				strom_kosten_verbrauch_betrag.setText(rs_rechnung.getString("betrag_menge_strom") + " €");
				strom_kosten_pauschale_menge.setText(rs_zeitraum.getString(2) + " Tage");
				strom_kosten_pauschale_preis.setText(""+rs_einstellung.getDouble("pauschale_strom")+" €");
				strom_kosten_pauschale_betrag.setText(rs_rechnung.getString("pauschale_strom") + " €");
				strom_kosten_steuer_menge.setText(rs_rechnung.getString("menge_strom") + " kWh");
				strom_kosten_steuer_preis.setText(""+rs_einstellung.getDouble("steuersatz_strom")+" €");
				strom_kosten_steuer_betrag.setText(rs_rechnung.getString("steuer_strom") + " €");
				strom_kosten_netto_menge.setText(rs_rechnung.getString("menge_strom") + " kWh");
				strom_kosten_netto_betrag.setText(rs_rechnung.getString("betrag_netto_strom") + " €");
				strom_kosten_umsatzsteuer
						.setText("Umsatzsteuer: " + Double.parseDouble(rs_einstellung.getString("umsatzsteuer_strom"))*100 + "%");
				strom_kosten_umsatzsteuer_betrag.setText(rs_rechnung.getString("umsatz_steuer_strom") + " €");
				strom_kosten_brutto_betrag.setText(rs_rechnung.getString("betrag_brutto_strom") + " €");

				// Set Labels Mengenberechnung Erdgas
				erdgas_zeitraum.setText(zeitraum_ges);
				erdgas_zaehler.setText(rs_zaehler_erdgas.getString("nummer"));
				erdgas_alt.setText(rs_zaehlerstand.getString("erdgas_alt"));
				erdgas_neu.setText(rs_zaehlerstand.getString("erdgas_neu"));
				erdgas_differenz.setText(rs_zaehlerstand.getString("differenz_erdgas"));
				erdgas_faktor.setText(rs_einstellung.getString("faktor_erdgas"));
				erdgas_menge.setText(rs_rechnung.getString("menge_erdgas") + " kWh");

				// Set Labels Kostenberechnung Erdgas
				erdgas_kosten_verbrauch_zeitraum.setText(zeitraum_ges);
				erdgas_kosten_verbrauch_menge.setText(rs_rechnung.getString("menge_erdgas") + " kWh");
				erdgas_kosten_verbrauch_preis.setText(""+rs_einstellung.getDouble("preis_erdgas")+" €");
				erdgas_kosten_verbrauch_betrag.setText(rs_rechnung.getString("betrag_menge_erdgas") + " €");
				erdgas_kosten_pauschale_menge.setText(rs_zeitraum.getString(2) + " Tage");
				erdgas_kosten_pauschale_preis.setText(""+rs_einstellung.getDouble("pauschale_erdgas")+" €");
				erdgas_kosten_pauschale_betrag.setText(rs_rechnung.getString("pauschale_erdgas") + " €");
				erdgas_kosten_steuer_menge.setText(rs_rechnung.getString("menge_erdgas") + " kWh");
				erdgas_kosten_steuer_preis.setText(""+rs_einstellung.getDouble("steuersatz_erdgas")+" €");
				erdgas_kosten_steuer_betrag.setText(rs_rechnung.getString("steuer_erdgas") + " €");
				erdgas_kosten_netto_menge.setText(rs_rechnung.getString("menge_erdgas") + " kWh");
				erdgas_kosten_netto_betrag.setText(rs_rechnung.getString("betrag_netto_erdgas") + " €");
				erdgas_kosten_umsatzsteuer
						.setText("Umsatzsteuer: " + Double.parseDouble(rs_einstellung.getString("umsatzsteuer_erdgas"))*100 + "%");
				erdgas_kosten_umsatzsteuer_betrag.setText(rs_rechnung.getString("umsatz_steuer_erdgas") + " €");
				erdgas_kosten_brutto_betrag.setText(rs_rechnung.getString("betrag_brutto_erdgas") + " €");

				// Set Labels Mengenberechnung Wasser
				wasser_zeitraum.setText(zeitraum_ges);
				wasser_zaehler.setText(rs_zaehler_wasser.getString("nummer"));
				wasser_alt.setText(rs_zaehlerstand.getString("wasser_alt"));
				wasser_neu.setText(rs_zaehlerstand.getString("wasser_neu"));
				wasser_differenz.setText(rs_zaehlerstand.getString("differenz_wasser"));
				wasser_faktor.setText(rs_einstellung.getString("faktor_wasser"));
				wasser_menge.setText(rs_rechnung.getString("menge_wasser") + " m³");

				// Set Labels Kostenberechnung Wasser
				wasser_kosten_verbrauch_zeitraum.setText(zeitraum_ges);
				wasser_kosten_verbrauch_menge.setText(rs_rechnung.getString("menge_wasser") + " m³");
				wasser_kosten_verbrauch_preis.setText(""+rs_einstellung.getDouble("preis_wasser")+" €");
				wasser_kosten_verbrauch_betrag.setText(rs_rechnung.getString("betrag_menge_wasser") + " €");
				wasser_kosten_pauschale_menge.setText(rs_zeitraum.getString(2) + " Tage");
				wasser_kosten_pauschale_preis.setText(""+rs_einstellung.getDouble("pauschale_wasser")+" €");
				wasser_kosten_pauschale_betrag.setText(rs_rechnung.getString("pauschale_wasser") + " €");
				wasser_kosten_netto_menge.setText(rs_rechnung.getString("menge_wasser") + " m³");
				wasser_kosten_netto_betrag.setText(rs_rechnung.getString("betrag_netto_wasser") + " €");
				wasser_kosten_umsatzsteuer
						.setText("Umsatzsteuer: " + BasicFunctions.roundDoubleNachkommastellen(Double.parseDouble(rs_einstellung.getString("umsatzsteuer_wasser"))*100 , 1)+ "%");
				wasser_kosten_umsatzsteuer_betrag.setText(rs_rechnung.getString("umsatz_steuer_wasser") + " €");
				wasser_kosten_brutto_betrag.setText(rs_rechnung.getString("betrag_brutto_wasser") + " €");

				// Set Labels Mengenberechnung Abwasser
				abwasser_zeitraum.setText(zeitraum_ges);
				abwasser_zaehler.setText(rs_zaehler_wasser.getString("nummer"));
				abwasser_alt.setText(rs_zaehlerstand.getString("wasser_alt"));
				abwasser_neu.setText(rs_zaehlerstand.getString("wasser_neu"));
				abwasser_differenz.setText(rs_zaehlerstand.getString("differenz_wasser"));
				abwasser_faktor.setText(rs_einstellung.getString("faktor_abwasser"));
				abwasser_menge.setText(rs_rechnung.getString("menge_abwasser") + " m³");

				// Set Labels Kostenberechnung Abwasser
				abwasser_kosten_verbrauch_zeitraum.setText(zeitraum_ges);
				abwasser_kosten_verbrauch_menge.setText(rs_rechnung.getString("menge_abwasser") + " m³");
				abwasser_kosten_verbrauch_preis.setText(""+rs_einstellung.getDouble("preis_abwasser")+" €");
				abwasser_kosten_verbrauch_betrag.setText(rs_rechnung.getString("betrag_menge_abwasser") + " €");
				abwasser_kosten_pauschale_menge.setText(rs_zeitraum.getString(2) + " Tage");
				abwasser_kosten_pauschale_preis.setText(""+rs_einstellung.getDouble("pauschale_abwasser_faktor") * rs_einstellung.getDouble("quadratmeter_gesamt")+" €");	//Im Jahr
				abwasser_kosten_pauschale_betrag.setText(rs_rechnung.getString("pauschale_abwasser") + " €");
				abwasser_kosten_netto_menge.setText(rs_rechnung.getString("menge_abwasser") + " m³");
				abwasser_kosten_netto_betrag.setText(rs_rechnung.getString("betrag_netto_abwasser") + " €");
				abwasser_kosten_umsatzsteuer
						.setText("Umsatzsteuer: " + Double.parseDouble(rs_einstellung.getString("umsatzsteuer_abwasser"))*100 + "%");
				abwasser_kosten_umsatzsteuer_betrag.setText(rs_rechnung.getString("umsatz_steuer_abwasser") + " €");
				abwasser_kosten_brutto_betrag.setText(rs_rechnung.getString("betrag_brutto_abwasser") + " €");
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void action_zeitraum_geweahlt() {
		RechnungData rechnung_data = uebersicht_table_zahlungen.getSelectionModel().getSelectedItem();
		//System.out.println(rechnung_data.getZeitraum_von());  
		//System.out.println(rechnung_data.getZeitraum_bis());
			
		DB db = new DB();
		initTables(db, rechnung_data.getZeitraum_von(), rechnung_data.getZeitraum_bis());
	}
	
	public void action_tabelle_zeitraum_ausgewaehlt() {
		//System.out.println("Zeile ausgewählt");
		RechnungData rechnung_data = uebersicht_table_zahlungen.getSelectionModel().getSelectedItem();
		//System.out.println(rechnung_data.getZeitraum_von());  
		//System.out.println(rechnung_data.getZeitraum_bis());  
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

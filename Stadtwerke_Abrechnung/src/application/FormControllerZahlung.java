package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.temporal.ChronoUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormControllerZahlung {

	@FXML
	private DatePicker dp_zeitraum_von;
	@FXML
	private DatePicker dp_zeitraum_bis;
	@FXML
	private ComboBox<String> cb_zaehler_strom;
	@FXML
	private ComboBox<String> cb_zaehler_erdgas;
	@FXML
	private ComboBox<String> cb_zaehler_wasser;
	@FXML
	private ComboBox<String> cb_zaehler_abwasser;
	@FXML
	private TextField tf_strom_alt;
	@FXML
	private TextField tf_strom_neu;
	@FXML
	private TextField tf_erdgas_alt;
	@FXML
	private TextField tf_erdgas_neu;
	@FXML
	private TextField tf_wasser_alt;
	@FXML
	private TextField tf_wasser_neu;
	@FXML
	private TextField tf_abwasser_alt;
	@FXML
	private TextField tf_abwasser_neu;
	@FXML
	private Label lb_dif_tage;
	@FXML
	private Label lb_dif_strom;
	@FXML
	private Label lb_dif_erdgas;
	@FXML
	private Label lb_dif_wasser;
	@FXML
	private Label lb_dif_abwasser;
	@FXML
	private Label lb_faktor_strom;
	@FXML
	private Label lb_faktor_erdgas;
	@FXML
	private Label lb_faktor_wasser;
	@FXML
	private Label lb_faktor_abwasser;
	@FXML
	private Label lb_menge_strom;
	@FXML
	private Label lb_menge_erdgas;
	@FXML
	private Label lb_menge_wasser;
	@FXML
	private Label lb_menge_abwasser;
	@FXML
	private Label lb_betrag_strom;
	@FXML
	private Label lb_betrag_erdgas;
	@FXML
	private Label lb_betrag_wasser;
	@FXML
	private Label lb_betrag_abwasser;
	@FXML
	private TextField tf_betrag_abweichung;
	@FXML
	private TextField tf_betrag_abschlagszahlung_bisher;
	@FXML
	private TextField tf_betrag_abschlagszahlung_zukunft;
	@FXML
	private Button bt_speichern;
	@FXML
	private ComboBox<Integer> cb_einstellungen_id;

	 public static double faktor_strom = 1; 
	 public static double faktor_wasser = 1; 
	 public static double faktor_abwasser = 1; 
	 public static double erdgas_faktor_zustandszahl = 0.9533; 
	 public static double erdgas_faktor_brennwert = 11.331;
	 
	 public static double preis_strom = 0.2510; 
	 public static double preis_erdgas = 0.0550; 
	 public static double preis_wasser = 2.27; 
	 public static double preis_abwasser = 1.10;
	 
	 // müssen alle noch /360 * anz_tage_zeitraum 
	 public static double pauschale_strom = 46.44; // im Jahr 
	 public static double pauschale_erdgas = 168.48; // im Jahr 
	 public static double pauschale_wasser = 28.66; // im Jahr 
	 public static double quadratmeter = 144; // im Jahr 
	 public static double pauschale_abwasser_faktor = 0.75; // im Jahr 
	 public static double pauschale_abwasser = quadratmeter * pauschale_abwasser_faktor; // im Jahr
	  
	 public static double steuer_strom = 0.0205; // pro Menge (kWh) public
	 public static double steuer_erdgas = 0.0055; // pro Menge (kWh)
	  
	 public static double umsatzsteuer_strom = 0.19; 
	 public static double umsatzsteuer_erdgas = 0.19; 
	 public static double umsatzsteuer_wasser = 0.07; 
	 public static double umsatzsteuer_abwasser = 0.0;
	 

	public void initialize() {
		DB db = new DB();
		lb_menge_strom.setId("label-bold");

		init_CB_Zaehler(db);
		initCBEinstellungen(db);

	}

	public void init_CB_Zaehler(DB db) {
		Connection con = db.getConnection();
		// Strom
		String sql_zaehler_strom = "SELECT `nummer` FROM `zaehler` WHERE `kategorie` = 'Strom'";
		ResultSet rs_zaehler_strom = db.executeQueryWithResult(sql_zaehler_strom);
		ObservableList<String> list_zaehler_strom = FXCollections.observableArrayList();
		// Erdgas
		String sql_zaehler_erdgas = "SELECT `nummer` FROM `zaehler` WHERE `kategorie` = 'Erdgas'";
		ResultSet rs_zaehler_erdgas = db.executeQueryWithResult(sql_zaehler_erdgas);
		ObservableList<String> list_zaehler_erdgas = FXCollections.observableArrayList();
		// Wasser
		String sql_zaehler_wasser = "SELECT `nummer` FROM `zaehler` WHERE `kategorie` = 'Wasser'";
		ResultSet rs_zaehler_wasser = db.executeQueryWithResult(sql_zaehler_wasser);
		ObservableList<String> list_zaehler_wasser = FXCollections.observableArrayList();
		// Abwasser = Wasser
		try {
			while (rs_zaehler_strom.next()) {
				list_zaehler_strom.add(rs_zaehler_strom.getString("nummer"));
			}
			while (rs_zaehler_erdgas.next()) {
				list_zaehler_erdgas.add(rs_zaehler_erdgas.getString("nummer"));
			}
			while (rs_zaehler_wasser.next()) {
				list_zaehler_wasser.add(rs_zaehler_wasser.getString("nummer"));
			}

		} catch (SQLException e) {
		}

		cb_zaehler_strom.getItems().setAll(list_zaehler_strom);
		cb_zaehler_erdgas.getItems().setAll(list_zaehler_erdgas);
		cb_zaehler_wasser.getItems().setAll(list_zaehler_wasser);
		cb_zaehler_abwasser.getItems().setAll(list_zaehler_wasser);
	}

	public void initCBEinstellungen(DB db) {

		// Get alle Einstellungen
		String sql_einstellungen = "SELECT `id` FROM `einstellung`";
		ResultSet rs_einstellungen = db.executeQueryWithResult(sql_einstellungen);
		ObservableList<Integer> list_einstellungen = FXCollections.observableArrayList();
		try {
			while (rs_einstellungen.next()) {
				list_einstellungen.add(rs_einstellungen.getInt("id"));
			}
		} catch (SQLException e) {
		}

		cb_einstellungen_id.getItems().setAll(list_einstellungen);

		// SET automatisch letzte Einstellung ID in der ComboBox
		int letzte_einstellung_id = 0;
		try {
			String sql_letzte_einstellung = "SELECT MAX(`id`) FROM `einstellung`";
			ResultSet rs_letzte_einstellung_id = db.executeQueryWithResult(sql_letzte_einstellung);
			if (rs_letzte_einstellung_id.next()) {
				letzte_einstellung_id = rs_letzte_einstellung_id.getInt(1);
			}
		} catch (SQLException e1) {
		}
		cb_einstellungen_id.setValue(letzte_einstellung_id);

		action_einstellung_geweahlt();

	}

	public void action_einstellung_geweahlt() {
		// Einstellungs-Konstanten des Controllers aus der gewäehlten Einstellung initialisieren
		DB db = new DB();
		// Get gewählte Einstellungen
		
		String sql_geweahlte_einstellungen = "SELECT * FROM `einstellung` WHERE `id` = "+cb_einstellungen_id.getValue()+"";
		ResultSet rs_einstellungen = db.executeQueryWithResult(sql_geweahlte_einstellungen);
		try {
			if (rs_einstellungen.next()) {
				
				erdgas_faktor_zustandszahl = rs_einstellungen.getFloat("erdgas_faktor_zustandszahl");
				erdgas_faktor_brennwert = rs_einstellungen.getFloat("erdgas_faktor_brennwert");
				preis_strom = rs_einstellungen.getFloat("preis_strom");
				preis_erdgas = rs_einstellungen.getFloat("preis_erdgas");
				preis_wasser = rs_einstellungen.getFloat("preis_wasser");
				preis_abwasser = rs_einstellungen.getFloat("preis_abwasser");
				pauschale_strom = rs_einstellungen.getFloat("pauschale_strom");
				pauschale_erdgas = rs_einstellungen.getFloat("pauschale_erdgas");
				pauschale_wasser = rs_einstellungen.getFloat("pauschale_wasser");
				quadratmeter = rs_einstellungen.getFloat("quadratmeter_gesamt");
				pauschale_abwasser_faktor = rs_einstellungen.getFloat("pauschale_abwasser_faktor");
				pauschale_abwasser = quadratmeter * pauschale_abwasser_faktor;
				steuer_strom = rs_einstellungen.getFloat("steuersatz_strom");
				steuer_erdgas = rs_einstellungen.getFloat("steuersatz_erdgas");
				umsatzsteuer_strom = rs_einstellungen.getFloat("umsatzsteuer_strom");
				umsatzsteuer_erdgas = rs_einstellungen.getFloat("umsatzsteuer_erdgas");
				umsatzsteuer_wasser = rs_einstellungen.getFloat("umsatzsteuer_wasser");
				umsatzsteuer_abwasser = rs_einstellungen.getFloat("umsatzsteuer_abwasser");
				faktor_strom = rs_einstellungen.getInt("faktor_strom");
				faktor_wasser = rs_einstellungen.getInt("faktor_wasser");
				faktor_abwasser = rs_einstellungen.getInt("faktor_abwasser");
				lb_faktor_strom.setText(""+faktor_strom);
				lb_faktor_wasser.setText(""+faktor_wasser);
				lb_faktor_abwasser.setText(""+faktor_abwasser);
				lb_faktor_erdgas.setText(""+BasicFunctions.roundDoubleNachkommastellen((erdgas_faktor_brennwert* erdgas_faktor_brennwert),6));
								
				System.out.println("Einstellungen geupdated");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Werte neu berechnen, wenn neue Einstellung gewählt wurde
		action_strom_berechnen();
		action_ergas_berechnen();
		action_wasser_berechnen();
		action_abwasser_berechnen();
	}

	public void action_datum_berechnen() {
		if (dp_zeitraum_bis.getValue() != null && dp_zeitraum_von.getValue() != null) {
			long days_between = ChronoUnit.DAYS.between(dp_zeitraum_von.getValue(), dp_zeitraum_bis.getValue());
			lb_dif_tage.setText("" + (days_between + 1)); // +1, da inklusive bis-Datum
		}
	}

	public void action_strom_berechnen() {

		if (!tf_strom_alt.getText().isEmpty() && !tf_strom_neu.getText().isEmpty()) {
			int strom_neu = Integer.parseInt(tf_strom_neu.getText());
			int strom_alt = Integer.parseInt(tf_strom_alt.getText());
			int strom_dif = strom_neu - strom_alt;

			lb_dif_strom.setText("" + strom_dif);
			lb_menge_strom.setText("" + strom_dif * Double.parseDouble(lb_faktor_strom.getText())+" kWh" );
		}
	}

	public void action_ergas_berechnen() {
		if (!tf_erdgas_alt.getText().isEmpty() && !tf_erdgas_neu.getText().isEmpty()) {
			int erdgas_neu = Integer.parseInt(tf_erdgas_neu.getText());
			int erdgas_alt = Integer.parseInt(tf_erdgas_alt.getText());
			int erdgas_dif = erdgas_neu - erdgas_alt;

			lb_dif_erdgas.setText("" + (erdgas_dif));
			lb_menge_erdgas.setText("" + (int) Math.round(erdgas_dif * erdgas_faktor_zustandszahl * erdgas_faktor_brennwert)+" kWh");
		}
	}

	public void action_wasser_berechnen() {
		if (!tf_wasser_alt.getText().isEmpty() && !tf_wasser_neu.getText().isEmpty()) {
			int wasser_neu = Integer.parseInt(tf_wasser_neu.getText());
			int wasser_alt = Integer.parseInt(tf_wasser_alt.getText());
			int wasser_dif = wasser_neu - wasser_alt;

			lb_dif_wasser.setText("" + (wasser_neu - wasser_alt));
			lb_menge_wasser.setText("" + wasser_dif * Double.parseDouble(lb_faktor_wasser.getText())+" m3");
		}
	}

	public void action_abwasser_berechnen() {
		if (!tf_abwasser_alt.getText().isEmpty() && !tf_abwasser_neu.getText().isEmpty()) {
			int abwasser_neu = Integer.parseInt(tf_abwasser_neu.getText());
			int abwasser_alt = Integer.parseInt(tf_abwasser_alt.getText());
			int abwasser_dif = abwasser_neu - abwasser_alt;

			lb_dif_abwasser.setText("" + (abwasser_dif));
			lb_menge_abwasser.setText("" + abwasser_dif * Double.parseDouble(lb_faktor_abwasser.getText())+" m3");
		}
	}

	

	public void action_bt_speichern() {

		// Get Connection
		DB db = new DB();
		Connection con = db.getConnection();

		// SPEICHERN ZEITRAUM
		// --------------------------------------------------------------------------------------------

		String zeitraum_von_tag = DateConversion.fillUpValue(dp_zeitraum_von.getValue().getDayOfMonth());
		String zeitraum_von_monat = DateConversion.fillUpValue(dp_zeitraum_von.getValue().getMonthValue());
		String zeitraum_von_jahr = "" + dp_zeitraum_von.getValue().getYear();

		String zeitraum_bis_tag = DateConversion.fillUpValue(dp_zeitraum_bis.getValue().getDayOfMonth());
		String zeitraum_bis_monat = DateConversion.fillUpValue(dp_zeitraum_bis.getValue().getMonthValue());
		String zeitraum_bis_jahr = "" + dp_zeitraum_bis.getValue().getYear();

		java.sql.Date sql_date_zeitraum_von = DateConversion
				.dateConversion(zeitraum_von_jahr + "-" + zeitraum_von_monat + "-" + zeitraum_von_tag);
		java.sql.Date sql_date_zeitraum_bis = DateConversion
				.dateConversion("" + zeitraum_bis_jahr + "-" + zeitraum_bis_monat + "-" + zeitraum_bis_tag);

		String sql_zeitraum = "INSERT INTO `zeitraum`(`zeitraum_von`, `zeitraum_bis`, `differenz_tage`, `zeitraum_von_jahr`, `zeitraum_von_monat`, `zeitraum_von_tag`, `zeitraum_bis_jahr`, `zeitraum_bis_monat`, `zeitraum_bis_tag`) VALUES(?,?,?,?,?,?,?,?,?)";

		int gen_key_zeitraum = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql_zeitraum, Statement.RETURN_GENERATED_KEYS);

			ps.setDate(1, sql_date_zeitraum_von);
			ps.setDate(2, sql_date_zeitraum_bis);
			ps.setInt(3, Integer.parseInt(lb_dif_tage.getText()));
			ps.setInt(4, dp_zeitraum_von.getValue().getYear());
			ps.setInt(5, dp_zeitraum_von.getValue().getMonthValue());
			ps.setInt(6, dp_zeitraum_von.getValue().getDayOfMonth());
			ps.setInt(7, dp_zeitraum_bis.getValue().getYear());
			ps.setInt(8, dp_zeitraum_bis.getValue().getMonthValue());
			ps.setInt(9, dp_zeitraum_bis.getValue().getDayOfMonth());

			gen_key_zeitraum = db.executeUpdate(ps);
			System.out.println(gen_key_zeitraum);

		} catch (SQLException e) {
		}

		// SPEICHERN Zaehlerstand
		// --------------------------------------------------------------------------------------------
		String sql_zaehlerstand = "INSERT INTO `zaehlerstand`(`zaehler_id_strom`, `zaehler_id_erdgas`, `zaehler_id_wasser`, `strom_alt`, `strom_neu`, `erdgas_alt`, `erdgas_neu`, `wasser_alt`, `wasser_neu`, `differenz_strom`, `differenz_erdgas`, `differenz_wasser`)  "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

		int zaehler_id_strom = 0;
		int zaehler_id_erdgas = 0;
		int zaehler_id_wasser = 0;
		int gen_key_zaehlerstand = 0;
		try {

			// GET IDs von den ausgewählten Zählern
			ResultSet rs_zaehler_strom = db.executeQueryWithResult(
					"SELECT `id` FROM `zaehler` WHERE `nummer`= " + cb_zaehler_strom.getValue() + "");
			if (rs_zaehler_strom.next()) {
				zaehler_id_strom = rs_zaehler_strom.getInt("id");
			}

			ResultSet rs_zaehler_erdgas = db.executeQueryWithResult(
					"SELECT `id` FROM `zaehler` WHERE `nummer`= " + cb_zaehler_erdgas.getValue() + "");
			if (rs_zaehler_erdgas.next()) {
				zaehler_id_erdgas = rs_zaehler_erdgas.getInt("id");
			}

			ResultSet rs_zaehler_wasser = db.executeQueryWithResult(
					"SELECT `id` FROM `zaehler` WHERE `nummer`= '" + cb_zaehler_wasser.getValue() + "'");
			if (rs_zaehler_wasser.next()) {
				zaehler_id_wasser = rs_zaehler_wasser.getInt("id");
			}
			// der Abwasserzähler entspricht immer dem Wasserzähler! => keine Abfrage
			// benötigt

			PreparedStatement ps_zaehlerstand = con.prepareStatement(sql_zaehlerstand, Statement.RETURN_GENERATED_KEYS);

			ps_zaehlerstand.setInt(1, zaehler_id_strom);
			ps_zaehlerstand.setInt(2, zaehler_id_erdgas);
			ps_zaehlerstand.setInt(3, zaehler_id_wasser);
			ps_zaehlerstand.setInt(4, Integer.parseInt(tf_strom_alt.getText()));
			ps_zaehlerstand.setInt(5, Integer.parseInt(tf_strom_neu.getText()));
			ps_zaehlerstand.setInt(6, Integer.parseInt(tf_erdgas_alt.getText()));
			ps_zaehlerstand.setInt(7, Integer.parseInt(tf_erdgas_neu.getText()));
			ps_zaehlerstand.setInt(8, Integer.parseInt(tf_wasser_alt.getText()));
			ps_zaehlerstand.setInt(9, Integer.parseInt(tf_wasser_neu.getText()));
			ps_zaehlerstand.setInt(10, Integer.parseInt(lb_dif_strom.getText()));
			ps_zaehlerstand.setInt(11, Integer.parseInt(lb_dif_erdgas.getText()));
			ps_zaehlerstand.setInt(12, Integer.parseInt(lb_dif_wasser.getText()));

			gen_key_zaehlerstand = db.executeUpdate(ps_zaehlerstand);

		} catch (SQLException e) {
		}

		// SPEICHERN Rechnung
		// --------------------------------------------------------------------------------------------
		String sql_zahlung = "INSERT INTO `rechnung`(`zeitraum_id`, `zaehlerstand_id`, `einstellung_id`, `menge_strom`, `menge_erdgas`, `menge_wasser`, `menge_abwasser`, `betrag_menge_strom`, `betrag_menge_erdgas`, `betrag_menge_wasser`, `betrag_menge_abwasser`, `pauschale_strom`, `pauschale_erdgas`, `pauschale_wasser`, `pauschale_abwasser`, `steuer_strom`, `steuer_erdgas`, `betrag_netto_strom`, `betrag_netto_erdgas`, `betrag_netto_wasser`, `betrag_netto_abwasser`, `umsatz_steuer_strom`, `umsatz_steuer_erdgas`, `umsatz_steuer_wasser`, `umsatz_steuer_abwasser`, `betrag_brutto_strom`, `betrag_brutto_erdgas`, `betrag_brutto_wasser`, `betrag_brutto_abwasser`, `betrag_nachzahlung`,`betrag_gezahlte_abschlaege`,`betrag_zukuenftige_abschlaege`)  "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; // 32

		try {
			PreparedStatement ps_zahlung = con.prepareStatement(sql_zahlung, Statement.RETURN_GENERATED_KEYS);

			// FKs
			ps_zahlung.setInt(1, gen_key_zeitraum);
			ps_zahlung.setInt(2, gen_key_zaehlerstand);
			ps_zahlung.setInt(3, cb_einstellungen_id.getValue());

			// Mengen
			double menge_strom = Double.parseDouble(lb_menge_strom.getText());
			double menge_erdgas = Double.parseDouble(lb_menge_erdgas.getText());
			double menge_wasser = Double.parseDouble(lb_menge_wasser.getText());
			double menge_abwasser = Double.parseDouble(lb_menge_abwasser.getText());

			ps_zahlung.setDouble(4, menge_strom);
			ps_zahlung.setDouble(5, menge_erdgas);
			ps_zahlung.setDouble(6, menge_wasser);
			ps_zahlung.setDouble(7, menge_abwasser);

			// Betrag_Mengen
			double betrag_menge_strom = menge_strom * preis_strom;
			double betrag_menge_erdgas = menge_erdgas * preis_erdgas;
			double betrag_menge_wasser = menge_wasser * preis_wasser;
			double betrag_menge_abwasser = menge_abwasser * preis_abwasser;

			ps_zahlung.setDouble(8, betrag_menge_strom);
			ps_zahlung.setDouble(9, betrag_menge_erdgas);
			ps_zahlung.setDouble(10, betrag_menge_wasser);
			ps_zahlung.setDouble(11, betrag_menge_abwasser);
			
			lb_betrag_strom.setText(""+betrag_menge_strom +" Euro");
			lb_betrag_erdgas.setText(""+betrag_menge_erdgas +" Euro");
			lb_betrag_wasser.setText(""+betrag_menge_wasser +" Euro");
			lb_betrag_abwasser.setText(""+betrag_menge_abwasser +" Euro");

			// Pauschalen alle /360 * anz_tage_zeitraum
			int anz_tage = Integer.parseInt(lb_dif_tage.getText());

			double pauschale_strom_lokal = anz_tage * pauschale_strom / 365;
			double pauschale_erdgas_lokal = anz_tage * pauschale_erdgas / 365;
			double pauschale_wasser_lokal = anz_tage * pauschale_wasser / 365;
			double pauschale_abwasser_lokal = anz_tage * pauschale_abwasser / 365;

			ps_zahlung.setDouble(12, pauschale_strom_lokal);
			ps_zahlung.setDouble(13, pauschale_erdgas_lokal);
			ps_zahlung.setDouble(14, pauschale_wasser_lokal);
			ps_zahlung.setDouble(15, pauschale_abwasser_lokal);

			// Steuer - nur für Strom und Erdgas
			double steuer_strom_betrag = menge_strom * steuer_strom;
			double steuer_erdgas_betrag = menge_erdgas * steuer_erdgas;

			ps_zahlung.setDouble(16, steuer_strom_betrag);
			ps_zahlung.setDouble(17, steuer_erdgas_betrag);

			// Betrag netto
			double betrag_netto_strom = betrag_menge_strom + pauschale_strom_lokal + steuer_strom_betrag;
			double betrag_netto_erdgas = betrag_menge_erdgas + pauschale_erdgas_lokal + steuer_erdgas_betrag;
			double betrag_netto_wasser = betrag_menge_wasser + pauschale_wasser_lokal;
			double betrag_netto_abwasser = betrag_menge_abwasser + pauschale_abwasser_lokal;

			ps_zahlung.setDouble(18, betrag_netto_strom);
			ps_zahlung.setDouble(19, betrag_netto_erdgas);
			ps_zahlung.setDouble(20, betrag_netto_wasser);
			ps_zahlung.setDouble(21, betrag_netto_abwasser);

			// Umsatzseteuer
			double umsatzsteuer_strom_lokal = betrag_netto_strom * umsatzsteuer_strom;
			double umsatzsteuer_erdgas_lokal = betrag_netto_erdgas * umsatzsteuer_erdgas;
			double umsatzsteuer_wasser_lokal = betrag_netto_wasser * umsatzsteuer_wasser;
			double umsatzsteuer_abwasser_lokal = betrag_netto_abwasser * umsatzsteuer_abwasser;

			ps_zahlung.setDouble(22, umsatzsteuer_strom_lokal);
			ps_zahlung.setDouble(23, umsatzsteuer_erdgas_lokal);
			ps_zahlung.setDouble(24, umsatzsteuer_wasser_lokal);
			ps_zahlung.setDouble(25, umsatzsteuer_abwasser_lokal);

			// Betrag brutto
			double betrag_brutto_strom = betrag_netto_strom + umsatzsteuer_strom_lokal;
			double betrag_brutto_erdgas = betrag_netto_erdgas + umsatzsteuer_erdgas_lokal;
			double betrag_brutto_wasser = betrag_netto_wasser + umsatzsteuer_wasser_lokal;
			double betrag_brutto_abwasser = betrag_netto_abwasser + umsatzsteuer_abwasser_lokal;

			ps_zahlung.setDouble(26, betrag_brutto_strom);
			ps_zahlung.setDouble(27, betrag_brutto_erdgas);
			ps_zahlung.setDouble(28, betrag_brutto_wasser);
			ps_zahlung.setDouble(29, betrag_brutto_abwasser);
			
			double abweichung = Double.parseDouble(tf_betrag_abweichung.getText());
			double abschlag_bisher = Double.parseDouble(tf_betrag_abschlagszahlung_bisher.getText());
			double abschlag_zukunft = Double.parseDouble(tf_betrag_abschlagszahlung_zukunft.getText());
			
			ps_zahlung.setDouble(30, abweichung);
			ps_zahlung.setDouble(31, abschlag_bisher);
			ps_zahlung.setDouble(32, abschlag_zukunft);

			int gen_key = db.executeUpdate(ps_zahlung);
			System.out.println(gen_key);

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
	
	public void action_menu_aktueller_stand() {
		Main.setStage("AktuellerStand");
	}

	public void action_menu_settings() {
		Main.setStage("Einstellungen");
	}

}

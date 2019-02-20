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
	private Button bt_speichern;
	@FXML
	private Button bt_berechnen;

	public static final double ERDGAS_FKTR_ZSTNDZHL = 0.9533;
	public static final double ERDGAS_FKTR_BRNWRT = 11.331;
	
	public static final double PREIS_STROM = 0.2510;	
	public static final double PREIS_ERDGAS = 0.0550;
	public static final double PREIS_WASSER = 2.27;
	public static final double PREIS_ABWASSER = 1.10;

	// müssen alle noch /360 * anz_tage_zeitraum
	public static final double PAUSCHALE_STROM = 46.44; // im Jahr
	public static final double PAUSCHALE_ERDGAS = 168.48; // im Jahr
	public static final double PAUSCHALE_WASSER = 28.66; // im Jahr
	public static final double QUADRATMETER = 144; // im Jahr
	public static final double PAUSCHALE_ABWASSER = QUADRATMETER * 0.75; // im Jahr

	public static final double STEUER_STROM = 0.0205; // pro Menge (kWh)
	public static final double STEUER_ERDGAS = 0.0055; // pro Menge (kWh)
	
	public static final double UMSATZSTEUER_STROM = 0.19;
	public static final double UMSATZSTEUER_ERDGAS = 0.19;
	public static final double UMSATZSTEUER_WASSER = 0.07;
	public static final double UMSATZSTEUER_ABWASSER = 0.0;
	

	public void initialize() {

		lb_menge_strom.setId("label-bold");
		
		init_CB_Zaehler();

		lb_faktor_strom.setText("1");
		lb_faktor_wasser.setText("1");
		lb_faktor_abwasser.setText("1");
	}
	
	public void init_CB_Zaehler() {
		DB db = new DB();
		Connection con = db.getConnection();
		//Strom
		String sql_zaehler_strom = "SELECT `nummer` FROM `zaehler` WHERE `kategorie` = 'Strom'"; 
		ResultSet rs_zaehler_strom = db.executeQueryWithResult(sql_zaehler_strom);
		ObservableList<String> list_zaehler_strom = FXCollections.observableArrayList();
		//Erdgas
		String sql_zaehler_erdgas = "SELECT `nummer` FROM `zaehler` WHERE `kategorie` = 'Erdgas'"; 
		ResultSet rs_zaehler_erdgas = db.executeQueryWithResult(sql_zaehler_erdgas);
		ObservableList<String> list_zaehler_erdgas = FXCollections.observableArrayList();
		//Wasser
		String sql_zaehler_wasser = "SELECT `nummer` FROM `zaehler` WHERE `kategorie` = 'Wasser'"; 
		ResultSet rs_zaehler_wasser = db.executeQueryWithResult(sql_zaehler_wasser);
		ObservableList<String> list_zaehler_wasser = FXCollections.observableArrayList();
		//Abwasser = Wasser
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
			
		} catch (SQLException e) {}
		
		cb_zaehler_strom.getItems().setAll(list_zaehler_strom);
		cb_zaehler_erdgas.getItems().setAll(list_zaehler_erdgas);
		cb_zaehler_wasser.getItems().setAll(list_zaehler_wasser);
		cb_zaehler_abwasser.getItems().setAll(list_zaehler_wasser);
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
			lb_menge_strom.setText("" + strom_dif * Integer.parseInt(lb_faktor_strom.getText()));
		}
	}

	public void action_ergas_berechnen() {
		if (!tf_erdgas_alt.getText().isEmpty() && !tf_erdgas_neu.getText().isEmpty()) {
			int erdgas_neu = Integer.parseInt(tf_erdgas_neu.getText());
			int erdgas_alt = Integer.parseInt(tf_erdgas_alt.getText());
			int erdgas_dif = erdgas_neu - erdgas_alt;

			lb_dif_erdgas.setText("" + (erdgas_dif));
			lb_menge_erdgas.setText("" + (int) Math.round(erdgas_dif * ERDGAS_FKTR_ZSTNDZHL * ERDGAS_FKTR_BRNWRT));
		}
	}

	public void action_wasser_berechnen() {
		if (!tf_wasser_alt.getText().isEmpty() && !tf_wasser_neu.getText().isEmpty()) {
			int wasser_neu = Integer.parseInt(tf_wasser_neu.getText());
			int wasser_alt = Integer.parseInt(tf_wasser_alt.getText());
			int wasser_dif = wasser_neu - wasser_alt;

			lb_dif_wasser.setText("" + (wasser_neu - wasser_alt));
			lb_menge_wasser.setText("" + wasser_dif * Integer.parseInt(lb_faktor_wasser.getText()));
		}
	}

	public void action_abwasser_berechnen() {
		if (!tf_abwasser_alt.getText().isEmpty() && !tf_abwasser_neu.getText().isEmpty()) {
			int abwasser_neu = Integer.parseInt(tf_abwasser_neu.getText());
			int abwasser_alt = Integer.parseInt(tf_abwasser_alt.getText());
			int abwasser_dif = abwasser_neu - abwasser_alt;

			lb_dif_abwasser.setText("" + (abwasser_dif));
			lb_menge_abwasser.setText("" + abwasser_dif * Integer.parseInt(lb_faktor_abwasser.getText()));
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

	public void action_bt_speichern () {
		
		//Get Connection
		DB db = new DB();
		Connection con = db.getConnection();
		
		
		//SPEICHERN ZEITRAUM --------------------------------------------------------------------------------------------
		
		String zeitraum_von_tag = DateConversion.fillUpValue(dp_zeitraum_von.getValue().getDayOfMonth());	
		String zeitraum_von_monat = DateConversion.fillUpValue(dp_zeitraum_von.getValue().getMonthValue());	
		String zeitraum_von_jahr = ""+ dp_zeitraum_von.getValue().getYear();
		
		String zeitraum_bis_tag = DateConversion.fillUpValue(dp_zeitraum_bis.getValue().getDayOfMonth());	
		String zeitraum_bis_monat = DateConversion.fillUpValue(dp_zeitraum_bis.getValue().getMonthValue());	
		String zeitraum_bis_jahr = ""+ dp_zeitraum_bis.getValue().getYear();
						
		java.sql.Date sql_date_zeitraum_von = DateConversion.dateConversion(zeitraum_von_jahr+"-"+zeitraum_von_monat+"-"+zeitraum_von_tag);
		java.sql.Date sql_date_zeitraum_bis = DateConversion.dateConversion(""+zeitraum_bis_jahr+"-"+zeitraum_bis_monat+"-"+zeitraum_bis_tag);
							
		String sql_zeitraum = "INSERT INTO `zeitraum`(`zeitraum_von`, `zeitraum_bis`, `differenz_tage`, `zeitraum_von_jahr`, `zeitraum_von_monat`, `zeitraum_von_tag`, `zeitraum_bis_jahr`, `zeitraum_bis_monat`, `zeitraum_bis_tag`) VALUES(?,?,?,?,?,?,?,?,?)";
		
		int gen_key_zeitraum = 0;
		try {
			PreparedStatement ps= con.prepareStatement(sql_zeitraum, Statement.RETURN_GENERATED_KEYS); 
			
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
		    
		    
		    
		} catch (SQLException e) {}
		
		
		//SPEICHERN Zaehlerstand --------------------------------------------------------------------------------------------
		String sql_zaehlerstand = "INSERT INTO `zaehlerstand`(`zaehler_id_strom`, `zaehler_id_erdgas`, `zaehler_id_wasser`, `strom_alt`, `strom_neu`, `erdgas_alt`, `erdgas_neu`, `wasser_alt`, `wasser_neu`, `differenz_strom`, `differenz_erdgas`, `differenz_wasser`)  "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		
		
		int zaehler_id_strom =0;
		int zaehler_id_erdgas =0;
		int zaehler_id_wasser =0;
		int gen_key_zaehlerstand = 0;
		try {
			
			//GET IDs von den ausgewählten Zählern
			ResultSet rs_zaehler_strom = db.executeQueryWithResult("SELECT `id` FROM `zaehler` WHERE `nummer`= "+ cb_zaehler_strom.getValue()+"");
			if(rs_zaehler_strom.next()) {
				zaehler_id_strom = rs_zaehler_strom.getInt("id");
			}
			
			ResultSet rs_zaehler_erdgas = db.executeQueryWithResult("SELECT `id` FROM `zaehler` WHERE `nummer`= "+ cb_zaehler_erdgas.getValue()+"");
			if(rs_zaehler_erdgas.next()) {
				zaehler_id_erdgas = rs_zaehler_erdgas.getInt("id");
			}
			
			ResultSet rs_zaehler_wasser = db.executeQueryWithResult("SELECT `id` FROM `zaehler` WHERE `nummer`= '"+ cb_zaehler_wasser.getValue()+"'");
			if(rs_zaehler_wasser.next()) {
				zaehler_id_wasser = rs_zaehler_wasser.getInt("id");
			}
			//der Abwasserzähler entspricht immer dem Wasserzähler! => keine Abfrage benötigt
			
			
			
			PreparedStatement ps_zaehlerstand= con.prepareStatement(sql_zaehlerstand, Statement.RETURN_GENERATED_KEYS); 
			
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
			
		} catch (SQLException e) {}
		
		//GET letzte Einstellung ID
		int letzte_einstellung_id = 0;
		try {
			String sql_letzte_einstellung = "SELECT MAX(`id`) FROM `einstellung`";
			ResultSet rs_letzte_einstellung_id = db.executeQueryWithResult(sql_letzte_einstellung);
			if(rs_letzte_einstellung_id.next()) {
				letzte_einstellung_id = rs_letzte_einstellung_id.getInt(1);
			}
		} catch (SQLException e1) {}
		
		//SPEICHERN Rechnung  --------------------------------------------------------------------------------------------
	    String sql_zahlung = "INSERT INTO `rechnung`(`zeitraum_id`, `zaehlerstand_id`, `einstellung_id`, `menge_strom`, `menge_erdgas`, `menge_wasser`, `menge_abwasser`, `betrag_menge_strom`, `betrag_menge_erdgas`, `betrag_menge_wasser`, `betrag_menge_abwasser`, `pauschale_strom`, `pauschale_erdgas`, `pauschale_wasser`, `pauschale_abwasser`, `steuer_strom`, `steuer_erdgas`, `betrag_netto_strom`, `betrag_netto_erdgas`, `betrag_netto_wasser`, `betrag_netto_abwasser`, `umsatz_steuer_strom`, `umsatz_steuer_erdgas`, `umsatz_steuer_wasser`, `umsatz_steuer_abwasser`, `betrag_brutto_strom`, `betrag_brutto_erdgas`, `betrag_brutto_wasser`, `betrag_brutto_abwasser`)  "
	    		+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";	//29
		
	    try {
			PreparedStatement ps_zahlung= con.prepareStatement(sql_zahlung, Statement.RETURN_GENERATED_KEYS); 
			
			//FKs
			ps_zahlung.setInt(1, gen_key_zeitraum);
			ps_zahlung.setInt(2, gen_key_zaehlerstand);
			ps_zahlung.setInt(3, letzte_einstellung_id);
			
			//Mengen
			double menge_strom = Double.parseDouble(lb_menge_strom.getText());
			double menge_erdgas = Double.parseDouble(lb_menge_erdgas.getText());
			double menge_wasser = Double.parseDouble(lb_menge_wasser.getText());
			double menge_abwasser = Double.parseDouble(lb_menge_abwasser.getText());
			
			ps_zahlung.setDouble(4, menge_strom);
			ps_zahlung.setDouble(5, menge_erdgas);
			ps_zahlung.setDouble(6, menge_wasser);
			ps_zahlung.setDouble(7, menge_abwasser);
			
			//Betrag_Mengen
			double betrag_menge_strom = menge_strom * PREIS_STROM;
			double betrag_menge_erdgas = menge_erdgas * PREIS_ERDGAS;
			double betrag_menge_wasser = menge_wasser * PREIS_WASSER;
			double betrag_menge_abwasser = menge_abwasser * PREIS_ABWASSER;
			
			ps_zahlung.setDouble(8, betrag_menge_strom);
			ps_zahlung.setDouble(9, betrag_menge_erdgas);
			ps_zahlung.setDouble(10, betrag_menge_wasser);
			ps_zahlung.setDouble(11, betrag_menge_abwasser);
			
			//Pauschalen alle /360 * anz_tage_zeitraum
			int anz_tage = Integer.parseInt(lb_dif_tage.getText());
			
			double pauschale_strom = anz_tage * PAUSCHALE_STROM / 360; 
			double pauschale_erdgas = anz_tage * PAUSCHALE_ERDGAS / 360;
			double pauschale_wasser = anz_tage * PAUSCHALE_WASSER / 360;
			double pauschale_abwasser = anz_tage * PAUSCHALE_ABWASSER / 360;
			
			ps_zahlung.setDouble(12, pauschale_strom);
			ps_zahlung.setDouble(13, pauschale_erdgas);
			ps_zahlung.setDouble(14, pauschale_wasser);
			ps_zahlung.setDouble(15, pauschale_abwasser);
			
			//Steuer - nur für Strom und Erdgas
			double steuer_strom_betrag = menge_strom * STEUER_STROM;
			double steuer_erdgas_betrag = menge_erdgas * STEUER_ERDGAS;
			
			ps_zahlung.setDouble(16, steuer_strom_betrag);
			ps_zahlung.setDouble(17, steuer_erdgas_betrag * STEUER_ERDGAS);
			
			//Betrag netto
			double betrag_netto_strom = betrag_menge_strom + pauschale_strom + steuer_strom_betrag;
			double betrag_netto_erdgas = betrag_menge_erdgas + pauschale_erdgas + steuer_erdgas_betrag;
			double betrag_netto_wasser = betrag_menge_wasser + pauschale_wasser;
			double betrag_netto_abwasser = betrag_menge_abwasser + pauschale_abwasser;
			
			ps_zahlung.setDouble(18, betrag_netto_strom);
			ps_zahlung.setDouble(19, betrag_netto_erdgas);
			ps_zahlung.setDouble(20, betrag_netto_wasser);
			ps_zahlung.setDouble(21, betrag_netto_abwasser);
			
			
			//Umsatzseteuer
			double umsatzsteuer_strom = betrag_netto_strom * UMSATZSTEUER_STROM;
			double umsatzsteuer_erdgas = betrag_netto_erdgas * UMSATZSTEUER_ERDGAS;
			double umsatzsteuer_wasser = betrag_netto_wasser * UMSATZSTEUER_WASSER;
			double umsatzsteuer_abwasser = betrag_netto_abwasser * UMSATZSTEUER_ABWASSER;
			
			ps_zahlung.setDouble(22, umsatzsteuer_strom);
			ps_zahlung.setDouble(23, umsatzsteuer_erdgas);
			ps_zahlung.setDouble(24, umsatzsteuer_wasser);
			ps_zahlung.setDouble(25, umsatzsteuer_abwasser);
			
			//Betrag brutto
			double betrag_brutto_strom = betrag_netto_strom + umsatzsteuer_strom;
			double betrag_brutto_erdgas = betrag_netto_erdgas + umsatzsteuer_erdgas;
			double betrag_brutto_wasser = betrag_netto_wasser + umsatzsteuer_wasser;
			double betrag_brutto_abwasser = betrag_netto_abwasser + umsatzsteuer_abwasser;
			
			ps_zahlung.setDouble(26, betrag_brutto_strom);
			ps_zahlung.setDouble(27, betrag_brutto_erdgas);
			ps_zahlung.setDouble(28, betrag_brutto_wasser);
			ps_zahlung.setDouble(29, betrag_brutto_abwasser);
			
		    
		    
		    int gen_key = db.executeUpdate(ps_zahlung);
		    System.out.println(gen_key);
		    
		    
		    
		} catch (SQLException e) {}
				
		
	}

}

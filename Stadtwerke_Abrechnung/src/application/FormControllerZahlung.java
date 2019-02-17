package application;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.temporal.ChronoUnit;

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
	
	public void initialize() {
		
		
		lb_menge_strom.setId("label-bold");
		
		cb_zaehler_strom.getItems().setAll("123", "456", "789");
		cb_zaehler_erdgas.getItems().setAll("123", "456", "789");
		cb_zaehler_wasser.getItems().setAll("123", "456", "789");
		cb_zaehler_abwasser.getItems().setAll("123", "456", "789");
		
		lb_faktor_strom.setText("1");
		lb_faktor_wasser.setText("1");
		lb_faktor_abwasser.setText("1");
	}
	
	public void action_datum_berechnen () {
		if(dp_zeitraum_bis.getValue()!= null && dp_zeitraum_von.getValue() != null) {
			long days_between = ChronoUnit.DAYS.between(dp_zeitraum_von.getValue(), dp_zeitraum_bis.getValue());
			lb_dif_tage.setText(""+(days_between+1)); //+1, da inklusive bis-Datum 
		}
	}
	
	public void action_strom_berechnen () {
		
		if(!tf_strom_alt.getText().isEmpty() && !tf_strom_neu.getText().isEmpty()) {
			int strom_neu = Integer.parseInt(tf_strom_neu.getText());
			int strom_alt = Integer.parseInt(tf_strom_alt.getText());
			int strom_dif= strom_neu-strom_alt;
		
			lb_dif_strom.setText(""+strom_dif);
			lb_menge_strom.setText(""+ strom_dif * Integer.parseInt(lb_faktor_strom.getText()));
		}
	}
	
	public void action_ergas_berechnen () {
		if(!tf_erdgas_alt.getText().isEmpty() && !tf_erdgas_neu.getText().isEmpty()) {
			int erdgas_neu = Integer.parseInt(tf_erdgas_neu.getText());
			int erdgas_alt = Integer.parseInt(tf_erdgas_alt.getText());
			int erdgas_dif = erdgas_neu - erdgas_alt;
			
			lb_dif_erdgas.setText(""+(erdgas_dif));
			lb_menge_erdgas.setText(""+ (int) Math.round(erdgas_dif * ERDGAS_FKTR_ZSTNDZHL * ERDGAS_FKTR_BRNWRT));
		}
	}
	
	public void action_wasser_berechnen () {
		if(!tf_wasser_alt.getText().isEmpty() && !tf_wasser_neu.getText().isEmpty()) {
			int wasser_neu = Integer.parseInt(tf_wasser_neu.getText());
			int wasser_alt = Integer.parseInt(tf_wasser_alt.getText());
			int wasser_dif = wasser_neu - wasser_alt;
						
			lb_dif_wasser.setText(""+(wasser_neu - wasser_alt));
			lb_menge_wasser.setText(""+ wasser_dif * Integer.parseInt(lb_faktor_wasser.getText()));
		}
	}
	
	public void action_abwasser_berechnen () {
		if(!tf_abwasser_alt.getText().isEmpty() && !tf_abwasser_neu.getText().isEmpty()) {
			int abwasser_neu = Integer.parseInt(tf_abwasser_neu.getText());
			int abwasser_alt = Integer.parseInt(tf_abwasser_alt.getText());
			int abwasser_dif = abwasser_neu - abwasser_alt;
			
			lb_dif_abwasser.setText(""+(abwasser_dif));
			lb_menge_abwasser.setText(""+ abwasser_dif * Integer.parseInt(lb_faktor_abwasser.getText()));
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
		
		
		//SPEICHERN ZEITRAUM
		String zeitraum_von_tag = DateConversion.fillUpValue(dp_zeitraum_von.getValue().getDayOfMonth());	
		String zeitraum_von_monat = DateConversion.fillUpValue(dp_zeitraum_von.getValue().getMonthValue());	
		String zeitraum_von_jahr = ""+ dp_zeitraum_von.getValue().getYear();
		
		String zeitraum_bis_tag = DateConversion.fillUpValue(dp_zeitraum_bis.getValue().getDayOfMonth());	
		String zeitraum_bis_monat = DateConversion.fillUpValue(dp_zeitraum_bis.getValue().getMonthValue());	
		String zeitraum_bis_jahr = ""+ dp_zeitraum_bis.getValue().getYear();
						
		java.sql.Date sql_date_zeitraum_von = DateConversion.dateConversion(zeitraum_von_jahr+"-"+zeitraum_von_monat+"-"+zeitraum_von_tag);
		java.sql.Date sql_date_zeitraum_bis = DateConversion.dateConversion(""+zeitraum_bis_jahr+"-"+zeitraum_bis_monat+"-"+zeitraum_bis_tag);
			
				
		String sql_zeitraum = "INSERT INTO `zeitraum`(`zeitraum_von`, `zeitraum_bis`, `zeitraum_von_jahr`, `zeitraum_von_monat`, `zeitraum_von_tag`, `zeitraum_bis_jahr`, `zeitraum_bis_monat`, `zeitraum_bis_tag`) VALUES(?,?,?,?,?,?,?,?)";
		
		int gen_key_zeitraum = 0;
		try {
			PreparedStatement ps= con.prepareStatement(sql_zeitraum, Statement.RETURN_GENERATED_KEYS); 
			
			ps.setDate(1, sql_date_zeitraum_von);
		    ps.setDate(2, sql_date_zeitraum_bis);
		    ps.setInt(3, dp_zeitraum_von.getValue().getYear());
		    ps.setInt(4, dp_zeitraum_von.getValue().getMonthValue());
		    ps.setInt(5, dp_zeitraum_von.getValue().getDayOfMonth());
		    ps.setInt(6, dp_zeitraum_bis.getValue().getYear());
		    ps.setInt(7, dp_zeitraum_bis.getValue().getMonthValue());
		    ps.setInt(8, dp_zeitraum_bis.getValue().getDayOfMonth());
		    
		    
		    gen_key_zeitraum = db.executeUpdate(ps);
		    System.out.println(gen_key_zeitraum);
		    
		    //db.printResultSet(rs);
		    
		} catch (SQLException e) {}
		
		
		
		//SPEICHERN ZAHLUNG 
	    String sql_zahlung = "INSERT INTO `zahlung`(`menge_strom`, `menge_erdgas`, `menge_wasser`, `menge_abwasser`, `zeitraum_id`) VALUES(?,?,?,?,?)";
		
	    try {
			PreparedStatement ps_zahlung= con.prepareStatement(sql_zahlung, Statement.RETURN_GENERATED_KEYS); 
			
			ps_zahlung.setFloat(1, Float.parseFloat(lb_menge_strom.getText()));
			ps_zahlung.setFloat(2, Float.parseFloat(lb_menge_erdgas.getText()));
			ps_zahlung.setFloat(3, Float.parseFloat(lb_menge_wasser.getText()));
			ps_zahlung.setFloat(4, Float.parseFloat(lb_menge_abwasser.getText()));
			ps_zahlung.setInt(5, gen_key_zeitraum);
			
		    
		    
		    int gen_key = db.executeUpdate(ps_zahlung);
		    System.out.println(gen_key);
		    
		    //db.printResultSet(rs);
		    
		} catch (SQLException e) {}
				
		
	}
	
}

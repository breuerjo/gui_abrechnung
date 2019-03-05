package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class FormControllerAuswertung {

	final static String austria = "Austria";
	final static String brazil = "Brazil";
	final static String france = "France";
	final static String italy = "Italy";
	final static String usa = "USA";

	@FXML
	private CategoryAxis xAxis_bc_ges;
	@FXML
	private NumberAxis yAxis_bc_ges;
	@FXML
	private BarChart<String, Double> bc_allgmeein_kosten;
	
	@FXML
	private CategoryAxis xAxis_bc_strom;
	@FXML
	private NumberAxis yAxis_bc_strom;
	@FXML
	private BarChart<String, Double> bc_strom_kosten;
	
	@FXML
	private CategoryAxis xAxis_bc_erdgas;
	@FXML
	private NumberAxis yAxis_bc_erdgas;
	@FXML
	private BarChart<String, Double> bc_erdgas_kosten;
	
	@FXML
	private CategoryAxis xAxis_bc_wasser;
	@FXML
	private NumberAxis yAxis_bc_wasser;
	@FXML
	private BarChart<String, Double> bc_wasser_kosten;
	
	@FXML
	private CategoryAxis xAxis_bc_abwasser;
	@FXML
	private NumberAxis yAxis_bc_abwasser;
	@FXML
	private BarChart<String, Double> bc_abwasse_kosten;
	
	//-----------------------------------------------------------------------
	
	@FXML
	private CategoryAxis xAxis_bc_menge__strom;
	@FXML
	private NumberAxis yAxis_bc_menge__strom;
	@FXML
	private BarChart<String, Double> bc_strom_menge;
	
	@FXML
	private CategoryAxis xAxis_bc_menge__erdgas;
	@FXML
	private NumberAxis yAxis_bc_menge__erdgas;
	@FXML
	private BarChart<String, Double> bc_erdgas_menge;
	
	@FXML
	private CategoryAxis xAxis_bc_menge__wasser;
	@FXML
	private NumberAxis yAxis_bc_menge__wasser;
	@FXML
	private BarChart<String, Double> bc_wasser_menge;
	
	@FXML
	private CategoryAxis xAxis_bc_menge__abwasser;
	@FXML
	private NumberAxis yAxis_bc_menge__abwasser;
	@FXML
	private BarChart<String, Double> bc_abwasser_menge;

	public void initialize() {
		DB db = new DB();
		initBarChart(db);
	}

	public void initBarChart(DB db) {


		bc_allgmeein_kosten.setTitle("Gesamte Kosten");
		bc_strom_kosten.setTitle("Kosten Strom:");
		bc_erdgas_kosten.setTitle("Kosten Erdgas:");
		bc_wasser_kosten.setTitle("Kosten Wasser:");
		bc_abwasse_kosten.setTitle("Kosten Abwasser:");
		
		bc_strom_menge.setTitle("Menge Strom:");
		bc_erdgas_menge.setTitle("Menge Erdgas:");
		bc_wasser_menge.setTitle("Menge Wasser:");
		bc_abwasser_menge.setTitle("Menge Abwasser:");

		String sql_jahre = "SELECT DISTINCT `zeitraum_von_jahr` FROM `zeitraum`";

		ResultSet rs_jahre = db.executeQueryWithResult(sql_jahre);

		try {
			while (rs_jahre.next()) {
				double gesamtbetrag = 0;
			
				int jahr = rs_jahre.getInt("zeitraum_von_jahr");
				System.out.println("" + jahr);

				String sql_kosten_pro_jahr = "SELECT `zeitraum_id`, SUM(`betrag_brutto_strom`), SUM(`betrag_brutto_wasser`), SUM(`betrag_brutto_erdgas`), SUM(`betrag_brutto_abwasser`), SUM(`menge_strom`), SUM(`menge_erdgas`), SUM(`menge_wasser`), SUM(`menge_abwasser`), `zeitraum_von_jahr`  FROM `rechnung`\r\n" + 
						"Inner join `zeitraum` on `zeitraum`.`id` = `rechnung`.`zeitraum_id` WHERE `zeitraum_von_jahr` = "
						+ jahr + "";
				ResultSet rs_kosten_pro_jahr = db.executeQueryWithResult(sql_kosten_pro_jahr);
				
				if(rs_kosten_pro_jahr.next()) {
					gesamtbetrag += rs_kosten_pro_jahr.getDouble(2) + rs_kosten_pro_jahr.getDouble(3) + rs_kosten_pro_jahr.getDouble(4) + rs_kosten_pro_jahr.getDouble(5);
					
					//-----------------------------Kosten-Charts:-----------------------
					Series<String, Double> set_gesamt = new XYChart.Series<String, Double>();
					Series<String, Double> set_strom = new XYChart.Series<String, Double>();
					Series<String, Double> set_erdgas = new XYChart.Series<String, Double>();
					Series<String, Double> set_wasser = new XYChart.Series<String, Double>();
					Series<String, Double> set_abwasser = new XYChart.Series<String, Double>();
					set_gesamt.setName("" + jahr);
					set_strom.setName("" + jahr);
					set_erdgas.setName("" + jahr);
					set_wasser.setName("" + jahr);
					set_abwasser.setName("" + jahr);
					
					set_gesamt.getData().add(new XYChart.Data<String, Double>("Gesamtbetrag" ,gesamtbetrag));
					set_strom.getData().add(new XYChart.Data<String, Double>("Betrag Strom" ,rs_kosten_pro_jahr.getDouble(2)));
					set_erdgas.getData().add(new XYChart.Data<String, Double>("Betrag Erdgas" ,rs_kosten_pro_jahr.getDouble(3)));
					set_wasser.getData().add(new XYChart.Data<String, Double>("Betrag Wasser" ,rs_kosten_pro_jahr.getDouble(4)));
					set_abwasser.getData().add(new XYChart.Data<String, Double>("Betrag Abwasser" ,rs_kosten_pro_jahr.getDouble(5)));

					bc_allgmeein_kosten.getData().addAll(set_gesamt);
					bc_strom_kosten.getData().addAll(set_strom);
					bc_erdgas_kosten.getData().addAll(set_erdgas);
					bc_wasser_kosten.getData().addAll(set_wasser);
					bc_abwasse_kosten.getData().addAll(set_abwasser);
					
					//-----------------------------Mengen-Charts:-----------------------
					Series<String, Double> set_strom_menge = new XYChart.Series<String, Double>();
					Series<String, Double> set_erdgas_menge = new XYChart.Series<String, Double>();
					Series<String, Double> set_wasser_menge = new XYChart.Series<String, Double>();
					Series<String, Double> set_abwasser_menge = new XYChart.Series<String, Double>();
					set_strom_menge.setName("" + jahr);
					set_erdgas_menge.setName("" + jahr);
					set_wasser_menge.setName("" + jahr);
					set_abwasser_menge.setName("" + jahr);
					
					set_strom_menge.getData().add(new XYChart.Data<String, Double>("Menge Strom (kWh)" ,rs_kosten_pro_jahr.getDouble(6)));
					set_erdgas_menge.getData().add(new XYChart.Data<String, Double>("Menge Erdgas (kWh)" ,rs_kosten_pro_jahr.getDouble(7)));
					set_wasser_menge.getData().add(new XYChart.Data<String, Double>("Menge Wasser (m3)" ,rs_kosten_pro_jahr.getDouble(8)));
					set_abwasser_menge.getData().add(new XYChart.Data<String, Double>("Menge Abwasser (m3)" ,rs_kosten_pro_jahr.getDouble(9)));

					bc_strom_menge.getData().addAll(set_strom_menge);
					bc_erdgas_menge.getData().addAll(set_erdgas_menge);
					bc_wasser_menge.getData().addAll(set_wasser_menge);
					bc_abwasser_menge.getData().addAll(set_abwasser_menge);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Series<String, Double> set1 = new XYChart.Series<String, Double>();
		 * set1.setName("Timiboy"); set1.getData().add(new XYChart.Data<String,
		 * Double>("100", 200.0));
		 * 
		 * bc.getData().addAll(set1);
		 */
		System.out.println("Init BarChart done");

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

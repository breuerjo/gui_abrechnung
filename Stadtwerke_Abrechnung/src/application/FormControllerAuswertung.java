package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;

public class FormControllerAuswertung {

	// -------------------------------------------------------------------------Kosten------------------------------
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

	// -----------------------------------------------------------------------MENGE------------------------------------

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

	// ------------------------------------------------------Preisentwicklungen
	// LinceChart-------------------------
	@FXML
	private CategoryAxis xAxis_lc_preise_strom;
	@FXML
	private NumberAxis yAxis_lc_preise_strom;
	@FXML
	private LineChart<String, Double> lc_preise_strom;
	
	@FXML
	private CategoryAxis xAxis_lc_preise_erdgas;
	@FXML
	private NumberAxis yAxis_lc_preise_erdgas;
	@FXML
	private LineChart<String, Double> lc_preise_erdgas;
	
	@FXML
	private CategoryAxis xAxis_lc_preise_wasser;
	@FXML
	private NumberAxis yAxis_lc_preise_wasser;
	@FXML
	private LineChart<String, Double> lc_preise_wasser;
	
	@FXML
	private CategoryAxis xAxis_lc_preise_abwasser;
	@FXML
	private NumberAxis yAxis_lc_preise_abwasser;
	@FXML
	private LineChart<String, Double> lc_preise_abwasser;

	// ------------------------------------------------------Pauschalen-Entwicklungen
	// LinceChart-------------------------
	@FXML
	private CategoryAxis xAxis_lc_pauschale_strom;
	@FXML
	private NumberAxis yAxis_lc_pauschale_strom;
	@FXML
	private LineChart<String, Double> lc_pauschale_strom;
	
	@FXML
	private CategoryAxis xAxis_lc_pauschale_erdgas;
	@FXML
	private NumberAxis yAxis_lc_pauschale_erdgas;
	@FXML
	private LineChart<String, Double> lc_pauschale_erdgas;
	
	@FXML
	private CategoryAxis xAxis_lc_pauschale_wasser;
	@FXML
	private NumberAxis yAxis_lc_pauschale_wasser;
	@FXML
	private LineChart<String, Double> lc_pauschale_wasser;
	
	@FXML
	private CategoryAxis xAxis_lc_pauschale_abwasser;
	@FXML
	private NumberAxis yAxis_lc_pauschale_abwasser;
	@FXML
	private LineChart<String, Double> lc_pauschale_abwasser;
	
	//----------------------------------------------------------------Preiszusammensetzung
	@FXML
	private CategoryAxis xAxis_lc_preiszusammensetzung_strom;
	@FXML
	private NumberAxis yAxis_lc_preiszusammensetzung_strom;
	@FXML
	private LineChart<String, Double> lc_preiszusammensetzung_strom;
	
	@FXML
	private CategoryAxis xAxis_lc_preiszusammensetzung_erdgas;
	@FXML
	private NumberAxis yAxis_lc_preiszusammensetzung_erdgas;
	@FXML
	private LineChart<String, Double> lc_preiszusammensetzung_erdgas;
	
	@FXML
	private CategoryAxis xAxis_lc_preiszusammensetzung_wasser;
	@FXML
	private NumberAxis yAxis_lc_preiszusammensetzung_wasser;
	@FXML
	private LineChart<String, Double> lc_preiszusammensetzung_wasser;
	
	@FXML
	private CategoryAxis xAxis_lc_preiszusammensetzung_abwasser;
	@FXML
	private NumberAxis yAxis_lc_preiszusammensetzung_abwasser;
	@FXML
	private LineChart<String, Double> lc_preiszusammensetzung_abwasser;
	

	// ------------------------------------------------------PIE-Chart-Kostenzusammensetzung
	@FXML
	private PieChart pc_kostenzusammensetzung;

	// ------------------------------------------------------Line Chart
	// Kostenverlauf--------------------
	@FXML
	private CategoryAxis xAxis_lc_kosten_allgemein;
	@FXML
	private NumberAxis yAxis_lc_kosten_allgemein;
	@FXML
	private LineChart<String, Double> lc_kosten_allgemein;

	// -------------------------------------------------------------Allgemein--------------------------
	@FXML
	private ComboBox<String> cb_auswahl_jahr;
	@FXML
	private ComboBox<String> cb_auswahl_monat_jahr;

	public void initialize() {
		DB db = new DB();
		initCB(db);
		boolean initDone = false;
		initBarChartAndLineChartKostenAllgemein(db, false, initDone);	//Standardmäßig Jahresansicht
		initPieChartKostenzusammensetzung();
		initLineChartPreise(db);
		initBarChartKostenAllgemeinProJahr(db, true);	//Standardmäßig Jahresansicht
		
		
	}

	public void initCB(DB db) {
		
		//Init CB Jahres-/Monatsanzeige
		ObservableList<String> list_monat_jahr = FXCollections.observableArrayList();
		list_monat_jahr.addAll("Zeitpunktansicht", "Jahresansicht");
		cb_auswahl_monat_jahr.setItems(list_monat_jahr);
		cb_auswahl_monat_jahr.setValue("Jahresansicht");
				
				
		ResultSet rs_jahre = db.executeQueryWithResult("SELECT DISTINCT `zeitraum_bis_jahr` FROM `zeitraum`");
		try {
			ObservableList<String> list_jahre = FXCollections.observableArrayList();

			while (rs_jahre.next()) {
				list_jahre.add(rs_jahre.getString("zeitraum_bis_jahr"));
			}

			cb_auswahl_jahr.setItems(list_jahre);

		} catch (SQLException e) {
		}

		// CB immer neuestes Jahr als Standard-Wert setzen
		ResultSet rs_max_jahr = db.executeQueryWithResult("SELECT MAX(`zeitraum_bis_jahr`) FROM `zeitraum`");
		try {
			if (rs_max_jahr.next()) {
				cb_auswahl_jahr.setValue(rs_max_jahr.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void actionCBJahrGewaehlt() {
		initPieChartKostenzusammensetzung();
	}

	public void initBarChartAndLineChartKostenAllgemein(DB db, boolean monats_ansicht, boolean initDone) {
		
		bc_allgmeein_kosten.getData().clear(); //Chart zurücksetzen
		if(!monats_ansicht && initDone == false) {
			bc_strom_kosten.getData().clear();
			bc_erdgas_kosten.getData().clear();
			bc_wasser_kosten.getData().clear();
			bc_abwasse_kosten.getData().clear();
			bc_strom_menge.getData().clear();
			bc_erdgas_menge.getData().clear();
			bc_wasser_menge.getData().clear();
			bc_abwasser_menge.getData().clear();
			lc_kosten_allgemein.getData().clear();
		}

		bc_allgmeein_kosten.setTitle("Gesamte Kosten:");
		bc_strom_kosten.setTitle("Kosten Strom:");
		bc_erdgas_kosten.setTitle("Kosten Erdgas:");
		bc_wasser_kosten.setTitle("Kosten Wasser:");
		bc_abwasse_kosten.setTitle("Kosten Abwasser:");

		bc_strom_menge.setTitle("Menge Strom:");
		bc_erdgas_menge.setTitle("Menge Erdgas:");
		bc_wasser_menge.setTitle("Menge Wasser:");
		bc_abwasser_menge.setTitle("Menge Abwasser:");
		
		lc_kosten_allgemein.setTitle("Allgemeine Kostenübersicht:");

		String sql_jahre = "SELECT `zeitraum_bis` FROM `zeitraum` ORDER BY `zeitraum_bis`";

		ResultSet rs_rechnungs_zeitpunkte = db.executeQueryWithResult(sql_jahre);

		// LineChart - KostenChart
		Series<String, Double> set_kosten_gesamt = new XYChart.Series<String, Double>();
		Series<String, Double> set_kosten_nachzahlung = new XYChart.Series<String, Double>();
		Series<String, Double> set_kosten_abschlaege = new XYChart.Series<String, Double>();

		set_kosten_gesamt.setName("Gesamtkosten");
		set_kosten_nachzahlung.setName("Nachzahlung");
		set_kosten_abschlaege.setName("Abschlagszahlung");

		try {
			while (rs_rechnungs_zeitpunkte.next()) {
				
				
				
				
				double gesamtbetrag = 0;

				String rechungs_datum = rs_rechnungs_zeitpunkte.getString("zeitraum_bis");
				//System.out.println("" + rechungs_datum);

				String sql_kosten_pro_jahr = "SELECT `zeitraum_id`, SUM(`betrag_brutto_strom`), SUM(`betrag_brutto_wasser`), SUM(`betrag_brutto_erdgas`), SUM(`betrag_brutto_abwasser`), SUM(`menge_strom`), SUM(`menge_erdgas`), SUM(`menge_wasser`), SUM(`menge_abwasser`), SUM(`betrag_nachzahlung`), MAX(`betrag_gezahlte_abschlaege`), `zeitraum_von_jahr`, `zeitraum_bis_monat`  FROM `rechnung`\r\n"
						+ "Inner join `zeitraum` on `zeitraum`.`id` = `rechnung`.`zeitraum_id` WHERE `zeitraum_bis` LIKE '"+ rechungs_datum + "'";
				ResultSet rs_kosten_pro_rechnungs_datum = db.executeQueryWithResult(sql_kosten_pro_jahr);

				if (rs_kosten_pro_rechnungs_datum.next()) {
					gesamtbetrag += rs_kosten_pro_rechnungs_datum.getDouble(2) + rs_kosten_pro_rechnungs_datum.getDouble(3)
							+ rs_kosten_pro_rechnungs_datum.getDouble(4) + rs_kosten_pro_rechnungs_datum.getDouble(5);

					// -----------------------------Kosten-Charts:-----------------------
					Series<String, Double> set_gesamt = new XYChart.Series<String, Double>();
					Series<String, Double> set_strom = new XYChart.Series<String, Double>();
					Series<String, Double> set_erdgas = new XYChart.Series<String, Double>();
					Series<String, Double> set_wasser = new XYChart.Series<String, Double>();
					Series<String, Double> set_abwasser = new XYChart.Series<String, Double>();
					set_gesamt.setName("" + DateConversion.dateFormating(rechungs_datum));
					set_strom.setName("" + DateConversion.dateFormating(rechungs_datum));
					set_erdgas.setName("" + DateConversion.dateFormating(rechungs_datum));
					set_wasser.setName("" + DateConversion.dateFormating(rechungs_datum));
					set_abwasser.setName("" + DateConversion.dateFormating(rechungs_datum));

					set_gesamt.getData().add(new XYChart.Data<String, Double>("Gesamtbetrag", gesamtbetrag));
					set_strom.getData()
							.add(new XYChart.Data<String, Double>("Betrag Strom", rs_kosten_pro_rechnungs_datum.getDouble(2)));
					set_erdgas.getData()
							.add(new XYChart.Data<String, Double>("Betrag Erdgas", rs_kosten_pro_rechnungs_datum.getDouble(3)));
					set_wasser.getData()
							.add(new XYChart.Data<String, Double>("Betrag Wasser", rs_kosten_pro_rechnungs_datum.getDouble(4)));
					set_abwasser.getData()
							.add(new XYChart.Data<String, Double>("Betrag Abwasser", rs_kosten_pro_rechnungs_datum.getDouble(5)));
					
					if(monats_ansicht) {//Wenn Ansicht Monat ist ausgewählt
						bc_allgmeein_kosten.getData().addAll(set_gesamt);
					}
					
					if(!monats_ansicht && initDone == false) {	
						bc_strom_kosten.getData().addAll(set_strom);
						bc_erdgas_kosten.getData().addAll(set_erdgas);
						bc_wasser_kosten.getData().addAll(set_wasser);
						bc_abwasse_kosten.getData().addAll(set_abwasser);
					}

					// -----------------------------KostenChart-LineChart--------------------------------------

					set_kosten_gesamt.getData().add(new XYChart.Data<String, Double>("" + DateConversion.dateFormating(rechungs_datum), gesamtbetrag));
					set_kosten_nachzahlung.getData()
							.add(new XYChart.Data<String, Double>("" + DateConversion.dateFormating(rechungs_datum), rs_kosten_pro_rechnungs_datum.getDouble(10)));
					
					if(rs_kosten_pro_rechnungs_datum.getInt("zeitraum_bis_monat") == 5) {
						set_kosten_abschlaege.getData()
						.add(new XYChart.Data<String, Double>("" + DateConversion.dateFormating(rechungs_datum), (rs_kosten_pro_rechnungs_datum.getDouble(11) * 5)));
					}
					else {
						set_kosten_abschlaege.getData()
						.add(new XYChart.Data<String, Double>("" + DateConversion.dateFormating(rechungs_datum), (rs_kosten_pro_rechnungs_datum.getDouble(11) * 7)));
					}
					
					if(!monats_ansicht && initDone == false) {
						lc_kosten_allgemein.getData().clear();
						lc_kosten_allgemein.getData().addAll(set_kosten_gesamt, set_kosten_nachzahlung, set_kosten_abschlaege);
					}
					

					// -----------------------------Mengen-Charts:-----------------------
					Series<String, Double> set_strom_menge = new XYChart.Series<String, Double>();
					Series<String, Double> set_erdgas_menge = new XYChart.Series<String, Double>();
					Series<String, Double> set_wasser_menge = new XYChart.Series<String, Double>();
					Series<String, Double> set_abwasser_menge = new XYChart.Series<String, Double>();
					set_strom_menge.setName("" + DateConversion.dateFormating(rechungs_datum));
					set_erdgas_menge.setName("" + DateConversion.dateFormating(rechungs_datum));
					set_wasser_menge.setName("" + DateConversion.dateFormating(rechungs_datum));
					set_abwasser_menge.setName("" + DateConversion.dateFormating(rechungs_datum));

					set_strom_menge.getData().add(
							new XYChart.Data<String, Double>("Menge Strom (kWh)", rs_kosten_pro_rechnungs_datum.getDouble(6)));
					set_erdgas_menge.getData().add(
							new XYChart.Data<String, Double>("Menge Erdgas (kWh)", rs_kosten_pro_rechnungs_datum.getDouble(7)));
					set_wasser_menge.getData().add(
							new XYChart.Data<String, Double>("Menge Wasser (m³)", rs_kosten_pro_rechnungs_datum.getDouble(8)));
					set_abwasser_menge.getData().add(
							new XYChart.Data<String, Double>("Menge Abwasser (m³)", rs_kosten_pro_rechnungs_datum.getDouble(9)));
					
					if(!monats_ansicht && initDone == false) {
						bc_strom_menge.getData().addAll(set_strom_menge);
						bc_erdgas_menge.getData().addAll(set_erdgas_menge);
						bc_wasser_menge.getData().addAll(set_wasser_menge);
						bc_abwasser_menge.getData().addAll(set_abwasser_menge);
					}
					
				}

			}//while
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		initDone = true;

	}
	
	public void initBarChartKostenAllgemeinProJahr(DB db, boolean jahres_ansicht) {
		if(jahres_ansicht) {
			bc_allgmeein_kosten.getData().clear(); //Chart zurücksetzen
			ResultSet rs_jahre = db.executeQueryWithResult("SELECT DISTINCT `zeitraum_bis_jahr` FROM `zeitraum` ORDER BY `zeitraum_bis_jahr`");
	
			// LineChart - KostenChart pro Jahr
			Series<String, Double> set_kosten_gesamt = new XYChart.Series<String, Double>();
	
	
			set_kosten_gesamt.setName("Gesamtkosten");
	
	
			try {
				while (rs_jahre.next()) {
					double gesamtbetrag = 0;
	
					String rechungs_jahr = rs_jahre.getString("zeitraum_bis_jahr");
					//System.out.println("" + rechungs_jahr);
	
					String sql_kosten_pro_jahr = "SELECT `zeitraum_id`, SUM(`betrag_brutto_strom`), SUM(`betrag_brutto_wasser`), SUM(`betrag_brutto_erdgas`), SUM(`betrag_brutto_abwasser`) `zeitraum_von_jahr`  FROM `rechnung`\r\n"
							+ "Inner join `zeitraum` on `zeitraum`.`id` = `rechnung`.`zeitraum_id` WHERE `zeitraum_bis_jahr` = "+ rechungs_jahr + "";
					
					ResultSet rs_kosten_pro_jahr = db.executeQueryWithResult(sql_kosten_pro_jahr);
	
					if (rs_kosten_pro_jahr.next()) {
						gesamtbetrag += rs_kosten_pro_jahr.getDouble(2) + rs_kosten_pro_jahr.getDouble(3)
								+ rs_kosten_pro_jahr.getDouble(4) + rs_kosten_pro_jahr.getDouble(5);
	
						// -----------------------------Kosten-Charts:-----------------------
						Series<String, Double> set_gesamt = new XYChart.Series<String, Double>();
						
						set_gesamt.setName("" + rechungs_jahr);
						
	
						set_gesamt.getData().add(new XYChart.Data<String, Double>("Gesamtbetrag", gesamtbetrag));
						
												bc_allgmeein_kosten.getData().addAll(set_gesamt);
					}
				}
			}catch (Exception e) {}
		}//if
				
	}

	public void initPieChartKostenzusammensetzung() {
		DB db = new DB();

		// CheckBox abfragen und Jahr holen
		String jahr = cb_auswahl_jahr.getValue();

		pc_kostenzusammensetzung.setTitle("Kostenzusammensetzung: " + jahr);

		String sql_jahre = "SELECT `id` FROM `zeitraum` WHERE `zeitraum_bis_jahr` = " + jahr + "";

		ResultSet rs_jahre = db.executeQueryWithResult(sql_jahre);

		double kosten_strom_pro_Jahr = 0;
		double kosten_erdgas_pro_Jahr = 0;
		double kosten_wasser_pro_Jahr = 0;
		double kosten_abwasser_pro_Jahr = 0;
		double gesamtbetrag_pro_jahr = 0;

		double anteil_strom = 0;
		double anteil_erdgas = 0;
		double anteil_wasser = 0;
		double anteil_abwasser = 0;

		try {
			while (rs_jahre.next()) { // Alle Einträge in diesem Jahr durchgehen und
				String sql_rechnung = "SELECT `betrag_brutto_strom`, `betrag_brutto_erdgas`, `betrag_brutto_wasser`, `betrag_brutto_abwasser` FROM `rechnung` WHERE `zeitraum_id` = "
						+ rs_jahre.getInt("id") + "";
				ResultSet rs_rechnung = db.executeQueryWithResult(sql_rechnung);

				if (rs_rechnung.next()) {
					kosten_strom_pro_Jahr += rs_rechnung.getDouble("betrag_brutto_strom");
					kosten_erdgas_pro_Jahr += rs_rechnung.getDouble("betrag_brutto_erdgas");
					kosten_wasser_pro_Jahr += rs_rechnung.getDouble("betrag_brutto_wasser");
					kosten_abwasser_pro_Jahr += rs_rechnung.getDouble("betrag_brutto_abwasser");
				}

				gesamtbetrag_pro_jahr = kosten_strom_pro_Jahr + kosten_erdgas_pro_Jahr + kosten_wasser_pro_Jahr
						+ kosten_abwasser_pro_Jahr;

				// Anteile berechnen in Prozent
				anteil_strom = kosten_strom_pro_Jahr / gesamtbetrag_pro_jahr * 100;
				anteil_erdgas = kosten_erdgas_pro_Jahr / gesamtbetrag_pro_jahr * 100;
				anteil_wasser = kosten_wasser_pro_Jahr / gesamtbetrag_pro_jahr * 100;
				anteil_abwasser = kosten_abwasser_pro_Jahr / gesamtbetrag_pro_jahr * 100;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Strom", BasicFunctions.roundDoubleNachkommastellen(anteil_strom, 2)),
				new PieChart.Data("Erdgas", BasicFunctions.roundDoubleNachkommastellen(anteil_erdgas, 2)),
				new PieChart.Data("Wasser", BasicFunctions.roundDoubleNachkommastellen(anteil_wasser, 2)),
				new PieChart.Data("Abwasser", BasicFunctions.roundDoubleNachkommastellen(anteil_abwasser, 2)));

		// Werte an Diagramm anzeigen
		pieChartData.forEach(
				data -> data.nameProperty().bind(Bindings.concat(data.getName(), " ", data.pieValueProperty(), " %")));
		pc_kostenzusammensetzung.getData().clear();
		pc_kostenzusammensetzung.getData().addAll(pieChartData);

		//System.out.println("Init Pie Chart");
	}

	public void initLineChartPreise(DB db) {
		
		lc_preise_strom.setTitle("Strompreise im Vergleich:");
		lc_pauschale_strom.setTitle("Strompauschalen im Vergleich:");
		lc_preiszusammensetzung_strom.setTitle("Kostenzusammensetzung Strom:");
		
		lc_preise_erdgas.setTitle("Erdgaspreise im Vergleich:");
		lc_pauschale_erdgas.setTitle("Erdgaspauschalen im Vergleich:");
		lc_preiszusammensetzung_erdgas.setTitle("Kostenzusammensetzung Erdgas:");
		
		lc_preise_wasser.setTitle("Wasserpreise im Vergleich:");
		lc_pauschale_wasser.setTitle("Wasserpauschalen im Vergleich:");
		lc_preiszusammensetzung_wasser.setTitle("Kostenzusammensetzung Wasser:");
		
		lc_preise_abwasser.setTitle("Abwasserpreise im Vergleich:");
		lc_pauschale_abwasser.setTitle("Abwasserpauschalen im Vergleich:");
		lc_preiszusammensetzung_abwasser.setTitle("Kostenzusammensetzung Abwasser:");
		
		

		Series<String, Double> set_preise_strom = new XYChart.Series<String, Double>();
		Series<String, Double> set_preise_erdgas = new XYChart.Series<String, Double>();
		Series<String, Double> set_preise_wasser = new XYChart.Series<String, Double>();
		Series<String, Double> set_preise_abwasser = new XYChart.Series<String, Double>();

		Series<String, Double> set_pauschale_strom = new XYChart.Series<String, Double>();
		Series<String, Double> set_pauschale_erdgas = new XYChart.Series<String, Double>();
		Series<String, Double> set_pauschale_wasser = new XYChart.Series<String, Double>();
		Series<String, Double> set_pauschale_abwasser = new XYChart.Series<String, Double>();
		
		Series<String, Double> set_zusammensetzung_strom_verbrauch = new XYChart.Series<String, Double>();
		Series<String, Double> set_zusammensetzung_erdgas_verbrauch = new XYChart.Series<String, Double>();
		Series<String, Double> set_zusammensetzung_wasser_verbrauch = new XYChart.Series<String, Double>();
		Series<String, Double> set_zusammensetzung_abwasser_verbrauch = new XYChart.Series<String, Double>();
		
		Series<String, Double> set_zusammensetzung_strom_pauschale = new XYChart.Series<String, Double>();
		Series<String, Double> set_zusammensetzung_erdgas_pauschale = new XYChart.Series<String, Double>();
		Series<String, Double> set_zusammensetzung_wasser_pauschale = new XYChart.Series<String, Double>();
		Series<String, Double> set_zusammensetzung_abwasser_pauschale = new XYChart.Series<String, Double>();

		set_preise_strom.setName("Strompreise in €");
		set_preise_erdgas.setName("Erdgaspreise in €");
		set_preise_wasser.setName("Wasserpreise in €");
		set_preise_abwasser.setName("Abwasserpreise in €");

		set_pauschale_strom.setName("Strompauschale in €");
		set_pauschale_erdgas.setName("Erdgaspauschale in €");
		set_pauschale_wasser.setName("Wasserpauschale in €");
		set_pauschale_abwasser.setName("Abwasserpauschale in €");
		
		set_zusammensetzung_strom_verbrauch.setName("Verbrauch in %");
		set_zusammensetzung_erdgas_verbrauch.setName("Verbrauch in %");
		set_zusammensetzung_wasser_verbrauch.setName("Verbrauch in %");
		set_zusammensetzung_abwasser_verbrauch.setName("Verbrauch in %");
		
		set_zusammensetzung_strom_pauschale.setName("Pauschale in %");
		set_zusammensetzung_erdgas_pauschale.setName("Pauschale in %");
		set_zusammensetzung_wasser_pauschale.setName("Pauschale in %");
		set_zusammensetzung_abwasser_pauschale.setName("Pauschale in %");
		
		
				
		// Rechnungs-Zeitpunkte holen
		ResultSet rs_zeitpunkte_rechnung = db.executeQueryWithResult(
				"SELECT DISTINCT `id`, `zeitraum_bis`, `differenz_tage` FROM `zeitraum` ORDER BY `zeitraum_bis`");

		int anzahl_tage = 0;
		double gezahlte_pauschale_strom = 0;
		double gezahlte_pauschale_erdgas = 0;
		double gezahlte_pauschale_wasser = 0;
		double gezahlte_pauschale_abwasser = 0;

		try {
			while (rs_zeitpunkte_rechnung.next()) {

				anzahl_tage = rs_zeitpunkte_rechnung.getInt("differenz_tage");

				// Rechnung zu diesem Zeitraum holen => daraus dann Einstellung-ID holen
				ResultSet rs_rechnung = db.executeQueryWithResult(
						"SELECT `einstellung_id`, `zeitraum_id`, `betrag_menge_strom`, `betrag_menge_erdgas`, `betrag_menge_wasser`, `betrag_menge_abwasser`  FROM `rechnung` WHERE `zeitraum_id` = "
								+ rs_zeitpunkte_rechnung.getInt("id") + "");
				if (rs_rechnung.next()) {
					// Einstellung holen:
					ResultSet rs_einstellung = db.executeQueryWithResult(
							"SELECT `preis_strom`, `preis_erdgas` , `preis_wasser`,`preis_abwasser`, `pauschale_strom`, `pauschale_erdgas`, `pauschale_wasser`, `quadratmeter_gesamt`, `pauschale_abwasser_faktor` FROM `einstellung` WHERE `id` = "
									+ rs_rechnung.getInt("einstellung_id") + "");
					if (rs_einstellung.next()) {

						gezahlte_pauschale_strom = rs_einstellung.getDouble("pauschale_strom") / 365 * anzahl_tage;
						gezahlte_pauschale_erdgas = rs_einstellung.getDouble("pauschale_erdgas") / 365 * anzahl_tage;
						gezahlte_pauschale_wasser = rs_einstellung.getDouble("pauschale_wasser") / 365 * anzahl_tage;
						gezahlte_pauschale_abwasser = rs_einstellung.getDouble("quadratmeter_gesamt")
								* rs_einstellung.getDouble("pauschale_abwasser_faktor") / 365 * anzahl_tage;

						try {
							set_preise_strom.getData()
									.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
											rs_einstellung.getDouble("preis_strom")));
							set_pauschale_strom.getData()
									.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
											rs_einstellung.getDouble("pauschale_strom")));

							set_preise_erdgas.getData()
									.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
											rs_einstellung.getDouble("preis_erdgas")));
							set_pauschale_erdgas.getData()
									.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
											rs_einstellung.getDouble("pauschale_erdgas")));

							set_preise_wasser.getData()
									.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
											rs_einstellung.getDouble("preis_wasser")));
							set_pauschale_wasser.getData()
									.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
											rs_einstellung.getDouble("pauschale_wasser")));

							set_preise_abwasser.getData()
									.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
											rs_einstellung.getDouble("preis_abwasser")));
							set_pauschale_abwasser.getData()
									.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
											(rs_einstellung.getDouble("quadratmeter_gesamt")
													* (rs_einstellung.getDouble("pauschale_abwasser_faktor")))));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						
						double gesamtbetrag_strom = rs_rechnung.getDouble("betrag_menge_strom") + gezahlte_pauschale_strom;
						double anteil_pauschale_strom = gezahlte_pauschale_strom / gesamtbetrag_strom * 100;

						double gesamtbetrag_erdgas = rs_rechnung.getDouble("betrag_menge_erdgas")
								+ gezahlte_pauschale_erdgas;
						double anteil_pauschale_erdgas = gezahlte_pauschale_erdgas / gesamtbetrag_erdgas * 100;

						double gesamtbetrag_wasser = rs_rechnung.getDouble("betrag_menge_wasser")
								+ gezahlte_pauschale_wasser;
						double anteil_pauschale_wasser = gezahlte_pauschale_wasser / gesamtbetrag_wasser * 100;

						double gesamtbetrag_abwasser = rs_rechnung.getDouble("betrag_menge_abwasser")
								+ gezahlte_pauschale_abwasser;
						double anteil_pauschale_abwasser = gezahlte_pauschale_abwasser / gesamtbetrag_abwasser * 100;
						
						try {
							set_zusammensetzung_strom_pauschale.getData()
							.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
									(anteil_pauschale_strom)));
							set_zusammensetzung_erdgas_pauschale.getData()
							.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
									(anteil_pauschale_erdgas)));
							set_zusammensetzung_wasser_pauschale.getData()
							.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
									(anteil_pauschale_wasser)));
							set_zusammensetzung_abwasser_pauschale.getData()
							.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
									(anteil_pauschale_abwasser)));

							set_zusammensetzung_strom_verbrauch.getData()
							.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
									(100 - anteil_pauschale_strom)));
							set_zusammensetzung_erdgas_verbrauch.getData()
							.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
									(100 - anteil_pauschale_erdgas)));
							set_zusammensetzung_wasser_verbrauch.getData()
							.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
									(100 - anteil_pauschale_wasser)));
							set_zusammensetzung_abwasser_verbrauch.getData()
							.add(new XYChart.Data<String, Double>(DateConversion.dateFormating(rs_zeitpunkte_rechnung.getString("zeitraum_bis")),
									(100 - anteil_pauschale_abwasser)));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

					
					/*initPieChartPreiszusammensetzung(anteil_pauschale_strom, "Strom");
					initPieChartPreiszusammensetzung(anteil_pauschale_erdgas, "Erdgas");
					initPieChartPreiszusammensetzung(anteil_pauschale_wasser, "Wasser");
					initPieChartPreiszusammensetzung(anteil_pauschale_abwasser, "Abwasser");*/

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Serien zu Charts hinzufügen
		lc_preise_strom.getData().addAll(set_preise_strom); // Preise
		lc_pauschale_strom.getData().addAll(set_pauschale_strom); // Pauschale

		lc_preise_erdgas.getData().addAll(set_preise_erdgas);
		lc_pauschale_erdgas.getData().addAll(set_pauschale_erdgas);

		lc_preise_wasser.getData().addAll(set_preise_wasser);
		lc_pauschale_wasser.getData().addAll(set_pauschale_wasser);

		lc_preise_abwasser.getData().addAll(set_preise_abwasser);
		lc_pauschale_abwasser.getData().addAll(set_pauschale_abwasser);
		
		lc_preiszusammensetzung_strom.getData().addAll(set_zusammensetzung_strom_verbrauch, set_zusammensetzung_strom_pauschale);
		lc_preiszusammensetzung_erdgas.getData().addAll(set_zusammensetzung_erdgas_verbrauch, set_zusammensetzung_erdgas_pauschale);
		lc_preiszusammensetzung_wasser.getData().addAll(set_zusammensetzung_wasser_verbrauch, set_zusammensetzung_wasser_pauschale);
		lc_preiszusammensetzung_abwasser.getData().addAll(set_zusammensetzung_abwasser_verbrauch, set_zusammensetzung_abwasser_pauschale);

	}
	
	public void actionAuswahlMonatJahr() {
		DB db = new DB();
		
		if(cb_auswahl_monat_jahr.getValue().equals("Zeitpunktansicht")) { // => Monatsanzeige
			initBarChartAndLineChartKostenAllgemein(db, true, true);	
			initBarChartKostenAllgemeinProJahr(db, false);
		}
		else {
			initBarChartAndLineChartKostenAllgemein(db, false, true);		// => Jahresanzeige
			initBarChartKostenAllgemeinProJahr(db, true);
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

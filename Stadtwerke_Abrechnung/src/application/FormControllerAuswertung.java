package application;

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
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Double> bc;
    
	public void initialize() {
		initBarChart();
	}
	
	public void initBarChart() {
		
		xAxis = new CategoryAxis();
        xAxis.setLabel("Länder");
        
        yAxis = new NumberAxis();
        yAxis.setLabel("Anzahl");
        
		
        bc.setTitle("Country Summary");
        
          
       
        Series<String, Double> set1 = new XYChart.Series<String, Double>();
        set1.setName("Timiboy");
		set1.getData().add(new XYChart.Data<String, Double>("100", 200.0)); 
		
        bc.getData().addAll(set1);
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

package application;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class FormControllerAuswertung {
	
	final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";
    
    @FXML
    private NumberAxis xAxis;
    @FXML
    private CategoryAxis yAxis;

    @FXML
    private BarChart<Number, String> bc;
    
	public void initialize() {
		initBarChart();
	}
	
	public void initBarChart() {
		bc = new BarChart<Number,String>(xAxis,yAxis);
        bc.setTitle("Country Summary");
        xAxis.setLabel("Value");  
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Country");        
 
        XYChart.Series<Number, String> series1 = new XYChart.Series();
        series1.setName("2003");       
        series1.getData().add(new XYChart.Data(25601.34, austria));
        series1.getData().add(new XYChart.Data(20148.82, brazil));
        series1.getData().add(new XYChart.Data(10000, france));
        series1.getData().add(new XYChart.Data(35407.15, italy));
        series1.getData().add(new XYChart.Data(12000, usa));      
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().add(new XYChart.Data(57401.85, austria));
        series2.getData().add(new XYChart.Data(41941.19, brazil));
        series2.getData().add(new XYChart.Data(45263.37, france));
        series2.getData().add(new XYChart.Data(117320.16, italy));
        series2.getData().add(new XYChart.Data(14845.27, usa));  
        
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("2005");
        series3.getData().add(new XYChart.Data(45000.65, austria));
        series3.getData().add(new XYChart.Data(44835.76, brazil));
        series3.getData().add(new XYChart.Data(18722.18, france));
        series3.getData().add(new XYChart.Data(17557.31, italy));
        series3.getData().add(new XYChart.Data(92633.68, usa));  
        
       
        bc.getData().addAll(series1, series2, series3);
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

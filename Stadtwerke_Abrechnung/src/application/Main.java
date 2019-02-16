package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public static Stage window = null;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		window = primaryStage;
		
		try { 
			BorderPane root =  (BorderPane) FXMLLoader.load(Main.class.getResource("Uebersicht.fxml"));
			Scene scene_start = new Scene(root,1920,998);
			scene_start.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			window.setScene(scene_start);
			window.show();
			window.setTitle("Abrechnungsprogramm");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setStage(String scene_name) {
		try {
			BorderPane root = null;		
			root = FXMLLoader.load(Main.class.getResource(scene_name+".fxml")); 
			
			Scene scene = new Scene(root,1920,998);
			scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

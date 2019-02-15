package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
			BorderPane root = FXMLLoader.load(getClass().getResource("Main.fxml"));  
			Scene scene_start = new Scene(root,1536,864);
			scene_start.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene_start);
			primaryStage.show();
			
			primaryStage.setTitle("Abrechnungsprogramm");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Stage getStage() {
		return window;
	}
	
	
}

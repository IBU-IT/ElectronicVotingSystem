package application;
	
import com.sun.javafx.iio.common.ScalerFactory;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Scale;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/application/Results.fxml"));
			//blabla
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			//	primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			//primaryStage.setMaximized(true);
			primaryStage.setFullScreen(true);
		
			
		} catch(Exception e) {
			System.out.println(e);
			System.out.println(e.getCause().toString());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

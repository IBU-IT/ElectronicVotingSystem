package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class SecondScreenController implements Initializable {

	@FXML
	private Button nextButton;
	
	Scene sc;
	Parent root;
	@FXML
	public void onNext(ActionEvent e) throws IOException{
		sc=(Scene) nextButton.getScene();
		root = FXMLLoader.load(getClass().getResource("/application/ScreenAfterIntro.fxml")); 	      
		sc.setRoot(root);		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}

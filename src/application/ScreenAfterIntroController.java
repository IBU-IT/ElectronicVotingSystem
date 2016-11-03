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

public class ScreenAfterIntroController implements Initializable {

	
	Scene sc;
	Parent root;
	
	@FXML
	private Button adminMode;
	@FXML
	private Button voterMode;
	
	@FXML
	public void adminMode(ActionEvent e) throws IOException{
		sc=(Scene) adminMode.getScene();
		root = FXMLLoader.load(getClass().getResource("/application/AdminModeView.fxml")); 	      
		sc.setRoot(root);	
	}
	@FXML
	public void voterMode(ActionEvent e) throws IOException{
		sc=(Scene) voterMode.getScene();
		root = FXMLLoader.load(getClass().getResource("/application/VoterModeView.fxml")); 	      
		sc.setRoot(root);	
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}

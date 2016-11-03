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

public class VoterModeViewController implements Initializable{

	@FXML private Button goToScan;
	@FXML private Button goToType;
	
	Parent root;
	Scene scene;
	@FXML
	public void OnScanButton(ActionEvent e) throws IOException{
		scene=(Scene) goToScan.getScene();
		root=FXMLLoader.load(getClass().getResource("/application/WebCamView.fxml"));
		scene.setRoot(root);
	}
	@FXML
	public void onTypeButton(ActionEvent e) throws IOException{
		scene=(Scene) goToType.getScene();
		root=FXMLLoader.load(getClass().getResource("/application/TypeVoterModeView.fxml"));
		scene.setRoot(root);
	}
	
	@FXML private Button over;
	
	@FXML
	public void onOver(ActionEvent e) throws IOException
	{
		scene=(Scene) over.getScene();
		root=FXMLLoader.load(getClass().getResource("/application/AdminCheck.fxml"));
		scene.setRoot(root);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}

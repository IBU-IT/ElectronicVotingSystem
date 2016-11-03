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

public class BadCodeController implements Initializable{

	@FXML Button ok;
	Parent root;
	Scene sc;
	public void OnOK(ActionEvent e) throws IOException{
		sc=(Scene) ok.getScene();
		root=FXMLLoader.load(getClass().getResource("/application/WebCamView.fxml"));
		sc.setRoot(root);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}

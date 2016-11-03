package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import burch.edu.ibu.DatabaseManipulation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AdminCheckController implements Initializable {

	@FXML private Button verify;
	@FXML private TextField usr;
	@FXML private PasswordField pass;
	
	
	 Parent root;
	 Scene sc;
	 
	public void onVerify(ActionEvent e) throws IOException{
		
		if(DatabaseManipulation.ConnectToDataBase(usr.getText(), pass.getText())){
			sc=(Scene) verify.getScene();
			
			root=FXMLLoader.load(getClass().getResource("/application/Results.fxml"));
			sc.setRoot(root);
		}
		else 
		{
			sc=(Scene) verify.getScene();
			
			root=FXMLLoader.load(getClass().getResource("/application/VoterModeViewFailedAdmin.fxml"));
			sc.setRoot(root);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}

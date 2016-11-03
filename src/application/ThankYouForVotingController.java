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

public class ThankYouForVotingController implements Initializable {

	@FXML private Button nextvoter;
	
	Scene sc;
	Parent root;
	@FXML public void onNext(ActionEvent e) throws IOException{
		
		//treba napravit da ustvari provjerava sad ce vazda bit true ovaj gore statement
		sc=(Scene) nextvoter.getScene();
		//load up OTHER FXML document
		root = FXMLLoader.load(getClass().getResource("/application/VoterModeView.fxml")); 	      
		sc.setRoot(root);		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}

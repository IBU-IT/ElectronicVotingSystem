package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.fabric.xmlrpc.base.Data;
import com.mysql.jdbc.UpdatableResultSet;

import burch.edu.ibu.DatabaseManipulation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class TypeVoterModeController implements Initializable {

	@FXML private TextField UserID;
	@FXML private Button proccedToVote;
	
	Parent root;
	Scene sc;
	
	
	
	@FXML public void onProccedToVote(ActionEvent e) throws NumberFormatException, SQLException, IOException
	{
		int test=DatabaseManipulation.FindYourIDVoter(DatabaseManipulation.returnConnection(), 
				Integer.parseInt(UserID.getText()));
		if(test==1)
		{
			sc=(Scene) proccedToVote.getScene();
			root = FXMLLoader.load(getClass().getResource("/application/VotingModeView.fxml")); 	      
			sc.setRoot(root);	
			DatabaseManipulation.UpdateVoter(DatabaseManipulation.returnConnection(), Integer.parseInt(UserID.getText()));
			
		}
		else if(test==0)
		{
			sc=(Scene) proccedToVote.getScene();
			root = FXMLLoader.load(getClass().getResource("/application/TypeVoterModeFailedView.fxml")); 	      
			sc.setRoot(root);			
		}
		else if(test==2){
			sc=(Scene) proccedToVote.getScene();
			root = FXMLLoader.load(getClass().getResource("/application/VoterAlreadyVoted.fxml")); 	      
			sc.setRoot(root);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}

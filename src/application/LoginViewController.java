package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import burch.edu.ibu.DatabaseManipulation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
public class LoginViewController implements Initializable {

	@FXML
	private Button buttonForConnect;
	@FXML
	private TextField usernamefield;
	@FXML	
	private PasswordField passwordfield;
	@FXML
	private Button exitButton;
		Scene sc;
	    Parent root;
	    Stage st;
	@FXML
	public void connectButtonHandler(ActionEvent e) throws IOException
	{	
	  
		if(DatabaseManipulation.ConnectToDataBase(usernamefield.getText(), passwordfield.getText())) //ako prodje username i password, otvara drugi ekran
		{
			//treba napravit da ustvari provjerava sad ce vazda bit true ovaj gore statement
			sc=(Scene) buttonForConnect.getScene();
			//load up OTHER FXML document
			root = FXMLLoader.load(getClass().getResource("/application/SecondScreenView.fxml")); 	      
			sc.setRoot(root);		
		}
		else //otvara ovaj prvi opet samo napise wrong username or password please try again 
		{
					
			sc=(Scene) buttonForConnect.getScene();
			//load up OTHER FXML document
			root = FXMLLoader.load(getClass().getResource("/application/UserOrPassFailedView.fxml"));  	      
			sc.setRoot(root);			
		}	   
	}		
	
	public void onExitButton(ActionEvent e)
	{
		st=(Stage) exitButton.getScene().getWindow();
		st.close();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}

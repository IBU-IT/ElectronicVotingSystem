package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import burch.edu.ibu.DatabaseManipulation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class VotingModeViewController implements Initializable{

	
	@FXML private TableView tableviewCand;
	@FXML private ObservableList datacand;
	
	void BuildDataTable(){
	
			  Connection c ;
				tableviewCand.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			  datacand = FXCollections.observableArrayList();
	          try{
	            c =DatabaseManipulation.returnConnection();
	            //SQL FOR SELECTING ALL OF CUSTOMER
	            String SQL = "SELECT idcandidate,candidatename,candidatelastname from candidates";
	            //ResultSet
	            ResultSet rs = c.createStatement().executeQuery(SQL);

	            if (tableviewCand.getColumns().size() == 0){
	            	 
	            	TableColumn col1 = new TableColumn("ID");
	            	col1.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
	                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
	                        return new SimpleStringProperty(param.getValue().get(0).toString());                        
	                    }                    
	                });
	            	tableviewCand.getColumns().add(col1);
	            	 
	            	TableColumn col2 = new TableColumn("First Name");
	            	col2.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
	                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
	                        return new SimpleStringProperty(param.getValue().get(1).toString());                        
	                    }                    
	                });
	            	tableviewCand.getColumns().add(col2);
	            	
	            	TableColumn col3 = new TableColumn("Last Name");
	            	col3.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
	                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
	                        return new SimpleStringProperty(param.getValue().get(2).toString());                        
	                    }                    
	                });
	            	tableviewCand.getColumns().add(col3);
	            }
	            
	            while(rs.next()){
	                //Iterate Row
	                ObservableList<String> row = FXCollections.observableArrayList();
	                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
	                    //Iterate Column
	                    row.add(rs.getString(i));
	                }
	                System.out.println("Row [1] added "+row );
	                datacand.add(row);
	            }

	            //FINALLY ADDED TO TableView
	            tableviewCand.setItems(datacand);
	            
	          }catch(Exception e){
	              e.printStackTrace();
	              System.out.println("Error on Building Data");             
	          }
	      }
	
	
	@FXML private Button castVote;
	@FXML private TextField id;
	@FXML private TextField name;
	@FXML private TextField lname;
	int idTorec=0;
	public void RecogniseID(KeyEvent k) throws SQLException
	{
		idTorec=Integer.parseInt(id.getText());
		System.out.println(idTorec);
		 ResultSet rs;
		 Connection con=DatabaseManipulation.returnConnection();
		 Statement stmt = con.createStatement();

         rs = stmt.executeQuery("SELECT * FROM candidates");
         while ( rs.next() ) {
        	 int id=rs.getInt("idcandidate");
        	 System.out.println("id from db " + id);
        	   if(id==idTorec){
        		   String firstName=rs.getString("candidatename");
        		   System.out.println(firstName);
              	 String lastName = rs.getString("candidatelastname"); 
              	 System.out.println(lastName);
        	   name.setText(firstName);
        	   lname.setText(lastName);
        	   break;
           } 
           else {
        	   name.setText("ID NOT FOUND");
        	   lname.setText("ID NOT FOUND");
           }
         } 
	 }
	Parent root;
	Scene sc;
	public void onCast(ActionEvent e) throws NumberFormatException, SQLException, IOException{
		if(name.getText().equals("ID NOT FOUND"))
		{
			
			sc=(Scene) castVote.getScene();
			//load up OTHER FXML document
			root = FXMLLoader.load(getClass().getResource("/application/WrongCast.fxml"));  	      
			sc.setRoot(root);		
		}
		else{
			DatabaseManipulation.CastVoteIntoDB(DatabaseManipulation.returnConnection(),
					Integer.parseInt(id.getText()),name.getText(),lname.getText());
			sc=(Scene) castVote.getScene();
			//load up OTHER FXML document
			root = FXMLLoader.load(getClass().getResource("/application/ThankYouForVoting.fxml"));  	      
			sc.setRoot(root);	
		}
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		BuildDataTable();
	}

}

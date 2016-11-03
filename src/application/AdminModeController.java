package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;


public class AdminModeController implements Initializable {
	
	@FXML private TextField name;
	@FXML private TextField lastname;
	@FXML private TextField id;
	@FXML private Button insert;
 	@FXML  private ObservableList<ObservableList> datacand;
	@FXML  private ObservableList<ObservableList> datavote;
	@FXML  private TableView tableviewVoters;
	@FXML  private TableView tableviewCand;
	@FXML private Button generateData;
	@FXML private Button deleteByID;
	@FXML private Button deleteByName;
	@FXML private Button deleteByLastName;
	@FXML private TextField nametodel;
	@FXML private TextField lastnametodel;
	@FXML private TextField idtodel;

	
//DataBuilder za tabelu koji ne radi jebem mu sve 
		void buildDataforVoters(){
			tableviewVoters.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		  Connection c ;
		  datavote = FXCollections.observableArrayList();
          try{
            c =DatabaseManipulation.returnConnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT idvoters,votersname,votersln from voters";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            if (tableviewVoters.getColumns().size() == 0){
            	 
            	TableColumn col1 = new TableColumn("ID");
            	col1.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(0).toString());                        
                    }                    
                });
            	tableviewVoters.getColumns().add(col1);
            	 
            	TableColumn col2 = new TableColumn("First Name");
            	col2.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(1).toString());                        
                    }                    
                });
            	tableviewVoters.getColumns().add(col2);
            	
            	TableColumn col3 = new TableColumn("Last Name");
            	col3.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(2).toString());                        
                    }                    
                });
            	tableviewVoters.getColumns().add(col3);
            }
            
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                datavote.add(row);
            }

            //FINALLY ADDED TO TableView
            tableviewVoters.setItems(datavote);
            
          }catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");             
          }
      }
		void buildDataforCands(){
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

	
	// treba napravit da samo jednom build data pa da je refresh ili da obrise staru tabelu pa upise novu
	  //treba googlat
		
		//ZA VOTER TAB
	 public void OnDeletebyID(ActionEvent e) throws NumberFormatException, SQLException
	  {
		  DatabaseManipulation.DeleteVotersFromDBbyID(DatabaseManipulation.returnConnection(), Integer.parseInt(idtodel.getText()));
		  buildDataforVoters();
		  }	 
	 public void OnDeletebyName(ActionEvent e) throws SQLException
	  {
		DatabaseManipulation.DeleteVotersFromDBbyName(DatabaseManipulation.returnConnection(), nametodel.getText());  
		buildDataforVoters();
		}
	 public void OnDeletebyLastName(ActionEvent e) throws SQLException
	  {
		DatabaseManipulation.DeleteVotersFromDBbyLastName(DatabaseManipulation.returnConnection(), lastnametodel.getText());  
		buildDataforVoters();
		}	 
     @FXML
     public void onInsertButton(ActionEvent e) throws SQLException	//ovdje ima sranje sa builddata i refreshom
     {
    	 DatabaseManipulation.InsertVotersIntoDB(DatabaseManipulation.returnConnection(),
    			 		name.getText(), lastname.getText(), Integer.parseInt(id.getText()));
    	buildDataforVoters();
    	 
     }	
  
     // ZA CANDIDATE TAB
	
    @FXML private TextField name_cand;
    @FXML private TextField lastname_cand;
    @FXML private TextField id_cand;
    @FXML private Button insertCand;
    @FXML private TextField nametodelcand;
 	@FXML private TextField lastnametodelcand;
 	@FXML private TextField idtodelcand;
 	@FXML private Button deleteByIDcand;
	@FXML private Button deleteByNamecand;
	@FXML private Button deleteByLastNamecand;
     
	
	
	 public void OnInsertButtonCand(ActionEvent e){
	    	 DatabaseManipulation.InsertCandidatesIntoDB(DatabaseManipulation.returnConnection(), 
	    			 name_cand.getText(), lastname_cand.getText(), Integer.parseInt(id_cand.getText()));
	    	 buildDataforCands();
	     }
     public void OnDeleteByIDCand(ActionEvent e) throws NumberFormatException, SQLException{
    	 DatabaseManipulation.DeleteCandidatesFromDBbyID(DatabaseManipulation.returnConnection(), 
    			 Integer.parseInt(idtodelcand.getText()));
    	 buildDataforCands();
     }
     public void OnDeleteByNameCand(ActionEvent e) throws SQLException{
    	 DatabaseManipulation.DeleteCandidatesFromDBbyName(DatabaseManipulation.returnConnection(), 
    			 	nametodelcand.getText());
    	 buildDataforCands();
     }
     public void OnDeleteByLastNameCand(ActionEvent e) throws SQLException{
    	 DatabaseManipulation.DeleteCandidatesFromDBbyLastName(DatabaseManipulation.returnConnection(),
    			 lastnametodelcand.getText());
    	 buildDataforCands();
     }
     
      @FXML private Button done;
      
      public void onDone(ActionEvent e) throws IOException{
    	  
    	  Parent root;
    		Scene sc;
    		sc=(Scene) done.getScene();
			root = FXMLLoader.load(getClass().getResource("/application/ScreenAfterIntro.fxml")); 	      
			sc.setRoot(root);		
      }
     
     @Override	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		buildDataforCands();
		buildDataforVoters();
	}

	

}

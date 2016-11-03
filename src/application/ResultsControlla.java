package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import burch.edu.ibu.DatabaseManipulation;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class ResultsControlla implements Initializable{

	@FXML private TableView tableviewVoters;
	@FXML private ObservableList datavoter;
	
	
	@FXML private TableView tableviewCands;
	@FXML private ObservableList datacands;
	@FXML private ObservableList datavote;
	

	@FXML private Button next;
	
	public void onNext(ActionEvent e){}
	void outputVoters(){
		tableviewVoters.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		  Connection c ;
		  datavoter = FXCollections.observableArrayList();
        try{
          c =DatabaseManipulation.returnConnection();
          //SQL FOR SELECTING ALL OF CUSTOMER
          String SQL = "SELECT * from voters";
          //aa
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
          	
          	TableColumn col4=new TableColumn("Has the user voted");
        	col4.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                	if((param.getValue().get(3).toString()).equals("1")){
                	    return new SimpleStringProperty("Yes");                        
                        
                	}
                	else return new SimpleStringProperty("No");                        
                    
                }                    
            });
        	tableviewVoters.getColumns().add(col4);
          	
          }
          
          while(rs.next()){
              //Iterate Row
              ObservableList<String> row = FXCollections.observableArrayList();
              for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                  //Iterate Column
                  row.add(rs.getString(i));
              }
              System.out.println("Row [1] added "+row );
              datavoter.add(row);
          }

          //FINALLY ADDED TO TableView
          tableviewVoters.setItems(datavoter);
          
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");  
        }
        }
	void outputCandsandResults(){
		tableviewCands.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		  Connection c ;
		  datacands= FXCollections.observableArrayList();
      try{
        c =DatabaseManipulation.returnConnection();
        DatabaseManipulation.updateResults();
        
String sql="DELETE from results where name is null";
		
		PreparedStatement stat=c.prepareStatement(sql);
		stat.execute();
        //SQL FOR SELECTING ALL OF CUSTOMER
        String SQL = "SELECT name, lastname,numberofvotes from votingsystemdb.results";
        //aa
        //ResultSet
        ResultSet rs = c.createStatement().executeQuery(SQL);

        if (tableviewCands.getColumns().size() == 0){
        	 
        	 
        	TableColumn col1 = new TableColumn("First Name");
        	col1.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                    return new SimpleStringProperty(param.getValue().get(0).toString());                        
                }                    
            });
        	tableviewCands.getColumns().add(col1);
        	
        	TableColumn col2 = new TableColumn("Last Name");
        	col2.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                    return new SimpleStringProperty(param.getValue().get(1).toString());                        
                }                    
            });
        	tableviewCands.getColumns().add(col2);
        	
        	TableColumn col3 = new TableColumn("Number of votes won");
        	col3.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                    return new SimpleStringProperty(param.getValue().get(2).toString());                        
                }                    
            });
        	tableviewCands.getColumns().add(col3);
        	
      		
        }
        
        while(rs.next()){
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<=(rs.getMetaData().getColumnCount()); i++){
                //Iterate Column
                row.add(rs.getString(i));
            }
            System.out.println("Row [1] added "+row );
            datacands.add(row);
        }

        //FINALLY ADDED TO TableView
        tableviewCands.setItems(datacands);
        
      }catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");  
      }
	}
	
	
	@FXML private Button end;
	
	public void onEnd(ActionEvent e){
		Platform.exit();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		outputVoters();
		outputCandsandResults();
	
	}

}

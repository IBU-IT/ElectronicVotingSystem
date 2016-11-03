package burch.edu.ibu;

import java.sql.*;
public class DatabaseManipulation { 
	/*
	 * klasa sa svim metodama vezanim za
	 * konektovanje, ubacivanje i editovanje podataka u bazi
	 */
	
	//kada napravimo gui trebat ce editovat metode tako da odgovaraju gui-u i dodati parametre osim konekcije, npr. ime i prezime votera
	
	public static boolean ConnectToDataBase(String usr,String pass) {	//Bez odradjivanja ove metode nista ne moze poceti 
	

	String host="jdbc:mysql://127.0.0.1:3306/votingsystemdb?autoReconnect=true&useSSL=false";
		
		try {
			Connection con=DriverManager.getConnection(host, usr, pass);
		//	System.out.println("Worked");
			return true;
		} catch (SQLException e) {
		//	// TODO Auto-generated catch block			
			return false;
		}		
	}
	public static Connection returnConnection(){
		String host="jdbc:mysql://127.0.0.1:3306/votingsystemdb?autoReconnect=true&useSSL=false";
		String usr="root";
		String pass="admin";
		Connection con = null;
		try {
			con=DriverManager.getConnection(host, usr, pass);
		//	System.out.println("Worked");
			return con;
		} catch (SQLException e) {
		//	// TODO Auto-generated catch block			
			return con;
		}		
	}

	
	//ova se metoda i ne koristi jer ima select tamo u controlleru za build data za tableview al eto nek stoji	
	public static void SelectVotersFromDB(Connection con){
		 try {
			 ResultSet rs;
			 Statement stmt = con.createStatement();

	         rs = stmt.executeQuery("SELECT * FROM voters");
	         while ( rs.next() ) {
	        	 int id=rs.getInt("votersid");
	        	 String firstName=rs.getString("votername");
	        	 String lastName = rs.getString("votersln");
	             System.out.print(id +" ");
	        	 System.out.print(firstName+" ");
	        	 System.out.println(lastName);
	         } 
		 }
			
		catch (Exception e) {
			// TODO: handle exception
		}
        		
	}


	
	public static void InsertVotersIntoDB(Connection con,String name,String lastname,int id){
		try {
			
		      // the mysql insert statement
		      String query = " insert into voters(idvoters,votersname,votersln, voterhasvoted)"
		        + " values (?, ?, ?, ?)";

		     String namecor=name.substring(0, 1).toUpperCase() + name.substring(1);
		      String lastnamecor=lastname.substring(0, 1).toUpperCase() + lastname.substring(1);
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setInt (1, id);
		      preparedStmt.setString (2,namecor );
		      preparedStmt.setString (3, lastnamecor);
		      preparedStmt.setInt(4, 0);
		      
		     

		      // execute the preparedstatement
		      preparedStmt.execute();
		      System.out.println("worked");
		      			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void DeleteVotersFromDBbyID(Connection con,int ID) throws SQLException{
		
		String sql="DELETE from voters where idvoters=?";
		
		PreparedStatement stat=con.prepareStatement(sql);
		stat.setInt(1, ID);
		stat.execute();
		
	}
	public static void DeleteVotersFromDBbyName(Connection con,String name) throws SQLException
	{
		String namecor=name.substring(0,1).toUpperCase() + name.substring(1);
		String sql="DELETE from voters where votersname=?";
		
		PreparedStatement stat=con.prepareStatement(sql);
		stat.setString(1, namecor);
		stat.execute();
	}
	public static void DeleteVotersFromDBbyLastName(Connection con, String lastname) throws SQLException {
		String lastnamecor=lastname.substring(0,1).toUpperCase() + lastname.substring(1);
		String sql="DELETE from voters where votersname=?";
		
		PreparedStatement stat=con.prepareStatement(sql);
		stat.setString(1, lastnamecor);
		stat.execute();
		
	}

	
	public static void InsertCandidatesIntoDB(Connection con,String name,String lastname,int id){
		try {
			
		      // the mysql insert statement
		      String query = " insert into candidates(idcandidate,candidatename,candidatelastname)"
		        + " values (?, ?, ?)";

		     String namecor=name.substring(0, 1).toUpperCase() + name.substring(1);
		      String lastnamecor=lastname.substring(0, 1).toUpperCase() + lastname.substring(1);
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setInt (1, id);
		      preparedStmt.setString (2,namecor );
		      preparedStmt.setString (3, lastnamecor);
		     
		          

		      // execute the preparedstatement
		      preparedStmt.execute();
		      			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void DeleteCandidatesFromDBbyID(Connection con,int ID) throws SQLException{
		
		String sql="DELETE from candidates where idcandidate=?";
		
		PreparedStatement stat=con.prepareStatement(sql);
		stat.setInt(1, ID);
		stat.execute();
		
	}
	public static void DeleteCandidatesFromDBbyName(Connection con,String name) throws SQLException
	{
		String namecor=name.substring(0,1).toUpperCase() + name.substring(1);
		String sql="DELETE from candidates where candidatename=?";
		
		PreparedStatement stat=con.prepareStatement(sql);
		stat.setString(1, namecor);
		stat.execute();
	}
	public static void DeleteCandidatesFromDBbyLastName(Connection con,String lastname) throws SQLException
	{
		String lastnamecor=lastname.substring(0,1).toUpperCase() + lastname.substring(1);
		String sql="DELETE from candidates where candidatelastname=?";
		
		PreparedStatement stat=con.prepareStatement(sql);
		stat.setString(1, lastnamecor);
		stat.execute();
	}


	public static int FindYourIDVoter(Connection con,int ID) throws SQLException
	{
		 ResultSet rs;
		 Statement stmt = con.createStatement();

         rs = stmt.executeQuery("SELECT * FROM voters");
         while ( rs.next() ) {
        	 int id=rs.getInt("idvoters");
        	 String firstName=rs.getString("votersname");
        	 String lastName = rs.getString("votersln");
        	 int hasvoted=rs.getInt("voterhasvoted");
             if(id==ID){
            	if(hasvoted==0)
            	{
            		return 1;
            	}
            	else return 2;
             }           	
	}
         return 0;

	}

	public static void UpdateVoter(Connection con, int ID) throws SQLException
	{
		String querry=" update voters set voterhasvoted=? WHERE idvoters = ? " ;
		PreparedStatement stmt=con.prepareStatement(querry);
		stmt.setInt(1, 1);
		stmt.setInt(2, ID);
		
		stmt.executeUpdate();
		stmt.close();
	}

public static void CastVoteIntoDB(Connection con,int vote, String name, String lname) throws SQLException{
	String querry="insert into castedvotes(idvotecastedto,candname,candln)" + "values (?,?,?)";

	PreparedStatement stmt=con.prepareStatement(querry);

	stmt.setInt(1, vote);
	stmt.setString(2, name);
	stmt.setString(3, lname);
	stmt.execute();
	
	
	
	
	}

public static int returnNumofCands(){
	   try {
		 ResultSet rs;
		 Connection con=DatabaseManipulation.returnConnection();
		 Statement stmt = con.createStatement();
		 int numofcan=0;
        rs = stmt.executeQuery("SELECT * FROM candidates");
        while ( rs.next() ) {
       	 int id=rs.getInt("idcandidate");
       	 String firstName=rs.getString("candidatename");
       	 String lastName = rs.getString("candidatelastname");
       	 numofcan++;
        } 
        return numofcan;
        }
		
	   
	catch (Exception e) {
		// TODO: handle exception
	}
	return 0;
	   
}
 
public static void updateResults() throws SQLException{
	Connection con=DatabaseManipulation.returnConnection();
	Statement st=con.createStatement();
	String querry="insert into votingsystemdb.results(name,lastname,numberofvotes) SELECT candname, candln, COUNT(idvotecastedto) AS numofvotes FROM"
			+ " votingsystemdb.castedvotes GROUP BY candname WITH ROLLUP";
	st.execute(querry);

	
}


}

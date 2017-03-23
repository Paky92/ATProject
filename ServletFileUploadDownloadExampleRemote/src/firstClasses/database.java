package firstClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
	
	private Connection connection;
	 
 	public database (String driver, String url) {
 		try {
 			Class.forName(driver);
 			connection=DriverManager.getConnection(url);
 		}catch(Exception ex) {
  			ex.printStackTrace();
  		}
 	}

 	public boolean isClosed(){
 	
 		boolean closed=false;
 		try{
 			closed= connection.isClosed();
 		}catch(SQLException  ex){
 			ex.printStackTrace();
 		}
 		return closed;
 	}
 	
 	public void close() {
 		try{
 			if (connection != null) connection.close();
 		}catch(SQLException ex) {
 	 }
 	}
 
 	public ResultSet sqlExp(String sql) {
 		
 		ResultSet r = null;
	
	  	try{
	  		Statement statement = connection.createStatement();
	  		boolean hasResults = statement.execute(sql);
	  	  	if(hasResults) r = statement.getResultSet();
	  	}catch(Exception ex) {
	  		System.out.println(ex.toString());
	  	}
	  	return r;
 	}
 	
 	public ResultSet sqlUpdate(String sql) {
 		
 		ResultSet r = null;
	
	  	try{
	  		Statement statement = connection.createStatement();
	  		int hasResults = statement.executeUpdate(sql);
	  	  if(hasResults != 0) r = statement.getResultSet();
	  	}catch(Exception ex) {
	  	}
	  	return r;
 	}	 	
}

package projectx.mySQL; 
import java.sql.*;

import projectx.engine.io.IO; 
/*
@author: Alain Njipwo 
@data: 10-30-18 
this class connects to the DB. Allows you to create a DataBase, Create tables, insert records. All have to be done manually 
and in accordance to SQL syntax.  
*/
public class JDBC_Connect_Create_update {

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; 
	static final String DB_URL = "jdbc:mysql://proj309-sb-06.misc.iastate.edu:4040/309_projx?serverTimezone=UTCuseUnicode=true&characterEncoding=UTF-8"; 
	
	// creadentials  // I am not sure how this would be done with a local host
	static final String USER = ""; 
	static final String PASS = "SB_6projectuser"; 
	
	public static void main(String[] args) throws SQLException {
		Connection conn = null; 
		Statement stmt = null; 
		
		// try and catch statements ... 
		try {
			
			Class.forName("com.mysql.jdbc.Driver"); 
			System.out.printf("attempting to connect to %s \n",DB_URL);
			
				try {
					// open a connection 
					conn =DriverManager.getConnection(DB_URL, USER, PASS);
					IO.println("Successfully connected to the database");
				} catch(Exception e) {
					IO.println("cannot connect properly \n exiting");
					System.exit(0);
			}
			
			
			// test cases for query(s). 
			
			stmt = conn.createStatement();
			
			//----------------------------------------------------------------------Test cases-------------------------------------------
		
						/*
							//creating database      // getting an error because of the user access will try and give all access necessary when i ssh @Alain 10-30-18
							 
							IO.println("Creating database");
							String c_db = "CREATE DATABASE TestDB_00";
							stmt.execute(c_db); 
							IO.println("database created successfully");
						*/
			
			
			
			
//			//creating a table 
//			IO.println("Creating table...");
//			
//			 String sql1 = "CREATE TABLE byte_array_Table " +
//	                   "(byte_value char(9) not NULL)"; 
//			
//			//String table = "CREATE TABLE byte_table";
//			stmt.executeUpdate(sql1);
//			IO.println("table created");
//			
			// deleting tables 
			
			IO.println("Deleting table....");
			String delT ="DROP TABLE byte_array_Table"; 
			stmt.executeUpdate(delT);
		IO.println("Table successfully dropped ");
		
			
			//deleting table 
			//IO.println("Deleting table");
			//String rem = "DELETE TABLE byte_array_table";
			//stmt.executeQuery(rem); 
			//String sql ="SELECT* from Entity_inventory"; 
			//stmt.executeUpdate(sql); 
			//stmt.executeQuery(sql1);
			//IO.println("database created successfully");
		
		
			stmt.close();
			conn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			
				try {
					if(stmt !=null)
						stmt.close(); 
				}catch(SQLException se2) {
					
				} // do nothing 
				try {
					if(conn != null)
						conn.close(); 
						
				}catch(SQLException se) {
					se.printStackTrace();
				} // end finally try
		    } // end try 
		IO.println("Exiting");
  } // end main 
	
	
	
	
}
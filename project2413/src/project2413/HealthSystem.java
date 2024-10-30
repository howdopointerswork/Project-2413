package project2413;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class HealthSystem{
	
	private User currentUser;
	
	private String version;
	
	private String url; //for connecting to db using conn and 
	
	private String query; //for writing sql queries
	
	private Connection conn; //for creating statements
	
	private Statement exQ; //for executing queries
	
	private ResultSet rs; //for executing SELECT queries
	
	private boolean isAuthenticated;
	
	
	
	
	//add args below (exam, db, etc.)
	
	public HealthSystem() {
		
		
		this.url = "jdbc:sqlite:Database.db";
		
	}
	

	
	
	
	public boolean db_open() { 
	
		
		try {
			
			this.conn = DriverManager.getConnection(this.url);
			this.exQ = this.conn.createStatement();
		
		} catch (SQLException e) {

			System.out.println(e.getMessage());
			
		}
				
		return (conn != null ? true: false);
					
			
	}
	
	public void setQuery(String newQuery) {
		
		this.query = newQuery;
		
	}
	
	public void testQuery() { //for debugging and trying queries
	
		//getting table names
		try {
			
			this.rs = exQ.executeQuery(this.query);
			
			System.out.println("Tables: ");
			
			while(this.rs.next()) {
				
				System.out.println(this.rs.getString("name"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public boolean addResult(Exam result) {
		
		//Replace with values in db
		
		//setQuery("INSERT INTO Table (name) VALUES ('')");
		//this.exQ.execute(this.query);
		
		//check if it worked, then return true
		//else return false
		return true;
	}
	
	
	
	public void editResult(Exam result, int id) {
		
		
	}
	
	
	public void deleteResult(Exam result, int id) {
		
		
		
	}
	
	
	public Exam findResult(int id) {
		
		Exam e = new Exam("28/10/24", "Liver Function", 0);
		
		return e;
		
	}
	
	
	public boolean logIn(String username, String password) {
		
		return true;
		
	}
	
	
	public boolean signUp(String username, String password) {
		
		return true;
	}
	
	
	public boolean signOut(User u) {
		
		return true;
	}
	
	public void addRecord() {
		
		
	}
	
	public void modifyRecord() {
		
	}
	
	public void deleteRecord() {
		
		
	}
	
}
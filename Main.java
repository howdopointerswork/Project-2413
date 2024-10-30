package project2413;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;



public class Main{
	
	public static void main(String[] args) {
		
		
		User u = new User(1, "User", "Password");
		
		System.out.println("ID: " + u.getID() + "; Username: " + u.getUsername());
		
		System.out.println("Password changed: " + u.changePassword("new"));
		
		System.out.println();
		
		//Database
		
		
		HealthSystem hs = new HealthSystem();
		
		if (hs.db_open()) {
			
			System.out.println("Database opened");
			
			hs.setQuery("SELECT name FROM sqlite_master WHERE type='table';");
			hs.testQuery();
			
		}
		
	
		
	}
}
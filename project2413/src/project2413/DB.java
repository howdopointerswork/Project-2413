package project2413;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class DB{
	
	public static void main(String[] args) {
		
		
		System.out.println("Hello World");
		
		String link = "jdbc:sqlite:Test.db";
		
		try (Connection conn = DriverManager.getConnection(link)){
			
			if (conn != null) {
				
				Statement s = conn.createStatement();
				
				String createTable = "CREATE TABLE IF NOT EXISTS Demo (" 
						+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
						+ "name TEST NOT NULL);";
				s.execute(createTable);
				
				//String insert = "INSERT INTO Demo (name) VALUES ('John')";
				//s.execute(insert);
				
				String get = "SELECT * FROM Demo;";
				ResultSet rs = s.executeQuery(get);
				System.out.println(rs.getInt("id"));
				System.out.println(rs.getString("name"));
						
			}
		}
		catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
		
		
	}
	
}
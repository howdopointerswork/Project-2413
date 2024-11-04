package project2413;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.SQLException;




public class Main{
	
	public static void main(String[] args) {
		
	
		//Create sample Test here
		//Date curr = new Date(System.currentTimeMillis());
		
		//Create exam out of database exam
		//This one just for debugging
		Exam ct = new CardiovascularTest("31-10-2024", "Cardiovascular Test", 4);
		//Create Exam using addResult here
		
		
		//log in and sign up / authenticating should be called here
		//Create user objects based on existing data
		//For example, from the below code, we created a user
		//With Username: User, Password: Test, ID: 1
		//So we would take the elements on lines 62-64 and create a User instance
		//from it
		
		/*System.out.println("ID: " + u.getID() + "; Username: " + u.getUsername());
		
		System.out.println("Password changed: " + u.changePassword("new"));
		
		System.out.println();
		
		System.out.println("Test ID: " + ct.getID());*/
		
		//Database
		
		
		HealthSystem hs = new HealthSystem();
		
		Scanner scan = new Scanner(System.in);
		
		int choice=-1;
		String username;
		String password;
		
		
		while(hs.db_open() || choice != 0) {
			//0 to quit
			
			System.out.println("Welcome to the Health Monitoring System");
			System.out.println("1. Log In");
			System.out.println("2. Sign Up");
			
			
			choice = scan.nextInt();
			scan.nextLine();
			User current;
			
			switch(choice) {
			
			case 1:
				
				System.out.print("Enter username: ");
				//user name text field use here
				username = scan.nextLine();
				
				System.out.println();
				//pass word text field here
				System.out.print("Enter password: ");
				
				password = scan.nextLine();
				
				System.out.println();
				
				current = new User(hs.logIn(username, password), username, password);
				
				if(current.getID() != 0) {
					
					hs.changeAuthenticate();
					System.out.println();
					System.out.println("Signed in as: " + current.getUsername());
				}
				//for loop for checking users here
				break;
				
			case 2:
				
				
				System.out.print("Enter username: ");
				//user name text field use here
				username = scan.nextLine();
				
				System.out.println();
				//pass word text field here
				System.out.print("Enter password: ");
				
				password = scan.nextLine();
				
				System.out.println();
				
				current = hs.signUp(username, password);
				if(current.getID() == 2) {
					
					System.out.println("Signed up successfully");
					
				}
				break;
			
			}
			
			System.out.println("What would you like to do?");
			
			
			//hs.setQuery("INSERT INTO Exam_Results (Exam_ID, User_ID, Test_ID, Date, Status) VALUES (?, ?, ?, ?, ?);");
			
			
			//hs.exQuery();
			
			
			
			
			//table names for debugging
			/*hs.executeQuery();
			
			//column names for debugging
			System.out.println();
			System.out.println("User table:");
			hs.executeQuery("PRAGMA table_info(User);");
			
			System.out.println();
			System.out.println("Exam table:");
			hs.executeQuery("PRAGMA table_info(Exam_Results);");
			
			System.out.println();
			System.out.println("Test table:");
			hs.executeQuery("PRAGMA table_info(Test);");*/
			
			
			//Add user to try
			
			//hs.executeQuery("INSERT INTO User VALUES ('" + u.getID() + "', '" + u.getUsername() + "', '" + u.getPassword() + "');");
			//Printing user info
			/*hs.executeQuery("SELECT * FROM User;");
			try {
				while(hs.rs.next()) {
					
					System.out.println(hs.rs.getInt("User_ID"));
					System.out.println(hs.rs.getString("Username"));
					System.out.println(hs.rs.getString("Password"));
					
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}*/
			
			
			System.out.println();
			
		}
		scan.close();
	
	
		
	}
}
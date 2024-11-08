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
import java.util.ArrayList;



public class Main{
	
	public static void main(String[] args) {
		
		
		HealthSystem hs = new HealthSystem(); //health system class object
		
		Monitor mon = new Monitor();
		
		String username;
		
		String password;
		
		int choice = -1; //for loop
		
		hs.db_open(); //open database and initialize connection variables
		
		while(choice != 0)	{ //main loop
			
			 //display logged out menu (replace with GUI)
			
			switch(hs.loggedOutMenu()) {
			
			case 1:
				
				System.out.print("Enter username: ");
				//user name text field use here
				username = hs.scan.nextLine();
				
				System.out.println();
				//pass word text field here
				System.out.print("Enter password: ");
				
				password = hs.scan.nextLine();
				
				System.out.println();
				
				hs.current = new User(hs.logIn(username, password), username, password);
				
				if(hs.getAuthenticated()) {
					
					System.out.println();
					System.out.println("Signed in as: " + hs.current.getUsername());
				}
				
				//for loop for checking users here
				break;
				
			case 2:
				
				
				System.out.print("Enter username: ");
				//user name text field use here
				username = hs.scan.nextLine();
				
				System.out.println();
				//pass word text field here
				System.out.print("Enter password: ");
				
				password = hs.scan.nextLine();
				
				System.out.println();
				
				hs.current = hs.signUp(username, password);
				//change hard code
				if(hs.getAuthenticated()) {
					
					System.out.println("Signed up successfully");
					
				}
				break;
			
			}
			
			if(hs.getAuthenticated()) { //check if user matches credentials in db
				int option = hs.loggedInMenu();
		
				
				switch(option) {
				
				case 1:
					
					//Add Result
					System.out.println("How many exams?");
					int exams = hs.scan.nextInt();
					hs.scan.nextLine();
					
					for(int i=0; i<exams; i++) {
						
						System.out.println("Enter date:");
						String date = hs.scan.nextLine();
						System.out.println("Enter category:");
						String category = hs.scan.nextLine();
						System.out.println("Enter status:");
						int status = hs.scan.nextInt();
						hs.scan.nextLine();
						
						Exam e = new Exam(date, hs.getExamID(), category, status, hs.current.getID());
						
						hs.addResult(e.enterResults(hs));
						
						
					}
				
					break;
					
				
				case 2:
					hs.editResult();
					//Edit Result
					break;
					
					
				case 3:
					//Delete Result
					hs.deleteResult();
					break;
					
				
				case 4:
					
					//Monitor settings
					//allow for setting up of monitor before use
					//and add a function to monitor based on category
					mon.scanResults(hs);
					if(mon.compareSize() >= 3) {
					
						mon.checkAlertTriggered(hs, "Test");
						
					}
					
					break;
					
				case 5:
					
					//activity management here
					break;
					
				case 6:
					
					hs.signOut(hs.current);
					break;
					
				default: 
					
					hs.loggedInMenu();
				
				
				}
			}
			
			System.out.println();
			
		}

	
	
		
	}
}
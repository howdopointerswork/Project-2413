package project2413;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.ArrayList;



public class HealthSystem{
	
	private User currentUser;
	
	private String version;
	
	private String url; //for connecting to db using conn 
	
	private String query; //for writing sql queries
	
	private Connection conn; //for creating statements
	
	private Statement exQ; //for executing queries
	
	private PreparedStatement pexQ;
	
	private DatabaseMetaData schema;
	
	public ResultSet rs; //for executing SELECT queries
	
	public User current;
	
	public Scanner scan;
	
	private boolean isAuthenticated;
	
	
	//read and write
	private int examID;
	
	
	//read and write
	private int userID;
	
	private ArrayList<Exam> results;

	
	
	
	//add args below (exam, db, etc.)
	
	public HealthSystem() {
		
		//change when adding and deleting
		this.examID = 1;
		
		this.userID = 1;
		
		//test db for now
		this.url = "jdbc:sqlite:DatabaseTest.db";
		
		this.isAuthenticated = false;
		
		this.results = new ArrayList<Exam>();
		
		this.scan = new Scanner(System.in);
	}
	
	
	
	
	

	
	
	
	public boolean db_open() { 
	
		
		try {
			
			this.conn = DriverManager.getConnection(this.url);
			this.exQ = this.conn.createStatement();
			this.exQ.execute("PRAGMA journal_mode=WAL;");
			this.schema = this.conn.getMetaData();
		
		} catch (SQLException e) {

			System.out.println(e.getMessage());
			
		}
				
		
		return (this.conn != null ? true: false);
					
			
	}
	
	
	
	
	
	
	public void runQuery(String query, boolean select) {
		
	if(select == false) {
		
		try {
			
			
			this.exQ.execute(query);
			
			//this.rs == null ? 
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	else {
		
		try {
			
			
			this.rs = this.exQ.executeQuery(query);
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}
		
	}
	


	

		
	
	public boolean addResult(Exam result) {
		
		//also give user option to cancel 
		
		this.results.add(result);
		++this.examID;
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ld = LocalDate.parse(result.getDate(), format);
		
		Date examDate = Date.valueOf(ld);
		
		System.out.println("Enter the status of the result");
		int status = this.scan.nextInt();
		this.scan.nextLine();
		
		
		this.runQuery("INSERT INTO Exam_Results VALUES(" + this.examID + ", " + this.current.getID() + ", " + result.getID() + ", " + examDate + ", " + status + ");", false);
		System.out.println("Successfully added Exam");
		//read and write
		
		//confirm insertion
		return true;
	}
	
	
	
	
	
	public void editResult() {
		
		ArrayList<String> arr = new ArrayList<String>();
		//do multiple edits at once later
		 System.out.println("Which exam result?");
		 runQuery("SELECT * FROM Exam_Results;", true);
		 
		 try {
			
			 while(this.rs.next()) {
				 
				System.out.println(this.rs.getInt("Exam_ID"));
				 
			 }
		
		 
			int whichID = this.scan.nextInt();
			this.scan.nextLine();
		 
			System.out.println("Which column?");
			this.rs = schema.getColumns(null, null, "Exam_Results", null);
		
	
			while(this.rs.next()) {
				
				System.out.println(this.rs.getString("COLUMN_NAME"));
				arr.add(this.rs.getString("COLUMN_NAME"));
				
				
			}
				
			int whichCol = this.scan.nextInt();
			
			
			System.out.println("Change it to:");
			
			//change later
			//for debugging input
			if(whichCol == 4) {
				
				String date = this.scan.nextLine();
				
				
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate ld = LocalDate.parse(date, format);
				
				Date examDate = Date.valueOf(ld);
				
				runQuery("UPDATE Exam_Results SET " + arr.get(whichCol-1) + "=" + examDate + " WHERE User_ID=" + this.current.getID() + ";", false);
			}
			else {
				
				
				int col = this.scan.nextInt();
				this.scan.nextLine();
				
				runQuery("UPDATE Exam_Results SET " + arr.get(whichCol-1) + "=" + col + ";", false);
		
			}
			this.scan.nextLine();
			System.out.println("Column updated");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//check type here and get according scan input
		//execute query here
		
		
		 
		
	}
	
	
	
	
	
	public void deleteResult() {
		
		
		System.out.println("Which one?");
		this.runQuery("SELECT * FROM Exam_Results;", true);
		int del = this.scan.nextInt();
		this.scan.nextLine();
		
		try {
			
			while(this.rs.next()) {
				
				if(this.rs.getInt("Exam_ID") == del) {
					
					this.runQuery("DELETE FROM Exam_Results WHERE Exam_ID="+del, false);
					System.out.println("Successfully deleted");
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		--this.examID;
		
		
		
	}
	
	
	
	
	public Exam findResult(int id) {
		
		Exam e = new Exam("28/10/24", 0, "Blood Test", 0, 0);
		
		return e;
		
	}
	
	
	
	
	
	public int logIn(String username, String password) {
		
		//if User exists, create User object from database
		
		//read data from database
		
		try {
		
			this.runQuery("SELECT * FROM User;", true);
			
			while(this.rs.next()) {
				
				if(this.rs.getString("Username").equals(username) && this.rs.getString("Password").equals(password)) {
						
					
					System.out.println("Log in successful");
					this.isAuthenticated = true;
					return this.rs.getInt("User_ID");
						
				}
				
			
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("Login failed");
		return 0;
	}
	
	
	
	
	
	
	
	public User signUp(String username, String password) {
		
		//add User record to database if it does not exist
		//And create User object from the database
		
		//add confirm before add
		//write to and load from file
		this.userID++;
		
		User signup = new User(this.userID, username, password);
		
		System.out.println("Enter First Name:");
		
		String fname = this.scan.nextLine();
		
		System.out.println("Enter Last Name:");
		
		String lname = this.scan.nextLine();
		
		System.out.println("Enter DoB:");
		
		String dob = this.scan.nextLine();
		
		System.out.println("Enter Sex:");
		
		String sex = this.scan.nextLine();
		
		System.out.println("Enter Weight:");
		
		float w = scan.nextFloat();
		this.scan.nextLine();
		
		System.out.println("Enter Height:");
		
		float h = scan.nextFloat(); 
		this.scan.nextLine();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ld = LocalDate.parse(dob, format);
		
		Date dobDate = Date.valueOf(ld);
			
		//confirm entry or cancel here
		
		try {			
			this.conn.setAutoCommit(false);
			this.runQuery("INSERT INTO User (User_ID, Username, Password) VALUES(" + this.userID + ", '" + username + "', '" + password + "');", false);
			this.runQuery("INSERT INTO user_info (User_ID, Fname, Lname, DoB, Sex, Weight, Height) VALUES(" + this.userID + ", '" + fname + "', '" + lname + "', '" + dobDate + "', '" + sex + "', " + w + ", " + h + ");", false);
			this.conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		this.isAuthenticated = true;
		
		return signup;
	}
	
	
	
	
	
	public boolean signOut(User u) {
		//write data here
		//including ArrayList
		//then clear current
		this.isAuthenticated = false;
		return true;
	}
	
	
	
	//for activities
	//when monitor gives the alert
	//create the activity through GUI/console
	public void addRecord() {
		
		
	}
	
	
	
	
	public void modifyRecord() {
		
	}
	
	
	
	
	public void deleteRecord() {
		
		
	}
	
	
	
	
	public int getExamID() {
		
		return this.examID;
	}
	
	
	
	public boolean getAuthenticated() {
		
		return this.isAuthenticated;
		
	}
	

	
	
	public int loggedInMenu() {
		
		
		//put this part as function
		System.out.println();
		System.out.println("Select an option:");
		System.out.println("1. Add Result");
		System.out.println("2. Edit Result");
		System.out.println("3. Delete Result");
		System.out.println("4. Monitor Settings");
		System.out.println("5. Sign Out");

		//Activity
		//Report
		
		int choice = this.scan.nextInt();
		this.scan.nextLine();
		
		return choice;
		
	}
	
	
	public int loggedOutMenu() {
		
	
		String username;
		String password;
	
		//0 to quit
		//put this part as function as well
		System.out.println("Welcome to the Health Monitoring System");
		System.out.println("1. Log In");
		System.out.println("2. Sign Up");
		
		
		int choice = this.scan.nextInt();
		this.scan.nextLine();
		 
		return choice;
		
		
	}
	
	
	
	
}
	

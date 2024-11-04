package project2413;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Scanner;


public class HealthSystem{
	
	private User currentUser;
	
	private String version;
	
	private String url; //for connecting to db using conn 
	
	private String query; //for writing sql queries
	
	private Connection conn; //for creating statements
	
	private Statement exQ; //for executing queries
	
	private PreparedStatement pexQ;
	
	public ResultSet rs; //for executing SELECT queries
	
	private boolean isAuthenticated;
	
	private int examID;
	
	private int userID;
	
	
	
	
	//add args below (exam, db, etc.)
	
	public HealthSystem() {
		
		//change when adding and deleting
		this.examID = 0;
		
		this.userID = 1;
		
		this.url = "jdbc:sqlite:Database.db";
		
	}
	

	
	
	
	public boolean db_open() { 
	
		
		try {
			
			this.conn = DriverManager.getConnection(this.url);
			this.exQ = this.conn.createStatement();
			this.exQ.execute("PRAGMA journal_mode=WAL;");
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	else {
		
		try {
			
			
			this.rs = exQ.executeQuery(query);
			
			//this.rs == null ? 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	}
	

	
	

		
	
	public boolean addResult(Exam result, User u) {
		
		
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ld = LocalDate.parse(result.getDate(), format);
		
		Date examDate = Date.valueOf(ld);
		
		
		this.runQuery("INSERT INTO Exam_Results VALUES(" + this.examID + ", " + u.getID() + ", " + result.getID() + ", '" + examDate + "', " + 0 + ");", false);
		this.userID++;

		return true;
	}
	
	
	
	public void editResult(Exam result, int id) {
		
		
	}
	
	
	public void deleteResult(Exam result, int id) {
		
		this.examID--;
		
	}
	
	
	public Exam findResult(int id) {
		
		Exam e = new Exam("28/10/24", "Liver Function", 0);
		
		return e;
		
	}
	
	
	public int logIn(String username, String password) {
		
		//if User exists, create User object from database
		
		
		
		try {
			this.runQuery("SELECT * FROM User", true);
			
			while(this.rs.next()) {
				
				if(this.rs.getString("Username").equals(username) && this.rs.getString("Password").equals(password)) {
						

					System.out.println("Log in successful");
					return this.rs.getInt("User_ID");
						
				}
				
			
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("Login unsuccessful");
		return 0;
	}
	
	
	public User signUp(String username, String password) {
		
		//add User record to database if it does not exist
		//And create User object from the database
		
		this.userID++;
		
		User signup = new User(this.userID, username, password);
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter First Name:");
		
		String fname = scan.nextLine();
		
		System.out.println("Enter Last Name:");
		
		String lname = scan.nextLine();
		
		System.out.println("Enter DoB:");
		
		String dob = scan.nextLine();
		
		System.out.println("Enter Sex:");
		
		String sex = scan.nextLine();
		
		System.out.println("Enter Weight:");
		
		float w = scan.nextFloat();
		scan.nextLine();
		
		System.out.println("Enter Height:");
		
		float h = scan.nextFloat();
	
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ld = LocalDate.parse(dob, format);
		
		Date dobDate = Date.valueOf(ld);
		
		try {			
			this.conn.setAutoCommit(false);
			this.runQuery("INSERT INTO User (User_ID, Username, Password) VALUES(" + this.userID + ", '" + username + "', '" + password + "');", false);
			this.runQuery("INSERT INTO user_info (User_ID, Fname, Lname, DoB, Sex, Weight, Height) VALUES(" + this.userID + ", '" + fname + "', '" + lname + "', '" + dobDate + "', '" + sex + "', " + w + ", " + h + ");", false);
			this.conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		return signup;
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
	
	
	public int getExamID() {
		
		return this.examID;
	}
	
	public boolean changeAuthenticate() {
		
		this.isAuthenticated = (this.isAuthenticated = false ? true : false);
		
		return this.isAuthenticated;
	}
	
	public boolean getAuthenticated() {
		
		return this.isAuthenticated;
		
	}
}
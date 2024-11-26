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
import java.io.File;
import java.io.IOException;

import org.mindrot.jbcrypt.*;


public class HealthSystem{
	
	//
	
	private String version;
	
	private String url; //for connecting to db using conn 
	
	private String query; //for writing sql queries
	
	
	
	private Connection conn; //for creating statements
	
	private Statement exQ; //for executing queries
	
	
	//
	
	private PreparedStatement pexQ;
	
	private DatabaseMetaData schema;
	
	
	
	public ResultSet rs; //returns results from SELECT queries as Strings 
	
	
	
	public User current;
	
	public Exam result;
	
	public Loader ldr;
	
	

	
	
	
	public Scanner scan;
	
	private boolean isAuthenticated;
	
	
	//read and write
	private int examID;
	
	
	//read and write
	private int userID;
	
	public int catID;
	
	//for saving and loading results
	public ArrayList<String> categories;

	
	
	
	//add args below (exam, db, etc.)
	
	public HealthSystem() {
		
		//change when adding and deleting
		this.examID = 0;
		
		//default save and load
		this.userID = 0;
		
		this.catID = 0;
	
		
		//test db for now
		//this.url = "jdbc:sqlite:Database_REV3.db";
		this.url = "jdbc:sqlite:Database.db";
		this.isAuthenticated = false;
	
		
		this.categories = new ArrayList<String>();
		
		this.scan = new Scanner(System.in);
		
		this.result = new Exam();
		
		this.current = new User();
		
		this.ldr = new Loader();
		
		this.db_open();
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
	
	
	public void dbSwap(boolean tf){
	
		if(tf){
	
			this.url = "jdbc:sqlite:Database.db";
			
		}	
		else{
			System.out.println("Switched");
			this.url = "jdbc:sqlite:data.db";
			
			}
	
	
		this.db_open();
	
		}
	
	
	
	//put into 1 fun with args
	public void dataDb() {
		
	//	try {
			//this.conn.close();
			this.url = "jdbc:sqlite:data.db";
			System.out.println(db_open());
			System.out.println("Opened Data");
		//} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
		
	}
	
	public void databaseDb() {
		
	//	try {
	//		this.conn.close();
			this.url = "jdbc:sqlite:Database.db";
			System.out.println(db_open());
			System.out.println("Opened Database");
	//	} catch (SQLException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
		
	}
	
	
	
	public void runQueryP(String query, int examid, int userid, int testid, String name, int status) {
		
		
		try {
			pexQ = this.conn.prepareStatement(query);
			pexQ.setInt(1, examid);
			pexQ.setInt(2, userid);
			pexQ.setInt(3, testid);
			pexQ.setString(4, name);
			pexQ.setInt(5,  status);
			
			
			pexQ.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	public void runQuery(String query, boolean select) {
		
	if(select == false) {
		
		try {
			
			
			this.exQ.execute(query);
			
		
			
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
	


	
	public int countRows(String table) {
		
		int count = 0;
		
		try {
			
			this.runQuery("SELECT * FROM " + table + ";", true);
			
			while(this.rs.next()) {
				
				++count;
			}
			
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		return count;
		
	}

		
	
	public boolean addResult(Exam result, String table, boolean tf) {
		
		//also give user option to cancel 
		
		
		//this.results.add(result);
		if(tf) {
			++this.examID;
		}
		
	
		
		this.categories.add(result.getCategory());
	
		
		
		this.runQuery("INSERT INTO " + table + " VALUES(" + this.examID + ", " + this.current.getID() + ", " + result.getID() + ", '" + result.getDate() + "', " + result.getStatus() + ");", false);
		
		
		
		//this.results.add(result);
		System.out.println("Successfully added Exam");
		//read and write
		
		//confirm insertion
		return true;
	}
	
	
	
	
	
	public void editResult() {
		
		//edit categories?
		
		this.dbSwap(true);
		
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
				
				runQuery("UPDATE Exam_Results SET " + arr.get(whichCol-1) + "='" + examDate + "' WHERE User_ID=" + this.current.getID() + ";", false);
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
		
		//last for here
		
		//Delete subcategories as well
		
		//delete through other filters too
		System.out.println("Which one?");
		this.runQuery("SELECT * FROM Exam_Results;", true);
		
		int del = this.scan.nextInt();
		int id;
		
		this.scan.nextLine();
		
		
		
		try {
			
			while(this.rs.next()) {
				
				if(this.rs.getInt("Exam_ID") == del && this.rs.getInt("User_ID") == this.current.getID()) {
					
					this.runQuery("DELETE FROM Exam_Results WHERE Exam_ID="+del, false);
					System.out.println("Successfully deleted");
					this.dbSwap(false);
					this.runQuery("DELETE FROM categories WHERE EXAM_ID="+del, false);
					this.dbSwap(true);
				}
				
			
				//handle subcategory deletion here
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//last = examid
		

		
	}
	
	
	
	//search
	public Exam findResult(int id) {
		
		Exam ex = new Exam();
		
		return ex;
		
	}
	
	
	
	
	
	public boolean logIn(String username, String password, Monitor mon) {
		
		//if User exists, create User object from database
		
		//read data from database
		
		//read exam id, inputted rows (so when a new row is entered, it must be added to the read file)
		//also monitor's 2 IDs
		
		//also activities arraylist for monitor
		
		
		
		try {
			this.dbSwap(true);
			this.runQuery("SELECT * FROM User;", true);
			
			while(this.rs.next()) {
				
				if(this.rs.getString("Username").equals(username) && BCrypt.checkpw(password, this.rs.getString("Password"))) {
						
			
					System.out.println("Log in successful");
					this.current.setID(rs.getInt("User_ID"));
					this.current.setUsername(this.rs.getString("Username"));
					//this.current.setPassword(this.rs.getString("Password"));
				
					this.ldr.loadVars(this, mon);
				
					return true;
						
				}
				
			
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("Login failed");
		return false;
	}
	
	
	
	
	
	
	
	public void signUp(String username, String password, String fname, String lname, String dob, String sex, float w, float h, Monitor mon) {
		
		
		this.setExamID(0);
		this.databaseDb();
		this.runQuery("SELECT * FROM User;", true);
		int userid = 0;
		
		try {
			
			while(this.rs.next()) {
				
				userid = this.rs.getInt("User_ID");
			}
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
	
		this.userID = userid+1;
		
		
		//check here
		
		//just set id, username, and password 
		
		String pwHashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
		
		this.current = new User(this.userID, username, pwHashed);
		
	
		
		System.out.println("dob: " + dob);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ld = LocalDate.parse(dob, format);
		
		Date dobDate = Date.valueOf(ld);
			
		//confirm entry or cancel here
		
		
		//add text file for user here
		
		try {			
			this.conn.setAutoCommit(false);
			this.runQuery("INSERT INTO User (User_ID, Username, Password) VALUES(" + this.userID + ", '" + username + "', '" + pwHashed + "');", false);
			this.runQuery("INSERT INTO user_info (User_ID, Fname, Lname, DoB, Sex, Weight, Height) VALUES(" + this.userID + ", '" + fname + "', '" + lname + "', '" + dobDate + "', '" + sex + "', " + w + ", " + h + ");", false);
			this.conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		this.ldr.saveVars(this, mon);
		this.isAuthenticated = true;
		
		
	}
	
	
	
	
	
	public void signOut(Monitor mon) {
		//write data here
		//clear db and stuff
		System.out.println("Sign out");
		this.ldr.saveVars(this, mon);
		this.isAuthenticated = false;

	}
	
	
	
	//for activities
	//when monitor gives the alert
	//create the activity through GUI/console
	
	
	
	
	public int getExamID() {
		
		return this.examID;
	}
	
	
	public void setExamID(int newID) {
		
		this.examID = newID;
	}
	
	
	
	public boolean getAuthenticated() {
		
		return this.isAuthenticated;
		
	}
	
	public int getUserID() {
		
		
		return this.userID;
	}
	
	
	
public int matchCategory(String cat) {
		
	
		switch(cat.toLowerCase()) {
		

		case "blood":
			
			return 1;
		

		case "cardiovascular":
		
			
			return 2;
			
		
		case "gastrointestinal":
			
			return 3;
		

		case "respiratory":
			
			return 4;
		
		case "ultrasound":		
			
			return 5;
			
		case "x-ray":
			
			return 6;
			
			
		case "ct scan":	
			
			return 7;
			
		case "ecg":	
	
			
			return 8;
	
		
		
		}
		
		return 0;
		
	}


public String matchTestID(int testid) {
	
	switch(testid) {
	

	case 1:
		
		return "Blood";
	

	case 2:
		return "Cardiovascular";
	
		
	case 3:
		return "Gastrointestinal";
		
		
	case 4:
		return "Respiratory";
		
		
	case 5:
		return "Ultrasound";	
		
		
	case 6:	
		return "X-Ray";
		
		
	case 7:
		return "CT Scan";	
		
	case 8:
		return "ECG";

	
	
	}
	
	return "";
	
}
	

public void printResultSubs() {
	
	for(String val : this.result.subcategories.values()) {
		
		System.out.println(val);
	}
	
}


public void printInfo() {
	
	System.out.println("Exam ID: " + this.getExamID());
	System.out.println("User ID: " + this.current.getID());
	System.out.println("Username: " + this.current.getUsername());
	
}


	
}
	

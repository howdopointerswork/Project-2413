package project2413;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.io.File;

public class Loader{
	
	private BufferedReader reader;
	
	private BufferedWriter writer;
	
	
	

	
	
	//Write what needs to be saved here
	//1 - ExamID
	//2 - AlertID
	//3 - Inserted rows in Exam Results , function
	//4 - Username
	//5 - Password
	//6 - UserID
	//7 - Activities to load by number values , function
	//8 - Reports , function
	
	//do encryption here
	
	
	
	public void saveVars(HealthSystem hs, Monitor mon) {
		
		//hs.databaseDb();
		
		
		hs.dbSwap(true);
		
		hs.runQuery("SELECT * FROM Exam_Results;", true);
		
		try {
			
			while(hs.rs.next()) {
				
				if(hs.rs.getInt("User_ID") == hs.current.getID()) {
					
					//fix date
					
					Exam ex = new Exam(hs.rs.getString("Date"), hs.rs.getInt("Test_ID"), hs.result.getCategory(), hs.rs.getInt("Status"), hs.current.getID());
					System.out.println(hs.rs.getString("Date"));
					hs.setExamID(hs.rs.getInt("Exam_ID"));
					//hs.dataDb();
					hs.dbSwap(false);
					hs.addResult(ex, "exams", false);
					//hs.databaseDb();
					hs.dbSwap(true);
			
					
					
				}
				
				
			}
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		hs.runQuery("DELETE FROM Exam_Results;", false);
		
	}
	
	
	
	public void loadVars(HealthSystem hs, Monitor mon) {
		
		//hs.dataDb();
		hs.dbSwap(false);
	

		//load exam ID
		hs.runQuery("SELECT * FROM categories;", true);
		
		try {
			
			while(hs.rs.next()) {
				
				hs.catID = hs.rs.getInt("category_ID");
				
			}
			
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		++hs.catID;
		
		
		hs.runQuery("SELECT * FROM exams;", true);
		
		try {
			while(hs.rs.next()) {
				
				if(hs.rs.getInt("User_ID") == hs.current.getID()) {
					
					Exam ex = new Exam(hs.rs.getString("Date"), hs.rs.getInt("Test_ID"), hs.matchTestID(hs.rs.getInt("Test_ID")), hs.rs.getInt("Status"), hs.current.getID());
				//	ex.setCategory(hs.matchTestID(ex.getID()));
					//hs.databaseDb();
					hs.dbSwap(true);
					hs.setExamID(hs.rs.getInt("Exam_ID"));
					hs.addResult(ex, "Exam_Results", false);
					//hs.dataDb();
					hs.dbSwap(false);
					System.out.println(hs.rs.getInt("Exam_ID"));
					
					
				}
				
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hs.runQuery("DELETE FROM exams WHERE User_ID=" + hs.current.getID(), false);
		hs.dbSwap(true);
		//hs.databaseDb();
		
	}
	
	
	

	
	//save db data
	
	
	
	//load db data
	
}
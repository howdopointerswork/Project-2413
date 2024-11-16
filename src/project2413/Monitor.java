package project2413;

import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collections;
import java.util.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
//measure dates here
//compare earliest date
//if 3 months, change monitor boolean to true
//then create alert
//log the alert in user folder
//also save settings

public class Monitor{
	
	
	private int id;
	
	private int testAvg;
	
	private int alertID;
	
	private int activityID;
	
	private boolean alertStatus;
	
	private int abnormal;
	
	private String function;
	
	private Report rep;

	
	private ArrayList<Integer> compare;
	
	private ArrayList<Activity> activities; //can also make private with add/remove/access methods
	
	private ArrayList<Exam> exams;
	
	public Monitor() {
		
	
		//read and write
		this.alertID = 0;
		
		this.activityID = 0;
		
		this.activities = new ArrayList<Activity>();
		
		this.compare = new ArrayList<Integer>();
	}
	
	
	public int getAbnormal() {
		
		return this.abnormal;
			
	}
	
	public int compareSize() {
		
		return this.compare.size();
	}
	
	public int activitiesSize() {
		
		return this.activities.size();
	}
	
	
	public String setFunction(String set) {
		
		
		return this.function = set;
	}
	
	
	public void popUpAlert(Alert a, HealthSystem hs) {
		
		
		//do pop up stuff here
		//add activities here
		
		this.compare.clear();
		
		
		System.out.println("Alert " + a.getID());
		System.out.println(a.getDate());
		System.out.println(a.getReason());
		
		int[] average = new int[4];
		String[] prompts = {"Food intake", "Rest quality", "Emotion", "Medication"}; 
		
		for(int i=0; i<4; i++) {
		
			System.out.println(prompts[i] + ":");
			average[i] = hs.scan.nextInt();
			hs.scan.nextLine(); 
			
		}

		
		++this.activityID;
		
		Activity act = new Activity(a.getDate(), this.activityID, average[0], average[1], average[2], average[3]);
		this.activities.add(act);
		this.sortActivities();
		System.out.println("Added activitity");
		//add record
		//hs.addRecord(act);
	}
	
	
	
	public void popUpReminder() {
		
		
	}
	
	
	
	public boolean checkAlertTriggered(HealthSystem hs, String function) {
		
		//compare here
		//use category to match function in groups
		
		//use exams instead
		
		//check for same types of results
		//then make a string category to pass as reason for alert
		//pass the test id?
		String[] categories = {"Blood", "Cardiovascular", "Gastrointestinal", "Respiratory"};
		int index = 0;
		
		while(index < 4) {
			
			for(int i=0; i<this.exams.size(); i++) {
				
				//match the category with the main four for now
				if(this.exams.get(i).getCategory() == categories[index]) {
					
					if(this.exams.get(i).getStatus() < 50) {
						
						++this.abnormal;
						
					}
					else {
						
						--this.abnormal;
					}
					
					
				}
				
			}
			
			if(this.abnormal >= 3) {
				
				
				//create alert here
				
				//get local date for second arg
				Alert a = new Alert(this.alertID, "05-11-2024", categories[index], true);
				this.abnormal = 0;
				this.popUpAlert(a, hs);
			}
			else {
				
				this.abnormal = 0;
				++index;
			}
			
			
			
		}
		
		
		//change to 3 consecutive
	
		
		return this.alertStatus;
		
	}
	
	
	
	public Exam getRecentResult(HealthSystem hs) {
	
		int examid=0;
		int testid=0;
		int userid=0;
		int status=0;
		Date date;
		String textdate = "";
		
		Exam ex = null;
		
		
		//test id turns into category
		//date turns into string
		
		try {
		
			hs.runQuery("SELECT * FROM Exam_Results", true);
			while(hs.rs.next()) {
			
				examid = hs.rs.getInt("Exam_ID)");
				testid = hs.rs.getInt("Test_ID");
				userid = hs.rs.getInt("User_ID");
				status = hs.rs.getInt("Status");
				date = hs.rs.getDate("Date");
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				textdate = formatter.format(date);
				//date
				
			}
				
			
		}catch(SQLException e) {
		
			
			e.printStackTrace();
		}

		//temporary tests
		//will add rest with calculations based on sorted avgs
		switch(testid) {
		
		case 1: 
			ex = new BloodTest(textdate, examid, "Blood", status, userid);
			break;
		
		case 2:
			ex = new CardiovascularTest(textdate, examid, "Cardiovascular", status, userid);
			break;
			
		case 3:	
			ex = new GastrointestinalTest(textdate, examid, "Gastrointestinal", status, userid);
			break;
			
		case 4:
			ex = new RespiratoryTest(textdate, examid, "Respiratory", status, userid);
			break;
		
		}
			
		
		return ex;
	}
	
	
	
	public void scanResults(HealthSystem hs) {
		
		//use status for now
		//< 50
		int status=0;
		int examid=0;
		int userid=0;
		int testid=0;
		Date date;
		String textdate = "";
		
		
		Exam ex = null;
		

		try {
			//get past 3 months
			hs.runQuery("SELECT * FROM Exam_Results", true);
			
			while(hs.rs.next()) {
				
				examid = hs.rs.getInt("Exam_ID");
				userid = hs.rs.getInt("User_ID");
				testid = hs.rs.getInt("Test_ID");
				status = hs.rs.getInt("Status");
				date = hs.rs.getDate("Date");
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				textdate = formatter.format(date);
				

				//also check the test type/category
				//add abnormalities to array
				//rather than status, get the entire Exam result so we can check category
				//and still access status
				
				//this.exams.add(ex));	
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		switch(testid) {
		
		case 1: 
			ex = new BloodTest(textdate, examid, "Blood", status, userid);
			break;
		
		case 2:
			ex = new CardiovascularTest(textdate, examid, "Cardiovascular", status, userid);
			break;
			
		case 3:	
			ex = new GastrointestinalTest(textdate, examid, "Gastrointestinal", status, userid);
			break;
			
		case 4:
			ex = new RespiratoryTest(textdate, examid, "Respiratory", status, userid);
			break;
		
		}
			
		
		
		this.exams.add(ex);
		
		
	}
	
	
	
	public void sortActivities() {
		
		Collections.sort(this.activities);
		
		
	}
	
	
	public int matchCategory(String cat) {
		
		//add the rest too
		switch(cat) {
		
		case "Glucose":
		case "Calcium":
		case "Sodium":
			
			
			return 1;
		
		case "Blood Pressure Test":
		case "Heart Rate Test":
			
			return 2;
			
		
		case "pH urine":
		case "Stool":
		case "Liver enzymes":
		
			return 3;
		
		case "Lung Capacity":
		case "Rate of Flow":
			
			return 4;
	
		
		
		}
		
		return 0;
		
	}
	
	
	
		
		
		
		
}
	

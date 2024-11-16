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
		
		this.exams = new ArrayList<Exam>();
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
	
	
	public int getAlertID() {
		
		return this.alertID;
	}
	
	public void setAlertID(int newID) {
		
		this.alertID = newID;
	}
	
	
	public int getActivityID() {
		
		return this.activityID;
	}
	
	public void setActivityID(int newID) {
		
		this.activityID = newID;
	}
	
	
	public String setFunction(String set) {
		
		
		return this.function = set;
	}
	
	
	public void popUpAlert(Alert a, HealthSystem hs) {
		
		
		//do pop up stuff here
		//add activities here
		
		this.compare.clear();
		
		
		System.out.println("Alert #" + a.getID());
		System.out.println(a.getDate());
		System.out.println("Warning: HMS Found an issue related to: " + a.getReason());
		
		
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
	
			
			for(int i=0; i<this.exams.size(); i++) {
				
				//match the category with the main four for now
				
				//System.out.println(this.matchCategory(this.exams.get(i).getCategory()));
				//System.out.println(categories[index]);
				if(this.matchCategory(this.exams.get(i).getCategory()).equals(categories[index])) {
					
					if(this.exams.get(i).getStatus() < 50) {
						
						++this.abnormal;
						//System.out.println(this.abnormal);
						
					}
				
					
					if(this.abnormal >= 3) {
						
						
						//create alert here
						//System.out.println("Alert");
						//get local date for second arg
						Alert a = new Alert(this.alertID, "05-11-2024", categories[index], true);
						this.abnormal = 0;
						++index;
						this.popUpAlert(a, hs);
					}
				
					
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
			ex = new BloodTest(textdate, "Blood", status, userid);
			break;
		
		case 2:
			ex = new CardiovascularTest(textdate, "Cardiovascular", status, userid);
			break;
			
		case 3:	
			ex = new GastrointestinalTest(textdate, "Gastrointestinal", status, userid);
			break;
			
		case 4:
			ex = new RespiratoryTest(textdate, "Respiratory", status, userid);
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
		int index=0;
		Date date;
		String textdate = "";
		

		

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
	
				Exam ex = null;
				
				String result = hs.categories.get(index);
				++index;
				
				switch(testid) {
				
				case 1: 
					ex = new BloodTest(textdate, result,status, userid);
					this.exams.add(ex);
					break;
				
				case 2:
					ex = new CardiovascularTest(textdate, result, status, userid);
					this.exams.add(ex);
					break;
					
				case 3:	
					ex = new GastrointestinalTest(textdate, result, status, userid);
					this.exams.add(ex);
					break;
					
				case 4:
					ex = new RespiratoryTest(textdate, result, status, userid);
					this.exams.add(ex);
					break;
				}

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
		
		
		
	}
	
	
	
	public void sortActivities() {
		
		Collections.sort(this.activities);
		
		
	}
	
	
	public String matchCategory(String cat) {
		
		//add the rest too
		switch(cat) {
		
		case "Glucose":
		case "Calcium":
		case "Sodium":
			
			return "Blood";
		
		case "Blood Pressure Test":
		case "Heart Rate Test":
			
			return "Cardiovascular";
			
		
		case "pH urine":
		case "Stool":
		case "Liver enzymes":
		
			return "Gastrointestinal";
		
		case "Lung Capacity":
		case "Rate of Flow":
			
			return "Respiratory";
	
		
		
		}
		
		return "";
		
	}
	
	
	
		
		
		
		
}
	

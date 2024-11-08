package project2413;

import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collections;
import java.util.*;

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
	
	private boolean abnormal;
	
	private String function;

	
	private ArrayList<Integer> compare;
	
	private ArrayList<Activity> activities; //can also make private with add/remove/access methods
	
	
	
	public Monitor() {
		
	
		//read and write
		this.alertID = 0;
		
		this.activityID = 0;
		
		this.activities = new ArrayList<Activity>();
		
		this.compare = new ArrayList<Integer>();
	}
	
	
	public boolean getAbnormal() {
		
		return this.abnormal;
			
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
		//add record
		//hs.addRecord(act);
	}
	
	
	public void popUpReminder() {
		
		
	}
	
	
	
	public boolean checkAlertTriggered(HealthSystem hs, String function) {
		
		//compare here
		//use category to match function in groups
		
		for(int i=0; i<this.compare.size()-2; i++) {
			
			if(this.compare.get(i+2) < 50 && this.compare.get(i+1) < 50 && this.compare.get(i) < 50) {
				

				this.abnormal = true;
			}
			
			
		}
		
		
		//change to 3 consecutive
		if(this.abnormal) {
			
			
			//create alert here
			
			//get local date for second arg
			Alert a = new Alert(this.alertID, "05-11-2024", "Reason", true);
			this.abnormal = false;
			this.compare.clear();
			this.popUpAlert(a, hs);
		}
		
		return this.alertStatus;
		
	}
	
	
	
	public Exam getRecentResult(HealthSystem hs) {
	
		int examid = 0;
		int testid = 0;
		int userid = 0;
		int status = 0;
		String date = "";
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
				//date
				
			}
				
			
		}catch(SQLException e) {
		
			
			e.printStackTrace();
		}

		//temporary tests
		//will add rest with calculations based on sorted avgs
		switch(testid) {
		
		case 1: 
			ex = new BloodTest("07-11-2024", examid, "Blood", status, userid);
			break;
		
		case 2:
			ex = new CardiovascularTest("07-11-2024", examid, "Cardiovascular", status, userid);
			break;
			
		case 3:	
			ex = new GastrointestinalTest("07-11-2024", examid, "Gastrointestinal", status, userid);
			break;
			
		case 4:
			ex = new RespiratoryTest("07-11-2024", examid, "Respiratory", status, userid);
			break;
		
		}
			
		
		return ex;
	}
	
	
	
	public void scanResults(HealthSystem hs) {
		
		//use status for now
		//< 50

		try {
			//get past 3 months
			hs.runQuery("SELECT * FROM Exam_Results", true);
			
			while(hs.rs.next()) {
				
				//also check the test type/category
				//add abnormalities to array
				this.compare.add(hs.rs.getInt("Status"));	
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	
	public void sortActivities() {
		
		Collections.sort(this.activities);
		
		
	}
	
	
	
		
		
		
		
}
	

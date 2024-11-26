package project2413;

import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collections;
import java.util.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.*;
//measure dates here
//compare earliest date
//if 3 months, change monitor boolean to true
//then create alert
//log the alert in user folder
//also save settings

public class Monitor{
	
	
	
	private int alertID;
	
	private int activityID;
	
	private boolean alertStatus;
	
	public boolean reminderStatus;
	
	private int abnormal;
	
	public int cat;
	
	public String sub;
	
	public boolean onOff = false;
	
	public String lastMonth;
	
	private String lastDate; //for activities/reminder
	
	private String lastExamDate;
	
	private int abnormalID;
	
	private String lastAbnormalDate;
	
	private Report rep;
	
	private ArrayList<Integer> compare;
	
	private ArrayList<Activity> activities; //can also make private with add/remove/access methods
	
	private ArrayList<String> categories; 
	
	private ArrayList<String> dateParts; //0 is day, 1 is month, 2 is year
	
	
	
	public Monitor() {
		
	
		//read and write
		this.alertID = 0;
		
		this.activityID = 0;
		
		this.cat = 0;
		
		this.sub = "";
		
		this.activities = new ArrayList<Activity>();
		
		this.compare = new ArrayList<Integer>();

		this.categories = new ArrayList<String>();
		
		
		
		this.categories.add("Blood");
		this.categories.add("Cardiovascular");
		this.categories.add("Gastrointestinal");
		this.categories.add("Respiratory");
		this.categories.add("Ultrasound");
		this.categories.add("X-Ray"); 
		this.categories.add("CT Scan");
		this.categories.add("ECG");
		
		this.lastDate = "0000-01-01";
		
		this.lastExamDate = "0000-01-01";
		
		this.lastAbnormalDate = "0000-01-01";
		
		this.lastMonth = "";
		

		
		
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
	
	public boolean getAlertStatus() {
		
		return this.alertStatus;
	}
	
	
	public String getAbnormalCategory(HealthSystem hs, int n) {
		
		return hs.matchTestID(n);
	}
	
	
	public int getAbnormalID() {
		
		return this.abnormalID;
	}
	

	
	//add alert object?
	//move to HealthSystem
	public void addActivity(HealthSystem hs, int fi, int rq, int em, int med, String date, String comment) {
		
		hs.dbSwap(false);
		
		hs.runQuery("SELECT * FROM activities;", true);
		
		try {
			
			
			while(hs.rs.next()) {
				
				this.activityID = hs.rs.getInt("Alert_ID");
			}
				
				
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		//save/load activities here
		
		//do pop up stuff here
		//add activities here
		if(this.alertStatus) {
			
			System.out.println("Here");
			this.setLastDate(hs);
			this.abnormal = 0;
			hs.dbSwap(false);	
			hs.runQuery("INSERT INTO lastdate VALUES('" + this.lastDate + "');", false);
			
			hs.dbSwap(true);
			++this.activityID;
			this.alertStatus = false;
		}
	
		hs.dbSwap(false);
		++this.activityID;
		hs.runQuery("INSERT INTO activities VALUES(" + activityID + ", '" + date + "', " + hs.current.getID() + ", " + fi + ", " + rq + ", " + em + ", " + med + ", '" + comment + "');", false);
		hs.dbSwap(true);

		
	}
	

	

	
	
	
	public boolean popUpReminder(HealthSystem hs) {
		
		if(this.dateParts != null) {
		
			this.emptyDateParts();
			
		}	
		
		
		
		this.getLastDate(hs); // activity version
		
		this.reminderStatus = true;
		
		String[] local = getLocalDate().split("-");
		
		for(int i=0; i<this.dateParts.size(); i++) {
			
			local[i] = local[i].trim();
			this.dateParts.get(i).trim();
		}
		
		int localYear = Integer.parseInt(local[2]);
		int localMonth = Integer.parseInt(local[1]);
		int localDay = Integer.parseInt(local[0]);
	
		int year = Integer.parseInt(this.dateParts.get(0));
		int month = Integer.parseInt(this.dateParts.get(1));
		int day = Integer.parseInt(this.dateParts.get(2));
		
	
		//put limit here
		
		if(localYear == year && localDay >= day) {
		
			if(localMonth == month+3) { //wrap around, use %
				
				return true;
				
			}
			else {
				
				return false;
			}
		}
		
		return false;
		
	}
	
	
	

	
	
	
	public void checkAlertTriggered() {
		
	
			
			if(this.abnormal >= 3) {
				
				this.alertStatus = true;
			
				
			}
			else {
				
				this.alertStatus = false;
			}
			

		
	}
	
	
	
	
	
	
	
	public void scanResults(HealthSystem hs, int fi, int rq, int em, int med, String date) {
		
		//use status for now
		//< 50
		
		
		//load lastdate here
		try {
			hs.dbSwap(false);
			hs.runQuery("SELECT * FROM lastdate", true);
			
			while(hs.rs.next()) {
				//needs user id
				this.lastDate = hs.rs.getString("Date");
				System.out.println(this.lastDate);
			}
			System.out.println(lastDate);
					
					
			hs.dbSwap(true);
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		
		for(int i=0; i<this.categories.size(); i++) {
			
			this.abnormal = 0;
			
			try {
			
			//get past 3 months
				hs.runQuery("SELECT * FROM Exam_Results", true);
			
				while(hs.rs.next()) {
				
				
					if(hs.rs.getInt("User_ID") == hs.current.getID() && hs.matchTestID(hs.rs.getInt("Test_ID")) == this.categories.get(i)) {
						
						//compare date here
						LocalDate last = LocalDate.parse(this.lastExamDate);
						LocalDate examdate = LocalDate.parse(hs.rs.getString("Date"));
						
						System.out.println("Last Date: " + last);
						System.out.println("Exam Date: " + examdate);
						
						if(hs.rs.getInt("Status") == 0 && examdate.isAfter(last)) {
							++this.abnormal;
					
								
							
						}
					
						
							
						if(this.abnormal >= 3) {
							
							this.abnormalID = hs.rs.getInt("Test_ID");
							this.checkAlertTriggered();
							this.setExamDate(hs);
							
						}
						
						
				
							
						
						}
					
						
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
		this.popUpReminder(hs);
		
	}
	
	
	public boolean scanCategories(HealthSystem hs, String sub, int cat) {
		
		hs.dbSwap(false);
		
		this.sub = sub;
		this.cat = cat;	
	//	LocalDate last = LocalDate.parse(this.lastExamDate);
		
		hs.runQuery("SELECT * FROM categories", true);
		
		try {
			
			while(hs.rs.next()) {
				
				System.out.println("Category name: " + hs.rs.getString("Name").toLowerCase().trim());
				System.out.println("Sub name: " + this.sub.toLowerCase().trim());
				
				if(hs.rs.getInt("Status") == 0) {  
					System.out.println("Status is 0");
					if(hs.rs.getString("Name").toLowerCase().trim().equals(this.sub.toLowerCase().trim())) {
					
					System.out.println("Here");
					return true;
				}
				
			}
		}
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	
	
	public void sortActivities() {
		
		Collections.sort(this.activities);
		
		
	}
	
	
	
	public void breakString(String toSplit, String delimiter) {
		
		String[] parts = toSplit.split(delimiter);
		this.dateParts = new ArrayList<>(Arrays.asList(parts));
		
	}
	
	
	public void emptyDateParts() {
		
		this.dateParts.clear();
	}
	
	
	
	private void getLastDate(HealthSystem hs) {
		
		
		String lastDate = "";
		hs.dbSwap(true);
		hs.runQuery("SELECT * FROM Exam_Results", true);
		
		try {
			
			while(hs.rs.next()) {
				
				if(hs.rs.getInt("User_ID") == hs.current.getID()) {
					lastDate = hs.rs.getString("Date");
					
				}
				
			}
			
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		//for reports
		
		if(lastDate != "") {
			breakString(lastDate, "-");
			
		}	
		
		hs.dbSwap(true);
	}
	
	
	public String getLocalDate() {
		
		LocalDate ld = LocalDate.now();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		String today = ld.format(formatter);
		
		
		return today;
	}
	
	
	public void setLastDate(HealthSystem hs) {
		
		hs.dbSwap(false);
		
		try {
			
			hs.runQuery("SELECT * FROM activities;", true);
			
			while(hs.rs.next()) {
				if(hs.rs.getInt("User_ID") == hs.current.getID()) {
					this.lastDate = hs.rs.getString("Date");
				}
				
				
			}
			
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		hs.dbSwap(true);
		
	}
	
	
	
	public void setExamDate(HealthSystem hs) {
		
		hs.dbSwap(true);
		
		try {
			
			hs.runQuery("SELECT * FROM Exam_Results;", true);
			
			while(hs.rs.next()) {
				
				if(hs.rs.getInt("User_ID") == hs.current.getID()) {
					this.lastExamDate = hs.rs.getString("Date");
				}
				
			}
			
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public void setLastMonth(HealthSystem hs) {
		
		hs.dbSwap(false);
		
		try {
			
			hs.runQuery("SELECT * FROM activities", true);
			
			while(hs.rs.next()) {
				
				if(hs.rs.getInt("User_ID") == hs.current.getID()) {
					this.lastMonth = hs.rs.getString("Date");
				}
				
			}
			
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		hs.dbSwap(true);
		
	}

	
	
	
			
		
}
	

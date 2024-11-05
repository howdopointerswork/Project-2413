package project2413;

//measure dates here
//compare earliest date
//if 3 months, change monitor boolean to true
//then create alert
//log the alert in user folder
//also save settings

public class Monitor{
	
	private int abnormalCount;
	
	private int id;
	
	private int testAvg;
	
	private boolean alertStatus;
	
	private String function;
	
	Activity activities[]; //can also make private with add/remove/access methods
	
	
	public int getAbnormal() {
		
		return this.abnormalCount;
			
	}
	
	
	public void popUpAlert(Alert a) {
		
		
		
	}
	
	
	public void popUpReminder() {
		
		
	}
	
	public boolean checkAlertTriggered() {
		
		return true;
		
	}
	
	
	public Exam getRecentResult() {
	
		Exam e = new BloodTest("28/10/24", "ECG", 0); // change later
		//need to connect to db
		//Get highest id number / autonumber
		
		return e;
	}
	
	
	public int scanResults(int testAvg) {
		
		return 0;
	}
	
	
	public void sortActivities() {
		
		
	}
	
}
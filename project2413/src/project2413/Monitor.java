package project2413;


public class Monitor{
	
	private int abnormalCount;
	
	private int id;
	
	private int testAvg;
	
	private boolean alertStatus;
	
	Activity activities[]; //can also make private with add/remove/access methods
	
	
	public int getAbnormal() {
		
		return this.abnormalCount;
			
	}
	
	
	public void popUpAlert(/*Alert a*/) {
		
		
		
	}
	
	
	public void popUpReminder() {
		
		
	}
	
	public boolean checkAlertTriggered() {
		
		return true;
		
	}
	
	
	public Exam getRecentResult() {
	
		Exam e = new Exam("28/10/24", "Liver Function", 0); // change later
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
package project2413;


public class Alert{
	
	private int id;
	
	private String date;
	
	private String reason;
	
	private boolean recorded;
	
	
	Alert(int id, String date, String reason, boolean recorded){
		
		this.id = id;
		
		this.reason = reason;
		
		this.recorded = recorded;
		
	}
	
	
	public void changeRecorded() {
		
		this.recorded = (this.recorded = true) ? false : true;
		
	
	}
	
	
	public int getID() {
		
		return this.id;
	}
	
	
	
	public String getReason() {
		
		return this.reason;
	}
	
	
	public String getDate() {
		
		return this.date;
	}
	
	
	public boolean getRecorded() {
		
		
		return this.recorded;
	}
	
	
}
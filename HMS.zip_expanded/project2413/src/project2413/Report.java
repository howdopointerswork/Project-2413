package project2413;


public class Report{
	
	private String date;
	
	private String type;
	
	private int ReportID;
	
	
	Report(String date, String type, int ReportID){
		
		this.date = date;
		
		this.type = type;
		
		this.ReportID = ReportID;
		
	}
	
	
	public int getID() {
		
		return this.ReportID;
		
	}
	
	
	public String getType() {
		
		return this.type;
	}
	
	public String getDate() {
		
		return this.date;
	}
	
	
}
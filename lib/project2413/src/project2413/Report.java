package project2413;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Collections;
import java.io.FileWriter;
import java.io.IOException;


public class Report{
	
	private String date;
	
	private String type;
	
	private int ReportID;
	
	public float lowest;
	
	public String lowestval;
	
	public HashMap<String, Float> comp;
	
	private float fi;
	
	private float rq;
	
	private float em;
	
	private float med;
	
	
	
	Report(){
		
		this.comp = new HashMap<>();
	}
	
	
	Report(String date, String type, int ReportID){
		
		this.date = date;
		
		this.type = type;
		
		this.ReportID = ReportID;
		
		this.comp = new HashMap<>();
		
		this.fi = 0;
		
		this.rq = 0;
		
		this.em = 0;
		
		this.med = 0;
		
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
	
	
	public void generateMonthly(HealthSystem hs, int mon, int yr) {
		hs.dbSwap(false);
		hs.runQuery("SELECT * FROM activities", true);
		
		/*float fi = 0;
		float rq = 0;
		float em = 0;
		float med = 0;*/
		
		int count = 1;
		
		try {
			
			while(hs.rs.next()) {
				
				String[] months = hs.rs.getString("Date").trim().split("-");
			
			
				
				if(Integer.parseInt(months[1]) == mon && Integer.parseInt(months[0]) == yr && hs.rs.getInt("User_ID") == hs.current.getID()) {
				
					fi += hs.rs.getInt("food_intake");
					rq += hs.rs.getInt("rest_quality");
					em += hs.rs.getInt("emotion");
					med += hs.rs.getInt("medicine");
					++count;
			
					//divide by four
				}
				
				
			}
			
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		hs.dbSwap(true);
	
			this.comp.put("Food Intake", fi/count);
			this.comp.put("Rest Quality", rq/count);
			this.comp.put("Emotion", em/count);
			this.comp.put("Medicine", med/count);
			
		

		lowest = this.comp.get("Food Intake");
		lowestval = "";
		
		this.comp.forEach((key, value)->{
			
			if(value < lowest) {
				
				lowest = value;
				lowestval = key;
			}
			
		});
		
		
		String report = "Abnormalities may be caused by: " + lowestval + " with an average of: " + lowest;

		
		hs.dbSwap(true);
		hs.runQuery("SELECT * FROM Exam_Results", true);
		int ID = 0;
		
		try {
			
			while(hs.rs.next()) {
				
				if(hs.rs.getInt("User_ID") == hs.current.getID()) {
					ID = hs.rs.getInt("Exam_ID");
				}
			}
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		++ID;
		
		try(FileWriter writer = new FileWriter("report" + ID + "_" + hs.current.getID() + ".txt")){
			
			writer.write(report);
			writer.write(System.lineSeparator());
			writer.write("Your averages:");
			writer.write(System.lineSeparator());
			writer.write("Food Intake: " + this.comp.get("Food Intake"));
			writer.write(System.lineSeparator());
			writer.write("Rest Quality: " + this.comp.get("Rest Quality"));
			writer.write(System.lineSeparator());
			writer.write("Emotion: " + this.comp.get("Emotion"));
			writer.write(System.lineSeparator());
			writer.write("Medicine: " + this.comp.get("Medicine"));
			
			
			
		}catch(IOException e) {
			
			e.printStackTrace();
		}
		
		
		
		this.comp.clear();
		
		
		
	}
		

	
	public void generateAnnual(HealthSystem hs, int yr) {
		
		hs.dbSwap(false);
		hs.runQuery("SELECT * FROM activities", true);
		
		
		int count = 1;
		
		try {
			
			while(hs.rs.next()) {
				
				String[] years = hs.rs.getString("Date").trim().split("-");
				System.out.println(years[0]);
				
				if(Integer.parseInt(years[0]) == yr && hs.rs.getInt("User_ID") == hs.current.getID()) {
					
					fi += hs.rs.getInt("food_intake");
					rq += hs.rs.getInt("rest_quality");
					em += hs.rs.getInt("emotion");
					med += hs.rs.getInt("medicine");
					++count;
					
					
					//divide by four
				}
				
				
			}
			
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("Number: " + rq/(count-1));
		
		hs.dbSwap(true);
		if(count > 1) {
			this.comp.put("Food Intake", fi/count);
			this.comp.put("Rest Quality", rq/count);
			this.comp.put("Emotion", em/count);
			this.comp.put("Medicine", med/count);
		}
	
		lowest = this.comp.get("Food Intake");
		lowestval = "";
		
		this.comp.forEach((key, value)->{
			
			if(value < lowest) {
				
				lowest = value;
				lowestval = key;
			}
			
		});
		
		
		String report = "Abnormalities may be caused by: " + lowestval + " with an average of: " + lowest;

		
		hs.dbSwap(true);
		hs.runQuery("SELECT * FROM Exam_Results", true);
		int ID = 0;
		
		try {
			
			while(hs.rs.next()) {
				
				if(hs.rs.getInt("User_ID") == hs.current.getID()) {
					ID = hs.rs.getInt("Exam_ID");
				}
			}
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		++ID;
		
		try(FileWriter writer = new FileWriter("report" + ID + "_" + hs.current.getID() + ".txt")){
			
			writer.write(report);
			writer.write(System.lineSeparator());
			writer.write("Your averages:");
			writer.write(System.lineSeparator());
			writer.write("Food Intake: " + this.comp.get("Food Intake"));
			writer.write(System.lineSeparator());
			writer.write("Rest Quality: " + this.comp.get("Rest Quality"));
			writer.write(System.lineSeparator());
			writer.write("Emotion: " + this.comp.get("Emotion"));
			writer.write(System.lineSeparator());
			writer.write("Medicine: " + this.comp.get("Medicine"));
			writer.write(System.lineSeparator());
			
			
		}catch(IOException e) {
			
			e.printStackTrace();
		}
		
		this.comp.clear();
		
		
		
	}
	
}
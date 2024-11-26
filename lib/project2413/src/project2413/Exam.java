package project2413;
import java.util.HashMap;


public class Exam implements Comparable<Exam>{
	
	private String date;
	
	private String category;
	
	private int id;
	
	private Exam[] results;
	
	private int status;
	
	private int User_ID;
	
	//for saving and loading
	public HashMap<Integer, String> subcategories;
			
	
	public Exam(){
		
		this.subcategories = new HashMap<>();
	}
	
	public Exam(String date, int id, String category, int status, int User_ID){
		
			this.date = date;
			
			this.id = id;
			
			this.category = category;
			
			this.status = status;
			
			this.User_ID = User_ID;
			
			this.subcategories = new HashMap<>();
		
	}
	
	public String getDate() {
		
		return this.date;
		
	}
	
	public void setDate(String date) {
		
		this.date = date;
	}
	
	
	
	public int getID() {
		
		return this.id;
	}
	
	
	public void setID(int id) {
		
		this.id = id;
	}
	
	
	public int getStatus() {
		
		return this.status;
	}
	
	
	public void setStatus(int status) {
		
		this.status = status;
	}
	
	
	public int getUserID() {
		
		
		return this.User_ID;
	}
	
	
	public void setUserID(int User_ID) {
		
		
		this.User_ID = User_ID;
	}
	
	public String getCategory() {
		
		return this.category;
	}
	
	public void setCategory(String category) {
		
		this.category = category;
	}
	
	
	public Exam enterResults(HealthSystem hs) {
		
		System.out.println("Enter exam type");
		int type = hs.scan.nextInt();
		hs.scan.nextLine();
	
		Exam e = null;

		
		switch(type) {
		
		case 1:
		
			e = new BloodTest(this.date, this.getCategory(), this.getStatus(), hs.current.getID());
			break;
			
		case 2:
			
			e = new CardiovascularTest(this.date, this.getCategory(), this.getStatus(), hs.current.getID());
			break;
			
		
		case 3:
			
			e = new GastrointestinalTest(this.date, this.getCategory(), this.getStatus(), hs.current.getID());
			break;
			
		case 4:
			
			e = new RespiratoryTest(this.date, this.getCategory(), this.getStatus(), hs.current.getID());
			break;
			
				
		}
		

		
		return e;
		
	}
	
	
	@Override
	public int compareTo(Exam e) {
		
		return Integer.compare(this.status, e.getStatus());
	}
	
	
	public void addSubcategory(int key, String val) {
		
		this.subcategories.put(key, val);
		
	}
	
	
	public void removeSubcategory(int key) {
		
		this.subcategories.remove(key);
	}
	
	
	
}
package project2413;


public class Exam implements Comparable<Exam>{
	
	private String date;
	
	private String category;
	
	private int id;
	
	private Exam[] results;
	
	private int status;
	
	private int User_ID;
			
	
	Exam(){
		
	}
	
	Exam(String date, int id, String category, int status, int User_ID){
		
			this.date = date;
			
			this.id = id;
			
			this.category = category;
			
			this.status = status;
			
			this.User_ID = User_ID;
			
			
		
	}
	
	public String getDate() {
		
		return this.date;
		
	}
	
	
	
	public int getID() {
		
		return this.id;
	}
	
	
	public int getStatus() {
		
		return this.status;
	}
	
	
	public int getUserID() {
		
		
		return this.User_ID;
	}
	
	public String getCategory() {
		
		return this.category;
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
	
	
	
}
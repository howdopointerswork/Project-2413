package project2413;


public class Exam{
	
	private String date;
	
	private String category;
	
	private int id;
	
	private Exam[] results;
	
	private int status;
	
	private int User_ID;
			
	
	
	
	
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
	
	
	public Exam enterResults(HealthSystem hs) {
		
		Exam e = null;

		System.out.println("Enter type of exam, date, exam id, category, status, and userid");

		int type = hs.scan.nextInt();
		hs.scan.nextLine();
		
		String date = hs.scan.nextLine();
		
		int examid = hs.scan.nextInt();
		hs.scan.nextLine();
		
		String category = hs.scan.nextLine();
		
		int status = hs.scan.nextInt();
		hs.scan.nextLine();
		
		int userid = hs.scan.nextInt();
		hs.scan.nextLine();
		
		
		switch(type) {
		
		case 1:
			
			e = new BloodTest(date, examid, category, status, userid);
			break;
			
		case 2:
			
			e = new CardiovascularTest(date, examid, category, status, userid);
			break;
			
		
		case 3:
			
			e = new GastrointestinalTest(date, examid, category, status, userid);
			break;
			
		case 4:
			
			e = new RespiratoryTest(date, examid, category, status, userid);
			break;
			
				
		}
		
		
		return e;
		
		
		
	}
	
	
	
}
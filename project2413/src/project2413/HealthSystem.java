package project2413;


public class HealthSystem{
	
	private User currentUser;
	
	private String version;
	
	private boolean isAuthenticated;
	
	
	//add args below (exam, db, etc.)
	
	public boolean db_open(/*db database*/) { 
		
		return true;
	
	}
	
	
	public boolean addResult(Exam result) {
		
		return true;
	}
	
	
	public void editResult(Exam result, int id) {
		
		
	}
	
	
	public void deleteResult(Exam result, int id) {
		
		
		
	}
	
	
	public Exam findResult(int id) {
		
		Exam e = new Exam("28/10/24", "Liver Function", 0);
		
		return e;
		
	}
	
	
	public boolean logIn(String username, String password) {
		
		return true;
		
	}
	
	
	public boolean signUp(String username, String password) {
		
		return true;
	}
	
	
	public boolean signOut(User u) {
		
		return true;
	}
	
	public void addRecord() {
		
		
	}
	
	public void modifyRecord() {
		
	}
	
	public void deleteRecord() {
		
		
	}
	
}
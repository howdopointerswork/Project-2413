package project2413;


public class Exam{
	
	private String date;
	
	private String category;
	
	private int id;
	
	
	Exam(String date, String category, int id){
		
			this.date = date;
			
			this.category = category;
			
			this.id = id;
		
	}
	
	String getDate() {
		
		return this.date;
		
	}
	
	
	String getCategory() {
		
		return this.category;
	
	}
	
	int getID() {
		
		return this.id;
	}
	
	
	public void enterResults() {
		
		
	}
	
	
	
}
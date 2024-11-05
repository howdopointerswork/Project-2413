package project2413;


public class BloodTest extends Exam{
	
	
	public int parentExamID;
	
	private String category;
	
	
	
	BloodTest(String date, String category, int id){
		
		super(date, id);
		
		this.category = category;
		
	}
	
	
	public void enterResults(String bloodTestType, String testResult) {
		
		return;
		
	}
	
}
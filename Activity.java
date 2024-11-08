package project2413;

public class Activity implements Comparable<Activity>{
	
	
	private String date;
	
	private int id;
	
	private int food_intake;
	
	private int rest_quality;
	
	private int emotion;
	
	private int medication;
	
	private int avg;
	
	
	Activity(String date, int id, int fi, int rq, int em, int med){
		
		this.date = date;
		
		this.id = id;
		
		this.food_intake = fi;
		
		this.rest_quality = rq;
		
		this.emotion = em;
		
		this.medication = em;
		
	}
	
	
	
	public void printActivity() {
		
		System.out.println("Date: " + this.date);
		System.out.println("ID: " + this.id);
		System.out.println("Food Intake: " + this.food_intake);
		System.out.println("Rest Quality: " + this.rest_quality);
		System.out.println("Emotion: " + this.emotion);
		System.out.println("Medication: " + this.medication);
	}
	
	
	public int getAvg() {
		
		return this.avg = (this.food_intake + this.rest_quality + this.emotion + this.medication)/4;
	}
	
	
	@Override
	public int compareTo(Activity a) {
		
		return Integer.compare(this.avg, a.avg);
		
	}
	
	
}
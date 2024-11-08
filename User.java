package project2413;

public class User{
	
	
	private int id;
	
	private String username;
	
	private String password;
	
	
	public User(int id, String username, String password) {
		
		this.id = id;
		
		this.username = username;
		
		this.password = password;
		
		
	}
	
	
	public int getID() {
		
		return this.id;
	}
	
	
	
	public String getUsername() {
		
		
		return this.username;
	}
	
	
	
	public boolean changePassword(String newPassword) {
		
		String temp = this.password;
		
		this.password = newPassword;
		
		if (temp != this.password) {
			
			return true;
		}
		else {
			
			return false;
		}
		
	}
	
	
	public boolean toggleNotifications() {
		
		return true;
	}
	
	
	public void editActivities() {
		
		
	}
	
	
	public void deleteActivities() {
		
		
	}
	


	
}
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
	
	public void setID(int newID) {
		
		
		this.id = newID;
	}
	
	
	
	public String getUsername() {
		
		
		return this.username;
	}
	
	public void setUsername(String newName) {
		
		
		this.username = newName;
	}
	
	
	public String getPassword() {
		
		
		return this.password;
	}
	
	public void setPassword(String newPw) {
		
		
		this.password = newPw;
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
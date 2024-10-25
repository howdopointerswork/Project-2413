package project2413;


public class Main{
	
	public static void main(String[] args) {
		
		TestClass tc = new TestClass("Testing");
		
		tc.test();
		
		User u = new User(1, "User", "Password");
		
		System.out.println("ID: " + u.getID() + "; Username: " + u.getUsername());
		
		System.out.println("Password changed: " + u.changePassword("new"));
		
	}
}
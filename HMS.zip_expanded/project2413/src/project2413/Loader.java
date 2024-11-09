package project2413;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Loader{
	
	private BufferedReader reader;
	
	private BufferedWriter writer;
	
	
	//Write what needs to be saved here
	//1 - ExamID
	//2 - AlertID
	//3 - Inserted rows in Exam Results , function
	//4 - Username
	//5 - Password
	//6 - UserID
	//7 - Activities to load by number values , function
	//8 - Reports , function
	
	//do encryption here
	
	public void clearDatabase(HealthSystem hs) {
		
		//also do for test tracker
		
		hs.runQuery("DELETE FROM Exam_Results;", false);
		
		
		
	}
	
	
	public void saveVars(int examid, int alertid, int activityid, int userid, String username, String password) {
		
		try {
			
			this.writer = new BufferedWriter(new FileWriter((Integer.toString(userid)+".txt")));
			
			writer.write(Integer.toString(userid));
			writer.newLine();
			writer.write(username);
			writer.newLine();
			//encrypt
			writer.write(password);
			writer.newLine();
			writer.write(Integer.toString(examid));
			writer.newLine();
			writer.write(Integer.toString(alertid));
			writer.newLine();
			writer.write(Integer.toString(activityid));
			
			
		}catch(IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public void loadVars(HealthSystem hs, Monitor mon) {
		
		String line;
		
		try {
			
			this.reader = new BufferedReader(new FileReader(Integer.toString(hs.current.getID())+".txt"));
			
			if((line = this.reader.readLine()) != null) {
				
				int read_userid = Integer.parseInt(line.trim());
				hs.current.setID(read_userid);
			}
			
			
			
			if((line = this.reader.readLine()) != null) {
				
				String read_username = line.trim();
				hs.current.setUsername(read_username);
			}
			
			
			if((line = this.reader.readLine()) != null) {
				
				String read_password = line.trim();
				hs.current.setPassword(read_password);
				
			}
			
			
			
			
			if((line = this.reader.readLine()) != null) {
				
				int read_examid = Integer.parseInt(line.trim());
				hs.setExamID(read_examid);
			}
			
			
			
			
			if((line = this.reader.readLine()) != null) {
				
				int read_alertid = Integer.parseInt(line.trim());
				mon.setAlertID(read_alertid);
			}
			
			
			
			if((line = this.reader.readLine()) != null) {
				
				int read_activityid = Integer.parseInt(line.trim());
				mon.setActivityID(read_activityid);
			}
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
	}
	
	
	
	//save db data
	
	
	
	//load db data
	
}
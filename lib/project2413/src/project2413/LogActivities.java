package project2413;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LogActivities extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel centerPanel;
    private JTextField dateField;
    private AlertPopUp alert; //pop up alert
    private AlertPopUp reminder; //pop up reminder
    private Map<String, JSlider> activitySliders;
    private Map<String, JCheckBox> activityCheckboxes;
    private Map<String, JTextField> activityComments;
    private JButton submitButton;
    private int fi;
    private int rq;
    private int em;
    private int med;
    private int index;
    private String comment;
    



    private MainFrame mainFrame;

    public LogActivities(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        
        this.activityComments = new HashMap<>();
        this.index = 0;
        this.comment = "";
        this.alert = new AlertPopUp(mainFrame, "Abnormal results detected. Please log activities", "Abnormality Alert");
        this.reminder = new AlertPopUp(mainFrame, "3 month reminder. Please enter activity results again", "3 Month Reminder");
        // Top panel with nav buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        Font buttonFont = new Font("Tahoma", Font.PLAIN, 24);

        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.addActionListener(e -> mainFrame.showPreviousPage());

        JButton homeButton = new JButton("Home");
        homeButton.setFont(buttonFont);
        homeButton.addActionListener(e -> mainFrame.showPage("Home"));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(buttonFont);
        logoutButton.addActionListener(e -> mainFrame.showPage("Login"));

        buttonPanel.add(backButton);
        buttonPanel.add(homeButton);
        buttonPanel.add(logoutButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Center panel 
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

     // Instructions button
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        instructionsButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainFrame,
                "<html><div style='text-align: left; font-size: 20px;'>Here you can log activity information if you have been prompted by the Health Monitoring System.<br>Entering your activity data will help the system provide you advice regarding health categories which are being monitored.<br><br>"
                + "Select the activities you wish to log, use the toggle to indicate the quality of your activity (1 is poor, 10 is excellent). Enter comment(s) and then press submit to log.</div></html>",
                "Instructions", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add the Instructions button to the top left of the top panel
        topPanel.add(instructionsButton, BorderLayout.WEST);

        // date input
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dateLabel = new JLabel("Enter date for which you are logging activity data (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
        dateField = new JTextField(10);
        dateField.setFont(new Font("Tahoma", Font.PLAIN, 28));
        datePanel.add(dateLabel);
        datePanel.add(dateField);
        centerPanel.add(datePanel);

        // Initializing sliders ^& checkboxes 
        activitySliders = new HashMap<>();
        activityCheckboxes = new HashMap<>();
        String[] activities = {"Food Intake", "Rest Quality", "Emotion", "Medication"};

        for (String activity : activities) {
            JPanel activityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JCheckBox checkBox = new JCheckBox(activity);
            checkBox.setFont(new Font("Tahoma", Font.PLAIN, 28)); 
            checkBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            activityCheckboxes.put(activity, checkBox);

            JSlider slider = new JSlider(1, 10);
            slider.setMajorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setFont(new Font("Tahoma", Font.PLAIN, 18));
            activitySliders.put(activity, slider);

            activityPanel.add(checkBox);
            activityPanel.add(slider);

            JTextField commentField = new JTextField(15);
            commentField.setFont(new Font("Tahoma", Font.PLAIN, 24));

            JLabel commentLabel = new JLabel("Comment: ");
            commentLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
            activityComments.put(activities[index], commentField);
            ++index;
            activityPanel.add(commentLabel);
            activityPanel.add(commentField);

            centerPanel.add(activityPanel);
        }

        // Submit button
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Tahoma", Font.BOLD, 24));
        submitButton.addActionListener(e -> handleSubmit());
        centerPanel.add(submitButton);

        // add center panel to main panel
        add(centerPanel, BorderLayout.CENTER);
    }


    private void handleSubmit() {
    	//todo: connect to backend function
        String date = dateField.getText();

        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a date.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!isValidDate(date)) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid date format. Please enter the date as yyyy-mm-dd.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder message = new StringBuilder("Activity Log\nDate: " + date + "\n\n");

        boolean atLeastOneSelected = false;
        for (Map.Entry<String, JCheckBox> entry : activityCheckboxes.entrySet()) {
            String activity = entry.getKey();
            JCheckBox checkBox = entry.getValue();

            // validate user has selected at least 1 activity to submit 
            if (checkBox.isSelected()) {
                atLeastOneSelected = true;
                int score = activitySliders.get(activity).getValue();
              //  String comment = activityComments.get(activity).getText();
                
                
                message.append(activity).append(": ").append(score);
               // if (!comment.isEmpty()) {
                 //   message.append(" | Comment: ").append(comment);
                //}
                //message.append("\n");
            }
        }

        if (!atLeastOneSelected) {
            JOptionPane.showMessageDialog(mainFrame, "Please select at least one activity to log.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        
        
        //here
        
        //acitivitySliders
     
       
        
        activityCheckboxes.forEach((str, box)->{
        	
        	if(box.isSelected() == false) {
        		
        		switch(str.toLowerCase()) {
        		
        		case "food intake":
        			
        			this.fi = 0;
        			break;
        			
        		
        		case "rest quality":
        			
        			this.rq = 0;
        			break;
        			
        			
        		case "emotion":
        			
        			this.em = 0;
        			break;
        			
        		case "med":
        			
        			this.med = 0;
        			break;
        		
        	}
        		
        		
        	
        }
        	else {
        		
        		System.out.println(str.toLowerCase());
        		
        		switch(str.toLowerCase()) {
        		
        			case "food intake":
        			
        				this.fi = activitySliders.get("Food Intake").getValue();
        			
        				break;
        		
        		
        		
        			case "rest quality":
        			
        				this.rq = activitySliders.get("Rest Quality").getValue();
        				
        				break;
        			
        			
        			case "emotion":
        			
        				this.em = activitySliders.get("Emotion").getValue();
        			
        				break;
        			
        			case "med":
        			
        				this.med = activitySliders.get("Medication").getValue();

        				break;
        				
        		}
        		
        	}
        	
        }
        
        );
        
        //Why is this here?
        //Testing - do not delete
        

        
        activityComments.forEach((name, field)->{
        	
        	
        	comment += name;
        	comment += ": ";
        	comment += field.getText();
        	comment += " | ";
        	
        	
        	
        });
        
		
		  this.mainFrame.mon.addActivity(this.mainFrame.hs, fi, rq, em, med, date,
		  comment);
		  
		  if(this.mainFrame.mon.onOff) { System.out.println("onOff is true");
		  this.mainFrame.mon.scanResults(this.mainFrame.hs, fi, rq, em, med, date);
		  
		  if(this.mainFrame.mon.getAlertStatus()) {
		  System.out.println("Abnormality found"); this.alert.showAlert();
		  this.mainFrame.mon.addActivity(this.mainFrame.hs, fi, rq, em, med, date,
		  comment); //set new date here } else {
		  
		  if(this.mainFrame.mon.popUpReminder(this.mainFrame.hs));
		  
		  this.reminder.showAlert(); this.mainFrame.showPage("Log Activities"); } }
		 
     
        
        
        JOptionPane.showMessageDialog(mainFrame, message.toString(), "Submission Successful", JOptionPane.INFORMATION_MESSAGE);

        // Reset fields after a user submits
        dateField.setText("");
        for (JSlider slider : activitySliders.values()) {
            slider.setValue(1); // reset sliders
        }
        for (JCheckBox checkBox : activityCheckboxes.values()) {
            checkBox.setSelected(false); // uncheck checkboxes
        }
        for (JTextField commentField : activityComments.values()) {
            commentField.setText(""); // clar comments 
        }
    }
    
    private boolean isValidDate(String date) {
        // validate date pattern
        String datePattern = "\\d{4}-\\d{2}-\\d{2}";
        if (!date.matches(datePattern)) {
            return false;
        }

        // validate numeric date values
        try {
            String[] parts = date.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            // Check valid ranges
            if (month < 1 || month > 12) {
                return false;
            }
            if (day < 1 || day > 31) {
                return false;
            }

            // Check days in a month
            if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                return false;
            }
            if (month == 2) {
                // Leap year check
                boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                if (day > (isLeapYear ? 29 : 28)) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false; // Non-numeric values found
        }

        return true; // Date is valid
    }
}

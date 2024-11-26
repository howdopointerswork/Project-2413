package project2413;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LogExam extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel centerPanel;
    private Map<String, java.util.List<String>> subcategories;
    private JTextField dateField;
    private MainFrame mainFrame;
    private java.util.List<JTextField> resultFields;
    private java.util.List<JCheckBox> abnormalCheckboxes;
    HashMap<JCheckBox, JCheckBox> subBoxes;
    private int status = 1;

    public LogExam(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        resultFields = new ArrayList<>();
        abnormalCheckboxes = new ArrayList<>();
        subBoxes = new HashMap<>();
        initializeSubcategories();

        // Top panel setup
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        Font buttonFont = new Font("Tahoma", Font.PLAIN, 24);

        JButton backButton = new JButton("Back");
        JButton homeButton = new JButton("Home");
        JButton logoutButton = new JButton("Logout");
        Arrays.asList(backButton, homeButton, logoutButton).forEach(button -> {
            button.setFont(buttonFont);
            buttonPanel.add(button);
        });
        
        // Action listeners for buttons 
        backButton.addActionListener(e -> mainFrame.showPreviousPage());

        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Left menu panel
        JPanel leftMenuPanel = createLeftMenuPanel(buttonFont, mainFrame.hs);
        add(leftMenuPanel, BorderLayout.WEST);

        // Center panel
        centerPanel = new JPanel(new GridBagLayout());
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createLeftMenuPanel(Font buttonFont, HealthSystem hs) {
        JPanel leftMenuPanel = new JPanel(new BorderLayout());
        leftMenuPanel.setPreferredSize(new Dimension(200, 0));

        JLabel menuLabel = new JLabel("EXAM", SwingConstants.CENTER);
        menuLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        leftMenuPanel.add(menuLabel, BorderLayout.NORTH);

        JPanel menuButtonsPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        java.util.List<JButton> categoryButtons = Arrays.asList(
            new JButton("Blood"), new JButton("Cardiovascular"), new JButton("Gastrointestinal"),
            new JButton("Respiratory"), new JButton("Ultrasound"), new JButton("X-Ray"),
            new JButton("CT Scan"), new JButton("ECG")
        );

        ActionListener categoryListener = e -> showSubmenu(((JButton) e.getSource()).getText(), hs);
        categoryButtons.forEach(button -> {
            button.setFont(buttonFont);
            button.addActionListener(categoryListener);
            menuButtonsPanel.add(button);
        });

        leftMenuPanel.add(menuButtonsPanel, BorderLayout.CENTER);
        return leftMenuPanel;
    }

    // Subcategories 
    private void initializeSubcategories() {
        subcategories = new HashMap<>();
        subcategories.put("Blood", Arrays.asList("Glucose", "Calcium", "Sodium", "Kidney Function", "Red Blood Cell Count", "White Blood Cell Count", "Hemoglobin"));
        subcategories.put("Cardiovascular", Arrays.asList("Blood Pressure", "Heart Rate"));
        subcategories.put("Gastrointestinal", Arrays.asList("pH Urine", "Stool", "Liver Enzymes"));
        subcategories.put("Respiratory", Arrays.asList("Lung Capacity", "Rate of Flow"));
        subcategories.put("Ultrasound", Arrays.asList("Pelvic", "Abdominal", "Endoscopic", "Thyroid", "Transvaginal", "Obstetric"));
        subcategories.put("X-Ray", Arrays.asList("Dental", "Bones", "Chest", "Abdomen"));
        subcategories.put("CT Scan", Arrays.asList("Bones", "Muscles", "Organs", "Blood Vessels"));
        subcategories.put("ECG", Arrays.asList("Heart Beat", "Heart Rhythm"));

    }

    private void showSubmenu(String category, HealthSystem hs) {
    	
    	//hs.result.setID(hs.matchCategory(category));
        centerPanel.removeAll();
        resultFields.clear();
        abnormalCheckboxes.clear();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel title = new JLabel(category + " Exams");
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        centerPanel.add(title, gbc);

        gbc.gridy++;
        JLabel dateLabel = new JLabel("Enter date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        centerPanel.add(dateLabel, gbc);

        gbc.gridx = 1;
        dateField = new JTextField(10);
        dateField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        centerPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        java.util.List<String> subcategoryList = subcategories.getOrDefault(category, Collections.emptyList());
        
        // Add checkboxes for subcategories with result fields
        for (String subcategory : subcategoryList) {
            JCheckBox subCheckBox = new JCheckBox(subcategory);
            subCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
            centerPanel.add(subCheckBox, gbc);
            
            JTextField resultField = new JTextField(8);
            resultField.setFont(new Font("Tahoma", Font.PLAIN, 20));
            resultField.setEnabled(false); // Initially disabled
            resultFields.add(resultField);

            gbc.gridx = 1;
            centerPanel.add(resultField, gbc);

            JCheckBox abnormalCheckbox = new JCheckBox("Abnormal");
            abnormalCheckbox.setFont(new Font("Tahoma", Font.PLAIN, 20));
            abnormalCheckbox.setEnabled(false); // Initially disabled
            abnormalCheckboxes.add(abnormalCheckbox);

            gbc.gridx = 2;
            centerPanel.add(abnormalCheckbox, gbc);

            gbc.gridx = 0;
            gbc.gridy++;

            // Enable/disable result field based on checkbox selection
            subCheckBox.addActionListener(e -> {
                resultField.setEnabled(subCheckBox.isSelected());
                abnormalCheckbox.setEnabled(subCheckBox.isSelected());
                
                subBoxes.put(subCheckBox, abnormalCheckbox);
            });
        }
        
        //set status if any are abnormal
        //add to hash map as well
        if(subcategoryList == null) {
        	
        	System.out.println("Null");
        }

        // Add Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        submitButton.addActionListener(e -> handleSubmit(category, hs, subcategoryList));
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.gridy++;
        centerPanel.add(submitButton, gbc);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    
    private void handleSubmit(String category, HealthSystem hs, java.util.List<String> subcategoryList) {
        String date = dateField.getText();

        // validate date format (yyyy-mm-dd)
        if (!isValidDate(date)) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid date format. Please enter the date as yyyy-mm-dd.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //validate result fields
        for (int i = 0; i < resultFields.size(); i++) {
            JTextField resultField = resultFields.get(i);
            if (resultField.isEnabled()) {
                // Ensure the field is not empty
                if (resultField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Please fill out all checked subcategory fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        }
        
		
		  hs.result.setDate(date);
		  
		  
		  hs.result.setStatus(status);
		  
		  for(int k=0; k<subcategoryList.size(); k++) {
		  
		  if(abnormalCheckboxes.get(k).isSelected()) { status = 0;
		  hs.result.setStatus(status);
		  
		  } else { status = 1;
		  
		  }
		  
		  //for debugging hs.result.setID(hs.matchCategory(category));
		  System.out.println(category); System.out.println("Current ID: " +
		  hs.result.getID()); hs.result.setCategory(hs.matchTestID(hs.result.getID()));
		  System.out.println("Category: " + hs.result.getCategory());
		  hs.result.addSubcategory(status, subcategoryList.get(i));
		  
		  
		  }
		  
		  subBoxes.forEach((check, abnormal)->{
		  
		  
		  if(abnormal.isSelected()) {
		  
		  status = 0; } else {
		  
		  status = 1; }
		  
		  
		  if(check.isSelected()) {
		  
		  String comment = ""; for(JTextField field : resultFields) {
		  
		  if(field.getText() != "") {
		  
		  comment += field.getText(); comment += " | "; }
		  
		  } System.out.println(comment); hs.dbSwap(false);
		  System.out.println(check.getText());
		  
		  
		  
		  
		  hs.runQuery("INSERT INTO categories VALUES(" + hs.getExamID() + ", " +
		  hs.current.getID() + ", " + hs.result.getID() + ", '" + check.getText() +
		  "', " + status + ", '" + comment + "', '" + hs.result.getDate() + "', " +
		  this.mainFrame.hs.catID + ");", false); check.setSelected(false);
		  
		  
		  
		  ++this.mainFrame.hs.catID; System.out.println("Inserted categories");
		  hs.dbSwap(true); }
		  
		  
		  
		  }
		  
		  
		  );
		  
		  //use result fields as well
		  
		  //System.out.println() hs.addResult(hs.result, "Exam_Results", true);
		  System.out.println("Inserted");
		  
		  StringBuilder results = new StringBuilder("Exam Results:\nDate: " + date +
		  "\n");
		  
		  for (int j = 0; j < resultFields.size(); j++) { 
			  
			JTextField rf = resultFields.get(j); JCheckBox abnormalCheckbox = abnormalCheckboxes.get(j);
		  
		  if (rf.isEnabled() && !rf.getText().isEmpty()) {
		  results.append("Result for ") .append(subcategories.get(category).get(i))
		  .append(": ") .append(rf.getText()) .append(" (")
		  .append(abnormalCheckbox.isSelected() ? "Abnormal" : "Normal")
		  .append(")\n"); } }
		 
    
        JOptionPane.showMessageDialog(mainFrame, results.toString(), "Submission Successful", JOptionPane.INFORMATION_MESSAGE);

        // Optionally reset fields
        dateField.setText("");
        resultFields.forEach(field -> {
            field.setText("");
            field.setEnabled(false);
        });
        abnormalCheckboxes.forEach(cb -> cb.setEnabled(false));
        
    }}
    
    private boolean isValidDate(String date) {
        if (date == null || date.isEmpty()) return false;
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$"; 
        if (!date.matches(dateRegex)) return false;

        try {
            java.time.LocalDate.parse(date); 
            return true;
        } catch (java.time.format.DateTimeParseException e) {
            return false;
        }
    }
    
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Double.parseDouble(str); 
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}

package project2413;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Monitoring extends JPanel {
    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;
    private JPanel centerPanel;
    private JComboBox<String> categoryComboBox, subcategoryComboBox;
    private JButton setupButton, viewMonitoringButton;
    private JToggleButton monitoringToggle; 

    private Monitor mon;

    // Same categories & subcategories from LogExam class
    private final Map<String, java.util.List<String>> subcategories = new HashMap<>();

    public Monitoring(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.mon = new Monitor();
        initializeSubcategories();
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);
        add(createSideMenu(), BorderLayout.WEST);
        initializeComponents();
    }

    private void initializeSubcategories() {
        subcategories.put("Blood", Arrays.asList("Glucose", "Calcium", "Sodium", "Kidney Function", "Red Blood Cell Count", "White Blood Cell Count", "Hemoglobin"));
        subcategories.put("Cardiovascular", Arrays.asList("Blood Pressure", "Heart Rate"));
        subcategories.put("Gastrointestinal", Arrays.asList("pH Urine", "Stool", "Liver Enzymes"));
        subcategories.put("Respiratory", Arrays.asList("Lung Capacity", "Rate of Flow"));
        subcategories.put("Ultrasound", Arrays.asList("Pelvic", "Abdominal", "Endoscopic", "Thyroid", "Transvaginal", "Obstetric"));
        subcategories.put("X-Ray", Arrays.asList("Dental", "Bones", "Chest", "Abdomen"));
        subcategories.put("CT Scan", Arrays.asList("Bones", "Muscles", "Organs", "Blood Vessels"));
        subcategories.put("ECG", Arrays.asList("Heart Beat", "Heart Rhythm"));
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        Font buttonFont = new Font("Tahoma", Font.PLAIN, 24);

        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.addActionListener(e -> goBack());

        JButton homeButton = new JButton("Home");
        homeButton.setFont(buttonFont);
        homeButton.addActionListener(e -> goHome());

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(buttonFont);
        logoutButton.addActionListener(e -> logout());

        buttonPanel.add(backButton);
        buttonPanel.add(homeButton);
        buttonPanel.add(logoutButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        return topPanel;
    }

    private JPanel createSideMenu() {
        JPanel leftMenuPanel = new JPanel();
        leftMenuPanel.setLayout(new BorderLayout());
        leftMenuPanel.setPreferredSize(new Dimension(200, 0));

        JLabel menuLabel = new JLabel("MENU", SwingConstants.CENTER);
        menuLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        leftMenuPanel.add(menuLabel, BorderLayout.NORTH);

        JPanel menuButtonsPanel = new JPanel();
        menuButtonsPanel.setLayout(new GridLayout(6, 1, 10, 10));

        Font buttonFont = new Font("Tahoma", Font.PLAIN, 26);

        JButton setupMonitoringButton = new JButton("<html><div align='center'>Set up Monitoring</div></html>");
        setupMonitoringButton.setFont(buttonFont);
        setupMonitoringButton.addActionListener(e -> setupMonitoring());

        JButton viewMonitoringButton = new JButton("<html><div align='center'>View My Monitoring</div></html>");
        viewMonitoringButton.setFont(buttonFont);
        viewMonitoringButton.addActionListener(e -> viewMonitoring());

        
        //toggle 
        monitoringToggle = new JToggleButton("<html><div style='text-align: center;'>Monitoring OFF</div></html>");
        monitoringToggle.setFont(buttonFont);
        monitoringToggle.addActionListener(e -> toggleMonitoring());

        menuButtonsPanel.add(setupMonitoringButton);
        menuButtonsPanel.add(viewMonitoringButton);
        menuButtonsPanel.add(monitoringToggle); 
        leftMenuPanel.add(menuButtonsPanel, BorderLayout.CENTER);

        return leftMenuPanel;
    }

    private void initializeComponents() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
        add(centerPanel, BorderLayout.CENTER);

        setupButton = new JButton("Set up Monitoring");
        setupButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        setupButton.addActionListener(e -> setMonitoring());

        viewMonitoringButton = new JButton("View Monitoring Results");
        viewMonitoringButton.setFont(new Font("Tahoma", Font.BOLD, 20));
    }

    // monitoring message 
    private void toggleMonitoring() {
        if (monitoringToggle.isSelected()) {
        	this.mainFrame.mon.onOff = true;
            monitoringToggle.setText("<html><div style='text-align: center;'>Turn Monitoring OFF</div></html>");
            JOptionPane.showMessageDialog(this, "<html><div align='center'>Monitoring ON.</div></html>", "Monitor Status", JOptionPane.INFORMATION_MESSAGE);
        } else {
        	this.mainFrame.mon.onOff = false;
            monitoringToggle.setText("<html><div style='text-align: center;'>Turn Monitoring ON</div></html>");
            JOptionPane.showMessageDialog(this, "<html><div align='center'>Monitoring OFF.</div></html>", "Monitor Status", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void setupMonitoring() {
        centerPanel.removeAll();

        categoryComboBox = new JComboBox<>(subcategories.keySet().toArray(new String[0]));
        categoryComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        categoryComboBox.setPreferredSize(new Dimension(200, 30));
        categoryComboBox.addActionListener(e -> updateSubcategories());

        subcategoryComboBox = new JComboBox<>();
        subcategoryComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        subcategoryComboBox.setPreferredSize(new Dimension(200, 30));

        JLabel categoryLabel = new JLabel("Select Category:");
        categoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));

        JLabel subcategoryLabel = new JLabel("Select Subcategory:");
        subcategoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));

        centerPanel.add(categoryLabel);
        centerPanel.add(categoryComboBox);
        centerPanel.add(subcategoryLabel);
        centerPanel.add(subcategoryComboBox);
        centerPanel.add(setupButton);
        

        revalidate();
        repaint();
    }

    private void updateSubcategories() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        if (selectedCategory != null) {
            java.util.List<String> subcategoryList = subcategories.get(selectedCategory);
            subcategoryComboBox.setModel(new DefaultComboBoxModel<>(subcategoryList.toArray(new String[0])));
        }
    }

    //show monitoring here
    private void viewMonitoring() {
    		AlertPopUp view = new AlertPopUp(this.mainFrame, "Monitored Category: " + this.mainFrame.hs.matchTestID(this.mainFrame.mon.cat) + "    Monitored Subcategory: " + this.mon.sub, "Monitoring");
    		view.setSize(600,200);
    		view.showAlert();
       // System.out.println("Here");
      //  this.mon.scanResults(this.mainFrame.hs);
      //  this.mon.checkAlertTriggered(this.mainFrame.hs, "Liver Function");
    }
    
    
    private void setMonitoring() {
    	if(this.mainFrame.mon.onOff) {
    		String categoryString = (categoryComboBox.getSelectedItem()).toString();
    		this.mainFrame.mon.cat = this.mainFrame.hs.matchCategory(categoryString);
    		
    		String sub = (subcategoryComboBox.getSelectedItem()).toString();
    		
    	
    		if(mon.scanCategories(this.mainFrame.hs, sub, this.mainFrame.mon.cat)) {
    			this.mainFrame.showPage("Log Activities");    			
    			AlertPopUp alert = new AlertPopUp(this.mainFrame, "<html>Abnormality found in " + sub + ". Please log an activity related to: " + categoryString + "</html>", "Abnormality Found");
    			alert.showAlert();
    				
    		}
    		
    		
    	}
    	
    	
    }

    private void goBack() {
        mainFrame.showPreviousPage();
    }

    private void goHome() {
        mainFrame.showHomePage();
    }

    private void logout() {
        mainFrame.logout();
    }
}

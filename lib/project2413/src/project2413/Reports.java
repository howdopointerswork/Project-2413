package project2413;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class Reports extends JPanel {
    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;
    private JPanel centerPanel;
    private JComboBox<String> yearComboBox, monthComboBox;
    private JButton generateReportButton;
    private Report rep;
    private boolean yrOrmon;
    private JTextArea reportDisplay;

    public Reports(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        rep = new Report();
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);
        add(createSideMenu(), BorderLayout.WEST);
        initializeComponents();
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
        menuButtonsPanel.setLayout(new GridLayout(5, 1, 10, 10));

        Font buttonFont = new Font("Tahoma", Font.PLAIN, 26);

        JButton monthlyReportButton = new JButton("Generate a Monthly Report");
        monthlyReportButton.setFont(buttonFont);
        monthlyReportButton.addActionListener(e -> setupMonthlyReport());

        JButton annualReportButton = new JButton("Generate an Annual Report");
        annualReportButton.setFont(buttonFont);
        annualReportButton.addActionListener(e -> setupAnnualReport());

        menuButtonsPanel.add(monthlyReportButton);
        menuButtonsPanel.add(annualReportButton);
        leftMenuPanel.add(menuButtonsPanel, BorderLayout.CENTER);

        return leftMenuPanel;
    }

    private void initializeComponents() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
        add(centerPanel, BorderLayout.CENTER);
        
        generateReportButton = new JButton("Generate Report");
        generateReportButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        generateReportButton.addActionListener(e -> submitReport());
        
        // to display the report data in GUI
        reportDisplay = new JTextArea(15, 40);
        reportDisplay.setFont(new Font("Tahoma", Font.PLAIN, 18));
        reportDisplay.setLineWrap(true);
        reportDisplay.setWrapStyleWord(true);
        reportDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportDisplay);
        centerPanel.add(scrollPane);
    }

    private void setupMonthlyReport() {
        centerPanel.removeAll();
        
        this.yrOrmon = false;

        yearComboBox = new JComboBox<>(getYearOptions());
        yearComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        yearComboBox.setPreferredSize(new Dimension(150, 30));

        monthComboBox = new JComboBox<>(getMonthOptions());
        monthComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        monthComboBox.setPreferredSize(new Dimension(150, 30));

        // Updated font size for labels
        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setFont(new Font("Tahoma", Font.PLAIN, 22)); // Larger font for labels
        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setFont(new Font("Tahoma", Font.PLAIN, 22)); // Larger font for labels

        centerPanel.add(yearLabel);
        centerPanel.add(yearComboBox);
        centerPanel.add(monthLabel);
        centerPanel.add(monthComboBox);
        centerPanel.add(generateReportButton);

        revalidate();
        repaint();
    }

    private void setupAnnualReport() {
        centerPanel.removeAll();

        this.yrOrmon = true;
        
        yearComboBox = new JComboBox<>(getYearOptions());
        yearComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        yearComboBox.setPreferredSize(new Dimension(150, 30));

        // Updated font size for label
        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setFont(new Font("Tahoma", Font.PLAIN, 22)); // Larger font for label

        centerPanel.add(yearLabel);
        centerPanel.add(yearComboBox);
        centerPanel.add(generateReportButton);

        revalidate();
        repaint();
    }

    private String[] getYearOptions() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String[] years = new String[10];
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(currentYear - i);
        }
        return years;
    }

    private String[] getMonthOptions() {
        return new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    }
    
    
    private void submitReport() {
    	
    	reportDisplay.setText("");
    	String yr = (String) yearComboBox.getSelectedItem();
		int year = Integer.parseInt(yr);
    	
    	if(this.yrOrmon) {
    		
    		this.rep.generateAnnual(this.mainFrame.hs, year);
            reportDisplay.append("Annual Report for Year: " + year + "\n");
            reportDisplay.append("Abnormalities may be caused by: " + this.rep.lowestval + " with an average of: " + this.rep.lowest + "\n");
    	}
    	else {
    		
    		String mon = (String) monthComboBox.getSelectedItem();
    		int month = 0;
    		
    		switch(mon.toLowerCase()) {
    		
    		case "january":
    			
    			month = 1;
    			break;
    			
    		case "february":
    			
    			month = 2;
    			break;
    			
    		case "march":
    			
    			month = 3;
    			break;
    			
    		case "april":
    			
    			month = 4;
    			break;
    			
    		case "may":
    			
    			month = 5;
    			break;
    			
    		case "june":
    			
    			month = 6;
    			break;
    			
    		case "july":
    			
    			month = 7;
    			break;
    			
    		case "august":
    			
    			month = 8;
    			break;
    			
    		case "september":
    			
    			month = 9;
    			break;
    			
    		case "october":
    			
    			month = 10;
    			break;
    			
    		case "november":
    			
    			month = 11;
    			break;
    			
    		case "december":
    			
    			month = 12;
    			break;
    		}
    		
    		
    		this.rep.generateMonthly(this.mainFrame.hs, month, year);
    		reportDisplay.append("Monthly Report for " + mon + " " + year + "\n");
            reportDisplay.append("Abnormalities may be caused by: " + this.rep.lowestval + " with an average of: " + this.rep.lowest + "\n");
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

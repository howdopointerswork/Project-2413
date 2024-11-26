package project2413;

import javax.swing.*;
import java.awt.*;

public class UserHomePage extends JPanel {

    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;

    public UserHomePage(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Panel for back / home / logout buttons
        JPanel topPanel = new JPanel(new BorderLayout());

        // Back / home / logout button formatting
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
        backButton.addActionListener(e -> mainFrame.showPreviousPage());

        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
        homeButton.addActionListener(e -> mainFrame.showPage("Home"));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
        logoutButton.addActionListener(e -> mainFrame.showPage("Login"));
        logoutButton.addActionListener(e -> mainFrame.logout());
        // Add buttons to buttonPanel
        buttonPanel.add(backButton);
        buttonPanel.add(homeButton);
        buttonPanel.add(logoutButton);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Welcome message
        JTextArea welcomeTextArea = new JTextArea("Welcome to your Personal Health Monitoring System"
                + "\n\nHere, you can keep track of your exam results, "
                + "log your health-related activities, set up health monitoring "
                + "and generate reports.");
        welcomeTextArea.setFont(new Font("Tahoma", Font.PLAIN, 26));
        welcomeTextArea.setEditable(false);
        welcomeTextArea.setLineWrap(true);
        welcomeTextArea.setWrapStyleWord(true);
        welcomeTextArea.setOpaque(false);

        welcomeTextArea.setPreferredSize(new Dimension(800, 100));
        welcomeTextArea.setBorder(BorderFactory.createEmptyBorder(50, 40, 0, 0)); // padding for the welcome message
        add(welcomeTextArea, BorderLayout.CENTER);

        // Left menu
        setUpLeftMenu();
    }

    private void setUpLeftMenu() {
        JPanel leftMenuPanel = new JPanel();
        leftMenuPanel.setLayout(new BorderLayout());
        leftMenuPanel.setPreferredSize(new Dimension(200, 0));

        JLabel menuLabel = new JLabel("MENU", SwingConstants.CENTER);
        menuLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        leftMenuPanel.add(menuLabel, BorderLayout.NORTH);

        JPanel menuButtonsPanel = new JPanel();
        menuButtonsPanel.setLayout(new GridLayout(5, 1, 10, 10));

        Font buttonFont = new Font("Tahoma", Font.PLAIN, 26);

        JButton logExamResultButton = new JButton("Log Exam");
        logExamResultButton.setFont(buttonFont);
        logExamResultButton.addActionListener(e -> mainFrame.showPage("LogExam"));

        JButton logActivitiesButton = new JButton("Log Activities");
        logActivitiesButton.setFont(buttonFont);
        logActivitiesButton.addActionListener(e -> mainFrame.showPage("Log Activities"));
       // if(this.mainFrame.mon.popUpReminder(this.mainFrame)) show page here
        
       // Monitoring m = new Monitoring(this.mainFrame);
        JButton setMonitoringButton = new JButton("Monitoring");
        setMonitoringButton.setFont(buttonFont);
        setMonitoringButton.addActionListener(e -> mainFrame.showPage("Monitoring"));

        JButton generateReportButton = new JButton("Reports");
        generateReportButton.setFont(buttonFont);
        generateReportButton.addActionListener(e -> mainFrame.showPage("Reports"));

        JButton searchButton = new JButton("<html><div align='center'>View/Edit<br>Stored Exams<br>& Activities</div></html>");
        searchButton.setFont(buttonFont);
        searchButton.addActionListener(e -> mainFrame.showPage("Search"));
        

        // Main menu buttons
        menuButtonsPanel.add(logExamResultButton);
        menuButtonsPanel.add(logActivitiesButton);
        menuButtonsPanel.add(setMonitoringButton);
        menuButtonsPanel.add(generateReportButton);
        menuButtonsPanel.add(searchButton);

        leftMenuPanel.add(menuButtonsPanel, BorderLayout.CENTER);
        add(leftMenuPanel, BorderLayout.WEST);
    }

    private void showCustomMessage(String message) {
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        JOptionPane.showMessageDialog(this, messageLabel, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}

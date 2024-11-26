package project2413;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;
import project2413.*;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Stack<String> pageHistory;
    
    public HealthSystem hs;
    public Monitor mon = new Monitor();
 
    

    public MainFrame() {
        setTitle("Health Monitoring System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        
        this.hs = new HealthSystem();
       
        
        
        UserLoginPage loginPage = new UserLoginPage(this);
        setContentPane(loginPage);

        // Initialize CardLayout and card panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        pageHistory = new Stack<>();

        // Add the panels (pages) to the card panel
        addPages();

        // Add card panel to the frame
        setContentPane(cardPanel);
    }
    
   
    

    private void addPages() {
        // pagse to be created & added to panel

        UserLoginPage loginPage = new UserLoginPage(this);
        UserHomePage homePage = new UserHomePage(this);
        LogExam logExamPage = new LogExam(this);
        Search searchPage = new Search(this);
        RegistrationPage registrationPage = new RegistrationPage(this);
        LogActivities logActivitiesPage = new LogActivities(this);
        Reports reportsPage = new Reports(this);
        Monitoring monitorPage = new Monitoring(this);

        cardPanel.add(loginPage, "Login");
        cardPanel.add(homePage, "Home");
        cardPanel.add(logExamPage, "LogExam");
        cardPanel.add(searchPage, "Search");
        cardPanel.add(registrationPage, "Registration");
        cardPanel.add(logActivitiesPage, "Log Activities");
        cardPanel.add(reportsPage, "Reports");
        cardPanel.add(monitorPage, "Monitoring");

        // to show login page first
        showPage("Login");
    }
    

    public void showPage(String pageName) {
        if (!pageHistory.isEmpty() && !pageHistory.peek().equals(pageName)) {
            pageHistory.push(pageName);
        } else if (pageHistory.isEmpty()) {
            pageHistory.push(pageName);
        }
        cardLayout.show(cardPanel, pageName);
    }

    public void showPreviousPage() {
        if (pageHistory.size() > 1) {
            pageHistory.pop(); // Remove current page
            String previousPage = pageHistory.peek();
            cardLayout.show(cardPanel, previousPage);
        } else {
            JOptionPane.showMessageDialog(this, "No previous page.", "Navigation Error", JOptionPane.WARNING_MESSAGE);
        }
    }


	public void showHomePage() {
        showPage("Home");
        
        
		
	}
	//hs.current, ldr, hs, mon
    public void logout() {
    	System.out.println("Here");
        pageHistory.clear(); // Clear history when logging out
        showPage("Login");
        this.hs.signOut(this.mon);
    }
}

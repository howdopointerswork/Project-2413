package project2413;

import javax.swing.*;
import java.awt.*;


public class UserLoginPage extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private MainFrame mainFrame;

    public UserLoginPage(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());

        Font largeFont = new Font("Tahoma", Font.PLAIN, 24);

        // Title Label
        JLabel lblTitle = new JLabel("Personal Health Monitoring System");
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 28));
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.insets = new Insets(10, 10, 10, 10);
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.gridwidth = 3;
        gbcTitle.anchor = GridBagConstraints.CENTER;
        add(lblTitle, gbcTitle);

        // Username Label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(largeFont);
        GridBagConstraints gbcUsernameLabel = new GridBagConstraints();
        gbcUsernameLabel.insets = new Insets(10, 10, 10, 10);
        gbcUsernameLabel.gridx = 0;
        gbcUsernameLabel.gridy = 5;
        gbcUsernameLabel.anchor = GridBagConstraints.EAST;
        add(usernameLabel, gbcUsernameLabel);

        // Username Text Field
        textField = new JTextField(15);
        textField.setFont(largeFont);
        GridBagConstraints gbcTextField = new GridBagConstraints();
        gbcTextField.insets = new Insets(10, 10, 10, 10);
        gbcTextField.gridx = 2;
        gbcTextField.gridy = 5;
        add(textField, gbcTextField);

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(largeFont);
        GridBagConstraints gbcPasswordLabel = new GridBagConstraints();
        gbcPasswordLabel.insets = new Insets(10, 10, 10, 10);
        gbcPasswordLabel.gridx = 0;
        gbcPasswordLabel.gridy = 6;
        gbcPasswordLabel.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbcPasswordLabel);

        // Password Field
        passwordField = new JPasswordField(15);
        passwordField.setFont(largeFont);
        GridBagConstraints gbcPasswordField = new GridBagConstraints();
        gbcPasswordField.insets = new Insets(10, 10, 10, 10);
        gbcPasswordField.gridx = 2;
        gbcPasswordField.gridy = 6;
        add(passwordField, gbcPasswordField);

        // Login Button
        JButton loginButton = new JButton("Log In");
        loginButton.setFont(largeFont);
        GridBagConstraints gbcLoginButton = new GridBagConstraints();
        gbcLoginButton.insets = new Insets(10, 10, 10, 10);
        gbcLoginButton.gridx = 2;
        gbcLoginButton.gridy = 7;
        gbcLoginButton.anchor = GridBagConstraints.WEST;
        add(loginButton, gbcLoginButton);

        // Login button action listener
        loginButton.addActionListener(e -> {
            String username = textField.getText();
            String password = new String(passwordField.getPassword());

            if (mainFrame.hs.logIn(username, password, mainFrame.mon)) {
                // Track the logged-in user
                SessionManager.getInstance().login(username);
                mainFrame.hs.ldr.loadVars(mainFrame.hs, mainFrame.mon);
                mainFrame.hs.printInfo();
                // Switch to Home Page
                mainFrame.showPage("Home");
              
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Registration Prompt Label
        JLabel lblRegPrompt = new JLabel("No Account Yet?");
        lblRegPrompt.setFont(largeFont);
        GridBagConstraints gbcRegPrompt = new GridBagConstraints();
        gbcRegPrompt.insets = new Insets(10, 10, 10, 10);
        gbcRegPrompt.gridx = 0;
        gbcRegPrompt.gridy = 8;
        gbcRegPrompt.anchor = GridBagConstraints.EAST;
        add(lblRegPrompt, gbcRegPrompt);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(largeFont);
        GridBagConstraints gbcRegisterButton = new GridBagConstraints();
        gbcRegisterButton.insets = new Insets(10, 10, 10, 10);
        gbcRegisterButton.gridx = 2;
        gbcRegisterButton.gridy = 8;
        gbcRegisterButton.anchor = GridBagConstraints.WEST;
        add(registerButton, gbcRegisterButton);

        // register button listener
        registerButton.addActionListener(e -> mainFrame.showPage("Registration"));
    }
}

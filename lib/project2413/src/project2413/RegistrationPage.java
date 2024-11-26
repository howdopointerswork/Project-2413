package project2413;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class RegistrationPage extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JTextField weightField;
    private JTextField heightField;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private MainFrame mainFrame;

    public RegistrationPage(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());

        Font largeFont = new Font("Tahoma", Font.PLAIN, 24);

        // Title 
        JLabel lblTitle = new JLabel("Register New Account");
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 28));
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.insets = new Insets(10, 10, 20, 10);
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.gridwidth = 2;
        gbcTitle.anchor = GridBagConstraints.CENTER;
        add(lblTitle, gbcTitle);

        // Username 
        createLabelAndField("Username:", largeFont, usernameField = new JTextField(15), 1);

        // Password
        createLabelAndField("Password:", largeFont, passwordField = new JPasswordField(15), 2);

        // First Name
        createLabelAndField("First Name:", largeFont, firstNameField = new JTextField(15), 4);

        // Last Name
        createLabelAndField("Last Name:", largeFont, lastNameField = new JTextField(15), 5);

        // Date of Birth
        createLabelAndField("Date of Birth (YYYY-MM-DD):", largeFont, dobField = new JTextField(10), 6);

        // Gender Radio Buttons
        JLabel sexLabel = new JLabel("Sex:");
        sexLabel.setFont(largeFont);
        GridBagConstraints gbcSexLabel = new GridBagConstraints();
        gbcSexLabel.insets = new Insets(10, 10, 10, 10);
        gbcSexLabel.gridx = 0;
        gbcSexLabel.gridy = 7;
        gbcSexLabel.anchor = GridBagConstraints.EAST;
        add(sexLabel, gbcSexLabel);

        maleRadio = new JRadioButton("M");
        femaleRadio = new JRadioButton("F");
        maleRadio.setFont(largeFont);
        femaleRadio.setFont(largeFont);
        ButtonGroup sexGroup = new ButtonGroup();
        sexGroup.add(maleRadio);
        sexGroup.add(femaleRadio);

        JPanel sexPanel = new JPanel();
        sexPanel.add(maleRadio);
        sexPanel.add(femaleRadio);

        GridBagConstraints gbcSexPanel = new GridBagConstraints();
        gbcSexPanel.insets = new Insets(10, 10, 10, 10);
        gbcSexPanel.gridx = 1;
        gbcSexPanel.gridy = 7;
        add(sexPanel, gbcSexPanel);

        // Weight Field
        createLabelAndField("Weight (kg):", largeFont, weightField = new JTextField(10), 8);

        // Height Field
        createLabelAndField("Height (m):", largeFont, heightField = new JTextField(10), 9);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(largeFont);
        GridBagConstraints gbcRegisterButton = new GridBagConstraints();
        gbcRegisterButton.insets = new Insets(20, 10, 10, 10);
        gbcRegisterButton.gridx = 1;
        gbcRegisterButton.gridy = 10;
        gbcRegisterButton.anchor = GridBagConstraints.WEST;
        add(registerButton, gbcRegisterButton);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String dob = dobField.getText().trim();
            String weight = weightField.getText().trim();
            String height = heightField.getText().trim();
            String sex = maleRadio.isSelected() ? "M" : femaleRadio.isSelected() ? "F" : "";

            float w = Float.parseFloat(weight);
            float h = Float.parseFloat(height);
            
            // checking that no field is empty - display error messgaeg to user if any empty field
            if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || weight.isEmpty() || height.isEmpty() || sex.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // specific validations
            if (!validateUsername(username)) {
                JOptionPane.showMessageDialog(this, "Username must be between 10 and 126 characters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!validatePassword(password)) {
                JOptionPane.showMessageDialog(this, "Password must be between 10 and 126 characters, and contain at least one uppercase letter and one of the following special characters: !@#$%^&*()\\\\-_=+<>?/{}~|].*\".", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!validateFloat(weight) || Float.parseFloat(weight) > 1000) {
                JOptionPane.showMessageDialog(this, "Weight must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!validateFloat(height) || Float.parseFloat(height) > 1000) {
                JOptionPane.showMessageDialog(this, "Height must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //  if all validations are success
    		System.out.println(dob);
        	mainFrame.hs.signUp(username, password, firstName, lastName, dob, sex, w, h, mainFrame.mon);
            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.showPage("Login");
        });
        
        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(largeFont);
        GridBagConstraints gbcBackButton = new GridBagConstraints();
        gbcBackButton.insets = new Insets(20, 10, 10, 10);
        gbcBackButton.gridx = 0;
        gbcBackButton.gridy = 10;
        gbcBackButton.anchor = GridBagConstraints.EAST;
        add(backButton, gbcBackButton);

        // Back button action listener
        backButton.addActionListener(e -> mainFrame.showPage("Login"));
    }

    private boolean validateUsername(String username) {
        return username.length() >= 10 && username.length() <= 126;
    }

    private boolean validatePassword(String password) {
        if (password.length() < 10 || password.length() > 126) {
            return false;
        }

        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasSpecial = password.matches(".*[!@#$%^&*()\\-_=+<>?/{}~|].*");

        return hasUppercase && hasSpecial;
    }

    private boolean validateFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean validateDate(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false; // enforces the system's date format 
        }

        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        // validtae valid date
        if (month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }

        //checks for the number of dats in months
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            return false; // April, June, September, November have max 30 days
        }
        if (month == 2) { //feb check
            boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
            if (day > (isLeapYear ? 29 : 28)) {
                return false;
            }
        }

        return true;
    }

    private void createLabelAndField(String labelText, Font font, JTextField textField, int yPos) {
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.insets = new Insets(10, 10, 10, 10);
        gbcLabel.gridx = 0;
        gbcLabel.gridy = yPos;
        gbcLabel.anchor = GridBagConstraints.EAST;
        add(label, gbcLabel);

        textField.setFont(font);
        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.insets = new Insets(10, 10, 10, 10);
        gbcField.gridx = 1;
        gbcField.gridy = yPos;
        add(textField, gbcField);
    }
}

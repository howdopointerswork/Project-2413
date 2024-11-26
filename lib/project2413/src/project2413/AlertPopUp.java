package project2413;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlertPopUp extends JDialog {

    public AlertPopUp(JFrame parentFrame, String message, String title) {
        super(parentFrame, title, true); 
        setLayout(new BorderLayout());

        // Alert message
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 18)); // Adjust font size as needed
        add(messageLabel, BorderLayout.CENTER);

        // OK button
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        okButton.addActionListener(e -> dispose()); // Close the dialog on click
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // size / position 
        setSize(400, 200);
        setLocationRelativeTo(parentFrame); // Center on the parent frame
    }

     //Static method to display an alert
    public void showAlert() {
        //AlertPopUp alert = new AlertPopUp(parentFrame, message, title);
        this.setVisible(true); // Show the dialog
    	
      //  showAlert(parentFrame, "testestestestestest!", "Alert Title");
    }
    
    
}
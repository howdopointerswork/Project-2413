package project2413;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import java.security.interfaces.RSAKey;
import java.sql.SQLException;
import java.util.HashMap;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.sql.*;
import java.awt.event.*;


public class Search extends JPanel {
    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;
    private JPanel centerPanel;
    private JTextField searchField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JLabel toLabel;
    private JButton searchButton;
    private JComboBox<String> searchTypeComboBox;
    private JCheckBox abnormalResultsCheckBox;
    private JCheckBox foodIntakeCheckBox, restQualityCheckBox, emotionCheckBox, medicationCheckBox;
    private JCheckBox bloodCheckBox, cardiovascularCheckBox, gastrointestinalCheckBox, respiratoryCheckBox;
    private JCheckBox ultrasoundCheckBox, xrayCheckBox, ctScanCheckBox, ecgCheckBox;
    private JCheckBox activityTypeCheckBox1, activityTypeCheckBox2, activityTypeCheckBox3, activityTypeCheckBox4; 
    private int searchFontSize = 26;
    private  HashMap<Integer,JCheckBox> map; 
    private HashMap<Integer, JCheckBox> map2;
    private boolean examOractivity = true; //for when only abnormal is checked
    private  ActionListener act1;
    private ActionListener act2;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private Object[][] data = {};
    private int index;

    public Search(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.map = new HashMap<Integer,JCheckBox>();
        this.map2 = new HashMap<Integer,JCheckBox>();
        this.act1 = e -> searchRecords(true);
        this.act2 = e -> searchRecords(false);
        this.index = 0;
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);
        add(createSideMenu(), BorderLayout.WEST);
        initializeComponents();
        
        setupExamSearch();  // default search type is Exam Type
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

        JButton myExamsButton = new JButton("<html><div align='center'>Search My<br>Exams</div></html>");
        myExamsButton.setFont(buttonFont);
        myExamsButton.addActionListener(e -> setupExamSearch());

        JButton myActivitiesButton = new JButton("<html><div align='center'>Search My<br>Activities</div></html>");
        myActivitiesButton.setFont(buttonFont);
        myActivitiesButton.addActionListener(e -> setupActivitySearch());

        menuButtonsPanel.add(myExamsButton);
        menuButtonsPanel.add(myActivitiesButton);
        leftMenuPanel.add(menuButtonsPanel, BorderLayout.CENTER);

        return leftMenuPanel;
    }

    private void initializeComponents() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        JLabel searchTypeLabel = new JLabel("Search by:");
        searchTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, searchFontSize));
        searchPanel.add(searchTypeLabel);

        searchTypeComboBox = new JComboBox<>(new String[]{"Exam Type", "Date"});
        searchTypeComboBox.setFont(new Font("Tahoma", Font.PLAIN, searchFontSize));
        searchPanel.add(searchTypeComboBox);

        JLabel searchLabel = new JLabel("Enter value:");
        searchLabel.setFont(new Font("Tahoma", Font.PLAIN, searchFontSize));
        searchPanel.add(searchLabel);

        searchField = new JTextField(15);
        searchField.setFont(new Font("Tahoma", Font.PLAIN, searchFontSize));
        searchPanel.add(searchField);

        startDateField = new JTextField(10);
        endDateField = new JTextField(10);
        startDateField.setFont(new Font("Tahoma", Font.PLAIN, searchFontSize));
        endDateField.setFont(new Font("Tahoma", Font.PLAIN, searchFontSize));
        startDateField.setVisible(false);
        endDateField.setVisible(false);
        
        toLabel = new JLabel("to");
        toLabel.setFont(new Font("Tahoma", Font.PLAIN, searchFontSize));
        toLabel.setVisible(false);

        searchPanel.add(startDateField);
        searchPanel.add(toLabel);
        searchPanel.add(endDateField);

        // Exam Type checkboxes
        bloodCheckBox = createExamCheckBox("Blood");
        cardiovascularCheckBox = createExamCheckBox("Cardiovascular");
        gastrointestinalCheckBox = createExamCheckBox("Gastrointestinal");
        respiratoryCheckBox = createExamCheckBox("Respiratory");
        ultrasoundCheckBox = createExamCheckBox("Ultrasound");
        xrayCheckBox = createExamCheckBox("X-Ray");
        ctScanCheckBox = createExamCheckBox("CT Scan");
        ecgCheckBox = createExamCheckBox("ECG");

        searchPanel.add(bloodCheckBox);
        searchPanel.add(cardiovascularCheckBox);
        searchPanel.add(gastrointestinalCheckBox);
        searchPanel.add(respiratoryCheckBox);
        searchPanel.add(ultrasoundCheckBox);
        searchPanel.add(xrayCheckBox);
        searchPanel.add(ctScanCheckBox);
        searchPanel.add(ecgCheckBox);

        abnormalResultsCheckBox = new JCheckBox("Only show abnormal results");
        abnormalResultsCheckBox.setFont(new Font("Tahoma", Font.PLAIN, searchFontSize));
        searchPanel.add(abnormalResultsCheckBox);

        // Activity Type checkboxes
        activityTypeCheckBox1 = createActivityCheckBox("Food Intake");
        activityTypeCheckBox2 = createActivityCheckBox("Rest Quality");
        activityTypeCheckBox3 = createActivityCheckBox("Emotion");
        activityTypeCheckBox4 = createActivityCheckBox("Medication");
        searchPanel.add(activityTypeCheckBox1);
        searchPanel.add(activityTypeCheckBox2);
        searchPanel.add(activityTypeCheckBox3);
        searchPanel.add(activityTypeCheckBox4);

        centerPanel.add(searchPanel); 

        // Search Button
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Tahoma", Font.BOLD, searchFontSize));
        searchPanel.add(searchButton);

        // spacer - between search components and table. we can change if needed 
        centerPanel.add(Box.createVerticalStrut(5));  

        // Table 
        String[] columnNames = {"Date", "Exam Type", "Category ID", "Abnormality", "Modify", "Delete"};
       // Object[][] data = {}; // blank initially 

        tableModel = new DefaultTableModel(data, columnNames); /*{
            @Override
            public boolean isCellEditable(int row, int column) {
                // cell 4 & 5 (modify & delete) will not be editable 
                return column != 4 && column != 5;
            }
        };*/

        resultTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(resultTable);
        resultTable.setFillsViewportHeight(true); 

        // modify & delete buttons in last 2 cols of table 
        resultTable.getColumn("Modify").setCellRenderer(new ButtonRenderer());
        resultTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());

        resultTable.getColumn("Modify").setCellEditor(new ButtonEditor(new JCheckBox()));
        resultTable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));

        centerPanel.add(tableScrollPane);  

        add(centerPanel, BorderLayout.CENTER);

        searchTypeComboBox.addActionListener(e -> toggleSearchField());
    }
    
    public class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value == null) {
                return this;
            }
            setText(value.toString());
            return this;
        }
    }
    
    public class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                   
                    
                    int row = table.getSelectedRow();
                    if (isPushed) {
                    	
     
                        // Modify Button logic: Turn row into editable text fields
                        if (button.getText().equals("Modify")) {
   
                            modifyRecord(resultTable.getSelectedRow());
                        }
                        // Delete Button logic: Remove the row
                        else if (button.getText().equals("Delete")) {
                            //((DefaultTableModel) table.getModel()).removeRow(row);
                        	deleteRecord(resultTable.getSelectedRow());
                        }
                        else {
                        	//for debugging
                        	System.out.println("Text problem");
                        }
                        
                    }
                    isPushed = false;
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            return label;
        }

    
    }

    private JCheckBox createExamCheckBox(String label) {
        JCheckBox checkBox = new JCheckBox(label);
        checkBox.setFont(new Font("Tahoma", Font.PLAIN, searchFontSize));
        checkBox.setVisible(false);
        return checkBox;
    }

    private JCheckBox createActivityCheckBox(String label) {
        JCheckBox checkBox = new JCheckBox(label);
        checkBox.setFont(new Font("Tahoma", Font.PLAIN, searchFontSize));
        checkBox.setVisible(false);
        return checkBox;
    }

    private void setupExamSearch() {
        searchTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Exam Type", "Date"}));
        abnormalResultsCheckBox.setVisible(true);
        toggleSearchField();
        revalidate();
        repaint();
        searchButton.removeActionListener(act2);
        searchButton.addActionListener(act1);
        
	    String[] examColumns = { "Exam ID", "Date", "Exam Type", "Category ID", "Abnormality", "Modify", "Delete" };
	    setupTableColumns(examColumns);
	    this.examOractivity = true;
	    
	   /* this.mainFrame.hs.dbSwap(false);
		  
	    this.mainFrame.hs.runQuery("SELECT * FROM categories;", true);
	    
	    try {
	    	
	    	while(this.mainFrame.hs.rs.next()) {
	    		
	    		if(this.mainFrame.hs.current.getID() == this.mainFrame.hs.rs.getInt("User_ID")) {
	    			

					Object[] newRow = {this.mainFrame.hs.rs.getInt("category_ID"), this.mainFrame.hs.rs.getString("Date"), this.mainFrame.hs.rs.getString("Name"), this.mainFrame.hs.rs.getInt("category_ID"), this.mainFrame.hs.rs.getInt("Status"), "Modify", "Delete"};
					tableModel.addRow(newRow);
				
	    			
	    		}
	    		
	    	}
	    }catch(SQLException e) {
	    	
	    	e.printStackTrace();
	    }*/
	    	

	    
    }

    private void setupActivitySearch() {
        searchTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Activity Type", "Date"}));
        abnormalResultsCheckBox.setVisible(false);
        
        toggleSearchField();
        revalidate();
        repaint();
        
        searchButton.removeActionListener(act1);
        searchButton.addActionListener(act2);
        
        String[] activityColumns = { "Activity ID", "Date", "Food Intake", "Rest Quality", "Emotion", "Medication", "Modify", "Delete" };
	    setupTableColumns(activityColumns);
	    this.examOractivity = false;
	    
	    this.mainFrame.hs.dbSwap(false);
		  
	    this.mainFrame.hs.runQuery("SELECT * FROM activities;", true);
	    
	    try {
	    	
	    	while(this.mainFrame.hs.rs.next()) {
	    		
	    		if(this.mainFrame.hs.current.getID() == this.mainFrame.hs.rs.getInt("User_ID")) {
	    			

					Object[] newRow = {this.mainFrame.hs.rs.getInt("Alert_ID"), this.mainFrame.hs.rs.getString("Date"), this.mainFrame.hs.rs.getString("food_intake"), this.mainFrame.hs.rs.getInt("rest_quality"), this.mainFrame.hs.rs.getInt("emotion"), this.mainFrame.hs.rs.getInt("medicine"), "Modify", "Delete"};
					tableModel.addRow(newRow);
				
	    			
	    		}
	    		
	    	}
	    }catch(SQLException e) {
	    	
	    	e.printStackTrace();
	    }
    }
    
	private void setupTableColumns(String[] columnNames) {
	    tableModel.setDataVector(new Object[][]{}, columnNames); // Reset table with new columns
	    resultTable.getColumn("Modify").setCellRenderer(new ButtonRenderer());
	    resultTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
	    resultTable.getColumn("Modify").setCellEditor(new ButtonEditor(new JCheckBox()));
	    resultTable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));
	}
	
	

    private void toggleSearchField() {
        String selectedType = (String) searchTypeComboBox.getSelectedItem();
        
        if ("Date".equals(selectedType)) {
            searchField.setVisible(false);
            startDateField.setVisible(true);
            endDateField.setVisible(true);
            toLabel.setVisible(true);
            setExamCheckBoxesVisible(false);
            setActivityCheckBoxesVisible(false);
        } else if ("Exam Type".equals(selectedType)) {
            searchField.setVisible(false);
            startDateField.setVisible(false);
            endDateField.setVisible(false);
            toLabel.setVisible(false);
            setExamCheckBoxesVisible(true);
            setActivityCheckBoxesVisible(false);
        } else if ("Activity Type".equals(selectedType)) {
            searchField.setVisible(false);
            startDateField.setVisible(false);
            endDateField.setVisible(false);
            toLabel.setVisible(false);
            setExamCheckBoxesVisible(false);
            setActivityCheckBoxesVisible(true);
        } else {
            searchField.setVisible(true);
            startDateField.setVisible(false);
            endDateField.setVisible(false);
            toLabel.setVisible(false);
            setExamCheckBoxesVisible(false);
            setActivityCheckBoxesVisible(false);
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void setExamCheckBoxesVisible(boolean visible) {
        bloodCheckBox.setVisible(visible);
        cardiovascularCheckBox.setVisible(visible);
        gastrointestinalCheckBox.setVisible(visible);
        respiratoryCheckBox.setVisible(visible);
        ultrasoundCheckBox.setVisible(visible);
        xrayCheckBox.setVisible(visible);
        ctScanCheckBox.setVisible(visible);
        ecgCheckBox.setVisible(visible);
    }

    private void setActivityCheckBoxesVisible(boolean visible) {
        activityTypeCheckBox1.setVisible(visible);
        activityTypeCheckBox2.setVisible(visible);
        activityTypeCheckBox3.setVisible(visible);
        activityTypeCheckBox4.setVisible(visible);
        
    }
    
    //set default exam table function here 
    
    //

    private void searchRecords(boolean examResults) {
        // TODO: connect to backend function
    	
    	//add row example
    	//Object[] newRow = {"Test", "Test", "Test"};
    	//tableModel.addRow(newRow);
    	
    	//String[] arr = {"Food Intake", "Rest Quality", "Emotion", "Medicine"};
		 
    	tableModel.setRowCount(0);

    	map.clear();
    	map2.clear();
    	
    	
    	
    	
    	
		 if(examResults) {
			 System.out.println("True");
			 map.put(1,bloodCheckBox);
			 map.put(2,cardiovascularCheckBox);
			 map.put(3,gastrointestinalCheckBox); 
			 map.put(4,respiratoryCheckBox);
			 map.put(5,ultrasoundCheckBox);
			 map.put(6,xrayCheckBox); 
			 map.put(7,ctScanCheckBox); 
			 map.put(8,ecgCheckBox);
		 }
		 else {
			 System.out.println("False");
			 map2.put(1, activityTypeCheckBox1);
			 map2.put(2, activityTypeCheckBox2);
			 map2.put(3, activityTypeCheckBox3);
			 map2.put(4, activityTypeCheckBox4);
			 
		 }
		 
		    if ("Date".equals(searchTypeComboBox.getSelectedItem())) {
		        if (!validateDateFields()) {
		            return; // Exit if validation fails
		        }
		    }
		 
		// this.index = 0;
		 
    	
    	if(examResults) {
    		
    		if(searchTypeComboBox.getSelectedItem() == "Exam Type") {
    			
    			
    			
    			this.mainFrame.hs.dbSwap(false);
    			
    			map.forEach((key,box)->{
 
    				this.mainFrame.hs.runQuery("SELECT * FROM categories;", true);
    				
    				try {
    					//this.index = 0;
    					while(this.mainFrame.hs.rs.next()) {
    			
    						//check for userid
    						
    						if(box.isSelected() && this.mainFrame.hs.rs.getInt("Test_ID") == key) {
    						
    							if(abnormalResultsCheckBox.isSelected() && this.mainFrame.hs.rs.getInt("Status") == 0) {
    								//{"Date", "Exam Type", "Result", "Abnormality", "Modify", "Delete"};
    								
    								
    								Object[] newRow = {this.mainFrame.hs.rs.getInt("Exam_ID"), this.mainFrame.hs.rs.getString("Date"), this.mainFrame.hs.rs.getString("Name"), this.mainFrame.hs.rs.getInt("category_ID"), this.mainFrame.hs.rs.getInt("Status"), "Modify", "Delete"};
    								tableModel.addRow(newRow);
    								
    							}
    							else if(abnormalResultsCheckBox.isSelected() == false) {
    				    			
    								Object[] newRow = {this.mainFrame.hs.rs.getInt("Exam_ID"), this.mainFrame.hs.rs.getString("Date"), this.mainFrame.hs.rs.getString("Name"), this.mainFrame.hs.rs.getInt("category_ID"), this.mainFrame.hs.rs.getInt("Status"), "Modify", "Delete"};
    								tableModel.addRow(newRow);
    				    			
    				    		}
    						}
    				
    					//	++this.index;
    					}
    				}catch(SQLException e) {
    					
    					e.printStackTrace();
    				}
    				
    			});
    		}
    		
    		else {
    			
    			//this.index = 0;
    			
    			this.mainFrame.hs.dbSwap(false);
    			
    			this.mainFrame.hs.runQuery("SELECT * FROM categories;", true);
    			
    			//for categories that have no date
    			//delete at the end
    			String dt;
    			
    			try {
    			//	this.index=0;
    				while(this.mainFrame.hs.rs.next()) {
    					
    					
    					if(startDateField.getText() != "" && endDateField.getText() != "") {
    							if(this.mainFrame.hs.rs.getString("Date") == null) {
    								
    								dt = "2024-01-01";
    							}
    							else {
    								
    								dt = this.mainFrame.hs.rs.getString("Date");
    							}
    						 	LocalDate date = LocalDate.parse(dt);
    						 	LocalDate start = LocalDate.parse(startDateField.getText()); LocalDate end =
    						 	LocalDate.parse(endDateField.getText());
    						  
    						  
    						 	if(date.isAfter(start) && date.isBefore(end)) {
    						  
    						 		Object[] newRow = {this.mainFrame.hs.rs.getInt("Exam_ID"), this.mainFrame.hs.rs.getString("Date"), this.mainFrame.hs.rs.getString("Name"), this.mainFrame.hs.rs.getInt("category_ID"), this.mainFrame.hs.rs.getInt("Status"), "Modify", "Delete"};
    								tableModel.addRow(newRow);
    						  	
    						  
    						  	} 
    						  
    						  } 
    						//	++this.index;
    					
    						 }
    						  
    						}catch(SQLException e) {
    						  
    						  e.printStackTrace(); 
    						}
    						  
    			}			
    		
    	}
    	else {
    		
    		if(searchTypeComboBox.getSelectedItem() == "Activity Type") {
    	
    			this.mainFrame.hs.dbSwap(false);
    			
    			
    			map2.forEach((key, box)->{
    				
    				this.mainFrame.hs.runQuery("SELECT * FROM activities;", true);
    			//	this.index=0;
    				try {
    					
    					while(this.mainFrame.hs.rs.next()) {
    						
    						Object[] setVals = {this.mainFrame.mon.getActivityID(),this.mainFrame.hs.rs.getString("Date"), null, null, null, null, "Modify", "Delete"};
    						
    						int checkNull = 0;
    						
    						for(int i=0; i<4; i++) {
    							
    							if(setVals[i+2] == null && resultTable.getRowCount() > 0) {
    								
    								++checkNull;
    							}
    						}
    						if(checkNull < 4) {
    							tableModel.addRow(setVals);
    							
    						}
    						//fix after
    						
    						
    						int fi = this.mainFrame.hs.rs.getInt("food_intake");
    						int rq = this.mainFrame.hs.rs.getInt("rest_quality");
    						int em = this.mainFrame.hs.rs.getInt("emotion");
    						int med = this.mainFrame.hs.rs.getInt("medicine");
    						
    						
    						if(box.isSelected()) {
    							 switch(key) {
    							  
    							  case 1: 
    								  System.out.println(this.mainFrame.hs.rs.getInt("food_intake"));
    								  resultTable.setValueAt(fi, resultTable.getRowCount()-1, 2);
    								  
    							
    							  case 2:
    							  
    								  System.out.println(this.mainFrame.hs.rs.getInt("rest_quality")); 
    								  resultTable.setValueAt(rq, resultTable.getRowCount()-1, 3);
    							
    							
    							  case 3:
    							  
    								  System.out.println(this.mainFrame.hs.rs.getInt("emotion")); 
    								  resultTable.setValueAt(em, resultTable.getRowCount()-1,4);
    						
    							  case 4:
    							  
    								  System.out.println(this.mainFrame.hs.rs.getInt("medicine")); 
    								  resultTable.setValueAt(med, resultTable.getRowCount()-1,5);
    								 
    							  
    							
    					
    								
    								 
    							  }
    							
    						}
    						
    						
    						
    					//	++index;
    					}
    					
    				}catch(SQLException e) {
    					
    					e.printStackTrace();
    				}
    				
    			
    			});
    			
    			
    			
    		}
    		
    		else {
    			
    			
    			this.mainFrame.hs.dbSwap(false);
    			
    			this.mainFrame.hs.runQuery("SELECT * FROM activities;", true);
    			
    			try {
    			//	this.index=0;
    				while(this.mainFrame.hs.rs.next()) {
    					
    					  if(startDateField.getText() != "" && endDateField.getText() != "") {
    						  LocalDate date = LocalDate.parse(this.mainFrame.hs.rs.getString("Date"));
    						  LocalDate start = LocalDate.parse(startDateField.getText()); LocalDate end =
    						  LocalDate.parse(endDateField.getText());
    						  
    						  
    						  if(date.isAfter(start) && date.isBefore(end)) {
    						  
    			
    							 
    							  //add medicine
    							  Object[] newRow = {this.mainFrame.mon.getActivityID(),this.mainFrame.hs.rs.getString("Date"), this.mainFrame.hs.rs.getInt("food_intake"), this.mainFrame.hs.rs.getInt("rest_quality"), this.mainFrame.hs.rs.getInt("emotion"), this.mainFrame.hs.rs.getInt("medicine"), "Modify", "Delete"};
  								  tableModel.addRow(newRow);
  						  	
    						  } 
    						  
    					  } 
    				//	  ++this.index;
    				}
    				
    			}catch(SQLException e) {
    				
    				e.printStackTrace();
    			}
    		}
    		
    	}
    	
    	this.mainFrame.hs.dbSwap(true);
    
    
    	for (int i = 0; i < resultTable.getRowCount(); i++) {
    		
    	    final int index = i;  // Make sure this is final
    	    JButton deleteButton = new JButton("Delete");
    	    deleteButton.addActionListener(e -> deleteRecord(index));  

    	    JButton modifyButton = new JButton("Modify");
    	    modifyButton.addActionListener(e -> modifyRecord(index));  
    	 

          //  resultTable.setValueAt(deleteButton, i, 4); // to house delete button
           // resultTable.setValueAt(modifyButton, i, 5); //to house modify button
        }
    	
    }
    	
    	//check either exam type or date
    	
    	
    	private Object modifyRecord(int i) {
		// TODO Auto-generated method stub
			
		Object[] rowData = new Object[6];
	
		int len = 0;
		
		if(examOractivity) {
			
			len = 5;
		}
		else {
			
			len = 6;
		}
		
		
		System.out.println("Row: " + i);
		
		
		
		
		
		for(int j=0; j<len; j++) {
			rowData[j] = resultTable.getValueAt(i, j);
		}
		
		System.out.println(Arrays.toString(rowData));
		
		
		if(this.examOractivity) {
			this.mainFrame.hs.dbSwap(false);
			this.mainFrame.hs.runQuery("UPDATE categories SET Date = " + rowData[1] + ", Name = '" + rowData[2] + "', Status = "  + rowData[4] + " WHERE category_ID = " + rowData[3] + ";",false);
			
		}
		else {
			
			this.mainFrame.hs.dbSwap(false);
			this.mainFrame.hs.runQuery("UPDATE activities SET Date=" + rowData[1] + ", food_intake=" + rowData[2] + ", rest_quality=" + rowData[3] + ", emotion=" + rowData[4] + ", medicine WHERE Alert_ID=" + rowData[0] + ";",false);
	
		}
		
		this.mainFrame.hs.dbSwap(true);
    			
		return null;
	}

		private Object deleteRecord(int i) {
			
				
			Object[] rowData = new Object[6];
			
			int len = 0;
			
			if(examOractivity) {
				
				len = 5;
			}
			else {
				
				len = 6;
			}
			
			
			System.out.println("Row: " + i);
			
			
			
			
			for(int j=0; j<len; j++) {
				rowData[j] = resultTable.getValueAt(i, j);
			}
			
			System.out.println(Arrays.toString(rowData));
			
			if(this.examOractivity) {
				this.mainFrame.hs.dbSwap(false);
				this.mainFrame.hs.runQuery("DELETE FROM categories WHERE category_ID =" + rowData[2], false);
				tableModel.removeRow(i);
			}
			else {
				
				this.mainFrame.hs.dbSwap(false);
				this.mainFrame.hs.runQuery("DELETE FROM activities WHERE Alert_ID =" + rowData[0], false);
				tableModel.removeRow(i);
			}
			
			this.mainFrame.hs.dbSwap(true);
			
				//tableModel.removeRow(i);
		// TODO Auto-generated method stub
		return null;
	}
		
		private boolean validateDateFields() {
		    String startDateText = startDateField.getText().trim();
		    String endDateText = endDateField.getText().trim();

		    if (startDateText.isEmpty() || endDateText.isEmpty()) {
		        JOptionPane.showMessageDialog(this, "Both start date and end date must be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
		        return false;
		    }

		    try {
		        LocalDate startDate = LocalDate.parse(startDateText);
		        LocalDate endDate = LocalDate.parse(endDateText);

		        if (startDate.isAfter(endDate)) {
		            JOptionPane.showMessageDialog(this, "Start date must be before or equal to end date.", "Validation Error", JOptionPane.ERROR_MESSAGE);
		            return false;
		        }
		    } catch (DateTimeParseException e) {
		        JOptionPane.showMessageDialog(this, "Dates must be in yyyy-MM-dd format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
		        return false;
		    }

		    return true;
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

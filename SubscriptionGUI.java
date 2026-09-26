import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Handles the GUI for managing AI subscription plans.
 * Allows to add, manage, and display of PersonalPlan and ProPlan objects.
 * 
 * Demonstrates use of inheritance, polymorphism and runtime type checking.
 * 
 * @author Anusha Bishwas
 * @studentID np05cp4a250031
 * @version Milestone 2
 */
public class SubscriptionGUI extends JFrame {
    
    // ======================== DATA STORAGE ========================
    
    private ArrayList<AIModel> subscriptionPlans;  // Stores both PersonalPlan and ProPlan objects
    
    // ======================== INPUT FIELDS ========================
    
    // Input fields for creating plans
    private JTextField modelNameField;
    private JTextField priceField;
    private JTextField paramCountField;
    private JTextField contextWindowField;
    private JTextField promptsQuotaField;    //  PersonalPlan only
    private JTextField teamSlotsField;       // ProPlan only
    
    // Input fields for operations on existing plans
    private JTextField promptTextField;
    private JTextField responseLengthField;
    private JTextField teamMemberNameField;
    private JTextField indexNumberField;
    
    // Display area
    private JTextArea displayArea;
    
    // ======================== CONSTRUCTOR ========================
    
    /**
     * Sets up and displays the main GUI window.
     */
    public SubscriptionGUI() {
        // Holds all created plans
        subscriptionPlans = new ArrayList<AIModel>();
        
        // Set up the main window
        setTitle("LaughTale Neuralix");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Tabs for better organization of features
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Plans", createAddPlanPanel());
        tabbedPane.addTab("Manage Plans", createManagePlansPanel());
        tabbedPane.addTab("Display Plans", createDisplayPanel());
        add(tabbedPane, BorderLayout.CENTER);
        
        // Final window settings
        setSize(850, 650);
        setLocationRelativeTo(null);  
        setVisible(true);
    }
    
    // ======================== ADD PLAN PANEL ========================
    
    /**
     * Panel for adding new subscription plans.
     */
    private JPanel createAddPlanPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Model Name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Model Name:"), gbc);
        gbc.gridx = 1;
        modelNameField = new JTextField(25);
        panel.add(modelNameField, gbc);
        
        // Price
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Price (Rs. per 1 Lakh tokens):"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField(25);
        panel.add(priceField, gbc);
        
        // Parameter Count
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Parameter Count (billions):"), gbc);
        gbc.gridx = 1;
        paramCountField = new JTextField(25);
        panel.add(paramCountField, gbc);
        
        // Context Window
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Context Window (tokens):"), gbc);
        gbc.gridx = 1;
        contextWindowField = new JTextField(25);
        panel.add(contextWindowField, gbc);
        
        // Personal Plan specific field
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Initial Prompts Quota (Personal):"), gbc);
        gbc.gridx = 1;
        promptsQuotaField = new JTextField(25);
        panel.add(promptsQuotaField, gbc);
        
        // Add Personal Plan button
        JButton addPersonalBtn = new JButton("Add Personal Plan");
        addPersonalBtn.setBackground(new Color(173, 216, 230));
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(addPersonalBtn, gbc);
        
        // Pro Plan specific field
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Team Member Slots (Pro):"), gbc);
        gbc.gridx = 1;
        teamSlotsField = new JTextField(25);
        panel.add(teamSlotsField, gbc);
        
        // Add Pro Plan button
        JButton addProBtn = new JButton("Add Pro Plan");
        addProBtn.setBackground(new Color(200, 200, 230));
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(addProBtn, gbc);
        
        // Clear button
        JButton clearBtn = new JButton("Clear All Fields");
        clearBtn.setBackground(new Color(255, 175, 175));
        gbc.gridx = 0; gbc.gridy = 8;
        panel.add(clearBtn, gbc);
        
        // Button actions
        addPersonalBtn.addActionListener(e -> addPersonalPlan());
        addProBtn.addActionListener(e -> addProPlan());
        clearBtn.addActionListener(e -> clearFields());
        
        return panel;
    }
    //  ===================== MANAGE PANEL ===================
    /**
     * Creates the panel to manage plans.
     */
    private JPanel createManagePlansPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Index selection 
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Plan Index Number:"), gbc);
        gbc.gridx = 1;
        indexNumberField = new JTextField(20);
        panel.add(indexNumberField, gbc);
        
        JButton checkPlanBtn = new JButton("Check Plan Type");
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(checkPlanBtn, gbc);
        
        // Prompt simulation 
        gbc.gridy = 2;
        panel.add(new JLabel("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"), gbc);
        
        gbc.gridy = 3;
        panel.add(new JLabel("★ GIVE PROMPT (Works for both plan types)"), gbc);
        
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Prompt Text:"), gbc);
        gbc.gridx = 1;
        promptTextField = new JTextField(20);
        panel.add(promptTextField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Expected Response Length (tokens):"), gbc);
        gbc.gridx = 1;
        responseLengthField = new JTextField(20);
        panel.add(responseLengthField, gbc);
        
        JButton givePromptBtn = new JButton("Send Prompt");
        givePromptBtn.setBackground(new Color(200, 230, 200));
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(givePromptBtn, gbc);
        
        // Team management 
        gbc.gridy = 7;
        panel.add(new JLabel("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"), gbc);
        
        gbc.gridy = 8;
        panel.add(new JLabel("★ TEAM MANAGEMENT (Pro Plan only)"), gbc);
        
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Team Member Name:"), gbc);
        gbc.gridx = 1;
        teamMemberNameField = new JTextField(20);
        panel.add(teamMemberNameField, gbc);
        
        JButton addMemberBtn = new JButton("Add Team Member");
        addMemberBtn.setBackground(new Color(200, 200, 230));
        gbc.gridx = 0; gbc.gridy = 10;
        gbc.gridwidth = 1;
        panel.add(addMemberBtn, gbc);
        
        JButton removeMemberBtn = new JButton("Remove Team Member");
        removeMemberBtn.setBackground(new Color(230, 200, 200));
        gbc.gridx = 1;
        panel.add(removeMemberBtn, gbc);
        
        // Button actions
        checkPlanBtn.addActionListener(e -> checkPlanType());
        givePromptBtn.addActionListener(e -> givePrompt());
        addMemberBtn.addActionListener(e -> addTeamMember());
        removeMemberBtn.addActionListener(e -> removeTeamMember());
        
        return panel;
    }
    // ==================DISPLAY PANEL =======================
    /**
     * Creates the panel to display plans.
     */
    private JPanel createDisplayPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        displayArea = new JTextArea(25, 70);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton displayAllBtn = new JButton("Display All Subscriptions");
        displayAllBtn.setBackground(new Color(230, 230, 200));
        panel.add(displayAllBtn, BorderLayout.SOUTH);
        
        displayAllBtn.addActionListener(e -> displayAllPlans());
        
        return panel;
    }
    
    // ======================== VALIDATION METHOD ========================
    
    /**
     * Checks if entered index is valid.
     */
    private int getValidIndex() {
        String input = indexNumberField.getText().trim();
        
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an index number.", 
                                         "Input Required", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        
        try {
            int index = Integer.parseInt(input);
            
            if (index >= 0 && index < subscriptionPlans.size()) {
                return index;
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Index " + index + " is out of range.\nValid range: 0 to " + (subscriptionPlans.size() - 1),
                    "Invalid Index", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Invalid input: '" + input + "' is not a valid number.\nPlease enter digits only.",
                "Number Format Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }
    
    // ======================== ADD PLAN METHODS ========================
    
    /**
     * Creates a new PersonalPlan and adds it to the subscription list.
     */
    private void addPersonalPlan() {
        try {
            String modelName = modelNameField.getText().trim();
            String priceText = priceField.getText().trim();
            String paramText = paramCountField.getText().trim();
            String contextText = contextWindowField.getText().trim();
            String promptsText = promptsQuotaField.getText().trim();
            
            if (modelName.isEmpty() || priceText.isEmpty() || paramText.isEmpty() || 
                contextText.isEmpty() || promptsText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields before adding a plan.",
                                             "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double price = Double.parseDouble(priceText);
            int paramCount = Integer.parseInt(paramText);
            int contextWindow = Integer.parseInt(contextText);
            int promptsQuota = Integer.parseInt(promptsText);
            
            PersonalPlan plan = new PersonalPlan(modelName, price, paramCount, contextWindow, promptsQuota);
            subscriptionPlans.add(plan);
            
            JOptionPane.showMessageDialog(this, "Personal Plan added successfully!\nTotal plans: " + 
                                         subscriptionPlans.size(), "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please check your inputs.\n" +
                                         "Price, Parameter Count, Context Window, and Prompts Quota must be numbers.",
                                         "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Creates a new ProPlan and adds it to the subscription list.
     */
    private void addProPlan() {
        try {
            String modelName = modelNameField.getText().trim();
            String priceText = priceField.getText().trim();
            String paramText = paramCountField.getText().trim();
            String contextText = contextWindowField.getText().trim();
            String slotsText = teamSlotsField.getText().trim();

            if (modelName.isEmpty() || priceText.isEmpty() || paramText.isEmpty() || 
                contextText.isEmpty() || slotsText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields before adding a plan.",
                                             "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double price = Double.parseDouble(priceText);
            int paramCount = Integer.parseInt(paramText);
            int contextWindow = Integer.parseInt(contextText);
            int teamSlots = Integer.parseInt(slotsText);

            ProPlan plan = new ProPlan(modelName, price, paramCount, contextWindow, teamSlots);
            subscriptionPlans.add(plan);
            
            JOptionPane.showMessageDialog(this, "Pro Plan added successfully!\nTotal plans: " + 
                                         subscriptionPlans.size(), "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please check your inputs.\n" +
                                         "Price, Parameter Count, Context Window, and Team Slots must be numbers.",
                                         "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ======================== CLEAR METHOD ========================
    
    /**
     * Clears all text fields and input areas on the GUI.
     */
    private void clearFields() {
        modelNameField.setText("");
        priceField.setText("");
        paramCountField.setText("");
        contextWindowField.setText("");
        promptsQuotaField.setText("");
        teamSlotsField.setText("");
        promptTextField.setText("");
        responseLengthField.setText("");
        teamMemberNameField.setText("");
        indexNumberField.setText("");
    }
    
    // ======================== DISPLAY ========================
    
    /**
     * Displays all subscription plans in the text area.
     */
    private void displayAllPlans() {
        if (subscriptionPlans.isEmpty()) {
            displayArea.setText(" ╔═════════════════════════════════════════════╗\n" +
                               " ║     No subscriptions added yet.        ║\n" +
                               " ║ Use the 'Add Plans' tab to get started.║\n" +
                               " ╚═════════════════════════════════════════════╝");
            return;
        }
        
        StringBuilder output = new StringBuilder();
        output.append("╔══════════════════════════════════════════════════════════════════════╗\n");
        output.append("║                     AI SUBSCRIPTION MANAGEMENT SYSTEM        ║\n");
        output.append("║                         Total Plans: ").append(String.format("%-3d", subscriptionPlans.size())).append("                     ║\n");
        output.append("╚══════════════════════════════════════════════════════════════════════╝\n\n");
        
        for (int i = 0; i < subscriptionPlans.size(); i++) {
            output.append("┌─────────────────────────────────────────────────────────────────┐\n");
            output.append("│               PLAN ").append(String.format("%-3d", i)).append("                                   │\n");
            output.append("├─────────────────────────────────────────────────────────────────┤\n");
            output.append(subscriptionPlans.get(i).display());
            output.append("\n└─────────────────────────────────────────────────────────────────┘\n\n");
        }
        
        displayArea.setText(output.toString());
    }
    
    // ======================== TYPE CHECK (USES instanceof) ========================
    
    /**
     * Checks and shows the selected plan type.
     */
    private void checkPlanType() {
        int index = getValidIndex();
        if (index == -1) return;
        
        AIModel plan = subscriptionPlans.get(index);
        
        if (plan instanceof PersonalPlan) {
            JOptionPane.showMessageDialog(this, 
                "Plan at index " + index + " is a PERSONAL PLAN.\n" +
                "This plan has a monthly prompt quota.",
                "Plan Type Detected", JOptionPane.INFORMATION_MESSAGE);
        } else if (plan instanceof ProPlan) {
            JOptionPane.showMessageDialog(this, 
                "Plan at index " + index + " is a PRO PLAN.\n" +
                "This plan has team collaboration features.",
                "Plan Type Detected", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Unknown plan type.", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ======================== PROMPT METHOD (POLYMORPHISM) ========================
    
    /**
     * Sends a prompt.
     */
    private void givePrompt() {
        int index = getValidIndex();
        if (index == -1) return;
        
        AIModel plan = subscriptionPlans.get(index);
        String promptText = promptTextField.getText().trim();
        String lengthText = responseLengthField.getText().trim();
        
        if (promptText.isEmpty() || lengthText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both prompt text and expected response length.",
                                         "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int expectedLength = Integer.parseInt(lengthText);
            
            String result = plan.enterPrompt(promptText, expectedLength);
            
            JOptionPane.showMessageDialog(this, result, "Prompt Result", JOptionPane.INFORMATION_MESSAGE);
            displayAllPlans(); 
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Response length must be a valid number.",
                                         "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ======================== TEAM MANAGEMENT ========================
    
    /**
     * Adds a team member to a Pro Plan subscription.
     */
    private void addTeamMember() {
        int index = getValidIndex();
        if (index == -1) return;
        
        AIModel plan = subscriptionPlans.get(index);
        
        if (plan instanceof ProPlan) {
            String memberName = teamMemberNameField.getText().trim();
            
            if (memberName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a team member name.",
                                             "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            ProPlan proPlan = (ProPlan) plan;
            String result = proPlan.addTeamMember(memberName);
            
            JOptionPane.showMessageDialog(this, result, "Team Management", JOptionPane.INFORMATION_MESSAGE);
            displayAllPlans();  
            
        } else {
            JOptionPane.showMessageDialog(this, 
                "Team members can only be added to PRO PLANS.\n" +
                "The plan at index " + index + " is a Personal Plan.",
                "Operation Not Allowed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Removes a team member from a Pro Plan subscription.
    */
    private void removeTeamMember() {
        int index = getValidIndex();
        if (index == -1) return;
        
        AIModel plan = subscriptionPlans.get(index);
        
        if (plan instanceof ProPlan) {
            String memberName = teamMemberNameField.getText().trim();
            
            if (memberName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the team member name to remove.",
                                             "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            ProPlan proPlan = (ProPlan) plan;
            String result = proPlan.removeTeamMember(memberName);
            
            JOptionPane.showMessageDialog(this, result, "Team Management", JOptionPane.INFORMATION_MESSAGE);
            displayAllPlans();  
            
        } else {
            JOptionPane.showMessageDialog(this, 
                "Team members can only be removed from PRO PLANS.\n" +
                "The plan at index " + index + " is a Personal Plan.",
                "Operation Not Allowed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ======================== MAIN METHOD ========================
    
    /**
     * Entry point of the program.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SubscriptionGUI();
            }
        });
    }
}
import java.util.ArrayList;

/**
 * This is a Pro Plan subscription for team users.
 * <p>
 * Pro Plan users have unlimited prompts but there is a limit on how many 
 * team members can be added. Team members can be added and removed, and
 * the available slotsupdate accordingly.
 * </p>
 *
 * @author Student Name
 * @studentID np05cp4a250031
 * @version Milestone 2
 */
public class ProPlan extends AIModel {
    
    // ======================== ATTRIBUTES ========================
    
    private int availableSlots;              // Number of team slots still available
    private ArrayList<String> teamMembers;   // List of current team members
    
    // ======================== CONSTRUCTOR ========================
    
    /**
     * Creates a new ProPlan subscription for teams.
     * 
     * @param modelName the name of the AI model
     * @param price the cost per 1 Lakh tokens in NPR
     * @param parameterCount the model parameters in billions
     * @param contextWindow the maximum token limit of the model
     * @param teamSlots the initial number of available team member slots
     */
    public ProPlan(String modelName, double price, int parameterCount, 
                   int contextWindow, int teamSlots) {
        super(modelName, price, parameterCount, contextWindow);
        this.availableSlots = teamSlots;
        this.teamMembers = new ArrayList<String>();  // Start with empty team
    }
    
    // ======================== GET METHODS ========================
    
    /**
     * Gets the number of available team slots remaining.
     */
    public int getAvailableSlots() {
        return availableSlots;
    }
    
    /**
     *Gets the list of current team members.
     */
    public ArrayList<String> getTeamMembers() {
        return teamMembers;
    }
    
    // ======================== TEAM METHODS ========================
    
    /**
     * Adds a new member to the team if a slot is available.
     */
    public String addTeamMember(String memberName) {
        
        if (availableSlots <= 0) {
            return "Cannot add " + memberName + " - no team slots available.";
        }
        
        teamMembers.add(memberName);
        availableSlots--;
        return "Added " + memberName + " to the team. " + availableSlots + " slots remaining.";
    }
    
    /**
     * Removes a team member if they exist and frees up a slot.
     */
    public String removeTeamMember(String memberName) {
        
        if (!teamMembers.contains(memberName)) {
            return "Error: " + memberName + " is not a member of this team.";
        }
        
        teamMembers.remove(memberName);
        availableSlots++;
        return "Removed " + memberName + " from the team. " + availableSlots + " slots now available.";
    }
    
    // ======================== PROMPT METHOD ========================
    
    /**
     * Handles a prompt from a Pro Plan user.
     * Only checks token limit
     */
    @Override
    public String enterPrompt(String promptText, int expectedLength) {
        
        int totalTokens = calculateTokens(promptText) + expectedLength;
        
        if (totalTokens > getContextWindow()) {
            return "Token limit exceeded! Your request needs " + totalTokens + 
                   " tokens but this model only accepts " + getContextWindow() + " tokens.";
        }
        
        return "Pro Plan prompt processed successfully!\n" +
               "Prompt: " + promptText + "\n" +
               "Expected output length: " + expectedLength + " tokens\n" +
               "Total tokens used: " + totalTokens + "\n" +
               "(No quota limit - you're on the Pro Plan!)";
    }
    
    /**
     * Shows all Pro Plan details including inherited AIModel attributes.
     */
    @Override
    public String display() {
        
        String memberList;
        if (teamMembers.isEmpty()) {
            memberList = "None";
        } else {
            memberList = String.join(", ", teamMembers);
        }
        
            
    return "Model Name: " + getModelName() + 
           "\nPrice: Rs. " + getPrice() + " per 1 Lakh tokens" +
           "\nParameter Count: " + getParameterCount() + " billion" +
           "\nContext Window: " + getContextWindow() + " tokens" +
           "\n -------- Pro Plan Details --------" +
           "\n Available slots: " + availableSlots +
           "\n Team members: " + memberList +"\n";
        }
}
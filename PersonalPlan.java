/**
 * This is a Personal Plan subscription for individual users.
 * <p>
 * Personal Plan users have a monthly limit on prompts.
 * They can buy additional prompts if needed.
 * </p>
 *
 * @author Anusha Bishwas
 * @studentID np05cp4a250031
 * @version Milestone 2
 */
public class PersonalPlan extends AIModel {
    
    // ======================== ATTRIBUTE ========================
    
    private int promptsRemaining;   // Number of prompts left this month
    
    // ======================== CONSTRUCTOR ========================
    
    /**
     * Creates a new PersonalPlan subscription.
     * 
     * @param modelName the name of the AI model
     * @param price the cost per 1 Lakh tokens in NPR
     * @param parameterCount the model parameters in billions
     * @param contextWindow the maximum token limit of the model
     * @param promptsRemaining the initial number of prompts for the monthly quota
     */
    public PersonalPlan(String modelName, double price, int parameterCount, 
                        int contextWindow, int promptsRemaining) {
        super(modelName, price, parameterCount, contextWindow);
        this.promptsRemaining = promptsRemaining;
    }
    
    // ======================== GET METHOD ========================
    
    /**
     * Gets remaining prompts.
     */
    public int getPromptsRemaining() {
        return promptsRemaining;
    }
    
    // ======================== PLAN METHODS ========================
    
    /**
     * Lets the user buy additional prompts.
     */
    public String buyPrompts(int additionalPrompts) {
    
        if (additionalPrompts <= 0) {
            return "Error: Must enter a positive number. Consider upgrading to Pro Plan.";
        }
        promptsRemaining += additionalPrompts;
        return "Success! Added " + additionalPrompts + " prompts. You now have " + 
               promptsRemaining + " prompts remaining this month.";
    }
    
    /**
     * Handles a prompt from a Personal Plan user.
     * Checks quota and token limit.
     */
    @Override
    public String enterPrompt(String promptText, int expectedLength) {
       
        if (promptsRemaining <= 0) {
            return "Monthly quota reached! Please purchase additional prompts or upgrade to Pro Plan.";
        }
        
       
        int totalTokens = calculateTokens(promptText) + expectedLength;
        if (totalTokens > getContextWindow()) {
            return "Token limit exceeded! Your request needs " + totalTokens + 
                   " tokens but this model only accepts " + getContextWindow() + " tokens.";
        }
        
      
        promptsRemaining--;
        return "Prompt processed successfully!\n" +
               "Prompt: " + promptText + "\n" +
               "Expected output length: " + expectedLength + " tokens\n" +
               "Total tokens used: " + totalTokens + "\n" +
               "Prompts remaining this month: " + promptsRemaining;
    }
    
    /**
     * Shows all Personal Plan details including inherited AIModel attributes.
     */
    @Override
    public String display() {
    return "Model Name: " + getModelName() + 
           "\nPrice: Rs. " + getPrice() + " per 1 Lakh tokens" +
           "\nParameter Count: " + getParameterCount() + " billion" +
           "\nContext Window: " + getContextWindow() + " tokens" +
           "\n -------- Personal Plan Details -------- " +
           "\n Prompts remaining: " + promptsRemaining +"\n";
    }
}
/**
 * This is the parent class for all AI model subscriptions.
 * <p>
 * This class stores basic details that all AI models have in common like
 * model name, pricing, parameter count, and context window size.
 *This class is made abstract because we don't create objects of AIModel directly. 
 * It will be used by subclasses like PersonalPlan and ProPlan.
 * </p>
 *
 * @author Anusha Bishwas
 * @studentId np05cp4a250031
 * @version Milestone 2
 */
public abstract class AIModel {
    
    // ======================== ATTRIBUTES ========================
    
    private String modelName;       // Name of the AI model 
    private double price;           // Cost per 1 lakh token (NPR)
    private int parameterCount;     // Model size (in billions) 
    private int contextWindow;      // Maximum tokens capacity
    
    // ======================== CONSTRUCTOR ========================
    
    /**
     * Creates an AIModel with basic details.
     *
     * @param modelName the name of the AI model 
     * @param price the cost in Nepali Rupees per 1 Lakh tokens
     * @param parameterCount the number of model parameters in billions
     * @param contextWindow the maximum token limit the model can process
     */
    public AIModel(String modelName, double price, int parameterCount, int contextWindow) {
        this.modelName = modelName;
        this.price = price;
        this.parameterCount = parameterCount;
        this.contextWindow = contextWindow;
    }
    
    // ======================== GET METHODS ========================
    
    /**
     * Gets the name of the AI model.
     */
    public String getModelName() {
        return modelName;
    }
    
    /**
     * Gets the price per 1 Lakh tokens in Nepali Rupees.
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Gets the parameter count of the model in billions.
     */
    public int getParameterCount() {
        return parameterCount;
    }
    
    /**
     * Gets the context window size of the model.
     */
    public int getContextWindow() {
        return contextWindow;
    }
    
    // ======================== METHODS ========================
    
    /**
     * Roughly counts how many token the text will use.
     * It's not exact, just a simple calculation for this project. 
     */
    public int calculateTokens(String text) {
        
        if (text == null || text.isEmpty()) {
            return 0;
        }
    
        String[] words = text.split("\\s+");
        
        //Approximate token inflation due to punctuation and formatting
        return (int) Math.ceil(words.length * 1.3);
    }
    
    // ======================== ABSTRACT METHODS ========================
    
    /**
     * Displays all details of the AI model as a readable String.
     */
    public abstract String display();
    
    /**
     * Handles a prompt from the user and returns the result.
     * This method is abstract because PersonalPlan and ProPlan handle prompts differently.
     */
    public abstract String enterPrompt(String promptText, int expectedLength);
}
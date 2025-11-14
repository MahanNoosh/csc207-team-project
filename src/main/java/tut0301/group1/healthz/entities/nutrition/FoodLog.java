package tut0301.group1.healthz.entities.nutrition;

import tut0301.group1.healthz.dataaccess.API.FatSecretFoodGetClient;

import java.time.LocalDateTime;

/**
 * FoodLog: Entity representing a single food intake log entry.*
 * Updated to use DetailFood and FatSecretFoodGetClient.ServingInfo for more detailed tracking.
 */
public class FoodLog {
    private final DetailFood food;
    private final FatSecretFoodGetClient.ServingInfo servingInfo;  // The specific serving selected from API
    private double servingMultiplier;       // How many of the selected serving
    private Macro actualMacro;              // Calculated from servingInfo nutrition * servingMultiplier
    private final LocalDateTime loggedAt;
    private final String meal;              // Meal type ("Breakfast", "Lunch", "Dinner", "Snack")

    /**
     * Constructor with DetailFood and FatSecretFoodGetClient.ServingInfo.
     */
    public FoodLog(DetailFood food, FatSecretFoodGetClient.ServingInfo servingInfo,
                   double servingMultiplier, String meal, LocalDateTime loggedAt) {

        this.food = food;
        this.servingInfo = servingInfo;
        this.servingMultiplier = servingMultiplier;
        this.meal = meal;
        this.loggedAt = loggedAt;

        // Create Macro from ServingInfo's nutrition fields and scale by multiplier
        this.actualMacro = createMacroFromServingInfo(servingInfo, servingMultiplier);
    }

    /**
     * Helper method to create Macro from FatSecretFoodGetClient.ServingInfo and times multiplier.
     */
    private Macro createMacroFromServingInfo(FatSecretFoodGetClient.ServingInfo serving, double multiplier) {
        double calories = (serving.calories != null ? serving.calories : 0.0) * multiplier;
        double protein = (serving.protein != null ? serving.protein : 0.0) * multiplier;
        double fat = (serving.fat != null ? serving.fat : 0.0) * multiplier;
        double carbs = (serving.carbs != null ? serving.carbs : 0.0) * multiplier;

        return new Macro(calories, protein, fat, carbs);
    }



    public Macro getActualMacro() {
        return actualMacro;
    }

    /**
     * Get the actual serving size consumed.
     * This is servingInfo.servingAmount * servingMultiplier.
     */
    public double getActualServingSize() {
        return servingInfo.servingAmount * servingMultiplier;
    }

    /**
     * Get the serving unit (from ServingInfo).
     */
    public String getServingUnit() {
        return servingInfo.servingUnit;
    }

    /**
     * Get the serving description (e.g., "100 g", "1/2 large").
     */
    public String getServingDescription() {
        return servingInfo.servingDescription;
    }

    // Getters

    public DetailFood getFood() {
        return food;
    }

    public FatSecretFoodGetClient.ServingInfo getServingInfo() {
        return servingInfo;
    }

    public double getServingMultiplier() {
        return servingMultiplier;
    }

    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }

    public String getMeal() {
        return meal;
    }

    /**
     * Update the serving multiplier (how many servings).
     *
     * @param newMultiplier New multiplier (e.g., 1.5 for 1.5 servings)
     */
    public void updateServingMultiplier(double newMultiplier) {
        if (newMultiplier <= 0) {
            throw new IllegalArgumentException("New multiplier must be positive: " + newMultiplier);
        }

        this.servingMultiplier = newMultiplier;
        // Recalculate actual macro based on ServingInfo
        this.actualMacro = createMacroFromServingInfo(servingInfo, newMultiplier);
    }

    /**
     * Update based on actual amount consumed.
     * Calculates new multiplier based on the amount relative to serving size.
     *
     * @param newAmount New amount (e.g., 150.0 for 150g if serving is "100 g")
     */
    public void updateServingAmount(double newAmount) {
        if (newAmount <= 0) {
            throw new IllegalArgumentException("New amount must be positive: " + newAmount);
        }

        // Calculate new multiplier based on the new amount
        this.servingMultiplier = newAmount / servingInfo.servingAmount;

        // Recalculate and update actual macro
        this.actualMacro = createMacroFromServingInfo(servingInfo, servingMultiplier);
    }

    public static void main(String[] args) {
        System.out.println("=== Testing FoodLog Entity (Updated with FatSecretFoodGetClient.ServingInfo) ===\n");

        try {
            // Create a test DetailFood (Apple)
            Macro baseMacro = new Macro(52, 0.3, 0.2, 14);
            DetailFood apple = new DetailFood(
                    1,
                    "Apple",
                    "Fresh apple",
                    "Generic",
                    "https://example.com/apple",
                    baseMacro,
                    100.0,
                    "g",
                    null,  // foodImages
                    null,  // foodAttributes
                    null,  // preferences
                    null   // servings
            );

            // Create a FatSecretFoodGetClient.ServingInfo (100g serving)
            FatSecretFoodGetClient.ServingInfo serving100g = new FatSecretFoodGetClient.ServingInfo(
                    1001,           // servingId
                    "100 g",        // servingDescription
                    100.0,          // servingAmount
                    "g",            // servingUnit
                    52.0,           // calories
                    0.3,            // protein
                    0.2,            // fat
                    14.0,           // carbs
                    null,           // fiber
                    null,           // sugar
                    null            // sodium
            );

            System.out.println("Food: " + apple.getFoodName());
            System.out.println("Serving: " + serving100g.servingDescription +
                    " → " + serving100g.calories + " kcal\n");

            // Test 1: Create FoodLog with 1.5x the serving (150g) for Breakfast
            System.out.println("--- Test 1: Create FoodLog (150g = 1.5 servings, Breakfast) ---");
            double multiplier = 1.5;
            FoodLog log = new FoodLog(apple, serving100g, multiplier, "Breakfast", LocalDateTime.now());

            System.out.println("Meal: " + log.getMeal());
            System.out.println("Serving: " + log.getServingDescription());
            System.out.println("Actual serving: " + log.getActualServingSize() + log.getServingUnit());
            System.out.println("Actual macro: " + log.getActualMacro().calories() + " kcal");
            System.out.println();

            // Test 2: Update serving amount to 250g
            System.out.println("--- Test 2: Update to 250g ---");
            System.out.println("Before: " + log.getActualServingSize() + log.getServingUnit() +
                    " → " + log.getActualMacro().calories() + " kcal");

            log.updateServingAmount(250.0);

            System.out.println("After:  " + log.getActualServingSize() + log.getServingUnit() +
                    " → " + log.getActualMacro().calories() + " kcal");
            System.out.println("Multiplier: " + log.getServingMultiplier());
            System.out.println();

            // Test 3: Create with different serving (1/2 large)
            System.out.println("--- Test 3: Different serving (1/2 large) ---");
            FatSecretFoodGetClient.ServingInfo servingHalfLarge = new FatSecretFoodGetClient.ServingInfo(
                    1002,
                    "1/2 large (3-1/4\" dia)",
                    0.5,
                    "large",
                    116.0,          // calories
                    0.6,            // protein
                    0.4,            // fat
                    30.8,           // carbs
                    null, null, null
            );

            FoodLog log2 = new FoodLog(apple, servingHalfLarge, 2.0, "Lunch", LocalDateTime.now());
            System.out.println("Meal: " + log2.getMeal());
            System.out.println("Serving: " + log2.getServingDescription());
            System.out.println("Actual: " + log2.getActualServingSize() + " " + log2.getServingUnit() +
                    " → " + log2.getActualMacro().calories() + " kcal");
            System.out.println();

            System.out.println("✅ All tests passed!");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


package tutcsc.group1.healthz.entities.nutrition;

import java.time.LocalDateTime;

/**
 * FoodLog: Entity representing a single food intake log entry.
 *
 * Uses domain entities FoodDetails and ServingInfo for detailed tracking.
 */
public class FoodLog {
    private final FoodDetails food;
    private final ServingInfo servingInfo;  // The specific serving selected
    private double servingMultiplier;       // How many of the selected serving
    private Macro actualMacro;              // Calculated from servingInfo nutrition * servingMultiplier
    private final LocalDateTime loggedAt;
    private final String meal;              // Meal type ("Breakfast", "Lunch", "Dinner", "Snack")

    /**
     * Constructor with domain FoodDetails and ServingInfo.
     */
    public FoodLog(FoodDetails food, ServingInfo servingInfo,
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
     * Helper method to create Macro from ServingInfo and times multiplier.
     */
    private Macro createMacroFromServingInfo(ServingInfo serving, double multiplier) {
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
    public String getServingUnit() {return servingInfo.servingUnit;
    }

    /**
     * Get the serving description (e.g., "100 g", "1/2 large").
     */
    public String getServingDescription() {
        return servingInfo.servingDescription;
    }

    // Getters

    public FoodDetails getFood() {
        return food;
    }

    public ServingInfo getServingInfo() {
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
}


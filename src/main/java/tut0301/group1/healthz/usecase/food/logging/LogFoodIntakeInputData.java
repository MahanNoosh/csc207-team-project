package tut0301.group1.healthz.usecase.food.logging;

import tut0301.group1.healthz.entities.nutrition.FoodDetails;
import tut0301.group1.healthz.entities.nutrition.ServingInfo;

/**
 * Input DTO for logging food intake.
 */
public class LogFoodIntakeInputData {
    private final String userId;
    private final FoodDetails food;
    private final ServingInfo servingInfo;
    private final double servingMultiplier;
    private final String meal;

    /**
     * Constructor with serving multiplier.
     */
    public LogFoodIntakeInputData(String userId, FoodDetails food, ServingInfo servingInfo,
                                  double servingMultiplier, String meal) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (food == null) {
            throw new IllegalArgumentException("Food cannot be null");
        }
        if (servingInfo == null) {
            throw new IllegalArgumentException("ServingInfo cannot be null");
        }
        if (meal == null || meal.isBlank()) {
            throw new IllegalArgumentException("Meal cannot be null or empty");
        }
        if (servingMultiplier <= 0) {
            throw new IllegalArgumentException("Serving multiplier must be positive");
        }

        this.userId = userId;
        this.food = food;
        this.servingInfo = servingInfo;
        this.servingMultiplier = servingMultiplier;
        this.meal = meal;
    }

    /**
     * Constructor with actual amount (calculates multiplier automatically).
     */
    public static LogFoodIntakeInputData withActualAmount(String userId, FoodDetails food,
                                                          ServingInfo servingInfo,
                                                          double actualAmount, String meal) {
        double multiplier = actualAmount / servingInfo.servingAmount;
        return new LogFoodIntakeInputData(userId, food, servingInfo, multiplier, meal);
    }

    public String getUserId() {
        return userId;
    }

    public FoodDetails getFood() {
        return food;
    }

    public ServingInfo getServingInfo() {
        return servingInfo;
    }

    public double getServingMultiplier() {
        return servingMultiplier;
    }

    public String getMeal() {
        return meal;
    }
}

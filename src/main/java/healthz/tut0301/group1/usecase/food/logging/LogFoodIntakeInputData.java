package healthz.tut0301.group1.usecase.food.logging;

import healthz.tut0301.group1.entities.nutrition.FoodDetails;
import healthz.tut0301.group1.entities.nutrition.ServingInfo;

import java.time.LocalDateTime;

/**
 * Input DTO for logging food intake.
 */
public class LogFoodIntakeInputData {
    private final String userId;
    private final FoodDetails food;
    private final ServingInfo servingInfo;
    private final double servingMultiplier;
    private final String meal;
    private final LocalDateTime loggedAt;

    /**
     * Constructor with serving multiplier and custom timestamp.
     *
     * @param userId The user's ID
     * @param food The food details
     * @param servingInfo The serving information
     * @param servingMultiplier How many servings
     * @param meal The meal type (Breakfast, Lunch, Dinner, Snack)
     * @param loggedAt The timestamp when the food was consumed (if null, uses current time)
     */
    public LogFoodIntakeInputData(String userId, FoodDetails food, ServingInfo servingInfo,
                                  double servingMultiplier, String meal, LocalDateTime loggedAt) {
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
        this.loggedAt = loggedAt != null ? loggedAt : LocalDateTime.now();
    }

    /**
     * Constructor with serving multiplier (uses current time).
     *
     * @param userId The user's ID
     * @param food The food details
     * @param servingInfo The serving information
     * @param servingMultiplier How many servings
     * @param meal The meal type
     */
    public LogFoodIntakeInputData(String userId, FoodDetails food, ServingInfo servingInfo,
                                  double servingMultiplier, String meal) {
        this(userId, food, servingInfo, servingMultiplier, meal, null);
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

    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }
}

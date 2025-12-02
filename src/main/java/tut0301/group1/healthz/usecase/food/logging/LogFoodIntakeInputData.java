package tut0301.group1.healthz.usecase.food.logging;

import java.time.LocalDateTime;

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
     * @throws IllegalArgumentException if userId, food, or meal are invalid, or if servingMultiplier is not positive.
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
        if (loggedAt == null) {
            this.loggedAt = LocalDateTime.now();
        }
        else {
            this.loggedAt = loggedAt;
        }
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

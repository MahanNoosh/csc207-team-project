package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.entities.nutrition.BasicFood;
import tut0301.group1.healthz.entities.nutrition.FoodLog;

import java.time.LocalDateTime;

/**
 * Use Case: Log a food intake entry.
 */
public class LogFoodIntakeUseCase {

    /**
     * Execute the use case to log food intake.
     */
    public FoodLog execute(Profile profile, BasicFood food, double actualServingSize, String meal) {
        // Calculate serving multiplier
        // Example: If base is 100g and user ate 150g, multiplier = 150/100 = 1.5
        double multiplier = actualServingSize / food.getServingSize();

        // Create FoodLog with current timestamp
        FoodLog log = new FoodLog(food, multiplier, meal, LocalDateTime.now());

        // Add to profile
        profile.addFoodLog(log);

        return log;
    }

    /**
     * Execute the use case with a specific timestamp (useful for backdating entries).
     */
    public FoodLog execute(Profile profile, BasicFood food, double actualServingSize, String meal, LocalDateTime loggedAt) {
        // Calculate serving multiplier
        double multiplier = actualServingSize / food.getServingSize();

        // Create FoodLog with specified timestamp
        FoodLog log = new FoodLog(food, multiplier, meal, loggedAt);

        // Add to profile
        profile.addFoodLog(log);

        return log;
    }
}

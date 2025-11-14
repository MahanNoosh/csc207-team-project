package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.dataaccess.API.FatSecretFoodGetClient;
import tut0301.group1.healthz.entities.nutrition.DetailFood;
import tut0301.group1.healthz.entities.nutrition.FoodLog;

import java.time.LocalDateTime;

/**
 * Use Case: Log a food intake entry.
 *
 * Updated to work with DetailFood and FatSecretFoodGetClient.ServingInfo.
 */
public class LogFoodIntakeUseCase {

    /**
     * Execute the use case to log food intake.
     */
    public FoodLog execute(Profile profile, DetailFood food,
                          FatSecretFoodGetClient.ServingInfo servingInfo,
                          double servingMultiplier, String meal) {
        // Create FoodLog with current timestamp
        FoodLog log = new FoodLog(food, servingInfo, servingMultiplier, meal, LocalDateTime.now());

        // Add to profile
        profile.addFoodLog(log);

        return log;
    }

    /**
     * Convenience method: Execute by specifying actual amount consumed.
     * Calculates multiplier automatically.
     */
    public FoodLog executeWithAmount(Profile profile, DetailFood food,
                                     FatSecretFoodGetClient.ServingInfo servingInfo,
                                     double actualAmount, String meal) {
        // Calculate multiplier
        // Example: If serving is "100 g" and user ate 150g, multiplier = 150/100 = 1.5
        double multiplier = actualAmount / servingInfo.servingAmount;  // Direct field access

        return execute(profile, food, servingInfo, multiplier, meal);
    }
}

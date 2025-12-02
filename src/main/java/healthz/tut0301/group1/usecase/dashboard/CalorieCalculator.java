package healthz.tut0301.group1.usecase.dashboard;

import healthz.tut0301.group1.entities.Dashboard.Profile;
import healthz.tut0301.group1.usecase.nutritionadvice.NutritionAdvisorHelper;

/**
 * Calculator for daily calorie goals
 */
public class CalorieCalculator {

    /**
     * Calculate daily calorie goal based on profile
     * Uses HealthMetricsCalculator and NutritionAdvisorHelper
     */
    public static int calculateDailyCalorieGoal(Profile profile) {
        if (profile == null) {
            return 2000; // Default fallback
        }

        // If user has a custom calorie target, use it
        if (profile.getDailyCalorieTarget() != null && profile.getDailyCalorieTarget().isPresent()) {
            return profile.getDailyCalorieTarget().get().intValue();
        }

        // Otherwise calculate using existing helper
        double calculatedCalories = NutritionAdvisorHelper.calculateCalories(profile);

        return (int) Math.round(calculatedCalories);
    }
}
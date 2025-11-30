package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.usecase.nutritionadvice.HealthMetricsCalculator;
import tut0301.group1.healthz.usecase.nutritionadvice.NutritionAdvisorHelper;

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
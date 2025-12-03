package tutcsc.group1.healthz.use_case.nutritionadvice;
import tutcsc.group1.healthz.entities.dashboard.Profile;

public class NutritionAdvisorHelper {

    public static final double FIBRE_PER_1000KCAL = 14.0;

    public static double calculateCalories(Profile profile) {
        double tdee = HealthMetricsCalculator.calculateTDEE(profile);

        return switch (profile.getGoal()) {
            case WEIGHT_LOSS -> tdee - 500;
            case WEIGHT_GAIN -> tdee + 300;
            default -> tdee;
        };
    }

    public static double calculateGoalAdjustedCalories(Profile profile) {
        double currentWeight = profile.getWeightKg();
        double targetWeight = profile.getTargetWeightKg();
        double tdee = HealthMetricsCalculator.calculateTDEE(profile);

        // Each 0.5 kg = ~3500 kcal deficit/surplus per week
        double weightDiff = targetWeight - currentWeight;
        double totalCaloriesToAdjust = weightDiff * 7700; // kcal per kg of fat
        double dailyAdjustment = totalCaloriesToAdjust / (profile.getTargetWeeks() * 7);

        return tdee + dailyAdjustment; // surplus or deficit
    }

    public static double calculateProtein(Profile profile) {
        double multiplier;
        switch (profile.getGoal()) {
            case WEIGHT_LOSS -> multiplier = 1.8;
            case GENERAL_HEALTH -> multiplier = 1.4;
            case MUSCLE_GAIN -> multiplier = 2.0;
            default -> multiplier = 1.2;
        }
        return profile.getWeightKg() * multiplier;
    }
    public static double calculateFibre(Profile profile) {
        double calories =  calculateCalories(profile);
        return (calories / 1000.0) * FIBRE_PER_1000KCAL;
    }
    public static double calculateFat(Profile profile) {
        double calories = calculateCalories(profile);
        return (calories * 0.25) / 9.0;  // 25% of total kcal from fat, 9 kcal/g
    }

}
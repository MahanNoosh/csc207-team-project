package tutcsc.group1.healthz.use_case.nutritionadvice;

import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.entities.dashboard.Sex;

public class HealthMetricsCalculator {

    public static double calculateBMI(Profile profile) {
        double heightM = profile.getHeightCm() / 100.0;
        return profile.getWeightKg() / (heightM * heightM);
    }

    public static String getBMICategory(double bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 24.9) return "Normal weight";
        if (bmi < 29.9) return "Overweight";
        return "Obese";
    }

    public static double computeBMR(Profile profile) {
        if (profile.getSex() == Sex.MALE) {
            return 10 * profile.getWeightKg() + 6.25 * profile.getHeightCm() - 5 * profile.getAgeYears() + 5;
        } else {
            return 10 * profile.getWeightKg() + 6.25 * profile.getHeightCm() - 5 * profile.getAgeYears() - 161;
        }
    }
    public static double calculateTDEE(Profile profile) {
        if (profile.getActivitylevelmet() != null) {
            return computeBMR(profile) * profile.getActivitylevelmet();
        }
        return 2500;
    }


}

package tutcsc.group1.healthz.interface_adapter.auth.mapping;

import tutcsc.group1.healthz.entities.dashboard.Goal;
import tutcsc.group1.healthz.entities.dashboard.Sex;
import tutcsc.group1.healthz.view.auth.SignupView;
import tutcsc.group1.healthz.entities.dashboard.Profile;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public final class SignupProfileMapper {

    private SignupProfileMapper() {}

    public static Profile toProfile(String userId, SignupView.SignupData data) {

        // ========== AGE ==========
        Integer ageYears = null;
        String dobStr = data.getDateOfBirth();
        if (dobStr != null) {
            try {
                LocalDate dob = LocalDate.parse(dobStr); // yyyy-MM-dd
                ageYears = Period.between(dob, LocalDate.now()).getYears();
            } catch (Exception ignored) {}
        }

        // ========== SEX ==========
        Sex sexEnum = null;
        String sexStr = data.getSex();
        if (sexStr != null) {
            String raw = sexStr.trim().toLowerCase();

            if (raw.startsWith("m"))      sexEnum = Sex.MALE;
            else if (raw.startsWith("f")) sexEnum = Sex.FEMALE;
            else                          sexEnum = Sex.OTHER;
        }

        // ========== GOAL ==========
        Goal goalEnum = null;
        String goalStr = data.getGoal();
        if (goalStr != null) {
            String g = goalStr.toLowerCase();

            if (g.contains("lose")) {
                goalEnum = Goal.WEIGHT_LOSS;
            } else if (g.contains("gain muscle") || g.contains("muscle")) {
                goalEnum = Goal.MUSCLE_GAIN;
            } else if (g.contains("gain")) {
                goalEnum = Goal.WEIGHT_GAIN;
            } else {
                goalEnum = Goal.GENERAL_HEALTH;
            }
        }

        // ========== ACTIVITY MET (optional) ==========
        Double activityMet = mapActivityLevelToMET(data.getActivityLevel());

        // ========== NUMERIC FIELDS ==========
        Double heightCm     = data.getHeight() > 0 ? data.getHeight() : null;
        Double weightKg     = data.getWeight() > 0 ? data.getWeight() : null;
        Double targetWeight = data.getGoalWeight() > 0 ? data.getGoalWeight() : null;

        return new Profile(
                userId,
                weightKg,
                heightCm,
                ageYears,
                sexEnum,
                goalEnum,
                activityMet,
                targetWeight,
                Optional.empty(),   // dailyCalorieTarget (later)
                null                // healthCondition  (later)
        );
    }

    private static double mapActivityLevelToMET(String level) {
        if (level == null) return 1.2;
        switch (level) {
            case "Sedentary":
                return 1.2;
            case "Lightly Active":
                return 1.375;
            case "Moderately Active":
                return 1.55;
            case "Very Active":
                return 1.725;
            case "Extremely Active":
                return 1.9;
            default:
                return 1.2;
        }
    }
}

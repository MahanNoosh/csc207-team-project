package tut0301.group1.healthz.interfaceadapter.auth.mapping;

import tut0301.group1.healthz.view.auth.SignupView;
import tut0301.group1.healthz.usecase.dashboard.Profile;
import tut0301.group1.healthz.usecase.dashboard.UserDashboardPort;

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
        UserDashboardPort.Sex sexEnum = null;
        String sexStr = data.getSex();
        if (sexStr != null) {
            String raw = sexStr.trim().toLowerCase();

            if (raw.startsWith("m"))      sexEnum = UserDashboardPort.Sex.MALE;
            else if (raw.startsWith("f")) sexEnum = UserDashboardPort.Sex.FEMALE;
            else                          sexEnum = UserDashboardPort.Sex.OTHER;
        }

        // ========== GOAL ==========
        UserDashboardPort.Goal goalEnum = null;
        String goalStr = data.getGoal();
        if (goalStr != null) {
            String g = goalStr.toLowerCase();

            if (g.contains("lose")) {
                goalEnum = UserDashboardPort.Goal.WEIGHT_LOSS;
            } else if (g.contains("gain muscle") || g.contains("muscle")) {
                goalEnum = UserDashboardPort.Goal.MUSCLE_GAIN;
            } else if (g.contains("gain")) {
                goalEnum = UserDashboardPort.Goal.WEIGHT_GAIN;
            } else {
                goalEnum = UserDashboardPort.Goal.GENERAL_HEALTH;
            }
        }

        // ========== ACTIVITY MET (optional) ==========
        Double activityMet = null;

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
}

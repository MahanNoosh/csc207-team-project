package tut0301.group1.healthz.usecase.dashboard;

import java.util.Optional;

/**
 * Right-side port: provides the user's stored profile (metrics + goal).
 * Implement this in your data-access layer (DB/API).
 */
public interface UserDashboardPort {

    Optional<UserProfile> getProfile(String userId);
    void saveProfile(UserProfile userProfile);

    enum Sex { MALE, FEMALE, OTHER }

    enum Goal { GENERAL_HEALTH, WEIGHT_LOSS, ATHLETIC }
    enum HealthCondition {
        DIABETES,
        HYPERTENSION,
        HEART_DISEASE,
        THYROID,
        NONE
    }

    /** Immutable snapshot of user state relevant to scoring. */
    record UserProfile(
            String userId,
            Double weightKg,
            Double heightCm,
            Integer ageYears,
            Sex sex,
            Goal goal,
            Double activityLevelMET, // optional: e.g., 1.2 sedentary, 1.4 light, 1.6 moderate, 1.8+ high
            Double targetWeightKg,
            Optional<Double> dailyCalorieTarget,
            HealthCondition healthCondition
    ) {}
}

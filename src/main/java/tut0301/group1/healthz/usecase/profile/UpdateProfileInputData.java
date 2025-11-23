package tut0301.group1.healthz.usecase.profile;

import java.util.Optional;

/**
 * Input DTO for updating a user profile.
 */
public class UpdateProfileInputData {
    private final String userId;
    private final Double weightKg;
    private final Double heightCm;
    private final Integer ageYears;
    private final Sex sex;
    private final Goal goal;
    private final Double activityLevelMET;
    private final Double targetWeightKg;
    private final Optional<Double> dailyCalorieTarget;
    private final HealthCondition healthCondition;

    public UpdateProfileInputData(String userId, Double weightKg, Double heightCm, Integer ageYears,
                                  Sex sex, Goal goal,
                                  Double activityLevelMET, Double targetWeightKg,
                                  Optional<Double> dailyCalorieTarget,
                                  HealthCondition healthCondition) {
        this.userId = userId;
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.ageYears = ageYears;
        this.sex = sex;
        this.goal = goal;
        this.activityLevelMET = activityLevelMET;
        this.targetWeightKg = targetWeightKg;
        this.dailyCalorieTarget = dailyCalorieTarget;
        this.healthCondition = healthCondition;
    }

    public String getUserId() {
        return userId;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public Double getHeightCm() {
        return heightCm;
    }

    public Integer getAgeYears() {
        return ageYears;
    }

    public Sex getSex() {
        return sex;
    }

    public Goal getGoal() {
        return goal;
    }

    public Double getActivityLevelMET() {
        return activityLevelMET;
    }

    public Double getTargetWeightKg() {
        return targetWeightKg;
    }

    public Optional<Double> getDailyCalorieTarget() {
        return dailyCalorieTarget;
    }

    public HealthCondition getHealthCondition() {
        return healthCondition;
    }
}

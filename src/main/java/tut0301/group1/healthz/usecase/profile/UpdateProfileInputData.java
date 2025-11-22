package tut0301.group1.healthz.usecase.profile;

import tut0301.group1.healthz.usecase.dashboard.UserDashboardPort;

import java.util.Optional;

/**
 * Input DTO for updating a user profile.
 */
public class UpdateProfileInputData {
    private final String userId;
    private final Double weightKg;
    private final Double heightCm;
    private final Integer ageYears;
    private final UserDashboardPort.Sex sex;
    private final UserDashboardPort.Goal goal;
    private final Double activityLevelMET;
    private final Double targetWeightKg;
    private final Optional<Double> dailyCalorieTarget;
    private final UserDashboardPort.HealthCondition healthCondition;

    public UpdateProfileInputData(String userId, Double weightKg, Double heightCm, Integer ageYears,
                                  UserDashboardPort.Sex sex, UserDashboardPort.Goal goal,
                                  Double activityLevelMET, Double targetWeightKg,
                                  Optional<Double> dailyCalorieTarget,
                                  UserDashboardPort.HealthCondition healthCondition) {
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

    public UserDashboardPort.Sex getSex() {
        return sex;
    }

    public UserDashboardPort.Goal getGoal() {
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

    public UserDashboardPort.HealthCondition getHealthCondition() {
        return healthCondition;
    }
}

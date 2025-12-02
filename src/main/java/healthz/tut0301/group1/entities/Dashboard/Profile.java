package healthz.tut0301.group1.entities.Dashboard;

import java.util.Optional;

public class Profile {
    private final String userId;
    private Double weightKg;
    private Double heightCm;
    private Integer ageYears;
    private Sex sex;
    private Goal goal;
    private Double activityLevelMET; // optional: e.g., 1.2 sedentary, 1.4 light, 1.6 moderate, 1.8+ high
    private Double targetWeightKg;
    private Optional<Double> dailyCalorieTarget;
    private HealthCondition healthCondition;
    private int targetWeeks;
    private DietPreference dietPreference;

    public Profile(String userId, Double weightKg, Double heightCm, Integer ageYears,
                       Sex sex, Goal goal, Double activityLevelMET,
                       Double targetWeightKg, Optional<Double> dailyCalorieTarget,
                       HealthCondition healthCondition, DietPreference dietPreference) {
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
        this.targetWeeks = 0;
        this.dietPreference = dietPreference;
    }

    // overloading method with default diet preference value
    public Profile(String userId, Double weightKg, Double heightCm, Integer ageYears,
                   Sex sex, Goal goal, Double activityLevelMET,
                   Double targetWeightKg, Optional<Double> dailyCalorieTarget,
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
        this.targetWeeks = 0;
        this.dietPreference = DietPreference.NONE;
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
    public int getTargetWeeks() {
        return targetWeeks;
    }
    public DietPreference getDietPreference() { return dietPreference; }

    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
    public void setHeightCm(Double heightCm) { this.heightCm = heightCm; }
    public void setAgeYears(Integer ageYears) { this.ageYears = ageYears; }
    public void setSex(Sex sex) { this.sex = sex; }
    public void setGoal(Goal goal) { this.goal = goal; }
    public void setActivityLevelMET(Double activityLevelMET) {
        this.activityLevelMET = activityLevelMET; }
    public void setTargetWeightKg(Double targetWeightKg) {
        this.targetWeightKg = targetWeightKg; }
    public void setDailyCalorieTarget(Optional<Double> dailyCalorieTarget) {
        this.dailyCalorieTarget = dailyCalorieTarget; }
    public void setHealthCondition(HealthCondition healthCondition) {
        this.healthCondition = healthCondition; }
    public void setTargetWeeks(int targetWeeks) { this.targetWeeks = targetWeeks; }
    public void setDietPreference(DietPreference dietPreference) { this.dietPreference = dietPreference; }
}
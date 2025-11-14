package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Profile {
    private final String userId;
    private Double weightKg;
    private Double heightCm;
    private Integer ageYears;
    private UserDashboardPort.Sex sex;
    private UserDashboardPort.Goal goal;
    private Double activityLevelMET; // optional: e.g., 1.2 sedentary, 1.4 light, 1.6 moderate, 1.8+ high
    private Double targetWeightKg;
    private Optional<Double> dailyCalorieTarget;
    private UserDashboardPort.HealthCondition healthCondition;
    private final List<FoodLog> foodLogs;

    public Profile(String userId, Double weightKg, Double heightCm, Integer ageYears,
                       UserDashboardPort.Sex sex, UserDashboardPort.Goal goal, Double activityLevelMET,
                       Double targetWeightKg, Optional<Double> dailyCalorieTarget,
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
        this.foodLogs = new ArrayList<>();
    }

    /**
     * Constructor with existing food logs (for loading from repository).
     */
    public Profile(String userId, Double weightKg, Double heightCm, Integer ageYears,
                   UserDashboardPort.Sex sex, UserDashboardPort.Goal goal, Double activityLevelMET,
                   Double targetWeightKg, Optional<Double> dailyCalorieTarget,
                   UserDashboardPort.HealthCondition healthCondition, List<FoodLog> foodLogs) {
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
        this.foodLogs = new ArrayList<>(foodLogs != null ? foodLogs : Collections.emptyList());
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

    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
    public void setHeightCm(Double heightCm) { this.heightCm = heightCm; }
    public void setAgeYears(Integer ageYears) { this.ageYears = ageYears; }
    public void setSex(UserDashboardPort.Sex sex) { this.sex = sex; }
    public void setGoal(UserDashboardPort.Goal goal) { this.goal = goal; }
    public void setActivityLevelMET(Double activityLevelMET) { this.activityLevelMET = activityLevelMET; }
    public void setTargetWeightKg(Double targetWeightKg) { this.targetWeightKg = targetWeightKg; }
    public void setDailyCalorieTarget(Optional<Double> dailyCalorieTarget) { this.dailyCalorieTarget = dailyCalorieTarget; }
    public void setHealthCondition(UserDashboardPort.HealthCondition healthCondition) { this.healthCondition = healthCondition; }



    public void addFoodLog(FoodLog log) {
        if (log == null) {
            throw new IllegalArgumentException("Food log cannot be null");
        }
        foodLogs.add(log);
    }


    public List<FoodLog> getLogsByDate(LocalDate date) {
        return foodLogs.stream()
                .filter(log -> log.getLoggedAt().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }


    public Macro getDailyTotalMacro(LocalDate date) {
        return getLogsByDate(date).stream()
                .map(FoodLog::getActualMacro)
                .reduce(Macro.ZERO, Macro::add);
    }


    public List<FoodLog> getAllFoodLogs() {
        return Collections.unmodifiableList(foodLogs);
    }
}
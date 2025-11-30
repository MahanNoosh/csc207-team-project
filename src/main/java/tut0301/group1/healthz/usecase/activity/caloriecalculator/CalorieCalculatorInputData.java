package tut0301.group1.healthz.usecase.activity.caloriecalculator;

import tut0301.group1.healthz.entities.Dashboard.Profile;

public class CalorieCalculatorInputData {
    private final String exerciseName;
    private final int durationMinutes;

    public CalorieCalculatorInputData(String exerciseName, int durationMinutes) {
        this.exerciseName = exerciseName;
        this.durationMinutes = durationMinutes;
    }

    public String getExerciseName() {
        return exerciseName;
    }
    public int getDurationMinutes() {
        return durationMinutes;
    }
}

package tut0301.group1.healthz.usecase.activity.caloriecalculator;

import tut0301.group1.healthz.entities.Dashboard.Profile;

public interface CalorieCalculatorInputBoundary {
    void calculateCalories(CalorieCalculatorInputData inputData, Profile profile);
}

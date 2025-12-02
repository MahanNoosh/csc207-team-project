package healthz.tut0301.group1.usecase.activity.caloriecalculator;

import healthz.tut0301.group1.entities.Dashboard.Profile;

public interface CalorieCalculatorInputBoundary {
    void calculateCalories(CalorieCalculatorInputData inputData, Profile profile);
}

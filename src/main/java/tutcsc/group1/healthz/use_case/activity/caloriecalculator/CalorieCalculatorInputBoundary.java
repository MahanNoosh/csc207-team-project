package tutcsc.group1.healthz.use_case.activity.caloriecalculator;

import tutcsc.group1.healthz.entities.dashboard.Profile;

public interface CalorieCalculatorInputBoundary {
    void calculateCalories(CalorieCalculatorInputData inputData, Profile profile);
}

package tut0301.group1.healthz.interfaceadapter.activity;

import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogInputBoundary;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogInputData;
import tut0301.group1.healthz.usecase.activity.caloriecalculator.CalorieCalculatorInputBoundary;
import tut0301.group1.healthz.usecase.activity.caloriecalculator.CalorieCalculatorInputData;
import tut0301.group1.healthz.usecase.activity.caloriecalculator.CalorieCalculatorInteractor;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseFinderInputBoundary;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseInputData;

public class ActivityPageController {
    private final ExerciseFinderInputBoundary exerciseFinderInteractor;
    private final CalorieCalculatorInputBoundary calorieCalculatorInteractor;
    private final ActivityLogInputBoundary activityLogInteractor;

    public ActivityPageController(ExerciseFinderInputBoundary exerciseFinderInteractor, CalorieCalculatorInputBoundary calorieCalculatorInteractor, ActivityLogInputBoundary activityLogInteractor) {
        this.exerciseFinderInteractor = exerciseFinderInteractor;
        this.calorieCalculatorInteractor = calorieCalculatorInteractor;
        this.activityLogInteractor = activityLogInteractor;
    }

    public void onSearchQuery(String query) {
        exerciseFinderInteractor.findExercisesByQuery(new ExerciseInputData(query));
    }

    public void loadAllExercises() {
        exerciseFinderInteractor.findAllExercisesNames();
    }

    public void onDurationOrActivityChange(String exerciseName, String durationText, Profile profile) throws  Exception {
            int duration = Integer.parseInt(durationText);
            CalorieCalculatorInputData inputData = new CalorieCalculatorInputData(exerciseName, duration);
            calorieCalculatorInteractor.calculateCalories(inputData, profile);
    }

    public void logActivity(String exerciseName, String durationText, Profile profile) throws Exception {
        int minutes = Integer.parseInt(durationText);
        ActivityLogInputData inputData = new ActivityLogInputData(exerciseName, minutes);
        activityLogInteractor.logActivity(inputData, profile);
    }
}

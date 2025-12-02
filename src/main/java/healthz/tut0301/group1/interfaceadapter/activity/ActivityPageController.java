package healthz.tut0301.group1.interfaceadapter.activity;

import healthz.tut0301.group1.entities.Dashboard.Profile;
import healthz.tut0301.group1.usecase.activity.activitylog.ActivityLogInputBoundary;
import healthz.tut0301.group1.usecase.activity.activitylog.ActivityLogInputData;
import healthz.tut0301.group1.usecase.activity.caloriecalculator.CalorieCalculatorInputBoundary;
import healthz.tut0301.group1.usecase.activity.caloriecalculator.CalorieCalculatorInputData;
import healthz.tut0301.group1.usecase.activity.exercisefinder.ExerciseFinderInputBoundary;
import healthz.tut0301.group1.usecase.activity.exercisefinder.ExerciseInputData;

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

    public void loadActivityHistory() {
        try {
            activityLogInteractor.loadLogsForUser();
        } catch (Exception e) {
            System.err.println("Load failed: " + e.getMessage());
        }
    }
}

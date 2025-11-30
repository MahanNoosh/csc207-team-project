package tut0301.group1.healthz.usecase.activity.caloriecalculator;

import tut0301.group1.healthz.entities.Dashboard.Exercise;
import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseFinderInputBoundary;

public class CalorieCalculatorInteractor implements CalorieCalculatorInputBoundary{
    private final ExerciseFinderInputBoundary exerciseFinder;
    private final CalorieCalculatorOutputBoundary presenter;

    public CalorieCalculatorInteractor(ExerciseFinderInputBoundary exerciseFinder,
                                       CalorieCalculatorOutputBoundary presenter) {
        this.exerciseFinder = exerciseFinder;
        this.presenter = presenter;
    }

    @Override
    public void calculateCalories(CalorieCalculatorInputData inputData, Profile profile) {
        try {
            Exercise exercise = exerciseFinder.findExerciseByName(inputData.getExerciseName());
            double calories = (exercise.getMet() * 3.5 * profile.getWeightKg() / 200.0) *
                    inputData.getDurationMinutes();
            presenter.presentCalories(new CalorieCalculatorOutputData(calories));
        } catch (Exception e) {
            presenter.presentCalories(new CalorieCalculatorOutputData(0));
        }
    }
}

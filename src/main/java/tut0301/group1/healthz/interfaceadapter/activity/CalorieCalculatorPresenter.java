package tut0301.group1.healthz.interfaceadapter.activity;

import tut0301.group1.healthz.usecase.activity.caloriecalculator.CalorieCalculatorOutputBoundary;
import tut0301.group1.healthz.usecase.activity.caloriecalculator.CalorieCalculatorOutputData;

/**
 * Presenter for calorie calculation use case.
 * <p>
 * Converts the output data from the interactor into a form suitable for the {@link ExerciseListViewModel}.
 * </p>
 */
public class CalorieCalculatorPresenter implements CalorieCalculatorOutputBoundary {

    private final ExerciseListViewModel viewModel;

    /**
     * Constructs a CalorieCalculatorPresenter with the given view model.
     *
     * @param viewModel the {@link ExerciseListViewModel} to update with calculated calories
     */
    public CalorieCalculatorPresenter(final ExerciseListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Updates the view model with the calculated calories from the output data.
     *
     * @param outputData the {@link CalorieCalculatorOutputData} containing calorie results
     */
    @Override
    public void presentCalories(final CalorieCalculatorOutputData outputData) {
        viewModel.setCurrentCalories(outputData.getCalories());
    }
}

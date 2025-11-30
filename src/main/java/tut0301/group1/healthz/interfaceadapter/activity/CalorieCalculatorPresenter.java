package tut0301.group1.healthz.interfaceadapter.activity;

import tut0301.group1.healthz.usecase.activity.caloriecalculator.CalorieCalculatorOutputBoundary;
import tut0301.group1.healthz.usecase.activity.caloriecalculator.CalorieCalculatorOutputData;

public class CalorieCalculatorPresenter implements CalorieCalculatorOutputBoundary {

    private final ExerciseListViewModel viewModel;

    public CalorieCalculatorPresenter(ExerciseListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentCalories(CalorieCalculatorOutputData outputData) {
        viewModel.setCurrentCalories(outputData.getCalories());

    }

}

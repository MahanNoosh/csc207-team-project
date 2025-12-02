package healthz.tut0301.group1.interfaceadapter.activity;

import healthz.tut0301.group1.usecase.activity.caloriecalculator.CalorieCalculatorOutputBoundary;
import healthz.tut0301.group1.usecase.activity.caloriecalculator.CalorieCalculatorOutputData;

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

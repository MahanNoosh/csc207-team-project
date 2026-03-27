package tutcsc.group1.healthz.interface_adapter.activity;

import tutcsc.group1.healthz.use_case.activity.calorie_calculator.CalorieCalculatorOutputBoundary;
import tutcsc.group1.healthz.use_case.activity.calorie_calculator.CalorieCalculatorOutputData;

/**
 * Presenter for calorie calculation use case.
 *
 * <p>
 * Converts the output data from the interactor
 * into a form suitable for the {@link ExerciseListViewModel}.
 * </p>
 */
public class CalorieCalculatorPresenter
        implements CalorieCalculatorOutputBoundary {

    /**
     * The view model that receives updates for the current calculated calories.
     */
    private final ExerciseListViewModel viewModel;

    /**
     * Constructs a CalorieCalculatorPresenter with the given view model.
     *
     * @param listViewModel the {@link ExerciseListViewModel}
     *                     to update with calculated calories
     */
    public CalorieCalculatorPresenter(
            final ExerciseListViewModel listViewModel) {
        this.viewModel = listViewModel;
    }

    /**
     * Updates the view model with the calculated calories from the output data.
     *
     * @param outputData the {@link CalorieCalculatorOutputData}
     *                  containing calorie results
     */
    @Override
    public void presentCalories(final CalorieCalculatorOutputData outputData) {
        viewModel.setCurrentCalories(outputData.getCalories());
    }
}

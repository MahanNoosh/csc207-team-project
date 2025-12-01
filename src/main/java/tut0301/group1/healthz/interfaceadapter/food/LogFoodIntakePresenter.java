package tut0301.group1.healthz.interfaceadapter.food;

import tut0301.group1.healthz.usecase.food.logging.LogFoodIntakeOutputBoundary;
import tut0301.group1.healthz.usecase.food.logging.LogFoodIntakeOutputData;

/**
 * Presenter for LogFoodIntake functionality.
 *
 * Implements LogFoodIntakeOutputBoundary from the Use Case layer.
 * Updates the LogFoodIntakeViewModel based on logging results.
 *
 * Clean Architecture compliance:
 * - Presenter (Interface Adapter layer) implements OutputBoundary (Use Case layer interface)
 * - Presenter converts OutputData to ViewModel format
 * - Presenter does not know about Controller or View
 */
public class LogFoodIntakePresenter implements LogFoodIntakeOutputBoundary {

    private final LogFoodIntakeViewModel viewModel;

    public LogFoodIntakePresenter(LogFoodIntakeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentLogResult(LogFoodIntakeOutputData outputData) {
        if (outputData.isSuccess()) {
            // Success case: update ViewModel with logged food
            viewModel.setSuccess(true);
            viewModel.setMessage(outputData.getMessage());
            viewModel.setFoodLog(outputData.getFoodLog());
            viewModel.setLoading(false);
        } else {
            // Error case: update ViewModel with error message
            viewModel.setSuccess(false);
            viewModel.setMessage(outputData.getMessage());
            viewModel.setFoodLog(null);
            viewModel.setLoading(false);
        }
    }

    public LogFoodIntakeViewModel getViewModel() {
        return viewModel;
    }
}

package tut0301.group1.healthz.interfaceadapter.caloriesummary;

import tut0301.group1.healthz.usecase.macrosummary.GetDailyCalorieSummaryOutputBoundary;
import tut0301.group1.healthz.usecase.macrosummary.GetDailyCalorieSummaryOutputData;

/**
 * Presenter for Get Daily Calorie Summary functionality.
 *
 * Implements GetDailyCalorieSummaryOutputBoundary from the Use Case layer.
 * Updates the GetDailyCalorieSummaryViewModel based on summary results.
 *
 * Clean Architecture compliance:
 * - Presenter (Interface Adapter layer) implements OutputBoundary (Use Case layer interface)
 * - Presenter converts OutputData to ViewModel format
 * - Presenter does not know about Controller or View
 */
public class GetDailyCalorieSummaryPresenter implements GetDailyCalorieSummaryOutputBoundary {

    private final GetDailyCalorieSummaryViewModel viewModel;

    public GetDailyCalorieSummaryPresenter(GetDailyCalorieSummaryViewModel viewModel) {
        if (viewModel == null) {
            throw new IllegalArgumentException("ViewModel cannot be null");
        }
        this.viewModel = viewModel;
    }

    @Override
    public void presentDailySummary(GetDailyCalorieSummaryOutputData outputData) {
        viewModel.setDate(outputData.getDate());
        viewModel.setTotalMacro(outputData.getTotalMacro());
        viewModel.setTotalActivityCalories(outputData.getTotalActivityCalories());
        viewModel.setDailyCalorieTarget(outputData.getDailyCalorieTarget());
        viewModel.setErrorMessage(null);
        viewModel.setLoading(false);
    }

    @Override
    public void presentError(String errorMessage) {
        viewModel.clear();
        viewModel.setErrorMessage(errorMessage);
        viewModel.setLoading(false);

        System.err.println("❌ Daily calorie summary error: " + errorMessage);
    }

    public GetDailyCalorieSummaryViewModel getViewModel() {
        return viewModel;
    }
}

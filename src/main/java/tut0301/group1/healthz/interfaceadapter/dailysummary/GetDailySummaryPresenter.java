package tut0301.group1.healthz.interfaceadapter.dailysummary;

import tut0301.group1.healthz.usecase.dailysummary.GetDailySummaryOutputBoundary;
import tut0301.group1.healthz.usecase.dailysummary.GetDailySummaryOutputData;

/**
 * Presenter for Get Daily Summary functionality.
 *
 * Implements GetDailySummaryOutputBoundary from the Use Case layer.
 * Updates the GetDailySummaryViewModel based on summary results.
 *
 * Clean Architecture compliance:
 * - Presenter (Interface Adapter layer) implements OutputBoundary (Use Case layer interface)
 * - Presenter converts OutputData to ViewModel format
 * - Presenter does not know about Controller or View
 */
public class GetDailySummaryPresenter implements GetDailySummaryOutputBoundary {

    private final GetDailySummaryViewModel viewModel;

    public GetDailySummaryPresenter(GetDailySummaryViewModel viewModel) {
        if (viewModel == null) {
            throw new IllegalArgumentException("ViewModel cannot be null");
        }
        this.viewModel = viewModel;
    }

    @Override
    public void presentDailySummary(GetDailySummaryOutputData outputData) {
        // Update ViewModel with the summary data
        viewModel.setDate(outputData.getDate());
        viewModel.setFoodLogs(outputData.getFoodLogs());
        viewModel.setTotalMacro(outputData.getTotalMacro());
        viewModel.setErrorMessage(null);
        viewModel.setLoading(false);
    }

    @Override
    public void presentError(String errorMessage) {
        // Update ViewModel with error state
        viewModel.clear();
        viewModel.setErrorMessage(errorMessage);
        viewModel.setLoading(false);

        System.err.println("❌ Daily summary error: " + errorMessage);
    }

    public GetDailySummaryViewModel getViewModel() {
        return viewModel;
    }
}

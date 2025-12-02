package tut0301.group1.healthz.interfaceadapter.macrosummary;

import tut0301.group1.healthz.usecase.macrosummary.GetDailyMacroSummaryOutputBoundary;
import tut0301.group1.healthz.usecase.macrosummary.GetDailyMacroSummaryOutputData;

/**
 * Presenter for Get Daily Calorie Summary functionality.
 * Implements GetDailyCalorieSummaryOutputBoundary from the Use Case layer.
 * Updates the GetDailyCalorieSummaryViewModel based on summary results.
 * Clean Architecture compliance:
 * - Presenter (Interface Adapter layer) implements OutputBoundary (Use Case layer interface)
 * - Presenter converts OutputData to ViewModel format
 * - Presenter does not know about Controller or View
 */
public class GetDailyMacroSummaryPresenter implements GetDailyMacroSummaryOutputBoundary {

    private final GetDailyMacroSummaryViewModel viewModel;

    public GetDailyMacroSummaryPresenter(GetDailyMacroSummaryViewModel viewModel) {
        if (viewModel == null) {
            throw new IllegalArgumentException("ViewModel cannot be null");
        }
        this.viewModel = viewModel;
    }

    @Override
    public void presentDailySummary(GetDailyMacroSummaryOutputData outputData) {
        viewModel.setDate(outputData.getDate());
        viewModel.setTotalMacro(outputData.getTotalMacro());
        viewModel.setErrorMessage(null);
        viewModel.setLoading(false);
    }

    @Override
    public void presentError(String errorMessage) {
        viewModel.clear();
        viewModel.setErrorMessage(errorMessage);
        viewModel.setLoading(false);
    }

    public GetDailyMacroSummaryViewModel getViewModel() {
        return viewModel;
    }
}

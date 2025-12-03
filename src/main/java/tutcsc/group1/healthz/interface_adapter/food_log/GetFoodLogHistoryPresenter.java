package tutcsc.group1.healthz.interface_adapter.food_log;

import tutcsc.group1.healthz.use_case.food.foodloghistory.GetFoodLogHistoryOutputBoundary;
import tutcsc.group1.healthz.use_case.food.foodloghistory.GetFoodLogHistoryOutputData;

/**
 * Presenter for Get Food Log History functionality.
 *
 * Implements GetFoodLogHistoryOutputBoundary from the Use Case layer.
 * Updates the GetFoodLogHistoryViewModel based on the results.
 *
 * Clean Architecture compliance:
 * - Presenter (Interface Adapter layer) implements OutputBoundary (Use Case layer interface)
 * - Presenter converts OutputData to ViewModel format
 * - Presenter does not know about Controller or View
 */
public class GetFoodLogHistoryPresenter implements GetFoodLogHistoryOutputBoundary {

    private final GetFoodLogHistoryViewModel viewModel;

    public GetFoodLogHistoryPresenter(GetFoodLogHistoryViewModel viewModel) {
        if (viewModel == null) {
            throw new IllegalArgumentException("ViewModel cannot be null");
        }
        this.viewModel = viewModel;
    }

    @Override
    public void presentFoodLogHistory(GetFoodLogHistoryOutputData outputData) {
        viewModel.setDate(outputData.getDate());
        viewModel.setFoodLogs(outputData.getFoodLogs());
        viewModel.setErrorMessage(null);
        viewModel.setLoading(false);
    }

    @Override
    public void presentError(String errorMessage) {
        viewModel.clear();
        viewModel.setErrorMessage(errorMessage);
        viewModel.setLoading(false);

        System.err.println("❌ Food log history error: " + errorMessage);
    }

    public GetFoodLogHistoryViewModel getViewModel() {
        return viewModel;
    }
}

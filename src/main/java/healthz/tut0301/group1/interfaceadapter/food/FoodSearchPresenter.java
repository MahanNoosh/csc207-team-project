package healthz.tut0301.group1.interfaceadapter.food;

import healthz.tut0301.group1.interfaceadapter.macro.MacroSearchViewModel;
import healthz.tut0301.group1.usecase.food.search.SearchFoodOutputBoundary;
import healthz.tut0301.group1.usecase.food.search.SearchFoodOutputData;

/**
 * Presenter for Food Search functionality.
 *
 * Implements SearchFoodOutputBoundary from the Use Case layer.
 * Updates the MacroSearchViewModel based on search results.
 *
 * Clean Architecture compliance:
 * - Presenter (Interface Adapter layer) implements OutputBoundary (Use Case layer interface)
 * - Presenter converts OutputData to ViewModel format
 * - Presenter does not know about Controller or View
 */
public class FoodSearchPresenter implements SearchFoodOutputBoundary {

    private final MacroSearchViewModel viewModel;

    public FoodSearchPresenter(MacroSearchViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSearchResults(SearchFoodOutputData outputData) {
        if (outputData.isSuccess()) {
            // Success case: update ViewModel with search results
            viewModel.setResults(outputData.getFoods());
            viewModel.setMessage(null);
            viewModel.setLoading(false);
        } else {
            // Error case: update ViewModel with error message
            viewModel.setMessage(outputData.getErrorMessage());
            viewModel.setResults(java.util.List.of());
            viewModel.setLoading(false);
        }
    }

    public MacroSearchViewModel getViewModel() {
        return viewModel;
    }
}

package tutcsc.group1.healthz.interface_adapter.recipe;

import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailOutputBoundary;
import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailOutputData;

public class RecipeDetailPresenter implements RecipeDetailOutputBoundary {

    private final RecipeDetailViewModel viewModel;

    public RecipeDetailPresenter(RecipeDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(RecipeDetailOutputData outputData) {
        viewModel.setDetails(outputData.getDetails());
        viewModel.setMessage(null);
        viewModel.setLoading(false);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setMessage(errorMessage);
        viewModel.setDetails(null);
        viewModel.setLoading(false);
    }

    public RecipeDetailViewModel getViewModel() {
        return viewModel;
    }
}
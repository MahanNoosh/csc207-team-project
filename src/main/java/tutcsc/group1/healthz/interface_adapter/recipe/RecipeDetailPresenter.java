package tutcsc.group1.healthz.interface_adapter.recipe;

import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailOutputBoundary;
import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailOutputData;

/**
 * The recipe detail presenter.
 */
public final class RecipeDetailPresenter implements RecipeDetailOutputBoundary {

    /**
     * The recipe detail view model.
     */
    private final RecipeDetailViewModel viewModel;

    /**
     * Constructor for the recipe detail presenter.
     * @param pviewModel the recipe detail view model.
     */
    public RecipeDetailPresenter(final RecipeDetailViewModel pviewModel) {
        this.viewModel = pviewModel;
    }

    @Override
    public void prepareSuccessView(final RecipeDetailOutputData outputData) {
        viewModel.setDetails(outputData.getDetails());
        viewModel.setMessage(null);
        viewModel.setLoading(false);
    }

    @Override
    public void prepareFailView(final String errorMessage) {
        viewModel.setMessage(errorMessage);
        viewModel.setDetails(null);
        viewModel.setLoading(false);
    }

    /**
     * Get the recipe detail view model.
     * @return the recipe detail view model.
     */
    public RecipeDetailViewModel getViewModel() {
        return viewModel;
    }
}

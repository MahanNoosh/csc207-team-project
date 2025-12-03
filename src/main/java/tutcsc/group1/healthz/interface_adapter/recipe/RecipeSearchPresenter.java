package tutcsc.group1.healthz.interface_adapter.recipe;

import java.util.List;

import tutcsc.group1.healthz.entities.nutrition.RecipeSearchResult;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchOutputBoundary;

/**
 * The recipe search presenter.
 */
public final class RecipeSearchPresenter implements
        RecipeSearchOutputBoundary {
    /**
     * The recipe search view model.
     */
    private final RecipeSearchViewModel viewModel;

    /**
     * The constructor for the recipe search presenter.
     * @param pviewModel the recipe search view model.
     */
    public RecipeSearchPresenter(final RecipeSearchViewModel pviewModel) {
        this.viewModel = pviewModel;
    }

    @Override
    public void presentSuccess(final List<RecipeSearchResult> results) {
        System.out.println("Presenter: Presenting " + results.size()
                + " results");
        viewModel.setResults(results);
        viewModel.setMessage(null);
        viewModel.setLoading(false);
    }

    @Override
    public void presentFailure(final String errorMessage) {
        viewModel.setMessage(errorMessage);
        viewModel.setResults(java.util.List.of());
        viewModel.setLoading(false);
    }

    /**
     * Get the recipe search view model.
     * @return the recipe search view model.
     */
    public RecipeSearchViewModel getViewModel() {
        return viewModel;
    }
}

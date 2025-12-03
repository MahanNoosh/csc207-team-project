package tutcsc.group1.healthz.interface_adapter.recipe;

import tutcsc.group1.healthz.entities.nutrition.RecipeFilter;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchInputBoundary;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchInputData;

/**
 * Controller for recipe search.
 */
public final class RecipeSearchController {
    /**
     * Recipe search input boundary.
     */
    private final RecipeSearchInputBoundary interactor;

    /**
     * Recipe search presenter.
     */
    private final RecipeSearchPresenter presenter;

    /**
     * Constructor for recipe search controller.
     * @param pinteractor the recipe search input boundary.
     * @param ppresenter the recipe search presenter.
     */
    public RecipeSearchController(final RecipeSearchInputBoundary pinteractor,
                                  final RecipeSearchPresenter ppresenter) {
        this.interactor = pinteractor;
        this.presenter = ppresenter;
    }

    /**
     * Get the input data and pass it to the interactor.
     * @param query the search term (name or ingredient in recipe).
     * @param filter the recipe filter entity with filter parameters.
     */
    public void search(final String query, final RecipeFilter filter) {
        presenter.getViewModel().setMessage(null);
        presenter.getViewModel().setResults(java.util.List.of());
        presenter.getViewModel().setLoading(true);
        interactor.execute(new RecipeSearchInputData(query, filter));
    }

    /**
     * Get the recipe search view model.
     * @return the recipe search view model.
     */
    public RecipeSearchViewModel getViewModel() {
        return presenter.getViewModel();
    }
}

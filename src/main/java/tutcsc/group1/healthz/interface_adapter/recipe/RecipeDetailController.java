package tutcsc.group1.healthz.interface_adapter.recipe;

import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailInputBoundary;
import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailInputData;

/**
 * The recipe detail controller.
 */
public final class RecipeDetailController {
    /**
     * The recipe detail input boundary.
     */
    private final RecipeDetailInputBoundary interactor;

    /**
     * The recipe detail presenter.
     */
    private final RecipeDetailPresenter presenter;

    /**
     * The constructor of the recipe detail controller.
     * @param pinteractor the recipe detail input boundary.
     * @param ppresenter the recipe detail presenter.
     */
    public RecipeDetailController(final RecipeDetailInputBoundary pinteractor,
                                  final RecipeDetailPresenter ppresenter) {
        this.interactor = pinteractor;
        this.presenter = ppresenter;
    }

    /**
     * Get the input data and passes it to the interactor.
     * @param recipeId the ID of the recipe to view details of.
     */
    public void fetch(final long recipeId) {
        presenter.getViewModel().setMessage(null);
        presenter.getViewModel().setLoading(true);
        presenter.getViewModel().setDetails(null);
        interactor.execute(new RecipeDetailInputData(recipeId));
    }

    /**
     * Get the recipe detail view model.
     * @return the recipe detail view model.
     */
    public RecipeDetailViewModel getViewModel() {
        return presenter.getViewModel();
    }
}

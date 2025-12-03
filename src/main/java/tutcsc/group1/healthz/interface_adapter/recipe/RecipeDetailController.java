package tutcsc.group1.healthz.interface_adapter.recipe;

import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailInputBoundary;
import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailInputData;

public class RecipeDetailController {
    private final RecipeDetailInputBoundary interactor;
    private final RecipeDetailPresenter presenter;

    public RecipeDetailController(RecipeDetailInputBoundary interactor, RecipeDetailPresenter presenter) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    public void fetch(long recipeId) {
        presenter.getViewModel().setMessage(null);
        presenter.getViewModel().setLoading(true);
        presenter.getViewModel().setDetails(null);
        interactor.execute(new RecipeDetailInputData(recipeId));
    }

    public RecipeDetailViewModel getViewModel() {
        return presenter.getViewModel();
    }
}

package tut0301.group1.healthz.interfaceadapter.recipe;

import tut0301.group1.healthz.interfaceadapter.recipe.RecipeDetailPresenter;
import tut0301.group1.healthz.usecase.recipesearch.detailed.RecipeDetailInputBoundary;
import tut0301.group1.healthz.usecase.recipesearch.detailed.RecipeDetailInputData;

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

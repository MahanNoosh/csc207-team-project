package tut0301.group1.healthz.interfaceadapter.recipe;

import tut0301.group1.healthz.entities.nutrition.Recipe;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeSearchPresenter;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeSearchViewModel;
import tut0301.group1.healthz.usecase.recipesearch.RecipeSearchInputBoundary;

public class RecipeSearchController {
    private final RecipeSearchInputBoundary interactor;
    private final RecipeSearchPresenter presenter;

    public RecipeSearchController(RecipeSearchInputBoundary interactor, RecipeSearchPresenter presenter) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    public void search(String query) {
        presenter.getViewModel().setMessage(null);
        presenter.getViewModel().setResults(java.util.List.of());
        presenter.getViewModel().setLoading(true);
        interactor.search(query);
    }

    public RecipeSearchViewModel getViewModel() {
        return presenter.getViewModel();
    }
}

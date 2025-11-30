package tut0301.group1.healthz.interfaceadapter.recipe;

import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchInputBoundary;
import tut0301.group1.healthz.entities.nutrition.RecipeFilter;

public class RecipeSearchController {
    private final RecipeSearchInputBoundary interactor;
    private final RecipeSearchPresenter presenter;

    public RecipeSearchController(RecipeSearchInputBoundary interactor, RecipeSearchPresenter presenter) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    public void search(String query, RecipeFilter filter) {
        presenter.getViewModel().setMessage(null);
        presenter.getViewModel().setResults(java.util.List.of());
        presenter.getViewModel().setLoading(true);
        interactor.execute(query, filter);
    }

    public RecipeSearchViewModel getViewModel() {
        return presenter.getViewModel();
    }
}

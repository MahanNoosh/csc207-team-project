package tut0301.group1.healthz.controllers;

import tut0301.group1.healthz.presenter.SearchRecipesPresenter;
import tut0301.group1.healthz.usecase.recipes.SearchRecipesInputBoundary;
import tut0301.group1.healthz.usecase.recipes.model.SearchRecipesRequestModel;

/** Controller for recipe-related operations. */
public class RecipeController {
    private final SearchRecipesInputBoundary searchUC;
    private final SearchRecipesPresenter presenter;
    private final String userId; // who to personalize for

    public RecipeController(SearchRecipesInputBoundary searchUC,
                            SearchRecipesPresenter presenter,
                            String userId) {
        this.searchUC = searchUC;
        this.presenter = presenter;
        this.userId = userId;
    }

    public Object search(String query) {
        searchUC.execute(new SearchRecipesRequestModel(userId, query));
        return presenter.viewModel();
    }
}

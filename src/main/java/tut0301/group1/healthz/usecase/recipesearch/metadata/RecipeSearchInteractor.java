package tut0301.group1.healthz.usecase.recipesearch.metadata;

import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;
import tut0301.group1.healthz.entities.nutrition.RecipeFilter;

import java.util.List;

public class RecipeSearchInteractor implements RecipeSearchInputBoundary {
    private final RecipeSearchDataAccessInterface gateway;
    private final RecipeSearchOutputBoundary presenter;

    public RecipeSearchInteractor(RecipeSearchDataAccessInterface gateway, RecipeSearchOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(String query, RecipeFilter filter) {
        if (query == null || query.isBlank()) {
            presenter.presentFailure("Please enter a recipe name or ingredient to search for.");
            return;
        }

        try {
            List<RecipeSearchResult> results = gateway.search(
                    query.trim(), filter);
            presenter.presentSuccess(results);
        } catch (Exception e) {
            presenter.presentFailure("Could not fetch recipe data: " + e.getMessage());
        }
    }
}

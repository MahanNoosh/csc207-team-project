package tut0301.group1.healthz.usecase.recipesearch;

import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;
import tut0301.group1.healthz.usecase.recipesearch.RecipeSearchGateway;
import tut0301.group1.healthz.usecase.recipesearch.RecipeSearchOutputBoundary;

import java.util.List;

public class RecipeSearchInteractor implements RecipeSearchInputBoundary {
    private final RecipeSearchGateway gateway;
    private final RecipeSearchOutputBoundary presenter;

    public RecipeSearchInteractor(RecipeSearchGateway gateway, RecipeSearchOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void search(String query) {
        if (query == null || query.isBlank()) {
            presenter.presentFailure("Please enter a food name to search.");
            return;
        }

        try {
            List<RecipeSearchResult> results = gateway.search(query.trim());
            presenter.presentSuccess(results);
        } catch (Exception e) {
            presenter.presentFailure("Could not fetch recipe data: " + e.getMessage());
        }
    }
}

package tut0301.group1.healthz.usecase.recipesearch;

import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;

import java.util.List;

public interface RecipeSearchOutputBoundary {
    void presentSuccess(List<RecipeSearchResult> results);

    void presentFailure(String errorMessage);
}

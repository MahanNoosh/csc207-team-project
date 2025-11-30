package tut0301.group1.healthz.usecase.recipesearch.metadata;

import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;

import java.util.List;

public interface RecipeSearchOutputBoundary {
    void presentSuccess(List<RecipeSearchResult> results);

    void presentFailure(String errorMessage);

    void presentError(String pleaseEnterASearchTerm);

    void presentResults(List<RecipeSearchResult> results);
}

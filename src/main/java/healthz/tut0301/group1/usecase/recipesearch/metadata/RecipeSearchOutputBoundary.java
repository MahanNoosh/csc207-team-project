package healthz.tut0301.group1.usecase.recipesearch.metadata;

import healthz.tut0301.group1.entities.nutrition.RecipeSearchResult;

import java.util.List;

/**
 * The recipe search output boundary.
 */
public interface RecipeSearchOutputBoundary {
    /**
     * Presents the successful view.
     * @param results a list of recipe search results.
     */
    void presentSuccess(List<RecipeSearchResult> results);

    /**
     * Presents the failed view.
     * @param errorMessage the error message to display.
     */
    void presentFailure(String errorMessage);
}

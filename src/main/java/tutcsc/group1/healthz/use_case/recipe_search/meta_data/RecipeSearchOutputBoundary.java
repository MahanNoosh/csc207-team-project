package tutcsc.group1.healthz.use_case.recipe_search.meta_data;

import tutcsc.group1.healthz.entities.nutrition.RecipeSearchResult;

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

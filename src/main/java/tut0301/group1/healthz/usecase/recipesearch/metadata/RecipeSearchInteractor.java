package tut0301.group1.healthz.usecase.recipesearch.metadata;

import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;
import tut0301.group1.healthz.entities.nutrition.RecipeFilter;

import java.util.List;

/**
 * The recipe search interactor.
 */
public final class RecipeSearchInteractor implements RecipeSearchInputBoundary {
    private final RecipeSearchDataAccessInterface recipeSearchDataAccessObject;
    private final RecipeSearchOutputBoundary recipeSearchPresenter;

    /**
     * The recipe search interactor constructor.
     * @param recipeSearchDataAccessInterface the data access interface.
     * @param recipeSearchOutputBoundary the output boundary.
     */
    public RecipeSearchInteractor(final RecipeSearchDataAccessInterface
                                          recipeSearchDataAccessInterface,
                                  final RecipeSearchOutputBoundary
                                          recipeSearchOutputBoundary) {
        this.recipeSearchDataAccessObject = recipeSearchDataAccessInterface;
        this.recipeSearchPresenter = recipeSearchOutputBoundary;
    }

    @Override
    public void execute(final RecipeSearchInputData recipeSearchInputData) {
        String query = recipeSearchInputData.getQuery();
        RecipeFilter filter = recipeSearchInputData.getFilter();

        if (query == null || query.isBlank()) {
            recipeSearchPresenter.presentFailure(
                    "Please enter a recipe name or ingredient to search for.");
            return;
        }

        try {
            List<RecipeSearchResult> results =
                    recipeSearchDataAccessObject.search(query.trim(), filter);
            recipeSearchPresenter.presentSuccess(results);
        } catch (Exception e) {
            recipeSearchPresenter.presentFailure(
                    "Could not fetch recipe data: " + e.getMessage());
        }
    }
}

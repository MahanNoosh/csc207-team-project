package tut0301.group1.healthz.usecase.favoriterecipe;

import java.util.List;

import tut0301.group1.healthz.entities.nutrition.Recipe;

/**
 * Output boundary for presenting favorite recipes.
 * Defines the contract for displaying favorite recipe results and errors.
 */
public interface LoadFavoritesOutputBoundary {
    /**
     * Presents the list of favorite recipes to the user.
     *
     * @param recipes the list of favorite recipes to display
     */
    void presentFavorites(List<Recipe> recipes);

    /**
     * Presents an error message to the user.
     *
     * @param errorMessage the error message to display
     */
    void presentError(String errorMessage);
}

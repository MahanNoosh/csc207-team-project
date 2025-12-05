package tutcsc.group1.healthz.use_case.favorite_recipe;

import tutcsc.group1.healthz.entities.nutrition.Recipe;
import java.util.List;

import tutcsc.group1.healthz.entities.nutrition.Recipe;

/**
 * Gateway interface for favorite recipe data access.
 */
public interface FavoriteRecipeGateway {
    /**
     * Get all favorite recipes for current user.
     *
     * @param userId the ID of the user whose favorites to retrieve
     * @return list of favorite recipes for the user
     * @throws Exception if retrieval fails
     */
    List<Recipe> getFavoriteRecipes(String userId) throws Exception;

    /**
     * Add a recipe to favorites.
     *
     * @param userId the ID of the user adding the favorite
     * @param recipeId the ID of the recipe to add
     * @throws Exception if adding fails
     */
    void addFavorite(String userId, String recipeId) throws Exception;

    /**
     * Remove a recipe from favorites.
     *
     * @param userId the ID of the user removing the favorite
     * @param recipeId the ID of the recipe to remove
     * @throws Exception if removal fails
     */
    void removeFavorite(String userId, String recipeId) throws Exception;

    /**
     * Check if a recipe is favorited.
     *
     * @param userId the ID of the user to check
     * @param recipeId the ID of the recipe to check
     * @return true if the recipe is in the user's favorites, false otherwise
     * @throws Exception if check fails
     */
    boolean isFavorite(String userId, String recipeId) throws Exception;
}

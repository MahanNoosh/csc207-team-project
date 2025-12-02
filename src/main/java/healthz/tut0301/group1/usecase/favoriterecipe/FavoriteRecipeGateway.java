package healthz.tut0301.group1.usecase.favoriterecipe;

import healthz.tut0301.group1.entities.nutrition.Recipe;
import java.util.List;

/**
 * Gateway interface for favorite recipe data access
 */
public interface FavoriteRecipeGateway {
    /**
     * Get all favorite recipes for current user
     */
    List<Recipe> getFavoriteRecipes(String userId) throws Exception;

    /**
     * Add a recipe to favorites
     */
    void addFavorite(String userId, String recipeId) throws Exception;

    /**
     * Remove a recipe from favorites
     */
    void removeFavorite(String userId, String recipeId) throws Exception;

    /**
     * Check if a recipe is favorited
     */
    boolean isFavorite(String userId, String recipeId) throws Exception;
}
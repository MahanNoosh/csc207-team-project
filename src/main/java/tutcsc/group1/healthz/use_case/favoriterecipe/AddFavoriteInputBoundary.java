package tutcsc.group1.healthz.use_case.favoriterecipe;

/**
 * Input boundary for adding a recipe to user's favorites.
 * Defines the contract for the Add Favorite use case.
 */
public interface AddFavoriteInputBoundary {
    /**
     * Adds a recipe to the user's favorites.
     *
     * @param userId the ID of the user adding the favorite
     * @param recipeId the ID of the recipe to add to favorites
     * @throws Exception if the operation fails
     */
    void addFavorite(String userId, String recipeId) throws Exception;
}

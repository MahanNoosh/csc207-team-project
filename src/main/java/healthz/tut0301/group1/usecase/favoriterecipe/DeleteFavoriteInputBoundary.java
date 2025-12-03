package healthz.tut0301.group1.usecase.favoriterecipe;

/**
 * Input boundary for deleting a favorite recipe.
 * Defines the contract for the Delete Favorite use case.
 */
public interface DeleteFavoriteInputBoundary {
    /**
     * Deletes a recipe from the user's favorites.
     *
     * @param userId the ID of the user removing the favorite
     * @param recipeId the ID of the recipe to remove from favorites
     */
    void deleteFavorite(String userId, String recipeId);
}

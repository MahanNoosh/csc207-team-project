package healthz.tut0301.group1.usecase.favoriterecipe;

/**
 * Input boundary for deleting a favorite recipe
 */
public interface DeleteFavoriteInputBoundary {
    void deleteFavorite(String userId, String recipeId);
}
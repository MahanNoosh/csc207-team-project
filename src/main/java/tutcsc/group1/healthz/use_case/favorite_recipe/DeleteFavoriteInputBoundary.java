package tutcsc.group1.healthz.use_case.favorite_recipe;

/**
 * Input boundary for deleting a favorite recipe
 */
public interface DeleteFavoriteInputBoundary {
    void deleteFavorite(String userId, String recipeId);
}
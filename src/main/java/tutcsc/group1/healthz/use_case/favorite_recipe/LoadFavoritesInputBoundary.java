package tutcsc.group1.healthz.use_case.favorite_recipe;

/**
 * Input boundary for loading favorite recipes
 */
public interface LoadFavoritesInputBoundary {
    void loadFavorites(String userId);
}
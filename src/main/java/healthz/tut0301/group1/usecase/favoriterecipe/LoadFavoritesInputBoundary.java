package healthz.tut0301.group1.usecase.favoriterecipe;

/**
 * Input boundary for loading favorite recipes
 */
public interface LoadFavoritesInputBoundary {
    void loadFavorites(String userId);
}
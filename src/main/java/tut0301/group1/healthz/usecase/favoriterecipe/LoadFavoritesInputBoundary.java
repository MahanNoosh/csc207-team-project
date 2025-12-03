package tut0301.group1.healthz.usecase.favoriterecipe;

/**
 * Input boundary for loading favorite recipes.
 * Defines the contract for the Load Favorites use case.
 */
public interface LoadFavoritesInputBoundary {
    /**
     * Loads all favorite recipes for the specified user.
     *
     * @param userId the ID of the user whose favorites to load
     */
    void loadFavorites(String userId);
}

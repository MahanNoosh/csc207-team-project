package tutcsc.group1.healthz.use_case.favorite_recipe;

import tutcsc.group1.healthz.entities.nutrition.Recipe;
import java.util.List;

import tutcsc.group1.healthz.entities.nutrition.Recipe;

/**
 * Interactor for loading favorite recipes.
 * Implements the Load Favorites use case by retrieving and presenting the user's favorites.
 */
public class LoadFavoritesInteractor implements LoadFavoritesInputBoundary {
    private final FavoriteRecipeGateway gateway;
    private final LoadFavoritesOutputBoundary presenter;

    /**
     * Constructs a LoadFavoritesInteractor with the specified gateway and presenter.
     *
     * @param gateway the gateway for accessing favorite recipe data
     * @param presenter the presenter for displaying results to the user
     */
    public LoadFavoritesInteractor(FavoriteRecipeGateway gateway,
                                   LoadFavoritesOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    /**
     * Loads all favorite recipes for the specified user.
     * If the user ID is invalid, presents an error message.
     * If loading fails, presents an error message.
     *
     * @param userId the ID of the user whose favorites to load
     */
    @Override
    public void loadFavorites(String userId) {
        if (userId == null || userId.isEmpty()) {
            presenter.presentError("User not logged in");
        }
        else {
            this.loadAndPresentFavorites(userId);
        }
    }

    private void loadAndPresentFavorites(String userId) {
        try {
            final List<Recipe> favorites = gateway.getFavoriteRecipes(userId);
            presenter.presentFavorites(favorites);
        }
        catch (Exception ex) {
            presenter.presentError("Failed to load favorites: " + ex.getMessage());
        }
    }
}

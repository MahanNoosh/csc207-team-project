package tut0301.group1.healthz.usecase.favoriterecipe;

import tut0301.group1.healthz.entities.nutrition.Recipe;
import java.util.List;

/**
 * Interactor for loading favorite recipes
 */
public class LoadFavoritesInteractor implements LoadFavoritesInputBoundary {
    private final FavoriteRecipeGateway gateway;
    private final LoadFavoritesOutputBoundary presenter;

    public LoadFavoritesInteractor(FavoriteRecipeGateway gateway,
                                   LoadFavoritesOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void loadFavorites(String userId) {
        if (userId == null || userId.isEmpty()) {
            presenter.presentError("User not logged in");
            return;
        }

        try {
            List<Recipe> favorites = gateway.getFavoriteRecipes(userId);
            presenter.presentFavorites(favorites);
        } catch (Exception e) {
            presenter.presentError("Failed to load favorites: " + e.getMessage());
        }
    }
}
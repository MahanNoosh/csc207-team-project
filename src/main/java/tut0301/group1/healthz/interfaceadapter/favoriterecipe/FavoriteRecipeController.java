package tut0301.group1.healthz.interfaceadapter.favoriterecipe;

import tut0301.group1.healthz.usecase.favoriterecipe.LoadFavoritesInputBoundary;
import tut0301.group1.healthz.usecase.favoriterecipe.DeleteFavoriteInputBoundary;

/**
 * Controller for favorite recipes
 */
public class FavoriteRecipeController {
    private final LoadFavoritesInputBoundary loadInteractor;
    private final DeleteFavoriteInputBoundary deleteInteractor;
    private final FavoriteRecipePresenter presenter;

    public FavoriteRecipeController(LoadFavoritesInputBoundary loadInteractor,
                                    DeleteFavoriteInputBoundary deleteInteractor,
                                    FavoriteRecipePresenter presenter) {
        this.loadInteractor = loadInteractor;
        this.deleteInteractor = deleteInteractor;
        this.presenter = presenter;
    }

    /**
     * Load user's favorite recipes
     */
    public void loadFavorites(String userId) {
        System.out.println("🎮 Controller: Loading favorites for user: " + userId);
        presenter.getViewModel().setLoading(true);
        loadInteractor.loadFavorites(userId);
    }

    /**
     * Delete a favorite recipe
     */
    public void deleteFavorite(String userId, String recipeId) {
        System.out.println("🎮 Controller: Deleting favorite: " + recipeId);
        deleteInteractor.deleteFavorite(userId, recipeId);
    }

    public FavoriteRecipeViewModel getViewModel() {
        return presenter.getViewModel();
    }
}
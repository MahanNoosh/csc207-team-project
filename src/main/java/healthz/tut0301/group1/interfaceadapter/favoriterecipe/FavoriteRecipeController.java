package healthz.tut0301.group1.interfaceadapter.favoriterecipe;

import healthz.tut0301.group1.usecase.favoriterecipe.LoadFavoritesInputBoundary;
import healthz.tut0301.group1.usecase.favoriterecipe.DeleteFavoriteInputBoundary;

/**
 * Controller for favorite recipes.
 * Coordinates loading and deleting favorite recipes by delegating to use case interactors.
 */
public class FavoriteRecipeController {
    private final LoadFavoritesInputBoundary loadInteractor;
    private final DeleteFavoriteInputBoundary deleteInteractor;
    private final FavoriteRecipePresenter presenter;

    /**
     * Constructs a FavoriteRecipeController with the specified interactors and presenter.
     *
     * @param loadInteractor the interactor for loading favorites
     * @param deleteInteractor the interactor for deleting favorites
     * @param presenter the presenter for displaying favorite recipes
     */
    public FavoriteRecipeController(LoadFavoritesInputBoundary loadInteractor,
                                    DeleteFavoriteInputBoundary deleteInteractor,
                                    FavoriteRecipePresenter presenter) {
        this.loadInteractor = loadInteractor;
        this.deleteInteractor = deleteInteractor;
        this.presenter = presenter;
    }

    /**
     * Load user's favorite recipes.
     *
     * @param userId the ID of the user whose favorites to load
     */
    public void loadFavorites(String userId) {
        System.out.println("Controller: Loading favorites for user: " + userId);
        presenter.getViewModel().setLoading(true);
        loadInteractor.loadFavorites(userId);
    }

    /**
     * Delete a favorite recipe.
     *
     * @param userId the ID of the user removing the favorite
     * @param recipeId the ID of the recipe to delete
     */
    public void deleteFavorite(String userId, String recipeId) {
        System.out.println("Controller: Deleting favorite: " + recipeId);
        deleteInteractor.deleteFavorite(userId, recipeId);
    }

    /**
     * Gets the view model for favorite recipes.
     *
     * @return the favorite recipe view model
     */
    public FavoriteRecipeViewModel getViewModel() {
        return presenter.getViewModel();
    }
}

package tutcsc.group1.healthz.use_case.favorite_recipe;

/**
 * Interactor for deleting a favorite recipe
 */
public class DeleteFavoriteInteractor implements DeleteFavoriteInputBoundary {
    private final FavoriteRecipeGateway gateway;
    private final LoadFavoritesOutputBoundary presenter;

    public DeleteFavoriteInteractor(FavoriteRecipeGateway gateway,
                                    LoadFavoritesOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void deleteFavorite(String userId, String recipeId) {
        if (userId == null || userId.isEmpty()) {
            presenter.presentError("User not logged in");
            return;
        }

        if (recipeId == null || recipeId.isEmpty()) {
            presenter.presentError("Recipe ID is missing");
            return;
        }

        try {
            gateway.removeFavorite(userId, recipeId);
            // Reload favorites after deletion
            var favorites = gateway.getFavoriteRecipes(userId);
            presenter.presentFavorites(favorites);
        } catch (Exception e) {
            presenter.presentError("Failed to delete favorite: " + e.getMessage());
        }
    }
}
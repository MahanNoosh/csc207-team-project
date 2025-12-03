package tutcsc.group1.healthz.use_case.favoriterecipe;

/**
 * Interactor for deleting a favorite recipe.
 * Implements the Delete Favorite use case by validating inputs, removing the favorite,
 * and reloading the updated favorites list.
 */
public class DeleteFavoriteInteractor implements DeleteFavoriteInputBoundary {
    private final FavoriteRecipeGateway gateway;
    private final LoadFavoritesOutputBoundary presenter;

    /**
     * Constructs a DeleteFavoriteInteractor with the specified gateway and presenter.
     *
     * @param gateway the gateway for accessing favorite recipe data
     * @param presenter the presenter for displaying results to the user
     */
    public DeleteFavoriteInteractor(FavoriteRecipeGateway gateway,
                                    LoadFavoritesOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    /**
     * Deletes a recipe from the user's favorites.
     * Validates inputs, removes the favorite, and reloads the updated list.
     * If validation fails or deletion encounters an error, presents an error message.
     *
     * @param userId the ID of the user removing the favorite
     * @param recipeId the ID of the recipe to remove from favorites
     */
    @Override
    public void deleteFavorite(String userId, String recipeId) {
        if (userId == null || userId.isEmpty()) {
            presenter.presentError("User not logged in");
        }
        else if (recipeId == null || recipeId.isEmpty()) {
            presenter.presentError("Recipe ID is missing");
        }
        else {
            this.executeDelete(userId, recipeId);
        }
    }

    private void executeDelete(String userId, String recipeId) {
        try {
            gateway.removeFavorite(userId, recipeId);
            final var favorites = gateway.getFavoriteRecipes(userId);
            presenter.presentFavorites(favorites);
        }
        catch (Exception ex) {
            presenter.presentError("Failed to delete favorite: " + ex.getMessage());
        }
    }
}

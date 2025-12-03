package tut0301.group1.healthz.interfaceadapter.favoriterecipe;

import tut0301.group1.healthz.usecase.favoriterecipe.AddFavoriteInputBoundary;

/**
 * Controller for adding favorite recipes.
 * Handles user input and delegates to the Add Favorite use case.
 */
public class AddFavoriteController {

    private final AddFavoriteInputBoundary addFavoriteInteractor;

    /**
     * Constructs an AddFavoriteController with the specified interactor.
     *
     * @param addFavoriteInteractor the interactor for adding favorites
     */
    public AddFavoriteController(AddFavoriteInputBoundary addFavoriteInteractor) {
        this.addFavoriteInteractor = addFavoriteInteractor;
    }

    /**
     * Adds a recipe to the user's favorites.
     *
     * @param userId the ID of the user adding the favorite
     * @param recipeId the ID of the recipe to add
     * @throws Exception if adding the favorite fails
     */
    public void addFavorite(String userId, String recipeId) throws Exception {
        System.out.println("Controller: Adding favorite");
        addFavoriteInteractor.addFavorite(userId, recipeId);
    }
}

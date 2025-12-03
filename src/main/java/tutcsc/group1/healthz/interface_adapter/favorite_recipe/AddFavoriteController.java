package tutcsc.group1.healthz.interface_adapter.favorite_recipe;

import tutcsc.group1.healthz.use_case.favorite_recipe.AddFavoriteInputBoundary;

public class AddFavoriteController {

    private final AddFavoriteInputBoundary addFavoriteInteractor;

    public AddFavoriteController(AddFavoriteInputBoundary addFavoriteInteractor) {
        this.addFavoriteInteractor = addFavoriteInteractor;
    }

    public void addFavorite(String userId, String recipeId) throws Exception {
        System.out.println("Controller: Adding favorite");
        addFavoriteInteractor.addFavorite(userId, recipeId);
    }
}

package healthz.tut0301.group1.interfaceadapter.favoriterecipe;

import healthz.tut0301.group1.usecase.favoriterecipe.AddFavoriteInputBoundary;

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

package healthz.tut0301.group1.usecase.favoriterecipe;

public class AddFavoriteInteractor implements AddFavoriteInputBoundary {

    private final FavoriteRecipeGateway gateway;

    public AddFavoriteInteractor(FavoriteRecipeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void addFavorite(String userId, String recipeId) throws Exception {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        if (recipeId == null || recipeId.isEmpty()) {
            throw new IllegalArgumentException("Recipe ID cannot be null or empty");
        }

        System.out.println("Use Case: Adding favorite - User: " + userId + ", Recipe: " + recipeId);

        gateway.addFavorite(userId, recipeId);

        System.out.println("Use Case: Favorite added successfully");
    }
}
package tutcsc.group1.healthz.use_case.favorite_recipe;

/**
 * Interactor for adding a recipe to user's favorites.
 * Implements the Add Favorite use case by validating inputs and delegating to the gateway.
 */
public class AddFavoriteInteractor implements AddFavoriteInputBoundary {
    private final FavoriteRecipeGateway gateway;

    /**
     * Constructs an AddFavoriteInteractor with the specified gateway.
     *
     * @param gateway the gateway for accessing favorite recipe data
     */
    public AddFavoriteInteractor(FavoriteRecipeGateway gateway) {
        this.gateway = gateway;
    }

    /**
     * Adds a recipe to the user's favorites.
     * Validates that both userId and recipeId are non-null and non-empty before adding.
     *
     * @param userId the ID of the user adding the favorite
     * @param recipeId the ID of the recipe to add to favorites
     * @throws IllegalArgumentException if userId or recipeId is null or empty
     * @throws Exception if the gateway fails to add the favorite
     */
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

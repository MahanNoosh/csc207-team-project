package tutcsc.group1.healthz.use_case.favorite_recipe;

public interface AddFavoriteInputBoundary {
    void addFavorite(String userId, String recipeId) throws Exception;
}
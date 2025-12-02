package tut0301.group1.healthz.usecase.favoriterecipe;

public interface AddFavoriteInputBoundary {
    void addFavorite(String userId, String recipeId) throws Exception;
}
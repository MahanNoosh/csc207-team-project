package healthz.tut0301.group1.usecase.favoriterecipe;

public interface AddFavoriteInputBoundary {
    void addFavorite(String userId, String recipeId) throws Exception;
}
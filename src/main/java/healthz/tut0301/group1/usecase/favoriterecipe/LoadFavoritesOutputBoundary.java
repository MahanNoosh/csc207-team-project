package healthz.tut0301.group1.usecase.favoriterecipe;

import healthz.tut0301.group1.entities.nutrition.Recipe;
import java.util.List;

/**
 * Output boundary for presenting favorite recipes
 */
public interface LoadFavoritesOutputBoundary {
    void presentFavorites(List<Recipe> recipes);
    void presentError(String errorMessage);
}
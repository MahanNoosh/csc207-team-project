package tut0301.group1.healthz.usecase.favoriterecipe;

import tut0301.group1.healthz.entities.nutrition.Recipe;
import java.util.List;

/**
 * Output boundary for presenting favorite recipes
 */
public interface LoadFavoritesOutputBoundary {
    void presentFavorites(List<Recipe> recipes);
    void presentError(String errorMessage);
}
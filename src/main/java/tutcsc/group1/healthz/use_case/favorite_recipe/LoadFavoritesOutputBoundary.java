package tutcsc.group1.healthz.use_case.favorite_recipe;

import tutcsc.group1.healthz.entities.nutrition.Recipe;
import java.util.List;

/**
 * Output boundary for presenting favorite recipes
 */
public interface LoadFavoritesOutputBoundary {
    void presentFavorites(List<Recipe> recipes);
    void presentError(String errorMessage);
}
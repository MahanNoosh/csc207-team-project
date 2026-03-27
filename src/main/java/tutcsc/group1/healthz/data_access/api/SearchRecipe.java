package tutcsc.group1.healthz.data_access.api;
import tutcsc.group1.healthz.entities.nutrition.Recipe;

import java.util.List;

/**
 * Interface for searching for a recipe.
 */
public interface SearchRecipe {
    List<String> getRecipeNames(String searchExpression, Integer maxResults, Integer pageNumber,
                                String recipeType) throws SearchRecipe.RecipeNotFoundException;

    List<Recipe> searchByName(String searchExpression, Integer maxResults, Integer pageNumber,
                        String recipeType) throws SearchRecipe.RecipeNotFoundException;

    Recipe searchById(String id) throws SearchRecipe.RecipeNotFoundException;

    class RecipeNotFoundException extends Exception {
        public RecipeNotFoundException() {
            super("Recipe not found.");
        }
    }
}

package tut0301.group1.healthz.dataaccess.API;
import tut0301.group1.healthz.entities.nutrition.Recipe;

import java.util.List;

/**
 * Interface for searching for a recipe.
 */
public interface SearchRecipe {
    List<Recipe> searchByName(String searchExpression, Integer maxResults, Integer pageNumber,
                        String recipeType) throws SearchRecipe.RecipeNotFoundException;

    Recipe searchById(String id) throws SearchRecipe.RecipeNotFoundException;

    class RecipeNotFoundException extends Exception {
        public RecipeNotFoundException() {
            super("Recipe not found.");
        }
    }
}

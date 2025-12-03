package tutcsc.group1.healthz.data_access.api;

import java.util.List;

import tutcsc.group1.healthz.entities.nutrition.Recipe;

/**
 * Interface for searching for a recipe.
 */
public interface SearchRecipe {
    /**
     * Search for a recipe by name or ingredient.
     * @param searchExpression the search expression (query).
     * @param maxResults the maximum number of results to display.
     * @param pageNumber the number of pages to display.
     * @param recipeType the recipe category to search for.
     * @return a list of Recipe entities that are found.
     * @throws SearchRecipe.RecipeNotFoundException if a recipe is not found.
     */
    List<Recipe> searchByName(String searchExpression, Integer maxResults,
                              Integer pageNumber, String recipeType) throws
            SearchRecipe.RecipeNotFoundException;

    /**
     * Search for a recipe by ID.
     * @param id the ID of the recipe to search for.
     * @return the Recipe entity with the corresponding ID.
     * @throws SearchRecipe.RecipeNotFoundException if a recipe is not found.
     */
    Recipe searchById(String id) throws SearchRecipe.RecipeNotFoundException;

    class RecipeNotFoundException extends Exception {
        /**
         * The constructor for a recipe not found exception.
         */
        public RecipeNotFoundException() {
            super("Recipe not found.");
        }
    }
}

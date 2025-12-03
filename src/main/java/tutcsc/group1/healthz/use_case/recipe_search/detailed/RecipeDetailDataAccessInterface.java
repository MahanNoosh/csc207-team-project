package tutcsc.group1.healthz.use_case.recipe_search.detailed;

import tutcsc.group1.healthz.entities.nutrition.RecipeDetails;

/**
 * The recipe detail data access interface.
 */
public interface RecipeDetailDataAccessInterface {
    /**
     * Get the recipe details of the recipe with the given ID.
     * @param recipeId the ID of the recipe to display.
     * @return a RecipeDetails entity (output data).
     * @throws Exception if a recipe with the ID is not found.
     */
    RecipeDetails fetchDetails(long recipeId) throws Exception;
}

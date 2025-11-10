package tut0301.group1.healthz.dataaccess.recipeapi;

/**
 * Interface for getting a recipe name.
 */
public interface Recipe extends Search {

    /**
     * Fetch the name of the recipe.
     * @param recipe the recipe to search for
     * @return the name of the recipe
     * @throws RecipeNotFoundException if the recipe does not exist
     */
    String getRecipeName(String recipe) throws Recipe.RecipeNotFoundException;

    /**
     * Fetch the ID of the recipe.
     * @param recipe the recipe to search for
     * @return the ID of the recipe
     * @throws RecipeNotFoundException if the recipe does not exist
     */
    String getRecipeID(String recipe) throws Recipe.RecipeNotFoundException;

    class RecipeNotFoundException extends Exception {
        public RecipeNotFoundException(String recipe) {
            super("Recipe not found: " + recipe);
        }
    }
}

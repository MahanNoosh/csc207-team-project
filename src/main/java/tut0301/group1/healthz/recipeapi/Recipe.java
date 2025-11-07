package recipeapi;

/**
 * Interface for getting a recipe name.
 */
public interface Recipe {

    /**
     * Fetch the name of the recipe.
     * @param recipe the recipe to search for
     * @return the name of the recipe
     * @throws RecipeNotFoundException if the recipe does not exist
     */
    String getRecipeName(String recipe) throws Recipe.RecipeNotFoundException;

    class RecipeNotFoundException extends Exception {
        public RecipeNotFoundException(String recipe) {
            super("Recipe not found: " + recipe);
        }
    }
}

package tut0301.group1.healthz.dataaccess.API;

public interface RecipeMealDB {
    /**
     * Fetch the name of the recipe.
     * @param recipe the recipe to search for
     * @return the name of the recipe
     * @throws RecipeNotFoundException if no recipe is found
     */
    String getRecipeName(String recipe) throws RecipeMealDB.RecipeNotFoundException;

    class RecipeNotFoundException extends Exception {
        public RecipeNotFoundException() {
            super("Recipe not found.");
        }
    }
}

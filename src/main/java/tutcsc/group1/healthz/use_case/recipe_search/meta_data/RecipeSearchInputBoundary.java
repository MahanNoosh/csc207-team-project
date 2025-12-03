package tutcsc.group1.healthz.use_case.recipe_search.meta_data;

/**
 * The recipe search input boundary.
 */
public interface RecipeSearchInputBoundary {
    /**
     * Executes the recipe search use case.
     * @param recipeSearchInputData the recipe search input data.
     */
    void execute(RecipeSearchInputData recipeSearchInputData);
}

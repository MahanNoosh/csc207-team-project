package healthz.tut0301.group1.usecase.recipesearch.metadata;

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

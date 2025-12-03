package tutcsc.group1.healthz.use_case.recipe_search.detailed;

/**
 * The recipe detail input data.
 */
public class RecipeDetailInputData {
    /**
     * The recipe ID.
     */
    private final long recipeId;

    /**
     * Constructor of the recipe detail input data.
     * @param precipeId the recipe ID.
     */
    public RecipeDetailInputData(final long precipeId) {
        this.recipeId = precipeId;
    }

    /**
     * Get the recipe ID.
     * @return the recipe ID.
     */
    public long getRecipeId() {
        return recipeId;
    }
}

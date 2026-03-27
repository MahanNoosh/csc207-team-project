package tutcsc.group1.healthz.use_case.recipe_search.detailed;

public class RecipeDetailInputData {
    private final long recipeId;

    public RecipeDetailInputData(long recipeId) {
        this.recipeId = recipeId;
    }

    public long getRecipeId() {
        return recipeId;
    }
}
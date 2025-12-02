package tut0301.group1.healthz.usecase.recipesearch.detailed;

public class RecipeDetailInputData {
    private final long recipeId;

    public RecipeDetailInputData(long recipeId) {
        this.recipeId = recipeId;
    }

    public long getRecipeId() {
        return recipeId;
    }
}
package healthz.tut0301.group1.usecase.recipesearch.detailed;

import healthz.tut0301.group1.entities.nutrition.RecipeDetails;

/**
 * The recipe detail output data.
 */
public class RecipeDetailOutputData {
    /**
     * The recipe details entity containing all necessary information.
     */
    private final RecipeDetails details;

    /**
     * Constructor for recipe detail output data.
     * @param pdetails the recipe details to display.
     */
    public RecipeDetailOutputData(final RecipeDetails pdetails) {
        this.details = pdetails;
    }

    /**
     * Get the recipe details.
     * @return a recipe details entity.
     */
    public RecipeDetails getDetails() {
        return details;
    }
}

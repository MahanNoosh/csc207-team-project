package tutcsc.group1.healthz.use_case.recipe_search.detailed;

import tutcsc.group1.healthz.entities.nutrition.RecipeDetails;

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

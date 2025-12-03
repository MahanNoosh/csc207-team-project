package tutcsc.group1.healthz.use_case.recipe_search.detailed;

/**
 * The recipe detail output boundary.
 */
public interface RecipeDetailOutputBoundary {
    /**
     * Prepares the successful view.
     * @param outputData the recipe detail output data.
     */
    void prepareSuccessView(RecipeDetailOutputData outputData);

    /**
     * Prepares the failed view.
     * @param errorMessage the error message to display.
     */
    void prepareFailView(String errorMessage);
}

package tutcsc.group1.healthz.use_case.recipe_search.detailed;

/**
 * The recipe detail interactor.
 */
public final class RecipeDetailInteractor implements RecipeDetailInputBoundary {
    /**
     * The recipe detail data access interface.
     */
    private final RecipeDetailDataAccessInterface gateway;

    /**
     * The recipe detail output boundary.
     */
    private final RecipeDetailOutputBoundary presenter;

    /**
     * Constructor of the recipe detail interactor.
     * @param pgateway the recipe detail data access interface.
     * @param ppresenter the recipe detail output boundary.
     */
    public RecipeDetailInteractor(final RecipeDetailDataAccessInterface
                                          pgateway,
                                  final RecipeDetailOutputBoundary
                                          ppresenter) {
        this.gateway = pgateway;
        this.presenter = ppresenter;
    }

    @Override
    public void execute(final RecipeDetailInputData inputData) {
        if (inputData == null || inputData.getRecipeId() <= 0) {
            presenter.prepareFailView("Invalid or missing recipe ID.");
            return;
        }

        try {
            RecipeDetailOutputData outputData = new RecipeDetailOutputData(
                    gateway.fetchDetails(inputData.getRecipeId()));
            presenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            presenter.prepareFailView("Could not load recipe details: "
                    + e.getMessage());
        }
    }
}

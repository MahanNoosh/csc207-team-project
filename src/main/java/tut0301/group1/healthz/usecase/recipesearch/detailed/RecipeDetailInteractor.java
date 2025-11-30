package tut0301.group1.healthz.usecase.recipesearch.detailed;

public class RecipeDetailInteractor implements RecipeDetailInputBoundary {
    private final RecipeDetailDataAccessInterface gateway;
    private final RecipeDetailOutputBoundary presenter;

    public RecipeDetailInteractor(RecipeDetailDataAccessInterface gateway, RecipeDetailOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(RecipeDetailInputData inputData) {
        if (inputData == null || inputData.getRecipeId() <= 0) {
            presenter.prepareFailView("Invalid or missing recipe ID.");
            return;
        }

        try {
            RecipeDetailOutputData outputData = new RecipeDetailOutputData(gateway.fetchDetails(inputData.getRecipeId()));
            presenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            presenter.prepareFailView("Could not load recipe details: " + e.getMessage());
        }
    }
}

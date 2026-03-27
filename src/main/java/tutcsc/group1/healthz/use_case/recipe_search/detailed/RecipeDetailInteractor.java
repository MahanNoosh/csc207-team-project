package tutcsc.group1.healthz.use_case.recipe_search.detailed;

public class RecipeDetailInteractor implements RecipeDetailInputBoundary {
    private final RecipeDetailGateway gateway;
    private final RecipeDetailOutputBoundary presenter;

    public RecipeDetailInteractor(RecipeDetailGateway gateway, RecipeDetailOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(RecipeDetailInputData inputData) {
        if (inputData == null || inputData.getRecipeId() <= 0) {
            presenter.prepareFailView("Missing recipe id.");
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

package tutcsc.group1.healthz.use_case.recipe_search.detailed;

public interface RecipeDetailOutputBoundary {
    void prepareSuccessView(RecipeDetailOutputData outputData);
    void prepareFailView(String errorMessage);
}

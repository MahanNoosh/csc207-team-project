package tut0301.group1.healthz.usecase.recipesearch.detailed;

public interface RecipeDetailOutputBoundary {
    void prepareSuccessView(RecipeDetailOutputData outputData);
    void prepareFailView(String errorMessage);
}

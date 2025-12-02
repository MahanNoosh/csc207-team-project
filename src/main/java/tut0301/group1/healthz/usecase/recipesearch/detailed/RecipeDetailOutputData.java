package tut0301.group1.healthz.usecase.recipesearch.detailed;

import tut0301.group1.healthz.entities.nutrition.RecipeDetails;

public class RecipeDetailOutputData {
    private final RecipeDetails details;

    public RecipeDetailOutputData(RecipeDetails details) {
        this.details = details;
    }

    public RecipeDetails getDetails() {
        return details;
    }
}

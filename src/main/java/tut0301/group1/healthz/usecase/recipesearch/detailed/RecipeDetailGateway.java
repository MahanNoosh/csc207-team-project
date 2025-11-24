package tut0301.group1.healthz.usecase.recipesearch.detailed;

import tut0301.group1.healthz.entities.nutrition.RecipeDetails;

public interface RecipeDetailGateway {
    RecipeDetails fetchDetails(long recipeId) throws Exception;
}

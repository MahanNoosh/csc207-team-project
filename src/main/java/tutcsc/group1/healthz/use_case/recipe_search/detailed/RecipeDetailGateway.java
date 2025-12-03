package tutcsc.group1.healthz.use_case.recipe_search.detailed;

import tutcsc.group1.healthz.entities.nutrition.RecipeDetails;

public interface RecipeDetailGateway {
    RecipeDetails fetchDetails(long recipeId) throws Exception;
}

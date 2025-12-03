package tutcsc.group1.healthz.use_case.recipe_search.detailed;

import tutcsc.group1.healthz.entities.nutrition.RecipeDetails;

public class RecipeDetailOutputData {
    private final RecipeDetails details;

    public RecipeDetailOutputData(RecipeDetails details) {
        this.details = details;
    }

    public RecipeDetails getDetails() {
        return details;
    }
}

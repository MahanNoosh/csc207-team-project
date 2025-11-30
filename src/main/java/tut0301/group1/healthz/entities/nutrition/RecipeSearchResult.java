package tut0301.group1.healthz.entities.nutrition;

import java.util.List;

public record RecipeSearchResult (String recipeId, String recipeName, String description,
                                  List<String> ingredientNames, String imageUrl) {

    // Get methods
    public String getId() {
        return recipeId;
    }

    public String getName() {
        return recipeName;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getIngredientNames() {
        return ingredientNames;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

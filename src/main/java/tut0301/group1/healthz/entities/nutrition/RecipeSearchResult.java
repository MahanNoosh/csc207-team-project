package tut0301.group1.healthz.entities.nutrition;

import java.util.List;

public record RecipeSearchResult (String recipeId, String recipeName, String description,
                                  List<String> ingredientNames, String imageUrl) {
}

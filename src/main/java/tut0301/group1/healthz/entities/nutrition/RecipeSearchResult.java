package tut0301.group1.healthz.entities.nutrition;

import java.util.List;
import java.util.Optional;

public record RecipeSearchResult (String recipeId, String recipeName, String description, List<String> instructions,
                                  List<RecipeIngredient> ingredients, Optional<Integer> prepTimeMinutes,
                                  Optional<Integer> cookTimeMinutes, Optional<Integer> servings, String imageUrl) {
}

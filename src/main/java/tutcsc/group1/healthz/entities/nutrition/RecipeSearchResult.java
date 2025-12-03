package tutcsc.group1.healthz.entities.nutrition;

import java.util.List;

/**
 * The output data object of the recipe search use case.
 *
 * @param recipeId the ID of the recipe.
 * @param recipeName the recipe name.
 * @param description a description of the recipe.
 * @param ingredientNames a list of the ingredient names.
 * @param imageUrl the URL of the recipe image.
 */
public record RecipeSearchResult(String recipeId, String recipeName,
                                  String description,
                                  List<String> ingredientNames,
                                  String imageUrl) {

    /**
     * Get the ID of the recipe.
     * @return the ID of the recipe.
     */
    public String getId() {
        return recipeId;
    }

    /**
     * Get the name of the recipe.
     * @return the name of the recipe.
     */
    public String getName() {
        return recipeName;
    }

    /**
     * Get a description of the recipe.
     * @return a description of the recipe.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the ingredient names.
     * @return a list of the ingredient names.
     */
    public List<String> getIngredientNames() {
        return ingredientNames;
    }

    /**
     * Get the URL of the recipe image.
     * @return the URL of the recipe image.
     */
    public String getImageUrl() {
        return imageUrl;
    }
}

package tutcsc.group1.healthz.entities.nutrition;

import java.util.List;

public record RecipeDetails(String recipeName, String imageUrl,
                             Double calories, Double protein, Double carbs,
                             Double fats, String servingSize,
                             List<String> dietaryTags,
                             List<String> ingredients,
                             List<String> instructions) {

    /**
     * Method to get the recipe name.
     * @return The name of the recipe.
     */
    public String getName() {
        return recipeName;
    }

    /**
     * Method to get the recipe image url.
     * @return The url of the recipe image.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Method to get the recipe calories.
     * @return The calories of the recipe as a decimal.
     */
    public Double getCalories() {
        return calories;
    }

    /**
     * Method to get the recipe protein amount.
     * @return The amount of protein in the recipe as a decimal.
     */
    public Double getProtein() {
        return protein;
    }

    /**
     * Method to get the amount of carbs in the recipe.
     * @return The amount of carbs as a decimal.
     */
    public Double getCarbs() {
        return carbs;
    }

    /**
     * Method to get the amount of fat in the recipe.
     * @return The amount of fat as a decimal.
     */
    public Double getFats() {
        return fats;
    }

    /**
     * Method to get the recipe serving size.
     * @return The serving size of the recipe.
     */
    public String getServingSize() {
        return servingSize;
    }

    /**
     * Method to get the recipe dietary tags (categories).
     * @return A list of the categories of the recipe.
     */
    public List<String> getDietaryTags() {
        return dietaryTags;
    }

    /**
     * Method to get the recipe ingredients.
     * @return A list of the ingredient names.
     */
    public List<String> getIngredients() {
        return ingredients;
    }

    /**
     * Method to get the recipe instructions.
     * @return A list of the instructions.
     */
    public List<String> getInstructions() {
        return instructions;
    }
}

package tut0301.group1.healthz.entities.nutrition;
import  java.util.List;

public record RecipeDetails (String recipeName, String imageUrl, Double calories, Double protein, Double carbs,
                             Double fats, String servingSize, List<String> dietaryTags,
                             List<String> ingredients, List<String> instructions){

    // Get methods
    public String getName() {
        return recipeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Double getCalories() {
        return calories;
    }

    public Double getProtein() {
        return protein;
    }

    public Double getCarbs() {
        return carbs;
    }

    public Double getFats() {
        return fats;
    }

    public String getServingSize() {
        return servingSize;
    }

    public List<String> getDietaryTags() {
        return dietaryTags;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }
}

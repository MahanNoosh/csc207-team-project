package tutcsc.group1.healthz.entities.nutrition;
import  java.util.List;

public record RecipeDetails (String recipeName, String imageUrl, Double calories, Double protein, Double carbs,
                             Double fats, String servingSize, List<String> dietaryTags,
                             List<String> ingredients, List<String> instructions){
    public String getName() {
        return recipeName;
    }

}

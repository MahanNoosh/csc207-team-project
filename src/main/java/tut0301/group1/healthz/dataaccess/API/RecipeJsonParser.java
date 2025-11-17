package tut0301.group1.healthz.dataaccess.API;

import org.json.JSONArray;
import org.json.JSONObject;
import tut0301.group1.healthz.entities.nutrition.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeJsonParser {
    /**
     * Parses the JSON response from the FatSecret API call.
     * This assumes the JSON structure follows FatSecret's standard format.
     *
     * @param jsonResponse the JSON string returned from the API
     * @param recipeId the id of the recipe to search for
     * @return a Recipe entity that is found
     */
    public static Recipe getRecipeById(String jsonResponse, String recipeId) {
        JSONObject recipeObject = new JSONObject(jsonResponse);
        JSONObject recipe = recipeObject.getJSONObject("recipe");

        String name = recipe.getString("recipe_name");
        String description = recipe.getString("recipe_description");
        List<String> instructions = new ArrayList<>();

        JSONObject directions = recipe.getJSONObject("directions");
        JSONArray direction = directions.getJSONArray("direction");

        for (int i = 0; i < direction.length(); i++) {
            JSONObject step = direction.getJSONObject(i);
            instructions.add(step.getString("direction_description"));
        }

        int prepTime = recipe.getInt("preparation_time_min");
        int cookTime = recipe.getInt("cooking_time_min");
        int servings = recipe.getInt("number_of_servings");
        JSONObject recipeImages = recipe.getJSONObject("recipe_images");
        JSONArray recipeImage = recipeImages.getJSONArray("recipe_image");
        String imageUrl = recipeImage.get(0).toString();

        return new Recipe(recipeId, name, description, instructions, prepTime, cookTime, servings, imageUrl);
    }
}

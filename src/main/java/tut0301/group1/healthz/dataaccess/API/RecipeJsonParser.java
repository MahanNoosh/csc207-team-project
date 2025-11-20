package tut0301.group1.healthz.dataaccess.API;

import org.json.JSONArray;
import org.json.JSONObject;
import tut0301.group1.healthz.entities.nutrition.Recipe;
import tut0301.group1.healthz.entities.nutrition.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecipeJsonParser {
    /**
     * Parses the JSON response from the FatSecret API call for searching for a recipe by search term.
     * This assumes the JSON structure follows FatSecret's standard format.
     *
     * @param jsonResponse the JSON string returned from the API
     * @return a Recipe entity that is found
     */
    public static List<Recipe> getRecipesByName(String jsonResponse) {
        JSONObject recipeObject = new JSONObject(jsonResponse);
        JSONObject recipes = recipeObject.getJSONObject("recipes");
        JSONArray recipeArray = recipes.getJSONArray("recipe");
        List<Recipe> recipesList = new ArrayList<>();

        for (int i = 0; i < recipeArray.length(); i++) {
            JSONObject recipe = recipeArray.getJSONObject(i);
            JSONObject ingredients = recipe.getJSONObject("recipe_ingredients");
            JSONArray ingredientsArray = ingredients.getJSONArray("ingredient");
            List<String> ingredientNames = new ArrayList<>();

            for (int j = 0; j < ingredientsArray.length(); j++) {
                ingredientNames.add(ingredientsArray.getString(j));
            }

            Recipe recipeEntity = new Recipe(
                    recipe.getString("recipe_id"),
                    recipe.getString("recipe_name"),
                    recipe.getString("recipe_description"),
                    List.of("Not applicable"),
                    null, null, null, null,
                    recipe.getString("recipe_image")
            );
            recipesList.add(recipeEntity);
        }
        return recipesList;
    }

    /**
     * Parses the JSON response from the FatSecret API call for searching for a recipe by ID.
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

        JSONObject ingredients = recipe.getJSONObject("ingredients");
        JSONArray ingredientArray = ingredients.getJSONArray("ingredient");
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();

        for (int i = 0; i < ingredientArray.length(); i++) {
            JSONObject ingredient = ingredientArray.getJSONObject(i);

            RecipeIngredient recipeIngredient = new RecipeIngredient(
                    Integer.parseInt(ingredient.getString("food_id")),
                    ingredient.getString("food_name"),
                    ingredient.getString("ingredient_description"),
                    ingredient.getString("ingredient_url"),
                    ingredient.getString("measurement_description"),
                    Double.parseDouble(ingredient.getString("number_of_units")),
                    Integer.parseInt(ingredient.getString("serving_id"))
            );
            recipeIngredients.add(recipeIngredient);
        }

        Optional<Integer> prepTime = Integer.valueOf(recipe.getString("preparation_time_min")).describeConstable();
        Optional<Integer> cookTime = Integer.valueOf(recipe.getString("cooking_time_min")).describeConstable();
        Optional<Integer> servings = Integer.valueOf(recipe.getString("number_of_servings")).describeConstable();
        JSONObject recipeImages = recipe.getJSONObject("recipe_images");
        JSONArray recipeImage = recipeImages.getJSONArray("recipe_image");
        String imageUrl = recipeImage.get(0).toString();

        return new Recipe(recipeId, name, description, instructions, recipeIngredients, prepTime, cookTime,
                servings, imageUrl);
    }
}

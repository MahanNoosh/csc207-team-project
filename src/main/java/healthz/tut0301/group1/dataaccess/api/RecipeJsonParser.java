package healthz.tut0301.group1.dataaccess.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import healthz.tut0301.group1.entities.nutrition.Recipe;
import healthz.tut0301.group1.entities.nutrition.RecipeDetails;
import healthz.tut0301.group1.entities.nutrition.RecipeSearchResult;
import healthz.tut0301.group1.entities.nutrition.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class RecipeJsonParser {

    /**
     * Private constructor for the recipe JSON parser class.
     */
    private RecipeJsonParser() {
        throw new AssertionError("Cannot be instantiated.");
    }

    /**
     * Parses the JSON response from the FatSecret API call for searching
     * for a recipe by a search term.
     * This assumes the JSON structure follows FatSecret's standard format.
     *
     * @param jsonResponse the JSON string returned from the API.
     * @return a list of Recipe entities that are found.
     */
    public static List<Recipe> getRecipesByName(final String jsonResponse) {
        JSONObject recipeObject = new JSONObject(jsonResponse);
        JSONObject recipes = recipeObject.getJSONObject("recipes");
        JSONArray recipeArray = recipes.getJSONArray("recipe");
        List<Recipe> recipesList = new ArrayList<>();

        for (int i = 0; i < recipeArray.length(); i++) {
            JSONObject recipe = recipeArray.getJSONObject(i);
            JSONObject ingredients = recipe.getJSONObject(
                    "recipe_ingredients");
            JSONArray ingredientsArray = ingredients.getJSONArray(
                    "ingredient");
            List<String> ingredientNames = new ArrayList<>();

            for (int j = 0; j < ingredientsArray.length(); j++) {
                ingredientNames.add(ingredientsArray.getString(j));
            }

            Recipe recipeEntity = new Recipe(
                    recipe.getString("recipe_id"),
                    recipe.getString("recipe_name"),
                    recipe.getString("recipe_description"),
                    null, null, null, null, null,
                    recipe.getString("recipe_image")
            );
            recipesList.add(recipeEntity);
        }
        return recipesList;
    }

    /**
     * Parses the JSON response from the FatSecret API call for searching
     * for a recipe by ID.
     * This assumes the JSON structure follows FatSecret's standard format.
     *
     * @param jsonResponse the JSON string returned from the API.
     * @param recipeId the id of the recipe to search for.
     * @return a Recipe entity that is found.
     */
    public static Recipe getRecipeById(final String jsonResponse,
                                       final String recipeId) {
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

        Optional<Integer> prepTime =
                recipe.has("preparation_time_min")
                        ? Optional.of(recipe.getInt("preparation_time_min"))
                        : Optional.empty();

        Optional<Integer> cookTime =
                recipe.has("cooking_time_min")
                        ? Optional.of(recipe.getInt("cooking_time_min"))
                        : Optional.empty();

        Optional<Integer> servings =
                recipe.has("number_of_servings")
                        ? Optional.of(recipe.getInt("number_of_servings"))
                        : Optional.empty();

        JSONObject recipeImages = recipe.getJSONObject("recipe_images");
        JSONArray recipeImage = recipeImages.getJSONArray("recipe_image");
        String imageUrl = recipeImage.get(0).toString();

        return new Recipe(recipeId, name, description,
                Optional.of(instructions), recipeIngredients, prepTime,
                cookTime, servings, imageUrl);
    }

    /**
     * Parses the JSON response from the API call for searching for a recipe
     * by name or ingredient.
     * @param jsonResponse the JSON response from the API.
     * @return a list of RecipeSearchResult entities.
     */
    public static List<RecipeSearchResult> parseRecipeResults(
            final String jsonResponse) {
        List<RecipeSearchResult> results = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(jsonResponse);
            if (!root.has("recipes")) {
                return results;
            }

            JSONObject recipeObj = root.getJSONObject("recipes");
            JSONArray recipeArray = recipeObj.optJSONArray("recipe");
            if (recipeArray == null) {
                return results;
            }

            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject recipe = recipeArray.getJSONObject(i);

                String recipeId = recipe.optString("recipe_id", "");
                String recipeName = recipe.optString("recipe_name", "");
                String description = recipe.optString(
                        "recipe_description", "");

                JSONObject ingredients = recipe.getJSONObject(
                        "recipe_ingredients");
                JSONArray ingredientsArray = ingredients.getJSONArray(
                        "ingredient");
                List<String> ingredientNames = new ArrayList<>();

                for (int j = 0; j < ingredientsArray.length(); j++) {
                    ingredientNames.add(ingredientsArray.getString(j));
                }

                String imageUrl = recipe.optString("recipe_image", "");

                results.add(new RecipeSearchResult(recipeId, recipeName,
                        description, ingredientNames, imageUrl));
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to parse recipe results: "
                    + e.getMessage());
        }
        return results;
    }

    /**
     * Parses the JSON response from the API call to search for a recipe
     * by ID.
     * @param jsonResponse the JSON response from the API.
     * @return a RecipeDetails entity.
     */
    public static RecipeDetails parseRecipeDetails(final String jsonResponse) {
        RecipeDetails recipeDetails = null;
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject recipe = root.getJSONObject("recipe");

            if (!root.has("recipe")) {
                throw new JSONException("Missing 'recipe' object");
            }

            String name =  recipe.optString("recipe_name", "");

            JSONObject recipeImages = recipe.getJSONObject("recipe_images");
            JSONArray recipeImage = recipeImages.getJSONArray("recipe_image");
            String imageUrl = recipeImage.get(0).toString();

            // Nutrition
            JSONObject serving = recipe.getJSONObject("serving_sizes").
                    getJSONObject("serving");
            String servingSize = serving.optString("serving_size", "");
            double calories = serving.getDouble("calories");
            double protein = serving.getDouble("protein");
            double carbs = serving.getDouble("carbohydrate");
            double fats = serving.getDouble("fat");

            // Dietary Tags
            List<String> tags = new ArrayList<>();
            if (recipe.has("recipe_types")) {
                JSONObject recipeTypes = recipe.getJSONObject("recipe_types");
                JSONArray arr = recipeTypes.getJSONArray("recipe_type");
                for (int i = 0; i < arr.length(); i++) {
                    tags.add(arr.get(i).toString());
                }
            }

            // Ingredients
            List<String> ingredients = new ArrayList<>();
            if (recipe.has("ingredients")) {
                JSONObject recipeIngredients = recipe.getJSONObject(
                        "ingredients");
                JSONArray arr = recipeIngredients.getJSONArray("ingredient");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject ingredient = arr.getJSONObject(i);
                    String food = ingredient.get("food_name").toString();
                    ingredients.add(food);
                }
            }

            // Instructions
            List<String> instructions = new ArrayList<>();
            if (recipe.has("directions")) {
                JSONObject recipeDirections = recipe.getJSONObject(
                        "directions");
                JSONArray arr = recipeDirections.getJSONArray("direction");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject step = arr.getJSONObject(i);
                    instructions.add(step.get("direction_description").
                            toString());
                }
            }

            recipeDetails = new RecipeDetails(
                    name,
                    imageUrl,
                    calories,
                    protein,
                    carbs,
                    fats,
                    servingSize,
                    tags,
                    ingredients,
                    instructions);

        } catch (Exception e) {
            System.err.println("❌ Failed to parse recipe details: "
                    + e.getMessage());
        }
        return recipeDetails;
    }
}

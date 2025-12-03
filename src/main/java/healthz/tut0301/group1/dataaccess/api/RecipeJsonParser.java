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
     * @throws AssertionError since the class can't be instantiated.
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
        final JSONObject recipeObject = new JSONObject(jsonResponse);
        final JSONObject recipes = recipeObject.getJSONObject("recipes");
        final JSONArray recipeArray = recipes.getJSONArray("recipe");
        final List<Recipe> recipesList = new ArrayList<>();

        for (int i = 0; i < recipeArray.length(); i++) {
            final JSONObject recipe = recipeArray.getJSONObject(i);
            final JSONObject ingredients = recipe.getJSONObject(
                    "recipe_ingredients");
            final JSONArray ingredientsArray = ingredients.getJSONArray(
                    "ingredient");
            final List<String> ingredientNames = new ArrayList<>();

            for (int j = 0; j < ingredientsArray.length(); j++) {
                ingredientNames.add(ingredientsArray.getString(j));
            }

            final Recipe recipeEntity = new Recipe(
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
        final JSONObject recipeObject = new JSONObject(jsonResponse);
        final JSONObject recipe = recipeObject.getJSONObject("recipe");

        final String name = recipe.getString("recipe_name");
        final String description = recipe.getString("recipe_description");
        final List<String> instructions = new ArrayList<>();

        final JSONObject directions = recipe.getJSONObject("directions");
        final JSONArray direction = directions.getJSONArray("direction");

        for (int i = 0; i < direction.length(); i++) {
            final JSONObject step = direction.getJSONObject(i);
            instructions.add(step.getString("direction_description"));
        }

        final JSONObject ingredients = recipe.getJSONObject("ingredients");
        final JSONArray ingredientArray = ingredients.getJSONArray("ingredient");
        final List<RecipeIngredient> recipeIngredients = new ArrayList<>();

        for (int i = 0; i < ingredientArray.length(); i++) {
            final JSONObject ingredient = ingredientArray.getJSONObject(i);

            final RecipeIngredient recipeIngredient = new RecipeIngredient(
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

        final Optional<Integer> prepTime =
                recipe.has("preparation_time_min")
                        ? Optional.of(recipe.getInt("preparation_time_min"))
                        : Optional.empty();

        final Optional<Integer> cookTime =
                recipe.has("cooking_time_min")
                        ? Optional.of(recipe.getInt("cooking_time_min"))
                        : Optional.empty();

        final Optional<Integer> servings =
                recipe.has("number_of_servings")
                        ? Optional.of(recipe.getInt("number_of_servings"))
                        : Optional.empty();

        final JSONObject recipeImages = recipe.getJSONObject("recipe_images");
        final JSONArray recipeImage = recipeImages.getJSONArray("recipe_image");
        final String imageUrl = recipeImage.get(0).toString();

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
        final List<RecipeSearchResult> results = new ArrayList<>();

        try {
            final JSONObject root = new JSONObject(jsonResponse);
            if (!root.has("recipes")) {
                return results;
            }

            final JSONObject recipeObj = root.getJSONObject("recipes");
            final JSONArray recipeArray = recipeObj.optJSONArray("recipe");
            if (recipeArray == null) {
                return results;
            }

            for (int i = 0; i < recipeArray.length(); i++) {
                final JSONObject recipe = recipeArray.getJSONObject(i);

                final String recipeId = recipe.optString("recipe_id", "");
                final String recipeName = recipe.optString("recipe_name", "");
                final String description = recipe.optString(
                        "recipe_description", "");

                final JSONObject ingredients = recipe.getJSONObject(
                        "recipe_ingredients");
                final JSONArray ingredientsArray = ingredients.getJSONArray(
                        "ingredient");
                final List<String> ingredientNames = new ArrayList<>();

                for (int j = 0; j < ingredientsArray.length(); j++) {
                    ingredientNames.add(ingredientsArray.getString(j));
                }

                final String imageUrl = recipe.optString("recipe_image", "");

                results.add(new RecipeSearchResult(recipeId, recipeName,
                        description, ingredientNames, imageUrl));
            }
        }
        catch (Exception exception) {
            System.err.println("❌ Failed to parse recipe results: "
                    + exception.getMessage());
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
            final JSONObject root = new JSONObject(jsonResponse);
            final JSONObject recipe = root.getJSONObject("recipe");

            if (!root.has("recipe")) {
                throw new JSONException("Missing 'recipe' object");
            }

            final String name = recipe.optString("recipe_name", "");

            final JSONObject recipeImages = recipe.getJSONObject("recipe_images");
            final JSONArray recipeImage = recipeImages.getJSONArray("recipe_image");
            final String imageUrl = recipeImage.get(0).toString();

            // Nutrition
            final JSONObject serving = recipe.getJSONObject("serving_sizes")
                    .getJSONObject("serving");
            final String servingSize = serving.optString("serving_size", "");
            final double calories = serving.getDouble("calories");
            final double protein = serving.getDouble("protein");
            final double carbs = serving.getDouble("carbohydrate");
            final double fats = serving.getDouble("fat");

            // Dietary Tags
            final List<String> tags = new ArrayList<>();
            if (recipe.has("recipe_types")) {
                final JSONObject recipeTypes = recipe.getJSONObject("recipe_types");
                final JSONArray arr = recipeTypes.getJSONArray("recipe_type");
                for (int i = 0; i < arr.length(); i++) {
                    tags.add(arr.get(i).toString());
                }
            }

            // Ingredients
            final List<String> ingredients = new ArrayList<>();
            if (recipe.has("ingredients")) {
                final JSONObject recipeIngredients = recipe.getJSONObject(
                        "ingredients");
                final JSONArray arr = recipeIngredients.getJSONArray("ingredient");
                for (int i = 0; i < arr.length(); i++) {
                    final JSONObject ingredient = arr.getJSONObject(i);
                    final String food = ingredient.get("food_name").toString();
                    ingredients.add(food);
                }
            }

            // Instructions
            final List<String> instructions = new ArrayList<>();
            if (recipe.has("directions")) {
                final JSONObject recipeDirections = recipe.getJSONObject(
                        "directions");
                final JSONArray arr = recipeDirections.getJSONArray("direction");
                for (int i = 0; i < arr.length(); i++) {
                    final JSONObject step = arr.getJSONObject(i);
                    instructions.add(step.get("direction_description")
                            .toString());
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

        }
        catch (Exception exception) {
            System.err.println("❌ Failed to parse recipe details: "
                    + exception.getMessage());
        }
        return recipeDetails;
    }
}

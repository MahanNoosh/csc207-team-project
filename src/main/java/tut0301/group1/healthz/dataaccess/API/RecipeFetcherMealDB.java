package tut0301.group1.healthz.dataaccess.API;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class RecipeFetcherMealDB implements RecipeMealDB{
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Call the MealDB API to get the name of a recipe.
     *
     * @param recipe the recipe to search for
     * @return recipe_name the name of the recipe or null
     * @throws RecipeMealDB.RecipeNotFoundException if there is no recipe found
     */
    public String getRecipeName(String recipe) throws RecipeMealDB.RecipeNotFoundException {
        String url = "http://themealdb.com/api/json/v1/1/search.php?s=" + recipe;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new RecipeMealDB.RecipeNotFoundException();
            }

            String responseBody = response.body().string();
            JSONObject recipeObject = new JSONObject(responseBody);

            if (recipeObject.isNull("meals")) {
                throw new RecipeNotFoundException();
            }

            JSONArray meals = recipeObject.getJSONArray("meals");
            JSONObject meal = meals.getJSONObject(0);

            return meal.getString("strMeal");

        } catch (IOException e) {
            throw new RecipeMealDB.RecipeNotFoundException();
        }
    }

    public static void main(String[] args) {
        try {
            RecipeFetcherMealDB fetcher = new RecipeFetcherMealDB();
            String name = fetcher.getRecipeName("Arrabiata");
            System.out.println(name);
        } catch (RecipeMealDB.RecipeNotFoundException e) {
            System.out.println("Recipe not found!");
        }
    }
}

package tut0301.group1.healthz.recipeapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class RecipeFetcher implements Recipe {
    private final OkHttpClient client = new OkHttpClient();

    public String getRecipeName(String recipe) throws Recipe.RecipeNotFoundException{
        String url = "http://themealdb.com/api/json/v1/1/search.php?s=" + recipe;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new Recipe.RecipeNotFoundException(recipe);
            }

            String responseBody = response.body().string();
            JSONObject recipeObject = new JSONObject(responseBody);

            if (recipeObject.isNull("meals")) {
                throw new RecipeNotFoundException(recipe);
            }

            JSONArray meals = recipeObject.getJSONArray("meals");
            JSONObject meal = meals.getJSONObject(0);

            return meal.getString("strMeal");

        } catch (IOException e) {
            throw new Recipe.RecipeNotFoundException(recipe);
        }
    }
}

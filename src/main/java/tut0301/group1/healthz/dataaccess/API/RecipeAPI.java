package tut0301.group1.healthz.dataaccess.API;
import tut0301.group1.healthz.entities.nutrition.Recipe;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Fetch recipe data from the FatSecret API.
 */
public class RecipeAPI implements SearchRecipe {
    private final OkHttpClient client = new OkHttpClient();
    String url = "https://platform.fatsecret.com/rest/recipes/search/v3";
    String searchByIdUrl = "https://platform.fatsecret.com/rest/recipe/v2";
    private final String token;  // OAuth token

    public RecipeAPI(String token) {
        this.token = token;
    }

    /**
     * Call the FatSecret API using an existing token to search for recipe name(s).
     *
     * @param searchExpression the search term
     * @param maxResults       the maximum number of results to display
     * @param pageNumber       the page of results to show
     * @param recipeType       the type of recipe to search for
     * @return a list of the names of the recipes
     * @throws RecipeNotFoundException if there are no recipes found
     */
    @Override
    public List<String> getRecipeNames(String searchExpression, Integer maxResults,
                                       Integer pageNumber, String recipeType)
            throws SearchRecipe.RecipeNotFoundException {
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder()
                .addQueryParameter("search_expression", searchExpression)
                .addQueryParameter("max_results", String.valueOf(maxResults))
                .addQueryParameter("page_number", String.valueOf(pageNumber))
                .addQueryParameter("recipe_types", recipeType)
                .addQueryParameter("format", "json")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new SearchRecipe.RecipeNotFoundException();
            }

            String responseBody = response.body().string();
            JSONObject recipeObject = new JSONObject(responseBody);
            JSONArray recipes = recipeObject.getJSONObject("recipes").getJSONArray("recipe");

            if (recipes.isEmpty()) {
                throw new SearchRecipe.RecipeNotFoundException();
            }

            List<String> recipeNames = new ArrayList<>();

            for (int i = 0; i < recipes.length(); i++) {
                recipeNames.add(recipes.getJSONObject(i).getString("recipe_name"));
            }

            return recipeNames;

        } catch (IOException e) {
            throw new SearchRecipe.RecipeNotFoundException();
        }
    }

    /**
     * Call the FatSecret API using an existing token and id to search for recipe(s) by name.
     *
     * @param searchExpression the search term
     * @param maxResults       the maximum number of results to display
     * @param pageNumber       the page of results to show
     * @param recipeType       the type of recipe to search for
     * @return the list of Recipe objects that is found
     * @throws RecipeNotFoundException if there are no recipes found
     */
    @Override
    public List<Recipe> searchByName(String searchExpression, Integer maxResults,
                                     Integer pageNumber, String recipeType)
            throws SearchRecipe.RecipeNotFoundException {
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder()
                .addQueryParameter("search_expression", searchExpression)
                .addQueryParameter("max_results", String.valueOf(maxResults))
                .addQueryParameter("page_number", String.valueOf(pageNumber))
                .addQueryParameter("recipe_types", recipeType)
                .addQueryParameter("format", "json")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new SearchRecipe.RecipeNotFoundException();
            }

            String responseBody = response.body().string();
            JSONObject recipeObject = new JSONObject(responseBody);
            JSONArray recipes = recipeObject.getJSONObject("recipes").getJSONArray("recipe");

            if (recipes.isEmpty()) {
                throw new SearchRecipe.RecipeNotFoundException();
            }

            return RecipeJsonParser.getRecipesByName(responseBody);

        } catch (IOException e) {
            throw new SearchRecipe.RecipeNotFoundException();
        }
    }


    /**
     * Call the FatSecret API using an existing token and id to search for recipe(s) by ID.
     *
     * @param id the id of the recipe to search for
     * @return the recipe that is found
     * @throws RecipeNotFoundException if there are no recipes found
     */
    @Override
    public Recipe searchById(String id) throws SearchRecipe.RecipeNotFoundException {
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse(searchByIdUrl)).newBuilder()
                .addQueryParameter("recipe_id", id)
                .addQueryParameter("format", "json")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new SearchRecipe.RecipeNotFoundException();
            }

            String responseBody = response.body().string();

            return RecipeJsonParser.getRecipeById(responseBody, id);

        } catch (IOException e) {
            throw new SearchRecipe.RecipeNotFoundException();
        }
    }
}

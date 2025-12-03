package healthz.tut0301.group1.dataaccess.api;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import healthz.tut0301.group1.entities.nutrition.Recipe;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Fetch recipe data from the FatSecret API.
 */
public class RecipeApi implements SearchRecipe {
    /**
     * The OkHTTP client for making a FatSecret API call.
     */
    private final OkHttpClient client = new OkHttpClient();

     /**
     * The URL to search for a recipe by a search term.
     */
     private static final String URL =
             "https://platform.fatsecret.com/rest/recipes/search/v3";

    /**
     * The URL to search for a recipe by ID.
     */
    private final String searchByIdUrl =
            "https://platform.fatsecret.com/rest/recipe/v2";

    /**
     * The OAuth token for the API call.
     */
    private final String token;

    /**
     * The constructor for the RecipeApi class.
     * @param ptoken the OAuth token for the API call.
     */
    public RecipeApi(final String ptoken) {
        this.token = ptoken;
    }

    /**
     * Call the FatSecret API using an existing token to search for
     * recipe(s) by name.
     *
     * @param searchExpression the search term.
     * @param maxResults the maximum number of results to display.
     * @param pageNumber the number of pages to show.
     * @param recipeType the type of recipe to search for.
     * @return a list of Recipe entities that is found.
     * @throws RecipeNotFoundException if there are no recipes found.
     */
    @Override
    public List<Recipe> searchByName(final String searchExpression,
                                     final Integer maxResults,
                                     final Integer pageNumber,
                                     final String recipeType)
            throws SearchRecipe.RecipeNotFoundException {
        final HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse(URL))
                .newBuilder()
                .addQueryParameter("search_expression", searchExpression)
                .addQueryParameter("max_results", String.valueOf(maxResults))
                .addQueryParameter("page_number", String.valueOf(pageNumber))
                .addQueryParameter("recipe_types", recipeType)
                .addQueryParameter("format", "json")
                .build();

        final Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new SearchRecipe.RecipeNotFoundException();
            }

            final String responseBody = response.body().string();
            final JSONObject recipeObject = new JSONObject(responseBody);
            final JSONArray recipes = recipeObject.getJSONObject("recipes")
                .getJSONArray("recipe");

            if (recipes.isEmpty()) {
                throw new SearchRecipe.RecipeNotFoundException();
            }

            return RecipeJsonParser.getRecipesByName(responseBody);

        }
        catch (IOException exception) {
            throw new SearchRecipe.RecipeNotFoundException();
        }
    }

    /**
     * Call the FatSecret API using an existing token to search for a
     * recipe by ID.
     *
     * @param id the id of the recipe to search for.
     * @return the Recipe entity that is found.
     * @throws RecipeNotFoundException if there are no recipes found.
     */
    @Override
    public Recipe searchById(final String id) throws
            SearchRecipe.RecipeNotFoundException {
        final HttpUrl httpUrl = Objects.requireNonNull(
                HttpUrl.parse(searchByIdUrl)).newBuilder()
                .addQueryParameter("recipe_id", id)
                .addQueryParameter("format", "json")
                .build();

        final Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new SearchRecipe.RecipeNotFoundException();
            }

            final String responseBody = response.body().string();

            return RecipeJsonParser.getRecipeById(responseBody, id);

        }
        catch (IOException exception) {
            throw new SearchRecipe.RecipeNotFoundException();
        }
    }
}

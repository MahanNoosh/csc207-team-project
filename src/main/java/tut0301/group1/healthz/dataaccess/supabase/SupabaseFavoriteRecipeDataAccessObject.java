package tut0301.group1.healthz.dataaccess.supabase;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import tut0301.group1.healthz.dataaccess.API.RecipeAPI;
import tut0301.group1.healthz.dataaccess.API.SearchRecipe;
import tut0301.group1.healthz.entities.nutrition.Recipe;
import tut0301.group1.healthz.usecase.favoriterecipe.FavoriteRecipeGateway;

/**
 * Supabase implementation of the FavoriteRecipeGateway.
 * Manages favorite recipes in the Supabase database.
 */
public class SupabaseFavoriteRecipeDataAccessObject implements FavoriteRecipeGateway {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int HTTP_ERROR_THRESHOLD = 400;

    private final SupabaseClient client;
    private final RecipeAPI recipeApi;

    /**
     * Constructs a SupabaseFavoriteRecipeDataAccessObject.
     *
     * @param client the Supabase client for database access
     * @param oauthToken the OAuth token for API authentication
     */
    public SupabaseFavoriteRecipeDataAccessObject(SupabaseClient client, String oauthToken) {
        this.client = client;
        this.recipeApi = new RecipeAPI(oauthToken);
    }

    @Override
    public List<Recipe> getFavoriteRecipes(String userId) throws Exception {
        System.out.println("Fetching favorites for user: " + userId);

        final String endpoint = "favorite_recipes?select=" + FavoriteRecipeFields.projection()
                + "&user_id=eq." + URLEncoder.encode(userId, StandardCharsets.UTF_8)
                + "&order=created_at.desc";

        final HttpRequest req = client.rest(endpoint)
                .header(AUTHORIZATION, BEARER_PREFIX + client.getAccessToken())
                .GET()
                .build();

        final HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= HTTP_ERROR_THRESHOLD) {
            throw new RuntimeException("Fetch favorites failed: " + res.body());
        }

        final JSONArray arr = new JSONArray(res.body());
        final List<Recipe> favorites = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            final JSONObject row = arr.getJSONObject(i);
            favorites.add(jsonToRecipe(row));
        }

        System.out.println("Found " + favorites.size() + " favorites");
        return favorites;
    }

    @Override
    public void addFavorite(String userId, String recipeId) throws Exception {
        System.out.println("Adding favorite - Recipe: " + recipeId);

        if (isFavorite(userId, recipeId)) {
            System.out.println("Recipe already in favorites");
        }
        else {
            this.performAddFavorite(userId, recipeId);
        }
    }

    private void performAddFavorite(String userId, String recipeId) throws Exception {
        String recipeName = "Recipe " + recipeId;
        String recipeDescription = "";
        String imageUrl = null;

        try {
            System.out.println("Fetching recipe details from API...");
            final Recipe recipe = recipeApi.searchById(recipeId);

            recipeName = recipe.getName();
            recipeDescription = recipe.getDescription();
            imageUrl = recipe.getImageUrl();

            System.out.println("Fetched recipe: " + recipeName);
        }
        catch (SearchRecipe.RecipeNotFoundException ex) {
            System.err.println("Could not fetch recipe details, using defaults");
        }

        final JSONObject body = new JSONObject();
        body.put(FavoriteRecipeFields.USER_ID, userId);
        body.put(FavoriteRecipeFields.RECIPE_ID, recipeId);
        body.put(FavoriteRecipeFields.RECIPE_NAME, recipeName);
        body.put(FavoriteRecipeFields.RECIPE_DESCRIPTION, recipeDescription);
        final Object imageValue;
        if (imageUrl != null) {
            imageValue = imageUrl;
        }
        else {
            imageValue = JSONObject.NULL;
        }
        body.put(FavoriteRecipeFields.IMAGE_URL, imageValue);
        body.put(FavoriteRecipeFields.IMAGE_URL, imageValue);

        final String endpoint = "favorite_recipes";
        final HttpRequest req = client.rest(endpoint)
                .header(AUTHORIZATION, BEARER_PREFIX + client.getAccessToken())
                .header("Content-Type", "application/json")
                .header("Prefer", "return=minimal")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        final HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= HTTP_ERROR_THRESHOLD) {
            throw new RuntimeException("Add favorite failed: " + res.body());
        }

        System.out.println("Favorite added successfully with details");
    }

    @Override
    public void removeFavorite(String userId, String recipeId) throws Exception {
        System.out.println("Removing favorite - Recipe: " + recipeId);

        final String endpoint = "favorite_recipes?user_id=eq."
                + URLEncoder.encode(userId, StandardCharsets.UTF_8)
                + "&recipe_id=eq." + URLEncoder.encode(recipeId, StandardCharsets.UTF_8);

        final HttpRequest req = client.rest(endpoint)
                .header(AUTHORIZATION, BEARER_PREFIX + client.getAccessToken())
                .header("Prefer", "return=minimal")
                .DELETE()
                .build();

        final HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= HTTP_ERROR_THRESHOLD) {
            throw new RuntimeException("Remove favorite failed: " + res.body());
        }

        System.out.println("Favorite removed successfully");
    }

    @Override
    public boolean isFavorite(String userId, String recipeId) throws Exception {
        final String endpoint = "favorite_recipes?select=id&user_id=eq."
                + URLEncoder.encode(userId, StandardCharsets.UTF_8)
                + "&recipe_id=eq." + URLEncoder.encode(recipeId, StandardCharsets.UTF_8)
                + "&limit=1";

        final HttpRequest req = client.rest(endpoint)
                .header(AUTHORIZATION, BEARER_PREFIX + client.getAccessToken())
                .GET()
                .build();

        final HttpResponse<String> res = client.send(req);

        final boolean result;
        if (res.statusCode() >= HTTP_ERROR_THRESHOLD) {
            result = false;
        }
        else {
            final JSONArray arr = new JSONArray(res.body());
            result = arr.length() > 0;
        }

        return result;
    }

    private Recipe jsonToRecipe(JSONObject row) {
        final String recipeId = row.getString(FavoriteRecipeFields.RECIPE_ID);
        final String name = row.getString(FavoriteRecipeFields.RECIPE_NAME);
        final String description = row.optString(FavoriteRecipeFields.RECIPE_DESCRIPTION, "");
        final String imageUrl;
        if (row.isNull(FavoriteRecipeFields.IMAGE_URL)) {
            imageUrl = null;
        }
        else {
            imageUrl = row.getString(FavoriteRecipeFields.IMAGE_URL);
        }

        return new Recipe(
                recipeId,
                name,
                description,
                new ArrayList<>(),
                new ArrayList<>(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                imageUrl
        );
    }
}

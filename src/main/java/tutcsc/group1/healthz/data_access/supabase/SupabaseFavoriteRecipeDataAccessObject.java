package tutcsc.group1.healthz.data_access.supabase;

import org.json.JSONArray;
import org.json.JSONObject;
import tutcsc.group1.healthz.data_access.api.RecipeAPI;
import tutcsc.group1.healthz.data_access.api.SearchRecipe;
import tutcsc.group1.healthz.entities.nutrition.Recipe;
import tutcsc.group1.healthz.use_case.favorite_recipe.FavoriteRecipeGateway;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupabaseFavoriteRecipeDataAccessObject implements FavoriteRecipeGateway {

    private final SupabaseClient client;
    private final RecipeAPI recipeAPI;

    public SupabaseFavoriteRecipeDataAccessObject(SupabaseClient client, String oauthToken) {
        this.client = client;
        this.recipeAPI = new RecipeAPI(oauthToken);
    }

    @Override
    public List<Recipe> getFavoriteRecipes(String userId) throws Exception {
        System.out.println("Fetching favorites for user: " + userId);

        String endpoint = "favorite_recipes?select=" + FavoriteRecipeFields.projection() +
                "&user_id=eq." + URLEncoder.encode(userId, StandardCharsets.UTF_8) +
                "&order=created_at.desc";

        HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .GET()
                .build();

        HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= 400) {
            throw new RuntimeException("Fetch favorites failed: " + res.body());
        }

        JSONArray arr = new JSONArray(res.body());
        List<Recipe> favorites = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject row = arr.getJSONObject(i);
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
            return;
        }

        // Fetch recipe details from FatSecret API
        Recipe recipe = null;
        String recipeName = "Recipe " + recipeId;  // Default fallback
        String recipeDescription = "";
        String imageUrl = null;

        try {
            System.out.println("Fetching recipe details from API...");
            recipe = recipeAPI.searchById(recipeId);

            recipeName = recipe.getName();
            recipeDescription = recipe.getDescription();
            imageUrl = recipe.getImageUrl();

            System.out.println("Fetched recipe: " + recipeName);
        } catch (SearchRecipe.RecipeNotFoundException e) {
            System.err.println("Could not fetch recipe details, using defaults");
        }

        // Store in Supabase with actual details
        JSONObject body = new JSONObject();
        body.put(FavoriteRecipeFields.USER_ID, userId);
        body.put(FavoriteRecipeFields.RECIPE_ID, recipeId);
        body.put(FavoriteRecipeFields.RECIPE_NAME, recipeName);
        body.put(FavoriteRecipeFields.RECIPE_DESCRIPTION, recipeDescription);
        body.put(FavoriteRecipeFields.IMAGE_URL, imageUrl != null ? imageUrl : JSONObject.NULL);

        String endpoint = "favorite_recipes";
        HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .header("Content-Type", "application/json")
                .header("Prefer", "return=minimal")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= 400) {
            throw new RuntimeException("Add favorite failed: " + res.body());
        }

        System.out.println("Favorite added successfully with details");
    }

    @Override
    public void removeFavorite(String userId, String recipeId) throws Exception {
        System.out.println("🗑️ Removing favorite - Recipe: " + recipeId);

        String endpoint = "favorite_recipes?user_id=eq." + URLEncoder.encode(userId, StandardCharsets.UTF_8) +
                "&recipe_id=eq." + URLEncoder.encode(recipeId, StandardCharsets.UTF_8);

        HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .header("Prefer", "return=minimal")
                .DELETE()
                .build();

        HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= 400) {
            throw new RuntimeException("Remove favorite failed: " + res.body());
        }

        System.out.println("Favorite removed successfully");
    }

    @Override
    public boolean isFavorite(String userId, String recipeId) throws Exception {
        String endpoint = "favorite_recipes?select=id&user_id=eq." +
                URLEncoder.encode(userId, StandardCharsets.UTF_8) +
                "&recipe_id=eq." + URLEncoder.encode(recipeId, StandardCharsets.UTF_8) +
                "&limit=1";

        HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .GET()
                .build();

        HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= 400) {
            return false;
        }

        JSONArray arr = new JSONArray(res.body());
        return arr.length() > 0;
    }

    private Recipe jsonToRecipe(JSONObject row) {
        String recipeId = row.getString(FavoriteRecipeFields.RECIPE_ID);
        String name = row.getString(FavoriteRecipeFields.RECIPE_NAME);
        String description = row.optString(FavoriteRecipeFields.RECIPE_DESCRIPTION, "");
        String imageUrl = row.isNull(FavoriteRecipeFields.IMAGE_URL)
                ? null
                : row.getString(FavoriteRecipeFields.IMAGE_URL);

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
package tut0301.group1.healthz.dataaccess.API;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tut0301.group1.healthz.dataaccess.API.OAuth.OAuth;
import tut0301.group1.healthz.dataaccess.API.OAuth.OAuthDataAccessObject;
import tut0301.group1.healthz.dataaccess.config.EnvConfig;
import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;
import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchGateway;
import tut0301.group1.healthz.entities.nutrition.RecipeFilter;

public class FatSecretRecipeSearchDataAccessObject implements RecipeSearchGateway {
    private final OkHttpClient client = new OkHttpClient();
    private static final String SEARCH_URL = "https://platform.fatsecret.com/rest/recipes/search/v3";
    private static final String ID_URL = "https://platform.fatsecret.com/rest/recipe/v2";

    @Override
    public List<RecipeSearchResult> search(String query, RecipeFilter filter) throws Exception {
        String token = fetchToken();

        HttpUrl.Builder url = Objects.requireNonNull(HttpUrl.parse(SEARCH_URL)).newBuilder()
                // Only add the required parameters
                .addQueryParameter("search_expression", query)
                .addQueryParameter("format", "json");

        // Helper function to add only non-null parameters
        BiConsumer<String, Long> add = (k, v) -> {
            if (v != null) url.addQueryParameter(k, v.toString());
        };

        add.accept("calories.from", filter.caloriesFrom);
        add.accept("calories.to", filter.caloriesTo);
        add.accept("carb_percentage.from", filter.carbsFrom);
        add.accept("carb_percentage.to", filter.carbsTo);
        add.accept("protein_percentage.from", filter.proteinFrom);
        add.accept("protein_percentage.to", filter.proteinTo);
        add.accept("fat_percentage.from", filter.fatFrom);
        add.accept("fat_percentage.to", filter.fatTo);

        System.out.println("Full URL: " + SEARCH_URL);

        Request request = new Request.Builder()
                .url(url.build())
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("FatSecret API error: " + response.code());
            }

            String responseJson = response.body().string();
            return RecipeJsonParser.parseRecipeResults(responseJson);
        }
    }

    public String getRecipebyId(String accessToken, long recipeId) throws Exception {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(ID_URL))
                .newBuilder()
                .addQueryParameter("recipe_id", String.valueOf(recipeId))
                .addQueryParameter("format", "json")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("FatSecret API error (recipe detail): " + response.code());
            }
            return response.body().string();
        }
    }

    private String fetchToken() throws IOException, InterruptedException {
        String clientId = EnvConfig.getClientId();
        String clientSecret = EnvConfig.getClientSecret();

        OAuth fetcher = new OAuth(clientId, clientSecret);
        String raw = fetcher.getAccessTokenRaw("basic");
        String token = OAuthDataAccessObject.extractAccessToken(raw);


        if (token == null) {
            throw new IOException("Unable to retrieve FatSecret access token");
        }

        return token;
    }
}


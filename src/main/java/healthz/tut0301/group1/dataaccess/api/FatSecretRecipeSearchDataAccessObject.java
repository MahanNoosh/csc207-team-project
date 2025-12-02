package healthz.tut0301.group1.dataaccess.api;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import healthz.tut0301.group1.dataaccess.api.OAuth.OAuth;
import healthz.tut0301.group1.dataaccess.api.OAuth.OAuthDataAccessObject;
import healthz.tut0301.group1.dataaccess.config.EnvConfig;
import healthz.tut0301.group1.entities.nutrition.RecipeSearchResult;
import healthz.tut0301.group1.usecase.recipesearch.metadata.RecipeSearchDataAccessInterface;
import healthz.tut0301.group1.entities.nutrition.RecipeFilter;

public final class FatSecretRecipeSearchDataAccessObject implements
        RecipeSearchDataAccessInterface {
    /**
     * OkHTTP client to call the FatSecret API.
     */
    private final OkHttpClient client = new OkHttpClient();

    /**
     * URL for searching for a recipe by name or ingredient.
     */
    private static final String SEARCH_URL =
            "https://platform.fatsecret.com/rest/recipes/search/v3";

    /**
     * URL for searching for a recipe by ID.
     */
    private static final String ID_URL =
            "https://platform.fatsecret.com/rest/recipe/v2";

    @Override
    public List<RecipeSearchResult> search(final String query,
                                           final RecipeFilter filter)
            throws Exception {
        String token = fetchToken();

        HttpUrl.Builder url = Objects.requireNonNull(
                HttpUrl.parse(SEARCH_URL)).newBuilder()
                // Only add the required parameters
                .addQueryParameter("search_expression", query)
                .addQueryParameter("format", "json");

        // Helper function to add only non-null parameters
        BiConsumer<String, Long> add = (k, v) -> {
            if (v != null) {
                url.addQueryParameter(k, v.toString());
            }
        };

        add.accept("calories.from", filter.getCaloriesFrom());
        add.accept("calories.to", filter.getCaloriesTo());
        add.accept("carb_percentage.from", filter.getCarbsFrom());
        add.accept("carb_percentage.to", filter.getCarbsTo());
        add.accept("protein_percentage.from", filter.getProteinFrom());
        add.accept("protein_percentage.to", filter.getProteinTo());
        add.accept("fat_percentage.from", filter.getFatFrom());
        add.accept("fat_percentage.to", filter.getFatTo());

        System.out.println("Full URL: " + SEARCH_URL);

        Request request = new Request.Builder()
                .url(url.build())
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("FatSecret api error: "
                        + response.code());
            }

            String responseJson = response.body().string();
            return RecipeJsonParser.parseRecipeResults(responseJson);
        }
    }

    /**
     * Method to search for a recipe by its ID.
     * @param accessToken the access token for the FatSecret API.
     * @param recipeId the ID of the recipe to search for.
     * @return the JSON response of the API call.
     * @throws Exception if there is an error with calling the API.
     */
    public String getRecipebyId(final String accessToken, final long recipeId)
            throws Exception {
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
                throw new IOException("FatSecret api error: "
                        + response.code());
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


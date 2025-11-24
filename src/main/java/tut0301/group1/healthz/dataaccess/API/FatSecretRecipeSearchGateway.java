package tut0301.group1.healthz.dataaccess.API;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;
import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchGateway;

public class FatSecretRecipeSearchGateway implements RecipeSearchGateway {
    private final OkHttpClient client = new OkHttpClient();
    private static final String URL = "https://platform.fatsecret.com/rest/recipes/search/v3";
    private static final String ID_URL = "https://platform.fatsecret.com/rest/recipe/v2";

    @Override
    public List<RecipeSearchResult> search(String query) throws Exception {
        String token = fetchToken();

        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(URL)).newBuilder()
                .addQueryParameter("search_expression", query)
                .addQueryParameter("max_results", "10")
                .addQueryParameter("page_number", "0")
                .addQueryParameter("format", "json")
                .build();

        System.out.println("Full URL: " + URL);

        Request request = new Request.Builder()
                .url(url)
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

        FatSecretOAuthTokenFetcher fetcher = new FatSecretOAuthTokenFetcher(clientId, clientSecret);
        String raw = fetcher.getAccessTokenRaw("basic");
        String token = FatSecretOAuthTokenFetcher.TokenParser.extractAccessToken(raw);

        if (token == null) {
            throw new IOException("Unable to retrieve FatSecret access token");
        }

        return token;
    }
}


package tut0301.group1.healthz.dataaccess.API;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Uses FatSecret API to search foods by name.
 */
public class FatSecretFoodSearchGateway {

    private static final String SEARCH_URL = "https://platform.fatsecret.com/rest/server.api";
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Searches food information from FatSecret API.
     *
     * @param token     OAuth access token (Bearer)
     * @param foodName  The name of the food to search for
     * @return JSON response from API
     * @throws IOException if network or API error occurs
     */
    public String searchFoodByName(String token, String foodName) throws IOException {
        // ✅ Build query URL
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SEARCH_URL).newBuilder()
                .addQueryParameter("method", "foods.search")
                .addQueryParameter("search_expression", foodName)
                .addQueryParameter("format", "json")
                .addQueryParameter("max_results", "5");

        // ✅ Create HTTP GET request
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        // ✅ Send request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response.code() + " - " + response.message());
            }

            String jsonBody = response.body() != null ? response.body().string() : "{}";
            System.out.println("=== Raw JSON Response ===");
            System.out.println(jsonBody);
            return jsonBody;
        }
    }
}

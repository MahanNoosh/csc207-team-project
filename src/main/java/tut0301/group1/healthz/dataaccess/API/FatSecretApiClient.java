package tut0301.group1.healthz.dataaccess.API;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Unified HTTP client for FatSecret API.
 * Handles low-level HTTP communication for both search and detail endpoints.
 * Data Access layer - returns raw JSON responses.
 */
public class FatSecretApiClient {

    private static final String SEARCH_URL = "https://platform.fatsecret.com/rest/server.api";
    private static final String FOOD_GET_URL = "https://platform.fatsecret.com/rest/food/v5";

    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final HttpClient javaHttpClient = HttpClient.newHttpClient();

    /**
     * Searches food information from FatSecret API.
     *
     * @param token     OAuth access token (Bearer)
     * @param foodName  The name of the food to search for
     * @return JSON response as String
     * @throws IOException if network or API error occurs
     */
    public String searchFoodByName(String token, String foodName) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SEARCH_URL).newBuilder()
                .addQueryParameter("method", "foods.search")
                .addQueryParameter("search_expression", foodName)
                .addQueryParameter("format", "json")
                .addQueryParameter("max_results", "20");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response.code() + " - " + response.message());
            }

            return response.body() != null ? response.body().string() : "{}";
        }
    }

    /**
     * Gets detailed food information from FatSecret API.
     *
     * @param token  OAuth access token (Bearer)
     * @param foodId The FatSecret food ID
     * @return JSON response as String
     * @throws IOException if network or API error occurs
     * @throws InterruptedException if request is interrupted
     */
    public String getFoodDetails(String token, long foodId) throws IOException, InterruptedException {
        String url = FOOD_GET_URL + "?food_id=" + foodId + "&format=json";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = javaHttpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("food.get.v5 failed: HTTP " + response.statusCode() + " - " + response.body());
        }

        return response.body();
    }

}

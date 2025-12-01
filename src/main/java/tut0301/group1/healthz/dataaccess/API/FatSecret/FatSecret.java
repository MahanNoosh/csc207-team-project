package tut0301.group1.healthz.dataaccess.API.FatSecret;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import tut0301.group1.healthz.dataaccess.API.FoodJsonParser;
import tut0301.group1.healthz.dataaccess.config.EnvConfig;
import tut0301.group1.healthz.entities.nutrition.FoodDetails;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Base64;

import java.util.List;

import static tut0301.group1.healthz.dataaccess.API.FoodJsonParser.parseFoodDetails;

/**
 * Calls food.get.v5 to retrieve detailed nutrition info for a single food.
 * <p>
 * - Loads FATSECRET_CLIENT_ID and FATSECRET_CLIENT_SECRET from a .env file
 * - Fetches an OAuth2 access token (scope=basic)
 * - Calls GET https://platform.fatsecret.com/rest/food/v5?food_id=...&format=json
 */
public class FatSecret {

    private static final String TOKEN_URL = "https://oauth.fatsecret.com/connect/token";
    private static final String FOOD_GET_URL = "https://platform.fatsecret.com/rest/food/v5";
    private static final String SEARCH_URL = "https://platform.fatsecret.com/rest/server.api";
    private final OkHttpClient client = new OkHttpClient();

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String clientId;
    private final String clientSecret;

    public FatSecret() {
        this.clientId = EnvConfig.getClientId();
        this.clientSecret = EnvConfig.getClientSecret();


        if (clientId == null || clientSecret == null ||
                clientId.isBlank() || clientSecret.isBlank()) {
            throw new IllegalStateException(
                    "❌ Missing FATSECRET_CLIENT_ID or FATSECRET_CLIENT_SECRET in .env file.\n" +
                            "Please create a .env file in the project root with:\n" +
                            "FATSECRET_CLIENT_ID=your_client_id\n" +
                            "FATSECRET_CLIENT_SECRET=your_client_secret"
            );
        }
    }

    /**
     * Fetch an access token (scope=basic).
     * If your account requires a different scope (e.g. premier), change it here.
     */
    private String fetchAccessToken() throws IOException, InterruptedException {
        String credentials = clientId + ":" + clientSecret;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + encoded;

        String body = "grant_type=client_credentials&scope=basic";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_URL))
                .header("Authorization", authHeader)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to get access token. HTTP "
                    + response.statusCode() + ": " + response.body());
        }

        JSONObject json = new JSONObject(response.body());
        return json.getString("access_token");
    }

    /**
     * Get detailed nutrition info for a given food_id using food.get.v5.
     *
     * @param foodId the FatSecret food_id (from your search results)
     */
    public FoodDetails getFoodDetails(long foodId) throws IOException, InterruptedException {
        String accessToken = fetchAccessToken();

        String url = FOOD_GET_URL + "?food_id=" + foodId + "&format=json";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("HTTP Status: " + response.statusCode());
        if (response.statusCode() != 200) {
            System.out.println("Response Body: " + response.body());
            throw new IOException("food.get.v5 failed: HTTP " + response.statusCode());
        }

        System.out.println("Raw JSON Response: " + response.body());

        return parseFoodDetails(new JSONObject(response.body()));
    }

    public String searchFoodByName(String token, String foodName) throws IOException {
        // ✅ Build query URL
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SEARCH_URL).newBuilder()
                .addQueryParameter("method", "foods.search")
                .addQueryParameter("search_expression", foodName)
                .addQueryParameter("format", "json")
                .addQueryParameter("max_results", "20");

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

            return jsonBody;
        }
    }

}
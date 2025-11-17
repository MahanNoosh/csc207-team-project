package tut0301.group1.healthz.dataaccess.API;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import static tut0301.group1.healthz.dataaccess.API.FoodJsonParser.parseFoodsList;

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
     * @return JSON response as String
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

            return jsonBody;
        }
    }

    public List<String> searchFoodByNameToList(String token, String foodName) throws IOException {
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



            FoodJsonParser  foodJsonParser = new FoodJsonParser();

            return foodJsonParser.parseFoodsList(jsonBody);
        }
    }

    public JSONObject searchFoodByNameFormated(String token, String foodName) throws IOException {
        // ✅ Build query URL
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SEARCH_URL).newBuilder()
                .addQueryParameter("method", "foods.search")
                .addQueryParameter("search_expression", foodName)
                .addQueryParameter("format", "json")
                .addQueryParameter("max_results", "5");


        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();


        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response.code() + " - " + response.message());
            }

            String jsonBody = response.body() != null ? response.body().string() : "{}";

            System.out.println("=== Raw JSON Response (formatted) ===");
            try {
                JSONObject formatted = new JSONObject(jsonBody);
                System.out.println(formatted.toString(4)); // pretty print
            } catch (Exception e) {
                System.out.println(jsonBody); // fallback if not valid JSON
            }

            JSONObject formatted = new JSONObject(jsonBody);

            return formatted;
        }
    }

    /**
     * Retrieves a single food's detailed nutrient profile by id.
     */
    public String getFoodDetailsById(String token, long foodId) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SEARCH_URL).newBuilder()
                .addQueryParameter("method", "food.get.v3")
                .addQueryParameter("food_id", String.valueOf(foodId))
                .addQueryParameter("format", "json");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response.code() + " - " + response.message());
            }

            return response.body() != null ? response.body().string() : "{}";
        }
    }
}

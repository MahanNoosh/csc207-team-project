package tut0301.group1.healthz.dataaccess.API;

import org.json.JSONArray;
import org.json.JSONObject;
import tut0301.group1.healthz.dataaccess.config.EnvConfig;

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

/**
 * Calls food.get.v5 to retrieve detailed nutrition info for a single food.
 *
 * - Loads FATSECRET_CLIENT_ID and FATSECRET_CLIENT_SECRET from a .env file
 * - Fetches an OAuth2 access token (scope=basic)
 * - Calls GET https://platform.fatsecret.com/rest/food/v5?food_id=...&format=json
 */
public class FatSecretFoodGetClient {

    private static final String TOKEN_URL = "https://oauth.fatsecret.com/connect/token";
    private static final String FOOD_GET_URL = "https://platform.fatsecret.com/rest/food/v5";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String clientId;
    private final String clientSecret;

    public FatSecretFoodGetClient() {
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
     * Simple .env parser (key=value, # comments).
     */
    private Map<String, String> loadDotEnv(String fileName) {
        Map<String, String> env = new HashMap<>();
        Path path = Path.of(fileName);

        if (!Files.exists(path)) {
            System.err.println("⚠️ No .env file found at " + path.toAbsolutePath());
            return env;
        }

        try {
            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                int eq = line.indexOf('=');
                if (eq <= 0) continue;

                String key = line.substring(0, eq).trim();
                String value = line.substring(eq + 1).trim();

                if (value.startsWith("\"") && value.endsWith("\"") && value.length() >= 2) {
                    value = value.substring(1, value.length() - 1);
                }

                env.put(key, value);
            }
        } catch (IOException e) {
            System.err.println("⚠️ Failed to read .env file: " + e.getMessage());
        }

        return env;
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

        return parseFoodDetails(response.body());
    }

    /**
     * Parse the food.get.v5 JSON response into a FoodDetails object.
     */
    private FoodDetails parseFoodDetails(String json) {
        JSONObject root = new JSONObject(json);

        // Usually root has "food": { ... }
        JSONObject foodObj = root.has("food") ? root.getJSONObject("food") : root;

        long foodId = foodObj.optLong("food_id", -1);
        String name = foodObj.optString("food_name", "");
        String type = foodObj.optString("food_type", "");
        String brand = foodObj.optString("brand_name", null);
        String url = foodObj.optString("food_url", null);

        List<ServingInfo> servings = new ArrayList<>();

        JSONObject servingsWrapper = foodObj.optJSONObject("servings");
        if (servingsWrapper != null && servingsWrapper.has("serving")) {
            Object servingNode = servingsWrapper.get("serving");
            JSONArray servingArray;

            if (servingNode instanceof JSONArray) {
                servingArray = (JSONArray) servingNode;
            } else {
                servingArray = new JSONArray();
                servingArray.put(servingNode);
            }

            for (int i = 0; i < servingArray.length(); i++) {
                JSONObject s = servingArray.getJSONObject(i);

                long servingId = s.optLong("serving_id", -1);
                String desc = s.optString("serving_description", null);
                String measurementDesc = s.optString("measurement_description", null);

                // Parse serving amount and unit from description
                ServingAmountUnit parsed = parseServingDescription(desc, measurementDesc);

                Double calories = parseDoubleOrNull(s.optString("calories", null));
                Double protein = parseDoubleOrNull(s.optString("protein", null));
                Double fat = parseDoubleOrNull(s.optString("fat", null));
                Double carbs = parseDoubleOrNull(s.optString("carbohydrate", null));
                Double fiber = parseDoubleOrNull(s.optString("fiber", null));
                Double sugar = parseDoubleOrNull(s.optString("sugar", null));
                Double sodium = parseDoubleOrNull(s.optString("sodium", null));

                servings.add(new ServingInfo(
                        servingId,
                        desc,              // Keep original description
                        parsed.amount,     // Parsed amount
                        parsed.unit,       // Parsed unit
                        calories,
                        protein,
                        fat,
                        carbs,
                        fiber,
                        sugar,
                        sodium
                ));
            }
        }

        return new FoodDetails(foodId, name, type, brand, url, servings);
    }

    private Double parseDoubleOrNull(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Parse serving description to extract amount and unit.
     * Examples:
     *   "100 g" → amount=100.0, unit="g"
     *   "1 small (2-1/2\" dia)" → amount=1.0, unit="small"
     *   "1/2 large (yield after cooking)" → amount=0.5, unit="large"
     *   "1 cup" → amount=1.0, unit="cup"
     *   "2.5 oz" → amount=2.5, unit="oz"
     */
    private ServingAmountUnit parseServingDescription(String description, String measurementDescription) {
        double amount = 1.0;
        String unit = measurementDescription != null ? measurementDescription : "";

        if (description != null && !description.isBlank()) {
            // Try to extract leading number from description
            String trimmed = description.trim();
            String[] parts = trimmed.split("\\s+", 2);

            if (parts.length > 0) {
                try {
                    // Try to parse as fraction (e.g., "1/2")
                    if (parts[0].contains("/")) {
                        String[] fractionParts = parts[0].split("/");
                        if (fractionParts.length == 2) {
                            double numerator = Double.parseDouble(fractionParts[0]);
                            double denominator = Double.parseDouble(fractionParts[1]);
                            amount = numerator / denominator;
                        }
                    } else {
                        // Parse as regular number
                        amount = Double.parseDouble(parts[0]);
                    }

                    // If successfully parsed, use rest as unit if measurementDescription is empty
                    if (parts.length > 1 && (unit == null || unit.isBlank())) {
                        unit = parts[1];
                    }
                } catch (NumberFormatException e) {
                    // If first part is not a number, amount defaults to 1.0
                    amount = 1.0;
                }
            }
        }

        return new ServingAmountUnit(amount, unit);
    }

    /**
     * Helper class to hold parsed serving amount and unit.
     */
    private static class ServingAmountUnit {
        final double amount;
        final String unit;

        ServingAmountUnit(double amount, String unit) {
            this.amount = amount;
            this.unit = unit;
        }
    }

    /**
     * Holds top-level food info + all servings.
     */
    public static class FoodDetails {
        public final long foodId;
        public final String name;
        public final String foodType;
        public final String brandName;
        public final String foodUrl;
        public final List<ServingInfo> servings;

        public FoodDetails(long foodId,
                           String name,
                           String foodType,
                           String brandName,
                           String foodUrl,
                           List<ServingInfo> servings) {
            this.foodId = foodId;
            this.name = name;
            this.foodType = foodType;
            this.brandName = brandName;
            this.foodUrl = foodUrl;
            this.servings = Collections.unmodifiableList(servings);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("FoodDetails{")
                    .append("foodId=").append(foodId)
                    .append(", name='").append(name).append('\'');
            if (brandName != null && !brandName.isBlank()) {
                sb.append(", brand='").append(brandName).append('\'');
            }
            if (foodType != null && !foodType.isBlank()) {
                sb.append(", type='").append(foodType).append('\'');
            }
            if (foodUrl != null && !foodUrl.isBlank()) {
                sb.append(", url='").append(foodUrl).append('\'');
            }
            sb.append(", servings=").append(servings.size()).append('}');
            return sb.toString();
        }
    }

    /**
     * Holds nutrition info for a single serving.
     */
    public static class ServingInfo {
        public final long servingId;
        public final String servingDescription;  // Original full description: "100 g", "1/2 large (yield after cooking, bone removed)"
        public final double servingAmount;       // Parsed amount: 100.0, 0.5, 1.0
        public final String servingUnit;         // Parsed unit: "g", "cup", "large"

        public final Double calories;
        public final Double protein;
        public final Double fat;
        public final Double carbs;
        public final Double fiber;
        public final Double sugar;
        public final Double sodium;

        public ServingInfo(long servingId,
                           String servingDescription,
                           double servingAmount,
                           String servingUnit,
                           Double calories,
                           Double protein,
                           Double fat,
                           Double carbs,
                           Double fiber,
                           Double sugar,
                           Double sodium) {
            this.servingId = servingId;
            this.servingDescription = servingDescription;
            this.servingAmount = servingAmount;
            this.servingUnit = servingUnit;
            this.calories = calories;
            this.protein = protein;
            this.fat = fat;
            this.carbs = carbs;
            this.fiber = fiber;
            this.sugar = sugar;
            this.sodium = sodium;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("ServingInfo{");
            sb.append("servingId=").append(servingId);
            if (servingDescription != null && !servingDescription.isBlank()) {
                sb.append(", desc='").append(servingDescription).append('\'');
            }
            sb.append(", amount=").append(servingAmount);
            if (servingUnit != null && !servingUnit.isBlank()) {
                sb.append(", unit='").append(servingUnit).append('\'');
            }
            if (calories != null) sb.append(", kcal=").append(calories);
            if (protein != null) sb.append(", protein=").append(protein).append(" g");
            if (fat != null) sb.append(", fat=").append(fat).append(" g");
            if (carbs != null) sb.append(", carbs=").append(carbs).append(" g");
            if (fiber != null) sb.append(", fiber=").append(fiber).append(" g");
            if (sugar != null) sb.append(", sugar=").append(sugar).append(" g");
            if (sodium != null) sb.append(", sodium=").append(sodium).append(" mg");
            sb.append('}');
            return sb.toString();
        }
    }

    /**
     * Demo main: plug in a foodId you got from the search client.
     */
    public static void main(String[] args) {
        // Example: use an ID from your search results
        long foodId = 1641L; // replace with actual ID selected by user

        FatSecretFoodGetClient client = new FatSecretFoodGetClient();
        try {
            FoodDetails details = client.getFoodDetails(foodId);

            System.out.println("\n=== FOOD DETAILS ===");
            System.out.println(details);

            System.out.println("\n=== SERVINGS ===");
            for (ServingInfo s : details.servings) {
                System.out.println(" - " + s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


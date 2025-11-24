package tut0301.group1.healthz.dataaccess.API;

import org.json.JSONArray;
import org.json.JSONObject;
import tut0301.group1.healthz.dataaccess.API.OAuth.FatSecretOAuthTokenFetcher;
import tut0301.group1.healthz.dataaccess.config.EnvConfig;
import tut0301.group1.healthz.entities.nutrition.FoodDetails;
import tut0301.group1.healthz.entities.nutrition.ServingInfo;
import tut0301.group1.healthz.usecase.food.detail.FoodDetailGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gateway implementation for retrieving food details from FatSecret API.
 *
 * This class is in the Data Access layer and implements the Gateway interface
 * defined in the Use Case layer, following the Dependency Inversion Principle.
 */
public class FatSecretFoodDetailGateway implements FoodDetailGateway {
    private final FatSecretApiClient apiClient;
    private final FatSecretOAuthTokenFetcher tokenFetcher;

    public FatSecretFoodDetailGateway() {
        String clientId = EnvConfig.getClientId();
        String clientSecret = EnvConfig.getClientSecret();
        this.tokenFetcher = new FatSecretOAuthTokenFetcher(clientId, clientSecret);
        this.apiClient = new FatSecretApiClient();
    }

    @Override
    public FoodDetails getFoodDetails(long foodId) throws IOException, InterruptedException {
        String jsonResponse = tokenFetcher.getAccessTokenRaw("basic");
        String token = FatSecretOAuthTokenFetcher.TokenParser.extractAccessToken(jsonResponse);
        String foodJson = apiClient.getFoodDetails(token, foodId);
        return parseFoodDetails(foodJson);
    }

    /**
     * Parse the food.get.v5 JSON response into a FoodDetails object.
     */
    private FoodDetails parseFoodDetails(String json) {
        JSONObject root = new JSONObject(json);
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

                double amount = 1.0;
                String unit = measurementDesc != null ? measurementDesc : "";

                if (desc != null && !desc.isBlank()) {
                    String trimmed = desc.trim();
                    String[] parts = trimmed.split("\\s+", 2);

                    if (parts.length > 0) {
                        try {
                            if (parts[0].contains("/")) {
                                String[] fractionParts = parts[0].split("/");
                                if (fractionParts.length == 2) {
                                    double numerator = Double.parseDouble(fractionParts[0]);
                                    double denominator = Double.parseDouble(fractionParts[1]);
                                    amount = numerator / denominator;
                                }
                            } else {
                                amount = Double.parseDouble(parts[0]);
                            }

                            if (parts.length > 1 && (unit == null || unit.isBlank())) {
                                unit = parts[1];
                            }
                        } catch (NumberFormatException e) {
                            amount = 1.0;
                        }
                    }
                }

                Double calories = parseDoubleOrNull(s.optString("calories", null));
                Double protein = parseDoubleOrNull(s.optString("protein", null));
                Double fat = parseDoubleOrNull(s.optString("fat", null));
                Double carbs = parseDoubleOrNull(s.optString("carbohydrate", null));
                Double fiber = parseDoubleOrNull(s.optString("fiber", null));
                Double sugar = parseDoubleOrNull(s.optString("sugar", null));
                Double sodium = parseDoubleOrNull(s.optString("sodium", null));

                servings.add(new ServingInfo(
                        servingId,
                        desc,
                        amount,
                        unit,
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
}

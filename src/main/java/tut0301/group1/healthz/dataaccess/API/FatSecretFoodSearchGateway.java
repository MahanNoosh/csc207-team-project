package tut0301.group1.healthz.dataaccess.API;

import org.json.JSONArray;
import org.json.JSONObject;
import tut0301.group1.healthz.dataaccess.API.OAuth.FatSecretOAuthTokenFetcher;
import tut0301.group1.healthz.dataaccess.config.EnvConfig;
import tut0301.group1.healthz.entities.nutrition.BasicFood;
import tut0301.group1.healthz.entities.nutrition.Macro;
import tut0301.group1.healthz.usecase.food.search.FoodSearchGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gateway implementation for FoodSearchGateway (Use Case layer interface).
 * Data Access layer - handles FatSecret API integration.
 *
 * Clean Architecture:
 * - Implements interface from Use Case layer (Dependency Inversion)
 * - Uses HTTP client for low-level communication
 * - Transforms API data to domain entities
 */
public class FatSecretFoodSearchGateway implements FoodSearchGateway {

    private final FatSecretApiClient apiClient;
    private final FatSecretOAuthTokenFetcher tokenFetcher;

    public FatSecretFoodSearchGateway() {
        String clientId = EnvConfig.getClientId();
        String clientSecret = EnvConfig.getClientSecret();
        this.tokenFetcher = new FatSecretOAuthTokenFetcher(clientId, clientSecret);
        this.apiClient = new FatSecretApiClient();
    }

    @Override
    public List<BasicFood> searchByName(String foodName) throws IOException, InterruptedException {
        String jsonResponse = tokenFetcher.getAccessTokenRaw("basic");
        String token = FatSecretOAuthTokenFetcher.TokenParser.extractAccessToken(jsonResponse);
        String foodJson = apiClient.searchFoodByName(token, foodName);
        return parseBasicFoodList(foodJson);
    }

    private List<BasicFood> parseBasicFoodList(String jsonResponse) {
        List<BasicFood> foodList = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(jsonResponse);
            if (!root.has("foods")) return foodList;

            JSONObject foodsObj = root.getJSONObject("foods");
            if (!foodsObj.has("food")) return foodList;

            Object foodObj = foodsObj.get("food");

            if (foodObj instanceof JSONObject) {
                BasicFood food = parseSingleFood((JSONObject) foodObj);
                if (food != null) foodList.add(food);
            } else if (foodObj instanceof JSONArray) {
                JSONArray foodArray = (JSONArray) foodObj;
                for (int i = 0; i < foodArray.length(); i++) {
                    BasicFood food = parseSingleFood(foodArray.getJSONObject(i));
                    if (food != null) foodList.add(food);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to parse food list: " + e.getMessage());
        }

        return foodList;
    }

    private BasicFood parseSingleFood(JSONObject foodObj) {
        try {
            long foodId = foodObj.optLong("food_id", 0L);
            String foodName = foodObj.optString("food_name", "Unknown");
            String foodDescription = foodObj.optString("food_description", "");
            String foodType = foodObj.optString("food_type", "Generic");
            String foodUrl = foodObj.optString("food_url", "");

            Macro macro = parseMacroFromDescription(foodDescription);

            double servingAmount = 100.0;
            String servingUnit = "g";

            if (foodDescription != null && !foodDescription.isBlank()) {
                String lowerDesc = foodDescription.toLowerCase().trim();
                if (lowerDesc.startsWith("per ")) {
                    try {
                        int dashIndex = foodDescription.indexOf(" -");
                        String servingPart = dashIndex > 0
                            ? foodDescription.substring(4, dashIndex).trim()
                            : foodDescription.substring(4).trim();

                        Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)\\s*([a-zA-Z]+)");
                        Matcher matcher = pattern.matcher(servingPart);

                        if (matcher.find()) {
                            servingAmount = Double.parseDouble(matcher.group(1));
                            servingUnit = matcher.group(2);
                        }
                    } catch (Exception e) {
                        // Use defaults
                    }
                }
            }

            return new BasicFood(
                foodId,
                foodName,
                foodDescription,
                foodType,
                foodUrl,
                macro,
                servingAmount,
                servingUnit
            );
        } catch (Exception e) {
            return null;
        }
    }

    private Macro parseMacroFromDescription(String description) {
        double calories = 0, protein = 0, fat = 0, carbs = 0;

        try {
            String[] parts = description.split("\\|");
            for (String part : parts) {
                String lower = part.trim().toLowerCase();
                if (lower.contains("calories")) calories = extractNumber(part);
                else if (lower.contains("protein")) protein = extractNumber(part);
                else if (lower.contains("fat")) fat = extractNumber(part);
                else if (lower.contains("carb")) carbs = extractNumber(part);
            }
        } catch (Exception e) {
            // Return default values
        }

        return new Macro(calories, protein, fat, carbs);
    }

    private double extractNumber(String text) {
        try {
            String[] split = text.split(":");
            if (split.length < 2) return 0;

            Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)");
            Matcher matcher = pattern.matcher(split[1]);
            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            }
        } catch (Exception e) {
            // Return default
        }
        return 0;
    }
}

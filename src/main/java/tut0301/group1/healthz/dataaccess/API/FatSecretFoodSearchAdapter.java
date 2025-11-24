//package tut0301.group1.healthz.dataaccess.API;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import tut0301.group1.healthz.entities.nutrition.BasicFood;
//import tut0301.group1.healthz.entities.nutrition.Macro;
//import tut0301.group1.healthz.usecase.food.search.FoodSearchGateway;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Gateway implementation for FoodSearchGateway using FatSecret API.
// *
// * This class is in the Infrastructure layer (Data Access).
// * It implements the interface defined by the Use Case layer,
// * following the Dependency Inversion Principle.
// *
// * Responsibilities:
// * - Authentication with FatSecret API
// * - Making HTTP requests
// * - Parsing JSON responses
// * - Converting API data to domain entities (BasicFood)
// */
//public class FatSecretFoodSearchAdapter implements FoodSearchGateway {
//
//    private final FatSecretFoodSearchGateway searchGateway;
//    private final FatSecretOAuthTokenFetcher tokenFetcher;
//
//    /**
//     * Constructor that accepts dependencies.
//     *
//     * @param searchGateway The gateway for making API calls
//     * @param tokenFetcher The token fetcher for authentication
//     */
//    public FatSecretFoodSearchAdapter(FatSecretFoodSearchGateway searchGateway,
//                                      FatSecretOAuthTokenFetcher tokenFetcher) {
//        this.searchGateway = searchGateway;
//        this.tokenFetcher = tokenFetcher;
//    }
//
//    /**
//     * Convenience constructor that creates dependencies using environment config.
//     * This is where environment variables are read - in the Infrastructure layer.
//     */
//    public FatSecretFoodSearchAdapter() {
//        String clientId = EnvConfig.getClientId();
//        String clientSecret = EnvConfig.getClientSecret();
//
//        this.tokenFetcher = new FatSecretOAuthTokenFetcher(clientId, clientSecret);
//        this.searchGateway = new FatSecretFoodSearchGateway();
//    }
//
//    /**
//     * Implementation of the FoodSearchGateway interface.
//     * Searches for foods by name using the FatSecret API.
//     *
//     * @param foodName The name of the food to search for
//     * @return List of BasicFood entities matching the search
//     * @throws IOException if API call fails
//     * @throws InterruptedException if request is interrupted
//     */
//    @Override
//    public List<BasicFood> searchByName(String foodName) throws IOException, InterruptedException {
//        // 1. Get OAuth access token
//        String jsonResponse = tokenFetcher.getAccessTokenRaw("basic");
//        String token = FatSecretOAuthTokenFetcher.TokenParser.extractAccessToken(jsonResponse);
//
//        // 2. Search for food by name
//        String foodJson = searchGateway.searchFoodByName(token, foodName);
//
//        // 3. Parse JSON and convert to List<BasicFood>
//        return parseBasicFoodList(foodJson);
//    }
//
//    /**
//     * Parse the JSON response from FatSecret foods.search API and convert to List<BasicFood>.
//     *
//     * Handles two cases:
//     * - Single food object: {"foods": {"food": {...}}}
//     * - Multiple foods: {"foods": {"food": [{...}, {...}]}}
//     *
//     * @param jsonResponse JSON string from API
//     * @return List of BasicFood entities
//     */
//    private List<BasicFood> parseBasicFoodList(String jsonResponse) {
//        List<BasicFood> foodList = new ArrayList<>();
//
//        try {
//            JSONObject root = new JSONObject(jsonResponse);
//
//            if (!root.has("foods")) {
//                System.err.println("❌ Invalid JSON: missing 'foods' object.");
//                return foodList;
//            }
//
//            JSONObject foodsObj = root.getJSONObject("foods");
//
//            if (!foodsObj.has("food")) {
//                System.err.println("⚠️ No 'food' found in JSON.");
//                return foodList;
//            }
//
//            Object foodObj = foodsObj.get("food");
//
//            if (foodObj instanceof JSONObject) {
//                // Single food object
//                BasicFood food = parseSingleFood((JSONObject) foodObj);
//                if (food != null) {
//                    foodList.add(food);
//                }
//            } else if (foodObj instanceof JSONArray) {
//                // Array of foods
//                JSONArray foodArray = (JSONArray) foodObj;
//                for (int i = 0; i < foodArray.length(); i++) {
//                    JSONObject foodItem = foodArray.getJSONObject(i);
//                    BasicFood food = parseSingleFood(foodItem);
//                    if (food != null) {
//                        foodList.add(food);
//                    }
//                }
//            } else {
//                System.err.println("❌ Unexpected 'food' type in JSON.");
//            }
//
//        } catch (Exception e) {
//            System.err.println("❌ Failed to parse BasicFood list: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return foodList;
//    }
//
//    /**
//     * Parse a single food JSON object to BasicFood entity.
//     *
//     * Example JSON:
//     * {
//     *   "food_description": "Per 100g - Calories: 22kcal | Fat: 0.34g | Carbs: 3.28g | Protein: 3.09g",
//     *   "food_id": "36421",
//     *   "food_name": "Mushrooms",
//     *   "food_type": "Generic",
//     *   "food_url": "https://foods.fatsecret.com/calories-nutrition/usda/mushrooms"
//     * }
//     *
//     * @param foodObj JSON object representing a single food
//     * @return BasicFood entity or null if parsing fails
//     */
//    private BasicFood parseSingleFood(JSONObject foodObj) {
//        try {
//            // Extract food details
//            long foodId = foodObj.optLong("food_id", 0L);
//            String foodName = foodObj.optString("food_name", "Unknown");
//            String foodDescription = foodObj.optString("food_description", "");
//            String foodType = foodObj.optString("food_type", "Generic");
//            String foodUrl = foodObj.optString("food_url", "");
//
//            // Parse macro from description
//            Macro macro = parseMacroFromDescription(foodDescription);
//
//            // Parse serving size and unit from description (e.g., "Per 100g")
//            ServingInfo servingInfo = parseServingFromDescription(foodDescription);
//
//            // Create and return BasicFood entity
//            return new BasicFood(
//                foodId,
//                foodName,
//                foodDescription,
//                foodType,
//                foodUrl,
//                macro,
//                servingInfo.amount,
//                servingInfo.unit
//            );
//
//        } catch (Exception e) {
//            System.err.println("⚠️ Failed to parse food item: " + e.getMessage());
//            return null;
//        }
//    }
//
//    /**
//     * Parse macro information from food_description field.
//     * Example: "Per 100g - Calories: 22kcal | Fat: 0.34g | Carbs: 3.28g | Protein: 3.09g"
//     *
//     * @param description The food description string
//     * @return Macro object with nutritional information
//     */
//    private Macro parseMacroFromDescription(String description) {
//        double calories = 0, protein = 0, fat = 0, carbs = 0;
//
//        try {
//            String[] parts = description.split("\\|");
//            for (String part : parts) {
//                part = part.trim();
//
//                if (part.toLowerCase().contains("calories")) {
//                    calories = extractNumber(part);
//                } else if (part.toLowerCase().contains("protein")) {
//                    protein = extractNumber(part);
//                } else if (part.toLowerCase().contains("fat")) {
//                    fat = extractNumber(part);
//                } else if (part.toLowerCase().contains("carb")) {
//                    carbs = extractNumber(part);
//                }
//            }
//
//            return new Macro(calories, protein, fat, carbs);
//
//        } catch (Exception e) {
//            System.err.println("⚠️ Failed to parse macro values: " + e.getMessage());
//            return new Macro(0, 0, 0, 0);
//        }
//    }
//
//    /**
//     * Extract numeric value from text like "Calories: 22kcal" or "Fat: 0.34g"
//     *
//     * @param text The text containing the numeric value
//     * @return The extracted number, or 0 if not found
//     */
//    private double extractNumber(String text) {
//        try {
//            String[] split = text.split(":");
//            if (split.length < 2) return 0;
//            String afterColon = split[1];
//
//            // Use regex to find the first number
//            Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)");
//            Matcher matcher = pattern.matcher(afterColon);
//            if (matcher.find()) {
//                return Double.parseDouble(matcher.group(1));
//            }
//        } catch (Exception e) {
//            // ignore
//        }
//        return 0;
//    }
//
//    /**
//     * Parse serving information from description.
//     * Example: "Per 100g - ..." → ServingInfo(100.0, "g")
//     *          "Per 1 cup - ..." → ServingInfo(1.0, "cup")
//     *
//     * @param description The food description string
//     * @return ServingInfo with amount and unit
//     */
//    private ServingInfo parseServingFromDescription(String description) {
//        if (description == null || description.isBlank()) {
//            return new ServingInfo(100.0, "g"); // Default
//        }
//
//        try {
//            String lowerDesc = description.toLowerCase().trim();
//            if (!lowerDesc.startsWith("per ")) {
//                return new ServingInfo(100.0, "g");
//            }
//
//            // Extract the part after "Per " and before " -"
//            int dashIndex = description.indexOf(" -");
//            String servingPart;
//            if (dashIndex > 0) {
//                servingPart = description.substring(4, dashIndex).trim(); // Skip "Per "
//            } else {
//                servingPart = description.substring(4).trim();
//            }
//
//            // Parse amount and unit (e.g., "100g", "1 cup")
//            Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)\\s*([a-zA-Z]+)");
//            Matcher matcher = pattern.matcher(servingPart);
//
//            if (matcher.find()) {
//                double amount = Double.parseDouble(matcher.group(1));
//                String unit = matcher.group(2);
//                return new ServingInfo(amount, unit);
//            }
//
//        } catch (Exception e) {
//            // If parsing fails, return default
//        }
//
//        return new ServingInfo(100.0, "g");
//    }
//
//    /**
//     * Helper class to hold serving information.
//     */
//    private static class ServingInfo {
//        final double amount;
//        final String unit;
//
//        ServingInfo(double amount, String unit) {
//            this.amount = amount;
//            this.unit = unit;
//        }
//    }
//}

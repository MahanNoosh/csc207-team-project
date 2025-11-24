package tut0301.group1.healthz.dataaccess.API;

import org.json.JSONArray;
import org.json.JSONObject;
import tut0301.group1.healthz.entities.ServingSize;
import tut0301.group1.healthz.entities.nutrition.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to parse JSON responses returned by the FatSecret API.
 */
public class FoodJsonParser {

    /**
     * Pa the
    API .
     *
    raw servinges the JSON response from FatSecret foods.search API and extracts
     * all food_id and food_name pairs.
     *
     * @param jsonResponse the JSON string returned from API
     * @return a list of "food_id - food_name" strings
     */
    public static List<String> parseFoodsList(String jsonResponse) {
        List<String> foodsList = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(jsonResponse);

            if (!root.has("foods")) {
                System.err.println("❌ Invalid JSON: missing 'foods' object.");
                return foodsList;
            }

            JSONObject foodsObj = root.getJSONObject("foods");
            JSONArray foodArray = foodsObj.optJSONArray("food");

            if (foodArray == null) {
                System.err.println("⚠️ No 'food' array found in JSON.");
                return foodsList;
            }

            for (int i = 0; i < foodArray.length(); i++) {
                JSONObject food = foodArray.getJSONObject(i);
                long id = food.optLong("food_id", -1);
                String name = food.optString("food_name", "unknown");
                foodsList.add(id + " - " + name);
            }

        } catch (Exception e) {
            System.err.println("❌ Failed to parse foods list: " + e.getMessage());
        }

        return foodsList;
    }

    /**
     * Extracts Macro information (calories, protein, fat, carbs) for a given food name.
     * This assumes the JSON structure follows FatSecret's standard format.
     *
     * @param jsonResponse   the JSON string returned from API
     * @param targetFoodName the food name to search for
     * @return a Macro object with nutritional data (or null if not found)
     */
    public static Macro getMacroByName(String jsonResponse, String targetFoodName) {
        try {
            JSONObject root = new JSONObject(jsonResponse);
            if (!root.has("foods")) {
                System.err.println("❌ Invalid JSON: missing 'foods' object.");
                return null;
            }

            JSONObject foodsObj = root.getJSONObject("foods");
            JSONArray foodArray = foodsObj.optJSONArray("food");
            if (foodArray == null) return null;

            for (int i = 0; i < foodArray.length(); i++) {
                JSONObject food = foodArray.getJSONObject(i);
                String foodName = food.optString("food_name", "");

                if (foodName.equalsIgnoreCase(targetFoodName)) {
                    // Parse food_description like:
                    // "Per 100g - Calories: 89kcal | Fat: 0.3g | Carbs: 23g | Protein: 1.1g"
                    String desc = food.optString("food_description", "");
                    return parseMacroFromDescription(desc);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to extract macro info: " + e.getMessage());
        }

        return null;
    }

    /**
     * Helper: Parses "Calories: 89kcal | Fat: 0.3g | Carbs: 23g | Protein: 1.1g"
     * into a Macro object.
     */
    private static Macro parseMacroFromDescription(String description) {
        double calories = 0, protein = 0, fat = 0, carbs = 0;

        try {
            String[] parts = description.split("\\|");
            for (String part : parts) {
                part = part.trim();

                if (part.toLowerCase().contains("calories")) {
                    calories = extractNumberAfterColonStrict(part);
                } else if (part.toLowerCase().contains("protein")) {
                    protein = extractNumberAfterColonStrict(part);
                } else if (part.toLowerCase().contains("fat")) {
                    fat = extractNumberAfterColonStrict(part);
                } else if (part.toLowerCase().contains("carb")) {
                    carbs = extractNumberAfterColonStrict(part);
                }
            }

            return new Macro(calories, protein, fat, carbs);

        } catch (Exception e) {
            System.err.println("⚠️ Failed to parse macro values: " + e.getMessage());
            return null;
        }
    }

    /**
     * Extracts macro results for the list of foods returned by the search API.
     * Each result includes the food name, the raw serving description, and parsed macros.
     */
    public static List<MacroSearchResult> parseMacroResults(String jsonResponse) {
        List<MacroSearchResult> results = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(jsonResponse);
            if (!root.has("foods")) {
                return results;
            }

            JSONObject foodsObj = root.getJSONObject("foods");
            JSONArray foodArray = foodsObj.optJSONArray("food");
            if (foodArray == null) {
                return results;
            }

            for (int i = 0; i < foodArray.length(); i++) {
                JSONObject food = foodArray.getJSONObject(i);
                long foodId = food.optLong("food_id", -1);
                String foodName = food.optString("food_name", "");
                String description = food.optString("food_description", "");
                Macro macro = parseMacroFromDescription(description);
                results.add(new MacroSearchResult(foodId, foodName, description, macro));
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to parse macro results: " + e.getMessage());
        }

        return results;
    }

    /**
     * Extracts the first numeric value after the colon (or after "Calories", "Fat", etc.).
     * Example:
     * - "Per 1 apple - Calories: 100kcal" → 100.0
     * - "Calories: 52kcal" → 52.0
     */
    private static double extractNumberAfterColonStrict(String text) {
        try {
            String[] split = text.split(":");
            if (split.length < 2) return 0;
            String afterColon = split[1];

            // Use regex to find the first number after colon
            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(\\d+(\\.\\d+)?)").matcher(afterColon);
            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            }
        } catch (Exception e) {
            // ignore
        }
        return 0;
    }

    /**
     * Parse a full food detail response from FatSecret into a rich
     * nutrition model.
     */
    public static FoodDetails parseFoodDetails(JSONObject root) {

        // The outer "food" object
        JSONObject foodObj = root.getJSONObject("food");

        long foodId = foodObj.getLong("food_id");
        String name = foodObj.getString("food_name");
        String foodType = foodObj.getString("food_type");

        // Some foods may not have brand name, so use optString
        String brandName = foodObj.optString("brand_name", null);
        String foodUrl = foodObj.getString("food_url");

        // Parse servings
        JSONObject servingsObj = foodObj.getJSONObject("servings");
        JSONArray servingArray = servingsObj.getJSONArray("serving");

        List<ServingInfo> servings = new ArrayList<>();

        for (int i = 0; i < servingArray.length(); i++) {
            JSONObject s = servingArray.getJSONObject(i);

            ServingInfo info = new ServingInfo(
                    s.getLong("serving_id"),
                    s.getString("serving_description"),

                    // Metric amount for your parsed fields
                    s.getDouble("metric_serving_amount"),
                    s.getString("metric_serving_unit"),

                    // Nutrition fields (use opt to handle missing values)
                    parseDouble(s, "calories"),
                    parseDouble(s, "protein"),
                    parseDouble(s, "fat"),
                    parseDouble(s, "carbohydrate"),
                    parseDouble(s, "fiber"),
                    parseDouble(s, "sugar"),
                    parseDouble(s, "sodium")
            );

            servings.add(info);
        }

        return new FoodDetails(foodId, name, foodType, brandName, foodUrl, servings);
    }

    // Utility: safely parse a double field (may be missing)
    private static Double parseDouble(JSONObject obj, String key) {
        if (!obj.has(key)) return null;
        String raw = obj.optString(key, "");
        if (raw.isEmpty()) return null;
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Parse the JSON response from FatSecret foods.search API and convert to List<BasicFood>.
     *
     * Handles two cases:
     * - Single food object: {"foods": {"food": {...}}}
     * - Multiple foods: {"foods": {"food": [{...}, {...}]}}
     *
     * @param jsonResponse JSON string from API
     * @return List of BasicFood entities
     */
    public static List<BasicFood> parseBasicFoodList(String jsonResponse) {
        List<BasicFood> foodList = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(jsonResponse);

            if (!root.has("foods")) {
                System.err.println("❌ Invalid JSON: missing 'foods' object.");
                return foodList;
            }

            JSONObject foodsObj = root.getJSONObject("foods");

            if (!foodsObj.has("food")) {
                System.err.println("⚠️ No 'food' found in JSON.");
                return foodList;
            }

            Object foodObj = foodsObj.get("food");

            if (foodObj instanceof JSONObject) {
                // Single food object
                BasicFood food = parseSingleFood((JSONObject) foodObj);
                if (food != null) {
                    foodList.add(food);
                }
            } else if (foodObj instanceof JSONArray) {
                // Array of foods
                JSONArray foodArray = (JSONArray) foodObj;
                for (int i = 0; i < foodArray.length(); i++) {
                    JSONObject foodItem = foodArray.getJSONObject(i);
                    BasicFood food = parseSingleFood(foodItem);
                    if (food != null) {
                        foodList.add(food);
                    }
                }
            } else {
                System.err.println("❌ Unexpected 'food' type in JSON.");
            }

        } catch (Exception e) {
            System.err.println("❌ Failed to parse BasicFood list: " + e.getMessage());
            e.printStackTrace();
        }

        return foodList;
    }

    /**
     * Parse a single food JSON object to BasicFood entity.
     *
     * Example JSON:
     * {
     *   "food_description": "Per 100g - Calories: 22kcal | Fat: 0.34g | Carbs: 3.28g | Protein: 3.09g",
     *   "food_id": "36421",
     *   "food_name": "Mushrooms",
     *   "food_type": "Generic",
     *   "food_url": "https://foods.fatsecret.com/calories-nutrition/usda/mushrooms"
     * }
     *
     * @param foodObj JSON object representing a single food
     * @return BasicFood entity or null if parsing fails
     */
    private static BasicFood parseSingleFood(JSONObject foodObj) {
        try {
            // Extract food details
            long foodId = foodObj.optLong("food_id", 0L);
            String foodName = foodObj.optString("food_name", "Unknown");
            String foodDescription = foodObj.optString("food_description", "");
            String foodType = foodObj.optString("food_type", "Generic");
            String foodUrl = foodObj.optString("food_url", "");

            // Parse macro from description
            Macro macro = parseMacroFromDescription(foodDescription);

            // Parse serving size and unit from description (e.g., "Per 100g")
            ServingSize serving = parseServingFromDescription(foodDescription);

            // Create and return BasicFood entity
            return new BasicFood(
                    foodId,
                    foodName,
                    foodDescription,
                    foodType,
                    foodUrl,
                    macro,
                    serving.getAmount(),
                    serving.getUnit()
            );

        } catch (Exception e) {
            System.err.println("⚠️ Failed to parse food item: " + e.getMessage());
            return null;
        }
    }

    /**
     * Extract numeric value from text like "Calories: 22kcal" or "Fat: 0.34g"
     *
     * @param text The text containing the numeric value
     * @return The extracted number, or 0 if not found
     */
    private double extractNumber(String text) {
        try {
            String[] split = text.split(":");
            if (split.length < 2) return 0;
            String afterColon = split[1];

            // Use regex to find the first number
            Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)");
            Matcher matcher = pattern.matcher(afterColon);
            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            }
        } catch (Exception e) {
            // ignore
        }
        return 0;
    }

    /**
     * Parse serving information from description.
     * Example: "Per 100g - ..." → ServingInfo(100.0, "g")
     *          "Per 1 cup - ..." → ServingInfo(1.0, "cup")
     *
     * @param description The food description string
     * @return ServingInfo with amount and unit
     */
    private static ServingSize parseServingFromDescription(String description) {
        if (description == null || description.isBlank()) {
            return new ServingSize(100.0, "g"); // Default
        }

        try {
            String lowerDesc = description.toLowerCase().trim();
            if (!lowerDesc.startsWith("per ")) {
                return new ServingSize(100.0, "g");
            }

            // Extract the part after "Per " and before " -"
            int dashIndex = description.indexOf(" -");
            String servingPart;
            if (dashIndex > 0) {
                servingPart = description.substring(4, dashIndex).trim(); // Skip "Per "
            } else {
                servingPart = description.substring(4).trim();
            }

            // Parse amount and unit (e.g., "100g", "1 cup")
            Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)\\s*([a-zA-Z]+)");
            Matcher matcher = pattern.matcher(servingPart);

            if (matcher.find()) {
                double amount = Double.parseDouble(matcher.group(1));
                String unit = matcher.group(2);
                return new ServingSize(amount, unit);
            }

        } catch (Exception e) {
            // If parsing fails, return default
        }

        return new ServingSize(100.0, "g");
    }
}

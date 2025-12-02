package healthz.tut0301.group1.dataaccess.api;

import healthz.tut0301.group1.entities.nutrition.BasicFood;
import healthz.tut0301.group1.entities.nutrition.FoodDetails;
import healthz.tut0301.group1.entities.nutrition.Macro;
import healthz.tut0301.group1.entities.nutrition.ServingInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import healthz.tut0301.group1.entities.ServingSize;
import healthz.tut0301.group1.entities.nutrition.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to parse JSON responses returned by the FatSecret api.
 */
public class FoodJsonParser {


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

            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(\\d+(\\.\\d+)?)").matcher(afterColon);
            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            }
        } catch (Exception e) {
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

            double amount;
            String unit;

            if (s.has("measurement_description") && s.has("number_of_units")) {
                unit = s.getString("measurement_description");
                amount = s.getDouble("number_of_units");
            } else {
                String desc = s.getString("serving_description");
                ParsedServing parsed = parseUnitFromDescription(desc);

                if (parsed != null) {
                    amount = parsed.amount;
                    unit = parsed.unit;
                } else {
                    // Case 3: Fallback to metric
                    amount = s.optDouble("metric_serving_amount", 100.0);
                    unit = s.optString("metric_serving_unit", "g");
                }
            }

            ServingInfo info = new ServingInfo(
                    s.getLong("serving_id"),
                    s.getString("serving_description"),

                    amount,
                    unit,

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

    private record ParsedServing(double amount, String unit) {}

    private static ParsedServing parseUnitFromDescription(String description) {
        if (description == null || description.isBlank()) return null;
        try {
            String cleanDesc = description.trim();

            java.util.regex.Pattern p = java.util.regex.Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)\\s+(.+)");
            java.util.regex.Matcher m = p.matcher(cleanDesc);

            if (m.find()) {
                double val = Double.parseDouble(m.group(1));
                String text = m.group(2);

                int bracketIdx = text.indexOf("(");
                if (bracketIdx > 0) text = text.substring(0, bracketIdx).trim();

                int dashIdx = text.indexOf(" -");
                if (dashIdx > 0) text = text.substring(0, dashIdx).trim();

                return new ParsedServing(val, text);
            }
        } catch (Exception e) {
        }
        return null;
    }

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
     * Parse the JSON response from FatSecret foods.search api and convert to List<BasicFood>.
     *
     * Handles two cases:
     * - Single food object: {"foods": {"food": {...}}}
     * - Multiple foods: {"foods": {"food": [{...}, {...}]}}
     *
     * @param jsonResponse JSON string from api
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

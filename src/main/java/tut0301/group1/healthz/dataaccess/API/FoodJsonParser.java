package tut0301.group1.healthz.dataaccess.API;

import org.json.JSONArray;
import org.json.JSONObject;
import tut0301.group1.healthz.entities.nutrition.Macro;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to parse JSON responses returned by the FatSecret API.
 */
public class FoodJsonParser {

    /**
     * Parses the JSON response from FatSecret foods.search API and extracts
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
     * @param jsonResponse the JSON string returned from API
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
     * Extracts Macro information from food.get.v2 API response.
     * This method parses the detailed food object returned by the food.get API.
     *
     * @param jsonResponse the JSON string returned from food.get.v2 API
     * @return a Macro object with nutritional data (or null if not found)
     */
    public static Macro getMacroFromFoodGet(String jsonResponse) {
        try {
            JSONObject root = new JSONObject(jsonResponse);
            if (!root.has("food")) {
                System.err.println("❌ Invalid JSON: missing 'food' object.");
                return null;
            }

            JSONObject food = root.getJSONObject("food");

            // The food.get API returns servings with detailed nutrition info
            if (!food.has("servings")) {
                System.err.println("❌ No servings data found.");
                return null;
            }

            JSONObject servings = food.getJSONObject("servings");
            JSONArray servingArray = servings.optJSONArray("serving");

            if (servingArray == null || servingArray.length() == 0) {
                System.err.println("⚠️ No serving information available.");
                return null;
            }

            // Use the first serving (usually the default/standard serving)
            JSONObject serving = servingArray.getJSONObject(0);

            double calories = serving.optDouble("calories", 0);
            double protein = serving.optDouble("protein", 0);
            double fat = serving.optDouble("fat", 0);
            double carbs = serving.optDouble("carbohydrate", 0);

            return new Macro(calories, protein, fat, carbs);

        } catch (Exception e) {
            System.err.println("❌ Failed to extract macro info from food.get: " + e.getMessage());
            return null;
        }
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
     * Parses a single food entry from foods.search API response (when max_results=1).
     * This handles the case where "food" is an object, not an array.
     *
     * Example JSON:
     * {
     *   "foods": {
     *     "food": {
     *       "food_description": "Per 100g - Calories: 22kcal | Fat: 0.34g | Carbs: 3.28g | Protein: 3.09g",
     *       "food_id": "36421",
     *       "food_name": "Mushrooms",
     *       "food_type": "Generic",
     *       "food_url": "https://foods.fatsecret.com/calories-nutrition/usda/mushrooms"
     *     }
     *   }
     * }
     *
     * @param jsonResponse the JSON string returned from foods.search API
     * @return a FoodSearchResult object with food details (or null if not found)
     */
    public static FoodSearchResult parseFoodEntry(String jsonResponse) {
        try {
            JSONObject root = new JSONObject(jsonResponse);
            if (!root.has("foods")) {
                System.err.println("❌ Invalid JSON: missing 'foods' object.");
                return null;
            }

            JSONObject foodsObj = root.getJSONObject("foods");

            // Handle both cases: "food" as object or array
            JSONObject food;
            if (foodsObj.has("food")) {
                Object foodObj = foodsObj.get("food");
                if (foodObj instanceof JSONObject) {
                    // Single food object
                    food = (JSONObject) foodObj;
                } else if (foodObj instanceof JSONArray) {
                    // Array of foods, get first one
                    JSONArray foodArray = (JSONArray) foodObj;
                    if (foodArray.length() == 0) {
                        System.err.println("⚠️ No food items in array.");
                        return null;
                    }
                    food = foodArray.getJSONObject(0);
                } else {
                    System.err.println("❌ Unexpected 'food' type.");
                    return null;
                }
            } else {
                System.err.println("❌ No 'food' found in JSON.");
                return null;
            }

            // Extract food details
            long foodId = food.optLong("food_id", 0L);
            String foodName = food.optString("food_name", "Unknown");
            String foodDescription = food.optString("food_description", "");
            String foodType = food.optString("food_type", "");
            String foodUrl = food.optString("food_url", "");

            // Parse macro from description
            Macro macro = parseMacroFromDescription(foodDescription);

            return new FoodSearchResult(foodId, foodName, foodDescription, foodType, foodUrl, macro);

        } catch (Exception e) {
            System.err.println("❌ Failed to parse food entry: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Data class to hold food search result details.
     */
    public static class FoodSearchResult {
        public final long foodId;
        public final String foodName;
        public final String foodDescription;
        public final String foodType;
        public final String foodUrl;
        public final Macro macro;

        public FoodSearchResult(long foodId, String foodName, String foodDescription,
                                String foodType, String foodUrl, Macro macro) {
            this.foodId = foodId;
            this.foodName = foodName;
            this.foodDescription = foodDescription;
            this.foodType = foodType;
            this.foodUrl = foodUrl;
            this.macro = macro;
        }

        @Override
        public String toString() {
            return "FoodSearchResult{" +
                    "foodId=" + foodId +
                    ", foodName='" + foodName + '\'' +
                    ", foodDescription='" + foodDescription + '\'' +
                    ", foodType='" + foodType + '\'' +
                    ", foodUrl='" + foodUrl + '\'' +
                    ", macro=" + macro +
                    '}';
        }

        /**
         * Format as a readable food entry output.
         */
        public String toFoodEntry() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Food Entry ===\n");
            sb.append("Food ID: ").append(foodId).append("\n");
            sb.append("Name: ").append(foodName).append("\n");
            sb.append("Type: ").append(foodType).append("\n");
            sb.append("Description: ").append(foodDescription).append("\n");
            if (macro != null) {
                sb.append("Nutrition (per serving):\n");
                sb.append("  - Calories: ").append(macro.calories()).append(" kcal\n");
                sb.append("  - Protein: ").append(macro.proteinG()).append(" g\n");
                sb.append("  - Fat: ").append(macro.fatG()).append(" g\n");
                sb.append("  - Carbs: ").append(macro.carbsG()).append(" g\n");
            }
            sb.append("URL: ").append(foodUrl).append("\n");
            sb.append("==================");
            return sb.toString();
        }
    }

    /**
     * Extracts the first numeric value after the colon (or after "Calories", "Fat", etc.).
     * Example:
     *  - "Per 1 apple - Calories: 100kcal" → 100.0
     *  - "Calories: 52kcal" → 52.0
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

}

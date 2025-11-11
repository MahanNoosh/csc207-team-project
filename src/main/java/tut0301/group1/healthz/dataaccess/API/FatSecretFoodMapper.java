package tut0301.group1.healthz.dataaccess.API;

import org.json.JSONArray;
import org.json.JSONObject;
import tut0301.group1.healthz.entities.nutrition.BasicFood;
import tut0301.group1.healthz.entities.nutrition.Macro;

/**
 * Mapper for converting FatSecret API JSON responses to BasicFood entities.
 * This class belongs to the Data Access layer and can depend on external libraries.
 * It separates JSON parsing concerns from the pure domain entity.
 */
public class FatSecretFoodMapper {

    /**
     * Parse FatSecret API JSON response and create a BasicFood entity.
     *
     * @param jsonResponse JSON string from FatSecret API foods.search response
     * @param targetFoodName The name of the food to search for in the JSON
     * @return BasicFood entity
     * @throws RuntimeException if parsing fails
     */
    public BasicFood fromJson(String jsonResponse, String targetFoodName) {
        try {
            JSONObject root = new JSONObject(jsonResponse);

            if (!root.has("foods")) {
                throw new IllegalArgumentException("Invalid JSON: missing 'foods' object.");
            }

            JSONObject foodsObj = root.getJSONObject("foods");

            // Extract the food object (handles both single object and array cases)
            JSONObject food = extractFood(foodsObj, targetFoodName);

            // Extract food details
            int foodId = food.optInt("food_id", 0);
            String foodName = food.optString("food_name", "Unknown");
            String foodDescription = food.optString("food_description", "");
            String foodType = food.optString("food_type", "");
            String foodUrl = food.optString("food_url", "");

            // Parse macro from description
            Macro macro = parseMacroFromDescription(foodDescription);

            // Create BasicFood entity using simple constructor
            return new BasicFood(foodId, foodName, foodDescription, foodType, foodUrl, macro);

        } catch (Exception e) {
            System.err.println("❌ Failed to parse BasicFood from JSON: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create BasicFood from JSON", e);
        }
    }

    /**
     * Extract food object from the foods JSON, handling both object and array cases.
     *
     * @param foodsObj The "foods" JSON object
     * @param targetFoodName The food name to search for
     * @return The food JSON object
     */
    private JSONObject extractFood(JSONObject foodsObj, String targetFoodName) {
        if (!foodsObj.has("food")) {
            throw new IllegalArgumentException("No 'food' found in JSON.");
        }

        Object foodObj = foodsObj.get("food");

        if (foodObj instanceof JSONObject) {
            // Single food object
            return (JSONObject) foodObj;
        } else if (foodObj instanceof JSONArray) {
            // Array of foods, search for matching food name
            JSONArray foodArray = (JSONArray) foodObj;

            for (int i = 0; i < foodArray.length(); i++) {
                JSONObject currentFood = foodArray.getJSONObject(i);
                String currentName = currentFood.optString("food_name", "");
                if (currentName.equalsIgnoreCase(targetFoodName)) {
                    return currentFood;
                }
            }

            // If no match found, use first one
            if (foodArray.length() > 0) {
                return foodArray.getJSONObject(0);
            }

            throw new IllegalArgumentException("No food items found in array.");
        } else {
            throw new IllegalArgumentException("Unexpected 'food' type in JSON.");
        }
    }

    /**
     * Parse macro information from food_description field.
     * Example: "Per 100g - Calories: 22kcal | Fat: 0.34g | Carbs: 3.28g | Protein: 3.09g"
     *
     * @param description The food description string
     * @return Macro object with nutritional information
     */
    private Macro parseMacroFromDescription(String description) {
        double calories = 0, protein = 0, fat = 0, carbs = 0;

        try {
            String[] parts = description.split("\\|");
            for (String part : parts) {
                part = part.trim();

                if (part.toLowerCase().contains("calories")) {
                    calories = extractNumber(part);
                } else if (part.toLowerCase().contains("protein")) {
                    protein = extractNumber(part);
                } else if (part.toLowerCase().contains("fat")) {
                    fat = extractNumber(part);
                } else if (part.toLowerCase().contains("carb")) {
                    carbs = extractNumber(part);
                }
            }

            return new Macro(calories, protein, fat, carbs);

        } catch (Exception e) {
            System.err.println("⚠️ Failed to parse macro values: " + e.getMessage());
            return new Macro(0, 0, 0, 0);
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
     * Main method to test the mapper with sample JSON.
     */
    public static void main(String[] args) {
        // Test JSON provided by user
        String testJson = """
                {
                  "foods": {
                    "food": {
                      "food_description": "Per 100g - Calories: 22kcal | Fat: 0.34g | Carbs: 3.28g | Protein: 3.09g",
                      "food_id": "36421",
                      "food_name": "Mushrooms",
                      "food_type": "Generic",
                      "food_url": "https://foods.fatsecret.com/calories-nutrition/usda/mushrooms"
                    },
                    "max_results": "1",
                    "page_number": "0",
                    "total_results": "1129"
                  }
                }
                """;

        System.out.println("=== Testing FatSecretFoodMapper (Clean Architecture) ===\n");
        System.out.println("Input JSON:");
        System.out.println(testJson);
        System.out.println("\n");

        try {
            // Use mapper to convert JSON to BasicFood entity
            FatSecretFoodMapper mapper = new FatSecretFoodMapper();
            BasicFood basicFood = mapper.fromJson(testJson, "Mushrooms");

            // Test all getters
            System.out.println("=== BasicFood Entity (Created via Mapper) ===");
            System.out.println("Food ID: " + basicFood.getFoodId());
            System.out.println("Food Name: " + basicFood.getFoodName());
            System.out.println("Food Type: " + basicFood.getFoodType());
            System.out.println("Food Description: " + basicFood.getFoodDescription());
            System.out.println("Food URL: " + basicFood.getFoodUrl());
            System.out.println("\n");

            // Test macro getter
            System.out.println("=== Macro Information ===");
            Macro macro = basicFood.getMacro();
            if (macro != null) {
                System.out.println("Calories: " + macro.calories() + " kcal");
                System.out.println("Protein: " + macro.proteinG() + " g");
                System.out.println("Fat: " + macro.fatG() + " g");
                System.out.println("Carbs: " + macro.carbsG() + " g");
            }
            System.out.println("\n");

            System.out.println("✅ Test completed successfully!");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }

        // Test the basic constructor (direct entity creation)
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== Testing Direct Entity Creation ===\n");

        try {
            // Create a Macro object
            Macro testMacro = new Macro(150, 5.0, 2.0, 30.0);

            // Create BasicFood using the convenience constructor (no mapper needed)
            BasicFood simpleFood = new BasicFood(
                    "Apple",
                    "Per 100g - Fresh apple",
                    "Generic",
                    testMacro
            );

            System.out.println("=== BasicFood created directly (no mapper) ===");
            System.out.println("Food ID: " + simpleFood.getFoodId() + " (should be 0)");
            System.out.println("Food Name: " + simpleFood.getFoodName());
            System.out.println("Food Type: " + simpleFood.getFoodType());
            System.out.println("Food Description: " + simpleFood.getFoodDescription());
            System.out.println("Food URL: " + simpleFood.getFoodUrl() + " (should be null)");
            System.out.println("\nMacro Information:");
            System.out.println("  Calories: " + simpleFood.getMacro().calories() + " kcal");
            System.out.println("  Protein: " + simpleFood.getMacro().proteinG() + " g");
            System.out.println("  Fat: " + simpleFood.getMacro().fatG() + " g");
            System.out.println("  Carbs: " + simpleFood.getMacro().carbsG() + " g");

            System.out.println("\n✅ Direct creation test completed successfully!");

        } catch (Exception e) {
            System.err.println("❌ Direct creation test failed: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("🎯 Clean Architecture Benefits:");
        System.out.println("  ✅ BasicFood is a pure entity (no JSON dependency)");
        System.out.println("  ✅ FatSecretFoodMapper handles infrastructure concerns");
        System.out.println("  ✅ Easy to test: can create BasicFood without JSON");
        System.out.println("  ✅ Easy to maintain: JSON changes only affect mapper");
    }
}

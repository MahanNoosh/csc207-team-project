package tut0301.group1.healthz.dataaccess.API;

import tut0301.group1.healthz.entities.nutrition.Macro;

/**
 * Simplified MacroAPI that uses an existing access token to fetch macro data.
 */
public class MacroAPI {

    private static final FatSecretFoodSearchGateway gateway = new FatSecretFoodSearchGateway();

    /**
     * Calls the FatSecret API using an existing token to get macro data for a given food name.
     *
     * @param token     Valid OAuth access token
     * @param foodName  The food name to search for
     * @return Macro object with calories, protein, fat, carbs (or null if not found)
     */
    public static Macro getMacroByName(String token, String foodName) {
        try {
            if (token == null || token.isBlank()) {
                System.err.println("❌ Invalid token (null or empty).");
                return null;
            }

            System.out.println("🔎 Searching for food: " + foodName + " using provided token...");

            // ✅ Step 1: Call foods.search API via gateway
            String foodJson = gateway.searchFoodByName(token, foodName);

            // ✅ Step 2: Parse macro data from JSON
            Macro macro = FoodJsonParser.getMacroByName(foodJson, foodName);

            // ✅ Step 3: Print results
            if (macro == null) {
                System.err.println("No nutrition data found for '" + foodName + "'.");
            }

            return macro;

        } catch (Exception e) {
            System.err.println("API call failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

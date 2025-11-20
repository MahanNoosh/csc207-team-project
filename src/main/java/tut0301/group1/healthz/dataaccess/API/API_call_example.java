package tut0301.group1.healthz.dataaccess.API;

import org.json.JSONObject;
import tut0301.group1.healthz.entities.nutrition.Macro;

import java.awt.*;
import java.util.List;

public class API_call_example {
    public static void main(String[] args) {
        try {
            // ===================== 1 Read FatSecret credentials from environment variables =====================
            String clientId = System.getenv("FATSECRET_CLIENT_ID");
            String clientSecret = System.getenv("FATSECRET_CLIENT_SECRET");

            // Check if environment variables are properly set
            if (clientId == null || clientSecret == null) {
                System.err.println("❌ Environment variables FATSECRET_CLIENT_ID or FATSECRET_CLIENT_SECRET not found!");
                System.err.println("👉 Please set them in your system or IDE before running this program.");
                return;
            }

            // ===================== 2 Get the access token (JSON response) =====================
            FatSecretOAuthTokenFetcher fetcher = new FatSecretOAuthTokenFetcher(clientId, clientSecret);
            String jsonResponse = fetcher.getAccessTokenRaw("basic");

            System.out.println("\n=== Raw Token JSON ===");
            System.out.println(jsonResponse);
            System.out.println("======================\n");

            // ===================== 3 Extract the access token from JSON =====================
            String token = FatSecretOAuthTokenFetcher.TokenParser.extractAccessToken(jsonResponse);
            if (token == null) {
                System.err.println("❌ Failed to extract access token. Stopping execution.");
                return;
            }

            // ===================== 4 Use the token to search for food =====================
            FatSecretFoodSearchGateway gateway = new FatSecretFoodSearchGateway();
            String foodName = "fish";

            System.out.println("🔎 Searching for food: " + foodName + " ...\n");

            String foodJson = gateway.searchFoodByName(token, foodName);

            System.out.println("=== Food Search Result  ===");
            System.out.println(foodJson);
            System.out.println("=================================\n");
            List<String> foodJsonlist = gateway.searchFoodByNameToList(token, foodName);
            System.out.println("=== Food Search Result(List)  ===");
            System.out.println(foodJsonlist);
            JSONObject fjl = gateway.searchFoodByNameFormated(token, foodName);
            System.out.println("=== Food Search Result(formated)  ===");

            // ===================== 5 Use the token to search for macro =====================
            MacroAPI api = new MacroAPI();


            System.out.println("🔎 Searching for food: " + foodName + " ...\n");

            Macro macro = api.getMacroByName(token, foodName);

            System.out.println("=== Macro Search Result  ===");
            System.out.println(foodName);
            System.out.println(macro);
            System.out.println("=================================\n");

        } catch (Exception e) {
            System.err.println("❌ An error occurred: " + e.getMessage());
            e.printStackTrace();
        }


    }
}

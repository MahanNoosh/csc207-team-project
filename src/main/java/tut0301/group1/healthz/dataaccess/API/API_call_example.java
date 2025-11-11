package tut0301.group1.healthz.dataaccess.API;

import org.json.JSONObject;
import tut0301.group1.healthz.entities.nutrition.BasicFood;
import tut0301.group1.healthz.entities.nutrition.Macro;

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
            String foodName = "Apple";

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

            // ===================== 5 Use the token to search for macro by name =====================

            System.out.println("🔎 Searching for food: " + foodName + " ...\n");

            Macro macro = MacroAPI.getMacroByName(token, foodName);

            System.out.println("=== Macro Search Result (by name) ===");
            System.out.println("Food Name: " + foodName);
            if (macro != null) {
                System.out.println("Calories: " + macro.calories() + " kcal");
                System.out.println("Protein: " + macro.proteinG() + "g");
                System.out.println("Fat: " + macro.fatG() + "g");
                System.out.println("Carbs: " + macro.carbsG() + "g");
            } else {
                System.out.println("No macro data found");
            }
            System.out.println("=================================\n");

            // ===================== 6 Use the token to get macro by food ID =====================
            // Extract food_id from the search results list
            if (foodJsonlist != null && !foodJsonlist.isEmpty()) {
                String firstFood = foodJsonlist.get(0);
                System.out.println("📋 First food from search: " + firstFood + "\n");

                // Extract food_id from "12345 - Food Name" format
                String[] parts = firstFood.split(" - ");
                if (parts.length > 0) {
                    try {
                        long foodId = 44911664;
                        //or input foodId

                        System.out.println("🔎 Getting food details by ID: " + foodId + " ...\n");

                        Macro macroById = MacroAPI.getMacroByFoodId(token, foodId);

                        System.out.println("=== Macro Search Result (by ID) ===");
                        System.out.println("Food ID: " + foodId);
                        if (macroById != null) {
                            System.out.println("Calories: " + macroById.calories() + " kcal");
                            System.out.println("Protein: " + macroById.proteinG() + "g");
                            System.out.println("Fat: " + macroById.fatG() + "g");
                            System.out.println("Carbs: " + macroById.carbsG() + "g");
                        } else {
                            System.out.println("No macro data found for ID: " + foodId);
                        }
                        System.out.println("=================================\n");

                    } catch (NumberFormatException e) {
                        System.err.println("⚠️ Could not parse food ID from: " + firstFood);
                    }
                }
            } else {
                System.out.println("⚠️ No food results to extract ID from. Skipping getMacroByFoodId test.\n");
            }


            // ===================== 7 Parse and output BasicFood (Clean Architecture) =====================
            System.out.println("🔎 Creating BasicFood from search result using Mapper...\n");

            try {
                // Use FatSecretFoodMapper to convert JSON to BasicFood entity
                FatSecretFoodMapper mapper = new FatSecretFoodMapper();
                BasicFood basicFood = mapper.fromJson(foodJson, foodName);

                // Display the entity data
                System.out.println("=== BasicFood Details (Clean Architecture) ===");
                System.out.println("Food ID: " + basicFood.getFoodId());
                System.out.println("Food Name: " + basicFood.getFoodName());
                System.out.println("Food Type: " + basicFood.getFoodType());
                System.out.println("Food Description: " + basicFood.getFoodDescription());
                System.out.println("Macro: " + basicFood.getMacro());
                System.out.println("URL: " + basicFood.getFoodUrl());
                System.out.println("===============================================\n");

            } catch (Exception e) {
                System.err.println("❌ Failed to create BasicFood: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("❌ An error occurred: " + e.getMessage());
            e.printStackTrace();
        }


    }
}

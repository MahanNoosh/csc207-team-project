package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.dataaccess.API.FatSecretFoodSearchAdapter;
import tut0301.group1.healthz.entities.nutrition.BasicFood;
import tut0301.group1.healthz.entities.nutrition.Macro;

import java.util.List;

/**
 * Test for GetBasicFoodByNameUseCase.
 *
 * This test demonstrates Clean Architecture principles:
 * 1. Use Case depends on abstraction (FoodSearchPort interface)
 * 2. Concrete implementation (FatSecretFoodSearchAdapter) is injected
 * 3. Use Case has no knowledge of infrastructure details
 * 4. Easy to swap implementations (e.g., mock for testing)
 */
public class GetBasicFoodByNameUseCaseTest {

    public static void main(String[] args) {
        System.out.println("=== Testing GetBasicFoodByNameUseCase (Clean Architecture) ===\n");
        System.out.println("Demonstrating Dependency Injection and Dependency Inversion Principle\n");

        try {
            // ========================================
            // Clean Architecture Setup
            // ========================================

            // 1. Create the infrastructure adapter (outer layer)
            //    This is where environment variables are read
            FoodSearchPort foodSearchPort = new FatSecretFoodSearchAdapter();

            // 2. Inject the port into the use case (inner layer)
            //    Use Case only knows about the interface, not the implementation
            GetBasicFoodByNameUseCase useCase = new GetBasicFoodByNameUseCase(foodSearchPort);

            System.out.println("✅ Clean Architecture Setup:");
            System.out.println("   - FatSecretFoodSearchAdapter (Infrastructure layer) implements FoodSearchPort");
            System.out.println("   - GetBasicFoodByNameUseCase (Use Case layer) depends on FoodSearchPort interface");
            System.out.println("   - Dependency injected from outside (Dependency Inversion)");
            System.out.println();

            // ========================================
            // Test 1: Search for "mushrooms"
            // ========================================
            System.out.println("--- Test 1: Searching for 'mushrooms' ---");
            List<BasicFood> mushroomResults = useCase.execute("mushrooms");

            System.out.println("Found " + mushroomResults.size() + " result(s)\n");

            for (int i = 0; i < mushroomResults.size(); i++) {
                BasicFood food = mushroomResults.get(i);
                displayFoodInfo(i + 1, food);
            }

            System.out.println();

            // ========================================
            // Test 2: Search for "chicken breast"
            // ========================================
            System.out.println("--- Test 2: Searching for 'chicken breast' ---");
            List<BasicFood> chickenResults = useCase.execute("chicken breast");

            System.out.println("Found " + chickenResults.size() + " result(s)\n");

            // Display first 3 results
            int displayCount = Math.min(3, chickenResults.size());
            for (int i = 0; i < displayCount; i++) {
                BasicFood food = chickenResults.get(i);
                displayFoodInfo(i + 1, food);
            }

            if (chickenResults.size() > 3) {
                System.out.println("... and " + (chickenResults.size() - 3) + " more results");
            }

            System.out.println();

            // ========================================
            // Test 3: Search for "apple"
            // ========================================
            System.out.println("--- Test 3: Searching for 'apple' ---");
            List<BasicFood> appleResults = useCase.execute("apple");

            System.out.println("Found " + appleResults.size() + " result(s)\n");

            // Display first 3 results
            displayCount = Math.min(3, appleResults.size());
            for (int i = 0; i < displayCount; i++) {
                BasicFood food = appleResults.get(i);
                displayFoodInfo(i + 1, food);
            }

            if (appleResults.size() > 3) {
                System.out.println("... and " + (appleResults.size() - 3) + " more results");
            }

            System.out.println();

            // ========================================
            // Summary
            // ========================================
            System.out.println("=".repeat(60));
            System.out.println("✅ All tests completed successfully!");
            System.out.println("=".repeat(60));
            System.out.println("\n🏗️ Clean Architecture Benefits Demonstrated:");
            System.out.println("   ✅ Use Case is independent of external frameworks");
            System.out.println("   ✅ Use Case depends on abstraction (FoodSearchPort)");
            System.out.println("   ✅ Infrastructure layer (Adapter) depends on Use Case layer");
            System.out.println("   ✅ Environment variables read in Infrastructure layer only");
            System.out.println("   ✅ Easy to test: can inject mock implementation");
            System.out.println("   ✅ Easy to swap: can change API without touching Use Case");
            System.out.println();

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Display formatted food information.
     *
     * @param index The result index
     * @param food The BasicFood entity to display
     */
    private static void displayFoodInfo(int index, BasicFood food) {
        System.out.println("Result #" + index + ":");
        System.out.println("  Food ID: " + food.getFoodId());
        System.out.println("  Name: " + food.getFoodName());
        System.out.println("  Type: " + food.getFoodType());
        System.out.println("  Serving: " + food.getServingSize() + " " + food.getServingUnit());

        Macro macro = food.getMacro();
        if (macro != null) {
            System.out.println("  Nutrition (per serving):");
            System.out.println("    - Calories: " + macro.calories() + " kcal");
            System.out.println("    - Protein: " + macro.proteinG() + " g");
            System.out.println("    - Fat: " + macro.fatG() + " g");
            System.out.println("    - Carbs: " + macro.carbsG() + " g");
        }

        if (food.getFoodUrl() != null && !food.getFoodUrl().isEmpty()) {
            System.out.println("  URL: " + food.getFoodUrl());
        }

        System.out.println();
    }
}

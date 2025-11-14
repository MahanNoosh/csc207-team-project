package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.dataaccess.API.FatSecretFoodGetClient;
import tut0301.group1.healthz.entities.nutrition.DetailFood;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Simple test for LogFoodIntakeUseCase (Updated with FatSecretFoodGetClient.ServingInfo).
 */
public class LogFoodIntakeUseCaseTest {

    public static void main(String[] args) {
        System.out.println("=== Testing LogFoodIntakeUseCase (Updated) ===\n");

        try {
            // 1. Create a Profile
            Profile profile = new Profile(
                    "user123",
                    70.0,
                    175.0,
                    25,
                    UserDashboardPort.Sex.MALE,
                    UserDashboardPort.Goal.WEIGHT_LOSS,
                    1.4,
                    65.0,
                    Optional.of(2000.0),
                    UserDashboardPort.HealthCondition.NONE
            );
            System.out.println("Created profile: " + profile.getUserId());
            System.out.println();

            // 2. Create a test DetailFood (Apple)
            Macro baseMacro = new Macro(52, 0.3, 0.2, 14);
            DetailFood apple = new DetailFood(
                    1,
                    "Apple",
                    "Fresh apple",
                    "Generic",
                    "http://example.com",
                    baseMacro,
                    100.0,
                    "g",
                    null,  // foodImages
                    null,  // foodAttributes
                    null,  // preferences
                    null   // servings
            );
            System.out.println("Created food: " + apple.getFoodName());
            System.out.println();

            // 3. Create FatSecretFoodGetClient.ServingInfo options
            FatSecretFoodGetClient.ServingInfo serving100g = new FatSecretFoodGetClient.ServingInfo(
                    1001,           // servingId
                    "100 g",        // servingDescription
                    100.0,          // servingAmount
                    "g",            // servingUnit
                    52.0,           // calories
                    0.3,            // protein
                    0.2,            // fat
                    14.0,           // carbs
                    null,           // fiber
                    null,           // sugar
                    null            // sodium
            );

            FatSecretFoodGetClient.ServingInfo servingSmall = new FatSecretFoodGetClient.ServingInfo(
                    1002,
                    "1 small (2-1/2\" dia)",
                    1.0,
                    "small",
                    77.0,           // calories
                    0.39,           // protein
                    0.25,           // fat
                    20.58,          // carbs
                    null, null, null
            );

            System.out.println("Available servings:");
            System.out.println("  1. " + serving100g.servingDescription +
                             " = " + serving100g.calories + " kcal");
            System.out.println("  2. " + servingSmall.servingDescription +
                             " = " + servingSmall.calories + " kcal");
            System.out.println();

            // 4. Use the use case to log food intake
            LogFoodIntakeUseCase useCase = new LogFoodIntakeUseCase();

            // Test 1: Log 1.5x of 100g serving (= 150g)
            System.out.println("--- Test 1: Log 1.5 servings of '100 g' for breakfast ---");
            FoodLog log1 = useCase.execute(profile, apple, serving100g, 1.5, "Breakfast");

            System.out.println("✅ Food logged successfully!");
            System.out.println("  Meal: " + log1.getMeal());
            System.out.println("  Serving: " + log1.getServingDescription());
            System.out.println("  Amount: " + log1.getActualServingSize() + log1.getServingUnit());
            System.out.println("  Calories: " + log1.getActualMacro().calories() + " kcal");
            System.out.println("  Multiplier: " + log1.getServingMultiplier());
            System.out.println();

            // Test 2: Use convenience method with actual amount
            System.out.println("--- Test 2: Log 150g using executeWithAmount() ---");
            FoodLog log2 = useCase.executeWithAmount(profile, apple, serving100g, 150.0, "Lunch");

            System.out.println("✅ Food logged successfully!");
            System.out.println("  Meal: " + log2.getMeal());
            System.out.println("  Amount: " + log2.getActualServingSize() + log2.getServingUnit());
            System.out.println("  Calories: " + log2.getActualMacro().calories() + " kcal");
            System.out.println();

            // Test 3: Log with different serving
            System.out.println("--- Test 3: Log 2 small apples for snack ---");
            FoodLog log3 = useCase.execute(profile, apple, servingSmall, 2.0, "Snack");

            System.out.println("✅ Food logged successfully!");
            System.out.println("  Meal: " + log3.getMeal());
            System.out.println("  Serving: " + log3.getServingDescription());
            System.out.println("  Amount: " + log3.getActualServingSize() + " " + log3.getServingUnit());
            System.out.println("  Calories: " + log3.getActualMacro().calories() + " kcal");
            System.out.println();

            // 5. Verify profile has the logs
            System.out.println("--- Verify profile has the logs ---");
            System.out.println("Total logs in profile: " + profile.getAllFoodLogs().size());
            System.out.println("Logs for today: " + profile.getLogsByDate(LocalDate.now()).size());
            System.out.println();

            // 6. Check daily total
            Macro dailyTotal = profile.getDailyTotalMacro(LocalDate.now());
            System.out.println("--- Daily Total ---");
            System.out.println("Total calories: " + dailyTotal.calories() + " kcal");
            System.out.println("Total protein: " + dailyTotal.proteinG() + " g");
            System.out.println();

            System.out.println("✅ All tests passed!");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

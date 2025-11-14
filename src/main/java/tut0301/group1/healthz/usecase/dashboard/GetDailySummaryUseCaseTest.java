package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.dataaccess.API.FatSecretFoodGetClient;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

/**
 * Simple test for GetDailySummaryUseCase.
 */
public class GetDailySummaryUseCaseTest {

    public static void main(String[] args) {
        System.out.println("=== Testing GetDailySummaryUseCase ===\n");

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
            System.out.println("Target: " + profile.getDailyCalorieTarget().orElse(0.0) + " kcal\n");

            // 2. Create test foods with FatSecretFoodGetClient.FoodDetails and ServingInfo
            FatSecretFoodGetClient.ServingInfo appleServing = new FatSecretFoodGetClient.ServingInfo(
                    1001, "100 g", 100.0, "g",
                    52.0, 0.3, 0.2, 14.0, null, null, null);
            FatSecretFoodGetClient.FoodDetails apple = new FatSecretFoodGetClient.FoodDetails(
                    1, "Apple", "Generic", null, "http://example.com/apple",
                    Collections.singletonList(appleServing));

            FatSecretFoodGetClient.ServingInfo chickenServing = new FatSecretFoodGetClient.ServingInfo(
                    2001, "100 g", 100.0, "g",
                    165.0, 31.0, 3.6, 0.0, null, null, null);
            FatSecretFoodGetClient.FoodDetails chicken = new FatSecretFoodGetClient.FoodDetails(
                    2, "Chicken Breast", "Generic", null, "http://example.com/chicken",
                    Collections.singletonList(chickenServing));

            FatSecretFoodGetClient.ServingInfo riceServing = new FatSecretFoodGetClient.ServingInfo(
                    3001, "100 g", 100.0, "g",
                    130.0, 2.7, 0.3, 28.0, null, null, null);
            FatSecretFoodGetClient.FoodDetails rice = new FatSecretFoodGetClient.FoodDetails(
                    3, "White Rice", "Generic", null, "http://example.com/rice",
                    Collections.singletonList(riceServing));

            // 3. Use LogFoodIntakeUseCase to add some food logs
            LogFoodIntakeUseCase logUseCase = new LogFoodIntakeUseCase();

            System.out.println("--- Logging food intake ---");
            logUseCase.executeWithAmount(profile, apple, appleServing, 150.0, "Breakfast");
            System.out.println("Logged: 150g Apple (Breakfast)");

            logUseCase.executeWithAmount(profile, chicken, chickenServing, 200.0, "Lunch");
            System.out.println("Logged: 200g Chicken (Lunch)");

            logUseCase.executeWithAmount(profile, rice, riceServing, 150.0, "Lunch");
            System.out.println("Logged: 150g Rice (Lunch)");

            logUseCase.executeWithAmount(profile, chicken, chickenServing, 150.0, "Dinner");
            System.out.println("Logged: 150g Chicken (Dinner)");
            System.out.println();

            // 4. Use GetDailySummaryUseCase to get summary
            GetDailySummaryUseCase summaryUseCase = new GetDailySummaryUseCase();
            GetDailySummaryUseCase.DailySummary summary = summaryUseCase.execute(profile, LocalDate.now());

            System.out.println("--- Daily Summary ---");
            System.out.println("Date: " + summary.getDate());
            System.out.println("Total entries: " + summary.getTotalEntries());
            System.out.println("Has logs: " + summary.hasLogs());
            System.out.println();

            // 5. Display detailed log
            System.out.println("--- Food Log Details ---");
            for (FoodLog log : summary.getFoodLogs()) {
                System.out.println(String.format("  [%s] %s: %.1f%s (%.1f kcal)",
                        log.getMeal(),
                        log.getFood().name,
                        log.getActualServingSize(),
                        log.getServingUnit(),
                        log.getActualMacro().calories()));
            }
            System.out.println();

            // 6. Display total macro
            Macro total = summary.getTotalMacro();
            System.out.println("--- Total Macro ---");
            System.out.println("Calories: " + total.calories() + " kcal");
            System.out.println("Protein:  " + total.proteinG() + " g");
            System.out.println("Fat:      " + total.fatG() + " g");
            System.out.println("Carbs:    " + total.carbsG() + " g");
            System.out.println();

            // 7. Display formatted summary string
            System.out.println("--- Formatted Summary ---");
            System.out.println(summary.getSummaryString());
            System.out.println();

            // 8. Compare with target
            double target = profile.getDailyCalorieTarget().orElse(0.0);
            double actual = total.calories();
            double difference = actual - target;

            System.out.println("--- Target Comparison ---");
            System.out.println("Target:     " + target + " kcal");
            System.out.println("Actual:     " + actual + " kcal");
            System.out.println("Difference: " + (difference > 0 ? "+" : "") + difference + " kcal");

            if (difference > 0) {
                System.out.println("⚠️  Over target by " + Math.abs(difference) + " kcal");
            } else if (difference < 0) {
                System.out.println("✅ Under target by " + Math.abs(difference) + " kcal");
            } else {
                System.out.println("✅ Exactly on target!");
            }
            System.out.println();

            System.out.println("✅ All tests passed!");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

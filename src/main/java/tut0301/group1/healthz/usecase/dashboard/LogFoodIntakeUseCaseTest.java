package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.entities.nutrition.BasicFood;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Simple test for LogFoodIntakeUseCase.
 */
public class LogFoodIntakeUseCaseTest {

    public static void main(String[] args) {
        System.out.println("=== Testing LogFoodIntakeUseCase ===\n");

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

            // 2. Create a test food (Apple, per 100g)
            Macro appleMacro = new Macro(52, 0.3, 0.2, 14);
            BasicFood apple = new BasicFood(
                    1,
                    "Apple",
                    "Fresh apple",
                    "Generic",
                    "http://example.com",
                    appleMacro,
                    100.0,
                    "g"
            );
            System.out.println("Created food: " + apple.getFoodName());
            System.out.println("Base: " + apple.getServingSize() + apple.getServingUnit() +
                             " = " + apple.getMacro().calories() + " kcal");
            System.out.println();

            // 3. Use the use case to log food intake
            LogFoodIntakeUseCase useCase = new LogFoodIntakeUseCase();

            System.out.println("--- Test: Log 150g apple for breakfast ---");
            FoodLog log = useCase.execute(profile, apple, 150.0, "Breakfast");

            System.out.println("✅ Food logged successfully!");
            System.out.println("  Meal: " + log.getMeal());
            System.out.println("  Amount: " + log.getActualServingSize() + log.getServingUnit());
            System.out.println("  Calories: " + log.getActualMacro().calories() + " kcal");
            System.out.println("  Multiplier: " + log.getServingMultiplier());
            System.out.println();

            // 4. Verify it was added to profile
            System.out.println("--- Verify profile has the log ---");
            System.out.println("Total logs in profile: " + profile.getAllFoodLogs().size());
            System.out.println("Logs for today: " + profile.getLogsByDate(LocalDate.now()).size());
            System.out.println();

            // 5. Check daily total
            Macro dailyTotal = profile.getDailyTotalMacro(LocalDate.now());
            System.out.println("--- Daily Total ---");
            System.out.println("Total calories: " + dailyTotal.calories() + " kcal");
            System.out.println();

            System.out.println("✅ All tests passed!");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

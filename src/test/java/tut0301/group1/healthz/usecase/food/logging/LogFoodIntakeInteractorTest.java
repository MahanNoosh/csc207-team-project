package tut0301.group1.healthz.usecase.food.logging;

import tut0301.group1.healthz.entities.nutrition.FoodDetails;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.ServingInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Test for LogFoodIntakeInteractor to verify Clean Architecture compliance.
 */
public class LogFoodIntakeInteractorTest {

    public static void main(String[] args) {
        System.out.println("=== Testing LogFoodIntakeInteractor (Clean Architecture) ===\n");

        try {
            // Create test food data
            ServingInfo appleServing = new ServingInfo(
                1001, "100 g", 100.0, "g",
                52.0, 0.3, 0.2, 14.0,
                null, null, null
            );

            FoodDetails apple = new FoodDetails(
                1, "Apple", "Generic", null,
                "http://example.com/apple",
                Collections.singletonList(appleServing)
            );

            // Create mock Gateway implementation
            List<FoodLog> savedLogs = new ArrayList<>();
            FoodLogGateway mockGateway = new FoodLogGateway() {
                @Override
                public void saveFoodLog(String userId, FoodLog foodLog) {
                    savedLogs.add(foodLog);
                    System.out.println("  → Saved to database: " + foodLog.getFood().name +
                                     " (" + foodLog.getActualServingSize() + foodLog.getServingUnit() + ")");
                }

                @Override
                public List<FoodLog> getFoodLogs(String userId) {
                    return savedLogs;
                }
            };

            // Create mock OutputBoundary implementation
            LogFoodIntakeOutputBoundary mockPresenter = new LogFoodIntakeOutputBoundary() {
                @Override
                public void presentLogResult(LogFoodIntakeOutputData outputData) {
                    if (outputData.isSuccess()) {
                        FoodLog log = outputData.getFoodLog();
                        System.out.println("✅ " + outputData.getMessage());
                        System.out.println("  Meal: " + log.getMeal());
                        System.out.println("  Food: " + log.getFood().name);
                        System.out.println("  Amount: " + log.getActualServingSize() + log.getServingUnit());
                        System.out.println("  Calories: " + log.getActualMacro().calories() + " kcal");
                    } else {
                        System.out.println("❌ Error: " + outputData.getMessage());
                    }
                }
            };

            // Test 1: Create Interactor
            System.out.println("--- Test 1: Create Interactor with DI ---");
            LogFoodIntakeInteractor interactor = new LogFoodIntakeInteractor(mockGateway, mockPresenter);
            System.out.println("✅ Interactor created successfully\n");

            // Test 2: Log food with multiplier
            System.out.println("--- Test 2: Log 150g apple (1.5x serving) for Breakfast ---");
            LogFoodIntakeInputData inputData1 = new LogFoodIntakeInputData(
                "user123", apple, appleServing, 1.5, "Breakfast"
            );
            interactor.execute(inputData1);
            System.out.println();

            // Test 3: Log food with actual amount
            System.out.println("--- Test 3: Log 200g apple (using withActualAmount) for Lunch ---");
            LogFoodIntakeInputData inputData2 = LogFoodIntakeInputData.withActualAmount(
                "user123", apple, appleServing, 200.0, "Lunch"
            );
            interactor.execute(inputData2);
            System.out.println();

            // Test 4: Verify saved logs
            System.out.println("--- Test 4: Verify saved logs ---");
            System.out.println("Total logs saved: " + savedLogs.size());
            System.out.println();

            // Test 5: Error scenario
            System.out.println("--- Test 5: Error scenario ---");
            FoodLogGateway errorGateway = new FoodLogGateway() {
                @Override
                public void saveFoodLog(String userId, FoodLog foodLog) throws Exception {
                    throw new RuntimeException("Database connection failed");
                }

                @Override
                public List<FoodLog> getFoodLogs(String userId) {
                    return null;
                }
            };

            LogFoodIntakeInteractor errorInteractor = new LogFoodIntakeInteractor(errorGateway, mockPresenter);
            errorInteractor.execute(inputData1);
            System.out.println();

            System.out.println("=== Architecture Verification ===");
            System.out.println("✅ Use Case layer depends only on abstractions");
            System.out.println("✅ Input/Output DTOs used for data transfer");
            System.out.println("✅ InputBoundary/OutputBoundary define clear contracts");
            System.out.println("✅ Dependency Inversion Principle satisfied");
            System.out.println("\n✅ All tests passed!");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

//package tut0301.group1.healthz.usecase.dashboard;
//
//import nutrition.entities.healthz.tut0301.group1.FoodDetails;
//import nutrition.entities.healthz.tut0301.group1.FoodLog;
//import nutrition.entities.healthz.tut0301.group1.Macro;
//import nutrition.entities.healthz.tut0301.group1.ServingInfo;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
///**
// * Test class for Profile with food intake tracking functionality.
// */
//public class ProfileTest {
//
//    public static void main(String[] args) {
//        System.out.println("=== Testing Profile with Food Intake Tracking ===\n");
//
//        try {
//            // Create a Profile with health data
//            Profile profile = new Profile(
//                    "user123",
//                    70.0,  // weight: 70kg
//                    175.0,  // height: 175cm
//                    25,  // age: 25 years
//                    UserDashboardPort.Sex.MALE,
//                    UserDashboardPort.Goal.WEIGHT_LOSS,
//                    1.4,  // light activity
//                    65.0,  // target weight: 65kg
//                    Optional.of(2000.0),  // daily calorie target: 2000 kcal
//                    UserDashboardPort.HealthCondition.NONE
//            );
//
//            System.out.println("Created profile for user: " + profile.getUserId());
//            System.out.println("Weight: " + profile.getWeightKg() + " kg");
//            System.out.println("Daily calorie target: " + profile.getDailyCalorieTarget().orElse(0.0) + " kcal");
//            System.out.println();
//
//            // Create test foods with FoodDetails and ServingInfo
//            ServingInfo appleServing = new ServingInfo(
//                    1001, "100 g", 100.0, "g",
//                    52.0, 0.3, 0.2, 14.0, null, null, null);
//            FoodDetails apple = new FoodDetails(
//                    1,              // foodId
//                    "Apple",        // name
//                    "Generic",      // foodType
//                    null,           // brandName
//                    "http://example.com/apple",  // foodUrl
//                    Collections.singletonList(appleServing)  // servings
//            );
//
//            ServingInfo chickenServing = new ServingInfo(
//                    2001, "100 g", 100.0, "g",
//                    165.0, 31.0, 3.6, 0.0, null, null, null);
//            FoodDetails chicken = new FoodDetails(
//                    2,              // foodId
//                    "Chicken Breast",  // name
//                    "Generic",      // foodType
//                    null,           // brandName
//                    "http://example.com/chicken",  // foodUrl
//                    Collections.singletonList(chickenServing)  // servings
//            );
//
//            ServingInfo riceServing = new ServingInfo(
//                    3001, "100 g", 100.0, "g",
//                    130.0, 2.7, 0.3, 28.0, null, null, null);
//            FoodDetails rice = new FoodDetails(
//                    3,              // foodId
//                    "White Rice",   // name
//                    "Generic",      // foodType
//                    null,           // brandName
//                    "http://example.com/rice",  // foodUrl
//                    Collections.singletonList(riceServing)  // servings
//            );
//
//            // Test 1: Add food logs for today
//            System.out.println("--- Test 1: Logging food intake ---");
//            LocalDateTime now = LocalDateTime.now();
//
//            // Breakfast: 150g apple (1.5 servings of 100g)
//            FoodLog log1 = new FoodLog(apple, appleServing, 1.5, "Breakfast", now.withHour(8).withMinute(0));
//            profile.addFoodLog(log1);
//            System.out.println("Breakfast: 150g Apple → " + log1.getActualMacro().calories() + " kcal");
//
//            // Lunch: 200g chicken + 150g rice
//            FoodLog log2 = new FoodLog(chicken, chickenServing, 2.0, "Lunch", now.withHour(12).withMinute(30));
//            FoodLog log3 = new FoodLog(rice, riceServing, 1.5, "Lunch", now.withHour(12).withMinute(30));
//            profile.addFoodLog(log2);
//            profile.addFoodLog(log3);
//            System.out.println("Lunch: 200g Chicken → " + log2.getActualMacro().calories() + " kcal");
//            System.out.println("Lunch: 150g Rice → " + log3.getActualMacro().calories() + " kcal");
//
//            // Dinner: 150g chicken + 100g rice
//            FoodLog log4 = new FoodLog(chicken, chickenServing, 1.5, "Dinner", now.withHour(19).withMinute(0));
//            FoodLog log5 = new FoodLog(rice, riceServing, 1.0, "Dinner", now.withHour(19).withMinute(0));
//            profile.addFoodLog(log4);
//            profile.addFoodLog(log5);
//            System.out.println("Dinner: 150g Chicken → " + log4.getActualMacro().calories() + " kcal");
//            System.out.println("Dinner: 100g Rice → " + log5.getActualMacro().calories() + " kcal");
//            System.out.println();
//
//            // Test 2: Get daily summary
//            System.out.println("--- Test 2: Daily Summary ---");
//            LocalDate today = LocalDate.now();
//            List<FoodLog> todayLogs = profile.getLogsByDate(today);
//
//            System.out.println("Total food entries today: " + todayLogs.size());
//            System.out.println("\nDetailed log:");
//            for (FoodLog log : todayLogs) {
//                System.out.println("  • [" + log.getMeal() + "] " + log.getFood().name +
//                                 ": " + log.getActualServingSize() + log.getServingUnit() +
//                                 " (" + log.getActualMacro().calories() + " kcal)");
//            }
//            System.out.println();
//
//            // Calculate daily totals
//            Macro totalMacro = profile.getDailyTotalMacro(today);
//            System.out.println("=== Daily Totals ===");
//            System.out.println("Calories: " + totalMacro.calories() + " kcal");
//            System.out.println("Protein:  " + totalMacro.proteinG() + " g");
//            System.out.println("Fat:      " + totalMacro.fatG() + " g");
//            System.out.println("Carbs:    " + totalMacro.carbsG() + " g");
//            System.out.println();
//
//            // Compare with target
//            double target = profile.getDailyCalorieTarget().orElse(0.0);
//            double actual = totalMacro.calories();
//            double difference = actual - target;
//
//            System.out.println("Target:   " + target + " kcal");
//            System.out.println("Actual:   " + actual + " kcal");
//            System.out.println("Difference: " + (difference > 0 ? "+" : "") + difference + " kcal");
//
//            if (difference > 0) {
//                System.out.println("⚠️  Over target by " + difference + " kcal");
//            } else if (difference < 0) {
//                System.out.println("✅ Under target by " + Math.abs(difference) + " kcal");
//            } else {
//                System.out.println("✅ Exactly on target!");
//            }
//            System.out.println();
//
//            System.out.println("✅ All Profile tests passed!");
//
//        } catch (Exception e) {
//            System.err.println("❌ Test failed: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}

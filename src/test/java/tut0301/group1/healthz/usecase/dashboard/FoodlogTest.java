//package tut0301.group1.healthz.usecase.dashboard;
//
//import tut0301.group1.healthz.entities.nutrition.FoodDetails;
//import tut0301.group1.healthz.entities.nutrition.FoodLog;
//import tut0301.group1.healthz.entities.nutrition.ServingInfo;
//
//import java.time.LocalDateTime;
//
//public class FoodlogTest {
//    public static void main(String[] args) {
//        System.out.println("=== Testing FoodLog Entity (Updated with FoodDetails) ===\n");
//
//        try {
//            // Create a ServingInfo (100g serving)
//            ServingInfo serving100g = new ServingInfo(
//                    1001,           // servingId
//                    "100 g",        // servingDescription
//                    100.0,          // servingAmount
//                    "g",            // servingUnit
//                    52.0,           // calories
//                    0.3,            // protein
//                    0.2,            // fat
//                    14.0,           // carbs
//                    null,           // fiber
//                    null,           // sugar
//                    null            // sodium
//            );
//
//            ServingInfo servingHalfLarge = new ServingInfo(
//                    1002,
//                    "1/2 large (3-1/4\" dia)",
//                    0.5,
//                    "large",
//                    116.0,          // calories
//                    0.6,            // protein
//                    0.4,            // fat
//                    30.8,           // carbs
//                    null, null, null
//            );
//
//            // Create a test FoodDetails (Apple) with multiple servings
//            java.util.List<ServingInfo> servings = java.util.Arrays.asList(
//                    serving100g,
//                    servingHalfLarge
//            );
//
//            FoodDetails apple = new FoodDetails(
//                    1,              // foodId
//                    "Apple",        // name
//                    "Generic",      // foodType
//                    null,           // brandName
//                    "https://example.com/apple",  // foodUrl
//                    servings        // servings
//            );
//
//            System.out.println("Food: " + apple.name);
//            System.out.println("Available servings: " + apple.servings.size());
//            System.out.println("Selected serving: " + serving100g.servingDescription +
//                    " → " + serving100g.calories + " kcal\n");
//
//            // Test 1: Create FoodLog with 1.5x the serving (150g) for Breakfast
//            System.out.println("--- Test 1: Create FoodLog (150g = 1.5 servings, Breakfast) ---");
//            double multiplier = 1.5;
//            FoodLog log = new FoodLog(apple, serving100g, multiplier, "Breakfast", LocalDateTime.now());
//
//            System.out.println("Meal: " + log.getMeal());
//            System.out.println("Serving: " + log.getServingDescription());
//            System.out.println("Actual serving: " + log.getActualServingSize() + log.getServingUnit());
//            System.out.println("Actual macro: " + log.getActualMacro().calories() + " kcal");
//            System.out.println();
//
//            // Test 2: Update serving amount to 250g
//            System.out.println("--- Test 2: Update to 250g ---");
//            System.out.println("Before: " + log.getActualServingSize() + log.getServingUnit() +
//                    " → " + log.getActualMacro().calories() + " kcal");
//
//            log.updateServingAmount(250.0);
//
//            System.out.println("After:  " + log.getActualServingSize() + log.getServingUnit() +
//                    " → " + log.getActualMacro().calories() + " kcal");
//            System.out.println("Multiplier: " + log.getServingMultiplier());
//            System.out.println();
//
//            // Test 3: Create with different serving (1/2 large)
//            System.out.println("--- Test 3: Different serving (1/2 large) ---");
//
//            FoodLog log2 = new FoodLog(apple, servingHalfLarge, 2.0, "Lunch", LocalDateTime.now());
//            System.out.println("Meal: " + log2.getMeal());
//            System.out.println("Serving: " + log2.getServingDescription());
//            System.out.println("Actual: " + log2.getActualServingSize() + " " + log2.getServingUnit() +
//                    " → " + log2.getActualMacro().calories() + " kcal");
//            System.out.println();
//
//            System.out.println("✅ All tests passed!");
//
//        } catch (Exception e) {
//            System.err.println("❌ Test failed: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}

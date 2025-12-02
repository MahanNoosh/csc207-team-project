package tut0301.group1.healthz.entities.nutrition;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Complete test suite for FoodLog with full branch and method coverage.
 * Run directly: java -ea FoodLogTest
 * Or via Maven: mvn test
 */
public class FoodLogAndDetailsTest {

    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        // Test 1: All getters
        try {
            ServingInfo s = new ServingInfo(1001, "100 g", 100.0, "g", 52.0, 0.3, 0.2, 14.0, 2.4, 10.0, 1.0);
            FoodDetails f = new FoodDetails(1, "Apple", "Generic", "USDA", "", Arrays.asList(s));
            LocalDateTime t = LocalDateTime.of(2024, 11, 19, 8, 30);
            FoodLog log = new FoodLog(f, s, 1.5, "Breakfast", t);

            assert log.getFood() == f;
            assert log.getFood().getName().equals("Apple");
            assert log.getServingInfo() == s;
            assert Math.abs(log.getServingMultiplier() - 1.5) < 0.001;
            assert log.getLoggedAt().equals(t);
            assert log.getMeal().equals("Breakfast");
            assert log.getActualMacro() != null;
            assert Math.abs(log.getActualMacro().calories() - 78.0) < 0.001;
            assert Math.abs(log.getActualMacro().proteinG() - 0.45) < 0.001;
            assert Math.abs(log.getActualMacro().fatG() - 0.3) < 0.001;
            assert Math.abs(log.getActualMacro().carbsG() - 21.0) < 0.001;
            assert Math.abs(log.getActualServingSize() - 150.0) < 0.001;
            assert log.getServingUnit().equals("g");
            assert log.getServingDescription().equals("100 g");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 2: Null calories
        try {
            ServingInfo s = new ServingInfo(2001, "1 s", 100.0, "g", null, 0.5, 0.3, 15.0, null, null, null);
            FoodDetails f = new FoodDetails(1, "T", "G", null, "", Arrays.asList(s));
            LocalDateTime t = LocalDateTime.now();
            FoodLog log = new FoodLog(f, s, 2.0, "L", t);
            assert log.getFood() == f;
            assert log.getServingInfo() == s;
            assert Math.abs(log.getServingMultiplier() - 2.0) < 0.001;
            assert log.getLoggedAt().equals(t);
            assert log.getMeal().equals("L");
            assert Math.abs(log.getActualServingSize() - 200.0) < 0.001;
            assert log.getServingUnit().equals("g");
            assert log.getServingDescription().equals("1 s");
            assert Math.abs(log.getActualMacro().calories() - 0.0) < 0.001;
            assert Math.abs(log.getActualMacro().proteinG() - 1.0) < 0.001;
            assert Math.abs(log.getActualMacro().fatG() - 0.6) < 0.001;
            assert Math.abs(log.getActualMacro().carbsG() - 30.0) < 0.001;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 3: Null protein
        try {
            ServingInfo s = new ServingInfo(2002, "1 s", 100.0, "g", 100.0, null, 0.3, 15.0, null, null, null);
            FoodDetails f = new FoodDetails(1, "T", "G", null, "", Arrays.asList(s));
            LocalDateTime t = LocalDateTime.now();
            FoodLog log = new FoodLog(f, s, 2.0, "L", t);
            assert log.getFood() == f;
            assert log.getServingInfo() == s;
            assert Math.abs(log.getServingMultiplier() - 2.0) < 0.001;
            assert log.getLoggedAt().equals(t);
            assert log.getMeal().equals("L");
            assert Math.abs(log.getActualServingSize() - 200.0) < 0.001;
            assert log.getServingUnit().equals("g");
            assert log.getServingDescription().equals("1 s");
            assert Math.abs(log.getActualMacro().calories() - 200.0) < 0.001;
            assert Math.abs(log.getActualMacro().proteinG() - 0.0) < 0.001;
            assert Math.abs(log.getActualMacro().fatG() - 0.6) < 0.001;
            assert Math.abs(log.getActualMacro().carbsG() - 30.0) < 0.001;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 4: Null fat
        try {
            ServingInfo s = new ServingInfo(2003, "1 s", 100.0, "g", 100.0, 0.5, null, 15.0, null, null, null);
            FoodDetails f = new FoodDetails(1, "T", "G", null, "", Arrays.asList(s));
            LocalDateTime t = LocalDateTime.now();
            FoodLog log = new FoodLog(f, s, 2.0, "L", t);
            assert log.getFood() == f;
            assert log.getServingInfo() == s;
            assert Math.abs(log.getServingMultiplier() - 2.0) < 0.001;
            assert log.getLoggedAt().equals(t);
            assert log.getMeal().equals("L");
            assert Math.abs(log.getActualServingSize() - 200.0) < 0.001;
            assert log.getServingUnit().equals("g");
            assert log.getServingDescription().equals("1 s");
            assert Math.abs(log.getActualMacro().calories() - 200.0) < 0.001;
            assert Math.abs(log.getActualMacro().proteinG() - 1.0) < 0.001;
            assert Math.abs(log.getActualMacro().fatG() - 0.0) < 0.001;
            assert Math.abs(log.getActualMacro().carbsG() - 30.0) < 0.001;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 5: Null carbs
        try {
            ServingInfo s = new ServingInfo(2004, "1 s", 100.0, "g", 100.0, 0.5, 0.3, null, null, null, null);
            FoodDetails f = new FoodDetails(1, "T", "G", null, "", Arrays.asList(s));
            LocalDateTime t = LocalDateTime.now();
            FoodLog log = new FoodLog(f, s, 2.0, "L", t);
            assert log.getFood() == f;
            assert log.getServingInfo() == s;
            assert Math.abs(log.getServingMultiplier() - 2.0) < 0.001;
            assert log.getLoggedAt().equals(t);
            assert log.getMeal().equals("L");
            assert Math.abs(log.getActualServingSize() - 200.0) < 0.001;
            assert log.getServingUnit().equals("g");
            assert log.getServingDescription().equals("1 s");
            assert Math.abs(log.getActualMacro().calories() - 200.0) < 0.001;
            assert Math.abs(log.getActualMacro().proteinG() - 1.0) < 0.001;
            assert Math.abs(log.getActualMacro().fatG() - 0.6) < 0.001;
            assert Math.abs(log.getActualMacro().carbsG() - 0.0) < 0.001;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 6: All null nutrition
        try {
            ServingInfo s = new ServingInfo(2005, "1 s", 100.0, "g", null, null, null, null, null, null, null);
            FoodDetails f = new FoodDetails(1, "T", "G", null, "", Arrays.asList(s));
            LocalDateTime t = LocalDateTime.now();
            FoodLog log = new FoodLog(f, s, 2.0, "L", t);
            assert log.getFood() == f;
            assert log.getServingInfo() == s;
            assert Math.abs(log.getServingMultiplier() - 2.0) < 0.001;
            assert log.getLoggedAt().equals(t);
            assert log.getMeal().equals("L");
            assert Math.abs(log.getActualServingSize() - 200.0) < 0.001;
            assert log.getServingUnit().equals("g");
            assert log.getServingDescription().equals("1 s");
            assert Math.abs(log.getActualMacro().calories()) < 0.001;
            assert Math.abs(log.getActualMacro().proteinG()) < 0.001;
            assert Math.abs(log.getActualMacro().fatG()) < 0.001;
            assert Math.abs(log.getActualMacro().carbsG()) < 0.001;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 7: Mixed null nutrition
        try {
            ServingInfo s = new ServingInfo(2006, "1 s", 100.0, "g", 100.0, null, 2.0, null, null, null, null);
            FoodDetails f = new FoodDetails(1, "T", "G", null, "", Arrays.asList(s));
            LocalDateTime t = LocalDateTime.now();
            FoodLog log = new FoodLog(f, s, 3.0, "D", t);
            assert log.getFood() == f;
            assert log.getServingInfo() == s;
            assert Math.abs(log.getServingMultiplier() - 3.0) < 0.001;
            assert log.getLoggedAt().equals(t);
            assert log.getMeal().equals("D");
            assert Math.abs(log.getActualServingSize() - 300.0) < 0.001;
            assert log.getServingUnit().equals("g");
            assert log.getServingDescription().equals("1 s");
            assert Math.abs(log.getActualMacro().calories() - 300.0) < 0.001;
            assert Math.abs(log.getActualMacro().proteinG() - 0.0) < 0.001;
            assert Math.abs(log.getActualMacro().fatG() - 6.0) < 0.001;
            assert Math.abs(log.getActualMacro().carbsG() - 0.0) < 0.001;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 8: Update amount to 150g
        try {
            FoodLog log = createTestLog();
            log.updateServingAmount(150.0);
            assert Math.abs(log.getActualServingSize() - 150.0) < 0.001;
            assert Math.abs(log.getServingMultiplier() - 1.5) < 0.001;
            assert Math.abs(log.getActualMacro().calories() - 78.0) < 0.001;
            assert Math.abs(log.getActualMacro().proteinG() - 0.45) < 0.001;
            assert Math.abs(log.getActualMacro().fatG() - 0.3) < 0.001;
            assert Math.abs(log.getActualMacro().carbsG() - 21.0) < 0.001;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 9: Update amount to 250g
        try {
            FoodLog log = createTestLog();
            log.updateServingAmount(250.0);
            assert Math.abs(log.getActualServingSize() - 250.0) < 0.001;
            assert Math.abs(log.getServingMultiplier() - 2.5) < 0.001;
            assert Math.abs(log.getActualMacro().calories() - 130.0) < 0.001;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 10: Update amount to 50g
        try {
            FoodLog log = createTestLog();
            log.updateServingAmount(50.0);
            assert Math.abs(log.getActualServingSize() - 50.0) < 0.001;
            assert Math.abs(log.getServingMultiplier() - 0.5) < 0.001;
            assert Math.abs(log.getActualMacro().calories() - 26.0) < 0.001;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 11: Multiple amount updates
        try {
            FoodLog log = createTestLog();
            log.updateServingAmount(200.0);
            assert Math.abs(log.getActualMacro().calories() - 104.0) < 0.001;
            log.updateServingAmount(75.0);
            assert Math.abs(log.getActualMacro().calories() - 39.0) < 0.001;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 12: Zero amount throws exception
        try {
            FoodLog log = createTestLog();
            try { log.updateServingAmount(0.0); assert false; }
            catch (IllegalArgumentException e) { }
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 13: Negative amount throws exception
        try {
            FoodLog log = createTestLog();
            try { log.updateServingAmount(-50.0); assert false; }
            catch (IllegalArgumentException e) { }
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 14: Update multiplier to 2.0
        try {
            FoodLog log = createTestLog();
            log.updateServingMultiplier(2.0);
            assert Math.abs(log.getServingMultiplier() - 2.0) < 0.001;
            assert Math.abs(log.getActualMacro().calories() - 104.0) < 0.001;
            assert Math.abs(log.getActualMacro().proteinG() - 0.6) < 0.001;
            assert Math.abs(log.getActualMacro().fatG() - 0.4) < 0.001;
            assert Math.abs(log.getActualMacro().carbsG() - 28.0) < 0.001;
            assert Math.abs(log.getActualServingSize() - 200.0) < 0.001;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 15: Zero multiplier throws exception
        try {
            FoodLog log = createTestLog();
            try { log.updateServingMultiplier(0.0); assert false; }
            catch (IllegalArgumentException e) { }
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 16: Negative multiplier throws exception
        try {
            FoodLog log = createTestLog();
            try { log.updateServingMultiplier(-1.5); assert false; }
            catch (IllegalArgumentException e) { }
            passed++;
        } catch (AssertionError e) { failed++; }

        // === Dedicated Getter Tests ===

        // Test 17: getFood() returns correct FoodDetails
        try {
            ServingInfo s = new ServingInfo(1001, "100 g", 100.0, "g", 52.0, 0.3, 0.2, 14.0, null, null, null);
            FoodDetails f = new FoodDetails(1, "Apple", "Generic", null, "", Arrays.asList(s));
            FoodLog log = new FoodLog(f, s, 1.0, "Breakfast", LocalDateTime.now());

            FoodDetails result = log.getFood();
            assert result == f;
            assert result.getFoodId() == 1;
            assert result.getName().equals("Apple");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 18: getServingInfo() returns correct ServingInfo
        try {
            ServingInfo s = new ServingInfo(1001, "100 g", 100.0, "g", 52.0, 0.3, 0.2, 14.0, null, null, null);
            FoodDetails f = new FoodDetails(1, "Apple", "Generic", null, "", Arrays.asList(s));
            FoodLog log = new FoodLog(f, s, 1.0, "Breakfast", LocalDateTime.now());

            ServingInfo result = log.getServingInfo();
            assert result == s;
            assert result.getServingId() == 1001;
            assert result.getServingDescription().equals("100 g");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 19: getServingMultiplier() returns correct value
        try {
            FoodLog log = createTestLog();
            assert Math.abs(log.getServingMultiplier() - 1.0) < 0.001;

            log.updateServingMultiplier(2.5);
            assert Math.abs(log.getServingMultiplier() - 2.5) < 0.001;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 20: getLoggedAt() returns correct timestamp
        try {
            LocalDateTime t = LocalDateTime.of(2024, 12, 1, 14, 30, 45);
            FoodLog log = new FoodLog(
                createTestLog().getFood(),
                createTestLog().getServingInfo(),
                1.0,
                "Lunch",
                t
            );

            LocalDateTime result = log.getLoggedAt();
            assert result.equals(t);
            assert result.getYear() == 2024;
            assert result.getMonthValue() == 12;
            assert result.getDayOfMonth() == 1;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 21: getMeal() returns correct meal type
        try {
            FoodLog breakfast = new FoodLog(
                createTestLog().getFood(),
                createTestLog().getServingInfo(),
                1.0,
                "Breakfast",
                LocalDateTime.now()
            );
            assert breakfast.getMeal().equals("Breakfast");

            FoodLog lunch = new FoodLog(
                createTestLog().getFood(),
                createTestLog().getServingInfo(),
                1.0,
                "Lunch",
                LocalDateTime.now()
            );
            assert lunch.getMeal().equals("Lunch");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 22: getActualMacro() returns calculated Macro
        try {
            ServingInfo s = new ServingInfo(1001, "100 g", 100.0, "g", 52.0, 0.3, 0.2, 14.0, null, null, null);
            FoodDetails f = new FoodDetails(1, "Apple", "Generic", null, "", Arrays.asList(s));
            FoodLog log = new FoodLog(f, s, 2.0, "Breakfast", LocalDateTime.now());

            Macro result = log.getActualMacro();
            assert result != null;
            assert Math.abs(result.calories() - 104.0) < 0.001;  // 52 * 2
            assert Math.abs(result.proteinG() - 0.6) < 0.001;    // 0.3 * 2
            assert Math.abs(result.fatG() - 0.4) < 0.001;        // 0.2 * 2
            assert Math.abs(result.carbsG() - 28.0) < 0.001;     // 14 * 2
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 23: getActualServingSize() calculates correctly
        try {
            ServingInfo s = new ServingInfo(1001, "100 g", 100.0, "g", 52.0, 0.3, 0.2, 14.0, null, null, null);
            FoodDetails f = new FoodDetails(1, "Apple", "Generic", null, "", Arrays.asList(s));
            FoodLog log = new FoodLog(f, s, 1.5, "Breakfast", LocalDateTime.now());

            double result = log.getActualServingSize();
            assert Math.abs(result - 150.0) < 0.001;  // 100 * 1.5

            log.updateServingMultiplier(2.0);
            assert Math.abs(log.getActualServingSize() - 200.0) < 0.001;  // 100 * 2
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 24: getServingUnit() returns unit from ServingInfo
        try {
            ServingInfo s1 = new ServingInfo(1001, "100 g", 100.0, "g", 52.0, 0.3, 0.2, 14.0, null, null, null);
            FoodLog log1 = new FoodLog(
                new FoodDetails(1, "Apple", "Generic", null, "", Arrays.asList(s1)),
                s1, 1.0, "Breakfast", LocalDateTime.now()
            );
            assert log1.getServingUnit().equals("g");

            ServingInfo s2 = new ServingInfo(1002, "1 cup", 240.0, "ml", 100.0, 5.0, 2.0, 20.0, null, null, null);
            FoodLog log2 = new FoodLog(
                new FoodDetails(2, "Milk", "Generic", null, "", Arrays.asList(s2)),
                s2, 1.0, "Breakfast", LocalDateTime.now()
            );
            assert log2.getServingUnit().equals("ml");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 25: getServingDescription() returns description from ServingInfo
        try {
            ServingInfo s = new ServingInfo(1001, "1/2 large (3-1/4\" dia)", 100.0, "g", 52.0, 0.3, 0.2, 14.0, null, null, null);
            FoodDetails f = new FoodDetails(1, "Apple", "Generic", null, "", Arrays.asList(s));
            FoodLog log = new FoodLog(f, s, 1.0, "Breakfast", LocalDateTime.now());

            String result = log.getServingDescription();
            assert result.equals("1/2 large (3-1/4\" dia)");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 26: ServingInfo.toString() with all fields
        try {
            ServingInfo s = new ServingInfo(1001, "100 g", 100.0, "g", 52.0, 0.3, 0.2, 14.0, 2.4, 10.0, 1.0);
            String str = s.toString();
            assert str.contains("servingId=1001");
            assert str.contains("desc='100 g'");
            assert str.contains("amount=100.0");
            assert str.contains("unit='g'");
            assert str.contains("kcal=52.0");
            assert str.contains("protein=0.3 g");
            assert str.contains("fat=0.2 g");
            assert str.contains("carbs=14.0 g");
            assert str.contains("fiber=2.4 g");
            assert str.contains("sugar=10.0 g");
            assert str.contains("sodium=1.0 mg");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 27: ServingInfo.toString() with null/blank fields
        try {
            ServingInfo s1 = new ServingInfo(2001, "", 50.0, "", null, null, null, null, null, null, null);
            String str1 = s1.toString();
            assert str1.contains("servingId=2001");
            assert str1.contains("amount=50.0");
            assert str1.indexOf("desc=") == -1;
            assert str1.indexOf("unit=") == -1;
            assert str1.indexOf("kcal=") == -1;

            ServingInfo s2 = new ServingInfo(2002, null, 75.0, null, 100.0, 5.0, null, 20.0, null, null, null);
            String str2 = s2.toString();
            assert str2.contains("servingId=2002");
            assert str2.contains("kcal=100.0");
            assert str2.contains("protein=5.0 g");
            assert str2.contains("carbs=20.0 g");
            assert str2.indexOf("fat=") == -1;
            assert str2.indexOf("fiber=") == -1;
            assert str2.indexOf("sugar=") == -1;
            assert str2.indexOf("sodium=") == -1;

            ServingInfo s3 = new ServingInfo(2003, "  ", 60.0, "  ", 50.0, null, 1.0, null, 3.0, null, 5.0);
            String str3 = s3.toString();
            assert str3.contains("servingId=2003");
            assert str3.contains("amount=60.0");
            assert str3.contains("kcal=50.0");
            assert str3.contains("fat=1.0 g");
            assert str3.contains("fiber=3.0 g");
            assert str3.contains("sodium=5.0 mg");
            assert str3.indexOf("desc=") == -1;
            assert str3.indexOf("unit=") == -1;
            assert str3.indexOf("protein=") == -1;
            assert str3.indexOf("carbs=") == -1;
            assert str3.indexOf("sugar=") == -1;
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 28: FoodDetails.toString() with all fields
        try {
            ServingInfo s = new ServingInfo(1001, "100 g", 100.0, "g", 52.0, 0.3, 0.2, 14.0, null, null, null);
            FoodDetails f = new FoodDetails(1, "Apple", "Generic", "USDA", "https://example.com", Arrays.asList(s));
            String str = f.toString();
            assert str.contains("foodId=1");
            assert str.contains("name='Apple'");
            assert str.contains("brand='USDA'");
            assert str.contains("type='Generic'");
            assert str.contains("url='https://example.com'");
            assert str.contains("servings=1");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 29: FoodDetails.toString() with null/blank fields
        try {
            ServingInfo s = new ServingInfo(1001, "100 g", 100.0, "g", 52.0, 0.3, 0.2, 14.0, null, null, null);
            FoodDetails f1 = new FoodDetails(2, "Banana", "", null, "", Arrays.asList(s));
            String str1 = f1.toString();
            assert str1.contains("foodId=2");
            assert str1.contains("name='Banana'");
            assert str1.indexOf("brand=") == -1;
            assert str1.indexOf("type=") == -1;
            assert str1.indexOf("url=") == -1;

            FoodDetails f2 = new FoodDetails(3, "Orange", "Fruit", null, null, Arrays.asList(s, s));
            String str2 = f2.toString();
            assert str2.contains("foodId=3");
            assert str2.contains("name='Orange'");
            assert str2.contains("type='Fruit'");
            assert str2.contains("servings=2");
            assert str2.indexOf("brand=") == -1;
            assert str2.indexOf("url=") == -1;

            FoodDetails f3 = new FoodDetails(4, "Milk", null, "Organic", "  ", Arrays.asList(s));
            String str3 = f3.toString();
            assert str3.contains("foodId=4");
            assert str3.contains("name='Milk'");
            assert str3.contains("brand='Organic'");
            assert str3.contains("servings=1");
            assert str3.indexOf("type=") == -1;
            assert str3.indexOf("url=") == -1;

            FoodDetails f4 = new FoodDetails(5, "Cheese", "  ", "  ", "https://test.com", Arrays.asList(s));
            String str4 = f4.toString();
            assert str4.contains("foodId=5");
            assert str4.contains("name='Cheese'");
            assert str4.contains("url='https://test.com'");
            assert str4.indexOf("brand=") == -1;
            assert str4.indexOf("type=") == -1;
            passed++;
        } catch (AssertionError e) { failed++; }

        System.out.println(failed == 0 ? "✅ All " + passed + " tests passed!" : "❌ " + failed + " tests failed, " + passed + " passed");
        System.exit(failed == 0 ? 0 : 1);
    }

    private static FoodLog createTestLog() {
        ServingInfo s = new ServingInfo(1001, "100 g", 100.0, "g", 52.0, 0.3, 0.2, 14.0, null, null, null);
        FoodDetails f = new FoodDetails(1, "Apple", "Generic", null, "", Arrays.asList(s));
        return new FoodLog(f, s, 1.0, "Breakfast", LocalDateTime.now());
    }
}

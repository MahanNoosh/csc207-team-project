package tutcsc.group1.healthz.entities.nutrition;

/**
 * Complete test suite for BasicFood with full method coverage.
 * Run directly: java -ea BasicFoodTest
 * Or via Maven: mvn test
 */
public class BasicFoodTest {

    // Helper assertion methods (JUnit-style)
    private static void assertEquals(long expected, long actual) {
        assert expected == actual : "Expected: " + expected + ", but got: " + actual;
    }

    private static void assertEquals(double expected, double actual, double delta) {
        assert Math.abs(expected - actual) < delta : "Expected: " + expected + ", but got: " + actual;
    }

    private static void assertEquals(String expected, String actual) {
        assert (expected == null && actual == null) || (expected != null && expected.equals(actual))
            : "Expected: " + expected + ", but got: " + actual;
    }

    private static void assertEquals(Object expected, Object actual) {
        assert expected == actual : "Expected same object reference";
    }

    private static void assertTrue(boolean condition) {
        assert condition : "Expected true, but got false";
    }

    private static void assertFalse(boolean condition) {
        assert !condition : "Expected false, but got true";
    }

    private static void assertNull(Object obj) {
        assert obj == null : "Expected null, but got: " + obj;
    }

    private static void assertNotNull(Object obj) {
        assert obj != null : "Expected non-null object";
    }

    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        // Test 1: Full constructor with all getters
        try {
            Macro m = new Macro(52.0, 0.3, 0.2, 14.0);
            BasicFood food = new BasicFood(1001L, "Apple", "Red apple", "Generic",
                                          "https://example.com/apple", m, 100.0, "g");

            assert food.getFoodId() == 1001L;
            assert food.getFoodName().equals("Apple");
            assert food.getFoodDescription().equals("Red apple");
            assert food.getFoodType().equals("Generic");
            assert food.getFoodUrl().equals("https://example.com/apple");
            assert food.getMacro() == m;
            assert Math.abs(food.getMacro().calories() - 52.0) < 0.001;
            assert Math.abs(food.getMacro().proteinG() - 0.3) < 0.001;
            assert Math.abs(food.getMacro().fatG() - 0.2) < 0.001;
            assert Math.abs(food.getMacro().carbsG() - 14.0) < 0.001;
            assert Math.abs(food.getServingSize() - 100.0) < 0.001;
            assert food.getServingUnit().equals("g");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 2: Convenience constructor with default values
        try {
            Macro m = new Macro(100.0, 5.0, 2.0, 20.0);
            BasicFood food = new BasicFood("Banana", "Yellow banana", "Fruit", m);

            assert food.getFoodId() == 0L;
            assert food.getFoodName().equals("Banana");
            assert food.getFoodDescription().equals("Yellow banana");
            assert food.getFoodType().equals("Fruit");
            assert food.getFoodUrl() == null;
            assert food.getMacro() == m;
            assert Math.abs(food.getServingSize() - 0.0) < 0.001;
            assert food.getServingUnit().equals("g");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 3: Null foodUrl and foodDescription
        try {
            Macro m = new Macro(200.0, 10.0, 5.0, 30.0);
            BasicFood food = new BasicFood(2001L, "Test Food", null, "Brand",
                                          null, m, 150.0, "ml");

            assert food.getFoodId() == 2001L;
            assert food.getFoodName().equals("Test Food");
            assert food.getFoodDescription() == null;
            assert food.getFoodType().equals("Brand");
            assert food.getFoodUrl() == null;
            assert food.getMacro() == m;
            assert Math.abs(food.getMacro().calories() - 200.0) < 0.001;
            assert Math.abs(food.getMacro().proteinG() - 10.0) < 0.001;
            assert Math.abs(food.getMacro().fatG() - 5.0) < 0.001;
            assert Math.abs(food.getMacro().carbsG() - 30.0) < 0.001;
            assert Math.abs(food.getServingSize() - 150.0) < 0.001;
            assert food.getServingUnit().equals("ml");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 4: Empty strings
        try {
            Macro m = new Macro(0.0, 0.0, 0.0, 0.0);
            BasicFood food = new BasicFood(0L, "", "", "", "", m, 0.0, "");

            assert food.getFoodId() == 0L;
            assert food.getFoodName().equals("");
            assert food.getFoodDescription().equals("");
            assert food.getFoodType().equals("");
            assert food.getFoodUrl().equals("");
            assert Math.abs(food.getMacro().calories()) < 0.001;
            assert Math.abs(food.getMacro().proteinG()) < 0.001;
            assert Math.abs(food.getMacro().fatG()) < 0.001;
            assert Math.abs(food.getMacro().carbsG()) < 0.001;
            assert Math.abs(food.getServingSize()) < 0.001;
            assert food.getServingUnit().equals("");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 5: Large values
        try {
            Macro m = new Macro(1000.0, 50.0, 40.0, 100.0);
            BasicFood food = new BasicFood(999999L, "High Calorie Food", "Very caloric",
                                          "Processed", "https://example.com", m, 500.0, "g");

            assert food.getFoodId() == 999999L;
            assert food.getFoodName().equals("High Calorie Food");
            assert food.getFoodDescription().equals("Very caloric");
            assert food.getFoodType().equals("Processed");
            assert food.getFoodUrl().equals("https://example.com");
            assert food.getMacro() == m;
            assert Math.abs(food.getMacro().calories() - 1000.0) < 0.001;
            assert Math.abs(food.getMacro().proteinG() - 50.0) < 0.001;
            assert Math.abs(food.getMacro().fatG() - 40.0) < 0.001;
            assert Math.abs(food.getMacro().carbsG() - 100.0) < 0.001;
            assert Math.abs(food.getServingSize() - 500.0) < 0.001;
            assert food.getServingUnit().equals("g");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 6: Fractional values
        try {
            Macro m = new Macro(52.7, 0.35, 0.18, 14.2);
            BasicFood food = new BasicFood(1L, "Food", "Desc", "Type", "url", m, 100.5, "g");

            assert food.getFoodId() == 1L;
            assert food.getFoodName().equals("Food");
            assert food.getFoodDescription().equals("Desc");
            assert food.getFoodType().equals("Type");
            assert food.getFoodUrl().equals("url");
            assert food.getMacro() == m;
            assert Math.abs(food.getMacro().calories() - 52.7) < 0.001;
            assert Math.abs(food.getMacro().proteinG() - 0.35) < 0.001;
            assert Math.abs(food.getMacro().fatG() - 0.18) < 0.001;
            assert Math.abs(food.getMacro().carbsG() - 14.2) < 0.001;
            assert Math.abs(food.getServingSize() - 100.5) < 0.001;
            assert food.getServingUnit().equals("g");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 7: Different serving units
        try {
            Macro m1 = new Macro(100.0, 5.0, 2.0, 20.0);
            BasicFood food1 = new BasicFood(1L, "F1", "D", "T", "u", m1, 250.0, "ml");
            assert food1.getFoodId() == 1L;
            assert food1.getFoodName().equals("F1");
            assert food1.getFoodDescription().equals("D");
            assert food1.getFoodType().equals("T");
            assert food1.getFoodUrl().equals("u");
            assert food1.getMacro() == m1;
            assert Math.abs(food1.getMacro().calories() - 100.0) < 0.001;
            assert Math.abs(food1.getMacro().proteinG() - 5.0) < 0.001;
            assert Math.abs(food1.getMacro().fatG() - 2.0) < 0.001;
            assert Math.abs(food1.getMacro().carbsG() - 20.0) < 0.001;
            assert Math.abs(food1.getServingSize() - 250.0) < 0.001;
            assert food1.getServingUnit().equals("ml");

            Macro m2 = new Macro(150.0, 7.0, 3.0, 25.0);
            BasicFood food2 = new BasicFood(2L, "F2", "D", "T", "u", m2, 1.0, "cup");
            assert food2.getServingUnit().equals("cup");

            Macro m3 = new Macro(200.0, 10.0, 5.0, 30.0);
            BasicFood food3 = new BasicFood(3L, "F3", "D", "T", "u", m3, 2.0, "tbsp");
            assert food3.getServingUnit().equals("tbsp");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 8: Different food types
        try {
            Macro m = new Macro(100.0, 5.0, 2.0, 20.0);

            BasicFood generic = new BasicFood(1L, "F", "D", "Generic", "u", m, 100.0, "g");
            assert generic.getFoodId() == 1L;
            assert generic.getFoodName().equals("F");
            assert generic.getFoodDescription().equals("D");
            assert generic.getFoodType().equals("Generic");
            assert generic.getFoodUrl().equals("u");
            assert generic.getMacro() == m;
            assert Math.abs(generic.getMacro().calories() - 100.0) < 0.001;
            assert Math.abs(generic.getMacro().proteinG() - 5.0) < 0.001;
            assert Math.abs(generic.getMacro().fatG() - 2.0) < 0.001;
            assert Math.abs(generic.getMacro().carbsG() - 20.0) < 0.001;
            assert Math.abs(generic.getServingSize() - 100.0) < 0.001;
            assert generic.getServingUnit().equals("g");

            BasicFood brand = new BasicFood(2L, "F", "D", "Brand", "u", m, 100.0, "g");
            assert brand.getFoodType().equals("Brand");

            BasicFood recipe = new BasicFood(3L, "F", "D", "Recipe", "u", m, 100.0, "g");
            assert recipe.getFoodType().equals("Recipe");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 9: Negative foodId (edge case)
        try {
            Macro m = new Macro(50.0, 2.0, 1.0, 10.0);
            BasicFood food = new BasicFood(-1L, "Test", "Desc", "Type", null, m, 100.0, "g");

            assert food.getFoodId() == -1L;
            assert food.getFoodName().equals("Test");
            assert food.getFoodDescription().equals("Desc");
            assert food.getFoodType().equals("Type");
            assert food.getFoodUrl() == null;
            assert food.getMacro() == m;
            assert Math.abs(food.getMacro().calories() - 50.0) < 0.001;
            assert Math.abs(food.getMacro().proteinG() - 2.0) < 0.001;
            assert Math.abs(food.getMacro().fatG() - 1.0) < 0.001;
            assert Math.abs(food.getMacro().carbsG() - 10.0) < 0.001;
            assert Math.abs(food.getServingSize() - 100.0) < 0.001;
            assert food.getServingUnit().equals("g");
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 10: Convenience constructor with null values
        try {
            Macro m = new Macro(75.0, 3.0, 1.5, 15.0);
            BasicFood food = new BasicFood(null, null, null, m);

            assert food.getFoodId() == 0L;
            assert food.getFoodName() == null;
            assert food.getFoodDescription() == null;
            assert food.getFoodType() == null;
            assert food.getFoodUrl() == null;
            assert food.getMacro() == m;
            assert Math.abs(food.getMacro().calories() - 75.0) < 0.001;
            assert Math.abs(food.getMacro().proteinG() - 3.0) < 0.001;
            assert Math.abs(food.getMacro().fatG() - 1.5) < 0.001;
            assert Math.abs(food.getMacro().carbsG() - 15.0) < 0.001;
            assert Math.abs(food.getServingSize() - 0.0) < 0.001;
            assert food.getServingUnit().equals("g");
            passed++;
        } catch (AssertionError e) { failed++; }

        // === Dedicated Getter Tests ===

        // Test 11: getFoodId() returns correct ID
        try {
            Macro m = new Macro(100.0, 5.0, 2.0, 20.0);

            BasicFood food1 = new BasicFood(12345L, "Food1", "Desc", "Type", "url", m, 100.0, "g");
            assertEquals(12345L, food1.getFoodId());

            BasicFood food2 = new BasicFood(0L, "Food2", "Desc", "Type", "url", m, 100.0, "g");
            assertEquals(0L, food2.getFoodId());

            BasicFood food3 = new BasicFood(-999L, "Food3", "Desc", "Type", "url", m, 100.0, "g");
            assertEquals(-999L, food3.getFoodId());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 12: getFoodName() returns correct name
        try {
            Macro m = new Macro(100.0, 5.0, 2.0, 20.0);

            BasicFood food1 = new BasicFood(1L, "Apple", "Desc", "Type", "url", m, 100.0, "g");
            assertEquals("Apple", food1.getFoodName());

            BasicFood food2 = new BasicFood(2L, "", "Desc", "Type", "url", m, 100.0, "g");
            assertEquals("", food2.getFoodName());

            BasicFood food3 = new BasicFood(3L, "Very Long Food Name With Spaces", "Desc", "Type", "url", m, 100.0, "g");
            assertEquals("Very Long Food Name With Spaces", food3.getFoodName());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 13: getFoodDescription() returns correct description
        try {
            Macro m = new Macro(100.0, 5.0, 2.0, 20.0);

            BasicFood food1 = new BasicFood(1L, "Name", "Fresh red apple", "Type", "url", m, 100.0, "g");
            assertEquals("Fresh red apple", food1.getFoodDescription());

            BasicFood food2 = new BasicFood(2L, "Name", null, "Type", "url", m, 100.0, "g");
            assertNull(food2.getFoodDescription());

            BasicFood food3 = new BasicFood(3L, "Name", "", "Type", "url", m, 100.0, "g");
            assertEquals("", food3.getFoodDescription());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 14: getFoodType() returns correct type
        try {
            Macro m = new Macro(100.0, 5.0, 2.0, 20.0);

            BasicFood generic = new BasicFood(1L, "Name", "Desc", "Generic", "url", m, 100.0, "g");
            assertEquals("Generic", generic.getFoodType());

            BasicFood brand = new BasicFood(2L, "Name", "Desc", "Brand", "url", m, 100.0, "g");
            assertEquals("Brand", brand.getFoodType());

            BasicFood recipe = new BasicFood(3L, "Name", "Desc", "Recipe", "url", m, 100.0, "g");
            assertEquals("Recipe", recipe.getFoodType());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 15: getFoodUrl() returns correct URL
        try {
            Macro m = new Macro(100.0, 5.0, 2.0, 20.0);

            BasicFood food1 = new BasicFood(1L, "Name", "Desc", "Type", "https://example.com/food/123", m, 100.0, "g");
            assertEquals("https://example.com/food/123", food1.getFoodUrl());

            BasicFood food2 = new BasicFood(2L, "Name", "Desc", "Type", null, m, 100.0, "g");
            assertNull(food2.getFoodUrl());

            BasicFood food3 = new BasicFood(3L, "Name", "Desc", "Type", "", m, 100.0, "g");
            assertEquals("", food3.getFoodUrl());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 16: getMacro() returns correct Macro object
        try {
            Macro m1 = new Macro(52.0, 0.3, 0.2, 14.0);
            BasicFood food1 = new BasicFood(1L, "Apple", "Desc", "Type", "url", m1, 100.0, "g");

            Macro result1 = food1.getMacro();
            assertEquals(m1, result1);
            assertEquals(52.0, result1.calories(), 0.001);
            assertEquals(0.3, result1.proteinG(), 0.001);
            assertEquals(0.2, result1.fatG(), 0.001);
            assertEquals(14.0, result1.carbsG(), 0.001);

            Macro m2 = new Macro(0.0, 0.0, 0.0, 0.0);
            BasicFood food2 = new BasicFood(2L, "Empty", "Desc", "Type", "url", m2, 100.0, "g");
            Macro result2 = food2.getMacro();
            assertEquals(m2, result2);
            assertEquals(0.0, result2.calories(), 0.001);
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 17: getServingSize() returns correct size
        try {
            Macro m = new Macro(100.0, 5.0, 2.0, 20.0);

            BasicFood food1 = new BasicFood(1L, "Name", "Desc", "Type", "url", m, 100.0, "g");
            assertEquals(100.0, food1.getServingSize(), 0.001);

            BasicFood food2 = new BasicFood(2L, "Name", "Desc", "Type", "url", m, 250.5, "g");
            assertEquals(250.5, food2.getServingSize(), 0.001);

            BasicFood food3 = new BasicFood(3L, "Name", "Desc", "Type", "url", m, 0.0, "g");
            assertEquals(0.0, food3.getServingSize(), 0.001);
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 18: getServingUnit() returns correct unit
        try {
            Macro m = new Macro(100.0, 5.0, 2.0, 20.0);

            BasicFood food1 = new BasicFood(1L, "Name", "Desc", "Type", "url", m, 100.0, "g");
            assertEquals("g", food1.getServingUnit());

            BasicFood food2 = new BasicFood(2L, "Name", "Desc", "Type", "url", m, 240.0, "ml");
            assertEquals("ml", food2.getServingUnit());

            BasicFood food3 = new BasicFood(3L, "Name", "Desc", "Type", "url", m, 1.0, "cup");
            assertEquals("cup", food3.getServingUnit());

            BasicFood food4 = new BasicFood(4L, "Name", "Desc", "Type", "url", m, 15.0, "tbsp");
            assertEquals("tbsp", food4.getServingUnit());
            passed++;
        } catch (AssertionError e) { failed++; }

        // === Additional Comprehensive Getter Tests ===

        // Test 19: All getters with convenience constructor
        try {
            Macro m = new Macro(200.0, 10.0, 5.0, 30.0);
            BasicFood food = new BasicFood("Banana", "Yellow banana", "Fruit", m);

            assertEquals(0L, food.getFoodId());
            assertEquals("Banana", food.getFoodName());
            assertEquals("Yellow banana", food.getFoodDescription());
            assertEquals("Fruit", food.getFoodType());
            assertNull(food.getFoodUrl());
            assertNotNull(food.getMacro());
            assertEquals(m, food.getMacro());
            assertEquals(200.0, food.getMacro().calories(), 0.001);
            assertEquals(10.0, food.getMacro().proteinG(), 0.001);
            assertEquals(5.0, food.getMacro().fatG(), 0.001);
            assertEquals(30.0, food.getMacro().carbsG(), 0.001);
            assertEquals(0.0, food.getServingSize(), 0.001);
            assertEquals("g", food.getServingUnit());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 20: Multiple calls to same getter return consistent values
        try {
            Macro m = new Macro(150.0, 7.5, 3.5, 25.0);
            BasicFood food = new BasicFood(999L, "Test Food", "Test Desc", "Test Type", "http://test.com", m, 150.0, "g");

            // Call each getter multiple times
            assertEquals(999L, food.getFoodId());
            assertEquals(999L, food.getFoodId());
            assertEquals(999L, food.getFoodId());

            assertEquals("Test Food", food.getFoodName());
            assertEquals("Test Food", food.getFoodName());

            assertEquals("Test Desc", food.getFoodDescription());
            assertEquals("Test Desc", food.getFoodDescription());

            assertEquals("Test Type", food.getFoodType());
            assertEquals("Test Type", food.getFoodType());

            assertEquals("http://test.com", food.getFoodUrl());
            assertEquals("http://test.com", food.getFoodUrl());

            assertEquals(m, food.getMacro());
            assertEquals(m, food.getMacro());

            assertEquals(150.0, food.getServingSize(), 0.001);
            assertEquals(150.0, food.getServingSize(), 0.001);

            assertEquals("g", food.getServingUnit());
            assertEquals("g", food.getServingUnit());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 21: Getters with extreme values
        try {
            Macro m = new Macro(9999.99, 999.99, 999.99, 9999.99);
            BasicFood food = new BasicFood(Long.MAX_VALUE, "Max", "Maximum values", "Extreme", "url", m, Double.MAX_VALUE, "kg");

            assertEquals(Long.MAX_VALUE, food.getFoodId());
            assertEquals("Max", food.getFoodName());
            assertEquals("Maximum values", food.getFoodDescription());
            assertEquals("Extreme", food.getFoodType());
            assertEquals("url", food.getFoodUrl());
            assertEquals(m, food.getMacro());
            assertEquals(Double.MAX_VALUE, food.getServingSize(), 0.001);
            assertEquals("kg", food.getServingUnit());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 22: Getters with minimum values
        try {
            Macro m = new Macro(0.01, 0.01, 0.01, 0.01);
            BasicFood food = new BasicFood(Long.MIN_VALUE, "Min", "Minimum values", "Tiny", "u", m, 0.01, "mg");

            assertEquals(Long.MIN_VALUE, food.getFoodId());
            assertEquals("Min", food.getFoodName());
            assertEquals("Minimum values", food.getFoodDescription());
            assertEquals("Tiny", food.getFoodType());
            assertEquals("u", food.getFoodUrl());
            assertEquals(m, food.getMacro());
            assertEquals(0.01, food.getServingSize(), 0.001);
            assertEquals("mg", food.getServingUnit());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 23: Getters return immutable values (defensive copies not needed for primitives/immutable objects)
        try {
            Macro m = new Macro(100.0, 5.0, 2.0, 20.0);
            BasicFood food = new BasicFood(1L, "Apple", "Red", "Generic", "url", m, 100.0, "g");

            // Verify getters return the same object references for immutable types
            Macro macro1 = food.getMacro();
            Macro macro2 = food.getMacro();
            assertEquals(macro1, macro2);
            assertTrue(macro1 == macro2); // Same reference

            String name1 = food.getFoodName();
            String name2 = food.getFoodName();
            assertEquals(name1, name2);
            assertTrue(name1 == name2); // Same reference for String

            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 24: Getters with special characters in strings
        try {
            Macro m = new Macro(50.0, 2.5, 1.5, 10.0);
            BasicFood food = new BasicFood(1L, "食物 (Food)", "描述 with émojis 🍎", "Type™", "https://example.com/食物?id=123&test=true", m, 100.0, "g/ml");

            assertEquals("食物 (Food)", food.getFoodName());
            assertEquals("描述 with émojis 🍎", food.getFoodDescription());
            assertEquals("Type™", food.getFoodType());
            assertEquals("https://example.com/食物?id=123&test=true", food.getFoodUrl());
            assertEquals("g/ml", food.getServingUnit());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 25: Verify all getters for a complete real-world example
        try {
            Macro appleMacro = new Macro(52.0, 0.3, 0.2, 14.0);
            BasicFood apple = new BasicFood(
                1001L,
                "Fuji Apple",
                "Fresh organic Fuji apple, medium size",
                "Generic",
                "https://nutritiondata.com/foods/apple/1001",
                appleMacro,
                182.0,
                "g"
            );

            // Verify all getters
            assertEquals(1001L, apple.getFoodId());
            assertEquals("Fuji Apple", apple.getFoodName());
            assertEquals("Fresh organic Fuji apple, medium size", apple.getFoodDescription());
            assertEquals("Generic", apple.getFoodType());
            assertEquals("https://nutritiondata.com/foods/apple/1001", apple.getFoodUrl());
            assertNotNull(apple.getMacro());
            assertEquals(appleMacro, apple.getMacro());
            assertEquals(52.0, apple.getMacro().calories(), 0.001);
            assertEquals(0.3, apple.getMacro().proteinG(), 0.001);
            assertEquals(0.2, apple.getMacro().fatG(), 0.001);
            assertEquals(14.0, apple.getMacro().carbsG(), 0.001);
            assertEquals(182.0, apple.getServingSize(), 0.001);
            assertEquals("g", apple.getServingUnit());
            passed++;
        } catch (AssertionError e) { failed++; }

        System.out.println(failed == 0 ? "✅ All " + passed + " tests passed!" : "❌ " + failed + " tests failed, " + passed + " passed");
        System.exit(failed == 0 ? 0 : 1);
    }
}

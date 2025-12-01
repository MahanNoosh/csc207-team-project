package tut0301.group1.healthz.usecase.food.search;

import tut0301.group1.healthz.entities.nutrition.BasicFood;
import tut0301.group1.healthz.entities.nutrition.Macro;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Unit test for SearchFoodInteractor with Clean Architecture compliance.
 * Run directly: java -ea SearchFoodInteractorTest
 * Or via Maven: mvn test
 */
public class SearchFoodInteractorTest {

    // Helper assertion methods
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

    private static void assertEquals(int expected, int actual) {
        assert expected == actual : "Expected: " + expected + ", but got: " + actual;
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

    // Capturing presenter to verify outputs
    private static class CapturingPresenter implements SearchFoodOutputBoundary {
        private SearchFoodOutputData capturedOutput;
        private int callCount = 0;

        @Override
        public void presentSearchResults(SearchFoodOutputData outputData) {
            this.capturedOutput = outputData;
            this.callCount++;
        }

        public SearchFoodOutputData getCapturedOutput() {
            return capturedOutput;
        }

        public int getCallCount() {
            return callCount;
        }

        public void reset() {
            capturedOutput = null;
            callCount = 0;
        }
    }

    // Capturing gateway to verify search calls
    private static class CapturingGateway implements FoodSearchDataAccessInterface {
        private String capturedSearchTerm;
        private int callCount = 0;
        private List<BasicFood> resultsToReturn;
        private Exception exceptionToThrow;

        public CapturingGateway(List<BasicFood> resultsToReturn) {
            this.resultsToReturn = resultsToReturn;
        }

        public CapturingGateway(Exception exceptionToThrow) {
            this.exceptionToThrow = exceptionToThrow;
        }

        @Override
        public List<BasicFood> searchByName(String foodName) throws IOException, InterruptedException {
            this.capturedSearchTerm = foodName;
            this.callCount++;

            if (exceptionToThrow != null) {
                if (exceptionToThrow instanceof IOException) {
                    throw (IOException) exceptionToThrow;
                } else if (exceptionToThrow instanceof InterruptedException) {
                    throw (InterruptedException) exceptionToThrow;
                }
            }

            return resultsToReturn;
        }

        public String getCapturedSearchTerm() {
            return capturedSearchTerm;
        }

        public int getCallCount() {
            return callCount;
        }
    }

    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        // Test 1: Constructor creates interactor with valid dependencies
        try {
            List<BasicFood> emptyList = Collections.emptyList();
            CapturingGateway gateway = new CapturingGateway(emptyList);
            CapturingPresenter presenter = new CapturingPresenter();

            SearchFoodInteractor interactor = new SearchFoodInteractor(gateway, presenter);
            assertNotNull(interactor);
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 2: Constructor throws exception when gateway is null
        try {
            CapturingPresenter presenter = new CapturingPresenter();
            boolean exceptionThrown = false;

            try {
                new SearchFoodInteractor(null, presenter);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
                assertTrue(e.getMessage().contains("FoodSearchGateway cannot be null"));
            }

            assertTrue(exceptionThrown);
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 3: Constructor throws exception when outputBoundary is null
        try {
            List<BasicFood> emptyList = Collections.emptyList();
            CapturingGateway gateway = new CapturingGateway(emptyList);
            boolean exceptionThrown = false;

            try {
                new SearchFoodInteractor(gateway, null);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
                assertTrue(e.getMessage().contains("SearchFoodOutputBoundary cannot be null"));
            }

            assertTrue(exceptionThrown);
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 4: Execute searches and returns results
        try {
            BasicFood apple = new BasicFood(
                1L, "Apple", "Per 100g - Calories: 52kcal",
                "Generic", "http://example.com/apple",
                new Macro(52.0, 0.3, 0.2, 14.0), 100.0, "g"
            );

            BasicFood applePie = new BasicFood(
                2L, "Apple Pie", "Per 100g - Calories: 237kcal",
                "Generic", "http://example.com/apple-pie",
                new Macro(237.0, 2.0, 11.0, 34.0), 100.0, "g"
            );

            List<BasicFood> searchResults = Arrays.asList(apple, applePie);
            CapturingGateway gateway = new CapturingGateway(searchResults);
            CapturingPresenter presenter = new CapturingPresenter();
            SearchFoodInteractor interactor = new SearchFoodInteractor(gateway, presenter);

            SearchFoodInputData inputData = new SearchFoodInputData("apple");
            interactor.execute(inputData);

            assertEquals(1, gateway.getCallCount());
            assertEquals("apple", gateway.getCapturedSearchTerm());

            assertEquals(1, presenter.getCallCount());
            assertNotNull(presenter.getCapturedOutput());
            assertTrue(presenter.getCapturedOutput().isSuccess());
            assertNull(presenter.getCapturedOutput().getErrorMessage());
            assertNotNull(presenter.getCapturedOutput().getFoods());
            assertEquals(2, presenter.getCapturedOutput().getResultCount());
            assertEquals(searchResults, presenter.getCapturedOutput().getFoods());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 5: Execute with empty results
        try {
            List<BasicFood> emptyResults = Collections.emptyList();
            CapturingGateway gateway = new CapturingGateway(emptyResults);
            CapturingPresenter presenter = new CapturingPresenter();
            SearchFoodInteractor interactor = new SearchFoodInteractor(gateway, presenter);

            interactor.execute(new SearchFoodInputData("nonexistent"));

            assertTrue(presenter.getCapturedOutput().isSuccess());
            assertEquals(0, presenter.getCapturedOutput().getResultCount());
            assertNotNull(presenter.getCapturedOutput().getFoods());
            assertTrue(presenter.getCapturedOutput().getFoods().isEmpty());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 6: Execute with IOException creates error output
        try {
            CapturingGateway gateway = new CapturingGateway(new IOException("Network error"));
            CapturingPresenter presenter = new CapturingPresenter();
            SearchFoodInteractor interactor = new SearchFoodInteractor(gateway, presenter);

            interactor.execute(new SearchFoodInputData("test"));

            assertEquals(1, presenter.getCallCount());
            assertFalse(presenter.getCapturedOutput().isSuccess());
            assertNull(presenter.getCapturedOutput().getFoods());
            assertNotNull(presenter.getCapturedOutput().getErrorMessage());
            assertTrue(presenter.getCapturedOutput().getErrorMessage().contains("Failed to search foods"));
            assertTrue(presenter.getCapturedOutput().getErrorMessage().contains("Network error"));
            assertEquals(0, presenter.getCapturedOutput().getResultCount());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 7: Execute with InterruptedException creates error output
        try {
            CapturingGateway gateway = new CapturingGateway(new InterruptedException("Thread interrupted"));
            CapturingPresenter presenter = new CapturingPresenter();
            SearchFoodInteractor interactor = new SearchFoodInteractor(gateway, presenter);

            interactor.execute(new SearchFoodInputData("banana"));

            assertEquals(1, presenter.getCallCount());
            assertFalse(presenter.getCapturedOutput().isSuccess());
            assertNull(presenter.getCapturedOutput().getFoods());
            assertNotNull(presenter.getCapturedOutput().getErrorMessage());
            assertTrue(presenter.getCapturedOutput().getErrorMessage().contains("Failed to search foods"));
            assertTrue(presenter.getCapturedOutput().getErrorMessage().contains("Thread interrupted"));
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 8: Multiple searches work correctly
        try {
            BasicFood food1 = new BasicFood(1L, "Food1", "Desc1", "Type1", "url1",
                new Macro(100.0, 5.0, 2.0, 20.0), 100.0, "g");
            BasicFood food2 = new BasicFood(2L, "Food2", "Desc2", "Type2", "url2",
                new Macro(200.0, 10.0, 4.0, 40.0), 100.0, "g");

            List<BasicFood> results1 = Arrays.asList(food1);
            List<BasicFood> results2 = Arrays.asList(food2);

            CapturingGateway gateway1 = new CapturingGateway(results1);
            CapturingPresenter presenter = new CapturingPresenter();
            SearchFoodInteractor interactor1 = new SearchFoodInteractor(gateway1, presenter);

            interactor1.execute(new SearchFoodInputData("food1"));
            assertEquals(1, presenter.getCapturedOutput().getResultCount());
            assertEquals("Food1", presenter.getCapturedOutput().getFoods().get(0).getFoodName());

            presenter.reset();
            CapturingGateway gateway2 = new CapturingGateway(results2);
            SearchFoodInteractor interactor2 = new SearchFoodInteractor(gateway2, presenter);

            interactor2.execute(new SearchFoodInputData("food2"));
            assertEquals(1, presenter.getCapturedOutput().getResultCount());
            assertEquals("Food2", presenter.getCapturedOutput().getFoods().get(0).getFoodName());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 9: Verify search term is passed correctly
        try {
            List<BasicFood> results = Collections.emptyList();
            CapturingGateway gateway = new CapturingGateway(results);
            CapturingPresenter presenter = new CapturingPresenter();
            SearchFoodInteractor interactor = new SearchFoodInteractor(gateway, presenter);

            interactor.execute(new SearchFoodInputData("chocolate"));
            assertEquals("chocolate", gateway.getCapturedSearchTerm());

            gateway = new CapturingGateway(results);
            interactor = new SearchFoodInteractor(gateway, presenter);
            interactor.execute(new SearchFoodInputData("banana"));
            assertEquals("banana", gateway.getCapturedSearchTerm());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 10: Verify output contains correct food details
        try {
            BasicFood apple = new BasicFood(
                999L, "Fuji Apple", "Fresh organic apple",
                "Fruit", "http://example.com/fuji",
                new Macro(60.0, 0.4, 0.3, 15.0), 150.0, "g"
            );

            List<BasicFood> results = Arrays.asList(apple);
            CapturingGateway gateway = new CapturingGateway(results);
            CapturingPresenter presenter = new CapturingPresenter();
            SearchFoodInteractor interactor = new SearchFoodInteractor(gateway, presenter);

            interactor.execute(new SearchFoodInputData("fuji"));

            List<BasicFood> outputFoods = presenter.getCapturedOutput().getFoods();
            assertEquals(1, outputFoods.size());

            BasicFood resultFood = outputFoods.get(0);
            assertEquals(999L, resultFood.getFoodId());
            assertEquals("Fuji Apple", resultFood.getFoodName());
            assertEquals("Fresh organic apple", resultFood.getFoodDescription());
            assertEquals("Fruit", resultFood.getFoodType());
            assertEquals("http://example.com/fuji", resultFood.getFoodUrl());
            assertEquals(60.0, resultFood.getMacro().calories(), 0.001);
            assertEquals(0.4, resultFood.getMacro().proteinG(), 0.001);
            assertEquals(0.3, resultFood.getMacro().fatG(), 0.001);
            assertEquals(15.0, resultFood.getMacro().carbsG(), 0.001);
            assertEquals(150.0, resultFood.getServingSize(), 0.001);
            assertEquals("g", resultFood.getServingUnit());
            passed++;
        } catch (AssertionError e) { failed++; }

        System.out.println(failed == 0 ? "✅ All " + passed + " tests passed!" : "❌ " + failed + " tests failed, " + passed + " passed");
        System.exit(failed == 0 ? 0 : 1);
    }
}

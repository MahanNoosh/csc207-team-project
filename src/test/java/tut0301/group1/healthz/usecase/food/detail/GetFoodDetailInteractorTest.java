package tut0301.group1.healthz.usecase.food.detail;

import tut0301.group1.healthz.entities.nutrition.FoodDetails;
import tut0301.group1.healthz.entities.nutrition.ServingInfo;

import java.util.Collections;

/**
 * Unit test for GetFoodDetailInteractor with Clean Architecture compliance.
 * Run directly: java -ea GetFoodDetailInteractorTest
 * Or via Maven: mvn test
 */
public class GetFoodDetailInteractorTest {

    // Helper assertion methods
    private static void assertEquals(long expected, long actual) {
        assert expected == actual : "Expected: " + expected + ", but got: " + actual;
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
    private static class CapturingPresenter implements GetFoodDetailOutputBoundary {
        private GetFoodDetailOutputData capturedOutput;
        private int callCount = 0;

        @Override
        public void presentFoodDetail(GetFoodDetailOutputData outputData) {
            this.capturedOutput = outputData;
            this.callCount++;
        }

        public GetFoodDetailOutputData getCapturedOutput() {
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

    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        // Test 1: Constructor creates interactor with dependencies
        try {
            FoodDetailGateway mockGateway = new FoodDetailGateway() {
                @Override
                public FoodDetails getFoodDetails(long foodId) {
                    return null;
                }
            };
            CapturingPresenter presenter = new CapturingPresenter();

            GetFoodDetailInteractor interactor = new GetFoodDetailInteractor(mockGateway, presenter);
            assertNotNull(interactor);
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 2: Execute with valid foodId calls gateway and presenter
        try {
            ServingInfo serving = new ServingInfo(
                1001, "100 g", 100.0, "g",
                52.0, 0.3, 0.2, 14.0,
                null, null, null
            );

            FoodDetails expectedFood = new FoodDetails(
                12345L,
                "Test Apple",
                "Generic",
                null,
                "http://example.com/apple",
                Collections.singletonList(serving)
            );

            FoodDetailGateway mockGateway = new FoodDetailGateway() {
                @Override
                public FoodDetails getFoodDetails(long foodId) {
                    assertEquals(12345L, foodId);
                    return expectedFood;
                }
            };

            CapturingPresenter presenter = new CapturingPresenter();
            GetFoodDetailInteractor interactor = new GetFoodDetailInteractor(mockGateway, presenter);

            GetFoodDetailInputData inputData = new GetFoodDetailInputData(12345L);
            interactor.execute(inputData);

            assertEquals(1, presenter.getCallCount());
            assertNotNull(presenter.getCapturedOutput());
            assertTrue(presenter.getCapturedOutput().isSuccess());
            assertNull(presenter.getCapturedOutput().getErrorMessage());
            assertNotNull(presenter.getCapturedOutput().getFoodDetails());
            assertEquals(expectedFood, presenter.getCapturedOutput().getFoodDetails());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 3: Execute returns correct food details
        try {
            ServingInfo serving = new ServingInfo(
                1001, "100 g", 100.0, "g",
                52.0, 0.3, 0.2, 14.0,
                2.4, 10.0, 1.0
            );

            FoodDetails testFood = new FoodDetails(
                999L,
                "Banana",
                "Fruit",
                "Organic",
                "http://example.com/banana",
                Collections.singletonList(serving)
            );

            FoodDetailGateway mockGateway = new FoodDetailGateway() {
                @Override
                public FoodDetails getFoodDetails(long foodId) {
                    return testFood;
                }
            };

            CapturingPresenter presenter = new CapturingPresenter();
            GetFoodDetailInteractor interactor = new GetFoodDetailInteractor(mockGateway, presenter);
            interactor.execute(new GetFoodDetailInputData(999L));

            FoodDetails result = presenter.getCapturedOutput().getFoodDetails();
            assertEquals(999L, result.foodId);
            assertEquals("Banana", result.name);
            assertEquals("Fruit", result.foodType);
            assertEquals("Organic", result.brandName);
            assertEquals("http://example.com/banana", result.foodUrl);
            assertEquals(1, result.servings.size());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 4: Execute with gateway exception creates error output
        try {
            FoodDetailGateway errorGateway = new FoodDetailGateway() {
                @Override
                public FoodDetails getFoodDetails(long foodId) throws RuntimeException {
                    throw new RuntimeException("Network error");
                }
            };

            CapturingPresenter presenter = new CapturingPresenter();
            GetFoodDetailInteractor interactor = new GetFoodDetailInteractor(errorGateway, presenter);
            interactor.execute(new GetFoodDetailInputData(99999L));

            assertEquals(1, presenter.getCallCount());
            assertNotNull(presenter.getCapturedOutput());
            assertFalse(presenter.getCapturedOutput().isSuccess());
            assertNull(presenter.getCapturedOutput().getFoodDetails());
            assertNotNull(presenter.getCapturedOutput().getErrorMessage());
            assertTrue(presenter.getCapturedOutput().getErrorMessage().contains("Network error"));
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 5: Execute with different exception message
        try {
            FoodDetailGateway errorGateway = new FoodDetailGateway() {
                @Override
                public FoodDetails getFoodDetails(long foodId) throws RuntimeException {
                    throw new RuntimeException("Food not found");
                }
            };

            CapturingPresenter presenter = new CapturingPresenter();
            GetFoodDetailInteractor interactor = new GetFoodDetailInteractor(errorGateway, presenter);
            interactor.execute(new GetFoodDetailInputData(12345L));

            assertFalse(presenter.getCapturedOutput().isSuccess());
            assertTrue(presenter.getCapturedOutput().getErrorMessage().contains("Food not found"));
            assertTrue(presenter.getCapturedOutput().getErrorMessage().contains("Failed to retrieve food details"));
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 6: Multiple executions work correctly
        try {
            FoodDetails food1 = new FoodDetails(1L, "Food1", "Type1", null, "", Collections.emptyList());
            FoodDetails food2 = new FoodDetails(2L, "Food2", "Type2", null, "", Collections.emptyList());

            FoodDetailGateway mockGateway = new FoodDetailGateway() {
                @Override
                public FoodDetails getFoodDetails(long foodId) {
                    if (foodId == 1L) return food1;
                    if (foodId == 2L) return food2;
                    throw new RuntimeException("Unknown food");
                }
            };

            CapturingPresenter presenter = new CapturingPresenter();
            GetFoodDetailInteractor interactor = new GetFoodDetailInteractor(mockGateway, presenter);

            interactor.execute(new GetFoodDetailInputData(1L));
            assertTrue(presenter.getCapturedOutput().isSuccess());
            assertEquals(food1, presenter.getCapturedOutput().getFoodDetails());

            presenter.reset();
            interactor.execute(new GetFoodDetailInputData(2L));
            assertTrue(presenter.getCapturedOutput().isSuccess());
            assertEquals(food2, presenter.getCapturedOutput().getFoodDetails());

            presenter.reset();
            interactor.execute(new GetFoodDetailInputData(999L));
            assertFalse(presenter.getCapturedOutput().isSuccess());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 7: Presenter is called exactly once per execution
        try {
            FoodDetailGateway mockGateway = new FoodDetailGateway() {
                @Override
                public FoodDetails getFoodDetails(long foodId) {
                    return new FoodDetails(1L, "Test", "Type", null, "", Collections.emptyList());
                }
            };

            CapturingPresenter presenter = new CapturingPresenter();
            GetFoodDetailInteractor interactor = new GetFoodDetailInteractor(mockGateway, presenter);

            assertEquals(0, presenter.getCallCount());
            interactor.execute(new GetFoodDetailInputData(1L));
            assertEquals(1, presenter.getCallCount());
            interactor.execute(new GetFoodDetailInputData(2L));
            assertEquals(2, presenter.getCallCount());
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 8: InputData passes correct foodId to gateway
        try {
            long[] capturedFoodId = new long[1];

            FoodDetailGateway mockGateway = new FoodDetailGateway() {
                @Override
                public FoodDetails getFoodDetails(long foodId) {
                    capturedFoodId[0] = foodId;
                    return new FoodDetails(foodId, "Test", "Type", null, "", Collections.emptyList());
                }
            };

            CapturingPresenter presenter = new CapturingPresenter();
            GetFoodDetailInteractor interactor = new GetFoodDetailInteractor(mockGateway, presenter);

            interactor.execute(new GetFoodDetailInputData(54321L));
            assertEquals(54321L, capturedFoodId[0]);

            interactor.execute(new GetFoodDetailInputData(11111L));
            assertEquals(11111L, capturedFoodId[0]);
            passed++;
        } catch (AssertionError e) { failed++; }

        System.out.println(failed == 0 ? "✅ All " + passed + " tests passed!" : "❌ " + failed + " tests failed, " + passed + " passed");
        System.exit(failed == 0 ? 0 : 1);
    }
}

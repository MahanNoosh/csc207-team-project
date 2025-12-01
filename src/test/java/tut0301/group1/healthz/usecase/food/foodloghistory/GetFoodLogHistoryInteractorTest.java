package tut0301.group1.healthz.usecase.food.foodloghistory;

import tut0301.group1.healthz.entities.nutrition.FoodDetails;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.ServingInfo;
import tut0301.group1.healthz.usecase.food.logging.FoodLogGateway;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unit test for GetFoodLogHistoryInteractor with Clean Architecture compliance.
 * Run directly: java -ea GetFoodLogHistoryInteractorTest
 * Or via Maven: mvn test
 */
public class GetFoodLogHistoryInteractorTest {
    private static void assertEquals(int expected, int actual) {
        assert expected == actual : "Expected: " + expected + ", but got: " + actual;
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

    private static void assertNotNull(Object obj) {
        assert obj != null : "Expected non-null object";
    }

    // Capturing presenter to verify outputs
    private static class CapturingPresenter implements GetFoodLogHistoryOutputBoundary {
        private GetFoodLogHistoryOutputData capturedOutput;
        private String capturedError;
        private int callCount = 0;

        @Override
        public void presentFoodLogHistory(GetFoodLogHistoryOutputData outputData) {
            this.capturedOutput = outputData;
            this.callCount++;
        }

        @Override
        public void presentError(String errorMessage) {
            this.capturedError = errorMessage;
            this.callCount++;
        }

        public GetFoodLogHistoryOutputData getCapturedOutput() {
            return capturedOutput;
        }

        public String getCapturedError() {
            return capturedError;
        }

        public int getCallCount() {
            return callCount;
        }

        public void reset() {
            capturedOutput = null;
            capturedError = null;
            callCount = 0;
        }
    }

    // Capturing gateway to verify queries
    private static class CapturingGateway implements FoodLogGateway {
        private final List<FoodLog> foodLogsToReturn = new ArrayList<>();
        private boolean shouldThrowException = false;
        private String lastQueriedUserId;
        private LocalDate lastQueriedDate;

        @Override
        public void saveFoodLog(String userId, FoodLog foodLog) {
            // Not used in this test
        }

        @Override
        public List<FoodLog> getFoodLogsByDate(String userId, LocalDate date) throws Exception {
            if (shouldThrowException) {
                throw new RuntimeException("Database connection failed");
            }
            this.lastQueriedUserId = userId;
            this.lastQueriedDate = date;
            return new ArrayList<>(foodLogsToReturn);
        }

        public void addFoodLogToReturn(FoodLog foodLog) {
            foodLogsToReturn.add(foodLog);
        }

        public void setShouldThrowException(boolean shouldThrow) {
            this.shouldThrowException = shouldThrow;
        }

        public String getLastQueriedUserId() {
            return lastQueriedUserId;
        }

        public LocalDate getLastQueriedDate() {
            return lastQueriedDate;
        }

        public void reset() {
            foodLogsToReturn.clear();
            shouldThrowException = false;
            lastQueriedUserId = null;
            lastQueriedDate = null;
        }
    }

    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        // Test 1: Constructor throws exception when gateway is null
        try {
            CapturingPresenter presenter = new CapturingPresenter();
            boolean exceptionThrown = false;

            try {
                new GetFoodLogHistoryInteractor(null, presenter);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
                assertTrue(e.getMessage().contains("FoodLogGateway cannot be null"));
            }

            assertTrue(exceptionThrown);
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 2: Constructor throws exception when outputBoundary is null
        try {
            CapturingGateway gateway = new CapturingGateway();
            boolean exceptionThrown = false;

            try {
                new GetFoodLogHistoryInteractor(gateway, null);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
                assertTrue(e.getMessage().contains("OutputBoundary cannot be null"));
            }

            assertTrue(exceptionThrown);
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 3: Execute retrieves food logs successfully
        try {
            CapturingGateway gateway = new CapturingGateway();
            CapturingPresenter presenter = new CapturingPresenter();
            GetFoodLogHistoryInteractor interactor = new GetFoodLogHistoryInteractor(gateway, presenter);

            // Create test food logs
            ServingInfo serving = new ServingInfo(
                1001, "100 g", 100.0, "g",
                52.0, 0.3, 0.2, 14.0,
                null, null, null
            );

            FoodDetails apple = new FoodDetails(
                1, "Apple", "Fruit", null,
                "http://example.com/apple",
                Collections.singletonList(serving)
            );

            FoodLog foodLog1 = new FoodLog(apple, serving, 1.0, "Breakfast", LocalDateTime.now());
            FoodLog foodLog2 = new FoodLog(apple, serving, 1.5, "Lunch", LocalDateTime.now());

            gateway.addFoodLogToReturn(foodLog1);
            gateway.addFoodLogToReturn(foodLog2);

            LocalDate testDate = LocalDate.of(2024, 1, 15);
            GetFoodLogHistoryInputData inputData = new GetFoodLogHistoryInputData("user123", testDate);

            interactor.execute(inputData);

            // Verify gateway was called with correct parameters
            assertEquals("user123", gateway.getLastQueriedUserId());
            assertEquals(testDate, gateway.getLastQueriedDate());

            // Verify presenter received output
            assertEquals(1, presenter.getCallCount());
            assertNotNull(presenter.getCapturedOutput());
            assertEquals(testDate, presenter.getCapturedOutput().getDate());
            assertEquals(2, presenter.getCapturedOutput().getTotalEntries());
            assertTrue(presenter.getCapturedOutput().hasLogs());
            assertEquals(2, presenter.getCapturedOutput().getFoodLogs().size());

            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 4: Execute handles empty food logs
        try {
            CapturingGateway gateway = new CapturingGateway();
            CapturingPresenter presenter = new CapturingPresenter();
            GetFoodLogHistoryInteractor interactor = new GetFoodLogHistoryInteractor(gateway, presenter);

            LocalDate testDate = LocalDate.of(2024, 1, 16);
            GetFoodLogHistoryInputData inputData = new GetFoodLogHistoryInputData("user456", testDate);

            interactor.execute(inputData);

            // Verify output for empty logs
            assertEquals(1, presenter.getCallCount());
            assertNotNull(presenter.getCapturedOutput());
            assertEquals(0, presenter.getCapturedOutput().getTotalEntries());
            assertFalse(presenter.getCapturedOutput().hasLogs());
            assertEquals(0, presenter.getCapturedOutput().getFoodLogs().size());

            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 5: Execute handles gateway exception
        try {
            CapturingGateway gateway = new CapturingGateway();
            gateway.setShouldThrowException(true);
            CapturingPresenter presenter = new CapturingPresenter();
            GetFoodLogHistoryInteractor interactor = new GetFoodLogHistoryInteractor(gateway, presenter);

            LocalDate testDate = LocalDate.of(2024, 1, 17);
            GetFoodLogHistoryInputData inputData = new GetFoodLogHistoryInputData("user789", testDate);

            interactor.execute(inputData);

            // Verify error was presented
            assertEquals(1, presenter.getCallCount());
            assertNotNull(presenter.getCapturedError());
            assertTrue(presenter.getCapturedError().contains("Failed to retrieve food log history"));
            assertTrue(presenter.getCapturedError().contains("Database connection failed"));

            passed++;
        } catch (AssertionError e) { failed++; }

        System.out.println(failed == 0 ? "✅ All " + passed + " tests passed!" : "❌ " + failed + " tests failed, " + passed + " passed");
        System.exit(failed == 0 ? 0 : 1);
    }
}

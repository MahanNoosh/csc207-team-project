package tut0301.group1.healthz.usecase.food.logging;

import tut0301.group1.healthz.entities.nutrition.FoodDetails;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.ServingInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unit test for LogFoodIntakeInteractor with Clean Architecture compliance.
 * Run directly: java -ea LogFoodIntakeInteractorTest
 * Or via Maven: mvn test
 */
public class LogFoodIntakeInteractorTest {
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
    private static class CapturingPresenter implements LogFoodIntakeOutputBoundary {
        private LogFoodIntakeOutputData capturedOutput;
        private int callCount = 0;

        @Override
        public void presentLogResult(LogFoodIntakeOutputData outputData) {
            this.capturedOutput = outputData;
            this.callCount++;
        }

        public LogFoodIntakeOutputData getCapturedOutput() {
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

    // Capturing gateway to verify saves
    private static class CapturingGateway implements FoodLogGateway {
        private final List<FoodLog> savedLogs = new ArrayList<>();
        private final List<String> savedUserIds = new ArrayList<>();
        private boolean shouldThrowException = false;

        @Override
        public void saveFoodLog(String userId, FoodLog foodLog) {
            if (shouldThrowException) {
                throw new RuntimeException("Database connection failed");
            }
            savedLogs.add(foodLog);
            savedUserIds.add(userId);
        }

        @Override
        public List<FoodLog> getFoodLogsByDate(String userId, java.time.LocalDate date) {
            return savedLogs;
        }

        public List<FoodLog> getSavedLogs() {
            return savedLogs;
        }

        public List<String> getSavedUserIds() {
            return savedUserIds;
        }

        public void setShouldThrowException(boolean shouldThrow) {
            this.shouldThrowException = shouldThrow;
        }

        public void reset() {
            savedLogs.clear();
            savedUserIds.clear();
            shouldThrowException = false;
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
                new LogFoodIntakeInteractor(null, presenter);
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
                new LogFoodIntakeInteractor(gateway, null);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
                assertTrue(e.getMessage().contains("LogFoodIntakeOutputBoundary cannot be null"));
            }

            assertTrue(exceptionThrown);
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 3: Execute logs food successfully with multiplier
        try {
            ServingInfo serving = new ServingInfo(
                1001, "100 g", 100.0, "g",
                52.0, 0.3, 0.2, 14.0,
                null, null, null
            );

            FoodDetails apple = new FoodDetails(
                1, "Apple", "Generic", null,
                "http://example.com/apple",
                Collections.singletonList(serving)
            );

            CapturingGateway gateway = new CapturingGateway();
            CapturingPresenter presenter = new CapturingPresenter();
            LogFoodIntakeInteractor interactor = new LogFoodIntakeInteractor(gateway, presenter);

            LogFoodIntakeInputData inputData = new LogFoodIntakeInputData(
                "user123", apple, serving, 1.5, "Breakfast"
            );

            interactor.execute(inputData);

            assertEquals(1, gateway.getSavedLogs().size());
            assertEquals(1, gateway.getSavedUserIds().size());
            assertEquals("user123", gateway.getSavedUserIds().get(0));

            FoodLog savedLog = gateway.getSavedLogs().get(0);
            assertEquals(apple, savedLog.getFood());
            assertEquals(serving, savedLog.getServingInfo());
            assertEquals(1.5, savedLog.getServingMultiplier(), 0.001);
            assertEquals("Breakfast", savedLog.getMeal());
            assertEquals(150.0, savedLog.getActualServingSize(), 0.001);

            assertEquals(1, presenter.getCallCount());
            assertTrue(presenter.getCapturedOutput().isSuccess());
            assertNotNull(presenter.getCapturedOutput().getFoodLog());
            assertTrue(presenter.getCapturedOutput().getMessage().contains("successfully"));
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 4: Execute with gateway exception creates error output
        try {
            ServingInfo serving = new ServingInfo(
                1001, "100 g", 100.0, "g",
                52.0, 0.3, 0.2, 14.0,
                null, null, null
            );

            FoodDetails apple = new FoodDetails(
                1, "Apple", "Generic", null,
                "http://example.com/apple",
                Collections.singletonList(serving)
            );

            CapturingGateway gateway = new CapturingGateway();
            gateway.setShouldThrowException(true);
            CapturingPresenter presenter = new CapturingPresenter();
            LogFoodIntakeInteractor interactor = new LogFoodIntakeInteractor(gateway, presenter);

            LogFoodIntakeInputData inputData = new LogFoodIntakeInputData(
                "user789", apple, serving, 1.0, "Dinner"
            );

            interactor.execute(inputData);

            assertEquals(1, presenter.getCallCount());
            assertFalse(presenter.getCapturedOutput().isSuccess());
            assertNull(presenter.getCapturedOutput().getFoodLog());
            assertNotNull(presenter.getCapturedOutput().getMessage());
            assertTrue(presenter.getCapturedOutput().getMessage().contains("Failed to log food intake"));
            assertTrue(presenter.getCapturedOutput().getMessage().contains("Database connection failed"));
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 5: Verify macro calculations in saved log
        try {
            ServingInfo serving = new ServingInfo(
                1001, "100 g", 100.0, "g",
                100.0, 5.0, 2.0, 20.0,
                null, null, null
            );

            FoodDetails food = new FoodDetails(
                3, "Test Food", "Generic", null, "",
                Collections.singletonList(serving)
            );

            CapturingGateway gateway = new CapturingGateway();
            CapturingPresenter presenter = new CapturingPresenter();
            LogFoodIntakeInteractor interactor = new LogFoodIntakeInteractor(gateway, presenter);

            interactor.execute(new LogFoodIntakeInputData("user1", food, serving, 2.0, "Breakfast"));

            FoodLog savedLog = gateway.getSavedLogs().get(0);
            assertEquals(200.0, savedLog.getActualMacro().calories(), 0.001);
            assertEquals(10.0, savedLog.getActualMacro().proteinG(), 0.001);
            assertEquals(4.0, savedLog.getActualMacro().fatG(), 0.001);
            assertEquals(40.0, savedLog.getActualMacro().carbsG(), 0.001);
            passed++;
        } catch (AssertionError e) { failed++; }

        System.out.println(failed == 0 ? "✅ All " + passed + " tests passed!" : "❌ " + failed + " tests failed, " + passed + " passed");
        System.exit(failed == 0 ? 0 : 1);
    }
}

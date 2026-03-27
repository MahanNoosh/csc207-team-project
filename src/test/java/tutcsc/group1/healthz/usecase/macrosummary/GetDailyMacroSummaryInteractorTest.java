package tutcsc.group1.healthz.usecase.macrosummary;

import tutcsc.group1.healthz.entities.nutrition.FoodDetails;
import tutcsc.group1.healthz.entities.nutrition.FoodLog;
import tutcsc.group1.healthz.entities.nutrition.Macro;
import tutcsc.group1.healthz.entities.nutrition.ServingInfo;
import tutcsc.group1.healthz.use_case.food.logging.FoodLogDataAccessInterface;
import tutcsc.group1.healthz.use_case.macro_summary.GetDailyMacroSummaryInputData;
import tutcsc.group1.healthz.use_case.macro_summary.GetDailyMacroSummaryInteractor;
import tutcsc.group1.healthz.use_case.macro_summary.GetDailyMacroSummaryOutputBoundary;
import tutcsc.group1.healthz.use_case.macro_summary.GetDailyMacroSummaryOutputData;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unit test for GetDailyMacroSummaryInteractor with Clean Architecture compliance.
 */
public class GetDailyMacroSummaryInteractorTest {
    private static void assertEquals(double expected, double actual, double delta) {
        assert Math.abs(expected - actual) < delta : "Expected: " + expected + ", but got: " + actual;
    }

    private static void assertEquals(int expected, int actual) {
        assert expected == actual : "Expected: " + expected + ", but got: " + actual;
    }

    private static void assertEquals(Object expected, Object actual) {
        assert (expected == null && actual == null) || (expected != null && expected.equals(actual))
            : "Expected: " + expected + ", but got: " + actual;
    }

    private static void assertTrue(boolean condition) {
        assert condition : "Expected true, but got false";
    }

    private static void assertNotNull(Object obj) {
        assert obj != null : "Expected non-null object";
    }

    // Capturing presenter
    private static class CapturingPresenter implements GetDailyMacroSummaryOutputBoundary {
        private GetDailyMacroSummaryOutputData capturedOutput;
        private String capturedError;
        private int callCount = 0;

        @Override
        public void presentDailySummary(GetDailyMacroSummaryOutputData outputData) {
            this.capturedOutput = outputData;
            this.callCount++;
        }

        @Override
        public void presentError(String errorMessage) {
            this.capturedError = errorMessage;
            this.callCount++;
        }

        public GetDailyMacroSummaryOutputData getCapturedOutput() {
            return capturedOutput;
        }

        public String getCapturedError() {
            return capturedError;
        }

        public int getCallCount() {
            return callCount;
        }
    }

    // Mock FoodLogGateway
    private static class MockFoodLogGateway implements FoodLogDataAccessInterface {
        private final List<FoodLog> foodLogsToReturn = new ArrayList<>();
        private boolean shouldThrowException = false;

        @Override
        public void saveFoodLog(String userId, FoodLog foodLog) {}

        @Override
        public List<FoodLog> getFoodLogsByDate(String userId, LocalDate date) throws IOException {
            if (shouldThrowException) {
                throw new IOException("Database error");
            }
            return new ArrayList<>(foodLogsToReturn);
        }

        public void addFoodLog(FoodLog foodLog) {
            foodLogsToReturn.add(foodLog);
        }

        public void setShouldThrowException(boolean shouldThrow) {
            this.shouldThrowException = shouldThrow;
        }
    }

    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        // Test 1: Constructor throws exception when foodLogGateway is null
        try {
            CapturingPresenter presenter = new CapturingPresenter();
            boolean exceptionThrown = false;

            try {
                new GetDailyMacroSummaryInteractor(null, presenter);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
                assertTrue(e.getMessage().contains("FoodLogGateway cannot be null"));
            }

            assertTrue(exceptionThrown);
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 2: Constructor throws exception when outputBoundary is null
        try {
            MockFoodLogGateway foodGateway = new MockFoodLogGateway();
            boolean exceptionThrown = false;

            try {
                new GetDailyMacroSummaryInteractor(foodGateway, null);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
                assertTrue(e.getMessage().contains("OutputBoundary cannot be null"));
            }

            assertTrue(exceptionThrown);
            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 3: Execute calculates macro summary with food logs
        try {
            MockFoodLogGateway foodGateway = new MockFoodLogGateway();
            CapturingPresenter presenter = new CapturingPresenter();

            // Add food logs
            ServingInfo serving = new ServingInfo(
                1001, "100 g", 100.0, "g",
                100.0, 5.0, 2.0, 20.0,
                null, null, null
            );
            FoodDetails apple = new FoodDetails(
                1, "Apple", "Fruit", null, "",
                Collections.singletonList(serving)
            );
            FoodLog foodLog1 = new FoodLog(apple, serving, 2.0, "Breakfast", LocalDateTime.now());
            FoodLog foodLog2 = new FoodLog(apple, serving, 1.5, "Lunch", LocalDateTime.now());
            foodGateway.addFoodLog(foodLog1);
            foodGateway.addFoodLog(foodLog2);

            LocalDate testDate = LocalDate.of(2024, 1, 15);
            GetDailyMacroSummaryInteractor interactor = new GetDailyMacroSummaryInteractor(
                foodGateway, presenter
            );

            GetDailyMacroSummaryInputData inputData = new GetDailyMacroSummaryInputData("user123", testDate);
            interactor.execute(inputData);

            // Verify output with all getters
            assertEquals(1, presenter.getCallCount());
            GetDailyMacroSummaryOutputData output = presenter.getCapturedOutput();
            assertNotNull(output);
            assertEquals(testDate, output.getDate());

            // Verify getTotalMacro() and its components
            assertEquals(350.0, output.getTotalMacro().calories(), 0.01);  // 2.0*100 + 1.5*100 = 350
            assertEquals(17.5, output.getTotalMacro().proteinG(), 0.01);   // (2.0 + 1.5) * 5.0 = 17.5
            assertEquals(7.0, output.getTotalMacro().fatG(), 0.01);        // (2.0 + 1.5) * 2.0 = 7.0
            assertEquals(70.0, output.getTotalMacro().carbsG(), 0.01);     // (2.0 + 1.5) * 20.0 = 70.0

            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 4: Execute handles exception from gateway
        try {
            MockFoodLogGateway foodGateway = new MockFoodLogGateway();
            foodGateway.setShouldThrowException(true);
            CapturingPresenter presenter = new CapturingPresenter();

            GetDailyMacroSummaryInteractor interactor = new GetDailyMacroSummaryInteractor(
                foodGateway, presenter
            );

            LocalDate testDate = LocalDate.of(2024, 1, 17);
            GetDailyMacroSummaryInputData inputData = new GetDailyMacroSummaryInputData("user789", testDate);
            interactor.execute(inputData);

            // Verify error was presented
            assertEquals(1, presenter.getCallCount());
            assertNotNull(presenter.getCapturedError());
            assertTrue(presenter.getCapturedError().contains("Failed to retrieve daily summary"));
            assertTrue(presenter.getCapturedError().contains("Database error"));

            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 5: InputData validation
        try {
            LocalDate testDate = LocalDate.of(2024, 1, 20);

            // Test null userId
            boolean exceptionThrown = false;
            try {
                new GetDailyMacroSummaryInputData(null, testDate);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
                assertTrue(e.getMessage().contains("User ID cannot be null or empty"));
            }
            assertTrue(exceptionThrown);

            // Test blank userId
            exceptionThrown = false;
            try {
                new GetDailyMacroSummaryInputData("  ", testDate);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
                assertTrue(e.getMessage().contains("User ID cannot be null or empty"));
            }
            assertTrue(exceptionThrown);

            // Test null date
            exceptionThrown = false;
            try {
                new GetDailyMacroSummaryInputData("user123", null);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
                assertTrue(e.getMessage().contains("Date cannot be null"));
            }
            assertTrue(exceptionThrown);

            // Test valid InputData with getters
            GetDailyMacroSummaryInputData validInput = new GetDailyMacroSummaryInputData("user123", testDate);
            assertEquals("user123", validInput.getUserId());
            assertEquals(testDate, validInput.getDate());

            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 6: OutputData validation
        try {
            LocalDate testDate = LocalDate.of(2024, 1, 21);
            Macro testMacro = new Macro(100.0, 5.0, 2.0, 20.0);

            // Test null date
            boolean exceptionThrown = false;
            try {
                new GetDailyMacroSummaryOutputData(null, testMacro);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
                assertTrue(e.getMessage().contains("Date cannot be null"));
            }
            assertTrue(exceptionThrown);

            // Test null totalMacro
            exceptionThrown = false;
            try {
                new GetDailyMacroSummaryOutputData(testDate, null);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
                assertTrue(e.getMessage().contains("Total macro cannot be null"));
            }
            assertTrue(exceptionThrown);

            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 7: Execute with empty food logs list
        try {
            MockFoodLogGateway foodGateway = new MockFoodLogGateway();
            CapturingPresenter presenter = new CapturingPresenter();

            // Don't add any food logs - gateway will return empty list
            LocalDate testDate = LocalDate.of(2024, 1, 22);
            GetDailyMacroSummaryInteractor interactor = new GetDailyMacroSummaryInteractor(
                foodGateway, presenter
            );

            GetDailyMacroSummaryInputData inputData = new GetDailyMacroSummaryInputData("user456", testDate);
            interactor.execute(inputData);

            // Verify output shows zero macros
            assertEquals(1, presenter.getCallCount());
            GetDailyMacroSummaryOutputData output = presenter.getCapturedOutput();
            assertNotNull(output);
            assertEquals(testDate, output.getDate());
            assertEquals(0.0, output.getTotalMacro().calories(), 0.01);
            assertEquals(0.0, output.getTotalMacro().proteinG(), 0.01);
            assertEquals(0.0, output.getTotalMacro().fatG(), 0.01);
            assertEquals(0.0, output.getTotalMacro().carbsG(), 0.01);

            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 8: Execute with single food log
        try {
            MockFoodLogGateway foodGateway = new MockFoodLogGateway();
            CapturingPresenter presenter = new CapturingPresenter();

            // Add single food log
            ServingInfo serving = new ServingInfo(
                2001, "1 cup", 240.0, "ml",
                150.0, 8.0, 4.0, 12.0,
                2.0, 10.0, 100.0
            );
            FoodDetails milk = new FoodDetails(
                2, "Milk", "Dairy", "Organic Valley", "",
                Collections.singletonList(serving)
            );
            FoodLog foodLog = new FoodLog(milk, serving, 1.0, "Breakfast", LocalDateTime.now());
            foodGateway.addFoodLog(foodLog);

            LocalDate testDate = LocalDate.of(2024, 1, 23);
            GetDailyMacroSummaryInteractor interactor = new GetDailyMacroSummaryInteractor(
                foodGateway, presenter
            );

            GetDailyMacroSummaryInputData inputData = new GetDailyMacroSummaryInputData("user789", testDate);
            interactor.execute(inputData);

            // Verify output matches the single food log
            assertEquals(1, presenter.getCallCount());
            GetDailyMacroSummaryOutputData output = presenter.getCapturedOutput();
            assertNotNull(output);
            assertEquals(testDate, output.getDate());
            assertEquals(150.0, output.getTotalMacro().calories(), 0.01);
            assertEquals(8.0, output.getTotalMacro().proteinG(), 0.01);
            assertEquals(4.0, output.getTotalMacro().fatG(), 0.01);
            assertEquals(12.0, output.getTotalMacro().carbsG(), 0.01);

            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 9: Execute with IOException (different exception path)
        try {
            MockFoodLogGateway foodGateway = new MockFoodLogGateway();
            foodGateway.setShouldThrowException(true);
            CapturingPresenter presenter = new CapturingPresenter();

            GetDailyMacroSummaryInteractor interactor = new GetDailyMacroSummaryInteractor(
                foodGateway, presenter
            );

            LocalDate testDate = LocalDate.of(2024, 1, 24);
            GetDailyMacroSummaryInputData inputData = new GetDailyMacroSummaryInputData("user999", testDate);
            interactor.execute(inputData);

            // Verify error was presented with correct format
            assertEquals(1, presenter.getCallCount());
            String error = presenter.getCapturedError();
            assertNotNull(error);
            assertTrue(error.startsWith("Failed to retrieve daily summary:"));

            passed++;
        } catch (AssertionError e) { failed++; }

        // Test 10: Execute with multiple food logs of different types
        try {
            MockFoodLogGateway foodGateway = new MockFoodLogGateway();
            CapturingPresenter presenter = new CapturingPresenter();

            // Add multiple different food logs
            ServingInfo serving1 = new ServingInfo(
                3001, "1 slice", 30.0, "g",
                80.0, 3.0, 1.0, 15.0,
                1.0, 2.0, 120.0
            );
            FoodDetails bread = new FoodDetails(
                3, "Bread", "Grain", "Wonder", "",
                Collections.singletonList(serving1)
            );

            ServingInfo serving2 = new ServingInfo(
                4001, "1 tbsp", 15.0, "ml",
                50.0, 0.0, 5.0, 0.0,
                null, null, 50.0
            );
            FoodDetails butter = new FoodDetails(
                4, "Butter", "Dairy", null, "",
                Collections.singletonList(serving2)
            );

            FoodLog log1 = new FoodLog(bread, serving1, 2.0, "Breakfast", LocalDateTime.now());
            FoodLog log2 = new FoodLog(butter, serving2, 1.0, "Breakfast", LocalDateTime.now());
            FoodLog log3 = new FoodLog(bread, serving1, 1.0, "Lunch", LocalDateTime.now());

            foodGateway.addFoodLog(log1);
            foodGateway.addFoodLog(log2);
            foodGateway.addFoodLog(log3);

            LocalDate testDate = LocalDate.of(2024, 1, 25);
            GetDailyMacroSummaryInteractor interactor = new GetDailyMacroSummaryInteractor(
                foodGateway, presenter
            );

            GetDailyMacroSummaryInputData inputData = new GetDailyMacroSummaryInputData("user111", testDate);
            interactor.execute(inputData);

            // Verify output aggregates all food logs correctly
            assertEquals(1, presenter.getCallCount());
            GetDailyMacroSummaryOutputData output = presenter.getCapturedOutput();
            assertNotNull(output);
            assertEquals(testDate, output.getDate());

            // 2.0*80 + 1.0*50 + 1.0*80 = 290
            assertEquals(290.0, output.getTotalMacro().calories(), 0.01);
            // 2.0*3.0 + 1.0*0.0 + 1.0*3.0 = 9.0
            assertEquals(9.0, output.getTotalMacro().proteinG(), 0.01);
            // 2.0*1.0 + 1.0*5.0 + 1.0*1.0 = 8.0
            assertEquals(8.0, output.getTotalMacro().fatG(), 0.01);
            // 2.0*15.0 + 1.0*0.0 + 1.0*15.0 = 45.0
            assertEquals(45.0, output.getTotalMacro().carbsG(), 0.01);

            passed++;
        } catch (AssertionError e) { failed++; }

        System.out.println(failed == 0 ? "✅ All " + passed + " tests passed!" : "❌ " + failed + " tests failed, " + passed + " passed");
        System.exit(failed == 0 ? 0 : 1);
    }
}

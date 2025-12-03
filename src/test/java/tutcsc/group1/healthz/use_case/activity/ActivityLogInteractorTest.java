package tutcsc.group1.healthz.use_case.activity;

import tutcsc.group1.healthz.entities.dashboard.*;
import tutcsc.group1.healthz.use_case.activity.activitylog.*;
import tutcsc.group1.healthz.use_case.activity.exercisefinder.*;
import tutcsc.tut0301.group1.usecase.activity.activitylog.*;
import org.junit.jupiter.api.Test;
import tutcsc.tut0301.group1.entities.Dashboard.*;
import tutcsc.tut0301.group1.usecase.activity.exercisefinder.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ActivityLogInteractor.
 * Verifies that activity logs are saved correctly following Clean Architecture principles.
 */
class ActivityLogInteractorTest {

    @Test
    void successTest() throws Exception {
        // --- Arrange ---
        ActivityLogDataAccessInterface dao = new InMemoryActivityLogDataAccessObject();
        ExerciseDataAccessInterface exerciseDAO = new InMemoryExerciseDataAccessObject();

        // Exercise finder dummy interactor
        ExerciseFinderOutputBoundary exercisePresenter = output ->
                assertTrue(output.getNames().contains("Hiking"));
        ExerciseFinderInputBoundary finder =
                new ExerciseFinderInteractor(exerciseDAO, exercisePresenter);
        finder.findExercisesByQuery(new ExerciseInputData("Hik"));

        Profile profile = new Profile(
                "user-1",
                60.0,
                165.0,
                22,
                Sex.FEMALE,
                Goal.WEIGHT_LOSS,
                2.0,
                80.0,
                Optional.of(2000.0),
                HealthCondition.NONE,
                DietPreference.VEGAN
        );

        ActivityLogSaveOutputBoundary presenter = new ActivityLogSaveOutputBoundary() {
            @Override
            public void prepareSuccessView(ActivityLogSaveOutputData data) {
                assertEquals("Hiking", data.getExerciseName());
                assertEquals(60, data.getDurationMinutes());
                assertTrue(data.getCalories() > 0);
                assertNotNull(data.getTimestamp());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Should not fail for valid input: " + error);
            }
        };

        ActivityLogLoadOutputBoundary loadPresenter = new ActivityLogLoadOutputBoundary() {
            @Override
            public void presentActivityLogs(ActivityLogLoadOutputData data) {
                assertEquals(2, data.getLogs().size());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Should not fail for valid input: " + error);
            }
        };

        ActivityLogInputBoundary interactor =
                new ActivityLogInteractor(dao, finder, presenter, loadPresenter);

        // --- Act ---
        ActivityLogInputData input = new ActivityLogInputData("Hiking", 60);
        interactor.logActivity(input, profile);

        // --- Assert ---
        assertEquals(3, ((InMemoryActivityLogDataAccessObject) dao).getActivitiesForUser().size());
    }

    @Test
    void failureTest_invalidDuration() {
        InMemoryActivityLogDataAccessObject dao = new InMemoryActivityLogDataAccessObject();
        Profile profile = new Profile(
                "user-1",
                60.0,
                165.0,
                22,
                Sex.FEMALE,
                Goal.WEIGHT_LOSS,
                2.0,
                80.0,
                Optional.of(2000.0),
                HealthCondition.NONE,
                DietPreference.VEGAN
        );

        ActivityLogSaveOutputBoundary presenter = new ActivityLogSaveOutputBoundary() {
            @Override
            public void prepareSuccessView(ActivityLogSaveOutputData data) {
                fail("Should not succeed with invalid duration");
            }

            @Override
            public void prepareFailView(String error) {
                System.out.println(error);
                assertEquals("Failed to save activity log", error);
            }
        };
        ActivityLogLoadOutputBoundary loadPresenter = new ActivityLogLoadOutputBoundary() {
            @Override
            public void presentActivityLogs(ActivityLogLoadOutputData data) {}
            @Override
            public void prepareFailView(String error) {}
        };

        // Dummy finder not used in failure case
        ExerciseFinderInputBoundary dummyFinder = new ExerciseFinderInputBoundary() {
            @Override public void findExercisesByQuery(ExerciseInputData inputData) {}
            @Override public void findAllExercisesNames() {}

            @Override
            public Exercise findExerciseByName(String exerciseName) throws Exception {
                return null;
            }

            @Override public Exercise findExerciseById(long id) { return new Exercise("Running", id, 8.0); }
        };

        ActivityLogInteractor interactor =
                new ActivityLogInteractor(dao, dummyFinder, presenter,loadPresenter );

        ActivityLogInputData input = new ActivityLogInputData("Hiking", -10);
    }

    // --- In-Memory Fakes for Testing ---

    static class InMemoryActivityLogDataAccessObject implements ActivityLogDataAccessInterface {
        private final List<ActivityEntry> logs = new ArrayList<>(
                List.of(
                        new ActivityEntry(2, 10, 41, LocalDateTime.now()),
                        new ActivityEntry(1, 32, 105, LocalDateTime.now())
                )
        );

        @Override
        public void saveActivityLog(ActivityEntry entry, Profile profile) {
            logs.add(entry);
        }

        @Override
        public List<ActivityEntry> getActivitiesForUser() {
            return logs;
        }

        @Override
        public Map<java.time.DayOfWeek, Double> getWeeklyActivitySummary() {
            return Map.of();
        }
    }

    static class InMemoryExerciseDataAccessObject implements ExerciseDataAccessInterface {
        @Override public Exercise fetchExerciseByExactName(String name) { return new Exercise(name, 1, 6.0); }
        @Override public Exercise fetchExerciseByExactId(long id) { return new Exercise("Hiking", id, 6.0); }
        @Override public List<String> fetchAllExercisesNames() { return List.of("Hiking", "Badminton", "Walking"); }
        @Override public List<String> searchExercisesByQuery(String query) { return List.of("Hiking"); }
    }
}

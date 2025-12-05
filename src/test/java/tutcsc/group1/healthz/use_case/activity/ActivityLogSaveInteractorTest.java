package tutcsc.group1.healthz.use_case.activity;


import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import tutcsc.group1.healthz.entities.dashboard.*;
import tutcsc.group1.healthz.use_case.activity.activity_log.*;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseFinderInputBoundary;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseInputData;

import static org.testng.AssertJUnit.*;


class ActivityLogSaveInteractorTest {

    /**
     * A simple in-memory DAO for testing.
     */
    private static final class InMemoryActivityLogDataAccessObject implements ActivityLogDataAccessInterface {
        private final List<ActivityEntry> saved = new ArrayList<>();

        @Override
        public void saveActivityLog(ActivityEntry entry, Profile profile) {
            saved.add(entry);
        }

        @Override
        public List<ActivityEntry> getActivitiesForUser() throws Exception {
            return List.of();
        }

        @Override
        public Map<DayOfWeek, Double> getWeeklyActivitySummary() throws Exception {
            return Map.of();
        }
    }

    /**
     * Simple exercise finder — always returns a fixed Exercise.
     */
    private static class StubExerciseFinder implements ExerciseFinderInputBoundary {
        @Override
        public Exercise findExerciseByName(String name) {
            return new Exercise("Running", 10, 8.0);
        }

        @Override
        public Exercise findExerciseById(long id) throws Exception {
            return null;
        }

        @Override
        public void findAllExercisesNames() {

        }

        @Override
        public void searchExercisesByQuery(ExerciseInputData inputData) {

        }
    }

    @Test
    void successTest() throws Exception {
        final InMemoryActivityLogDataAccessObject dao = new InMemoryActivityLogDataAccessObject();
        final StubExerciseFinder finder = new StubExerciseFinder();
       final Profile profile = new Profile(
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
        ); // 60 kg user

        ActivityLogInputData input = new ActivityLogInputData("Running", 30);

        ActivityLogSaveOutputBoundary successPresenter = new ActivityLogSaveOutputBoundary() {
            @Override
            public void prepareSuccessView(ActivityLogSaveOutputData output) {
                assertEquals("Running", output.getExerciseName());
                assertEquals(30, output.getDurationMinutes());
                assertTrue(output.getCalories() > 0);
                assertEquals(1, dao.saved.size());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Unexpected failure: " + error);
            }
        };

        final ActivityLogSaveInteractor interactor =
                new ActivityLogSaveInteractor(dao, finder, successPresenter);

        interactor.execute(input, profile);
    }

    @Test
    void failureNullInputTest() throws Exception {
        final InMemoryActivityLogDataAccessObject dao = new InMemoryActivityLogDataAccessObject();
        final StubExerciseFinder finder = new StubExerciseFinder();
        final Profile profile = new Profile(
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
        final ActivityLogSaveOutputBoundary failurePresenter = new ActivityLogSaveOutputBoundary() {
            @Override
            public void prepareSuccessView(ActivityLogSaveOutputData output) {
                fail("Success is not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Input data or profile is null.", error);
            }
        };

        final ActivityLogSaveInteractor interactor =
                new ActivityLogSaveInteractor(dao, finder, failurePresenter);

        interactor.execute(null, profile);
    }

    @Test
    void failureInvalidDuration() throws Exception {
        final InMemoryActivityLogDataAccessObject dao = new InMemoryActivityLogDataAccessObject();
        final StubExerciseFinder finder = new StubExerciseFinder();
        final Profile profile = new Profile(
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

        final ActivityLogInputData input = new ActivityLogInputData("Running", 0);

        final ActivityLogSaveOutputBoundary failurePresenter = new ActivityLogSaveOutputBoundary() {
            @Override
            public void prepareSuccessView(ActivityLogSaveOutputData output) {
                fail("Should not succeed with invalid duration.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Duration must be greater than 0.", error);
            }
        };

        final ActivityLogSaveInteractor interactor =
                new ActivityLogSaveInteractor(dao, finder, failurePresenter);

        interactor.execute(input, profile);
    }
}

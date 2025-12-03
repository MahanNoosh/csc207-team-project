package tutcsc.group1.healthz.use_case.activity;

import org.junit.jupiter.api.Test;
import tutcsc.group1.healthz.entities.dashboard.*;
import tutcsc.group1.healthz.use_case.activity.activity_log.*;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseFinderInputBoundary;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseInputData;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ActivityLogSaveInteractorTest {

    /**
     * A simple in-memory DAO for testing.
     */
    private static class InMemoryActivityLogDAO implements ActivityLogDataAccessInterface {
        List<ActivityEntry> saved = new ArrayList<>();

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
            return new Exercise("Running", 10, 8.0); // id=10, MET=8
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
        InMemoryActivityLogDAO dao = new InMemoryActivityLogDAO();
        StubExerciseFinder finder = new StubExerciseFinder();
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

        ActivityLogSaveInteractor interactor =
                new ActivityLogSaveInteractor(dao, finder, successPresenter);

        interactor.execute(input, profile);
    }

    @Test
    void failureNullInputTest() throws Exception {
        InMemoryActivityLogDAO dao = new InMemoryActivityLogDAO();
        StubExerciseFinder finder = new StubExerciseFinder();
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
        ActivityLogSaveOutputBoundary failurePresenter = new ActivityLogSaveOutputBoundary() {
            @Override
            public void prepareSuccessView(ActivityLogSaveOutputData output) {
                fail("Success is not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Input data or profile is null.", error);
            }
        };

        ActivityLogSaveInteractor interactor =
                new ActivityLogSaveInteractor(dao, finder, failurePresenter);

        interactor.execute(null, profile);
    }

    @Test
    void failureInvalidDuration() throws Exception {
        InMemoryActivityLogDAO dao = new InMemoryActivityLogDAO();
        StubExerciseFinder finder = new StubExerciseFinder();
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

        ActivityLogInputData input = new ActivityLogInputData("Running", 0);

        ActivityLogSaveOutputBoundary failurePresenter = new ActivityLogSaveOutputBoundary() {
            @Override
            public void prepareSuccessView(ActivityLogSaveOutputData output) {
                fail("Should not succeed with invalid duration.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Duration must be greater than 0.", error);
            }
        };

        ActivityLogSaveInteractor interactor =
                new ActivityLogSaveInteractor(dao, finder, failurePresenter);

        interactor.execute(input, profile);
    }
}

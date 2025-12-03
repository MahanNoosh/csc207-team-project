package tutcsc.group1.healthz.use_case.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;
import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogDataAccessInterface;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogLoadInteractor;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogLoadOutputBoundary;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogLoadOutputData;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ActivityLogLoadInteractor}.
 * Ensures correct handling of success, empty results,
 * and failure scenarios when loading activity logs.
 */
public class ActivityLogLoadTest {

    /** Fake presenter used to capture output during tests. */
    private FakePresenter presenter;

    /**
     * Sets up a new presenter before each test.
     */
    @BeforeEach
    public void setUp() {
        presenter = new FakePresenter();
    }

    /**
     * A fake DAO that returns a predefined list of activity logs.
     */
    private static final class FakeSuccessDao
            implements ActivityLogDataAccessInterface {

        /** Stored logs to return. */
        private final List<ActivityEntry> storedLogs;

        /**
         * Constructs a fake DAO that returns provided logs.
         *
         * @param logs the logs that will be returned
         */
        FakeSuccessDao(final List<ActivityEntry> logs) {
            this.storedLogs = logs;
        }

        @Override
        public void saveActivityLog(ActivityEntry entry, Profile profile) throws Exception {

        }

        /**
         * Returns the stored logs.
         *
         * @return list of activity entries
         */
        @Override
        public List<ActivityEntry> getActivitiesForUser() {
            return storedLogs;
        }

        @Override
        public Map<DayOfWeek, Double> getWeeklyActivitySummary() throws Exception {
            return Map.of();
        }
    }

    /**
     * A fake DAO that always throws an exception
     * to simulate data access failure.
     */
    private static final class FakeFailDao
            implements ActivityLogDataAccessInterface {

        @Override
        public void saveActivityLog(ActivityEntry entry, Profile profile) throws Exception {

        }

        /**
         * Always throws an exception.
         *
         * @return nothing; always throws
         * @throws Exception always thrown
         */
        @Override
        public List<ActivityEntry> getActivitiesForUser() throws Exception {
            throw new Exception("Database read error");
        }

        @Override
        public Map<DayOfWeek, Double> getWeeklyActivitySummary() throws Exception {
            return Map.of();
        }
    }

    /**
     * A fake presenter that stores success or failure outputs.
     */
    private static final class FakePresenter
            implements ActivityLogLoadOutputBoundary {

        /** The success data received, if any. */
        private ActivityLogLoadOutputData successData;

        /** The error message received, if any. */
        private String errorMessage;


        /**
         * Captures success output data.
         *
         * @param outputData the data to store
         */
        @Override
        public void presentActivityLogs(
                final ActivityLogLoadOutputData outputData) throws Exception {
            this.successData = outputData;
        }

        /**
         * Captures a failure message.
         *
         * @param error the failure message received
         */
        @Override
        public void prepareFailView(final String error) {
            this.errorMessage = error;
        }

    }

    /**
     * Tests that a single activity entry loads successfully.
     *
     * @throws Exception if load unexpectedly fails
     */
    @Test
    public void testLoadSuccessWithOneEntry() throws Exception {
        final ActivityEntry entry = new ActivityEntry(
                1,
                45,
                300.0,
                LocalDateTime.now()
        );
        final List<ActivityEntry> entries = List.of(entry);

        final ActivityLogLoadInteractor interactor =
                new ActivityLogLoadInteractor(
                        new FakeSuccessDao(entries), presenter);

        interactor.execute();

        assertNotNull(
                presenter.successData,
                "Success data should not be null."
        );
        assertEquals(
                1,
                presenter.successData.getLogs().size(),
                "Exactly one activity should be loaded."
        );
        assertNull(
                presenter.errorMessage,
                "No error message expected."
        );
    }

    /**
     * Tests loading when the DAO returns an empty list.
     *
     * @throws Exception if load unexpectedly fails
     */
    @Test
    public void testLoadSuccessWithEmptyList() throws Exception {
        final List<ActivityEntry> emptyList = new ArrayList<>();

        final ActivityLogLoadInteractor interactor =
                new ActivityLogLoadInteractor(
                        new FakeSuccessDao(emptyList), presenter);

        interactor.execute();

        assertNotNull(
                presenter.successData,
                "Presenter should receive success output."
        );
        assertTrue(
                presenter.successData.getLogs().isEmpty(),
                "Activity list should be empty."
        );
        assertNull(
                presenter.errorMessage,
                "No error message expected."
        );
    }

    /**
     * Tests that an exception thrown by the DAO is propagated
     * and that the presenter does not receive success output.
     */
    @Test
    public void testLoadFailureThrowsException() {
        final ActivityLogLoadInteractor interactor =
                new ActivityLogLoadInteractor(
                        new FakeFailDao(), presenter);

        final Exception thrown =
                assertThrows(Exception.class, interactor::execute);

        assertEquals(
                "Database read error",
                thrown.getMessage(),
                "Exception message should match expected value."
        );
        assertNull(
                presenter.successData,
                "Presenter success output must not be called on failure."
        );
    }

    /**
     * Tests that presenter failure handling works independently.
     */
    @Test
    public void testPresenterReceivesFailureMessage() {
        final String error = "Something went wrong.";

        presenter.prepareFailView(error);

        assertEquals(
                error,
                presenter.errorMessage,
                "Presenter should store the failure message."
        );
        assertNull(
                presenter.successData,
                "Success data must remain null after failure."
        );
    }
}

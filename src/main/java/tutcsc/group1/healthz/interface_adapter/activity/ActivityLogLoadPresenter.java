package tutcsc.group1.healthz.interface_adapter.activity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogLoadOutputBoundary;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogLoadOutputData;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseFinderInputBoundary;

/**
 * Presenter for loading activity logs.
 * Converts use case output data into
 * objects suitable for display in the UI, updating
 * the ActivityHistoryViewModel.
 */
public class ActivityLogLoadPresenter implements ActivityLogLoadOutputBoundary {

    /**
     * Logger used to record presenter events and errors.
     */
    private static final Logger LOGGER =
            Logger.getLogger(ActivityLogLoadPresenter.class.getName());

    /**
     * ViewModel that receives UI-ready activity history items.
     */
    private final ActivityHistoryViewModel viewModel;

    /**
     * Interactor used to look up exercise names based on identifiers.
     */
    private final ExerciseFinderInputBoundary exerciseFinderInteractor;

    /**
     * Constructs a new ActivityLogLoadPresenter.
     *
     * @param viewmodel   the view model to update with activity entries
     * @param interactor the interactor for resolving exercise names by ID
     */
    public ActivityLogLoadPresenter(final ActivityHistoryViewModel viewmodel,
                            final ExerciseFinderInputBoundary interactor) {
        this.viewModel = viewmodel;
        this.exerciseFinderInteractor = interactor;
    }

    /**
     * Handles failure in loading activity logs.
     *
     * @param errorMessage a descriptive message about the failure
     */
    @Override
    public void prepareFailView(final String errorMessage) {
        LOGGER.log(Level.WARNING,
                "Failed to load activity logs: {0}", errorMessage);
        viewModel.setErrorMessage(errorMessage);
    }

    /**
     * Converts activity log output data
     * into UI-ready items and updates the ViewModel.
     *
     * @param outputData contains the list of {@link ActivityEntry} to display
     */
    @Override
    public void presentActivityLogs(
            final ActivityLogLoadOutputData outputData) throws Exception {
        final List<ActivityEntry> logs = outputData.getLogs();
        final List<ActivityItem> items = new ArrayList<>();

        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("d MMM yyyy");

        for (ActivityEntry entry : logs) {
            final String exerciseName = exerciseFinderInteractor
                    .findExerciseById(entry.getActivityId())
                    .getName();

            final ActivityItem item = new ActivityItem(
                    exerciseName,
                    entry.getDurationMinutes() + " min",
                    entry.getTimestamp().toLocalDate().format(formatter),
                    (int) entry.getCaloriesBurned()
            );

            items.add(item);
        }

        viewModel.setHistory(items);
        LOGGER.info(() -> {
            return "Loaded " + items.size() + " activity log entries.";
        });

    }
}

package tut0301.group1.healthz.interfaceadapter.activity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogLoadOutputBoundary;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogLoadOutputData;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseFinderInputBoundary;

/**
 * Presenter for loading activity logs.
 * Converts use case output data into objects suitable for display in the UI, updating
 * the ActivityHistoryViewModel.
 */
public class ActivityLogLoadPresenter implements ActivityLogLoadOutputBoundary {

    private static final Logger LOGGER =
            Logger.getLogger(ActivityLogLoadPresenter.class.getName());

    private final ActivityHistoryViewModel viewModel;
    private final ExerciseFinderInputBoundary exerciseFinderInteractor;

    /**
     * Constructs a new ActivityLogLoadPresenter.
     *
     * @param viewModel                the view model to update with activity entries
     * @param exerciseFinderInteractor the interactor for resolving exercise names by ID
     */
    public ActivityLogLoadPresenter(ActivityHistoryViewModel viewModel,
                                    ExerciseFinderInputBoundary exerciseFinderInteractor) {
        this.viewModel = viewModel;
        this.exerciseFinderInteractor = exerciseFinderInteractor;
    }

    /**
     * Handles failure in loading activity logs.
     *
     * @param errorMessage a descriptive message about the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        LOGGER.log(Level.WARNING, "Failed to load activity logs: {0}", errorMessage);
        viewModel.setErrorMessage(errorMessage);
    }

    /**
     * Converts activity log output data into UI-ready items and updates the ViewModel.
     *
     * @param outputData contains the list of {@link ActivityEntry} to display
     */
    @Override
    public void presentActivityLogs(ActivityLogLoadOutputData outputData) {
        List<ActivityEntry> logs = outputData.getLogs();
        List<ActivityItem> items = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");

        for (ActivityEntry entry : logs) {
            try {
                String exerciseName = exerciseFinderInteractor
                        .findExerciseById(entry.getActivityId())
                        .getName();

                ActivityItem item = new ActivityItem(
                        exerciseName,
                        entry.getDurationMinutes() + " min",
                        entry.getTimestamp().toLocalDate().format(formatter),
                        (int) entry.getCaloriesBurned()
                );

                items.add(item);

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, () -> "Error mapping activity entry: " + entry);
            }

            viewModel.setHistory(items);
            LOGGER.info(() -> "✅ Loaded " + items.size() + " activity log entries.");
        }
    }
}

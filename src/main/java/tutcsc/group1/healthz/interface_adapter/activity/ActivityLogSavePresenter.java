package tutcsc.group1.healthz.interface_adapter.activity;

import java.util.logging.Level;
import java.util.logging.Logger;

import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogSaveOutputBoundary;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogSaveOutputData;

/**
 * Presenter for handling the output of the Activity Log Save use case.
 * Updates the {@link ActivityHistoryViewModel} when a new log is saved.
 */
public class ActivityLogSavePresenter implements ActivityLogSaveOutputBoundary {

    /** Logger for this presenter class. */
    private static final Logger LOGGER =
            Logger.getLogger(ActivityLogSavePresenter.class.getName());

    /** The view model that holds the activity history for the view. */
    private final ActivityHistoryViewModel historyViewModel;

    /**
     * Constructs a new ActivityLogSavePresenter.
     *
     * @param viewModel the view model
     *                 to update with new activity entries
     */
    public ActivityLogSavePresenter(final ActivityHistoryViewModel viewModel) {
        this.historyViewModel = viewModel;
    }

    /**
     * Called when the interactor successfully saves an activity log.
     *
     * @param outputData the output data containing saved activity details
     */
    @Override
    public void prepareSuccessView(final ActivityLogSaveOutputData outputData) {
        final ActivityItem item = new ActivityItem(
                outputData.getExerciseName(),
                outputData.getDurationMinutes() + " min",
                outputData.getTimestamp(),
                outputData.getCalories()
        );
        historyViewModel.addLogEntry(item);
        LOGGER.info(
                "Activity log saved successfully for: "
                        + outputData.getExerciseName());
    }

    /**
     * Called when the interactor fails to save an activity log.
     *
     * @param errorMessage the message describing the failure reason
     */
    @Override
    public void prepareFailView(final String errorMessage) {
        LOGGER.log(Level.WARNING,
                "Failed to save activity log: {0}", errorMessage);
    }
}

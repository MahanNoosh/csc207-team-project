package tutcsc.group1.healthz.interface_adapter.dashboard;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;
import tutcsc.group1.healthz.interface_adapter.activity.ActivityItem;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseFinderInputBoundary;
import tutcsc.group1.healthz.use_case.activity.recent.RecentActivityOutputBoundary;
import tutcsc.group1.healthz.use_case.activity.recent.RecentActivityOutputData;

/**
 * Presenter for recent activity use case.
 *
 * <p>
 * Converts the output data from the interactor into a form suitable for
 * {@link RecentActivityViewModel}.
 * </p>
 */
public class RecentActivityPresenter implements RecentActivityOutputBoundary {

    /** Logger for recording events and errors in RecentActivityPresenter. */
    private static final Logger LOGGER =
            Logger.getLogger(RecentActivityPresenter.class.getName());

    /**
     * ViewModel that receives UI-ready recent activity items.
     */
    private final RecentActivityViewModel viewModel;

    /**
     * Interactor used to look up exercise names based on identifiers.
     */
    private final ExerciseFinderInputBoundary exerciseFinderInteractor;

    /**
     * Constructs a RecentActivityPresenter.
     *
     * @param activityViewModel the {@link RecentActivityViewModel} to update
     * @param finderInputBoundary the interactor for resolving exercise names
     */
    public RecentActivityPresenter(
            final RecentActivityViewModel activityViewModel,
            final ExerciseFinderInputBoundary finderInputBoundary) {
        this.viewModel = activityViewModel;
        this.exerciseFinderInteractor = finderInputBoundary;
    }

    /**
     * Converts recent activity output data into view model items
     * and updates the {@link RecentActivityViewModel}.
     *
     * @param outputData the {@link RecentActivityOutputData}
     *                  containing recent activities
     */
    @Override
    public void presentRecentActivities(
            final RecentActivityOutputData outputData) throws Exception {
        final List<ActivityEntry> logs = outputData.getRecentActivities();
        final List<ActivityItem> items = new ArrayList<>();

        final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("d MMM yyyy");

        for (final ActivityEntry entry : logs) {
            final String exerciseName = exerciseFinderInteractor
                    .findExerciseById(entry.getActivityId())
                    .getName();

            final ActivityItem item = new ActivityItem(
                    exerciseName,
                    String.valueOf(entry.getDurationMinutes()),
                    entry.getTimestamp().toLocalDate().format(formatter),
                    (int) entry.getCaloriesBurned()
            );

            items.add(item);
        }

        viewModel.setRecent(items);
    }

    /**
     * Handles failure in loading recent activities.
     *
     * @param error a descriptive error message
     */
    @Override
    public void prepareFailView(final String error) {
        LOGGER.log(Level.SEVERE,
                "Failed to load recent activities: {0}", error);
    }
}

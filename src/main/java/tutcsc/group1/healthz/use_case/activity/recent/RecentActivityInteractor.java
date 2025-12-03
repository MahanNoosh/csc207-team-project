package tutcsc.group1.healthz.use_case.activity.recent;

import java.util.List;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogDataAccessInterface;

/**
 * Interactor for loading recent activity logs for the current user.
 * Implements the {@link RecentActivityInputBoundary} interface.
 * Handles fetching recent activities from the data access layer
 * and passing them to the presenter.
 */
public class RecentActivityInteractor implements RecentActivityInputBoundary {

    /** Data access interface for retrieving activity logs. */
    private final ActivityLogDataAccessInterface activityLogDataAccessInterface;

    /** Output boundary for presenting recent activity data. */
    private final RecentActivityOutputBoundary presenter;

    /**
     * Constructs a RecentActivityInteractor with
     *          the given data access and presenter.
     *
     * @param dataAccessInterface the data access
     *                           interface for activity logs
     * @param outputBoundary the output boundary
     *                       responsible for presenting the results
     */
    public RecentActivityInteractor(
            final ActivityLogDataAccessInterface dataAccessInterface,
            final RecentActivityOutputBoundary outputBoundary) {
        this.activityLogDataAccessInterface = dataAccessInterface;
        this.presenter = outputBoundary;
    }

    /**
     * Loads recent activities for the current user.
     * This method retrieves activity entries from the data access layer
     * and passes them to the presenter. If an exception occurs while
     * fetching the data, a failure message is sent to the presenter.
     */
    @Override
    public void loadRecentActivities() {
        try {
            final List<ActivityEntry> logs =
                    activityLogDataAccessInterface.getActivitiesForUser();
            presenter.presentRecentActivities(
                    new RecentActivityOutputData(logs));
        } catch (Exception ex) {
            presenter.prepareFailView(
                    "Failed to load activity logs: " + ex.getMessage());
        }
    }
}

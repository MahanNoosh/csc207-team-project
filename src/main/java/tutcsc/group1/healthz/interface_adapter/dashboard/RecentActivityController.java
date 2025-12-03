package tutcsc.group1.healthz.interface_adapter.dashboard;

import tutcsc.group1.healthz.use_case.activity.recent.RecentActivityInputBoundary;

/**
 * Controller for handling recent activity operations in the dashboard.
 *
 * <p>
 * Coordinates user actions with the {@link RecentActivityInputBoundary}
 * interactor to load recent activities.
 * </p>
 */
public class RecentActivityController {

    /**
     * Interactor responsible for loading recent activity data.
     */
    private final RecentActivityInputBoundary recentActivityInputBoundary;

    /**
     * Constructs a new RecentActivityController with the given interactor.
     *
     * @param interactor the {@link RecentActivityInputBoundary}
     *                  to delegate requests to
     */
    public RecentActivityController(
            final RecentActivityInputBoundary interactor) {
        this.recentActivityInputBoundary = interactor;
    }

    /**
     * Initiates loading of recent activities from the data source.
     */
    public void loadRecentActivities() {
        recentActivityInputBoundary.loadRecentActivities();
    }
}

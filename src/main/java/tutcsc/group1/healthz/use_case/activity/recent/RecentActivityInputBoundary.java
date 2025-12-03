package tutcsc.group1.healthz.use_case.activity.recent;

/**
 * Input boundary for the Recent Activity use case.
 * Defines the operations that the interactor exposes to load recent activities.
 */
public interface RecentActivityInputBoundary {

    /**
     * Loads recent activities for the current user.
     * The result will be passed to the output boundary (presenter).
     */
    void loadRecentActivities();
}

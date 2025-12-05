package tutcsc.group1.healthz.use_case.activity.recent;

/**
 * Output boundary for the Recent Activity use case.
 * Defines methods for presenting recent activity data
 * and handling failure scenarios.
 */
public interface RecentActivityOutputBoundary {

    /**
     * Presents a list of recent activity entries to the view.
     *
     * @param outputData the output data containing recent activity logs
     * @throws Exception if presenting the data fails
     */
    void presentRecentActivities(
            RecentActivityOutputData outputData) throws Exception;

    /**
     * Prepares the view to display an error message when
     * loading or presenting recent activities fails.
     *
     * @param error the error message to be displayed
     */
    void prepareFailView(String error);
}

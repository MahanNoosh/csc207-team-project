package tutcsc.group1.healthz.use_case.activity.activitylog;

/**
 * Output boundary for presenting the result of loading activity logs.
 * Implementations of this interface should handle both success and failure views
 * when retrieving user activity log data.
 */
public interface ActivityLogLoadOutputBoundary {

    /**
     * Called when loading activity logs fails.
     *
     * @param errorMessage the reason for failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Called when activity logs are successfully retrieved.
     *
     * @param outputData the loaded activity logs
     */
    void presentActivityLogs(ActivityLogLoadOutputData outputData);
}

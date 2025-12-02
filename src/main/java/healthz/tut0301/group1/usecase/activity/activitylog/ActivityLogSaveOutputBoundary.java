package healthz.tut0301.group1.usecase.activity.activitylog;

/**
 * Output boundary for saving activity logs.
 * Defines methods for presenting success or failure outcomes
 * to the presenter after attempting to save an activity log.
 */
public interface ActivityLogSaveOutputBoundary {

    /**
     * Called when the activity log is successfully saved.
     *
     * @param activityLogOutputData the output data to present
     */
    void prepareSuccessView(ActivityLogSaveOutputData activityLogOutputData);

    /**
     * Called when saving the activity log fails.
     *
     * @param failedToSaveActivityLog an error message to present
     */
    void prepareFailView(String failedToSaveActivityLog);
}

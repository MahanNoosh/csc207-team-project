package tut0301.group1.healthz.usecase.activity.activitylog;

import tut0301.group1.healthz.entities.Dashboard.Profile;

/**
 * Input boundary for logging and retrieving user activity logs.
 * Defines the application-specific use case methods related to activity logs.
 */
public interface ActivityLogInputBoundary {

    /**
     * Logs a new activity for the given user.
     *
     * @param activityLogInputData The data representing the activity to be logged.
     * @param profile              The user's profile used for context (e.g. for calorie calculation).
     * @throws Exception if logging fails.
     */
    void logActivity(ActivityLogInputData activityLogInputData, Profile profile) throws Exception;

    /**
     * Loads activity log history for the current user.
     *
     * @throws Exception if retrieval fails.
     */
    void loadLogsForUser() throws Exception;
}

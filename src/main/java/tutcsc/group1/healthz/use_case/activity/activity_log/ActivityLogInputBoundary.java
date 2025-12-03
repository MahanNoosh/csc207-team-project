package tutcsc.group1.healthz.use_case.activity.activity_log;

import tutcsc.group1.healthz.entities.dashboard.Profile;

/**
 * Input boundary for logging and retrieving user activity logs.
 * Defines the application-specific use case methods related to activity logs.
 */
public interface ActivityLogInputBoundary {

    /**
     * Logs a new activity for the given user.
     *
     * @param activityLogInputData The data to be logged.
     * @param profile The user's profile used for context.
     * @throws Exception if logging fails.
     */
    void logActivity(ActivityLogInputData activityLogInputData, Profile profile)
            throws Exception;

    /**
     * Loads activity log history for the current user.
     *
     * @throws Exception if retrieval fails.
     */
    void loadLogsForUser() throws Exception;
}

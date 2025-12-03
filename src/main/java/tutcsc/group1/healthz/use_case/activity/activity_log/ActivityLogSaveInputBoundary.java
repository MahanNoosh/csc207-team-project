package tutcsc.group1.healthz.use_case.activity.activity_log;

import tutcsc.group1.healthz.entities.dashboard.Profile;

public interface ActivityLogSaveInputBoundary {
    /**
     * Logs a new activity for the given user.
     *
     * @param activityLogInputData The data to be logged.
     * @param profile The user's profile used for context.
     * @throws Exception if logging fails.
     */
    void execute(ActivityLogInputData activityLogInputData, Profile profile)
            throws Exception;

}

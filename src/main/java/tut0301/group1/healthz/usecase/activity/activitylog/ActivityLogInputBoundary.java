package tut0301.group1.healthz.usecase.activity.activitylog;

import tut0301.group1.healthz.entities.Dashboard.Profile;

public interface ActivityLogInputBoundary {
    void logActivity(ActivityLogInputData activityLogInputData, Profile profile)throws Exception;
    void loadLogsForUser() throws Exception;
}

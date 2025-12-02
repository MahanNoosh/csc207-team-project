package healthz.tut0301.group1.usecase.activity.activitylog;

import healthz.tut0301.group1.entities.Dashboard.Profile;

public interface ActivityLogInputBoundary {
    void logActivity(ActivityLogInputData activityLogInputData, Profile profile)throws Exception;
    void loadLogsForUser() throws Exception;
}

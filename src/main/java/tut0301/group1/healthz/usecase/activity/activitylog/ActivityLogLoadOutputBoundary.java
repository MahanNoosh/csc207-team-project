package tut0301.group1.healthz.usecase.activity.activitylog;

public interface ActivityLogLoadOutputBoundary {
    void prepareFailView(String failedToLoadActivityLogs);

    void presentActivityLogs(ActivityLogLoadOutputData activityLogLoadOutputData);
}

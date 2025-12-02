package healthz.tut0301.group1.usecase.activity.activitylog;

public interface ActivityLogLoadOutputBoundary {
    void prepareFailView(String failedToLoadActivityLogs);

    void presentActivityLogs(ActivityLogLoadOutputData activityLogLoadOutputData);
}

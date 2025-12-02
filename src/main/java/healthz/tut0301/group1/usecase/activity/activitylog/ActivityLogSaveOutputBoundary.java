package healthz.tut0301.group1.usecase.activity.activitylog;

public interface ActivityLogSaveOutputBoundary {

    void prepareSuccessView(ActivityLogSaveOutputData activityLogOutputData);

    void prepareFailView(String failedToSaveActivityLog);
}

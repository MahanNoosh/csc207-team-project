package tut0301.group1.healthz.usecase.activity.activitylog;

public interface ActivityLogSaveOutputBoundary {

    void prepareSuccessView(ActivityLogSaveOutputData activityLogOutputData);

    void prepareFailView(String failedToSaveActivityLog);
}

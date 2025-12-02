package healthz.tut0301.group1.usecase.activity.recent;

public interface RecentActivityOutputBoundary {
    void presentRecentActivities(RecentActivityOutputData outputData);
    void prepareFailView(String error);
}

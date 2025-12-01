package tut0301.group1.healthz.usecase.activity.recent;

public interface RecentActivityOutputBoundary {
    void presentRecentActivities(RecentActivityOutputData outputData);
    void prepareFailView(String error);
}

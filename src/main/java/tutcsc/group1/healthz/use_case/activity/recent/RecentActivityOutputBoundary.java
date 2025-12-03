package tutcsc.group1.healthz.use_case.activity.recent;

public interface RecentActivityOutputBoundary {
    void presentRecentActivities(RecentActivityOutputData outputData);
    void prepareFailView(String error);
}

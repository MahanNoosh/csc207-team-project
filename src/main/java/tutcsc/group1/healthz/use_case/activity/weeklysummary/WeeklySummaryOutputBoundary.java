package tutcsc.group1.healthz.use_case.activity.weeklysummary;

public interface WeeklySummaryOutputBoundary {
    void presentSummary(WeeklySummaryOutputData outputData);
    void prepareFailView(String error);
}

package tut0301.group1.healthz.usecase.activity.weeklysummary;

public interface WeeklySummaryOutputBoundary {
    void presentSummary(WeeklySummaryOutputData outputData);
    void prepareFailView(String error);
}

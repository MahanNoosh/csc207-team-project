package tut0301.group1.healthz.usecase.dashboard;

/**
 * Output Boundary for Dashboard Use Case
 */
public interface DashboardOutputBoundary {
    void presentDashboard(DashboardOutputData data);
    void presentError(String message);
}
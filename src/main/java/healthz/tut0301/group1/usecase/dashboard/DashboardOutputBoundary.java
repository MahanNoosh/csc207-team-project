package healthz.tut0301.group1.usecase.dashboard;

/**
 * Output Boundary for Dashboard Use Case
 */
public interface DashboardOutputBoundary {
    void presentDashboard(DashboardOutputData data);
    void presentError(String message);
}
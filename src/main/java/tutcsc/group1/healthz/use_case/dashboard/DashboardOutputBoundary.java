package tutcsc.group1.healthz.use_case.dashboard;

/**
 * Output Boundary for Dashboard Use Case
 */
public interface DashboardOutputBoundary {
    void presentDashboard(DashboardOutputData data);
    void presentError(String message);
}
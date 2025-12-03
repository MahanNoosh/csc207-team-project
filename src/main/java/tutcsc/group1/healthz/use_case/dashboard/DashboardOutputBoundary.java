package tutcsc.group1.healthz.use_case.dashboard;

/**
 * Output Boundary for dashboard Use Case
 */
public interface DashboardOutputBoundary {
    void presentDashboard(DashboardOutputData data);
    void presentError(String message);
}
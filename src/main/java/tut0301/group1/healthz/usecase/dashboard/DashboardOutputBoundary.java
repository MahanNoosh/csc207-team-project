package tut0301.group1.healthz.usecase.dashboard;

/**
 * Output Boundary for Dashboard Use Case
 */
public interface DashboardOutputBoundary {
    /**
     * Present successful dashboard data
     * @param data The dashboard output data containing calories and user info
     */
    void presentDashboard(DashboardOutputData data);

    /**
     * Present an error message when dashboard loading fails
     */
    void presentError(String message);
}
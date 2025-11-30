package tut0301.group1.healthz.usecase.dashboard;

/**
 * Input Boundary for Dashboard Use Case
 */
public interface DashboardInputBoundary {
    /**
     * Load dashboard data for a specific user
     * @param userId The unique identifier of the user
     */
    void loadDashboard(String userId);
}
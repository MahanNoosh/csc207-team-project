package tutcsc.group1.healthz.interface_adapter.dashboard;

import tutcsc.group1.healthz.use_case.dashboard.DashboardInputBoundary;

/**
 * Controller for Dashboard
 */
public class DashboardController {
    private final DashboardInputBoundary interactor;

    public DashboardController(DashboardInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void loadDashboard(String userId) {
        System.out.println("DashboardController: Loading dashboard for user " + userId);
        interactor.loadDashboard(userId);
    }
}
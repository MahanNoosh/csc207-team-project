package healthz.tut0301.group1.interfaceadapter.dashboard;

import healthz.tut0301.group1.usecase.dashboard.DashboardInputBoundary;

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
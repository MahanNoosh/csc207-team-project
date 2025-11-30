package tut0301.group1.healthz.interfaceadapter.dashboard;

import tut0301.group1.healthz.usecase.dashboard.DashboardInputBoundary;

/**
 * Controller for Dashboard
 * Receives user input and triggers use cases
 */
public class DashboardController {
    private final DashboardInputBoundary interactor;

    public DashboardController(DashboardInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Load dashboard for a specific user
     * @param userId The user's unique identifier
     */
    public void loadDashboard(String userId) {
        System.out.println("DashboardController: Loading dashboard for user " + userId);
        interactor.loadDashboard(userId);
    }
}
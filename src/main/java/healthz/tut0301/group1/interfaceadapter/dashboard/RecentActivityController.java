package healthz.tut0301.group1.interfaceadapter.dashboard;

import healthz.tut0301.group1.usecase.activity.recent.RecentActivityInputBoundary;

public class RecentActivityController {

    private final RecentActivityInputBoundary interactor;

    public RecentActivityController(RecentActivityInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void loadRecentActivities() {
        interactor.loadRecentActivities();
    }

}

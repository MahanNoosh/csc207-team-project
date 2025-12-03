package tutcsc.group1.healthz.interface_adapter.dashboard;

import tutcsc.group1.healthz.use_case.activity.recent.RecentActivityInputBoundary;

public class RecentActivityController {

    private final RecentActivityInputBoundary interactor;

    public RecentActivityController(RecentActivityInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void loadRecentActivities() {
        interactor.loadRecentActivities();
    }

}

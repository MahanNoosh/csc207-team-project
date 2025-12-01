package tut0301.group1.healthz.interfaceadapter.dashboard;

import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogDataAccessInterface;
import tut0301.group1.healthz.usecase.activity.recent.RecentActivityInputBoundary;

public class RecentActivityController {

    private final RecentActivityInputBoundary interactor;

    public RecentActivityController(RecentActivityInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void loadRecentActivities() {
        interactor.loadRecentActivities();
    }

}

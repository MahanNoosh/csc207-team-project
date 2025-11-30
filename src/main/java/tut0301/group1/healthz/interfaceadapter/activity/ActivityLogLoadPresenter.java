package tut0301.group1.healthz.interfaceadapter.activity;

import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogLoadOutputBoundary;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogLoadOutputData;

public class ActivityLogLoadPresenter implements ActivityLogLoadOutputBoundary {
    @Override
    public void prepareFailView(String failedToLoadActivityLogs) {

    }

    @Override
    public void presentActivityLogs(ActivityLogLoadOutputData activityLogLoadOutputData) {

    }
}

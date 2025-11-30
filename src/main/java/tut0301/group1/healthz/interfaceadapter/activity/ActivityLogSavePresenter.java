package tut0301.group1.healthz.interfaceadapter.activity;

import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogSaveOutputBoundary;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogSaveOutputData;

public class ActivityLogSavePresenter implements ActivityLogSaveOutputBoundary {
    private final ActivityHistoryViewModel historyViewModel;

    public ActivityLogSavePresenter(ActivityHistoryViewModel historyViewModel) {
        this.historyViewModel = historyViewModel;
    }

    @Override
    public void prepareSuccessView(ActivityLogSaveOutputData activityLogOutputData) {
        ActivityItem item = new ActivityItem(
                activityLogOutputData.getExerciseName(),
                activityLogOutputData.getDurationMinutes() + " min",
                activityLogOutputData.getTimestamp(),
                activityLogOutputData.getCalories()
        );
        historyViewModel.addLogEntry(item);
    }

    @Override
    public void prepareFailView(String failedToSaveActivityLog) {

    }
}

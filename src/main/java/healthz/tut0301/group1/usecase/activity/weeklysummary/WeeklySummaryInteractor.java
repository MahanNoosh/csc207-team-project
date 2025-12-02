package healthz.tut0301.group1.usecase.activity.weeklysummary;

import healthz.tut0301.group1.usecase.activity.activitylog.ActivityLogDataAccessInterface;

import java.time.DayOfWeek;
import java.util.Map;

public class WeeklySummaryInteractor implements WeeklySummaryInputBoundary{
    private final ActivityLogDataAccessInterface dataAccess;
    private final WeeklySummaryOutputBoundary presenter;

    public WeeklySummaryInteractor(ActivityLogDataAccessInterface dataAccess, WeeklySummaryOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void loadWeeklySummary() throws Exception {
        try {
            Map<DayOfWeek, Double> minutes = dataAccess.getWeeklyActivitySummary();
            WeeklySummaryOutputData outputData = new WeeklySummaryOutputData(minutes);
            presenter.presentSummary(outputData);
        } catch (Exception e) {
            presenter.prepareFailView("Failed to load weekly activity summary: " + e.getMessage());
        }
    }
}

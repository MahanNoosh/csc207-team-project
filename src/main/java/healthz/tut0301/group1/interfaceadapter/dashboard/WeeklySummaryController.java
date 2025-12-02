package healthz.tut0301.group1.interfaceadapter.dashboard;

import healthz.tut0301.group1.usecase.activity.weeklysummary.WeeklySummaryInputBoundary;

public class WeeklySummaryController {
    private final WeeklySummaryInputBoundary interactor;

    public WeeklySummaryController(WeeklySummaryInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void loadSummary() {
        try {
            interactor.loadWeeklySummary();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

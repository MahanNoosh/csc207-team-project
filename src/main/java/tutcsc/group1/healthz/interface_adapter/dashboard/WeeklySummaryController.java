package tutcsc.group1.healthz.interface_adapter.dashboard;

import tutcsc.group1.healthz.use_case.activity.weeklysummary.WeeklySummaryInputBoundary;

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

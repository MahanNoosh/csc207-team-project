package tutcsc.group1.healthz.interface_adapter.dashboard;

import java.util.logging.Logger;

import tutcsc.group1.healthz.use_case.activity.weekly_summary.WeeklySummaryInputBoundary;

/**
 * Controller for loading the weekly activity summary.
 * Delegates the request to the {@link WeeklySummaryInputBoundary} interactor.
 */
public class WeeklySummaryController {

    /**
     * Logger for reporting errors or information.
     */
    private static final Logger LOGGER =
            Logger.getLogger(WeeklySummaryController.class.getName());

    /**
     * Interactor responsible for handling weekly summary use case.
     */
    private final WeeklySummaryInputBoundary interactor;

    /**
     * Constructs a new WeeklySummaryController.
     *
     * @param summaryInputBoundary the interactor for weekly summary
     */
    public WeeklySummaryController(
            final WeeklySummaryInputBoundary summaryInputBoundary) {
        this.interactor = summaryInputBoundary;
    }

    /**
     * Requests the interactor to load the weekly summary.
     *
     * @throws Exception if the interactor fails
     */
    public void loadSummary() throws Exception {
        interactor.loadWeeklySummary();
    }
}

package tutcsc.group1.healthz.use_case.activity.weekly_summary;

/**
 * Output boundary interface for the Weekly Summary use case.
 * Defines methods to present the weekly summary data or handle failures.
 */
public interface WeeklySummaryOutputBoundary {

    /**
     * Presents the weekly summary data to the view.
     *
     * @param outputData the weekly summary output data containing
     *                   total activity minutes per day
     */
    void presentSummary(WeeklySummaryOutputData outputData);

    /**
     * Handles the case when loading or processing the weekly summary fails.
     *
     * @param error a descriptive error message
     */
    void prepareFailView(String error);
}

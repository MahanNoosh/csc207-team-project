package tutcsc.group1.healthz.use_case.activity.weekly_summary;

/**
 * Input boundary for the Weekly Summary use case.
 * Defines the operations that can be called by the controller
 * to request weekly activity summary data.
 */
public interface WeeklySummaryInputBoundary {

    /**
     * Loads the weekly activity summary for the current user.
     *
     * @throws Exception if there is a failure in retrieving the summary
     */
    void loadWeeklySummary() throws Exception;
}

package tutcsc.group1.healthz.use_case.macrosummary;

/**
 * Output Boundary for Get Daily Calorie Summary use case.
 *
 * This interface is implemented by the Presenter in the Interface Adapter layer,
 * following the Dependency Inversion Principle.
 */
public interface GetDailyCalorieSummaryOutputBoundary {
    /**
     * Present the daily calorie summary to the user.
     *
     * @param outputData The output data containing the daily calorie summary
     */
    void presentDailySummary(GetDailyCalorieSummaryOutputData outputData);

    /**
     * Present an error message when the use case fails.
     *
     * @param errorMessage The error message to present
     */
    void presentError(String errorMessage);
}

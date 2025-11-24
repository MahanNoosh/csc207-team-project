package tut0301.group1.healthz.usecase.dailysummary;

/**
 * Output Boundary for Get Daily Summary use case.
 *
 * This interface is implemented by the Presenter in the Interface Adapter layer,
 * following the Dependency Inversion Principle.
 */
public interface GetDailySummaryOutputBoundary {
    /**
     * Present the daily summary to the user.
     *
     * @param outputData The output data containing the daily summary
     */
    void presentDailySummary(GetDailySummaryOutputData outputData);

    /**
     * Present an error message when the use case fails.
     *
     * @param errorMessage The error message to present
     */
    void presentError(String errorMessage);
}

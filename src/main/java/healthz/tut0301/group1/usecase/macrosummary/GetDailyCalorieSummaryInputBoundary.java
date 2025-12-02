package healthz.tut0301.group1.usecase.macrosummary;

/**
 * Input Boundary for Get Daily Calorie Summary use case.
 *
 * This interface defines the contract for the use case interactor,
 * following the Dependency Inversion Principle.
 */
public interface GetDailyCalorieSummaryInputBoundary {
    /**
     * Execute the get daily summary use case.
     *
     * @param inputData The input data containing user ID and date
     */
    void execute(GetDailyCalorieSummaryInputData inputData);
}

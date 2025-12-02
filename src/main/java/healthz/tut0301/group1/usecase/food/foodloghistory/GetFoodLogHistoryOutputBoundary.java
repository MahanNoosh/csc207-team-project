package healthz.tut0301.group1.usecase.food.foodloghistory;

/**
 * Output Boundary for Get Food Log History use case.
 *
 * Defines the contract for presenting food log history results.
 * This interface is defined in the Use Case layer and implemented by the Presenter.
 */
public interface GetFoodLogHistoryOutputBoundary {
    /**
     * Present the food log history to the view.
     *
     * @param outputData The output data containing food logs
     */
    void presentFoodLogHistory(GetFoodLogHistoryOutputData outputData);

    /**
     * Present an error message when the use case fails.
     *
     * @param errorMessage The error message
     */
    void presentError(String errorMessage);
}

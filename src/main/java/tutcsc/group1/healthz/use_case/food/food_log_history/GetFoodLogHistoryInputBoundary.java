package tutcsc.group1.healthz.use_case.food.foodloghistory;

/**
 * Input Boundary for Get Food Log History use case.
 * Defines the contract for retrieving food log history.
 * This interface is defined in the Use Case layer and implemented by the Interactor.
 */
public interface GetFoodLogHistoryInputBoundary {
    /**
     * Execute the use case to retrieve food log history.
     *
     * @param inputData The input data containing userId and date
     */
    void execute(GetFoodLogHistoryInputData inputData);
}

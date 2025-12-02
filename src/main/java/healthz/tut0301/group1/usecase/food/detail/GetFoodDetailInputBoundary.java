package healthz.tut0301.group1.usecase.food.detail;

/**
 * Input Boundary interface for GetFoodDetail use case.
 *
 * This interface defines the contract between the Controller
 * and the Interactor. The Controller calls this method,
 * and the Interactor implements it.
 */
public interface GetFoodDetailInputBoundary {
    /**
     * Executes the use case to retrieve food details.
     *
     * @param inputData contains the food ID to retrieve
     */
    void execute(GetFoodDetailInputData inputData);
}

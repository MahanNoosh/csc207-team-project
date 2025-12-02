package tut0301.group1.healthz.usecase.food.detail;

/**
 * Output Boundary interface for GetFoodDetail use case.
 *
 * This interface defines the contract between the Interactor
 * and the Presenter. The Interactor calls this method,
 * and the Presenter implements it.
 */
public interface GetFoodDetailOutputBoundary {
    /**
     * Presents the food detail result to the view.
     *
     * @param outputData contains the food details or error message
     */
    void presentFoodDetail(GetFoodDetailOutputData outputData);
}

package tut0301.group1.healthz.usecase.food.logging;

/**
 * Output Boundary for logging food intake.
 *
 * The Interactor calls this interface.
 * The Presenter implements this interface.
 */
public interface LogFoodIntakeOutputBoundary {
    /**
     * Presents the result of food intake logging.
     *
     * @param outputData contains the logged food or error information
     */
    void presentLogResult(LogFoodIntakeOutputData outputData);
}

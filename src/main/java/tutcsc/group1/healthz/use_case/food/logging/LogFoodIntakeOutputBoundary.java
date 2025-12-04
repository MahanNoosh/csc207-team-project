package tutcsc.group1.healthz.use_case.food.logging;

/**
 * Output Boundary for logging food intake.
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

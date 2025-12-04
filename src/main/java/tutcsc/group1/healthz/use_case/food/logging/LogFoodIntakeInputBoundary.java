package tutcsc.group1.healthz.use_case.food.logging;

/**
 * Input Boundary for logging food intake.
 * The Controller calls this interface.
 * The Interactor implements this interface.
 */
public interface LogFoodIntakeInputBoundary {
    /**
     * Executes the use case to log food intake.
     *
     * @param inputData contains the food, serving, and meal information
     */
    void execute(LogFoodIntakeInputData inputData);
}

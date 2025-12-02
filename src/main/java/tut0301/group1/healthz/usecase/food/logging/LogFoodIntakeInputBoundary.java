package tut0301.group1.healthz.usecase.food.logging;

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

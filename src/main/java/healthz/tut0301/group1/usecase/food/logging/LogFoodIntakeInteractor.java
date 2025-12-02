package healthz.tut0301.group1.usecase.food.logging;

import healthz.tut0301.group1.entities.nutrition.FoodLog;

/**
 * Interactor for logging food intake.
 *
 * This class implements the business logic for recording
 * food consumption, following Clean Architecture principles.
 */
public class LogFoodIntakeInteractor implements LogFoodIntakeInputBoundary {
    private final FoodLogGateway gateway;
    private final LogFoodIntakeOutputBoundary outputBoundary;

    /**
     * Constructor with dependency injection.
     *
     * @param gateway the gateway for saving food logs
     * @param outputBoundary the presenter for displaying results
     */
    public LogFoodIntakeInteractor(FoodLogGateway gateway,
                                   LogFoodIntakeOutputBoundary outputBoundary) {
        if (gateway == null) {
            throw new IllegalArgumentException("FoodLogGateway cannot be null");
        }
        if (outputBoundary == null) {
            throw new IllegalArgumentException("LogFoodIntakeOutputBoundary cannot be null");
        }
        this.gateway = gateway;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(LogFoodIntakeInputData inputData) {
        try {
            // Create FoodLog with the timestamp from input data
            FoodLog foodLog = new FoodLog(
                inputData.getFood(),
                inputData.getServingInfo(),
                inputData.getServingMultiplier(),
                inputData.getMeal(),
                inputData.getLoggedAt()
            );

            // Save through gateway
            gateway.saveFoodLog(inputData.getUserId(), foodLog);

            // Create success output
            LogFoodIntakeOutputData outputData = new LogFoodIntakeOutputData(
                foodLog,
                "Food intake logged successfully"
            );

            // Pass to presenter
            outputBoundary.presentLogResult(outputData);

        } catch (Exception e) {
            // Create error output
            LogFoodIntakeOutputData outputData = new LogFoodIntakeOutputData(
                "Failed to log food intake: " + e.getMessage()
            );

            // Pass error to presenter
            outputBoundary.presentLogResult(outputData);
        }
    }
}

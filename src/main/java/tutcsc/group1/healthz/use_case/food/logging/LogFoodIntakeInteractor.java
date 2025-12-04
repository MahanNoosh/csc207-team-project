package tutcsc.group1.healthz.use_case.food.logging;

import java.io.IOException;

import tutcsc.group1.healthz.entities.nutrition.FoodLog;

/**
 * Interactor for logging food intake.
 * This class implements the business logic for recording
 * food consumption, following Clean Architecture principles.
 */
public class LogFoodIntakeInteractor implements LogFoodIntakeInputBoundary {

    private final FoodLogDataAccessInterface gateway;
    private final LogFoodIntakeOutputBoundary outputBoundary;

    /**
     * Constructor with dependency injection.
     *
     * @param gateway        the gateway for saving food logs
     * @param outputBoundary the presenter for displaying results
     * @throws IllegalArgumentException if gateway or outputBoundary is null
     */
    public LogFoodIntakeInteractor(FoodLogDataAccessInterface gateway, LogFoodIntakeOutputBoundary outputBoundary) {
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
            final FoodLog foodLog = new FoodLog(inputData.getFood(), inputData.getServingInfo(),
                    inputData.getServingMultiplier(), inputData.getMeal(), inputData.getLoggedAt());

            // Save through gateway
            gateway.saveFoodLog(inputData.getUserId(), foodLog);

            // Create success output
            final LogFoodIntakeOutputData outputData = new LogFoodIntakeOutputData(foodLog,
                    "Food intake logged " + "successfully");

            // Pass to presenter
            outputBoundary.presentLogResult(outputData);

        }
        catch (IOException | InterruptedException exception) {
            // Create error output
            final LogFoodIntakeOutputData outputData =
                    new LogFoodIntakeOutputData("Failed to log food intake: " + exception.getMessage());

            // Pass error to presenter
            outputBoundary.presentLogResult(outputData);
        }
    }
}

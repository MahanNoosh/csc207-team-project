package tutcsc.group1.healthz.use_case.food.foodloghistory;

import tutcsc.group1.healthz.entities.nutrition.FoodLog;
import tutcsc.group1.healthz.use_case.food.logging.FoodLogGateway;

import java.util.List;

/**
 * Interactor for Get Food Log History use case.
 *
 * This class implements the business logic for retrieving food logs for a specific date.
 * It follows Clean Architecture principles by depending on abstractions (interfaces)
 * rather than concrete implementations.
 */
public class GetFoodLogHistoryInteractor implements GetFoodLogHistoryInputBoundary {
    private final FoodLogGateway foodLogGateway;
    private final GetFoodLogHistoryOutputBoundary outputBoundary;

    /**
     * Constructor with dependency injection.
     *
     * @param foodLogGateway The gateway for accessing food log data
     * @param outputBoundary The presenter for formatting and presenting output
     */
    public GetFoodLogHistoryInteractor(FoodLogGateway foodLogGateway,
                                       GetFoodLogHistoryOutputBoundary outputBoundary) {
        if (foodLogGateway == null) {
            throw new IllegalArgumentException("FoodLogGateway cannot be null");
        }
        if (outputBoundary == null) {
            throw new IllegalArgumentException("OutputBoundary cannot be null");
        }
        this.foodLogGateway = foodLogGateway;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(GetFoodLogHistoryInputData inputData) {
        try {
            // Get food logs for the specified date
            List<FoodLog> foodLogs = foodLogGateway.getFoodLogsByDate(
                    inputData.getUserId(),
                    inputData.getDate()
            );

            // Create output data
            GetFoodLogHistoryOutputData outputData = new GetFoodLogHistoryOutputData(
                    inputData.getDate(),
                    foodLogs
            );

            // Present the results
            outputBoundary.presentFoodLogHistory(outputData);

        } catch (Exception e) {
            outputBoundary.presentError("Failed to retrieve food log history: " + e.getMessage());
        }
    }
}

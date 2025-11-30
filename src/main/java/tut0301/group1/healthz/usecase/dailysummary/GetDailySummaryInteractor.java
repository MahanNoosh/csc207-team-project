package tut0301.group1.healthz.usecase.dailysummary;

import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;
import tut0301.group1.healthz.usecase.food.logging.FoodLogGateway;

import java.util.List;

/**
 * Interactor for Get Daily Summary use case.
 *
 * This class implements the business logic for retrieving a user's
 * daily nutrition summary, following Clean Architecture principles.
 */
public class GetDailySummaryInteractor implements GetDailySummaryInputBoundary {
    private final FoodLogGateway foodLogGateway;
    private final GetDailySummaryOutputBoundary outputBoundary;

    /**
     * Constructor with dependency injection.
     *
     * @param foodLogGateway The gateway for accessing food log data
     * @param outputBoundary The presenter for formatting and presenting output
     */
    public GetDailySummaryInteractor(FoodLogGateway foodLogGateway,
                                     GetDailySummaryOutputBoundary outputBoundary) {
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
    public void execute(GetDailySummaryInputData inputData) {
        try {
            List<FoodLog> foodLogs = foodLogGateway.getFoodLogsByDate(
                    inputData.getUserId(),
                    inputData.getDate()
            );

            Macro totalMacro = foodLogs.stream()
                    .map(FoodLog::getActualMacro)
                    .reduce(Macro.ZERO, Macro::add);

            GetDailySummaryOutputData outputData = new GetDailySummaryOutputData(
                    inputData.getDate(),
                    foodLogs,
                    totalMacro
            );

            outputBoundary.presentDailySummary(outputData);

        } catch (Exception e) {
            outputBoundary.presentError("Failed to retrieve daily summary: " + e.getMessage());
        }
    }
}

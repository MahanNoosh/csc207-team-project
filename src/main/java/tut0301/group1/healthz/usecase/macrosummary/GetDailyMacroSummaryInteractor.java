package tut0301.group1.healthz.usecase.macrosummary;

import java.util.List;

import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;
import tut0301.group1.healthz.usecase.food.logging.FoodLogDataAccessInterface;

/**
 * Interactor for Get Daily Calorie Summary use case.
 * This class implements the business logic for retrieving a user's
 * daily nutrition summary, following Clean Architecture principles.
 */
public class GetDailyMacroSummaryInteractor implements GetDailyMacroSummaryInputBoundary {
    private final FoodLogDataAccessInterface foodLogGateway;
    private final GetDailyMacroSummaryOutputBoundary outputBoundary;

    /**
     * Constructor with dependency injection.
     *
     * @param foodLogGateway The gateway for accessing food log data
     * @param outputBoundary The presenter for formatting and presenting output
     * @throws IllegalArgumentException if foodLogGateway or outputBoundary is null
     */
    public GetDailyMacroSummaryInteractor(FoodLogDataAccessInterface foodLogGateway,
                                          GetDailyMacroSummaryOutputBoundary outputBoundary) {
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
    public void execute(GetDailyMacroSummaryInputData inputData) {
        try {
            final List<FoodLog> foodLogs = foodLogGateway.getFoodLogsByDate(
                    inputData.getUserId(),
                    inputData.getDate()
            );

            Macro totalMacro = Macro.ZERO;

            for (FoodLog log : foodLogs) {
                final Macro actual = log.getActualMacro();
                totalMacro = totalMacro.add(actual);
            }

            // Create output data with aggregated macro summary
            final GetDailyMacroSummaryOutputData outputData = new GetDailyMacroSummaryOutputData(
                    inputData.getDate(),
                    totalMacro
            );

            outputBoundary.presentDailySummary(outputData);

        }
        catch (Exception exception) {
            outputBoundary.presentError("Failed to retrieve daily summary: " + exception.getMessage());
        }
    }
}

package tut0301.group1.healthz.interfaceadapter.dailysummary;

import tut0301.group1.healthz.usecase.dailysummary.GetDailySummaryInputBoundary;
import tut0301.group1.healthz.usecase.dailysummary.GetDailySummaryInputData;

import java.time.LocalDate;

/**
 * Controller for Get Daily Summary functionality.
 *
 * Responsibilities:
 * - Receives user input from the View
 * - Creates InputData objects
 * - Calls the Interactor (Use Case)
 *
 * Clean Architecture compliance:
 * - Controller does NOT know about Presenter or ViewModel
 * - Controller only knows about Interactor (through InputBoundary interface)
 * - Data flow: Controller -> Interactor -> Presenter -> ViewModel -> View
 */
public class GetDailySummaryController {

    private final GetDailySummaryInputBoundary interactor;

    public GetDailySummaryController(GetDailySummaryInputBoundary interactor) {
        if (interactor == null) {
            throw new IllegalArgumentException("Interactor cannot be null");
        }
        this.interactor = interactor;
    }

    /**
     * Get daily summary for a specific user and date.
     * This method creates the input data and delegates to the interactor.
     * The interactor will call the presenter to update the view model.
     *
     * @param userId The user ID
     * @param date The date to get summary for
     */
    public void execute(String userId, LocalDate date) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        GetDailySummaryInputData inputData = new GetDailySummaryInputData(userId, date);
        interactor.execute(inputData);
    }

    /**
     * Get daily summary for today.
     *
     * @param userId The user ID
     */
    public void executeToday(String userId) {
        execute(userId, LocalDate.now());
    }
}

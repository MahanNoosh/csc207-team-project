package tutcsc.group1.healthz.interface_adapter.food_log;

import tutcsc.group1.healthz.use_case.food.foodloghistory.GetFoodLogHistoryInputBoundary;
import tutcsc.group1.healthz.use_case.food.foodloghistory.GetFoodLogHistoryInputData;

import java.time.LocalDate;

/**
 * Controller for Get Food Log History functionality.
 *
 * Responsible for handling user requests to retrieve food log history
 * and converting them into use case input.
 *
 * Clean Architecture compliance:
 * - Controller (Interface Adapter layer) depends on InputBoundary (Use Case layer interface)
 * - Controller converts user requests into InputData format
 * - Controller does not know about Presenter or ViewModel
 */
public class GetFoodLogHistoryController {
    private final GetFoodLogHistoryInputBoundary interactor;

    public GetFoodLogHistoryController(GetFoodLogHistoryInputBoundary interactor) {
        if (interactor == null) {
            throw new IllegalArgumentException("Interactor cannot be null");
        }
        this.interactor = interactor;
    }

    /**
     * Execute the use case to retrieve food log history for a specific user and date.
     *
     * @param userId The ID of the user
     * @param date The date to retrieve logs for
     */
    public void execute(String userId, LocalDate date) {
        GetFoodLogHistoryInputData inputData = new GetFoodLogHistoryInputData(userId, date);
        interactor.execute(inputData);
    }

    /**
     * Execute the use case to retrieve today's food log history for a specific user.
     *
     * @param userId The ID of the user
     */
    public void executeToday(String userId) {
        execute(userId, LocalDate.now());
    }
}

package tutcsc.group1.healthz.interface_adapter.food;

import tutcsc.group1.healthz.entities.nutrition.FoodDetails;
import tutcsc.group1.healthz.entities.nutrition.ServingInfo;
import tutcsc.group1.healthz.use_case.food.logging.LogFoodIntakeInputBoundary;
import tutcsc.group1.healthz.use_case.food.logging.LogFoodIntakeInputData;

/**
 * Controller for LogFoodIntake functionality.*
 * Responsibilities:
 * - Receives user input from the View
 * - Creates InputData objects
 * - Calls the Interactor (Use Case)
 * Clean Architecture compliance:
 * - Controller does NOT know about Presenter or ViewModel
 * - Controller only knows about Interactor (through InputBoundary interface)
 * - Data flow: Controller -> Interactor -> Presenter -> ViewModel -> View
 */
public class LogFoodIntakeController {

    private final LogFoodIntakeInputBoundary interactor;

    public LogFoodIntakeController(LogFoodIntakeInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Log food intake.
     * This method creates the input data and delegates to the interactor.
     * The interactor will call the presenter to update the view model.
     *
     * @param userId User ID
     * @param foodDetails Food details including all servings
     * @param selectedServing The serving info selected by the user
     * @param servingMultiplier Number of servings (e.g., 2.5)
     * @param mealType Meal type (Breakfast, Lunch, Dinner, Snack)
     */
    public void logFood(String userId, FoodDetails foodDetails, ServingInfo selectedServing,
                       double servingMultiplier, String mealType) {
        LogFoodIntakeInputData inputData = new LogFoodIntakeInputData(
            userId,
            foodDetails,
            selectedServing,
            servingMultiplier,
            mealType
        );
        interactor.execute(inputData);
    }
}

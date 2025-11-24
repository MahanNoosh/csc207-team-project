package tut0301.group1.healthz.interfaceadapter.macro;

import tut0301.group1.healthz.usecase.food.detail.GetFoodDetailInputBoundary;
import tut0301.group1.healthz.usecase.food.detail.GetFoodDetailInputData;

/**
 * Controller for Macro Detail page.
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
public class MacroDetailController {

    private final GetFoodDetailInputBoundary interactor;

    public MacroDetailController(GetFoodDetailInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Fetch food details by food ID.
     * This method only creates the input data and delegates to the interactor.
     * The interactor will call the presenter to update the view model.
     */
    public void fetch(long foodId) {
        interactor.execute(new GetFoodDetailInputData(foodId));
    }
}
package tut0301.group1.healthz.interfaceadapter.macro;

import tut0301.group1.healthz.usecase.food.search.SearchFoodInputBoundary;
import tut0301.group1.healthz.usecase.food.search.SearchFoodInputData;

/**
 * Controller for Macro Search page.
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
public class MacroSearchController {

    private final SearchFoodInputBoundary interactor;

    public MacroSearchController(SearchFoodInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Execute food search by query.
     * This method only creates the input data and delegates to the interactor.
     * The interactor will call the presenter to update the view model.
     */
    public void search(String query) {
        SearchFoodInputData inputData = new SearchFoodInputData(query);
        interactor.execute(inputData);
    }
}
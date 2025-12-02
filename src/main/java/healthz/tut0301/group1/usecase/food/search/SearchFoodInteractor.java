package healthz.tut0301.group1.usecase.food.search;

import healthz.tut0301.group1.entities.nutrition.BasicFood;

import java.io.IOException;
import java.util.List;

/**
 * Interactor for searching foods by name.
 *
 * This class implements the business logic for food search,
 * following Clean Architecture principles with dependency injection.
 */
public class SearchFoodInteractor implements SearchFoodInputBoundary {
    private final FoodSearchDataAccessInterface gateway;
    private final SearchFoodOutputBoundary outputBoundary;

    /**
     * Constructor with dependency injection.
     *
     * @param gateway the gateway for accessing food data
     * @param outputBoundary the presenter for displaying results
     */
    public SearchFoodInteractor(FoodSearchDataAccessInterface gateway,
                                SearchFoodOutputBoundary outputBoundary) {
        if (gateway == null) {
            throw new IllegalArgumentException("FoodSearchGateway cannot be null");
        }
        if (outputBoundary == null) {
            throw new IllegalArgumentException("SearchFoodOutputBoundary cannot be null");
        }
        this.gateway = gateway;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(SearchFoodInputData inputData) {
        try {
            // Search through the gateway
            List<BasicFood> foods = gateway.searchByName(inputData.getFoodName());

            // Create success output
            SearchFoodOutputData outputData = new SearchFoodOutputData(foods);

            // Pass to presenter
            outputBoundary.presentSearchResults(outputData);

        } catch (IOException | InterruptedException e) {
            // Create error output
            SearchFoodOutputData outputData = new SearchFoodOutputData(
                "Failed to search foods: " + e.getMessage()
            );

            // Pass error to presenter
            outputBoundary.presentSearchResults(outputData);
        }
    }
}

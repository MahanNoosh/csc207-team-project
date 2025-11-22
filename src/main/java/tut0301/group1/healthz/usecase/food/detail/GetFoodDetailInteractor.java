package tut0301.group1.healthz.usecase.food.detail;

import tut0301.group1.healthz.entities.nutrition.FoodDetails;

/**
 * Interactor (Use Case implementation) for retrieving detailed food information.
 *
 * This class contains the business logic for retrieving food details.
 * It depends on abstractions (Gateway and OutputBoundary interfaces)
 * rather than concrete implementations, following the Dependency Inversion Principle.
 */
public class GetFoodDetailInteractor implements GetFoodDetailInputBoundary {
    private final FoodDetailGateway gateway;
    private final GetFoodDetailOutputBoundary outputBoundary;

    /**
     * Constructor with dependency injection.
     *
     * @param gateway the gateway for accessing food data
     * @param outputBoundary the presenter for displaying results
     */
    public GetFoodDetailInteractor(FoodDetailGateway gateway,
                                   GetFoodDetailOutputBoundary outputBoundary) {
        this.gateway = gateway;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(GetFoodDetailInputData inputData) {
        try {
            // Retrieve food details through the gateway
            FoodDetails foodDetails = gateway.getFoodDetails(inputData.getFoodId());

            // Create success output
            GetFoodDetailOutputData outputData = new GetFoodDetailOutputData(foodDetails);

            // Pass to presenter
            outputBoundary.presentFoodDetail(outputData);

        } catch (Exception e) {
            // Create error output
            GetFoodDetailOutputData outputData = new GetFoodDetailOutputData(
                "Failed to retrieve food details: " + e.getMessage()
            );

            // Pass error to presenter
            outputBoundary.presentFoodDetail(outputData);
        }
    }
}

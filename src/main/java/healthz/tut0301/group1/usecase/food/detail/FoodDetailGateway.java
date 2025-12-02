package healthz.tut0301.group1.usecase.food.detail;

import healthz.tut0301.group1.entities.nutrition.FoodDetails;

import java.io.IOException;

/**
 * Gateway interface for retrieving food details.
 *
 * This interface is defined in the Use Case layer and implemented
 * in the Data Access layer, following the Dependency Inversion Principle.
 */
public interface FoodDetailGateway {
    /**
     * Retrieves detailed food information by food ID.
     *
     * @param foodId the unique identifier of the food
     * @return FoodDetails domain entity containing all food information
     * @throws IOException if there's a network or I/O error
     * @throws InterruptedException if the operation is interrupted
     */
    FoodDetails getFoodDetails(long foodId) throws IOException, InterruptedException;
}

package tut0301.group1.healthz.dataaccess.API;

import tut0301.group1.healthz.entities.nutrition.FoodDetails;
import tut0301.group1.healthz.usecase.food.detail.FoodDetailGateway;

import java.io.IOException;

/**
 * Gateway implementation for retrieving food details from FatSecret API.
 *
 * This class is in the Data Access layer and implements the Gateway interface
 * defined in the Use Case layer, following the Dependency Inversion Principle.
 */
public class FatSecretFoodDetailGateway implements FoodDetailGateway {
    private final FatSecretFoodGetClient client;

    public FatSecretFoodDetailGateway() {
        this.client = new FatSecretFoodGetClient();
    }

    @Override
    public FoodDetails getFoodDetails(long foodId) throws IOException, InterruptedException {
        // Get data from FatSecret API
        FatSecretFoodGetClient.FoodDetails apiFoodDetails = client.getFoodDetails(foodId);

        // Map API data to domain entity
        return FatSecretFoodMapper.toDomain(apiFoodDetails);
    }
}

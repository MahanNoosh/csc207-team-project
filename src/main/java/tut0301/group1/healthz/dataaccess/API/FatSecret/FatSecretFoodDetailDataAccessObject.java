package tut0301.group1.healthz.dataaccess.API.FatSecret;

import tut0301.group1.healthz.dataaccess.API.FatSecretFoodMapper;
import tut0301.group1.healthz.entities.nutrition.FoodDetails;
import tut0301.group1.healthz.usecase.food.detail.FoodDetailGateway;

import java.io.IOException;

/**
 * Gateway implementation for retrieving food details from FatSecret API.
 *
 * This class is in the Data Access layer and implements the Gateway interface
 * defined in the Use Case layer, following the Dependency Inversion Principle.
 */
public class FatSecretFoodDetailDataAccessObject implements FoodDetailGateway {
    private final FatSecret client;

    public FatSecretFoodDetailDataAccessObject() {
        this.client = new FatSecret();
    }

    @Override
    public FoodDetails getFoodDetails(long foodId) throws IOException, InterruptedException {
        // Get data from FatSecret API
        FoodDetails apiFoodDetails = client.getFoodDetails(foodId);

        // Map API data to domain entity
        return FatSecretFoodMapper.toDomain(apiFoodDetails);
    }
}

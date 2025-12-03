package tutcsc.group1.healthz.data_access.api.FatSecret;

import tutcsc.group1.healthz.entities.nutrition.FoodDetails;
import tutcsc.group1.healthz.use_case.food.detail.FoodDetailGateway;

import java.io.IOException;

/**
 * Gateway implementation for retrieving food details from FatSecret api.
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
        // Get data from FatSecret api
        return client.getFoodDetails(foodId);
    }
}

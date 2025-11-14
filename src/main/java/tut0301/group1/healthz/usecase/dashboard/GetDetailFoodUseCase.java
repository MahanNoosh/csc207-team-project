package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.dataaccess.API.FatSecretFoodGetClient;

import java.io.IOException;

public class GetDetailFoodUseCase {
    public FatSecretFoodGetClient.FoodDetails execute(long foodId) throws IOException, InterruptedException {


        FatSecretFoodGetClient client = new FatSecretFoodGetClient();
        FatSecretFoodGetClient.FoodDetails details = client.getFoodDetails(foodId);
        return details;

    }
}

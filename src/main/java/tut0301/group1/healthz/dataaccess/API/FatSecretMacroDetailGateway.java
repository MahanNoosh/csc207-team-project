package tut0301.group1.healthz.dataaccess.API;

import tut0301.group1.healthz.entities.nutrition.FoodNutritionDetails;
import tut0301.group1.healthz.usecase.macrosearch.MacroDetailGateway;

import static tut0301.group1.healthz.dataaccess.API.FoodJsonParser.parseFoodDetails;

public class FatSecretMacroDetailGateway implements MacroDetailGateway {

    private final FatSecretFoodSearchGateway foodGateway;

    public FatSecretMacroDetailGateway() {
        this.foodGateway = new FatSecretFoodSearchGateway();
    }

    @Override
    public FoodNutritionDetails fetchDetails(long foodId) throws Exception {
        String token = fetchToken();
        String json = foodGateway.getFoodDetailsById(token, foodId);
        return parseFoodDetails(json);
    }

    private String fetchToken() throws Exception {
        String clientId = EnvConfig.getClientId();
        String clientSecret = EnvConfig.getClientSecret();

        FatSecretOAuthTokenFetcher fetcher = new FatSecretOAuthTokenFetcher(clientId, clientSecret);
        String raw = fetcher.getAccessTokenRaw("basic");
        String token = FatSecretOAuthTokenFetcher.TokenParser.extractAccessToken(raw);

        if (token == null) {
            throw new Exception("Unable to retrieve FatSecret access token");
        }

        return token;
    }
}
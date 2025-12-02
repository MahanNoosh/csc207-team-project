package healthz.tut0301.group1.dataaccess.api.FatSecret;

import healthz.tut0301.group1.dataaccess.api.OAuth.OAuth;
import healthz.tut0301.group1.dataaccess.api.OAuth.OAuthDataAccessObject;
import healthz.tut0301.group1.dataaccess.config.EnvConfig;
import healthz.tut0301.group1.entities.nutrition.BasicFood;
import healthz.tut0301.group1.usecase.food.search.FoodSearchDataAccessInterface;

import java.io.IOException;
import java.util.List;

import static healthz.tut0301.group1.dataaccess.api.FoodJsonParser.parseBasicFoodList;

/**
 * Gateway implementation for FoodSearchGateway using FatSecret api.
 * <p>
 * This class is in the Infrastructure layer (Data Access).
 * It implements the interface defined by the Use Case layer,
 * following the Dependency Inversion Principle.
 * <p>
 * Responsibilities:
 * - Authentication with FatSecret api
 * - Making HTTP requests
 * - Parsing JSON responses
 * - Converting api data to domain entities (BasicFood)
 */
public class FatSecretFoodSearchDataAccessObject implements FoodSearchDataAccessInterface {

    private final FatSecret searchGateway;
    private final OAuth tokenFetcher;

    /**
     * Constructor that accepts dependencies.
     *
     * @param searchGateway The gateway for making api calls
     * @param tokenFetcher  The token fetcher for authentication
     */
    public FatSecretFoodSearchDataAccessObject(FatSecret searchGateway,
                                               OAuth tokenFetcher) {
        this.searchGateway = searchGateway;
        this.tokenFetcher = tokenFetcher;
    }

    /**
     * Convenience constructor that creates dependencies using environment config.
     * This is where environment variables are read - in the Infrastructure layer.
     */
    public FatSecretFoodSearchDataAccessObject() {
        String clientId = EnvConfig.getClientId();
        String clientSecret = EnvConfig.getClientSecret();

        this.tokenFetcher = new OAuth(clientId, clientSecret);
        this.searchGateway = new FatSecret();
    }

    /**
     * Implementation of the FoodSearchGateway interface.
     * Searches for foods by name using the FatSecret api.
     *
     * @param foodName The name of the food to search for
     * @return List of BasicFood entities matching the search
     * @throws IOException          if api call fails
     * @throws InterruptedException if request is interrupted
     */
    @Override
    public List<BasicFood> searchByName(String foodName) throws IOException, InterruptedException {
        // 1. Get OAuth access token
        String jsonResponse = tokenFetcher.getAccessTokenRaw("basic");
        String token = OAuthDataAccessObject.extractAccessToken(jsonResponse);

        // 2. Search for food by name
        String foodJson = searchGateway.searchFoodByName(token, foodName);

        // 3. Parse JSON and convert to List<BasicFood>
        return parseBasicFoodList(foodJson);
    }
}

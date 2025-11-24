package tut0301.group1.healthz.dataaccess.API.FatSecret;

import tut0301.group1.healthz.dataaccess.API.OAuth.OAuth;
import tut0301.group1.healthz.dataaccess.API.OAuth.OAuthDataAccessObject;
import tut0301.group1.healthz.dataaccess.config.EnvConfig;
import tut0301.group1.healthz.entities.nutrition.BasicFood;
import tut0301.group1.healthz.usecase.food.search.FoodSearchDataAccessInterface;

import java.io.IOException;
import java.util.List;

import static tut0301.group1.healthz.dataaccess.API.FoodJsonParser.parseBasicFoodList;

/**
 * Gateway implementation for FoodSearchGateway using FatSecret API.
 * <p>
 * This class is in the Infrastructure layer (Data Access).
 * It implements the interface defined by the Use Case layer,
 * following the Dependency Inversion Principle.
 * <p>
 * Responsibilities:
 * - Authentication with FatSecret API
 * - Making HTTP requests
 * - Parsing JSON responses
 * - Converting API data to domain entities (BasicFood)
 */
public class FatSecretFoodSearchDataAccessObject implements FoodSearchDataAccessInterface {

    private final FatSecret searchGateway;
    private final OAuth tokenFetcher;

    /**
     * Constructor that accepts dependencies.
     *
     * @param searchGateway The gateway for making API calls
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
     * Searches for foods by name using the FatSecret API.
     *
     * @param foodName The name of the food to search for
     * @return List of BasicFood entities matching the search
     * @throws IOException          if API call fails
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

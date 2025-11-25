package tut0301.group1.healthz.dataaccess.API;

import tut0301.group1.healthz.dataaccess.API.OAuth.OAuth;
import tut0301.group1.healthz.dataaccess.API.OAuth.OAuthDataAccessObject;
import tut0301.group1.healthz.dataaccess.config.EnvConfig;
import tut0301.group1.healthz.entities.nutrition.RecipeDetails;
import tut0301.group1.healthz.usecase.recipesearch.detailed.RecipeDetailGateway;
import static tut0301.group1.healthz.dataaccess.API.RecipeJsonParser.parseRecipeDetails;

public class FatSecretRecipeDetailGateway implements RecipeDetailGateway {
    private final FatSecretRecipeSearchGateway recipeGateway;

    public FatSecretRecipeDetailGateway() {
        this.recipeGateway = new FatSecretRecipeSearchGateway();
    }

    @Override
    public RecipeDetails fetchDetails(long recipeId) throws Exception {
        String token = fetchToken();
        String json = recipeGateway.getRecipebyId(token, recipeId);
        return parseRecipeDetails(json);
    }

    private String fetchToken() throws Exception {
        String clientId = EnvConfig.getClientId();
        String clientSecret = EnvConfig.getClientSecret();

        OAuth fetcher = new OAuth(clientId, clientSecret);
        String raw = fetcher.getAccessTokenRaw("basic");
        String token = OAuthDataAccessObject.extractAccessToken(raw);

        if (token == null) {
            throw new Exception("Unable to retrieve FatSecret access token");
        }

        return token;
    }
}

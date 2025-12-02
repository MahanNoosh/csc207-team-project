package healthz.tut0301.group1.dataaccess.api;

import healthz.tut0301.group1.dataaccess.api.OAuth.OAuth;
import healthz.tut0301.group1.dataaccess.api.OAuth.OAuthDataAccessObject;
import healthz.tut0301.group1.dataaccess.config.EnvConfig;
import healthz.tut0301.group1.entities.nutrition.RecipeDetails;
import healthz.tut0301.group1.usecase.recipesearch.detailed.RecipeDetailDataAccessInterface;
import static healthz.tut0301.group1.dataaccess.api.RecipeJsonParser.parseRecipeDetails;

public final class FatSecretRecipeDetailDataAccessObject implements
        RecipeDetailDataAccessInterface {
    /**
     * Recipe search data access object that calls the FatSecret api.
     */
    private final FatSecretRecipeSearchDataAccessObject recipeGateway;

    /**
     * Constructor for the recipe detail data access object.
     */
    public FatSecretRecipeDetailDataAccessObject() {
        this.recipeGateway = new FatSecretRecipeSearchDataAccessObject();
    }

    @Override
    public RecipeDetails fetchDetails(final long recipeId) throws Exception {
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

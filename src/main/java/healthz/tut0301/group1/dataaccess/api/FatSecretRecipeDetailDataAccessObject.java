package healthz.tut0301.group1.dataaccess.api;

import static healthz.tut0301.group1.dataaccess.api.RecipeJsonParser.parseRecipeDetails;

import healthz.tut0301.group1.dataaccess.api.OAuth.OAuth;
import healthz.tut0301.group1.dataaccess.api.OAuth.OAuthDataAccessObject;
import healthz.tut0301.group1.dataaccess.config.EnvConfig;
import healthz.tut0301.group1.entities.nutrition.RecipeDetails;
import healthz.tut0301.group1.usecase.recipesearch.detailed.RecipeDetailDataAccessInterface;

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
        final String token = fetchToken();
        final String json = recipeGateway.getRecipebyId(token, recipeId);
        return parseRecipeDetails(json);
    }

    private String fetchToken() throws Exception {
        final String clientId = EnvConfig.getClientId();
        final String clientSecret = EnvConfig.getClientSecret();

        final OAuth fetcher = new OAuth(clientId, clientSecret);
        final String raw = fetcher.getAccessTokenRaw("basic");
        final String token = OAuthDataAccessObject.extractAccessToken(raw);

        if (token == null) {
            throw new Exception("Unable to retrieve FatSecret access token");
        }
        return token;
    }
}

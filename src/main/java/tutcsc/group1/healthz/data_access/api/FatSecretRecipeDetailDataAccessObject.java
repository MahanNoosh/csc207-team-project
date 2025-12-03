package tutcsc.group1.healthz.data_access.api;

import static tutcsc.group1.healthz.data_access.api.RecipeJsonParser.parseRecipeDetails;

import tutcsc.group1.healthz.data_access.api.oauth.OAuth;
import tutcsc.group1.healthz.data_access.api.oauth.OAuthDataAccessObject;
import tutcsc.group1.healthz.data_access.config.EnvConfig;
import tutcsc.group1.healthz.entities.nutrition.RecipeDetails;
import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailDataAccessInterface;

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

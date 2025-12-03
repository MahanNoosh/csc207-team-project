package tutcsc.group1.healthz.data_access.api;


import tutcsc.group1.healthz.data_access.api.oauth.OAuth;
import tutcsc.group1.healthz.data_access.api.oauth.OAuthDataAccessObject;
import tutcsc.group1.healthz.data_access.config.EnvConfig;
import tutcsc.group1.healthz.entities.nutrition.RecipeDetails;
import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailGateway;

import static tutcsc.group1.healthz.data_access.api.RecipeJsonParser.parseRecipeDetails;

public class FatSecretRecipeDetailDataAccessObject implements RecipeDetailGateway {
    private final FatSecretRecipeSearchDataAccessObject recipeGateway;

    public FatSecretRecipeDetailDataAccessObject() {
        this.recipeGateway = new FatSecretRecipeSearchDataAccessObject();
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

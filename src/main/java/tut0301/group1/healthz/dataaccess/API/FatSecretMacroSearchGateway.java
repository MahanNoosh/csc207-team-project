package tut0301.group1.healthz.dataaccess.API;

import java.io.IOException;
import java.util.List;

import tut0301.group1.healthz.entities.nutrition.MacroSearchResult;
import tut0301.group1.healthz.usecase.macrosearch.MacroSearchGateway;

/**
 * Data-access gateway that fetches macro search results from FatSecret.
 */
public class FatSecretMacroSearchGateway implements MacroSearchGateway {

    private final FatSecretFoodSearchGateway foodGateway;

    public FatSecretMacroSearchGateway() {
        this.foodGateway = new FatSecretFoodSearchGateway();
    }

    @Override
    public List<MacroSearchResult> search(String query) throws Exception {
        String token = fetchToken();
        String json = foodGateway.searchFoodByName(token, query);
        return FoodJsonParser.parseMacroResults(json);
    }

    private String fetchToken() throws IOException, InterruptedException {
        String clientId = EnvConfig.getClientId();
        String clientSecret = EnvConfig.getClientSecret();

        FatSecretOAuthTokenFetcher fetcher = new FatSecretOAuthTokenFetcher(clientId, clientSecret);
        String raw = fetcher.getAccessTokenRaw("basic");
        String token = FatSecretOAuthTokenFetcher.TokenParser.extractAccessToken(raw);

        if (token == null) {
            throw new IOException("Unable to retrieve FatSecret access token");
        }

        return token;
    }
}

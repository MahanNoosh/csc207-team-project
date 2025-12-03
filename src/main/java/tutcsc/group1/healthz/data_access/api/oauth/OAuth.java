package tutcsc.group1.healthz.data_access.api.oauth;


import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Simple class that requests a FatSecret oauth 2.0 access token
 * and prints the raw JSON response (no parsing).
 */
public class OAuth {

    private static final String TOKEN_URL = "https://oauth.fatsecret.com/connect/token";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String clientId;
    private final String clientSecret;

    public OAuth(String clientId, String clientSecret) {
        if (clientId == null || clientSecret == null)
            throw new IllegalArgumentException("Client ID and secret must not be null.");
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /**
     * Fetches an access token from FatSecret and returns the raw JSON body.
     */
    public String getAccessTokenRaw(String scope) throws IOException, InterruptedException {
        String credentials = clientId + ":" + clientSecret;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + encoded;

        String body = "grant_type=client_credentials";
        if (scope != null && !scope.isBlank()) {
            body += "&scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_URL))
                .header("Authorization", authHeader)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Print some debug info
        System.out.println("HTTP Status: " + response.statusCode());
        System.out.println("Response Headers: " + response.headers().map());

        return response.body(); // raw JSON response text
    }

}

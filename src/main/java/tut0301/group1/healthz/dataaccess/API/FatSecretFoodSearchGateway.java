package tut0301.group1.healthz.dataaccess.API;

import okhttp3.*;
import java.io.IOException;

public class FatSecretFoodSearchGateway {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * @param CLIENT_ID FatSecret 的 client_id
     * @param CLIENT_SECRET FatSecret 的 client_secret
     * @param query food name
     * @return API return
     * @throws IOException internet or API error
     */
    public String searchFoods(String CLIENT_ID, String CLIENT_SECRET, String query) throws IOException {
        // 1️⃣  access token
        FatSecretOAuthTokenFetcher fetcher = new FatSecretOAuthTokenFetcher();
        String token = fetcher.FatSecretOAuthTokenFetcher(CLIENT_ID, CLIENT_SECRET);
        System.out.println("Access Token: " + token);

        // 2️⃣  API endpoint（ OAuth token）
        String url = "https://platform.fatsecret.com/rest/server.api";

        // 3️⃣  URL
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder()
                .addQueryParameter("method", "foods.search")
                .addQueryParameter("search_expression", query)
                .addQueryParameter("format", "json")
                .addQueryParameter("max_results", "5");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        // 4️⃣ send back JSON
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response.code());
            }

            String responseBody = response.body() != null ? response.body().string() : "{}";

            System.out.println("=== Raw Response Body ===");
            System.out.println(responseBody);
            System.out.println("==========================");

            return responseBody;
        }
    }
}

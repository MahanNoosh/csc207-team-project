package tut0301.group1.healthz.dataaccess.API;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Base64;

public class FatSecretOAuthTokenFetcher {
    private final OkHttpClient client = new OkHttpClient();

    // getAccessToken
    public String FatSecretOAuthTokenFetcher(String CLIENT_ID, String CLIENT_SECRET) throws IOException {
        String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

        String url = "https://oauth.fatsecret.com/connect/token";

        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("scope", "basic")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", basicAuth)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("HTTP Code: " + response.code());
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            System.out.println("=== Raw Response Body ===");
            System.out.println(responseBody);
            System.out.println("==========================");

            JSONObject json = new JSONObject(responseBody);
            return json.getString("access_token");
        }
    }
}

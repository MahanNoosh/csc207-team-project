package healthz.tut0301.group1.dataaccess.api.OAuth;

import org.json.JSONObject;

public class OAuthDataAccessObject {

    public static String extractAccessToken(String jsonResponse) {
        if (jsonResponse == null || jsonResponse.isBlank()) {
            System.err.println("❌ Empty JSON response!");
            return null;
        }

        try {
            JSONObject json = new JSONObject(jsonResponse);

            if (json.has("access_token")) {
                String token = json.getString("access_token");
                System.out.println("✅ Extracted Access Token: " + token);
                return token;
            } else {
                System.err.println("❌ No 'access_token' field found in JSON.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to parse JSON: " + e.getMessage());
            return null;
        }
    }
}


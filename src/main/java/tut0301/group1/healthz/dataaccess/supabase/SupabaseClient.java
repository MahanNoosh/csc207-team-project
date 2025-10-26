package tut0301.group1.healthz.dataaccess.supabase;

import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.io.IOException;

/**
 * Tiny Supabase client using Java 11 HttpClient + org.json.
 */
public class SupabaseClient {
    private final String supaUrl;   // e.g. https://xxx.supabase.co
    private final String anonKey;   // anon/public key
    private final HttpClient http = HttpClient.newHttpClient();

    private String accessToken;
    private String refreshToken;
    private Instant accessExpiry;

    public SupabaseClient(String supaUrl, String anonKey) {
        this.supaUrl = supaUrl.replaceAll("/+$", "");
        this.anonKey = anonKey;
    }

    // ---------- AUTH ----------
    public void signUpEmail(String email, String password) throws Exception {
        var uri = URI.create(supaUrl + "/auth/v1/signup");
        var body = new JSONObject();
        body.put("email", email);
        body.put("password", password);

        var req = HttpRequest.newBuilder(uri)
                .header("apikey", anonKey).header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8)).build();

        var res = send(req);
        if (res.statusCode() >= 400) throw new RuntimeException("Sign up failed: " + res.body());
    }

    public void signInEmail(String email, String password) throws Exception {
        var uri = URI.create(supaUrl + "/auth/v1/token?grant_type=password");
        var body = new JSONObject();
        body.put("email", email);
        body.put("password", password);

        var req = HttpRequest.newBuilder(uri)
                .header("apikey", anonKey).header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString())).build();

        var res = send(req);
        if (res.statusCode() >= 400) throw new RuntimeException("Sign in failed: " + res.body());

        JSONObject data = new JSONObject(res.body());
        this.accessToken = data.getString("access_token");
        this.refreshToken = data.getString("refresh_token");
        long expiresIn = data.optLong("expires_in", 3600L);
        this.accessExpiry = Instant.now().plusSeconds(Math.max(0, expiresIn - 30));
    }

    public void requestPasswordReset(String email, String redirectUrl) throws Exception {
        var uri = URI.create(supaUrl + "/auth/v1/recover");
        var body = new JSONObject();
        body.put("email", email);
        body.put("redirect_to", redirectUrl);

        var req = HttpRequest.newBuilder(uri)
                .header("apikey", anonKey).header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString())).build();

        var res = send(req);
        if (res.statusCode() >= 400) throw new RuntimeException("Reset request failed: " + res.body());
    }

    /**
     * Minimal user info from /auth/v1/user
     */
    private JSONObject getUserRaw() throws Exception {
        ensureFresh();
        var uri = URI.create(supaUrl + "/auth/v1/user");
        var req = HttpRequest.newBuilder(uri)
                .header("apikey", anonKey).header("Authorization", "Bearer " + accessToken)
                .GET().build();

        var res = send(req);
        if (res.statusCode() >= 400) throw new RuntimeException("Get user failed: " + res.body());

        return new JSONObject(res.body());
    }

    public String getUserId() throws Exception {
        var u = getUserRaw();
        return u.getString("id");
    }

    public String getUserEmail() throws Exception {
        var u = getUserRaw();
        return u.optString("email", "");
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    // ---------- Choices ------------

    public void upsertChoice(String userId, String key, String value) throws Exception {
        var uri = URI.create(supaUrl + "/rest/v1/choices");
        var body = new JSONObject();
        body.put("user_id", userId);
        body.put("key", key);
        body.put("value", value);

        var req = HttpRequest.newBuilder(uri)
                .header("apikey", anonKey)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        var res = send(req);
        if (res.statusCode() >= 400) throw new RuntimeException("Upsert failed: " + res.body());
    }

    public String listChoices() throws Exception {
        var userId = getUserId(); // Get userId from the authenticated user
        var uri = URI.create(supaUrl + "/rest/v1/choices?user_id=eq." + userId);
        var req = HttpRequest.newBuilder(uri)
                .header("apikey", anonKey)
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        var res = send(req);
        if (res.statusCode() >= 400) throw new RuntimeException("List failed: " + res.body());

        return res.body(); // Returns the JSON response as a string
    }

    // ---------- session refresh ----------
    public void refresh() throws Exception {
        if (refreshToken == null) throw new IllegalStateException("No refresh token");
        var uri = URI.create(supaUrl + "/auth/v1/token?grant_type=refresh_token");
        var body = new JSONObject();
        body.put("refresh_token", refreshToken);

        var req = HttpRequest.newBuilder(uri)
                .header("apikey", anonKey).header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString())).build();

        var res = send(req);
        if (res.statusCode() >= 400) throw new RuntimeException("Refresh failed: " + res.body());

        JSONObject data = new JSONObject(res.body());
        this.accessToken = data.getString("access_token");
        this.refreshToken = data.getString("refresh_token");
        long expiresIn = data.optLong("expires_in", 3600L);
        this.accessExpiry = Instant.now().plusSeconds(Math.max(0, expiresIn - 30));
    }

    private void ensureFresh() throws Exception {
        if (accessToken == null) throw new IllegalStateException("Not signed in");
        if (accessExpiry != null && Instant.now().isAfter(accessExpiry)) refresh();
    }

    // ---------- Send method ----------
    public HttpResponse<String> send(HttpRequest req) throws IOException, InterruptedException {
        return http.send(req, HttpResponse.BodyHandlers.ofString());
    }

    public HttpRequest.Builder rest(String endpoint) {
        return HttpRequest.newBuilder()
                .uri(URI.create(supaUrl + "/rest/v1/" + endpoint))
                .header("apikey", anonKey); // Common header for authorization
    }
}

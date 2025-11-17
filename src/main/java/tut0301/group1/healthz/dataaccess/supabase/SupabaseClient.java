package tut0301.group1.healthz.dataaccess.supabase;

import org.json.JSONArray;
import org.json.JSONObject;
import tut0301.group1.healthz.usecase.dashboard.Profile;
import tut0301.group1.healthz.usecase.dashboard.UserDashboardPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.io.IOException;
import java.util.Optional;

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
    private String displayName;

    public SupabaseClient(String supaUrl, String anonKey) {
        this.supaUrl = supaUrl.replaceAll("/+$", "");
        this.anonKey = anonKey;
    }

    // ---------- AUTH ----------
    public void signUpEmail(String email, String password, String displayName) throws Exception {
        var uri = URI.create(supaUrl + "/auth/v1/signup");
        var body = new JSONObject();
        body.put("email", email);
        body.put("password", password);

        JSONObject data = new JSONObject();
        data.put("displayName", displayName);
        body.put("data", data);

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
                .header("apikey", anonKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        var res = send(req);
        if (res.statusCode() >= 400) {
            throw new RuntimeException("Sign in failed: " + res.body());
        }

        JSONObject data = new JSONObject(res.body());
        this.accessToken = data.getString("access_token");
        this.refreshToken = data.getString("refresh_token");
        long expiresIn = data.optLong("expires_in", 3600L);
        this.accessExpiry = Instant.now().plusSeconds(Math.max(0, expiresIn - 30));

        String dn = null;
        JSONObject user = data.optJSONObject("user");
        if (user != null) {
            JSONObject meta = user.optJSONObject("user_metadata");
            if (meta != null) {
                dn = meta.optString("displayName", null);
            }
        }
        this.displayName = dn;
    }

    public void resendSignupVerification(String email) throws Exception {
        var uri = URI.create(supaUrl + "/auth/v1/resend");
        var body = new JSONObject();
        body.put("email", email);
        body.put("type", "signup"); // tells Supabase this is a signup verification resend

        var req = HttpRequest.newBuilder(uri)
                .header("apikey", anonKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        var res = send(req);
        if (res.statusCode() >= 400) {
            throw new RuntimeException("Resend verification failed: " + res.body());
        }
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

    public String getDisplayName() {
        return displayName;
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

    // ---------- User Data ------------
    public Optional<Profile> loadCurrentUserProfile() throws Exception {
        var userId = this.getUserId(); // ensures session
        var uri = URI.create(supaUrl
                + "/rest/v1/user_data?select=*&userId=eq." + userId + "&limit=1");
        var req = HttpRequest.newBuilder(uri)
                .header("apikey", anonKey)
                .header("Authorization", "Bearer " + accessToken)
                .GET().build();

        HttpResponse<String> res = this.send(req);
        if (res.statusCode() >= 400) throw new RuntimeException("Fetch user_data failed: " + res.body());

        var arr = new JSONArray(res.body());
        if (arr.isEmpty()) return Optional.empty();
        return Optional.of(jsonToProfile(arr.getJSONObject(0)));
    }

    public Profile createBlankForCurrentUserIfMissing() throws Exception {
        // Strategy: try to insert {}, which trigger completes with auth.uid().
        // Use upsert that **won’t overwrite** existing data:
        //   - If row exists, we send no updatable columns, so nothing changes.
        var uri = URI.create(supaUrl + "/rest/v1/user_data?on_conflict=userId");
        var req = HttpRequest.newBuilder(uri)
                .header("apikey", anonKey)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                // merge-duplicates: UPSERT; since body has no other cols, it won't clobber existing fields
                .header("Prefer", "resolution=merge-duplicates,return=representation")
                .POST(HttpRequest.BodyPublishers.ofString(new JSONObject().toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> res = this.send(req);
        if (res.statusCode() >= 400) throw new RuntimeException("Init profile failed: " + res.body());

        var arr = new JSONArray(res.body());
        // PostgREST returns the representation; if existing row, body may be [] depending on settings.
        // If empty, fall back to a select:
        if (arr.isEmpty()) {
            return loadCurrentUserProfile()
                    .orElseThrow(() -> new IllegalStateException("Profile not returned after init"));
        }
        return jsonToProfile(arr.getJSONObject(0));
    }

    // ---- mapping helpers ----

    private Profile jsonToProfile(JSONObject r) {
        String userId = r.optString("userId", null);

        Double weightKg = r.isNull("weightKg") ? null : r.getDouble("weightKg");
        Double heightCm = r.isNull("heightCm") ? null : r.getDouble("heightCm");
        Integer ageYears = r.isNull("ageYears") ? null : r.getInt("ageYears");

        UserDashboardPort.Sex sex = r.isNull("sex") ? null :
                parseEnumSafe(UserDashboardPort.Sex.class, r.getString("sex"));

        UserDashboardPort.Goal goal = r.isNull("goal") ? null :
                parseEnumSafe(UserDashboardPort.Goal.class, r.getString("goal"));

        Double activityLevelMET = r.isNull("activityLevelMET") ? null : r.getDouble("activityLevelMET");
        Double targetWeightKg   = r.isNull("targetWeightKg")   ? null : r.getDouble("targetWeightKg");

        java.util.Optional<Double> dailyCalorieTarget =
                r.isNull("dailyCalorieTarget") ? java.util.Optional.empty() :
                        java.util.Optional.of(r.getDouble("dailyCalorieTarget"));

        UserDashboardPort.HealthCondition hc = r.isNull("healthCondition") ? null :
                parseEnumSafe(UserDashboardPort.HealthCondition.class, r.getString("healthCondition"));

        return new Profile(
                userId, weightKg, heightCm, ageYears, sex, goal,
                activityLevelMET, targetWeightKg, dailyCalorieTarget, hc
        );
    }

    private static <E extends Enum<E>> E parseEnumSafe(Class<E> clazz, String raw) {
        if (raw == null) return null;
        var norm = raw.trim().toUpperCase();
        try { return Enum.valueOf(clazz, norm); }
        catch (IllegalArgumentException ex) { return null; }
    }
}

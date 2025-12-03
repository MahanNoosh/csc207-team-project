package tut0301.group1.healthz.dataaccess.supabase;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import org.json.JSONObject;

/**
 * Tiny Supabase client using Java 11 HttpClient and org.json.
 */
public class SupabaseClient {

    private static final String HEADER_API_KEY = "apikey";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String MIME_APPLICATION_JSON = "application/json";
    private static final String AUTH_BEARER_PREFIX = "Bearer ";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DISPLAY_NAME = "displayName";
    private static final String KEY_DATA = "data";
    private static final String KEY_TYPE = "type";
    private static final String KEY_SIGNUP = "signup";
    private static final String KEY_REDIRECT_TO = "redirect_to";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String KEY_USER = "user";
    private static final String KEY_USER_METADATA = "user_metadata";
    private static final String KEY_ID = "id";

    private static final int HTTP_ERROR_STATUS_THRESHOLD = 400;
    private static final long DEFAULT_EXPIRES_IN_SECONDS = 3600L;
    private static final long EXPIRY_SAFETY_MARGIN_SECONDS = 30L;

    /**
     * Base Supabase URL, for example {@code https://xxx.supabase.co}.
     */
    private final String supaUrl;

    /**
     * Supabase anonymous/public API key.
     */
    private final String anonKey;

    private final HttpClient http = HttpClient.newHttpClient();

    private String accessToken;
    private String refreshToken;
    private Instant accessExpiry;
    private String displayName;

    /**
     * Creates a new SupabaseClient.
     *
     * @param supaUrl the base Supabase URL
     * @param anonKey the anonymous/public API key
     */
    public SupabaseClient(final String supaUrl, final String anonKey) {
        this.supaUrl = supaUrl.replaceAll("/+$", "");
        this.anonKey = anonKey;
    }

    /**
     * Signs up a user with email and password.
     *
     * @param email           the email of the new user
     * @param password        the password of the new user
     * @param userDisplayName the display name to store in user metadata
     * @throws Exception        if the HTTP request fails
     * @throws RuntimeException if Supabase returns an error response
     */
    public void signUpEmail(final String email, final String password,
                            final String userDisplayName) throws Exception {
        final URI uri = URI.create(supaUrl + "/auth/v1/signup");

        final JSONObject body = new JSONObject();
        body.put(KEY_EMAIL, email);
        body.put(KEY_PASSWORD, password);

        final JSONObject data = new JSONObject();
        data.put(KEY_DISPLAY_NAME, userDisplayName);
        body.put(KEY_DATA, data);

        final HttpRequest req = HttpRequest.newBuilder(uri)
                .header(HEADER_API_KEY, anonKey)
                .header(HEADER_CONTENT_TYPE, MIME_APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        final HttpResponse<String> res = send(req);
        if (res.statusCode() >= HTTP_ERROR_STATUS_THRESHOLD) {
            throw new RuntimeException("Sign up failed: " + res.body());
        }
    }

    /**
     * Signs in a user using email and password.
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @throws Exception        if the HTTP request fails
     * @throws RuntimeException if Supabase returns an error response
     */
    public void signInEmail(final String email, final String password) throws Exception {
        final URI uri = URI.create(supaUrl + "/auth/v1/token?grant_type=password");

        final JSONObject body = new JSONObject();
        body.put(KEY_EMAIL, email);
        body.put(KEY_PASSWORD, password);

        final HttpRequest req = HttpRequest.newBuilder(uri)
                .header(HEADER_API_KEY, anonKey)
                .header(HEADER_CONTENT_TYPE, MIME_APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        final HttpResponse<String> res = send(req);
        if (res.statusCode() >= HTTP_ERROR_STATUS_THRESHOLD) {
            throw new RuntimeException("Sign in failed: " + res.body());
        }

        final JSONObject data = new JSONObject(res.body());
        accessToken = data.getString(KEY_ACCESS_TOKEN);
        refreshToken = data.getString(KEY_REFRESH_TOKEN);
        final long expiresIn = data.optLong(KEY_EXPIRES_IN, DEFAULT_EXPIRES_IN_SECONDS);
        accessExpiry = Instant.now()
                .plusSeconds(Math.max(0L, expiresIn - EXPIRY_SAFETY_MARGIN_SECONDS));

        String dn = null;
        final JSONObject user = data.optJSONObject(KEY_USER);
        if (user != null) {
            final JSONObject meta = user.optJSONObject(KEY_USER_METADATA);
            if (meta != null) {
                dn = meta.optString(KEY_DISPLAY_NAME, null);
            }
        }
        displayName = dn;
    }

    /**
     * Resends a signup verification email for the given address.
     *
     * @param email the email address to resend verification to
     * @throws Exception        if the HTTP request fails
     * @throws RuntimeException if Supabase returns an error response
     */
    public void resendSignupVerification(final String email) throws Exception {
        final URI uri = URI.create(supaUrl + "/auth/v1/resend");

        final JSONObject body = new JSONObject();
        body.put(KEY_EMAIL, email);
        body.put(KEY_TYPE, KEY_SIGNUP);

        final HttpRequest req = HttpRequest.newBuilder(uri)
                .header(HEADER_API_KEY, anonKey)
                .header(HEADER_CONTENT_TYPE, MIME_APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        final HttpResponse<String> res = send(req);
        if (res.statusCode() >= HTTP_ERROR_STATUS_THRESHOLD) {
            throw new RuntimeException("Resend verification failed: " + res.body());
        }
    }

    /**
     * Requests a password reset email to be sent to the given address.
     *
     * @param email       the email address of the user
     * @param redirectUrl the redirect URL for the reset flow
     * @throws Exception        if the HTTP request fails
     * @throws RuntimeException if Supabase returns an error response
     */
    public void requestPasswordReset(final String email,
                                     final String redirectUrl) throws Exception {
        final URI uri = URI.create(supaUrl + "/auth/v1/recover");

        final JSONObject body = new JSONObject();
        body.put(KEY_EMAIL, email);
        body.put(KEY_REDIRECT_TO, redirectUrl);

        final HttpRequest req = HttpRequest.newBuilder(uri)
                .header(HEADER_API_KEY, anonKey)
                .header(HEADER_CONTENT_TYPE, MIME_APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        final HttpResponse<String> res = send(req);
        if (res.statusCode() >= HTTP_ERROR_STATUS_THRESHOLD) {
            throw new RuntimeException("Reset request failed: " + res.body());
        }
    }

    /**
     * Retrieves minimal user information from {@code /auth/v1/user}.
     *
     * @return the raw user information as a JSON object
     * @throws Exception        if the HTTP request fails
     * @throws RuntimeException if Supabase returns an error response
     */
    private JSONObject getUserRaw() throws Exception {
        ensureFresh();

        final URI uri = URI.create(supaUrl + "/auth/v1/user");
        final HttpRequest req = HttpRequest.newBuilder(uri)
                .header(HEADER_API_KEY, anonKey)
                .header(HEADER_AUTHORIZATION, AUTH_BEARER_PREFIX + accessToken)
                .GET()
                .build();

        final HttpResponse<String> res = send(req);
        if (res.statusCode() >= HTTP_ERROR_STATUS_THRESHOLD) {
            throw new RuntimeException("Get user failed: " + res.body());
        }

        return new JSONObject(res.body());
    }

    /**
     * Returns the current user's unique identifier.
     *
     * @return the user identifier
     * @throws Exception        if the HTTP request fails
     * @throws RuntimeException if Supabase returns an error response
     */
    public String getUserId() throws Exception {
        final JSONObject user = getUserRaw();
        return user.getString(KEY_ID);
    }

    /**
     * Returns the current user's display name.
     *
     * @return the display name, or {@code null} if unknown
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the current user's email address.
     *
     * @return the email address, or an empty string if unavailable
     * @throws Exception        if the HTTP request fails
     * @throws RuntimeException if Supabase returns an error response
     */
    public String getUserEmail() throws Exception {
        final JSONObject user = getUserRaw();
        return user.optString(KEY_EMAIL, "");
    }

    /**
     * Returns the current access token.
     *
     * @return the access token, or {@code null} if not signed in
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Returns the current refresh token.
     *
     * @return the refresh token, or {@code null} if not available
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Refreshes the current session using the stored refresh token.
     *
     * @throws Exception             if the HTTP request fails
     * @throws IllegalStateException if no refresh token is available
     * @throws RuntimeException      if Supabase returns an error response
     */
    public void refresh() throws Exception {
        if (refreshToken == null) {
            throw new IllegalStateException("No refresh token");
        }

        final URI uri = URI.create(supaUrl + "/auth/v1/token?grant_type=refresh_token");

        final JSONObject body = new JSONObject();
        body.put(KEY_REFRESH_TOKEN, refreshToken);

        final HttpRequest req = HttpRequest.newBuilder(uri)
                .header(HEADER_API_KEY, anonKey)
                .header(HEADER_CONTENT_TYPE, MIME_APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        final HttpResponse<String> res = send(req);
        if (res.statusCode() >= HTTP_ERROR_STATUS_THRESHOLD) {
            throw new RuntimeException("Refresh failed: " + res.body());
        }

        final JSONObject data = new JSONObject(res.body());
        accessToken = data.getString(KEY_ACCESS_TOKEN);
        refreshToken = data.getString(KEY_REFRESH_TOKEN);
        final long expiresIn = data.optLong(KEY_EXPIRES_IN, DEFAULT_EXPIRES_IN_SECONDS);
        accessExpiry = Instant.now()
                .plusSeconds(Math.max(0L, expiresIn - EXPIRY_SAFETY_MARGIN_SECONDS));
    }

    /**
     * Ensures that the access token is still valid, refreshing it if necessary.
     *
     * @throws Exception             if refreshing fails
     * @throws IllegalStateException if no access token is present
     */
    private void ensureFresh() throws Exception {
        if (accessToken == null) {
            throw new IllegalStateException("Not signed in");
        }
        if (accessExpiry != null && Instant.now().isAfter(accessExpiry)) {
            refresh();
        }
    }

    /**
     * Sends an HTTP request using the underlying HttpClient.
     *
     * @param req the HTTP request to send
     * @return the HTTP response with a string body
     * @throws IOException          if an I/O error occurs when sending or receiving
     * @throws InterruptedException if the operation is interrupted
     */
    public HttpResponse<String> send(final HttpRequest req)
            throws IOException, InterruptedException {
        return http.send(req, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Creates a new HttpRequest builder for a REST endpoint under {@code /rest/v1}.
     *
     * @param endpoint the endpoint name under {@code /rest/v1}
     * @return a preconfigured HttpRequest.Builder for the given endpoint
     */
    public HttpRequest.Builder rest(final String endpoint) {
        return HttpRequest.newBuilder()
                .uri(URI.create(supaUrl + "/rest/v1/" + endpoint))
                .header(HEADER_API_KEY, anonKey);
    }
}

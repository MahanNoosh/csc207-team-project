package tut0301.group1.healthz.dataaccess.supabase;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.interfaceadapter.auth.mapping.ProfileJsonMapper;
import tut0301.group1.healthz.usecase.dashboard.UserDataDataAccessInterface;

/**
 * Data access object that implements {@link UserDataDataAccessInterface} using Supabase's REST API.
 */
public class SupabaseUserDataDataAccessObject implements UserDataDataAccessInterface {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_PREFER = "Prefer";
    private static final String MIME_APPLICATION_JSON = "application/json";
    private static final String AUTH_BEARER_PREFIX = "Bearer ";
    private static final String PREFER_INIT =
            "resolution=merge-duplicates,return=representation";
    private static final String PREFER_UPDATE = "return=representation";

    private static final int HTTP_ERROR_STATUS_THRESHOLD = 400;

    private final SupabaseClient client;

    /**
     * Creates a new SupabaseUserDataDataAccessObject.
     *
     * @param supabaseClient the Supabase client used to communicate with the backend
     */
    public SupabaseUserDataDataAccessObject(final SupabaseClient supabaseClient) {
        this.client = supabaseClient;
    }

    /**
     * Loads the current user's profile from Supabase.
     *
     * @return an {@link Optional} containing the profile if it exists, otherwise empty
     * @throws Exception        if the HTTP request fails
     * @throws RuntimeException if Supabase returns an error response
     */
    @Override
    public Optional<Profile> loadCurrentUserProfile() throws Exception {
        final String userId = client.getUserId();
        final String endpoint =
                "user_data?select=" + UserDataFields.projection()
                        + "&userId=eq." + userId + "&limit=1";

        final HttpRequest req = client.rest(endpoint)
                .header(HEADER_AUTHORIZATION, AUTH_BEARER_PREFIX + client.getAccessToken())
                .GET()
                .build();

        final HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= HTTP_ERROR_STATUS_THRESHOLD) {
            throw new RuntimeException("Fetch user_data failed: " + res.body());
        }

        final JSONArray arr = new JSONArray(res.body());

        final Optional<Profile> result;
        if (arr.isEmpty()) {
            result = Optional.empty();
        }
        else {
            result = Optional.of(ProfileJsonMapper.fromRow(arr.getJSONObject(0)));
        }

        return result;
    }

    /**
     * Creates a blank profile for the current user if one does not already exist.
     *
     * @return the created or existing profile
     * @throws Exception           if the HTTP request fails
     * @throws RuntimeException    if Supabase returns an error response
     * @throws IllegalStateException if no profile is returned after initialization
     */
    @Override
    public Profile createBlankForCurrentUserIfMissing() throws Exception {
        client.getUserId();

        final String endpoint = "user_data?on_conflict=userId";
        final HttpRequest req = client.rest(endpoint)
                .header(HEADER_AUTHORIZATION, AUTH_BEARER_PREFIX + client.getAccessToken())
                .header(HEADER_CONTENT_TYPE, MIME_APPLICATION_JSON)
                .header(HEADER_PREFER, PREFER_INIT)
                .POST(HttpRequest.BodyPublishers.ofString(
                        new JSONObject().toString(), StandardCharsets.UTF_8))
                .build();

        final HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= HTTP_ERROR_STATUS_THRESHOLD) {
            throw new RuntimeException("Init profile failed: " + res.body());
        }

        final JSONArray arr = new JSONArray(res.body());

        final Profile result;
        if (arr.isEmpty()) {
            result = loadCurrentUserProfile()
                    .orElseThrow(() -> new IllegalStateException("Profile not returned after init"));
        }
        else {
            result = ProfileJsonMapper.fromRow(arr.getJSONObject(0));
        }

        return result;
    }

    /**
     * Inserts or updates the given profile for the current user.
     *
     * @param profile the profile to upsert
     * @throws Exception             if the HTTP request fails
     * @throws RuntimeException      if Supabase returns an error response
     * @throws IllegalStateException if no profile is returned after upsert
     */
    public void upsertProfile(final Profile profile) throws Exception {
        final String currentUserId = client.getUserId();
        final Profile toSave = new Profile(
                currentUserId,
                profile.getWeightKg(),
                profile.getHeightCm(),
                profile.getAgeYears(),
                profile.getSex(),
                profile.getGoal(),
                profile.getActivityLevelMET(),
                profile.getTargetWeightKg(),
                profile.getDailyCalorieTarget(),
                profile.getHealthCondition()
        );

        final String endpoint = "user_data?on_conflict=" + UserDataFields.USER_ID;

        final JSONObject body = ProfileJsonMapper.toRow(toSave);

        final HttpRequest req = client.rest(endpoint)
                .header(HEADER_AUTHORIZATION, AUTH_BEARER_PREFIX + client.getAccessToken())
                .header(HEADER_CONTENT_TYPE, MIME_APPLICATION_JSON)
                .header(HEADER_PREFER, PREFER_INIT)
                .POST(HttpRequest.BodyPublishers.ofString(
                        body.toString(), StandardCharsets.UTF_8))
                .build();

        final HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= HTTP_ERROR_STATUS_THRESHOLD) {
            throw new RuntimeException("Upsert user_data failed: " + res.body());
        }
    }

    /**
     * Updates the profile row for the current user.
     *
     * @param profile the new profile values
     * @return the updated profile
     * @throws Exception           if the HTTP request fails
     * @throws RuntimeException    if Supabase returns an error response
     * @throws IllegalStateException if no profile row is returned after update
     */
    @Override
    public Profile updateCurrentUserProfile(final Profile profile) throws Exception {
        final String currentUserId = client.getUserId();

        final Profile toSave = new Profile(
                currentUserId,
                profile.getWeightKg(),
                profile.getHeightCm(),
                profile.getAgeYears(),
                profile.getSex(),
                profile.getGoal(),
                profile.getActivityLevelMET(),
                profile.getTargetWeightKg(),
                profile.getDailyCalorieTarget(),
                profile.getHealthCondition()
        );

        final String endpoint = "user_data?userId=eq." + currentUserId;

        final JSONObject body = ProfileJsonMapper.toRow(toSave);

        final HttpRequest req = client.rest(endpoint)
                .header(HEADER_AUTHORIZATION, AUTH_BEARER_PREFIX + client.getAccessToken())
                .header(HEADER_CONTENT_TYPE, MIME_APPLICATION_JSON)
                .header(HEADER_PREFER, PREFER_UPDATE)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(
                        body.toString(), StandardCharsets.UTF_8))
                .build();

        final HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= HTTP_ERROR_STATUS_THRESHOLD) {
            throw new RuntimeException("Update user_data failed: " + res.body());
        }

        final JSONArray arr = new JSONArray(res.body());

        final Profile result;
        if (arr.isEmpty()) {
            throw new IllegalStateException("No profile row returned after update");
        }
        else {
            result = ProfileJsonMapper.fromRow(arr.getJSONObject(0));
        }

        return result;
    }
}

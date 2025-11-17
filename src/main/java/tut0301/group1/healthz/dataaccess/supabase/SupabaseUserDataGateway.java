package tut0301.group1.healthz.dataaccess.supabase;

import org.json.JSONArray;
import org.json.JSONObject;
import tut0301.group1.healthz.interfaceadapter.auth.mapping.ProfileJsonMapper;
import tut0301.group1.healthz.usecase.dashboard.Profile;
import tut0301.group1.healthz.usecase.dashboard.UserDataGateway;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Adapter: implements UserDataGateway using Supabase's REST API.
 */
public class SupabaseUserDataGateway implements UserDataGateway {
    private final SupabaseClient client;

    public SupabaseUserDataGateway(SupabaseClient client) {
        this.client = client;
    }

    @Override
    public Optional<Profile> loadCurrentUserProfile() throws Exception {
        String userId = client.getUserId(); // ensures session/token
        String endpoint =
                "user_data?select=" + UserDataFields.projection() +
                        "&userId=eq." + userId + "&limit=1";

        HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .GET()
                .build();

        HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= 400) {
            throw new RuntimeException("Fetch user_data failed: " + res.body());
        }

        JSONArray arr = new JSONArray(res.body());
        if (arr.length() == 0) return Optional.empty();
        return Optional.of(ProfileJsonMapper.fromRow(arr.getJSONObject(0)));
    }

    @Override
    public Profile createBlankForCurrentUserIfMissing() throws Exception {
        client.getUserId(); // ensures valid session/token

        String endpoint = "user_data?on_conflict=userId";
        HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .header("Content-Type", "application/json")
                .header("Prefer", "resolution=merge-duplicates,return=representation")
                .POST(HttpRequest.BodyPublishers.ofString(new JSONObject().toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= 400) {
            throw new RuntimeException("Init profile failed: " + res.body());
        }

        JSONArray arr = new JSONArray(res.body());
        if (arr.length() == 0) {
            return loadCurrentUserProfile()
                    .orElseThrow(() -> new IllegalStateException("Profile not returned after init"));
        }
        return ProfileJsonMapper.fromRow(arr.getJSONObject(0));
    }

    public Profile upsertProfile(Profile profile) throws Exception {
        // Ensure we use the current user's id
        String currentUserId = client.getUserId();
        Profile toSave = new Profile(
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

        String endpoint = "user_data?on_conflict=" + UserDataFields.USER_ID;

        JSONObject body = ProfileJsonMapper.toRow(toSave);

        HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .header("Content-Type", "application/json")
                .header("Prefer", "resolution=merge-duplicates,return=representation")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= 400) {
            throw new RuntimeException("Upsert user_data failed: " + res.body());
        }

        JSONArray arr = new JSONArray(res.body());
        if (arr.length() == 0) {
            return loadCurrentUserProfile()
                    .orElseThrow(() -> new IllegalStateException("Profile not returned after upsert"));
        }
        return ProfileJsonMapper.fromRow(arr.getJSONObject(0));
    }
}

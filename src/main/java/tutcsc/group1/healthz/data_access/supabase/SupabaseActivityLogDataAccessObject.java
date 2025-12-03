package tutcsc.group1.healthz.data_access.supabase;

import org.json.JSONArray;
import org.json.JSONObject;
import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;
import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.interface_adapter.activity.mapping.ActivityLogJsonMapper;
import tutcsc.group1.healthz.use_case.activity.activitylog.ActivityLogDataAccessInterface;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for activity log persistence in Supabase.
 * Handles reading and writing activity log entries for the authenticated user.
 */
public class SupabaseActivityLogDataAccessObject implements ActivityLogDataAccessInterface {

    private static final Logger LOGGER =
            Logger.getLogger(SupabaseActivityLogDataAccessObject.class.getName());
    public static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    private final SupabaseClient client;

    public SupabaseActivityLogDataAccessObject(final SupabaseClient client) {
        this.client = client;
    }

    /**
     * Saves a new activity log entry for the given user profile.
     *
     * @param entry   the activity log to save
     * @param profile the user's profile (used to attach user_id)
     * @throws Exception if the request or mapping fails
     */
    @Override
    public void saveActivityLog(final ActivityEntry entry, final Profile profile) throws Exception {
        final String endpoint = "activity_logs";

        JSONObject body = ActivityLogJsonMapper.toRow(entry);
        body.put("user_id", profile.getUserId());

        HttpRequest request = client.rest(endpoint)
                .header(AUTHORIZATION, BEARER + " " + client.getAccessToken())
                .header("Prefer", "return=representation")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request);

        if (response.statusCode() >= 400) {
            LOGGER.log(Level.SEVERE, "Failed to save activity log: {0}", response.body());
            throw new RuntimeException("Failed to save activity log: " + response.body());
        }

        JSONArray arr = new JSONArray(response.body());
        if (arr.isEmpty()) {
            throw new RuntimeException("Supabase did not return the inserted log.");
        }

        LOGGER.info("✅ Activity log successfully saved to Supabase.");
        ActivityLogJsonMapper.fromRow(arr.getJSONObject(0));
    }

    /**
     * Fetches the list of activity logs for the current user within the last 31 days.
     *
     * @return list of {@link ActivityEntry}
     * @throws Exception if the request or parsing fails
     */
    @Override
    public List<ActivityEntry> getActivitiesForUser() throws Exception {
        final String endpoint = "activity_logs?user_id=eq." + client.getUserId()
                + "&timestamp::date=gte." + LocalDate.now().minusDays(31)
                + "&order=timestamp.desc";

        HttpRequest request = client.rest(endpoint)
                .header(AUTHORIZATION, BEARER + " " + client.getAccessToken())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request);

        if (response.statusCode() >= 400) {
            LOGGER.log(Level.SEVERE, "Failed to load recent activities: {0}", response.body());
            throw new RuntimeException("Failed to load recent activities: " + response.body());
        }

        JSONArray arr = new JSONArray(response.body());
        List<ActivityEntry> logs = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            logs.add(ActivityLogJsonMapper.fromRow(arr.getJSONObject(i)));
        }

        LOGGER.info(() -> "✅ Loaded " + logs.size() + " recent activity logs.");
        return logs;
    }

    /**
     * Retrieves a weekly activity summary (total minutes per day of week).
     *
     * @return map of {@link DayOfWeek} to total minutes
     * @throws Exception if Supabase query or parsing fails
     */
    @Override
    public Map<DayOfWeek, Double> getWeeklyActivitySummary() throws Exception {
        final String userId = client.getUserId();

        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate nextMonday = monday.plusDays(7);

        OffsetDateTime startUtc = monday.atStartOfDay(ZoneId.systemDefault())
                .toInstant().atOffset(ZoneOffset.UTC);
        OffsetDateTime endUtc = nextMonday.atStartOfDay(ZoneId.systemDefault())
                .toInstant().atOffset(ZoneOffset.UTC);

        final String endpoint = String.format(
                "activity_logs?user_id=eq.%s&timestamp=gte.%s&timestamp=lt.%s",
                userId, startUtc, endUtc
        );

        HttpRequest request = client.rest(endpoint)
                .header(AUTHORIZATION, BEARER + " " + client.getAccessToken())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request);

        if (response.statusCode() >= 400) {
            LOGGER.log(Level.SEVERE, "Failed to fetch weekly summary: {0}", response.body());
            throw new RuntimeException("Failed to fetch weekly summary: " + response.body());
        }

        JSONArray arr = new JSONArray(response.body());
        Map<DayOfWeek, Double> minutes = new EnumMap<>(DayOfWeek.class);

        for (int i = 0; i < arr.length(); i++) {
            JSONObject row = arr.getJSONObject(i);
            OffsetDateTime timestamp = OffsetDateTime.parse(row.getString("timestamp"));
            double duration = row.optDouble("duration_minutes", 0);
            DayOfWeek day = timestamp.toLocalDate().getDayOfWeek();
            minutes.merge(day, duration, Double::sum);
        }

        LOGGER.info(() -> "✅ Weekly summary fetched for user " + userId);
        return minutes;
    }
}

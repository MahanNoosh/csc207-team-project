/**
 * Provides Supabase-based data access objects for activity logging.
 *
 * <p>This package contains classes that interact with Supabase REST
 * endpoints to store and retrieve user activity information.</p>
 */

package tutcsc.group1.healthz.data_access.supabase;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;
import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.interface_adapter.activity.mapping.ActivityLogJsonMapper;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogDataAccessInterface;

/**
 * Data Access Object responsible for communicating with Supabase
 * to persist and retrieve activity log information.
 *
 * <p>This DAO implements the {@link ActivityLogDataAccessInterface} and
 * performs REST operations via {@link SupabaseClient}.</p>
 */
public class SupabaseActivityLogDataAccessObject
        implements ActivityLogDataAccessInterface {

    /** Header key for Authorization. */
    public static final String AUTHORIZATION = "Authorization";

    /** Authorization Bearer prefix. */
    private static final String BEARER = "Bearer";

    /** Header key for content type. */
    private static final String CONTENT_TYPE = "Content-Type";

    /** JSON content type. */
    private static final String APPLICATION_JSON = "application/json";

    /** Minimum status code considered failure. */
    private static final int STATUS = 400;

    /** Number of days to add to compute weekly range. */
    private static final int DAYS_TO_ADD = 7;

    /** Space separator for header concatenation. */
    private static final String SPACE = " ";

    /** Logger for debugging and error reporting. */
    private static final Logger LOGGER =
            Logger.getLogger(
                    SupabaseActivityLogDataAccessObject.class.getName());

    /** Supabase REST client used to send HTTP requests. */
    private final SupabaseClient client;

    /**
     * Creates a new DAO instance using the provided Supabase client.
     *
     * @param supabaseClient the Supabase client to send API requests through
     */
    public SupabaseActivityLogDataAccessObject(
            final SupabaseClient supabaseClient) {
        this.client = supabaseClient;
    }

    /**
     * Saves a new activity log entry for the given user profile.
     *
     * @param entry   the activity log to save
     * @param profile the user's profile (used to attach user_id)
     * @throws Exception if the request or mapping fails
     */
    @Override
    public void saveActivityLog(final ActivityEntry entry,
                                final Profile profile) throws Exception {
        final String endpoint = "activity_logs";
        final JSONObject body = ActivityLogJsonMapper.toRow(entry);
        body.put("user_id", profile.getUserId());

        final HttpRequest request = client.rest(endpoint)
                .header(AUTHORIZATION, BEARER + SPACE + client.getAccessToken())
                .header("Prefer", "return=representation")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers
                        .ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        final HttpResponse<String> response = client.send(request);

        if (response.statusCode() >= STATUS) {
            LOGGER.log(Level.SEVERE,
                    "Failed to save activity log: {0}",
                    response.body());
            throw new RuntimeException(
                    "Failed to save activity log: " + response.body());
        }

        final JSONArray arr = new JSONArray(response.body());
        if (arr.isEmpty()) {
            throw new RuntimeException(
                    "Supabase did not return the inserted log.");
        }

        LOGGER.info("Activity log successfully saved to Supabase.");
        ActivityLogJsonMapper.fromRow(arr.getJSONObject(0));
    }

    /**
     * Fetches the list of activity logs
     * for the current user within the last 31 days.
     *
     * @return list of {@link ActivityEntry}
     * @throws Exception if the request or parsing fails
     */
    @Override
    public List<ActivityEntry> getActivitiesForUser() throws Exception {
        final String endpoint = "activity_logs?user_id=eq." + client.getUserId()
                + "&timestamp::date=gte." + LocalDate.now().minusDays(31)
                + "&order=timestamp.desc";

        final HttpRequest request = client.rest(endpoint)
                .header(AUTHORIZATION, BEARER + SPACE + client.getAccessToken())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .GET()
                .build();

        final HttpResponse<String> response = client.send(request);
        if (response.statusCode() >= STATUS) {
            LOGGER.log(Level.SEVERE,
                    "Failed to load recent activities: {0}", response.body());
            throw new RuntimeException(
                    "Failed to load recent activities: " + response.body());
        }

        final JSONArray arr = new JSONArray(response.body());
        final List<ActivityEntry> logs = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            logs.add(ActivityLogJsonMapper.fromRow(arr.getJSONObject(i)));
        }

        LOGGER.info(() -> "Loaded " + logs.size() + " recent activity logs.");
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
        final LocalDate today = LocalDate.now();
        final LocalDate monday = today.with(java.time.temporal
                .TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        final LocalDate nextMonday = monday.plusDays(DAYS_TO_ADD);

        final OffsetDateTime startUtc = monday
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant().atOffset(ZoneOffset.UTC);
        final OffsetDateTime endUtc = nextMonday
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant().atOffset(ZoneOffset.UTC);

        final String endpoint = String.format(
                "activity_logs?user_id=eq.%s&timestamp=gte.%s&timestamp=lt.%s",
                userId, startUtc, endUtc
        );

        final HttpRequest request = client.rest(endpoint)
                .header(AUTHORIZATION, BEARER + SPACE + client.getAccessToken())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .GET()
                .build();

        final HttpResponse<String> response = client.send(request);

        if (response.statusCode() >= STATUS) {
            LOGGER.log(Level.SEVERE,
                    "Failed to fetch weekly summary: {0}", response.body());
            throw new RuntimeException(
                    "Failed to fetch weekly summary: " + response.body());
        }

        final JSONArray arr = new JSONArray(response.body());
        final Map<DayOfWeek, Double> minutes = new EnumMap<>(DayOfWeek.class);

        for (int i = 0; i < arr.length(); i++) {
            final JSONObject row = arr.getJSONObject(i);
            final OffsetDateTime timestamp = OffsetDateTime
                    .parse(row.getString("timestamp"));
            final double duration = row.optDouble("duration_minutes", 0);
            final DayOfWeek day = timestamp.toLocalDate().getDayOfWeek();
            minutes.merge(day, duration, Double::sum);
        }

        LOGGER.info(() -> "Weekly summary fetched for user " + userId);
        return minutes;
    }
}

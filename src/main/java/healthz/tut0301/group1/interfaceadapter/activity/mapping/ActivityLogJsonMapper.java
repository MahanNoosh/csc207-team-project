package healthz.tut0301.group1.interfaceadapter.activity.mapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import org.json.JSONObject;
import healthz.tut0301.group1.entities.Dashboard.ActivityEntry;

/**
 * Maps between JSON representations and {@link ActivityEntry} entities.
 * Provides bidirectional conversion between Supabase rows and domain entities.
 */
public final class ActivityLogJsonMapper {

    private ActivityLogJsonMapper() {
        // Prevent instantiation
    }

    /**
     * Converts a JSON object from Supabase into an {@link ActivityEntry}.
     *
     * @param json the JSON object representing a Supabase activity log row
     * @return an {@link ActivityEntry} instance
     */
    public static ActivityEntry fromRow(final JSONObject json) {
        final String ts = json.getString("timestamp");
        LocalDateTime timestamp;

        try {
            timestamp = OffsetDateTime.parse(ts).toLocalDateTime();
        } catch (Exception e) {
            // Fallback for inconsistent timestamp formats
            timestamp = LocalDateTime.parse(ts.split("\\.")[0]);
        }

        return new ActivityEntry(
                json.getLong("activity_id"),
                json.getInt("duration_minutes"),
                json.getDouble("calories_burned"),
                timestamp
        );
    }

    /**
     * Converts an {@link ActivityEntry} entity into a JSON object for Supabase storage.
     *
     * @param log the {@link ActivityEntry} instance to convert
     * @return a JSON object representing the activity entry
     */
    public static JSONObject toRow(final ActivityEntry log) {
        final JSONObject json = new JSONObject();
        json.put("activity_id", log.getActivityId());
        json.put("duration_minutes", log.getDurationMinutes());
        json.put("calories_burned", log.getCaloriesBurned());
        json.put("timestamp", log.getTimestamp().toString());
        return json;
    }
}

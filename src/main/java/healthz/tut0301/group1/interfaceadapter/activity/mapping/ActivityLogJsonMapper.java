package healthz.tut0301.group1.interfaceadapter.activity.mapping;

import org.json.JSONObject;
import healthz.tut0301.group1.entities.Dashboard.ActivityEntry;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class ActivityLogJsonMapper {

    public static ActivityEntry fromRow(JSONObject json) {
        String ts = json.getString("timestamp");
        LocalDateTime timestamp;

        try {
            timestamp = OffsetDateTime.parse(ts).toLocalDateTime();
        } catch (Exception e) {
            timestamp = LocalDateTime.parse(ts.split("\\.")[0]); // fallback if format changes
        }

        return new ActivityEntry(
                json.getLong("activity_id"),
                json.getInt("duration_minutes"),
                json.getDouble("calories_burned"),
                timestamp
        );
    }


    public static JSONObject toRow(ActivityEntry log) {
        JSONObject json = new JSONObject();
        json.put("activity_id", log.getActivityId());
        json.put("duration_minutes", log.getDurationMinutes());
        json.put("calories_burned", log.getCaloriesBurned());
        json.put("timestamp", log.getTimestamp().toString());
        return json;
    }

}

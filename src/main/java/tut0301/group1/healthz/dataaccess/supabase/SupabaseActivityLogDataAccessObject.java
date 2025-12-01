package tut0301.group1.healthz.dataaccess.supabase;

import org.json.JSONArray;
import org.json.JSONObject;
import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;
import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.interfaceadapter.activity.mapping.ActivityLogJsonMapper;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogDataAccessInterface;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.EnumMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class SupabaseActivityLogDataAccessObject implements ActivityLogDataAccessInterface {
    private final SupabaseClient client;

    public SupabaseActivityLogDataAccessObject(SupabaseClient client)
    {
        this.client = client;
    }

    @Override
    public ActivityEntry saveActivityLog(ActivityEntry entry, Profile profile) throws Exception {
        String endpoint = "activity_logs";

        // Inject userId before sending to Supabase
        JSONObject body = ActivityLogJsonMapper.toRow(entry);

        body.put("user_id", profile.getUserId());

        HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .header("Prefer", "return=representation")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> res = client.send(req);

        JSONArray arr = new JSONArray(res.body());

        if (arr.length() == 0) {
            throw new RuntimeException("Supabase did not return the inserted log.");
        }

        return ActivityLogJsonMapper.fromRow(arr.getJSONObject(0));


    }

    @Override
    public List<ActivityEntry> getActivitiesForUser() throws Exception {

        String endpoint = "activity_logs?user_id=eq." + client.getUserId() + "&timestamp::date=gte." + LocalDate.now().minusDays(31)
                + "&order=timestamp.desc";



        HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> res = client.send(req);

        if (res.statusCode() >= 400) {
            throw new RuntimeException("Failed to load recent activities: " + res.body());
        }
        JSONArray arr = new JSONArray(res.body());

        List<ActivityEntry> logs = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            logs.add(ActivityLogJsonMapper.fromRow(arr.getJSONObject(i)));
        }
        return logs;
    }

    @Override
    public Map<DayOfWeek, Double> getWeeklyActivitySummary() throws Exception {
        String userId = client.getUserId();

        LocalDate todayLocal = LocalDate.now();
        LocalDate mondayLocal = todayLocal.with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate nextMondayLocal = mondayLocal.plusDays(7);

        // Convert to UTC timestamps for Supabase query
        OffsetDateTime startUtc = mondayLocal.atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .atOffset(ZoneOffset.UTC);

        OffsetDateTime endUtc = nextMondayLocal.atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .atOffset(ZoneOffset.UTC);


        String endpoint = String.format(
                "activity_logs?user_id=eq.%s&timestamp=gte.%s&timestamp=lt.%s",
                userId, startUtc, endUtc
        );

        HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> res = client.send(req);

        if (res.statusCode() >= 400)
            throw new RuntimeException("Failed: " + res.body());

        JSONArray arr = new JSONArray(res.body());
        Map<DayOfWeek, Double> minutes = new EnumMap<>(DayOfWeek.class);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject row = arr.getJSONObject(i);
            OffsetDateTime ts = OffsetDateTime.parse(row.getString("timestamp"));
            double dur = row.optDouble("duration_minutes", 0);
            DayOfWeek day = ts.toLocalDate().getDayOfWeek();
            minutes.put(day, minutes.getOrDefault(day, 0.0) + dur);
        }
        return minutes;
    }

}


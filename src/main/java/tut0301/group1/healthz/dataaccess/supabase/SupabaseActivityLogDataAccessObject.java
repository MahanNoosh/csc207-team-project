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
import java.util.List;
import java.util.ArrayList;

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
                .header("Prefer", "return=representation")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> res = client.send(req);
        // Debug log to see exactly what Supabase says
        JSONArray arr = new JSONArray(res.body());

        if (arr.length() == 0) {
            throw new RuntimeException("Supabase did not return the inserted log.");
        }

        return ActivityLogJsonMapper.fromRow(arr.getJSONObject(0));


    }

    @Override
    public List<ActivityEntry> getActivitiesForUser() throws Exception {
        String endpoint = "activity_logs?user_id=eq." + client.getUserId();

        HttpRequest req = client.rest(endpoint)
                .GET()
                .build();

        HttpResponse<String> res = client.send(req);

        JSONArray arr = new JSONArray(res.body());
        List<ActivityEntry> logs = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            logs.add(ActivityLogJsonMapper.fromRow(arr.getJSONObject(i)));
        }
        return logs;
    }
}


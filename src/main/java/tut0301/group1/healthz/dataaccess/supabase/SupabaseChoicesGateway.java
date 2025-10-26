package tut0301.group1.healthz.dataaccess.supabase;

import tut0301.group1.healthz.usecase.choices.ChoicesGateway;
import org.json.JSONObject;
import org.json.JSONArray;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter: talk to /rest/v1/choices with RLS using user's token.
 */
public class SupabaseChoicesGateway implements ChoicesGateway {
    private final SupabaseClient client;

    public SupabaseChoicesGateway(SupabaseClient client) {
        this.client = client;
    }

    @Override
    public void upsert(String userId, String key, String value) throws Exception {
        // Create the JSON body for the upsert request
        JSONObject body = new JSONObject();
        body.put("user_id", userId);
        body.put("key", key);
        body.put("value", value);

        var req = client.rest("choices")
                .header("Content-Type", "application/json")
                .header("Prefer", "resolution=merge-duplicates,return=representation")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        try {
            client.send(req); // Sends the request and checks for errors
        } catch (Exception e) {
            throw new RuntimeException("Upsert failed: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ChoiceRow> list() throws Exception {
        var req = client.rest("choices?select=key,value,created_at&order=created_at.desc")
                .header("Accept", "application/json")
                .GET().build();

        var res = client.send(req);

        // Parse the response body into a JSONArray
        JSONArray rows = new JSONArray(res.body());
        var out = new ArrayList<ChoiceRow>();

        for (int i = 0; i < rows.length(); i++) {
            JSONObject r = rows.getJSONObject(i);
            out.add(new ChoiceRow(
                    r.getString("key"),
                    r.getString("value"),
                    r.getString("created_at")
            ));
        }

        return out;
    }
}

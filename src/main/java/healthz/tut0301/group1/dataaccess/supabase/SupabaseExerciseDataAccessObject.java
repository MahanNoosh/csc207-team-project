package healthz.tut0301.group1.dataaccess.supabase;

import org.json.JSONArray;
import org.json.JSONObject;
import healthz.tut0301.group1.entities.Dashboard.Exercise;
import healthz.tut0301.group1.usecase.activity.exercisefinder.ExerciseDataAccessInterface;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SupabaseExerciseDataAccessObject implements ExerciseDataAccessInterface {
    private final SupabaseClient client;

    public SupabaseExerciseDataAccessObject(SupabaseClient client) {
        this.client = client;
    }

    @Override
    public Exercise fetchExerciseByExactName(String exerciseName) {
        try {
            String endpoint = "exercises_met?exercise=eq." +
                    URLEncoder.encode(exerciseName, StandardCharsets.UTF_8);

            HttpRequest req = client.rest(endpoint).GET().build();

            HttpResponse<String> res = client.send(req);

            JSONArray arr = new JSONArray(res.body());
            if (arr.length() == 0) {
                throw new RuntimeException("Exercise not found with name: " + exerciseName);
            }

            JSONObject row = arr.getJSONObject(0);
            return new Exercise(
                    row.getString("exercise"),
                    row.getLong("id"),
                    row.getDouble("met")
            );

        } catch (Exception e) {
            throw new RuntimeException("Error fetching exercise by name", e);
        }
    }

    @Override
    public Exercise fetchExerciseByExactId(long id) {
        try {
            String endpoint = "exercises_met?id=eq." + id;

            HttpRequest req = client.rest(endpoint).GET().build();

            HttpResponse<String> res = client.send(req);

            JSONArray arr = new JSONArray(res.body());
            if (arr.length() == 0) {
                throw new RuntimeException("Exercise not found with id: " + id);
            }

            JSONObject row = arr.getJSONObject(0);
            return new Exercise(
                    row.getString("exercise"),
                    row.getLong("id"),
                    row.getDouble("met")
            );

        } catch (Exception e) {
            throw new RuntimeException("Error fetching exercise by id", e);
        }
    }

    @Override
    public List<String> fetchAllExercisesNames() {
        try {
            String endpoint = "exercises_met?select=exercise";

            HttpRequest req = client.rest(endpoint).GET().build();
            HttpResponse<String> res = client.send(req);

            JSONArray arr = new JSONArray(res.body());
            List<String> names = new java.util.ArrayList<>();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject row = arr.getJSONObject(i);
                names.add(row.getString("exercise"));
            }

            return names;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching all exercise names", e);
        }
    }

    @Override
    public List<String> searchExercisesByQuery(String query) {
        try {
            String endpoint = "exercises_met?select=exercise&exercise=ilike.*" +
                    URLEncoder.encode(query + "*", StandardCharsets.UTF_8);


            HttpRequest req = client.rest(endpoint).GET().build();

            HttpResponse<String> res = client.send(req);

            JSONArray arr = new JSONArray(res.body());
            List<String> names = new ArrayList<>();

            for (int i = 0; i < arr.length(); i++) {
                names.add(arr.getJSONObject(i).getString("exercise"));
            }

            return names;

        } catch (Exception e) {
            throw new RuntimeException("Error searching exercises", e);
        }
    }

}

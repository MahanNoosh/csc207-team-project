package tutcsc.group1.healthz.data_access.supabase;

import org.json.JSONArray;
import org.json.JSONObject;
import tutcsc.group1.healthz.entities.dashboard.Exercise;
import tutcsc.group1.healthz.use_case.activity.exercisefinder.ExerciseDataAccessInterface;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles communication with Supabase to retrieve and search exercise data.
 * Provides methods to fetch exercises by name, ID, or search query.
 */
public class SupabaseExerciseDataAccessObject implements ExerciseDataAccessInterface {

    private static final Logger LOGGER =
            Logger.getLogger(SupabaseExerciseDataAccessObject.class.getName());
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String EXERCISE = "exercise";

    private final SupabaseClient client;

    /**
     * Constructs a new SupabaseExerciseDataAccessObject.
     *
     * @param client the Supabase client used for making API requests
     */
    public SupabaseExerciseDataAccessObject(final SupabaseClient client) {
        this.client = client;
    }

    /**
     * Retrieves an exercise by its exact name.
     *
     * @param exerciseName the exact exercise name
     * @return the matching Exercise entity
     */
    @Override
    public Exercise fetchExerciseByExactName(final String exerciseName) {
        try {
            String endpoint = "exercises_met?exercise=eq."
                    + URLEncoder.encode(exerciseName, StandardCharsets.UTF_8);

            HttpRequest request = client.rest(endpoint)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request);
            JSONArray arr = new JSONArray(response.body());

            if (arr.isEmpty()) {
                throw new RuntimeException("Exercise not found with name: " + exerciseName);
            }

            JSONObject row = arr.getJSONObject(0);
            Exercise exercise = new Exercise(
                    row.getString(EXERCISE),
                    row.getLong("id"),
                    row.getDouble("met")
            );

            LOGGER.info(() -> "Fetched exercise by name: " + exerciseName);
            return exercise;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format("Error fetching exercise by name: : %s", exerciseName), e);
            throw new RuntimeException("Error fetching exercise by name", e);
        }
    }

    /**
     * Retrieves an exercise by its numeric ID.
     *
     * @param id the exercise ID
     * @return the matching Exercise entity
     */
    @Override
    public Exercise fetchExerciseByExactId(final long id) {
        try {
            String endpoint = "exercises_met?id=eq." + id;

            HttpRequest request = client.rest(endpoint)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request);
            JSONArray arr = new JSONArray(response.body());

            if (arr.isEmpty()) {
                throw new RuntimeException("Exercise not found with id: " + id);
            }

            JSONObject row = arr.getJSONObject(0);
            Exercise exercise = new Exercise(
                    row.getString(EXERCISE),
                    row.getLong("id"),
                    row.getDouble("met")
            );

            LOGGER.info(() -> "Fetched exercise by ID: " + id);
            return exercise;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format("Error fetching exercise by id: %s", id), e);
            throw new RuntimeException("Error fetching exercise by id", e);
        }
    }

    /**
     * Retrieves all exercise names available in Supabase.
     *
     * @return a list of exercise names
     */
    @Override
    public List<String> fetchAllExercisesNames() {
        try {
            String endpoint = "exercises_met?select=exercise";

            HttpRequest request = client.rest(endpoint)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request);
            JSONArray arr = new JSONArray(response.body());

            List<String> names = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                names.add(arr.getJSONObject(i).getString(EXERCISE));
            }

            LOGGER.info(() -> "Fetched all exercise names (" + names.size() + ")");
            return names;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching all exercise names", e);
            throw new RuntimeException("Error fetching all exercise names", e);
        }
    }

    /**
     * Searches for exercises containing the query text (case-insensitive).
     *
     * @param query the text to search for
     * @return a list of exercise names that match the query
     */
    @Override
    public List<String> searchExercisesByQuery(final String query) {
        try {
            String endpoint = "exercises_met?select=exercise&exercise=ilike.*"
                    + URLEncoder.encode(query + "*", StandardCharsets.UTF_8);

            HttpRequest request = client.rest(endpoint)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request);
            JSONArray arr = new JSONArray(response.body());

            List<String> names = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                names.add(arr.getJSONObject(i).getString(EXERCISE));
            }

            LOGGER.info(() -> "Found " + names.size() + " exercises for query: " + query);
            return names;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format("Error searching exercises for query: %s", query), e);
            throw new RuntimeException("Error searching exercises", e);
        }
    }
}

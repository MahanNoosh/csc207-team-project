/**
 * Provides data access classes for interacting with Supabase.
 * This package contains DAO implementations used to communicate
 * with the Supabase backend for fetching and storing application data.
 */
package tutcsc.group1.healthz.data_access.supabase;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import tutcsc.group1.healthz.entities.dashboard.Exercise;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseDataAccessInterface;

/**
 * Handles communication with Supabase to retrieve and search exercise data.
 * Provides methods to fetch exercises by name, ID, or search query.
 */
public class SupabaseExerciseDataAccessObject
        implements ExerciseDataAccessInterface {

    /**
     * Logger used to record information and error messages.
     */
    private static final Logger LOGGER =
            Logger.getLogger(SupabaseExerciseDataAccessObject.class.getName());

    /**
     * Standard HTTP header key for specifying content type.
     */
    private static final String CONTENT_TYPE = "Content-Type";

    /**
     * Standard HTTP content type for JSON payloads.
     */
    private static final String APPLICATION_JSON = "application/json";

    /**
     * The JSON key used to retrieve the exercise name field
     * from Supabase response objects.
     */
    private static final String EXERCISE = "exercise";


    /**
     * Client used to perform REST API calls to Supabase.
     */
    private final SupabaseClient client;

    /**
     * Constructs a new SupabaseExerciseDataAccessObject.
     *
     * @param supabaseClient the Supabase client used for making API requests
     */
    public SupabaseExerciseDataAccessObject(
            final SupabaseClient supabaseClient) {
        this.client = supabaseClient;
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
            final String endpoint = "exercises_met?exercise=eq."
                    + URLEncoder.encode(exerciseName, StandardCharsets.UTF_8);

            final HttpRequest request = client.rest(endpoint)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .GET()
                    .build();

            final HttpResponse<String> response = client.send(request);
            final JSONArray arr = new JSONArray(response.body());

            if (arr.isEmpty()) {
                throw new RuntimeException(
                        "Exercise not found with name: " + exerciseName);
            }

            final JSONObject row = arr.getJSONObject(0);
            final Exercise exercise = new Exercise(
                    row.getString(EXERCISE),
                    row.getLong("id"),
                    row.getDouble("met")
            );

            LOGGER.info(() -> "Fetched exercise by name: " + exerciseName);
            return exercise;

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    "Thread interrupted while fetching exercise by name",
                    exception);
        } catch (IOException | org.json.JSONException exception) {
            throw new RuntimeException(
                    "Error fetching exercise by name", exception);
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
            final String endpoint = "exercises_met?id=eq." + id;

            final HttpRequest request = client.rest(endpoint)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .GET()
                    .build();

            final HttpResponse<String> response = client.send(request);
            final JSONArray arr = new JSONArray(response.body());

            if (arr.isEmpty()) {
                throw new RuntimeException("Exercise not found with id: " + id);
            }

            final JSONObject row = arr.getJSONObject(0);
            final Exercise exercise = new Exercise(
                    row.getString(EXERCISE),
                    row.getLong("id"),
                    row.getDouble("met")
            );

            LOGGER.info(() -> "Fetched exercise by ID: " + id);
            return exercise;

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    "Thread interrupted while fetching exercise by id",
                    exception);
        } catch (IOException | org.json.JSONException exception) {
            throw new RuntimeException(
                    "Error fetching exercise by id", exception);
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
            final String endpoint = "exercises_met?select=exercise";

            final HttpRequest request = client.rest(endpoint)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .GET()
                    .build();

            final HttpResponse<String> response = client.send(request);
            final JSONArray arr = new JSONArray(response.body());

            final List<String> names = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                names.add(arr.getJSONObject(i).getString(EXERCISE));
            }

            LOGGER.info(() -> {
                return "Fetched all exercise names ("
                        + names.size() + ")";
            });
            return names;

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    "Thread interrupted while fetching all exercise names",
                    exception);
        } catch (IOException | org.json.JSONException exception) {
            throw new RuntimeException(
                    "Error fetching all exercise names", exception);
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
            final String endpoint =
                    "exercises_met?select=exercise&exercise=ilike.*"
                    + URLEncoder.encode(query + "*", StandardCharsets.UTF_8);

            final HttpRequest request = client.rest(endpoint)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .GET()
                    .build();

            final HttpResponse<String> response = client.send(request);
            final JSONArray arr = new JSONArray(response.body());

            final List<String> names = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                names.add(arr.getJSONObject(i).getString(EXERCISE));
            }

            LOGGER.info(() -> {
                return "Found " + names.size()
                        + " exercises for query: " + query;
            });
            return names;

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    "Thread interrupted while searching exercises", exception);
        } catch (IOException | org.json.JSONException exception) {
            throw new RuntimeException("Error searching exercises", exception);
        }
    }
}

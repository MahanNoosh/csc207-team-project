package tutcsc.group1.healthz.use_case.activity.exercise_finder;

import java.util.List;

import tutcsc.group1.healthz.entities.dashboard.Exercise;

/**
 * Interface for accessing exercise data from the data source.
 * Provides methods to fetch exercises by name, ID, or search queries.
 */
public interface ExerciseDataAccessInterface {

    /**
     * Fetches an exercise by its exact name.
     *
     * @param name the exact name of the exercise
     * @return the {@link Exercise} matching the name, or null if not found
     */
    Exercise fetchExerciseByExactName(String name);

    /**
     * Fetches an exercise by its unique ID.
     *
     * @param id the ID of the exercise
     * @return the {@link Exercise} with the specified ID, or null if not found
     */
    Exercise fetchExerciseByExactId(long id);

    /**
     * Retrieves the names of all exercises.
     *
     * @return a list of exercise names
     */
    List<String> fetchAllExercisesNames();

    /**
     * Searches exercises by a query string.
     * Can match partially or with case-insensitive
     * comparison depending on implementation.
     *
     * @param query the search query
     * @return a list of exercise names matching the query
     */
    List<String> searchExercisesByQuery(String query);
}

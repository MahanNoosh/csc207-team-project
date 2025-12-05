package tutcsc.group1.healthz.use_case.activity.exercise_finder;

import tutcsc.group1.healthz.entities.dashboard.Exercise;

/**
 * Input boundary for the Exercise Finder use case.
 * Defines the operations that
 * can be invoked by controllers or other interactors.
 */
public interface ExerciseFinderInputBoundary {

    /**
     * Finds an exercise by its exact name.
     *
     * @param exerciseName the exact name of the exercise
     * @return the {@link Exercise} matching the name
     * @throws Exception if the exercise cannot be found or an error occurs
     */
    Exercise findExerciseByName(String exerciseName) throws Exception;

    /**
     * Finds an exercise by its unique ID.
     *
     * @param id the ID of the exercise
     * @return the {@link Exercise} with the specified ID
     * @throws Exception if the exercise cannot be found or an error occurs
     */
    Exercise findExerciseById(long id) throws Exception;

    /**
     * Retrieves all exercise names.
     * The results should be passed to
     * the corresponding output boundary for presentation.
     */
    void findAllExercisesNames();

    /**
     * Searches for exercises using
     * a query string wrapped in {@link ExerciseInputData}.
     *
     * @param inputData the search query input data
     */
    void searchExercisesByQuery(ExerciseInputData inputData);
}

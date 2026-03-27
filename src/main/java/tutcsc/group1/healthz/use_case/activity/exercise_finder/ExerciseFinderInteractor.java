package tutcsc.group1.healthz.use_case.activity.exercise_finder;

import java.util.List;

import tutcsc.group1.healthz.entities.dashboard.Exercise;

/**
 * Interactor for the Exercise Finder use case.
 * Handles retrieving exercises from the data access layer
 * and passing results to the presenter.
 */
public class ExerciseFinderInteractor implements ExerciseFinderInputBoundary {

    /** Interface for accessing exercise data. */
    private final ExerciseDataAccessInterface exerciseDataAccess;

    /** Output boundary for presenting exercise data. */
    private final ExerciseFinderOutputBoundary presenter;

    /**
     * Constructs an ExerciseFinderInteractor.
     *
     * @param exerciseDataAccessInterface the data access
     *                                   interface for exercises
     * @param finderOutputBoundary the output boundary for presenting results
     */
    public ExerciseFinderInteractor(
            final ExerciseDataAccessInterface exerciseDataAccessInterface,
            final ExerciseFinderOutputBoundary finderOutputBoundary) {
        this.exerciseDataAccess = exerciseDataAccessInterface;
        this.presenter = finderOutputBoundary;
    }

    /**
     * Finds an exercise by its exact name.
     *
     * @param exerciseName the name of the exercise
     * @return the {@link Exercise} matching the given name
     */
    @Override
    public Exercise findExerciseByName(
            final String exerciseName) {
        if (exerciseName == null || exerciseName.isEmpty()) {
            throw new IllegalArgumentException(
                    "Exercise name must not be empty");
        }
        return exerciseDataAccess.fetchExerciseByExactName(exerciseName);
    }

    /**
     * Finds an exercise by its unique ID.
     *
     * @param id the ID of the exercise
     * @return the {@link Exercise} with the given ID
     */
    @Override
    public Exercise findExerciseById(final long id) {
        return exerciseDataAccess.fetchExerciseByExactId(id);
    }

    /**
     * Retrieves all exercise names and passes them to the presenter.
     */
    @Override
    public void findAllExercisesNames() {
        final List<String> names = exerciseDataAccess.fetchAllExercisesNames();
        presenter.presentExerciseList(new ExerciseListOutputData(names));
    }

    /**
     * Searches for exercises based on
     * the given query and passes the results to the presenter.
     *
     * @param exerciseInputData the input data containing the search query
     */
    @Override
    public void searchExercisesByQuery(
            final ExerciseInputData exerciseInputData) {
        final List<String> result =
                exerciseDataAccess.searchExercisesByQuery(
                        exerciseInputData.getQuery());
        presenter.presentExerciseList(new ExerciseListOutputData(result));
    }
}

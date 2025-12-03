package tutcsc.group1.healthz.use_case.activity.exercise_finder;

/**
 * Input data for searching exercises.
 * Encapsulates the search query provided by the user.
 */
public class ExerciseInputData {

    /** The search query string used to filter exercises. */
    private final String query;

    /**
     * Constructs an ExerciseInputData instance with the given query.
     *
     * @param searchQuery the search string used to find matching exercises
     */
    public ExerciseInputData(final String searchQuery) {
        this.query = searchQuery;
    }

    /**
     * Returns the search query string.
     *
     * @return the query
     */
    public String getQuery() {
        return query;
    }
}

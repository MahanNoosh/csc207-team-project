package tutcsc.group1.healthz.use_case.activity.exercise_finder;

import java.util.List;

/**
 * Output data representing a list of exercise names.
 * Passed from the interactor to the presenter to display exercise options.
 */
public class ExerciseListOutputData {

    /** The list of exercise names. */
    private final List<String> namesList;

    /**
     * Constructs an ExerciseListOutputData instance with the given names.
     *
     * @param names a list of exercise names
     */
    public ExerciseListOutputData(final List<String> names) {
        this.namesList = names;
    }

    /**
     * Returns the list of exercise names.
     *
     * @return the exercise names
     */
    public List<String> getNamesList() {
        return namesList;
    }
}

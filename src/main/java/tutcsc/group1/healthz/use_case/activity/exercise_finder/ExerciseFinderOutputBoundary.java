package tutcsc.group1.healthz.use_case.activity.exercise_finder;

/**
 * Output boundary for the Exercise Finder use case.
 * Responsible for presenting exercise search
 *          results to the view/presenter layer.
 */
public interface ExerciseFinderOutputBoundary {

    /**
     * Presents a list of exercises to the output layer.
     *
     * @param output an {@link ExerciseListOutputData}
     *               object containing the list of exercises
     */
    void presentExerciseList(ExerciseListOutputData output);

}

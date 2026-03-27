package tutcsc.group1.healthz.use_case.activity.activity_log;

/**
 * Data class representing input for logging an activity.
 * Holds the exercise name and duration in minutes.
 */
public class ActivityLogInputData {

    /** Name of the exercise performed. */
    private final String exerciseName;

    /** Duration of the exercise in minutes. */
    private final int durationMinutes;

    /**
     * Constructs a new ActivityLogInputData instance.
     *
     * @param name    the name of the exercise
     * @param minutes the duration of the exercise in minutes
     */
    public ActivityLogInputData(final String name, final int minutes) {
        this.exerciseName = name;
        this.durationMinutes = minutes;
    }

    /**
     * Returns the name of the exercise.
     *
     * @return the exercise name
     */
    public String getExerciseName() {
        return exerciseName;
    }

    /**
     * Returns the duration of the exercise in minutes.
     *
     * @return the exercise duration
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }
}

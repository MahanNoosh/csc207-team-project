package tutcsc.group1.healthz.use_case.activity.activitylog;

/**
 * Input data for logging an activity.
 * Contains the exercise name and duration in minutes.
 */
public class ActivityLogInputData {

    private final String exerciseName;
    private final int durationMinutes;

    /**
     * Constructs an ActivityLogInputData instance.
     *
     * @param exerciseName    The name of the exercise performed.
     * @param durationMinutes The duration of the exercise in minutes.
     */
    public ActivityLogInputData(String exerciseName, int durationMinutes) {
        this.exerciseName = exerciseName;
        this.durationMinutes = durationMinutes;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
}

package tut0301.group1.healthz.usecase.activity.activitylog;

/**
 * Output data representing a saved activity log.
 * Passed from the interactor to the presenter to update the view.
 */
public class ActivityLogSaveOutputData {

    private final String exerciseName;
    private final int durationMinutes;
    private final int calories;
    private final String timestamp;

    /**
     * Constructs an output data object for a saved activity log.
     *
     * @param exerciseName     the name of the exercise performed
     * @param durationMinutes  the duration in minutes
     * @param calories         the calories burned
     * @param timestamp        the time the activity was logged, formatted as a string
     */
    public ActivityLogSaveOutputData(String exerciseName, int durationMinutes,
                                     int calories, String timestamp) {
        this.exerciseName = exerciseName;
        this.durationMinutes = durationMinutes;
        this.calories = calories;
        this.timestamp = timestamp;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getCalories() {
        return calories;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

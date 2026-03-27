package tutcsc.group1.healthz.use_case.activity.activity_log;

/**
 * Represents the output data for a successfully saved activity log.
 * <p>
 * This class is used by the interactor to transfer details of a logged
 * activity to the presenter so that the view can be updated.
 * </p>
 */
public class ActivityLogSaveOutputData {

    /**
     * The name of the exercise that was performed.
     */
    private final String exerciseName;

    /**
     * The duration of the exercise in minutes.
     */
    private final int durationMinutes;

    /**
     * The calories burned during the exercise.
     */
    private final int calories;

    /**
     * The timestamp of when the activity was logged, formatted as a string.
     */
    private final String timestamp;

    /**
     * Constructs an {@code ActivityLogSaveOutputData} object representing
     * a saved activity log.
     *
     * @param name the name of the exercise performed
     * @param minutes the duration of the exercise in minutes
     * @param calorie the calories burned during the exercise
     * @param time the time the activity was logged, formatted as a string
     */
    public ActivityLogSaveOutputData(final String name,
                                     final int minutes,
                                     final int calorie,
                                     final String time) {
        this.exerciseName = name;
        this.durationMinutes = minutes;
        this.calories = calorie;
        this.timestamp = time;
    }

    /**
     * Returns the name of the exercise that was performed.
     *
     * @return the exercise name
     */
    public String getExerciseName() {
        return exerciseName;
    }

    /**
     * Returns the duration of the exercise in minutes.
     *
     * @return the duration in minutes
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * Returns the calories burned during the exercise.
     *
     * @return the number of calories burned
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Returns the timestamp of when the activity was logged.
     *
     * @return the timestamp as a formatted string
     */
    public String getTimestamp() {
        return timestamp;
    }
}

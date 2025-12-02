package healthz.tut0301.group1.entities.Dashboard;

import java.time.LocalDateTime;

/**
 * Represents a single recorded physical activity entry.
 * Each entry contains the activity ID, duration, calories burned, and timestamp.
 */
public class ActivityEntry {

    private final long activityId;
    private final int durationMinutes;
    private final double caloriesBurned;
    private final LocalDateTime timestamp;

    /**
     * Constructs an ActivityEntry object.
     *
     * @param activityId the unique ID of the activity
     * @param durationMinutes the duration of the activity in minutes
     * @param caloriesBurned the total calories burned during the activity
     * @param timestamp the date and time when the activity occurred
     */
    public ActivityEntry(
            final long activityId,
            final int durationMinutes,
            final double caloriesBurned,
            final LocalDateTime timestamp) {

        this.activityId = activityId;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
        this.timestamp = timestamp;
    }

    /** @return the unique activity ID */
    public long getActivityId() {
        return activityId;
    }

    /** @return the activity duration in minutes */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /** @return the total calories burned */
    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    /** @return the timestamp when the activity occurred */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

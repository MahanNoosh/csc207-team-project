/**
 * Contains entity classes used for displaying
 * and structuring dashboard-related activity data.
 */

package tutcsc.group1.healthz.entities.dashboard;

import java.time.LocalDateTime;

/**
 * Represents a single recorded physical activity entry.
 * Each entry contains the activity ID,
 * duration, calories burned, and timestamp.
 */
public class ActivityEntry {

    /**
     * The unique identifier of the activity performed.
     */
    private final long activityId;

    /**
     * The duration of the activity in minutes.
     */
    private final int durationMinutes;

    /**
     * The number of calories burned during the activity.
     */
    private final double caloriesBurned;

    /**
     * The date and time at which the activity was performed.
     */
    private final LocalDateTime timestamp;

    /**
     * Constructs an ActivityEntry object.
     *
     * @param id             the unique ID of the activity
     * @param minutes        the duration of the activity in minutes
     * @param burnedCalories the total calories burned during the activity
     * @param dateTime       the date and time when the activity occurred
     */
    public ActivityEntry(
            final long id,
            final int minutes,
            final double burnedCalories,
            final LocalDateTime dateTime) {

        this.activityId = id;
        this.durationMinutes = minutes;
        this.caloriesBurned = burnedCalories;
        this.timestamp = dateTime;
    }

    /**
     * Returns the unique ID of the activity.
     *
     * @return the activity ID
     */
    public long getActivityId() {
        return activityId;
    }

    /**
     * Returns the duration of the activity in minutes.
     *
     * @return the duration in minutes
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * Returns the total number of calories burned during the activity.
     *
     * @return the calories burned
     */
    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    /**
     * Returns the date and time when the activity took place.
     *
     * @return the timestamp of the activity
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

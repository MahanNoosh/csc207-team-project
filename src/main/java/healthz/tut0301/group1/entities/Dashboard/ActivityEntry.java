package healthz.tut0301.group1.entities.Dashboard;

import java.time.LocalDateTime;

public class ActivityEntry {
    private final long activityId;
    private final int durationMinutes;
    private final double caloriesBurned;
    private final LocalDateTime timestamp;


    public ActivityEntry(long activityId, int durationMinutes, double caloriesBurned, LocalDateTime timestamp) {
        this.activityId = activityId;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
        this.timestamp = timestamp;

    }
    public long getActivityId() {
        return activityId;
    }
    public int getDurationMinutes() {
        return durationMinutes;
    }
    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

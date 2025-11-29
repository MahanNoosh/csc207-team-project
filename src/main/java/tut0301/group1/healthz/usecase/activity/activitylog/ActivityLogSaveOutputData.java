package tut0301.group1.healthz.usecase.activity.activitylog;

import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;

public class ActivityLogSaveOutputData {
    private final String exerciseName;
    private final int durationMinutes;
    private final int calories;
    private final String timestamp;

    public ActivityLogSaveOutputData(String exerciseName, int durationMinutes,
                                     int calories, String timestamp) {
        this.exerciseName = exerciseName;
        this.durationMinutes = durationMinutes;
        this.calories = calories;
        this.timestamp = timestamp;
    }

    public String getExerciseName() { return exerciseName; }
    public int getDurationMinutes() { return durationMinutes; }
    public int getCalories() { return calories; }
    public String getTimestamp() { return timestamp; }
}

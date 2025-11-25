package tut0301.group1.healthz.entities.Dashboard;

import java.time.LocalDateTime;

public class ActivityEntry {
    private final Exercise exercise;
    private final int durationMinutes;
    private final double caloriesBurned;
    private final LocalDateTime timestamp;


    public ActivityEntry(Exercise exercise, int durationMinutes, double caloriesBurned, LocalDateTime timestamp) {
        this.exercise = exercise;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
        this.timestamp = timestamp;

    }
    public Exercise getExercise() {
        return exercise;
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

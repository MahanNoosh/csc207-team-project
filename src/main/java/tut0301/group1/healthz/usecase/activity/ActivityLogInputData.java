package tut0301.group1.healthz.usecase.activity;

public class ActivityLogInputData {
    private final String exerciseName;
    private final int durationMinutes;

    public  ActivityLogInputData(String exerciseName, int durationMinutes) {
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

package healthz.tut0301.group1.usecase.activity.caloriecalculator;

public class CalorieCalculatorInputData {
    private final String exerciseName;
    private final int durationMinutes;

    public CalorieCalculatorInputData(String exerciseName, int durationMinutes) {
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

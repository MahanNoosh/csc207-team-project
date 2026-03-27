package tutcsc.group1.healthz.use_case.activity.calorie_calculator;

/**
 * Input data for the Calorie Calculator use case.
 * Encapsulates the details of an activity for which calorie expenditure
 * should be calculated, such as exercise type and duration.
 */
public class CalorieCalculatorInputData {

    /** The name of the exercise being performed. */
    private final String exerciseName;

    /** The duration of the exercise in minutes. */
    private final int durationMinutes;

    /**
     * Constructs an instance of CalorieCalculatorInputData.
     *
     * @param name    the name of the exercise
     * @param minutes the duration of the exercise in minutes
     */
    public CalorieCalculatorInputData(final String name, final int minutes) {
        this.exerciseName = name;
        this.durationMinutes = minutes;
    }

    /**
     * Returns the name of the exercise.
     *
     * @return the exercise name
     */
    public String getExerciseName() {
        return exerciseName;
    }

    /**
     * Returns the duration of the exercise in minutes.
     *
     * @return the exercise duration
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }
}

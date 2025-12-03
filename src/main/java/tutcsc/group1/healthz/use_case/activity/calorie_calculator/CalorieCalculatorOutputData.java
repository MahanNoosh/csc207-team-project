package tutcsc.group1.healthz.use_case.activity.calorie_calculator;

/**
 * Output data representing the result of a calorie calculation.
 * Contains the total calories burned for a given exercise and duration.
 */
public class CalorieCalculatorOutputData {

    /** The total calories burned. */
    private final double calories;

    /**
     * Constructs a new output data object.
     *
     * @param calorie the total calories burned
     */
    public CalorieCalculatorOutputData(final double calorie) {
        this.calories = calorie;
    }

    /**
     * Returns the calculated calories.
     *
     * @return the calories burned
     */
    public double getCalories() {
        return calories;
    }
}

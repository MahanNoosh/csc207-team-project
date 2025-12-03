package tutcsc.group1.healthz.use_case.activity.calorie_calculator;

/**
 * Output boundary interface for presenting the results
 * of calorie calculation to the presenter or view.
 */
public interface CalorieCalculatorOutputBoundary {

    /**
     * Presents the calculated calories.
     *
     * @param outputData the output data containing the calories burned
     */
    void presentCalories(CalorieCalculatorOutputData outputData);
}

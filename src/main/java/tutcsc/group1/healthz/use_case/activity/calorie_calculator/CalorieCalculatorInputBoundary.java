package tutcsc.group1.healthz.use_case.activity.calorie_calculator;

import tutcsc.group1.healthz.entities.dashboard.Profile;

/**
 * Input boundary interface for the Calorie Calculator use case.
 * <p>
 * Defines the operations available for calculating calorie expenditure
 * based on user input data and profile information.
 * </p>
 */
public interface CalorieCalculatorInputBoundary {

    /**
     * Calculates the calories burned for a given activity and user profile.
     *
     * @param inputData the input data
     *                 containing activity details such as type and duration
     * @param profile the user's profile
     *               containing attributes like weight, height, age, etc.
     * @throws Exception if the calculation cannot be
 *                  performed due to invalid input or other errors
     */
    void calculateCalories(CalorieCalculatorInputData inputData,
                           Profile profile) throws Exception;
}

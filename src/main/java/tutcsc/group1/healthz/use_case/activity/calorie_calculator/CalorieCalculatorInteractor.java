package tutcsc.group1.healthz.use_case.activity.calorie_calculator;

import tutcsc.group1.healthz.entities.dashboard.Exercise;
import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseFinderInputBoundary;

/**
 * Interactor for calculating calories burned during an activity.
 * Implements the CalorieCalculatorInputBoundary to process input data
 * and uses an ExerciseFinder to retrieve exercise MET values.
 */
public class CalorieCalculatorInteractor
        implements CalorieCalculatorInputBoundary {

    /** Constant used in the calorie calculation formula. */
    private static final double CALORIE_CONST = 3.5;

    /** Interface to find exercises by name. */
    private final ExerciseFinderInputBoundary exerciseFinder;

    /** Presenter to output the calculated calories. */
    private final CalorieCalculatorOutputBoundary presenter;

    /**
     * Constructs a CalorieCalculatorInteractor.
     *
     * @param finderInputBoundary interface to retrieve exercise information
     * @param calculatorOutputBoundary interface to
     *                                output calculated calories
     */
    public CalorieCalculatorInteractor(
            final ExerciseFinderInputBoundary finderInputBoundary,
            final CalorieCalculatorOutputBoundary calculatorOutputBoundary) {
        this.exerciseFinder = finderInputBoundary;
        this.presenter = calculatorOutputBoundary;
    }

    /**
     * Calculates the calories burned based on the exercise MET, user's weight,
     * and duration of the exercise. Results are passed to the presenter.
     *
     * @param inputData the input data containing exercise name and duration
     * @param profile   the user's profile containing weight information
     * @throws Exception if exercise lookup fails
     */
    @Override
    public void calculateCalories(final CalorieCalculatorInputData inputData,
                                  final Profile profile) throws Exception {
        final Exercise exercise = exerciseFinder
                .findExerciseByName(inputData.getExerciseName());

        if (inputData.getDurationMinutes() > 0) {
            final double calories =
                    (exercise.getMet() * CALORIE_CONST
                            * profile.getWeightKg() / 200.0)
                            * inputData.getDurationMinutes();

            presenter.presentCalories(
                    new CalorieCalculatorOutputData(calories));
        }
    }
}

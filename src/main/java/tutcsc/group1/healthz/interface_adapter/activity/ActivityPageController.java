package tutcsc.group1.healthz.interface_adapter.activity;

import java.util.logging.Logger;
import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogInputBoundary;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogInputData;
import tutcsc.group1.healthz.use_case.activity.calorie_calculator.CalorieCalculatorInputBoundary;
import tutcsc.group1.healthz.use_case.activity.calorie_calculator.CalorieCalculatorInputData;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseFinderInputBoundary;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseInputData;

/**
 * Controller for the Activity Page.
 * Handles user interactions such as searching exercises,
 * calculating calories, logging activities, and loading activity history.
 *
 * <p>This class coordinates between the view and the use case interactors.</p>
 */
public class ActivityPageController {

    /**
     * Logger used to record controller events and errors.
     */
    private static final Logger LOGGER =
            Logger.getLogger(ActivityPageController.class.getName());

    /**
     * Interactor responsible for searching exercises
     * and resolving exercise names.
     */
    private final ExerciseFinderInputBoundary exerciseFinderInteractor;

    /**
     * Interactor responsible for calculating calories burned for activities.
     */
    private final CalorieCalculatorInputBoundary calorieCalculatorInteractor;

    /**
     * Interactor responsible for logging activity entries
     * and retrieving user activity history.
     */
    private final ActivityLogInputBoundary activityLogInteractor;

    /**
     * Constructs a new ActivityPageController.
     *
     * @param exerciseInteractor the interactor for exercise search
     * @param calorieInteractor the interactor for calorie calculation
     * @param logInteractor the interactor for activity logging
     */
    public ActivityPageController(
            final ExerciseFinderInputBoundary exerciseInteractor,
            final CalorieCalculatorInputBoundary calorieInteractor,
            final ActivityLogInputBoundary logInteractor) {
        this.exerciseFinderInteractor = exerciseInteractor;
        this.calorieCalculatorInteractor = calorieInteractor;
        this.activityLogInteractor = logInteractor;
    }

    /**
     * Handles search queries entered by the user.
     *
     * @param query the search string for exercises
     */
    public void onSearchQuery(final String query) {
        exerciseFinderInteractor.searchExercisesByQuery(
                new ExerciseInputData(query));
    }

    /**
     * Loads all available exercise names from the system.
     */
    public void loadAllExercises() {
        exerciseFinderInteractor.findAllExercisesNames();
    }

    /**
     * Recalculates calories burned when the user changes activity or duration.
     *
     * @param exerciseName the name of the exercise
     * @param durationText the duration entered by the user
     * @param profile      the user's profile information
     * @throws NumberFormatException if the duration input is invalid
     * @throws Exception              if calculation or interactor call fails
     */
    public void onDurationOrActivityChange(final String exerciseName,
                                           final String durationText,
                                           final Profile profile)
            throws Exception {
        final int duration = Integer.parseInt(durationText);
        final CalorieCalculatorInputData inputData =
                new CalorieCalculatorInputData(exerciseName, duration);
        calorieCalculatorInteractor.calculateCalories(inputData, profile);
    }

    /**
     * Logs a new activity entry to the system.
     *
     * @param exerciseName the name of the exercise performed
     * @param durationText the duration in minutes (string form)
     * @param profile      the user's profile
     * @throws NumberFormatException if the duration cannot be parsed
     * @throws Exception              if saving fails
     */
    public void logActivity(final String exerciseName,
                            final String durationText,
                            final Profile profile) throws Exception {
        final int minutes = Integer.parseInt(durationText);
        final ActivityLogInputData inputData =
                new ActivityLogInputData(exerciseName, minutes);
        activityLogInteractor.logActivity(inputData, profile);
    }

    /**
     * Loads the user's activity history from the data source.
     *
     * @throws Exception if loading logs fails
     */
    public void loadActivityHistory() throws Exception {
        activityLogInteractor.loadLogsForUser();
        LOGGER.info("Activity history loaded successfully.");
    }
}

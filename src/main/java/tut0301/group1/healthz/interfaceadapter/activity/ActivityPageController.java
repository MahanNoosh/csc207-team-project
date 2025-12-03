package tut0301.group1.healthz.interfaceadapter.activity;

import java.util.logging.Level;
import java.util.logging.Logger;
import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogInputBoundary;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogInputData;
import tut0301.group1.healthz.usecase.activity.caloriecalculator.CalorieCalculatorInputBoundary;
import tut0301.group1.healthz.usecase.activity.caloriecalculator.CalorieCalculatorInputData;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseFinderInputBoundary;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseInputData;

/**
 * Controller for the Activity Page.
 * Handles user interactions such as searching exercises,
 * calculating calories, logging activities, and loading activity history.
 *
 * <p>This class coordinates between the view and the use case interactors.</p>
 */
public class ActivityPageController {

    private static final Logger LOGGER =
            Logger.getLogger(ActivityPageController.class.getName());

    private final ExerciseFinderInputBoundary exerciseFinderInteractor;
    private final CalorieCalculatorInputBoundary calorieCalculatorInteractor;
    private final ActivityLogInputBoundary activityLogInteractor;

    /**
     * Constructs a new ActivityPageController.
     *
     * @param exerciseFinderInteractor     the interactor for exercise search
     * @param calorieCalculatorInteractor  the interactor for calorie calculation
     * @param activityLogInteractor        the interactor for activity logging
     */
    public ActivityPageController(
            ExerciseFinderInputBoundary exerciseFinderInteractor,
            CalorieCalculatorInputBoundary calorieCalculatorInteractor,
            ActivityLogInputBoundary activityLogInteractor) {
        this.exerciseFinderInteractor = exerciseFinderInteractor;
        this.calorieCalculatorInteractor = calorieCalculatorInteractor;
        this.activityLogInteractor = activityLogInteractor;
    }

    /**
     * Handles search queries entered by the user.
     *
     * @param query the search string for exercises
     */
    public void onSearchQuery(String query) {
        exerciseFinderInteractor.findExercisesByQuery(new ExerciseInputData(query));
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
    public void onDurationOrActivityChange(String exerciseName,
                                           String durationText,
                                           Profile profile) throws Exception {
        int duration = Integer.parseInt(durationText);
        CalorieCalculatorInputData inputData =
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
    public void logActivity(String exerciseName,
                            String durationText,
                            Profile profile) throws Exception {
        int minutes = Integer.parseInt(durationText);
        ActivityLogInputData inputData = new ActivityLogInputData(exerciseName, minutes);
        activityLogInteractor.logActivity(inputData, profile);
    }

    /**
     * Loads the user's activity history from the data source.
     */
    public void loadActivityHistory() {
        try {
            activityLogInteractor.loadLogsForUser();
            LOGGER.info("Activity history loaded successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load activity history.", e);
        }
    }
}

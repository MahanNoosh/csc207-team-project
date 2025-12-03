package tutcsc.group1.healthz.use_case.activity.activity_log;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;
import tutcsc.group1.healthz.entities.dashboard.Exercise;
import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseFinderInputBoundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ActivityLogSaveInteractor implements ActivityLogSaveInputBoundary{

    /**
     *  Conversion factor used in calorie calculation
     *  (MET × 3.5 × weight / 200 × minutes). */
    private static final double METRIC_CONVERSION_FACTOR = 3.5;

    /** Divisor used in calorie calculation formula. */
    private static final double CALORIE_DIVISOR = 200.0;

    /** Data access interface for saving and retrieving activity logs. */
    private final ActivityLogDataAccessInterface activityDataAccess;

    /** Interactor to find exercises by name. */
    private final ExerciseFinderInputBoundary exerciseFinderInputBoundary;

    /** Presenter for handling successful or failed save operations. */
    private final ActivityLogSaveOutputBoundary savePresenter;

    /**
     * Constructs an ActivityLogInteractor.
     *
     * @param logDataAccessInterface the data access interface for activity logs
     * @param finderInputBoundary  the interactor for exercise lookup
     * @param saveOutputBoundary  presenter for logging activities
     */
    public ActivityLogSaveInteractor(
            final ActivityLogDataAccessInterface logDataAccessInterface,
            final ExerciseFinderInputBoundary finderInputBoundary,
            final ActivityLogSaveOutputBoundary saveOutputBoundary) {
        this.activityDataAccess = logDataAccessInterface;
        this.exerciseFinderInputBoundary = finderInputBoundary;
        this.savePresenter = saveOutputBoundary;
    }

    /**
     * Logs an activity for a given profile.
     *
     * @param activityLogInputData the exercise name and duration
     * @param profile              the user's profile
     * @throws Exception if saving the activity fails
     */
    @Override
    public void execute(final ActivityLogInputData activityLogInputData,
                            final Profile profile) throws Exception {
        if (activityLogInputData == null || profile == null) {
            savePresenter.prepareFailView("Input data or profile is null.");
            return;
        }

        final double weightKg = profile.getWeightKg();
        final Exercise exercise =
                exerciseFinderInputBoundary
                        .findExerciseByName(
                                activityLogInputData.getExerciseName());
        final int duration = activityLogInputData.getDurationMinutes();

        if (duration <= 0) {
            savePresenter.prepareFailView("Duration must be greater than 0.");
            return;
        }

        final double calories = calculateCalories(
                exercise.getMet(), weightKg, duration);

        final ActivityEntry entry = new ActivityEntry(
                exercise.getId(),
                duration,
                calories,
                LocalDateTime.now()
        );

        activityDataAccess.saveActivityLog(entry, profile);

        final ActivityLogSaveOutputData output = new ActivityLogSaveOutputData(
                exercise.getName(),
                duration,
                (int) calories,
                entry.getTimestamp()
                        .format(DateTimeFormatter.ofPattern("d MMM yyyy"))
        );

        savePresenter.prepareSuccessView(output);
    }

    /**
     * Calculates calories burned based on MET, weight, and duration.
     *
     * @param met      the metabolic equivalent of the exercise
     * @param weightKg the user's weight in kilograms
     * @param minutes  the duration of exercise in minutes
     * @return the estimated calories burned
     */
    private double calculateCalories(
            final double met, final double weightKg, final int minutes) {
        return (met * METRIC_CONVERSION_FACTOR
                * weightKg / CALORIE_DIVISOR) * minutes;
    }
}

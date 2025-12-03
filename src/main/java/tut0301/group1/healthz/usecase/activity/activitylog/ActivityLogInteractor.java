package tut0301.group1.healthz.usecase.activity.activitylog;

import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;
import tut0301.group1.healthz.entities.Dashboard.Exercise;
import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseFinderInputBoundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Interactor for handling activity logging and loading operations.
 */
public class ActivityLogInteractor implements ActivityLogInputBoundary {

    private final ActivityLogDataAccessInterface activityDataAccess;
    private final ExerciseFinderInputBoundary exerciseFinderInputBoundary;
    private final ActivityLogSaveOutputBoundary savePresenter;
    private final ActivityLogLoadOutputBoundary loadPresenter;

    public ActivityLogInteractor(
            ActivityLogDataAccessInterface activityDataAccess,
            ExerciseFinderInputBoundary exerciseFinderInputBoundary,
            ActivityLogSaveOutputBoundary savePresenter,
            ActivityLogLoadOutputBoundary loadPresenter
    ) {
        this.activityDataAccess = activityDataAccess;
        this.exerciseFinderInputBoundary = exerciseFinderInputBoundary;
        this.savePresenter = savePresenter;
        this.loadPresenter = loadPresenter;
    }

    @Override
    public void logActivity(ActivityLogInputData activityLogInputData, Profile profile) {
        try {
            double weightKg = profile.getWeightKg();

            Exercise exercise = exerciseFinderInputBoundary
                    .findExerciseByName(activityLogInputData.getExerciseName());

            int duration = activityLogInputData.getDurationMinutes();
            if (duration <= 0) {
                savePresenter.prepareFailView("Duration must be greater than 0.");
                return;
            }

            double calories = calculateCalories(exercise.getMet(), weightKg, duration);

            ActivityEntry entry = new ActivityEntry(
                    exercise.getId(),
                    duration,
                    calories,
                    LocalDateTime.now()
            );

            activityDataAccess.saveActivityLog(entry, profile);

            ActivityLogSaveOutputData output = new ActivityLogSaveOutputData(
                    exercise.getName(),
                    duration,
                    (int) calories,
                    entry.getTimestamp().format(DateTimeFormatter.ofPattern("d MMM yyyy"))
            );

            savePresenter.prepareSuccessView(output);

        } catch (Exception e) {
            savePresenter.prepareFailView("Failed to save activity log.");
        }
    }

    @Override
    public void loadLogsForUser() {
        try {
            List<ActivityEntry> logs = activityDataAccess.getActivitiesForUser();
            loadPresenter.presentActivityLogs(new ActivityLogLoadOutputData(logs));
        } catch (Exception e) {
            loadPresenter.prepareFailView("Failed to load activity logs: " + e.getMessage());
        }
    }

    private double calculateCalories(double met, double weightKg, int minutes) {
        return (met * 3.5 * weightKg / 200.0) * minutes;
    }
}

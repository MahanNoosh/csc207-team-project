package tut0301.group1.healthz.usecase.activity.activitylog;

import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;
import tut0301.group1.healthz.entities.Dashboard.Exercise;
import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseFinderInputBoundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ActivityLogInteractor implements ActivityLogInputBoundary {
    private final ActivityLogDataAccessInterface activityDataAccess;
    private final ExerciseFinderInputBoundary exerciseFinderInputBoundary;
    private final ActivityLogSaveOutputBoundary savePresenter;
    private final ActivityLogLoadOutputBoundary loadPresenter;

    public ActivityLogInteractor(ActivityLogDataAccessInterface activityDataAccess,
                                 ExerciseFinderInputBoundary exerciseFinderInputBoundary,
                                 ActivityLogSaveOutputBoundary savePresenter,
                                 ActivityLogLoadOutputBoundary loadPresenter) {
        this.activityDataAccess = activityDataAccess;
        this.exerciseFinderInputBoundary = exerciseFinderInputBoundary;
        this.savePresenter = savePresenter;
        this.loadPresenter = loadPresenter;
    }
    public void logActivity(ActivityLogInputData activityLogInputData,
                            Profile profile)throws Exception {
        try {
        double weightKg = profile.getWeightKg();
        Exercise exercise = exerciseFinderInputBoundary.findExerciseByName(activityLogInputData.getExerciseName());
        double calories = calculateCalories(
                exercise.getMet(),
                weightKg,
                activityLogInputData.getDurationMinutes()
        );
        ActivityEntry entry = new ActivityEntry(
                exercise.getId(),
                activityLogInputData.getDurationMinutes(),
                calories,
                LocalDateTime.now()
        );
        ActivityLogSaveOutputData output = new ActivityLogSaveOutputData(
                activityLogInputData.getExerciseName(),
                entry.getDurationMinutes(),
                (int) entry.getCaloriesBurned(),
                entry.getTimestamp().format(DateTimeFormatter.ofPattern("d MMM yyyy"))
        );
        savePresenter.prepareSuccessView(output);
        activityDataAccess.saveActivityLog(entry, profile);


    } catch (Exception e) {
        savePresenter.prepareFailView("Failed to save activity log");
    }
    }

    @Override
    public void loadLogsForUser() throws Exception {
        try {
            List<ActivityEntry> logs = activityDataAccess.getActivitiesForUser();
            loadPresenter.presentActivityLogs(new ActivityLogLoadOutputData(logs));
        } catch (Exception e) {
            loadPresenter.prepareFailView("Failed to load activity logs" + e.getMessage());
        }
    }

    private double calculateCalories(double met, double weightKg, int minutes) {
        return (met * 3.5 * weightKg / 200.0) * minutes;
    }
}

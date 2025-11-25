package tut0301.group1.healthz.usecase.activity;

import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;
import tut0301.group1.healthz.entities.Dashboard.Exercise;
import tut0301.group1.healthz.entities.Dashboard.Profile;

import java.time.LocalDateTime;

public class ActivityLogInteractor implements ActivityLogInputBoundary {
    private final ActivityLogDataAccessInterface activityDataAccess;
    private final ExerciseFinderInputBoundary exerciseFinderInputBoundary;

    public ActivityLogInteractor(ActivityLogDataAccessInterface activityDataAccess,
                                 ExerciseFinderInputBoundary exerciseFinderInputBoundary) {
        this.activityDataAccess = activityDataAccess;
        this.exerciseFinderInputBoundary = exerciseFinderInputBoundary;
    }
    public void logActivity(ActivityLogInputData activityLogInputData,
                            Profile profile)throws Exception {
        double weightKg = profile.getWeightKg();
        Exercise exercise = exerciseFinderInputBoundary.findExerciseByName(activityLogInputData.getExerciseName());
        double calories = calculateCalories(
                exercise.getMet(),
                weightKg,
                activityLogInputData.getDurationMinutes()
        );
        ActivityEntry entry = new ActivityEntry(
                exercise,
                activityLogInputData.getDurationMinutes(),
                calories,
                LocalDateTime.now()
        );
        activityDataAccess.saveActivity(entry);
    }

    private double calculateCalories(double met, double weightKg, int minutes) {
        return (met * 3.5 * weightKg / 200.0) * minutes;
    }
}

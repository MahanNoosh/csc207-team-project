package healthz.tut0301.group1.interfaceadapter.activity;

import healthz.tut0301.group1.entities.Dashboard.ActivityEntry;
import healthz.tut0301.group1.usecase.activity.activitylog.ActivityLogLoadOutputBoundary;
import healthz.tut0301.group1.usecase.activity.activitylog.ActivityLogLoadOutputData;
import healthz.tut0301.group1.usecase.activity.exercisefinder.ExerciseFinderInputBoundary;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ActivityLogLoadPresenter implements ActivityLogLoadOutputBoundary {
    private final ActivityHistoryViewModel viewModel;
    private final ExerciseFinderInputBoundary exerciseFinderInteractor;

    public ActivityLogLoadPresenter(ActivityHistoryViewModel viewModel, ExerciseFinderInputBoundary exerciseFinderInteractor) {
        this.viewModel = viewModel;
        this.exerciseFinderInteractor = exerciseFinderInteractor;
    }

    @Override
    public void prepareFailView(String failedToLoadActivityLogs) {

    }

    @Override
    public void presentActivityLogs(ActivityLogLoadOutputData activityLogLoadOutputData) {
        List<ActivityEntry> logs = activityLogLoadOutputData.getLogs();

        List<ActivityItem> items = logs.stream()
                .map(entry -> {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM YYYY");
                        return new ActivityItem(
                                exerciseFinderInteractor.findExerciseById(entry.getActivityId()).getName(),
                                String.valueOf(entry.getDurationMinutes()),
                                entry.getTimestamp().toLocalDate().format(formatter),
                                (int) entry.getCaloriesBurned()
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        viewModel.setHistory(items);
    }
}

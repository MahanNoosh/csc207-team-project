package tut0301.group1.healthz.interfaceadapter.dashboard;

import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;
import tut0301.group1.healthz.interfaceadapter.activity.ActivityItem;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseFinderInputBoundary;
import tut0301.group1.healthz.usecase.activity.recent.RecentActivityOutputBoundary;
import tut0301.group1.healthz.usecase.activity.recent.RecentActivityOutputData;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecentActivityPresenter implements RecentActivityOutputBoundary {
    private final RecentActivityViewModel viewModel;
    private final ExerciseFinderInputBoundary exerciseFinderInteractor;

    public RecentActivityPresenter(RecentActivityViewModel viewModel, ExerciseFinderInputBoundary exerciseFinderInteractor) {
        this.viewModel = viewModel;
        this.exerciseFinderInteractor = exerciseFinderInteractor;
    }
    @Override
    public void presentRecentActivities(RecentActivityOutputData outputData) {
        List<ActivityEntry> logs = outputData.getRecentActivities();

        // Convert to ViewModel items
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

        // Update the UI model
        viewModel.setRecent(items);

    }

    @Override
    public void prepareFailView(String error) {
        System.err.println("❌ " + error);
    }
}

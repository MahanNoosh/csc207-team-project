package tutcsc.group1.healthz.interface_adapter.dashboard;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;
import tutcsc.group1.healthz.interface_adapter.activity.ActivityItem;
import tutcsc.group1.healthz.use_case.activity.exercisefinder.ExerciseFinderInputBoundary;
import tutcsc.group1.healthz.use_case.activity.recent.RecentActivityOutputBoundary;
import tutcsc.group1.healthz.use_case.activity.recent.RecentActivityOutputData;

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

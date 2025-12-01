package tut0301.group1.healthz.usecase.activity.recent;

import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogDataAccessInterface;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogLoadOutputData;

import java.util.List;

public class RecentActivityInteractor implements RecentActivityInputBoundary {
    private final ActivityLogDataAccessInterface activityLogDAO;
    private final RecentActivityOutputBoundary presenter;

    public RecentActivityInteractor(ActivityLogDataAccessInterface activityLogDAO,
                                    RecentActivityOutputBoundary presenter) {
        this.activityLogDAO = activityLogDAO;
        this.presenter = presenter;
    }

    @Override
    public void loadRecentActivities() {
        try {
            List<ActivityEntry> logs = activityLogDAO.getActivitiesForUser();
            presenter.presentRecentActivities(new RecentActivityOutputData(logs));
        } catch (Exception e) {
            presenter.prepareFailView("Failed to load activity logs" + e.getMessage());
        }
    }
}

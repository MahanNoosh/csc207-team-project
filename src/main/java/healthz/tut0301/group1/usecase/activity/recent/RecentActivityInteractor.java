package healthz.tut0301.group1.usecase.activity.recent;

import healthz.tut0301.group1.entities.Dashboard.ActivityEntry;
import healthz.tut0301.group1.usecase.activity.activitylog.ActivityLogDataAccessInterface;

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

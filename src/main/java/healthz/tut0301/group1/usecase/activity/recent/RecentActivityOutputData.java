package healthz.tut0301.group1.usecase.activity.recent;

import healthz.tut0301.group1.entities.Dashboard.ActivityEntry;

import java.util.List;

public class RecentActivityOutputData {
    private final List<ActivityEntry> recentActivities;

    public RecentActivityOutputData(List<ActivityEntry> recentActivities) {
        this.recentActivities = recentActivities;
    }

    public List<ActivityEntry> getRecentActivities() {
        return recentActivities;
    }
}

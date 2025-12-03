package tutcsc.group1.healthz.use_case.activity.recent;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;

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

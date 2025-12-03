package tut0301.group1.healthz.usecase.activity.recent;

import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;

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

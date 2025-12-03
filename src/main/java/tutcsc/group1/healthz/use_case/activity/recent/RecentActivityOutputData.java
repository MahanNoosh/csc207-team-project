package tutcsc.group1.healthz.use_case.activity.recent;

import java.util.List;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;

/**
 * Output data for the Recent Activity use case.
 * Contains a list of recent activity entries
 * to be passed from the interactor to the presenter.
 */
public class RecentActivityOutputData {

    /** List of recent activity entries for the user. */
    private final List<ActivityEntry> recentActivities;

    /**
     * Constructs a RecentActivityOutputData object.
     *
     * @param activityEntries the list of recent activity entries
     */
    public RecentActivityOutputData(
            final List<ActivityEntry> activityEntries) {
        this.recentActivities = activityEntries;
    }

    /**
     * Returns the list of recent activity entries.
     *
     * @return a list of {@link ActivityEntry} objects
     */
    public List<ActivityEntry> getRecentActivities() {
        return recentActivities;
    }
}

package tutcsc.group1.healthz.use_case.activity.activitylog;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;

import java.util.List;

/**
 * Output data class representing a list of activity log entries
 * to be passed from the interactor to the presenter.
 */
public class ActivityLogLoadOutputData {

    private final List<ActivityEntry> logs;

    public ActivityLogLoadOutputData(List<ActivityEntry> logs) {
        this.logs = logs;
    }

    public List<ActivityEntry> getLogs() {
        return logs;
    }
}

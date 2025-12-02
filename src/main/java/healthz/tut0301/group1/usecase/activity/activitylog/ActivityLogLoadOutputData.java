package healthz.tut0301.group1.usecase.activity.activitylog;

import healthz.tut0301.group1.entities.Dashboard.ActivityEntry;

import java.util.List;

public class ActivityLogLoadOutputData {
    private final List<ActivityEntry> logs;
    public ActivityLogLoadOutputData(List<ActivityEntry> logs) {
        this.logs = logs;
    }
    public List<ActivityEntry> getLogs() {
        return logs;
    }
}

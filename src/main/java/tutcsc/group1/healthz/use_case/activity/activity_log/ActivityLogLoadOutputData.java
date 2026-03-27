package tutcsc.group1.healthz.use_case.activity.activity_log;

import java.util.List;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;

/**
 * Represents the output data for the activity log loading use case.
 * <p>
 * This class is used to transfer a list of {@link ActivityEntry} objects
 * from the interactor to the presenter in the activity logging feature.
 * </p>
 */
public class ActivityLogLoadOutputData {

    /**
     * The list of activity entries for the current user.
     */
    private final List<ActivityEntry> logs;

    /**
     * Constructs a new {@code ActivityLogLoadOutputData} instance.
     *
     * @param activityEntries the list of {@link ActivityEntry}
     *                       objects to include in the output
     */
    public ActivityLogLoadOutputData(
            final List<ActivityEntry> activityEntries) {
        this.logs = activityEntries;
    }

    /**
     * Returns the list of activity entries contained in this output data.
     *
     * @return a {@link List} of {@link ActivityEntry} objects
     */
    public List<ActivityEntry> getLogs() {
        return logs;
    }
}

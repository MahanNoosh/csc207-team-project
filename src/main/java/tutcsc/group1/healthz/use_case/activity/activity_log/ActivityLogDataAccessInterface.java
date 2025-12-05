package tutcsc.group1.healthz.use_case.activity.activity_log;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;
import tutcsc.group1.healthz.entities.dashboard.Profile;

/**
 * Interface for accessing and persisting activity logs.
 */
public interface ActivityLogDataAccessInterface {

    /**
     * Saves a new activity entry for a given user profile.
     *
     * @param entry   The activity entry to save.
     * @param profile The profile associated with the activity.
     * @throws Exception if saving fails.
     */
    void saveActivityLog(ActivityEntry entry, Profile profile) throws Exception;

    /**
     * Retrieves all activity entries for the current user.
     *
     * @return A list of ActivityEntry objects.
     * @throws Exception if retrieval fails.
     */
    List<ActivityEntry> getActivitiesForUser() throws Exception;

    /**
     * Returns a summary of activity duration
     * (in minutes or METs) by day of the week.
     *
     * @return A map of DayOfWeek to activity total.
     * @throws Exception if calculation fails.
     */
    Map<DayOfWeek, Double> getWeeklyActivitySummary() throws Exception;
}

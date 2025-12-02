package healthz.tut0301.group1.usecase.activity.activitylog;
import healthz.tut0301.group1.entities.Dashboard.ActivityEntry;
import healthz.tut0301.group1.entities.Dashboard.Profile;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;


public interface ActivityLogDataAccessInterface {
    ActivityEntry saveActivityLog(ActivityEntry entry, Profile profile) throws Exception;
    List<ActivityEntry> getActivitiesForUser() throws Exception;
    Map<DayOfWeek, Double> getWeeklyActivitySummary() throws Exception;

}

package tut0301.group1.healthz.usecase.activity.activitylog;
import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;
import tut0301.group1.healthz.entities.Dashboard.Profile;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;


public interface ActivityLogDataAccessInterface {
    ActivityEntry saveActivityLog(ActivityEntry entry, Profile profile) throws Exception;
    List<ActivityEntry> getActivitiesForUser() throws Exception;
    Map<DayOfWeek, Double> getWeeklyActivitySummary() throws Exception;

}

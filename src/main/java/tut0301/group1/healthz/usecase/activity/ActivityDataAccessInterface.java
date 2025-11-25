package tut0301.group1.healthz.usecase.activity;
import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;

import java.util.List;


public interface ActivityDataAccessInterface {
    void saveActivity(ActivityEntry entry);
    List<ActivityEntry> getActivitiesForUser(String userId);
}

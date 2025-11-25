package tut0301.group1.healthz.usecase.activity;

import tut0301.group1.healthz.entities.Dashboard.Profile;

import java.util.List;

public interface ActivityLogInputBoundary {
    void logActivity(ActivityLogInputData activityLogInputData, Profile profile)throws Exception;
}

package healthz.tut0301.group1.usecase.dashboard;

import healthz.tut0301.group1.entities.Dashboard.Profile;

public interface ProfileInputBoundary {
    void updateProfile(Profile profile);
    Profile getProfile(String userId);
}
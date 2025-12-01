package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.entities.Dashboard.Profile;

public interface ProfileInputBoundary {
    void updateProfile(Profile profile);
    Profile getProfile(String userId);
}
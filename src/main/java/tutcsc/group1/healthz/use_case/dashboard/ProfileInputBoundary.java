package tutcsc.group1.healthz.use_case.dashboard;

import tutcsc.group1.healthz.entities.dashboard.Profile;

public interface ProfileInputBoundary {
    void updateProfile(Profile profile);
    Profile getProfile(String userId);
}
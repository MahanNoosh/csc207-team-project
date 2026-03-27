package tutcsc.group1.healthz.use_case.dashboard;

import tutcsc.group1.healthz.entities.dashboard.Profile;

public interface ProfileOutputBoundary {
    void presentProfile(Profile profile);
    void presentNoProfile();           // if somehow not found and couldn’t be created
    void presentError(String message); // for unexpected errors
}
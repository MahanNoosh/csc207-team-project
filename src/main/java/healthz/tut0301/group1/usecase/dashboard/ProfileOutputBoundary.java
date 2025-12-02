package healthz.tut0301.group1.usecase.dashboard;

import healthz.tut0301.group1.entities.Dashboard.Profile;

public interface ProfileOutputBoundary {
    void presentProfile(Profile profile);
    void presentNoProfile();           // if somehow not found and couldn’t be created
    void presentError(String message); // for unexpected errors
}
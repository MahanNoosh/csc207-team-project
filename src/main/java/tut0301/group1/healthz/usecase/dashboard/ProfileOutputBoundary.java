package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.usecase.dashboard.Profile;

public interface ProfileOutputBoundary {
    void presentProfile(Profile profile);
    void presentNoProfile();           // if somehow not found and couldn’t be created
    void presentError(String message); // for unexpected errors
}
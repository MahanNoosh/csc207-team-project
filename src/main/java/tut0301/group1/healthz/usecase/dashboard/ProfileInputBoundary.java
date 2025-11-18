package tut0301.group1.healthz.usecase.dashboard;

public interface ProfileInputBoundary {
    void createProfile(Profile profile);
    void updateProfile(Profile profile);
    Profile getProfile(String userId);
}
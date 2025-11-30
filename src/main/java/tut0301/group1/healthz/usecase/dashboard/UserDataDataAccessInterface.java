package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.entities.Dashboard.Profile;

import java.util.Optional;

public interface UserDataDataAccessInterface {
    Optional<Profile> loadCurrentUserProfile() throws Exception;
    Profile createBlankForCurrentUserIfMissing() throws Exception;
    Profile updateCurrentUserProfile(Profile profile) throws Exception;
}
package healthz.tut0301.group1.usecase.dashboard;

import healthz.tut0301.group1.entities.Dashboard.Profile;

import java.util.Optional;

public interface UserDataDataAccessInterface {
    Optional<Profile> loadCurrentUserProfile() throws Exception;
    Profile createBlankForCurrentUserIfMissing() throws Exception;
    Profile updateCurrentUserProfile(Profile profile) throws Exception;
}
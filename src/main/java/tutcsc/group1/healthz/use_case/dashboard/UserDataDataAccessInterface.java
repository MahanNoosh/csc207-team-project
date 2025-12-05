package tutcsc.group1.healthz.use_case.dashboard;

import tutcsc.group1.healthz.entities.dashboard.Profile;

import java.util.Optional;

public interface UserDataDataAccessInterface {
    Optional<Profile> loadCurrentUserProfile() throws Exception;
    Profile createBlankForCurrentUserIfMissing() throws Exception;
    Profile updateCurrentUserProfile(Profile profile) throws Exception;
}
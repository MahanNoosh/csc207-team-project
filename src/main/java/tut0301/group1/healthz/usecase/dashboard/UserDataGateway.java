package tut0301.group1.healthz.usecase.dashboard;

import java.util.Optional;

public interface UserDataGateway {
    Optional<Profile> loadCurrentUserProfile() throws Exception;
    Profile createBlankForCurrentUserIfMissing() throws Exception;
}
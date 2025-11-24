package tut0301.group1.healthz.usecase.profile;

import tut0301.group1.healthz.entities.Profile;

import java.util.Optional;

/**
 * Gateway interface for profile data access.
 *
 * This interface is defined in the Use Case layer and implemented
 * in the Data Access layer, following the Dependency Inversion Principle.
 */
public interface ProfileDataGateway {
    /**
     * Loads the current user's profile.
     *
     * @return Optional containing the profile if found
     * @throws Exception if data access fails
     */
    Optional<Profile> loadCurrentUserProfile() throws Exception;

    /**
     * Creates a blank profile for the current user if missing.
     *
     * @return the newly created profile
     * @throws Exception if creation fails
     */
    Profile createBlankForCurrentUserIfMissing() throws Exception;

    /**
     * Saves or updates a profile.
     *
     * @param profile the profile to save
     * @throws Exception if save operation fails
     */
    void saveProfile(Profile profile) throws Exception;
}

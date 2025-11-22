package tut0301.group1.healthz.usecase.profile;

/**
 * Input Boundary for profile management operations.
 *
 * The Controller calls this interface.
 * The Interactor implements this interface.
 */
public interface ProfileManagementInputBoundary {
    /**
     * Creates a new profile.
     *
     * @param inputData the profile data to create
     */
    void createProfile(UpdateProfileInputData inputData);

    /**
     * Updates an existing profile.
     *
     * @param inputData the updated profile data
     */
    void updateProfile(UpdateProfileInputData inputData);

    /**
     * Retrieves a profile by user ID.
     *
     * @param inputData contains the user ID
     */
    void getProfile(GetProfileInputData inputData);
}

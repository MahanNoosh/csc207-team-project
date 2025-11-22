package tut0301.group1.healthz.usecase.profile;

/**
 * Output Boundary for profile management operations.
 *
 * The Interactor calls this interface.
 * The Presenter implements this interface.
 */
public interface ProfileManagementOutputBoundary {
    /**
     * Presents the result of a profile retrieval.
     *
     * @param outputData contains the profile or error information
     */
    void presentProfile(GetProfileOutputData outputData);

    /**
     * Presents the result of a profile creation or update.
     *
     * @param outputData contains the result or error information
     */
    void presentProfileUpdate(ProfileOutputData outputData);
}

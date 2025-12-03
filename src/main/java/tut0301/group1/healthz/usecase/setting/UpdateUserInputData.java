package tut0301.group1.healthz.usecase.setting;

import tut0301.group1.healthz.entities.Dashboard.Profile;

/**
 * Data transfer object containing the profile information to be used
 * when updating a user's account.
 */
public class UpdateUserInputData {

    private final Profile newProfile;

    /**
     * Creates a new UpdateUserInputData instance.
     *
     * @param newProfile the updated profile data
     */
    public UpdateUserInputData(final Profile newProfile) {
        this.newProfile = newProfile;
    }

    /**
     * Returns the new profile data for updating the user.
     *
     * @return the updated profile
     */
    public Profile getNewProfile() {
        return newProfile;
    }
}

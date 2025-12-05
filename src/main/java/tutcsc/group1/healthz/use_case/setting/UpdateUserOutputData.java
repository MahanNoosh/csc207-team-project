package tutcsc.group1.healthz.use_case.setting;

import tutcsc.group1.healthz.entities.dashboard.Profile;

/**
 * Output data returned from a successful update-user operation.
 * Contains the updated profile for the current user.
 */
public class UpdateUserOutputData {

    private final Profile profile;

    /**
     * Creates a new UpdateUserOutputData instance.
     *
     * @param profile the updated user profile
     */
    public UpdateUserOutputData(final Profile profile) {
        this.profile = profile;
    }

    /**
     * Returns the updated profile.
     *
     * @return the updated {@link Profile}
     */
    public Profile getProfile() {
        return profile;
    }
}

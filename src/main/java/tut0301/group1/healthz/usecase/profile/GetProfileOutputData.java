package tut0301.group1.healthz.usecase.profile;

import tut0301.group1.healthz.entities.Profile;

/**
 * Output DTO for profile retrieval.
 */
public class GetProfileOutputData {
    private final Profile profile;
    private final boolean success;
    private final String errorMessage;
    private final boolean isNewProfile;

    /**
     * Constructor for successful result with existing profile.
     */
    public GetProfileOutputData(Profile profile, boolean isNewProfile) {
        this.profile = profile;
        this.success = true;
        this.errorMessage = null;
        this.isNewProfile = isNewProfile;
    }

    /**
     * Constructor for error result.
     */
    public GetProfileOutputData(String errorMessage) {
        this.profile = null;
        this.success = false;
        this.errorMessage = errorMessage;
        this.isNewProfile = false;
    }

    public Profile getProfile() {
        return profile;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isNewProfile() {
        return isNewProfile;
    }
}

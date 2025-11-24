package tut0301.group1.healthz.usecase.profile;

import tut0301.group1.healthz.entities.Profile;

/**
 * Output DTO for profile operations (create, update, delete).
 */
public class ProfileOutputData {
    private final Profile profile;
    private final boolean success;
    private final String message;

    /**
     * Constructor for successful result.
     */
    public ProfileOutputData(Profile profile, String message) {
        this.profile = profile;
        this.success = true;
        this.message = message;
    }

    /**
     * Constructor for error result.
     */
    public ProfileOutputData(String errorMessage) {
        this.profile = null;
        this.success = false;
        this.message = errorMessage;
    }

    public Profile getProfile() {
        return profile;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

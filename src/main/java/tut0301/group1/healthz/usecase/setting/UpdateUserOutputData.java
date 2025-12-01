package tut0301.group1.healthz.usecase.setting;

import tut0301.group1.healthz.entities.Dashboard.Profile;

public class UpdateUserOutputData {
    private final Profile profile;

    public UpdateUserOutputData(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }
}

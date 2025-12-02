package healthz.tut0301.group1.usecase.setting;

import healthz.tut0301.group1.entities.Dashboard.Profile;

public class UpdateUserOutputData {
    private final Profile profile;

    public UpdateUserOutputData(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }
}

package tutcsc.group1.healthz.use_case.setting;

import tutcsc.group1.healthz.entities.dashboard.Profile;

public class UpdateUserOutputData {
    private final Profile profile;

    public UpdateUserOutputData(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }
}

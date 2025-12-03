package tutcsc.group1.healthz.use_case.setting;

import tutcsc.group1.healthz.entities.dashboard.Profile;

public class UpdateUserInputData {

    private final Profile newProfile;

    public UpdateUserInputData(Profile newProfile) {
        this.newProfile = newProfile;
    }

    public Profile getNewProfile() {
        return newProfile;
    }
}

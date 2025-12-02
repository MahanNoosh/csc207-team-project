package healthz.tut0301.group1.usecase.setting;

import healthz.tut0301.group1.entities.Dashboard.Profile;

public class UpdateUserInputData {

    private final Profile newProfile;

    public UpdateUserInputData(Profile newProfile) {
        this.newProfile = newProfile;
    }

    public Profile getNewProfile() {
        return newProfile;
    }
}

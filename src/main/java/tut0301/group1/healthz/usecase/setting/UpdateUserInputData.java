package tut0301.group1.healthz.usecase.setting;

import tut0301.group1.healthz.entities.Profile;

public class UpdateUserInputData {

    private final Profile newProfile;

    public UpdateUserInputData(Profile newProfile) {
        this.newProfile = newProfile;
    }

    public Profile getNewProfile() {
        return newProfile;
    }
}

package tut0301.group1.healthz.usecase.setting;

import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.usecase.dashboard.UserDataDataAccessInterface;

public class UpdateUserInteractor implements UpdateUserInputBoundary {

    private final UserDataDataAccessInterface userDataDAO;
    private final UpdateUserOutputBoundary presenter;

    public UpdateUserInteractor(UserDataDataAccessInterface userDataDAO,
                                UpdateUserOutputBoundary presenter) {
        this.userDataDAO = userDataDAO;
        this.presenter = presenter;
    }

    @Override
    public void updateUser(UpdateUserInputData inputData) throws Exception {
        Profile profile = inputData.getNewProfile();

        // Update or create user data in Supabase
        Profile updated = userDataDAO.updateCurrentUserProfile(profile);

        // Prepare output
        UpdateUserOutputData outputData = new UpdateUserOutputData(updated);

        // Present response
        presenter.prepareSuccessView(outputData);
    }
}

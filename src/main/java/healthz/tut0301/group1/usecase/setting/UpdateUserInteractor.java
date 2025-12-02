package healthz.tut0301.group1.usecase.setting;

import healthz.tut0301.group1.entities.Dashboard.Profile;
import healthz.tut0301.group1.usecase.dashboard.UserDataDataAccessInterface;

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

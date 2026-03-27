package tutcsc.group1.healthz.use_case.setting;

import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.use_case.dashboard.UserDataDataAccessInterface;

/**
 * Interactor responsible for executing the update-user use case.
 * Retrieves the updated profile, forwards it to the data access layer,
 * and delegates presentation of the result to the output boundary.
 */
public class UpdateUserInteractor implements UpdateUserInputBoundary {

    private final UserDataDataAccessInterface userDataDao;
    private final UpdateUserOutputBoundary presenter;

    /**
     * Creates a new UpdateUserInteractor.
     *
     * @param userDataDao the data access object used to update user profiles
     * @param presenter   the presenter responsible for preparing output views
     */
    public UpdateUserInteractor(final UserDataDataAccessInterface userDataDao,
                                final UpdateUserOutputBoundary presenter) {
        this.userDataDao = userDataDao;
        this.presenter = presenter;
    }

    /**
     * Executes the update-user flow.
     *
     * @param inputData the data containing the updated profile
     * @throws Exception if updating the user profile fails
     */
    @Override
    public void updateUser(final UpdateUserInputData inputData) throws Exception {
        final Profile profile = inputData.getNewProfile();
        final Profile updated = userDataDao.updateCurrentUserProfile(profile);
        final UpdateUserOutputData outputData = new UpdateUserOutputData(updated);
        presenter.prepareSuccessView(outputData);
    }
}

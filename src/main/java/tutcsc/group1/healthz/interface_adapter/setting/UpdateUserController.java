package tutcsc.group1.healthz.interface_adapter.setting;

import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.use_case.setting.UpdateUserInputBoundary;
import tutcsc.group1.healthz.use_case.setting.UpdateUserInputData;

/**
 * Controller responsible for handling update-user requests from the interface layer.
 */
public class UpdateUserController {

    private final UpdateUserInputBoundary updateUserUseCase;
    private final UpdateUserPresenter presenter;

    /**
     * Creates a new UpdateUserController.
     *
     * @param updateUserUseCase the use case responsible for updating user data
     * @param presenter          the presenter used to prepare UI output
     */
    public UpdateUserController(final UpdateUserInputBoundary updateUserUseCase,
                                final UpdateUserPresenter presenter) {
        this.updateUserUseCase = updateUserUseCase;
        this.presenter = presenter;
    }

    /**
     * Executes the update-user flow with the provided profile.
     *
     * @param newProfile the updated profile values to apply
     * @throws Exception if the use case encounters an error
     */
    public void updateUser(final Profile newProfile) throws Exception {
        final UpdateUserInputData inputData = new UpdateUserInputData(newProfile);
        updateUserUseCase.updateUser(inputData);
    }

    /**
     * Returns the current view model from the presenter.
     *
     * @return the update-user view model
     */
    public UpdateUserViewModel getViewModel() {
        return presenter.getViewModel();
    }
}

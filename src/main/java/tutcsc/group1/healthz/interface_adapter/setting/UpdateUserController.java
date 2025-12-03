package tutcsc.group1.healthz.interface_adapter.setting;

import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.use_case.setting.UpdateUserInputBoundary;
import tutcsc.group1.healthz.use_case.setting.UpdateUserInputData;

public class UpdateUserController {

    private final UpdateUserInputBoundary updateUserUseCase;
    private final UpdateUserPresenter presenter;

    public UpdateUserController(UpdateUserInputBoundary updateUserUseCase,
                                UpdateUserPresenter presenter) {
        this.updateUserUseCase = updateUserUseCase;
        this.presenter = presenter;
    }

    public void updateUser(Profile newProfile) {
        UpdateUserInputData inputData = new UpdateUserInputData(newProfile);
        try {
            updateUserUseCase.updateUser(inputData);
        } catch (Exception e) {
            presenter.prepareFailView(e.getMessage());
        }
    }

    public UpdateUserViewModel getViewModel() {
        return presenter.getViewModel();
    }
}

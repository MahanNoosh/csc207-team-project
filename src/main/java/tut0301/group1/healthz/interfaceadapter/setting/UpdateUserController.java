package tut0301.group1.healthz.interfaceadapter.setting;

import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.usecase.setting.UpdateUserInputBoundary;
import tut0301.group1.healthz.usecase.setting.UpdateUserInputData;

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

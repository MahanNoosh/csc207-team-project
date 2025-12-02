package healthz.tut0301.group1.interfaceadapter.setting;

import healthz.tut0301.group1.entities.Dashboard.Profile;
import healthz.tut0301.group1.usecase.setting.UpdateUserInputBoundary;
import healthz.tut0301.group1.usecase.setting.UpdateUserInputData;

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

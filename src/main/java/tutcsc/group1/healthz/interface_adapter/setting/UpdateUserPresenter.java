package tutcsc.group1.healthz.interface_adapter.setting;

import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.use_case.setting.UpdateUserOutputBoundary;
import tutcsc.group1.healthz.use_case.setting.UpdateUserOutputData;

/**
 * Presenter for the Update User use case.
 * Updates the UpdateUserViewModel based on the use case output.
 */
public class UpdateUserPresenter implements UpdateUserOutputBoundary {

    private final UpdateUserViewModel viewModel;

    public UpdateUserPresenter(UpdateUserViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(UpdateUserOutputData outputData) {
        Profile profile = outputData.getProfile();
        UpdateUserViewModel.State newState =
                new UpdateUserViewModel.State(profile, null, true);
        viewModel.setState(newState);
    }

    @Override
    public void prepareFailView(String error) {
        UpdateUserViewModel.State newState =
                new UpdateUserViewModel.State(null, error, false);
        viewModel.setState(newState);
    }

    public UpdateUserViewModel getViewModel() {
        return viewModel;
    }
}

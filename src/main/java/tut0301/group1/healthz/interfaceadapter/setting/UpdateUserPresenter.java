package tut0301.group1.healthz.interfaceadapter.setting;

import tut0301.group1.healthz.entities.Profile;
import tut0301.group1.healthz.usecase.setting.UpdateUserOutputBoundary;
import tut0301.group1.healthz.usecase.setting.UpdateUserOutputData;

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

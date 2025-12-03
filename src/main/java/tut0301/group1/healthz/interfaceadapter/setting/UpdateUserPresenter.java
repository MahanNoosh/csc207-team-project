package tut0301.group1.healthz.interfaceadapter.setting;

import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.usecase.setting.UpdateUserOutputBoundary;
import tut0301.group1.healthz.usecase.setting.UpdateUserOutputData;

/**
 * Presenter for the Update User use case.
 * Updates the {@link UpdateUserViewModel} based on the use case output.
 */
public class UpdateUserPresenter implements UpdateUserOutputBoundary {

    private final UpdateUserViewModel viewModel;

    /**
     * Creates a new UpdateUserPresenter.
     *
     * @param viewModel the view model that will be updated with the use case results
     */
    public UpdateUserPresenter(final UpdateUserViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Prepares the success view based on the output data.
     *
     * @param outputData the output data containing the updated profile
     */
    @Override
    public void prepareSuccessView(final UpdateUserOutputData outputData) {
        final Profile profile = outputData.getProfile();
        final UpdateUserViewModel.State newState =
                new UpdateUserViewModel.State(profile, null, true);
        viewModel.setState(newState);
    }

    /**
     * Prepares the failure view with the given error message.
     *
     * @param error the error message describing the failure
     */
    @Override
    public void prepareFailView(final String error) {
        final UpdateUserViewModel.State newState =
                new UpdateUserViewModel.State(null, error, false);
        viewModel.setState(newState);
    }

    /**
     * Returns the view model maintained by this presenter.
     *
     * @return the update-user view model
     */
    public UpdateUserViewModel getViewModel() {
        return viewModel;
    }
}

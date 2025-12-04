package tutcsc.group1.healthz.interface_adapter.auth.log_in;

import tutcsc.group1.healthz.use_case.auth.log_in.LoginOutputBoundary;
import tutcsc.group1.healthz.use_case.auth.log_in.LoginOutputData;

/**
 * Presenter for the login use case.
 * Maps {@link LoginOutputData} into the {@link LoginViewModel}.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel viewModel;

    /**
     * Creates a new LoginPresenter.
     *
     * @param viewModel the view model that will be updated with login results
     */
    public LoginPresenter(final LoginViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Prepares the view for a successful login.
     *
     * @param output the login output data containing tokens and user information
     */
    @Override
    public void prepareSuccessView(final LoginOutputData output) {
        viewModel.setAccessToken(output.getAccessToken());
        viewModel.setRefreshToken(output.getRefreshToken());
        viewModel.setUserId(output.getUserId());
        viewModel.setDisplayName(output.getDisplayName());
        viewModel.setMessage("Login successful!");
    }

    /**
     * Prepares the view for a failed login attempt.
     *
     * @param errorMessage the error message describing why the login failed
     */
    @Override
    public void prepareFailView(final String errorMessage) {
        viewModel.setMessage(errorMessage);
    }

    /**
     * Returns the view model maintained by this presenter.
     *
     * @return the login view model
     */
    public LoginViewModel getViewModel() {
        return viewModel;
    }
}

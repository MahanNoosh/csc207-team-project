package tutcsc.group1.healthz.interface_adapter.auth.log_in;

import tutcsc.group1.healthz.use_case.auth.log_in.LoginOutputBoundary;
import tutcsc.group1.healthz.use_case.auth.log_in.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel viewModel;

    public LoginPresenter(LoginViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData output) {
        // Map LoginOutputData to LoginViewModel
        viewModel.setAccessToken(output.getAccessToken());
        viewModel.setRefreshToken(output.getRefreshToken());
        viewModel.setUserId(output.getUserId());
        viewModel.setDisplayName(output.getDisplayName());

        // Set the success message in the ViewModel
        viewModel.setMessage("Login successful!");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Set the error message in the ViewModel
        viewModel.setMessage(errorMessage);  // Set the failure message
    }

    // Expose the viewModel to be accessed by the controller
    public LoginViewModel getViewModel() {
        return viewModel;
    }
}

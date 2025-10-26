package tut0301.group1.healthz.presenter.auth;

import tut0301.group1.healthz.usecase.auth.login.LoginOutputBoundary;
import tut0301.group1.healthz.view.auth.LoginView;
import tut0301.group1.healthz.usecase.auth.login.LoginOutputData;

/**
 * Presenter: converts ResponseModel to ViewModel.
 */
public class AuthLoginPresenter implements LoginOutputBoundary {
    private LoginView vm;

    // Implement the prepareSuccessView method
    @Override
    public void prepareSuccessView(LoginOutputData output) {
        vm = new LoginView(output.getAccessToken(), output.getRefreshToken(), output.getUserId(), output.getDisplayName());
    }

    // Implement the prepareFailView method
    @Override
    public void prepareFailView(String errorMessage) {
        vm = new LoginView(null, null, null, errorMessage);
    }

    // Getter for the ViewModel
    public LoginView viewModel() {
        return vm;
    }
}

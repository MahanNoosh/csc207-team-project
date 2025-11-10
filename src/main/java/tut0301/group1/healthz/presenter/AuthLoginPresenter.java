package tut0301.group1.healthz.presenter;

import tut0301.group1.healthz.usecase.auth.LoginOutputBoundary;
import tut0301.group1.healthz.usecase.auth.model.LoginResponseModel;
import tut0301.group1.healthz.viewmodel.AuthLoginViewModel;

/**
 * Presenter: converts ResponseModel to ViewModel.
 */
public class AuthLoginPresenter implements LoginOutputBoundary {
    private AuthLoginViewModel vm;

    @Override
    public void present(LoginResponseModel resp) {
        vm = new AuthLoginViewModel(resp.accessToken(), resp.refreshToken(), resp.userId(), resp.displayName());
    }

    public AuthLoginViewModel viewModel() {
        return vm;
    }
}

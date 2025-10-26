package tut0301.group1.healthz.interfaceadapter.auth.login;

import tut0301.group1.healthz.usecase.auth.login.*;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel vm;

    public LoginPresenter(LoginViewModel vm) { this.vm = vm; }

    @Override public void prepareSuccessView(LoginOutputData output) {
        vm.setLoggedInUserId(output.getUserId());
        vm.setError(null);
        vm.firePropertyChanged();
    }

    @Override public void prepareFailView(String errorMessage) {
        vm.setError(errorMessage);
        vm.firePropertyChanged();
    }
}


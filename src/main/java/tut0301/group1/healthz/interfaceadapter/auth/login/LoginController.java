package tut0301.group1.healthz.interfaceadapter.auth.login;

import tut0301.group1.healthz.usecase.auth.login.*;

public class LoginController {
    private final LoginInputBoundary interactor;

    public LoginController(LoginInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String email, String password) {
        interactor.execute(new LoginInputData(email, password));
    }
}

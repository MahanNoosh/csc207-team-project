package tut0301.group1.healthz.controllers;

import tut0301.group1.healthz.usecase.auth.*;
import tut0301.group1.healthz.usecase.auth.model.*;
import tut0301.group1.healthz.presenter.AuthLoginPresenter;
import tut0301.group1.healthz.usecase.auth.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.model.LoginRequestModel;

/**
 * Controller: entry point from UI layer (here, called from Main).
 */
public class AuthController {
    private final LoginInputBoundary loginUC;
    private final AuthLoginPresenter presenter;

    public AuthController(LoginInputBoundary loginUC, AuthLoginPresenter presenter) {
        this.loginUC = loginUC;
        this.presenter = presenter;
    }

    public Object login(String email, String password) {
        loginUC.execute(new LoginRequestModel(email, password));
        return presenter.viewModel();
    }
}

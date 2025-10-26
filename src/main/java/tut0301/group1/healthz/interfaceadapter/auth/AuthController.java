package tut0301.group1.healthz.interfaceadapter.auth;

import tut0301.group1.healthz.presenter.auth.AuthLoginPresenter;
import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInputData;
import tut0301.group1.healthz.view.auth.LoginView;

public class AuthController {
    private final LoginInputBoundary loginUC;
    private final AuthLoginPresenter presenter;

    public AuthController(LoginInputBoundary loginUC, AuthLoginPresenter presenter) {
        this.loginUC = loginUC;
        this.presenter = presenter;
    }

    public LoginView login(String email, String password) {
        // Create LoginInputData object with the email and password
        LoginInputData inputData = new LoginInputData(email, password);

        // Call execute method from LoginInputBoundary
        loginUC.execute(inputData);

        // Return the view model from the presenter
        return presenter.viewModel();
    }
}

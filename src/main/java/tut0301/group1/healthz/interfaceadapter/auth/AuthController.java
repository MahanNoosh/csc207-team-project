package tut0301.group1.healthz.interfaceadapter.auth;

import tut0301.group1.healthz.interfaceadapter.auth.login.LoginPresenter;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginViewModel;
import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInputData;

public class AuthController {
    private final LoginInputBoundary loginUC;
    private final LoginPresenter presenter;

    public AuthController(LoginInputBoundary loginUC, LoginPresenter presenter) {
        this.loginUC = loginUC;
        this.presenter = presenter;
    }

    public void login(String email, String password) {
        // Create LoginInputData object with the email and password
        LoginInputData inputData = new LoginInputData(email, password);

        // Call execute method from LoginInputBoundary (Interactor)
        loginUC.execute(inputData);
    }

    // Return the updated view model from the presenter
    public LoginViewModel getViewModel() {
        return presenter.getViewModel();  // Correctly retrieves the LoginViewModel
    }
}

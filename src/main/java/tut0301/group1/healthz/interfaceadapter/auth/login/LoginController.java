package tut0301.group1.healthz.interfaceadapter.auth.login;

import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInputData;

public class LoginController {

    private final LoginInputBoundary loginInputBoundary;
    private final LoginPresenter presenter;

    public LoginController(LoginInputBoundary loginInputBoundary, LoginPresenter presenter) {
        this.loginInputBoundary = loginInputBoundary;
        this.presenter = presenter;
    }

    public void login(String email, String password) {
        // Create LoginInputData object with the email and password
        LoginInputData inputData = new LoginInputData(email, password);

        // Call execute method from LoginInputBoundary (Interactor)
        loginInputBoundary.execute(inputData);
    }

    // Return the updated view model from the presenter
    public LoginViewModel getViewModel() {
        return presenter.getViewModel();  // Retrieve the view model from the presenter
    }
}

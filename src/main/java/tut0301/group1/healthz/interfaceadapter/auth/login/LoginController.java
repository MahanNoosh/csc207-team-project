package tut0301.group1.healthz.interfaceadapter.auth.login;

import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInputData;

/**
 * Controller for handling user login requests.
 * Translates raw input into use case input data and invokes the login interactor.
 */
public class LoginController {

    private final LoginInputBoundary loginInputBoundary;
    private final LoginPresenter presenter;

    /**
     * Creates a new LoginController.
     *
     * @param loginInputBoundary the input boundary for executing login logic
     * @param presenter the presenter responsible for preparing view-model output
     */
    public LoginController(final LoginInputBoundary loginInputBoundary,
                           final LoginPresenter presenter) {
        this.loginInputBoundary = loginInputBoundary;
        this.presenter = presenter;
    }

    /**
     * Initiates a login request using the given credentials.
     *
     * @param email the user's email
     * @param password the user's password
     * @throws Exception if the login use case encounters an error
     */
    public void login(final String email, final String password) throws Exception {
        final LoginInputData inputData = new LoginInputData(email, password);
        loginInputBoundary.execute(inputData);
    }

    /**
     * Returns the current login view model.
     *
     * @return the login view model
     */
    public LoginViewModel getViewModel() {
        return presenter.getViewModel();
    }
}

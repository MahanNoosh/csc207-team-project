package tutcsc.group1.healthz.interface_adapter.auth;

import tutcsc.group1.healthz.interface_adapter.auth.log_in.LoginPresenter;
import tutcsc.group1.healthz.interface_adapter.auth.log_in.LoginViewModel;
import tutcsc.group1.healthz.use_case.auth.log_in.LoginInputBoundary;
import tutcsc.group1.healthz.use_case.auth.log_in.LoginInputData;

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

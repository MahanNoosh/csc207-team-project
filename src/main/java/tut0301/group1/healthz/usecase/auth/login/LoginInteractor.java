package tut0301.group1.healthz.usecase.auth.login;

import tut0301.group1.healthz.usecase.auth.AuthGateway;

public class LoginInteractor implements LoginInputBoundary {
    private final AuthGateway auth;
    private final LoginOutputBoundary presenter;

    public LoginInteractor(AuthGateway auth, LoginOutputBoundary presenter) {
        this.auth = auth;
        this.presenter = presenter;
    }

    @Override
    public void execute(LoginInputData input) {
        try {
            // Get email and password from input data
            String email = input.getEmail();
            String password = input.getPassword();

            // Call signInEmail method from AuthGateway
            auth.signInEmail(email, password);  // Assuming synchronous for this example

            // Ensure we have the required data after login attempt
            String accessToken = auth.getAccessToken();
            String refreshToken = auth.getRefreshToken();
            String userId = auth.getCurrentUserId();
            String displayName = auth.getCurrentUserEmail(); // Assuming this is the default display name

            // Check if the necessary data is available
            if (accessToken == null || userId == null) {
                // Login failed due to missing data
                presenter.prepareFailView("Login failed. Please retry.");
                return;
            }

            // Construct LoginOutputData with all the necessary information
            LoginOutputData outputData = new LoginOutputData(
                    accessToken,
                    refreshToken,
                    userId,
                    displayName
            );

            // Send the result to the presenter
            presenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            // If there's an error, send the error message to the presenter
            presenter.prepareFailView(e.getMessage());
        }
    }
}

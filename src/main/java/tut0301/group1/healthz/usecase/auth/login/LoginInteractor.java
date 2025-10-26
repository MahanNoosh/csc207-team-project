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
            auth.signInEmail(email, password);

            // Get user info from AuthGateway
            String userId = auth.getCurrentUserId();
            String display = auth.getCurrentUserEmail(); // default “display name”

            LoginOutputData outputData = new LoginOutputData(
                    auth.getAccessToken(),
                    auth.getRefreshToken(),
                    userId,
                    display
            );

            // Use prepareSuccessView to present the success response
            presenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            // Surface the error via a failure view — using prepareFailView
            presenter.prepareFailView("ERROR: " + e.getMessage());
        }
    }
}

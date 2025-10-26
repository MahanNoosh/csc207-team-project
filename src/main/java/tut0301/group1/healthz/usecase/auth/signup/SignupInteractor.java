package tut0301.group1.healthz.usecase.auth.signup;

import tut0301.group1.healthz.usecase.auth.AuthGateway;

public class SignupInteractor implements SignupInputBoundary {
    private final AuthGateway auth;
    private final SignupOutputBoundary presenter;

    public SignupInteractor(AuthGateway auth, SignupOutputBoundary presenter) {
        this.auth = auth;
        this.presenter = presenter;
    }

    @Override
    public void execute(SignupInputData input) {
        // 1. Validate passwords match
        if (!input.passwordsMatch()) {
            presenter.prepareFailView("Passwords do not match.");
            return;
        }

        // 2. Call the gateway to perform the signup
        try {
            auth.signUpEmail(input.getEmail(), input.getPassword1());
            // 3. On success, notify the presenter
            presenter.prepareSuccessView();
        } catch (Exception e) {
            // 4. On failure, show the error message
            presenter.prepareFailView("Sign-up failed: " + e.getMessage());
        }
    }
}

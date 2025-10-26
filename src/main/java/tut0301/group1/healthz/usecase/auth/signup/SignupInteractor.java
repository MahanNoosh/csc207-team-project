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
        try {
            auth.signUpEmail(input.email(), input.password());
            presenter.prepareSuccessView(); // user may need to confirm email
        } catch (Exception e) {
            presenter.prepareFailView(e.getMessage());
        }
    }
}

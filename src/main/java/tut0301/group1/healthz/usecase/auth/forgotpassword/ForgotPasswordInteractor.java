package tut0301.group1.healthz.usecase.auth.forgotpassword;

import tut0301.group1.healthz.usecase.auth.AuthGateway;

public class ForgotPasswordInteractor implements ForgotPasswordInputBoundary {
    private final AuthGateway auth;
    private final ForgotPasswordOutputBoundary presenter;

    public ForgotPasswordInteractor(AuthGateway auth, ForgotPasswordOutputBoundary presenter) {
        this.auth = auth;
        this.presenter = presenter;
    }

    @Override
    public void execute(ForgotPasswordInputData input) {
        try {
            auth.requestPasswordReset(input.email(), input.redirectUrl());
            presenter.prepareSuccessView();
        } catch (Exception e) {
            presenter.prepareFailView(e.getMessage());
        }
    }
}


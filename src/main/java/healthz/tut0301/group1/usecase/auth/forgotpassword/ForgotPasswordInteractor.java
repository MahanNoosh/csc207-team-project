package healthz.tut0301.group1.usecase.auth.forgotpassword;

import healthz.tut0301.group1.usecase.auth.AuthGateway;

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


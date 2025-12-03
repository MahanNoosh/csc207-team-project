package tutcsc.group1.healthz.use_case.auth.forgot_password;

import tutcsc.group1.healthz.use_case.auth.AuthGateway;

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


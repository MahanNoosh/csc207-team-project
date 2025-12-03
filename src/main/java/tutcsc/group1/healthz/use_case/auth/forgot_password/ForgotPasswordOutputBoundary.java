package tutcsc.group1.healthz.use_case.auth.forgot_password;

public interface ForgotPasswordOutputBoundary {
    void prepareSuccessView();
    void prepareFailView(String errorMessage);
}

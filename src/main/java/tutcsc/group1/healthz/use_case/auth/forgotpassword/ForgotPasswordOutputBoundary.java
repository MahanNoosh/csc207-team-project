package tutcsc.group1.healthz.use_case.auth.forgotpassword;

public interface ForgotPasswordOutputBoundary {
    void prepareSuccessView();
    void prepareFailView(String errorMessage);
}

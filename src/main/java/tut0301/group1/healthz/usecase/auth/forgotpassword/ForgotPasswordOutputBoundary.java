package tut0301.group1.healthz.usecase.auth.forgotpassword;

public interface ForgotPasswordOutputBoundary {
    void prepareSuccessView();
    void prepareFailView(String errorMessage);
}

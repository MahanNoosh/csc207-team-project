package healthz.tut0301.group1.usecase.auth.forgotpassword;

public interface ForgotPasswordOutputBoundary {
    void prepareSuccessView();
    void prepareFailView(String errorMessage);
}

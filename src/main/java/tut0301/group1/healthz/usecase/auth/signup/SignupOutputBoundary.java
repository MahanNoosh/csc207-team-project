package tut0301.group1.healthz.usecase.auth.signup;

public interface SignupOutputBoundary {
    void prepareSuccessView();
    void prepareFailView(String errorMessage);
}
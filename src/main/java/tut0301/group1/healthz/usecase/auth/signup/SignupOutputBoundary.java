package tut0301.group1.healthz.usecase.auth.signup;

public interface SignupOutputBoundary {
    void prepareSuccessView(SignupOutputData output);
    void prepareFailView(String errorMessage);
}
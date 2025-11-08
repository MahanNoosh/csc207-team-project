package tut0301.group1.healthz.usecase.auth.login;

public interface LoginOutputBoundary {
    void prepareSuccessView(LoginOutputData output);
    void prepareFailView(String errorMessage);
}

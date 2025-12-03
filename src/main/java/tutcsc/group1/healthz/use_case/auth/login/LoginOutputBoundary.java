package tutcsc.group1.healthz.use_case.auth.login;

public interface LoginOutputBoundary {
    void prepareSuccessView(LoginOutputData output);
    void prepareFailView(String errorMessage);
}

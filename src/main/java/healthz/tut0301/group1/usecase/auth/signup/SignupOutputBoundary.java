package healthz.tut0301.group1.usecase.auth.signup;

public interface SignupOutputBoundary {
    void prepareSuccessView(SignupOutputData output);
    void prepareFailView(String errorMessage);
}
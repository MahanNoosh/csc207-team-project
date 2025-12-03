package tutcsc.group1.healthz.use_case.auth.signup;

public interface SignupOutputBoundary {
    void prepareSuccessView(SignupOutputData output);
    void prepareFailView(String errorMessage);
}
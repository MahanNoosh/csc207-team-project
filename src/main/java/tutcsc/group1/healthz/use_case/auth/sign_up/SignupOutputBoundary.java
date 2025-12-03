package tutcsc.group1.healthz.use_case.auth.sign_up;

public interface SignupOutputBoundary {
    void prepareSuccessView(SignupOutputData output);
    void prepareFailView(String errorMessage);
}
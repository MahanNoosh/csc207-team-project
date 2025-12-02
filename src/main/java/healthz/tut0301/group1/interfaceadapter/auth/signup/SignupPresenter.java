package healthz.tut0301.group1.interfaceadapter.auth.signup;

import healthz.tut0301.group1.usecase.auth.signup.SignupOutputBoundary;
import healthz.tut0301.group1.usecase.auth.signup.SignupOutputData;

public class SignupPresenter implements SignupOutputBoundary {
    private final SignupViewModel viewModel;

    public SignupPresenter(SignupViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData output) {
        System.out.println("Presenter: Signup successful for " + output.getEmail());
        viewModel.setEmail(output.getEmail());
        viewModel.setErrorMessage(null);
        viewModel.setSignupSuccessful(true);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.err.println("Presenter: Signup failed - " + errorMessage);
        viewModel.setErrorMessage(errorMessage);
        viewModel.setSignupSuccessful(false);
    }

    public SignupViewModel getViewModel() {
        return viewModel;
    }
}
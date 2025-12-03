package tutcsc.group1.healthz.interface_adapter.auth.sign_up;

import tutcsc.group1.healthz.use_case.auth.sign_up.SignupOutputBoundary;
import tutcsc.group1.healthz.use_case.auth.sign_up.SignupOutputData;

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
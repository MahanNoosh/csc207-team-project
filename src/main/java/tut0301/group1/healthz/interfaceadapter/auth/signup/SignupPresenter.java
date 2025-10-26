package tut0301.group1.healthz.interfaceadapter.auth.signup;

import tut0301.group1.healthz.view.auth.SignupView;
import tut0301.group1.healthz.usecase.auth.signup.SignupOutputBoundary;

public class SignupPresenter implements SignupOutputBoundary {
    private final SignupView view;
    private final SignupViewModel viewModel;

    public SignupPresenter(SignupView view, SignupViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView() {
        viewModel.setMessage("Sign-up successful!");
        view.display();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setMessage("Error: " + errorMessage);
        view.display();
    }
}

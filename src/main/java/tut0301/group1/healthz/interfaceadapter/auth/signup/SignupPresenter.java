package tut0301.group1.healthz.interfaceadapter.auth.signup;

import tut0301.group1.healthz.view.auth.SignupView;
import tut0301.group1.healthz.usecase.auth.signup.SignupOutputBoundary;
import tut0301.group1.healthz.usecase.auth.signup.SignupOutputData;

/**
 * Presenter for the Signup use case.
 * Converts output data from the interactor into a message for the view.
 */
public class SignupPresenter implements SignupOutputBoundary {
    private final SignupView view;
    private final SignupViewModel viewModel;

    public SignupPresenter(SignupView view, SignupViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData output) {
        // Show a simple confirmation using the email
        String email = output.getEmail();
        viewModel.setMessage("Sign-up successful for: " + email);
        view.display(viewModel.getMessage());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setMessage("Error: " + errorMessage);
        view.display(viewModel.getMessage());
    }

    public SignupViewModel getViewModel() {
        return viewModel;
    }
}

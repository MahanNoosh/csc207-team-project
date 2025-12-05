package tutcsc.group1.healthz.interface_adapter.auth.sign_up;

import tutcsc.group1.healthz.use_case.auth.sign_up.SignupOutputBoundary;
import tutcsc.group1.healthz.use_case.auth.sign_up.SignupOutputData;

/**
 * Presenter responsible for formatting signup results into a view model.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel viewModel;

    /**
     * Creates a new SignupPresenter.
     *
     * @param viewModel the view model to update based on signup results
     */
    public SignupPresenter(SignupViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Prepares the view model for a successful signup.
     *
     * @param output the output data from the signup use case
     */
    @Override
    public void prepareSuccessView(SignupOutputData output) {
        System.out.println("Presenter: Signup successful for " + output.getEmail());
        viewModel.setEmail(output.getEmail());
        viewModel.setErrorMessage(null);
        viewModel.setSignupSuccessful(true);
    }

    /**
     * Prepares the view model for a failed signup.
     *
     * @param errorMessage the error message describing why signup failed
     */
    @Override
    public void prepareFailView(String errorMessage) {
        System.err.println("Presenter: Signup failed - " + errorMessage);
        viewModel.setErrorMessage(errorMessage);
        viewModel.setSignupSuccessful(false);
    }

    /**
     * Returns the current signup view model.
     *
     * @return the signup view model
     */
    public SignupViewModel getViewModel() {
        return viewModel;
    }
}

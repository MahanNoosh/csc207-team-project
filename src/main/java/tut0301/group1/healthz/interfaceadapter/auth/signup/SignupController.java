package tut0301.group1.healthz.interfaceadapter.auth.signup;

import tut0301.group1.healthz.usecase.auth.signup.SignupInputBoundary;
import tut0301.group1.healthz.usecase.auth.signup.SignupInputData;

/**
 * Controller responsible for handling signup requests from the interface layer.
 */
public class SignupController {

    private final SignupInputBoundary signupUseCase;
    private final SignupPresenter presenter;

    /**
     * Creates a new SignupController.
     *
     * @param signupUseCase the signup input boundary
     * @param presenter     the presenter used to obtain the view model
     */
    public SignupController(SignupInputBoundary signupUseCase, SignupPresenter presenter) {
        this.signupUseCase = signupUseCase;
        this.presenter = presenter;
    }

    /**
     * Executes the signup use case with the provided user data.
     *
     * @param email       the email entered by the user
     * @param password1   the first password field
     * @param password2   the repeated password field
     * @param displayName the display name chosen by the user
     */
    public void signup(String email, String password1, String password2, String displayName) {
        final SignupInputData inputData =
                new SignupInputData(email, password1, password2, displayName);

        try {
            signupUseCase.execute(inputData);
        }
        catch (Exception ex) {
            presenter.prepareFailView("Unexpected error: " + ex.getMessage());
        }
    }

    /**
     * Returns the presenter view model.
     *
     * @return the signup view model
     */
    public SignupViewModel getViewModel() {
        return presenter.getViewModel();
    }
}

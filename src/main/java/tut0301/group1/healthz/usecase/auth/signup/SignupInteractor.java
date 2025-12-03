package tut0301.group1.healthz.usecase.auth.signup;


import tut0301.group1.healthz.usecase.auth.AuthGateway;

/**
 * Handles the user sign-up use case.
 */
public class SignupInteractor implements SignupInputBoundary {

    private final AuthGateway authGateway;
    private final SignupOutputBoundary presenter;

    /**
     * Creates a new signup interactor.
     *
     * @param authGateway the authentication gateway used to perform signup
     * @param presenter   the presenter used to prepare the signup views
     */
    public SignupInteractor(final AuthGateway authGateway,
                            final SignupOutputBoundary presenter) {
        this.authGateway = authGateway;
        this.presenter = presenter;
    }

    /**
     * Executes the sign-up flow.
     *
     * @param input the sign-up data
     * @throws Exception if the authentication gateway encounters an error
     */
    @Override
    public void execute(final SignupInputData input) throws Exception {
        if (!input.passwordsMatch()) {
            presenter.prepareFailView("Passwords do not match.");
        }
        else {
            authGateway.signUpEmail(
                    input.getEmail(),
                    input.getPassword1(),
                    input.getDisplayName()
            );

            final SignupOutputData outputData = new SignupOutputData(input.getEmail());
            presenter.prepareSuccessView(outputData);
        }
    }
}

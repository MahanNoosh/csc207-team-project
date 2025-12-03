package tut0301.group1.healthz.usecase.auth.login;

import tut0301.group1.healthz.usecase.auth.AuthGateway;

/**
 * Interactor responsible for executing the login use case.
 */
public class LoginInteractor implements LoginInputBoundary {

    private final AuthGateway auth;
    private final LoginOutputBoundary presenter;

    /**
     * Creates a new LoginInteractor.
     *
     * @param auth      the authentication gateway used to perform login
     * @param presenter the presenter responsible for preparing output views
     */
    public LoginInteractor(final AuthGateway auth, final LoginOutputBoundary presenter) {
        this.auth = auth;
        this.presenter = presenter;
    }

    /**
     * Executes the login use case.
     *
     * @param input the input data containing login credentials
     * @throws Exception if the authentication gateway encounters an error
     */
    @Override
    public void execute(final LoginInputData input) throws Exception {
        // Perform sign-in using the auth gateway
        auth.signInEmail(input.getEmail(), input.getPassword());

        // Retrieve required user information
        final String accessToken = auth.getAccessToken();
        final String refreshToken = auth.getRefreshToken();
        final String userId = auth.getCurrentUserId();
        final String displayName = auth.getCurrentUserEmail();

        // Decide outcome based on data presence
        if (accessToken == null || userId == null) {
            presenter.prepareFailView("Login failed. Please retry.");
        }
        else {
            final LoginOutputData outputData = new LoginOutputData(
                    accessToken,
                    refreshToken,
                    userId,
                    displayName
            );
            presenter.prepareSuccessView(outputData);
        }
        // No return statements allowed for void methods by ReturnCount rule.
    }
}

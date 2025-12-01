package tut0301.group1.healthz.usecase.auth.signup;

import org.json.JSONException;
import org.json.JSONObject;
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
     * @param authGateway the auth gateway
     * @param presenter   the presenter
     */
    public SignupInteractor(AuthGateway authGateway, SignupOutputBoundary presenter) {
        this.authGateway = authGateway;
        this.presenter = presenter;
    }

    /**
     * Executes the sign-up flow.
     *
     * @param input the sign-up data
     */
    @Override
    public void execute(SignupInputData input) {
        if (!input.passwordsMatch()) {
            presenter.prepareFailView("Passwords do not match.");
            return;
        }

        try {
            authGateway.signUpEmail(
                    input.getEmail(),
                    input.getPassword1(),
                    input.getDisplayName()
            );

            presenter.prepareSuccessView(new SignupOutputData(input.getEmail()));

        } catch (Exception ex) {
            // This line WILL violate IllegalCatch unless authGateway throws a checked exception.
            String message = extractMessage(ex.getMessage());
            presenter.prepareFailView("Sign-up failed: " + message);
        }
    }

    private String extractMessage(String raw) {
        if (raw == null || raw.isEmpty()) {
            return "Unknown error.";
        }
        int brace = raw.indexOf('{');
        if (brace >= 0) {
            try {
                JSONObject json = new JSONObject(raw.substring(brace));
                String msg = json.optString("msg");
                if (!msg.isEmpty()) {
                    return msg;
                }
            } catch (JSONException ignored) {
                // keep original
            }
        }
        return raw;
    }
}

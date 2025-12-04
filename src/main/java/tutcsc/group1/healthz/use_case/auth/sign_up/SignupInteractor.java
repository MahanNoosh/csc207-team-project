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
     */
    // -@cs[IllegalCatch] Need to catch generic exceptions from auth gateway to show user-friendly errors.
    @Override
    public void execute(final SignupInputData input) {
        if (input.passwordsMatch()) {
            try {
                authGateway.signUpEmail(
                        input.getEmail(),
                        input.getPassword1(),
                        input.getDisplayName()
                );

                final SignupOutputData outputData = new SignupOutputData(input.getEmail());
                presenter.prepareSuccessView(outputData);
            }
            catch (Exception ex) {
                final String raw = ex.getMessage();
                String message = "Unknown error.";

                if (raw != null && !raw.trim().isEmpty()) {
                    final String extracted = extractMsg(raw);
                    if (extracted != null) {
                        message = extracted;
                    }
                    else {
                        message = raw;
                    }
                }

                presenter.prepareFailView("Sign-up failed: " + message);
            }
        }
        else {
            presenter.prepareFailView("Passwords do not match.");
        }
    }

    /**
     * Attempts to extract a {@code msg} field from a JSON object embedded in the raw message.
     *
     * @param raw the raw exception message
     * @return the extracted message, or {@code null} if none was found
     */
    private String extractMsg(final String raw) {
        String result = null;
        final int braceIndex = raw.indexOf('{');

        if (braceIndex >= 0) {
            final String jsonPart = raw.substring(braceIndex);
            try {
                final JSONObject jsonObject = new JSONObject(jsonPart);
                final String msg = jsonObject.optString("msg", "");
                if (!msg.isEmpty()) {
                    result = msg;
                }
            }
            catch (JSONException ex) {
                // Ignore and fall back to null.
            }
        }

        return result;
    }
}

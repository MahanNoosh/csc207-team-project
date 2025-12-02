package healthz.tut0301.group1.usecase.auth.signup;

import org.json.JSONObject;
import healthz.tut0301.group1.usecase.auth.AuthGateway;

public class SignupInteractor implements SignupInputBoundary {
    private final AuthGateway auth;
    private final SignupOutputBoundary presenter;

    public SignupInteractor(AuthGateway auth, SignupOutputBoundary presenter) {
        this.auth = auth;
        this.presenter = presenter;
    }

    @Override
    public void execute(SignupInputData input) {
        try {
            // 1. Validate passwords match
            if (!input.passwordsMatch()) {
                presenter.prepareFailView("Passwords do not match.");
                return;
            }

            // 2. Perform signup via gateway
            auth.signUpEmail(input.getEmail(), input.getPassword1(), input.getDisplayName());

            // 3. On success, return minimal output data
            SignupOutputData output = new SignupOutputData(input.getEmail());
            presenter.prepareSuccessView(output);

        } catch (Exception e) {
            // 4. Extract a clean message if the error body is JSON (Supabase-style)
            String message = e.getMessage();
            try {
                int brace = message.indexOf('{');
                if (brace >= 0) {
                    JSONObject err = new JSONObject(message.substring(brace));
                    message = err.optString("msg", message);
                }
            } catch (Exception ignore) {
                // not JSON, keep the original message
            }

            presenter.prepareFailView("Sign-up failed: " + message);
        }
    }
}

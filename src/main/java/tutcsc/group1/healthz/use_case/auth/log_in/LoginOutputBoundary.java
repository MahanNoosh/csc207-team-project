package tutcsc.group1.healthz.use_case.auth.log_in;

/**
 * Output boundary for the login use case.
 * Defines how login results are presented to the interface layer.
 */
public interface LoginOutputBoundary {

    /**
     * Prepares the view for a successful login.
     *
     * @param output the output data containing login result information
     */
    void prepareSuccessView(LoginOutputData output);

    /**
     * Prepares the view for a failed login attempt.
     *
     * @param errorMessage the message describing why the login failed
     */
    void prepareFailView(String errorMessage);
}

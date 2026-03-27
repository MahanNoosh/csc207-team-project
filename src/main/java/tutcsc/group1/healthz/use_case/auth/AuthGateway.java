package tutcsc.group1.healthz.use_case.auth;

/**
 * Port used by Login and Signup use cases to communicate with the authentication system.
 */
public interface AuthGateway {

    /**
     * Signs up a new user using email and password credentials.
     *
     * @param email        the user's email
     * @param password     the user's password
     * @param displayName  the display name for the user
     * @throws Exception if the sign-up process fails
     */
    void signUpEmail(String email, String password, String displayName) throws Exception;

    /**
     * Signs in an existing user using email and password credentials.
     *
     * @param email    the user's email
     * @param password the user's password
     * @throws Exception if authentication fails
     */
    void signInEmail(String email, String password) throws Exception;

    /**
     * Sends a password reset request to the provided email.
     *
     * @param email       the user's email
     * @param redirectUrl the redirect URL for the reset flow
     * @throws Exception if the request fails
     */
    void requestPasswordReset(String email, String redirectUrl) throws Exception;

    /**
     * Retrieves the unique identifier of the currently authenticated user.
     *
     * @return the user's UUID
     * @throws Exception if retrieval fails
     */
    String getCurrentUserId() throws Exception;

    /**
     * Retrieves the email of the currently authenticated user.
     *
     * @return the current user's email
     * @throws Exception if retrieval fails
     */
    String getCurrentUserEmail() throws Exception;

    /**
     * Retrieves the access token for the currently authenticated session.
     *
     * @return the access token
     */
    String getAccessToken();

    /**
     * Retrieves the refresh token for the currently authenticated session.
     *
     * @return the refresh token
     */
    String getRefreshToken();

    /**
     * Retrieves the current user's display name.
     *
     * @return the user's display name
     */
    String getCurrentUserName();

    /**
     * Resends a verification email to the given email address.
     *
     * @param email the email to resend the verification to
     * @throws Exception if the request fails
     */
    void resendSignupVerification(String email) throws Exception;
}

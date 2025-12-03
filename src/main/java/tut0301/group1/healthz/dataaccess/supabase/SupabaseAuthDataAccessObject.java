package tut0301.group1.healthz.dataaccess.supabase;

import tut0301.group1.healthz.usecase.auth.AuthGateway;

/**
 * Data access object that implements the {@link AuthGateway} interface using a {@link SupabaseClient}.
 * Acts as an adapter between the use-case layer and the Supabase authentication API.
 */
public class SupabaseAuthDataAccessObject implements AuthGateway {

    private final SupabaseClient client;

    /**
     * Creates a new SupabaseAuthDataAccessObject.
     *
     * @param client the Supabase client used to perform authentication operations
     */
    public SupabaseAuthDataAccessObject(final SupabaseClient client) {
        this.client = client;
    }

    /**
     * Signs up a user using email, password, and display name.
     *
     * @param email        the user's email address
     * @param password     the user's password
     * @param displayName  the chosen display name
     * @throws Exception        if an error occurs while contacting Supabase
     * @throws RuntimeException if Supabase returns an error response
     */
    @Override
    public void signUpEmail(final String email, final String password,
                            final String displayName) throws Exception {
        client.signUpEmail(email, password, displayName);
    }

    /**
     * Signs in a user using email and password.
     *
     * @param email    the user's email address
     * @param password the user's password
     * @throws Exception        if an error occurs while contacting Supabase
     * @throws RuntimeException if Supabase returns an error response
     */
    @Override
    public void signInEmail(final String email, final String password) throws Exception {
        client.signInEmail(email, password);
    }

    /**
     * Returns the current authenticated user's ID.
     *
     * @return the current user's identifier
     * @throws Exception        if an error occurs while contacting Supabase
     * @throws RuntimeException if Supabase returns an error response
     */
    @Override
    public String getCurrentUserId() throws Exception {
        return client.getUserId();
    }

    /**
     * Returns the current authenticated user's email.
     *
     * @return the current user's email
     * @throws Exception        if an error occurs while contacting Supabase
     * @throws RuntimeException if Supabase returns an error response
     */
    @Override
    public String getCurrentUserEmail() throws Exception {
        return client.getUserEmail();
    }

    /**
     * Returns the current access token.
     *
     * @return the access token issued by Supabase
     */
    @Override
    public String getAccessToken() {
        return client.getAccessToken();
    }

    /**
     * Returns the current refresh token.
     *
     * @return the refresh token issued by Supabase
     */
    @Override
    public String getRefreshToken() {
        return client.getRefreshToken();
    }

    /**
     * Requests a Supabase password reset email for the given user.
     *
     * @param email       the email of the user requesting a reset
     * @param redirectUrl the URL to redirect the user to after the reset
     * @throws Exception        if an error occurs while contacting Supabase
     * @throws RuntimeException if Supabase returns an error response
     */
    @Override
    public void requestPasswordReset(final String email,
                                     final String redirectUrl) throws Exception {
        client.requestPasswordReset(email, redirectUrl);
    }

    /**
     * Returns the current authenticated user's display name.
     *
     * @return the current user's display name, or null if unavailable
     */
    @Override
    public String getCurrentUserName() {
        return client.getDisplayName();
    }

    /**
     * Resends a signup verification email.
     *
     * @param email the email address for which verification should be resent
     * @throws Exception        if an error occurs while contacting Supabase
     * @throws RuntimeException if Supabase returns an error response
     */
    @Override
    public void resendSignupVerification(final String email) throws Exception {
        client.resendSignupVerification(email);
    }
}

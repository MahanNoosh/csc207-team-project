package tutcsc.group1.healthz.data_access.supabase;

import tutcsc.group1.healthz.use_case.auth.AuthGateway;

/**
 * Adapter: implements the AuthGateway using SupabaseClient.
 */
public class SupabaseAuthDataAccessObject implements AuthGateway {
    private final SupabaseClient client;

    public SupabaseAuthDataAccessObject(SupabaseClient client) {
        this.client = client;
    }

    // Implementing signUpEmail
    @Override
    public void signUpEmail(String email, String password, String displayName) throws Exception {
        client.signUpEmail(email, password, displayName); // Calls the method from SupabaseClient
    }

    @Override
    public void signInEmail(String email, String password) throws Exception {
        client.signInEmail(email, password);
    }

    @Override
    public String getCurrentUserId() throws Exception {
        return client.getUserId();
    }

    @Override
    public String getCurrentUserEmail() throws Exception {
        return client.getUserEmail();
    }

    @Override
    public String getAccessToken() {
        return client.getAccessToken();
    }

    @Override
    public String getRefreshToken() {
        return client.getRefreshToken();
    }

    // Implementing requestPasswordReset method
    @Override
    public void requestPasswordReset(String email, String redirectUrl) throws Exception {
        client.requestPasswordReset(email, redirectUrl); // Calls the method from SupabaseClient
    }

    @Override
    public String getCurrentUserName() {
        return client.getDisplayName();
    }

    @Override
    public void resendSignupVerification(String email) throws Exception{
        client.resendSignupVerification(email);
    }

}

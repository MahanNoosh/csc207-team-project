package tut0301.group1.healthz.usecase.auth;

/**
 * Port used by Login/Signup use cases to talk to auth.
 */
public interface AuthGateway {
    // Adding the method signatures for authentication
    void signUpEmail(String email, String password) throws Exception;

    void signInEmail(String email, String password) throws Exception;

    // Password reset method
    void requestPasswordReset(String email, String redirectUrl) throws Exception;

    // Methods for getting the current user's information
    String getCurrentUserId() throws Exception; // UUID

    String getCurrentUserEmail() throws Exception;

    // Methods for getting tokens for authentication
    String getAccessToken(); // JWT for RLS

    String getRefreshToken(); // Refresh token
}

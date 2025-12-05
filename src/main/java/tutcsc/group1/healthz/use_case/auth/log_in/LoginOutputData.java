package tutcsc.group1.healthz.use_case.auth.log_in;

/**
 * Output data returned from a successful login operation.
 * Contains authentication tokens and basic user information.
 */
public class LoginOutputData {

    private final String accessToken;
    private final String refreshToken;
    private final String userId;
    private final String displayName;

    /**
     * Creates a new LoginOutputData instance.
     *
     * @param accessToken the user's access token
     * @param refreshToken the user's refresh token
     * @param userId the identifier of the logged-in user
     * @param displayName the display name or email of the user
     */
    public LoginOutputData(final String accessToken,
                           final String refreshToken,
                           final String userId,
                           final String displayName) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.displayName = displayName;
    }

    /**
     * Returns the access token issued during login.
     *
     * @return the access token string
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Returns the refresh token issued during login.
     *
     * @return the refresh token string
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Returns the unique identifier of the logged-in user.
     *
     * @return the user identifier string
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns the user's display name.
     *
     * @return the display name string
     */
    public String getDisplayName() {
        return displayName;
    }
}

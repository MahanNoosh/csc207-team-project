package tut0301.group1.healthz.interfaceadapter.auth.login;

/**
 * View model representing the state of a login operation.
 * Contains authentication tokens, user information, and status messages.
 */
public class LoginViewModel {

    private String message;
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String displayName;

    /**
     * Returns the current status message.
     *
     * @return the status message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the status message.
     *
     * @param message the status message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Returns the access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the access token.
     *
     * @param accessToken the access token to set
     */
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Returns the refresh token.
     *
     * @return the refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Sets the refresh token.
     *
     * @param refreshToken the refresh token to set
     */
    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Returns the user ID.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the user ID to set
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Returns the display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the displayed user name.
     *
     * @param displayName the display name to set
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Determines whether the user is logged in.
     * A user is considered logged in when both the access token and user ID are present.
     *
     * @return {@code true} if logged in; {@code false} otherwise
     */
    public boolean isLoggedIn() {
        return accessToken != null && userId != null;
    }
}

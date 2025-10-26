package tut0301.group1.healthz.usecase.auth.login;

public class LoginOutputData {
    private final String accessToken;
    private final String refreshToken;
    private final String userId;
    private final String displayName;

    public LoginOutputData(String accessToken, String refreshToken, String userId, String displayName) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.displayName = displayName;
    }

    // Getters
    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Hide secrets when printed or logged
    @Override
    public String toString() {
        return "LoginOutputData[userId=" + userId + ", displayName=" + displayName + "]";
    }
}

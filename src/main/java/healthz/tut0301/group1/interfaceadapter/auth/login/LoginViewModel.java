package healthz.tut0301.group1.interfaceadapter.auth.login;

public class LoginViewModel {
    private String message;  // This holds the message (success or error)
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String displayName;

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;  // Set the message (success or error)
    }

    // Getters and Setters for other fields
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isLoggedIn() {
        return accessToken != null && userId != null;
    }

}

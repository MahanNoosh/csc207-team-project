package tut0301.group1.healthz.view.auth;

public class LoginView {
    private final String accessToken;
    private final String refreshToken;
    private final String userId;
    private final String displayName;

    public LoginView(String accessToken, String refreshToken, String userId, String displayName) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.displayName = displayName;
    }

    public String accessToken() { return accessToken; }
    public String refreshToken() { return refreshToken; }
    public String userId() { return userId; }
    public String displayName() { return displayName; }

    @Override
    public String toString() {
        return "AuthLoginViewModel{userId='%s', displayName='%s'}".formatted(userId, displayName);
    }
}

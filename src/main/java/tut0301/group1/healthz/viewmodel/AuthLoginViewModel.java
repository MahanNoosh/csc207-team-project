package tut0301.group1.healthz.viewmodel;

/**
 * ViewModel returned by Controller.
 */
public record AuthLoginViewModel(String accessToken, String refreshToken, String userId, String name) {
}

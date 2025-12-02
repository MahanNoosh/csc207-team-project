package healthz.tut0301.group1.usecase.auth.model;

/**
 * <DS> Response Model from Interactor to Presenter.
 */
public record LoginResponseModel(String accessToken, String refreshToken, String userId, String displayName) {
}

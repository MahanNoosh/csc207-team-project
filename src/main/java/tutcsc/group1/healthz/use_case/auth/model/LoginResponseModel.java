package tutcsc.group1.healthz.use_case.auth.model;

/**
 * <DS> Response Model from Interactor to Presenter.
 */
public record LoginResponseModel(String accessToken, String refreshToken, String userId, String displayName) {
}

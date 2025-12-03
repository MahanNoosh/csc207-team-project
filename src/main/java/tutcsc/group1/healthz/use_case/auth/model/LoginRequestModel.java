package tutcsc.group1.healthz.use_case.auth.model;

/**
 * <DS> Request Model from Controller to Interactor.
 */
public record LoginRequestModel(String email, String password) {
}

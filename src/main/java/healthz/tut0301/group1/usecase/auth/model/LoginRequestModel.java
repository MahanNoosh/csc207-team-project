package healthz.tut0301.group1.usecase.auth.model;

/**
 * <DS> Request Model from Controller to Interactor.
 */
public record LoginRequestModel(String email, String password) {
}

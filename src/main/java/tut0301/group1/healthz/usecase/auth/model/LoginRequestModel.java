package tut0301.group1.healthz.usecase.auth.model;

/**
 * <DS> Request Model from Controller to Interactor.
 */
public record LoginRequestModel(String email, String password) {
}

package tut0301.group1.healthz.usecase.auth;

import tut0301.group1.healthz.usecase.auth.model.LoginResponseModel;

/**
 * <I> Output Boundary for Login (Presenter implements).
 */
public interface LoginOutputBoundary {
    void present(LoginResponseModel response);
}

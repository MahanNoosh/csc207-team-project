package healthz.tut0301.group1.usecase.auth;

import healthz.tut0301.group1.usecase.auth.model.LoginResponseModel;

/**
 * <I> Output Boundary for Login (Presenter implements).
 */
public interface LoginOutputBoundary {
    void present(LoginResponseModel response);
}

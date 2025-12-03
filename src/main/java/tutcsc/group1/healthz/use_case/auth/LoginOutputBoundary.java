package tutcsc.group1.healthz.use_case.auth;

import tutcsc.group1.healthz.use_case.auth.model.LoginResponseModel;

/**
 * <I> Output Boundary for Login (Presenter implements).
 */
public interface LoginOutputBoundary {
    void present(LoginResponseModel response);
}

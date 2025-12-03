package tutcsc.group1.healthz.use_case.auth;

import tutcsc.group1.healthz.use_case.auth.model.LoginRequestModel;

/**
 * <I> Input Boundary for Login.
 */
public interface LoginInputBoundary {
    void execute(LoginRequestModel request);
}

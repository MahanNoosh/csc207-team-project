package healthz.tut0301.group1.usecase.auth;

import healthz.tut0301.group1.usecase.auth.model.LoginRequestModel;

/**
 * <I> Input Boundary for Login.
 */
public interface LoginInputBoundary {
    void execute(LoginRequestModel request);
}

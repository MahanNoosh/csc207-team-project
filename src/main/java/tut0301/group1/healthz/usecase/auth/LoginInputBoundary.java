package tut0301.group1.healthz.usecase.auth;

import tut0301.group1.healthz.usecase.auth.model.LoginRequestModel;

/**
 * <I> Input Boundary for Login.
 */
public interface LoginInputBoundary {
    void execute(LoginRequestModel request);
}

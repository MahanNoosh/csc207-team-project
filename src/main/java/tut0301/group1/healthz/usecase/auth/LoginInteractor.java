package tut0301.group1.healthz.usecase.auth;

import tut0301.group1.healthz.dataaccess.*;
import tut0301.group1.healthz.entities.user.*;
import tut0301.group1.healthz.usecase.auth.model.*;
import tut0301.group1.healthz.dataaccess.ClockGateway;
import tut0301.group1.healthz.dataaccess.PasswordHasher;
import tut0301.group1.healthz.dataaccess.TokenIssuer;
import tut0301.group1.healthz.dataaccess.UserDataAccessInterface;
import tut0301.group1.healthz.entities.user.User;
import tut0301.group1.healthz.usecase.auth.model.LoginRequestModel;
import tut0301.group1.healthz.usecase.auth.model.LoginResponseModel;

import java.util.Objects;

/**
 * Interactor: orchestrates the Login use case with ports only.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final UserDataAccessInterface users;
    private final PasswordHasher hasher;
    private final TokenIssuer tokens;
    private final ClockGateway clock;
    private final LoginOutputBoundary presenter;

    public LoginInteractor(UserDataAccessInterface users, PasswordHasher hasher, TokenIssuer tokens, ClockGateway clock, LoginOutputBoundary presenter) {
        this.users = Objects.requireNonNull(users);
        this.hasher = hasher;
        this.tokens = tokens;
        this.clock = clock;
        this.presenter = presenter;
    }

    @Override
    public void execute(LoginRequestModel req) {
        User u = users.findByEmail(req.email()).orElseThrow(() -> new IllegalArgumentException("Bad credentials"));
        if (!hasher.matches(req.password(), u.passwordHash().value()))
            throw new IllegalArgumentException("Bad credentials");
        var now = clock.now();
        presenter.present(new LoginResponseModel(
                tokens.issueAccessToken(u.id().value(), u.roles(), now.plusSeconds(3600)),
                tokens.issueRefreshToken(u.id().value(), now.plusSeconds(2592000)),
                u.id().value(), u.displayName()
        ));
    }
}

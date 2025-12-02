package healthz.tut0301.group1.usecase.auth;

import healthz.tut0301.group1.dataaccess.*;
import healthz.tut0301.group1.entities.user.*;
import healthz.tut0301.group1.usecase.auth.model.*;
import healthz.tut0301.group1.dataaccess.ClockGateway;
import healthz.tut0301.group1.dataaccess.PasswordHasher;
import healthz.tut0301.group1.dataaccess.TokenIssuer;
import healthz.tut0301.group1.dataaccess.UserDataAccessInterface;
import healthz.tut0301.group1.entities.user.User;
import healthz.tut0301.group1.usecase.auth.model.LoginRequestModel;
import healthz.tut0301.group1.usecase.auth.model.LoginResponseModel;

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

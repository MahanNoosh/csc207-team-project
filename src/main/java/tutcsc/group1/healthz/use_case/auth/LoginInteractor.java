package tutcsc.group1.healthz.use_case.auth;

import tutcsc.group1.healthz.data_access.*;
import tutcsc.group1.healthz.entities.user.*;
import tutcsc.group1.healthz.use_case.auth.model.*;
import tutcsc.group1.healthz.data_access.ClockGateway;
import tutcsc.group1.healthz.data_access.PasswordHasher;
import tutcsc.group1.healthz.data_access.TokenIssuer;
import tutcsc.group1.healthz.data_access.UserDataAccessInterface;
import tutcsc.group1.healthz.entities.user.User;
import tutcsc.group1.healthz.use_case.auth.model.LoginRequestModel;
import tutcsc.group1.healthz.use_case.auth.model.LoginResponseModel;

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

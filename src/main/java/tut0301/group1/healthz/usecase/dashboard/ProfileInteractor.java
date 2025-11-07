package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.usecase.auth.AuthGateway;

/** Ensures the profile row exists for the signed-in user, then loads it. */
public class ProfileInteractor {
    private final AuthGateway auth;
    private final UserDataGateway gateway;

    public ProfileInteractor(AuthGateway auth, UserDataGateway gateway) {
        this.auth = auth;
        this.gateway = gateway;
    }

    public Profile execute() throws Exception {
        // must be signed in; your AuthGateway already enforces this
        auth.getCurrentUserId();

        // If no row yet (fresh sign-up), create a blank one, else keep existing
        var existing = gateway.loadCurrentUserProfile();
        if (existing.isPresent()) return existing.get();

        gateway.createBlankForCurrentUserIfMissing();
        return gateway.loadCurrentUserProfile()
                .orElseThrow(() -> new IllegalStateException("Profile row was not created"));
    }
}

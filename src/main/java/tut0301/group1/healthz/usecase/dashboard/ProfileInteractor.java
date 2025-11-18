package tut0301.group1.healthz.usecase.dashboard;

import java.util.NoSuchElementException;

public class ProfileInteractor implements ProfileInputBoundary {
    private final UserDataGateway gateway;

    public ProfileInteractor(UserDataGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void createProfile(Profile profile) {
        throw new UnsupportedOperationException("createProfile not implemented yet");
    }

    @Override
    public void updateProfile(Profile profile) {
        throw new UnsupportedOperationException("updateProfile not implemented yet");
    }

    @Override
    public Profile getProfile(String userId) {
        try {
            // If missing, create a blank row (via Supabase defaults) and return it.
            return gateway.loadCurrentUserProfile()
                    .orElseGet(() -> {
                        try { return gateway.createBlankForCurrentUserIfMissing(); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    });
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load profile: " + e.getMessage(), e);
        }
    }
}
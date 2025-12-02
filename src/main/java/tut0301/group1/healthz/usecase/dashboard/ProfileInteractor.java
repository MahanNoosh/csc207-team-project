package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.entities.Dashboard.Profile;

public class ProfileInteractor implements ProfileInputBoundary {
    private final UserDataDataAccessInterface userDataDataAccessInterface;

    public ProfileInteractor(UserDataDataAccessInterface userDataDataAccessInterface) {
        this.userDataDataAccessInterface = userDataDataAccessInterface;
    }

    @Override
    public void updateProfile(Profile profile) {
        throw new UnsupportedOperationException("updateProfile not implemented yet");
    }

    @Override
    public Profile getProfile(String userId) {
        try {
            // If missing, create a blank row (via Supabase defaults) and return it.
            return userDataDataAccessInterface.loadCurrentUserProfile()
                    .orElseGet(() -> {
                        try { return userDataDataAccessInterface.createBlankForCurrentUserIfMissing(); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    });
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load profile: " + e.getMessage(), e);
        }
    }
}
package tut0301.group1.healthz.usecase.profile;

import tut0301.group1.healthz.entities.Dashboard.Profile;

/**
 * Interactor for profile management operations.
 *
 * This class implements the business logic for creating, updating,
 * and retrieving user profiles, following Clean Architecture principles.
 */
public class ProfileManagementInteractor implements ProfileManagementInputBoundary {
    private final ProfileDataGateway gateway;
    private final ProfileManagementOutputBoundary outputBoundary;

    /**
     * Constructor with dependency injection.
     *
     * @param gateway the gateway for accessing profile data
     * @param outputBoundary the presenter for displaying results
     */
    public ProfileManagementInteractor(ProfileDataGateway gateway,
                                       ProfileManagementOutputBoundary outputBoundary) {
        if (gateway == null) {
            throw new IllegalArgumentException("ProfileDataGateway cannot be null");
        }
        if (outputBoundary == null) {
            throw new IllegalArgumentException("ProfileManagementOutputBoundary cannot be null");
        }
        this.gateway = gateway;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void createProfile(UpdateProfileInputData inputData) {
        try {
            // Create new profile from input data
            Profile profile = new Profile(
                inputData.getUserId(),
                inputData.getWeightKg(),
                inputData.getHeightCm(),
                inputData.getAgeYears(),
                inputData.getSex(),
                inputData.getGoal(),
                inputData.getActivityLevelMET(),
                inputData.getTargetWeightKg(),
                inputData.getDailyCalorieTarget(),
                inputData.getHealthCondition()
            );

            // Save through gateway
            gateway.saveProfile(profile);

            // Create success output
            ProfileOutputData outputData = new ProfileOutputData(
                profile,
                "Profile created successfully"
            );

            // Pass to presenter
            outputBoundary.presentProfileUpdate(outputData);

        } catch (Exception e) {
            // Create error output
            ProfileOutputData outputData = new ProfileOutputData(
                "Failed to create profile: " + e.getMessage()
            );

            // Pass error to presenter
            outputBoundary.presentProfileUpdate(outputData);
        }
    }

    @Override
    public void updateProfile(UpdateProfileInputData inputData) {
        try {
            // Create updated profile from input data
            Profile profile = new Profile(
                inputData.getUserId(),
                inputData.getWeightKg(),
                inputData.getHeightCm(),
                inputData.getAgeYears(),
                inputData.getSex(),
                inputData.getGoal(),
                inputData.getActivityLevelMET(),
                inputData.getTargetWeightKg(),
                inputData.getDailyCalorieTarget(),
                inputData.getHealthCondition()
            );

            // Save through gateway
            gateway.saveProfile(profile);

            // Create success output
            ProfileOutputData outputData = new ProfileOutputData(
                profile,
                "Profile updated successfully"
            );

            // Pass to presenter
            outputBoundary.presentProfileUpdate(outputData);

        } catch (Exception e) {
            // Create error output
            ProfileOutputData outputData = new ProfileOutputData(
                "Failed to update profile: " + e.getMessage()
            );

            // Pass error to presenter
            outputBoundary.presentProfileUpdate(outputData);
        }
    }

    @Override
    public void getProfile(GetProfileInputData inputData) {
        try {
            // Try to load existing profile, or create blank if missing
            Profile profile = gateway.loadCurrentUserProfile()
                    .orElseGet(() -> {
                        try {
                            return gateway.createBlankForCurrentUserIfMissing();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

            // Determine if this is a new profile
            boolean isNewProfile = gateway.loadCurrentUserProfile().isEmpty();

            // Create success output
            GetProfileOutputData outputData = new GetProfileOutputData(profile, isNewProfile);

            // Pass to presenter
            outputBoundary.presentProfile(outputData);

        } catch (Exception e) {
            // Create error output
            GetProfileOutputData outputData = new GetProfileOutputData(
                "Failed to load profile: " + e.getMessage()
            );

            // Pass error to presenter
            outputBoundary.presentProfile(outputData);
        }
    }
}

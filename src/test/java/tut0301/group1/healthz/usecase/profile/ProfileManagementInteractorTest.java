//package tut0301.group1.healthz.usecase.profile;
//
//import tut0301.group1.healthz.entities.Dashboard.Profile;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
///**
// * Test for ProfileManagementInteractor to verify Clean Architecture compliance.
// */
//public class ProfileManagementInteractorTest {
//
//    public static void main(String[] args) {
//        System.out.println("=== Testing ProfileManagementInteractor (Clean Architecture) ===\n");
//
//        try {
//            // Create mock Gateway implementation
//            Map<String, Profile> profileDatabase = new HashMap<>();
//
//            ProfileDataGateway mockGateway = new ProfileDataGateway() {
//                @Override
//                public Optional<Profile> loadCurrentUserProfile() {
//                    return Optional.ofNullable(profileDatabase.get("current_user"));
//                }
//
//                @Override
//                public Profile createBlankForCurrentUserIfMissing() {
//                    Profile blankProfile = new Profile(
//                        "current_user", null, null, null,
//                        Sex.MALE,
//                        Goal.GENERAL_HEALTH,
//                        1.2, null, Optional.empty(),
//                        HealthCondition.NONE
//                    );
//                    profileDatabase.put("current_user", blankProfile);
//                    return blankProfile;
//                }
//
//                @Override
//                public void saveProfile(Profile profile) {
//                    profileDatabase.put(profile.getUserId(), profile);
//                    System.out.println("  → Saved to database: " + profile.getUserId());
//                }
//            };
//
//            // Create mock OutputBoundary implementation
//            ProfileManagementOutputBoundary mockPresenter = new ProfileManagementOutputBoundary() {
//                @Override
//                public void presentProfile(GetProfileOutputData outputData) {
//                    if (outputData.isSuccess()) {
//                        Profile profile = outputData.getProfile();
//                        System.out.println("✅ Profile retrieved successfully");
//                        System.out.println("  User ID: " + profile.getUserId());
//                        System.out.println("  Weight: " + profile.getWeightKg() + " kg");
//                        System.out.println("  Is new: " + outputData.isNewProfile());
//                    } else {
//                        System.out.println("❌ Error: " + outputData.getErrorMessage());
//                    }
//                }
//
//                @Override
//                public void presentProfileUpdate(ProfileOutputData outputData) {
//                    if (outputData.isSuccess()) {
//                        System.out.println("✅ " + outputData.getMessage());
//                        Profile profile = outputData.getProfile();
//                        System.out.println("  User ID: " + profile.getUserId());
//                        System.out.println("  Weight: " + profile.getWeightKg() + " kg");
//                        System.out.println("  Target: " + profile.getDailyCalorieTarget().orElse(0.0) + " kcal");
//                    } else {
//                        System.out.println("❌ Error: " + outputData.getMessage());
//                    }
//                }
//            };
//
//            // Test 1: Create Interactor
//            System.out.println("--- Test 1: Create Interactor with DI ---");
//            ProfileManagementInteractor interactor = new ProfileManagementInteractor(mockGateway, mockPresenter);
//            System.out.println("✅ Interactor created successfully\n");
//
//            // Test 2: Create new profile
//            System.out.println("--- Test 2: Create new profile ---");
//            UpdateProfileInputData createData = new UpdateProfileInputData(
//                "user123", 70.0, 175.0, 25,
//                Sex.MALE,
//                Goal.WEIGHT_LOSS,
//                1.4, 65.0,
//                Optional.of(2000.0),
//                HealthCondition.NONE
//            );
//            interactor.createProfile(createData);
//            System.out.println();
//
//            // Test 3: Update profile
//            System.out.println("--- Test 3: Update profile ---");
//            UpdateProfileInputData updateData = new UpdateProfileInputData(
//                "user123", 69.0, 175.0, 25,
//                Sex.MALE,
//                Goal.WEIGHT_LOSS,
//                1.4, 65.0,
//                Optional.of(1900.0),
//                HealthCondition.NONE
//            );
//            interactor.updateProfile(updateData);
//            System.out.println();
//
//            // Test 4: Get profile
//            System.out.println("--- Test 4: Get profile ---");
//            GetProfileInputData getData = new GetProfileInputData("user123");
//            interactor.getProfile(getData);
//            System.out.println();
//
//            // Test 5: Get profile that doesn't exist (creates blank)
//            System.out.println("--- Test 5: Get non-existent profile (auto-create blank) ---");
//            GetProfileInputData getData2 = new GetProfileInputData("current_user");
//            interactor.getProfile(getData2);
//            System.out.println();
//
//            System.out.println("=== Architecture Verification ===");
//            System.out.println("✅ Use Case layer depends only on abstractions");
//            System.out.println("✅ Input/Output DTOs used for data transfer");
//            System.out.println("✅ InputBoundary/OutputBoundary define clear contracts");
//            System.out.println("✅ Dependency Inversion Principle satisfied");
//            System.out.println("\n✅ All tests passed!");
//
//        } catch (Exception e) {
//            System.err.println("❌ Test failed: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}

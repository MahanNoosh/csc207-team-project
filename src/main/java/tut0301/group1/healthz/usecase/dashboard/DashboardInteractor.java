package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.entities.Dashboard.Profile;

/**
 * Interactor for Dashboard Use Case
 */
public class DashboardInteractor implements DashboardInputBoundary {
    private final UserDataDataAccessInterface userDataAccess;
    private final DashboardOutputBoundary presenter;

    public DashboardInteractor(UserDataDataAccessInterface userDataAccess,
                               DashboardOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void loadDashboard(String userId) {
        try {
            System.out.println("Loading dashboard for user: " + userId);

            // Load profile from Supabase
            Profile profile = userDataAccess.loadCurrentUserProfile()
                    .orElseGet(() -> {
                        try {
                            System.out.println("No profile found, creating blank profile");
                            return userDataAccess.createBlankForCurrentUserIfMissing();
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to create blank profile", e);
                        }
                    });

            // Log profile details
            System.out.println("   Profile loaded:");
            System.out.println("   Weight: " + profile.getWeightKg() + " kg");
            System.out.println("   Height: " + profile.getHeightCm() + " cm");
            System.out.println("   Age: " + profile.getAgeYears());
            System.out.println("   Sex: " + profile.getSex());
            System.out.println("   Goal: " + profile.getGoal());
            System.out.println("   Activity: " + profile.getActivityLevelMET());

            // Calculate daily calorie goal
            int dailyCalorieGoal = CalorieCalculator.calculateDailyCalorieGoal(profile);
            System.out.println("🔥 Calculated daily calorie goal: " + dailyCalorieGoal);

            // Get calories consumed (TODO: implement food log integration)
            int caloriesConsumed = 0;
            int caloriesRemaining = dailyCalorieGoal - caloriesConsumed;

            // User name will be set by Navigator
            String userName = "User";

            // Create output data
            DashboardOutputData data = new DashboardOutputData(
                    dailyCalorieGoal,
                    caloriesConsumed,
                    caloriesRemaining,
                    userName
            );

            // Present to view
            presenter.presentDashboard(data);
            System.out.println("Dashboard data presented successfully");

        } catch (Exception e) {
            System.err.println("Error loading dashboard: " + e.getMessage());
            e.printStackTrace();
            presenter.presentError("Failed to load dashboard: " + e.getMessage());
        }
    }
}
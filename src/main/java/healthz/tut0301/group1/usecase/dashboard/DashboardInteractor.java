package healthz.tut0301.group1.usecase.dashboard;

import healthz.tut0301.group1.entities.Dashboard.ActivityEntry;
import healthz.tut0301.group1.entities.Dashboard.Profile;
import healthz.tut0301.group1.entities.nutrition.FoodLog;
import healthz.tut0301.group1.usecase.activity.activitylog.ActivityLogDataAccessInterface;
import healthz.tut0301.group1.usecase.food.logging.FoodLogGateway;

import java.time.LocalDate;
import java.util.List;

/**
 * Interactor for Dashboard Use Case
 */
public class DashboardInteractor implements DashboardInputBoundary {
    private final UserDataDataAccessInterface userDataAccess;
    private final FoodLogGateway foodLogGateway;
    private final ActivityLogDataAccessInterface activityLogDataAccess;
    private final DashboardOutputBoundary presenter;

    public DashboardInteractor(UserDataDataAccessInterface userDataAccess,
                               FoodLogGateway foodLogGateway,
                               ActivityLogDataAccessInterface activityLogDataAccess,
                               DashboardOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.foodLogGateway = foodLogGateway;
        this.activityLogDataAccess = activityLogDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void loadDashboard(String userId) {
        try {
            System.out.println("Loading dashboard for user: " + userId);

            // Supabase
            Profile profile = userDataAccess.loadCurrentUserProfile()
                    .orElseGet(() -> {
                        try {
                            System.out.println("No profile found, creating blank profile");
                            return userDataAccess.createBlankForCurrentUserIfMissing();
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to create blank profile", e);
                        }
                    });

            // Calculate daily calorie goal
            int dailyCalorieGoal = CalorieCalculator.calculateDailyCalorieGoal(profile);
            System.out.println("🔥 Calculated daily calorie goal: " + dailyCalorieGoal);

            // Get today's date for filtering
            LocalDate today = LocalDate.now();

            // Get activity logs for today and calculate calories burned
            List<ActivityEntry> activities = activityLogDataAccess.getActivitiesForUser();

            int activityCalories = 0;
            for (ActivityEntry entry : activities) {
                if (entry.getTimestamp().toLocalDate().equals(today)) {
                    activityCalories += (int) entry.getCaloriesBurned();
                }
            }

            // Get food logs for today and calculate calories consumed
            List<FoodLog> foodLogs = foodLogGateway.getFoodLogsByDate(userId, today);

            int caloriesConsumed = 0;
            for (FoodLog entry : foodLogs) {
                caloriesConsumed += (int) entry.getActualMacro().calories();
            }

            // Calculate remaining calories
            int caloriesRemaining = dailyCalorieGoal - caloriesConsumed + activityCalories;

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

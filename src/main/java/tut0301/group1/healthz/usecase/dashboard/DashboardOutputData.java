package tut0301.group1.healthz.usecase.dashboard;

/**
 * Output Data for Dashboard
 * Contains all data needed to display the dashboard
 */
public class DashboardOutputData {
    private final int dailyCalorieGoal;
    private final int caloriesConsumed;
    private final int caloriesRemaining;
    private final String userName;

    public DashboardOutputData(int dailyCalorieGoal,
                               int caloriesConsumed,
                               int caloriesRemaining,
                               String userName) {
        this.dailyCalorieGoal = dailyCalorieGoal;
        this.caloriesConsumed = caloriesConsumed;
        this.caloriesRemaining = caloriesRemaining;
        this.userName = userName;
    }

    public int getDailyCalorieGoal() {
        return dailyCalorieGoal;
    }

    public int getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public int getCaloriesRemaining() {
        return caloriesRemaining;
    }

    public String getUserName() {
        return userName;
    }
}
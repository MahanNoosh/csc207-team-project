package tut0301.group1.healthz.interfaceadapter.dashboard;

/**
 * View Model for Dashboard
 * Holds the presentation state of the dashboard
 */
public class DashboardViewModel {
    private int dailyCalorieGoal;
    private int caloriesConsumed;
    private int caloriesRemaining;
    private String userName;
    private String errorMessage;

    public DashboardViewModel() {
        // Initialize with defaults
        this.dailyCalorieGoal = 2000;
        this.caloriesConsumed = 0;
        this.caloriesRemaining = 2000;
        this.userName = "User";
        this.errorMessage = null;
    }

    // Getters
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

    public String getErrorMessage() {
        return errorMessage;
    }

    // Setters
    public void setDailyCalorieGoal(int dailyCalorieGoal) {
        this.dailyCalorieGoal = dailyCalorieGoal;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public void setCaloriesRemaining(int caloriesRemaining) {
        this.caloriesRemaining = caloriesRemaining;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

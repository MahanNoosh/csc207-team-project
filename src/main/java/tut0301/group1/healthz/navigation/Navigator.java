package tut0301.group1.healthz.navigation;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import tut0301.group1.healthz.view.auth.LoginView;
import tut0301.group1.healthz.view.auth.SignupView;
import tut0301.group1.healthz.view.macro.MacroSearchView;
import tut0301.group1.healthz.view.settings.SettingsView;
import tut0301.group1.healthz.view.dashboard.DashboardView;
import tut0301.group1.healthz.view.nutrition.FoodLogView;

/**
 * Navigator - Handles all navigation between views
 */

public class Navigator {

    private static Navigator instance;

    private Stage primaryStage;

    private Navigator() {
        // private so we can prevent instantiation
    }

    /**
     * Get the singleton instance
     */
    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }

    /**
     * Initialize the navigator with the primary stage
     * Call this once at application startup
     */
    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    // ========== NAVIGATION METHODS ==========

    /**
     * Navigate to Login/Landing page
     */
    public void showLogin() {
        LoginView loginView = new LoginView();

        // Connect navigation from login page
        setupLoginNavigation(loginView);

        // Switch to login scene
        primaryStage.setScene(loginView.getScene());
        primaryStage.setTitle("HealthZ - Welcome");
    }

    /**
     * Navigate to Signup page
     */
    public void showSignup() {
        SignupView signupView = new SignupView();

        // Switch to signup scene
        primaryStage.setScene(signupView.getScene());
        primaryStage.setTitle("HealthZ - Sign Up");
    }

    /**
     * Navigate to Macro Search page
     */
    public void showMacroSearch() {
        MacroSearchView macroSearchView = new MacroSearchView();

        // connect macro search navigation
        setupMacroSearchNavigation(macroSearchView);

        // Switch to macro search scene
        primaryStage.setScene(macroSearchView.getScene());
        primaryStage.setTitle("HealthZ - Nutrition Lookup");
    }

    /**
     * Navigate to Settings page
     */
    public void showSettings() {
        SettingsView settingsView = new SettingsView();

        // connect settings navigation
        setupSettingsNavigation(settingsView);

        // Switch to settings scene
        primaryStage.setScene(settingsView.getScene());
        primaryStage.setTitle("HealthZ - Settings");
    }

    /**
     * Navigate to Food Log Page
     */
    public void showFoodLog() {
        FoodLogView foodLogView = new FoodLogView();

        // connect food log navigation
        setupFoodLogNavigation(foodLogView);

        // Switch to food log scene
        primaryStage.setScene(foodLogView.getScene());
        primaryStage.setTitle("HealthZ - Food Log");
    }

    /**
     * Navigate to Main App/Dashboard (after successful login/signup)
     */
    public void showMainApp() {
        DashboardView dashboardView = new DashboardView();

        // connect dashboard navigation
        setupDashboardNavigation(dashboardView);

        // Switch to dashboard scene
        primaryStage.setScene(dashboardView.getScene());
        primaryStage.setTitle("HealthZ - Dashboard");
    }

    /**
     * Go back to previous page
     * (Can implement navigation history stack later)
     */
    public void goBack() {
        // TODO: Implement navigation history stack
        System.out.println("Going back...");
        showLogin();
    }

    // ========== PRIVATE HELPER METHODS ==========

    /**
     * Setup navigation for Login page
     * Connects Sign Up and Log In buttons to navigation
     */
    private void setupLoginNavigation(LoginView loginView) {
        // Connect Sign Up button
        loginView.getSignUpButton().setOnAction(e -> {
            System.out.println("Navigating to Sign Up...");
            showSignup();
        });

        // Connect Log In button
        loginView.getLogInButton().setOnAction(e -> {
            System.out.println("Logging in...");
            // TODO: show actual login form or validate credentials
            // for now go to dashboard
            showMainApp();
        });
    }

    /**
     * Setup navigation for Dashboard page
     * Connects Sign Up and Log In buttons to navigation
     */
    private void setupDashboardNavigation(DashboardView dashboardView) {

        // connect to Settings page
        dashboardView.getSettingsButton().setOnAction(e -> {
            System.out.println("Navigating to Settings...");
            showSettings();
        });

        // connect to home (dashboard page)
        dashboardView.getHomeButton().setOnAction(e -> {
            System.out.println("Navigating to Home...");
            showMainApp();
        });

        // connect to macro page
        dashboardView.getMacrosButton().setOnAction(e -> {
            System.out.println("Navigating to Macros..");
            showMacroSearch();
        });

        // TODO: create showRecipeSearch
        dashboardView.getRecipesButton().setOnAction(e -> {
            System.out.println("Navigating to Recipes..");
        });

        // connect to food log page
        dashboardView.getFoodLogButton().setOnAction(e -> {
            System.out.println("Navigating to Food Log...");
            showFoodLog();
        });

        // TODO: create showActivityLog
        dashboardView.getActivityLogButton().setOnAction(e -> {
            System.out.println("Navigating to Activity Log...");
            // showActivityLog();
        });
    }

    /**
     * Setup navigation for Settings page
     */
    private void setupSettingsNavigation(SettingsView settingsView) {
        settingsView.getSidebar().getDashboardButton().setOnAction(e -> {
            System.out.println("Navigating to Dashboard...");
            showMainApp();
        });

        settingsView.getSidebar().getMealTrackerButton().setOnAction(e -> {
            System.out.println("Navigating to Food Log...");
            showFoodLog();
        });
    }

    /**
     * Setup navigation for Macro Search page
     */
    private void setupMacroSearchNavigation(MacroSearchView macroSearchView) {
        macroSearchView.gethealthzBtn().setOnAction(e -> {
            System.out.println("Navigating to Health Z...");
            showMainApp();
        });
    }

    /**
     * Setup navigation for Food Log page
     */
    private void setupFoodLogNavigation(FoodLogView foodLogView) {
        // connect to dashboard
        foodLogView.getSidebar().getDashboardButton().setOnAction(e -> {
            System.out.println("Navigating to Food Log...");
            showMainApp();
        });

        // connect to settings
        foodLogView.getSidebar().getSettingsButton().setOnAction(e -> {
            System.out.println("Navigating to Settings...");
            showSettings();
        });
    }

}

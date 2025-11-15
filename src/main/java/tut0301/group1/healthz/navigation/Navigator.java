package tut0301.group1.healthz.navigation;

import javafx.stage.Stage;
import tut0301.group1.healthz.view.auth.LoginView;
import tut0301.group1.healthz.view.auth.SignupView;
import tut0301.group1.healthz.view.macro.MacroSearchView;
import tut0301.group1.healthz.view.settings.SettingsView;

/**
 * Navigator - Handles all navigation between views
 * Part of the Presentation layer in Clean Architecture
 *
 * Singleton pattern to ensure one instance throughout the app
 */
public class Navigator {

    // Singleton instance
    private static Navigator instance;

    // The main application stage
    private Stage primaryStage;

    // Private constructor for singleton
    private Navigator() {
        // Private to prevent direct instantiation
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

        // Switch to macro search scene
        primaryStage.setScene(macroSearchView.getScene());
        primaryStage.setTitle("HealthZ - Nutrition Lookup");
    }

    /**
     * Navigate to Settings page
     */
    public void showSettings() {
        SettingsView settingsView = new SettingsView();

        // Switch to settings scene
        primaryStage.setScene(settingsView.getScene());
        primaryStage.setTitle("HealthZ - Settings");
    }

    /**
     * Navigate to Main App/Dashboard (after successful login/signup)
     */
    public void showMainApp() {
        // TODO: create DashboardView
        // For now, show macro search as placeholder
        System.out.println("✅ Login/Signup successful! Navigating to main app...");
        showMacroSearch();
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
            System.out.println("📝 Navigating to Sign Up...");
            showSignup();
        });

        // Connect Log In button
        loginView.getLogInButton().setOnAction(e -> {
            System.out.println("🔐 Logging in...");
            // TODO: show actual login form or validate credentials
            // For now, just go to main app
            showMainApp();
        });
    }
}
package tut0301.group1.healthz.navigation;

import javafx.stage.Stage;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseAuthGateway;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseClient;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginController;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginPresenter;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginViewModel;
import tut0301.group1.healthz.usecase.auth.AuthGateway;
import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInteractor;
import tut0301.group1.healthz.view.auth.LandingView;
import tut0301.group1.healthz.view.auth.LoginView;
import tut0301.group1.healthz.view.auth.SignupView;
import tut0301.group1.healthz.view.macro.MacroSearchView;
import tut0301.group1.healthz.view.settings.SettingsView;

/**
 * Navigator - Handles all navigation between views
 * Part of the Presentation layer in Clean Architecture
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
    public void showLanding() {
        LandingView landingView = new LandingView();

        // Connect navigation from login page
        setupLoginNavigation(landingView);

        // Switch to landing scene
        primaryStage.setScene(landingView.getScene());
        primaryStage.setTitle("HealthZ - Welcome");
    }

    /**
     * Navigate to Signup page
     */
    public void showSignup() {
        SignupView signupView = new SignupView();

        signupView.getLoginLinkButton().setOnAction(e -> {
            System.out.println("Already have an account -> back to login");
            showLogin();
        });

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

    public void showLogin() {
        LoginView loginView = new LoginView();

        // Sign up link -> go to signup
        loginView.getSignUpButton().setOnAction(e -> {
            System.out.println("📝 Navigating to Sign Up...");
            showSignup();
        });

        // Continue button -> perform login, then go to main app
        loginView.getLoginButton().setOnAction(e -> {
            System.out.println("🔐 Logging in with " +
                    loginView.getEmail());
            // TODO: validate credentials with your Login use case
            String url  = System.getenv("SUPABASE_URL");
            String anon = System.getenv("SUPABASE_ANON_KEY");
            if (url == null || anon == null) {
                System.err.println("Set SUPABASE_URL and SUPABASE_ANON_KEY");
                System.exit(1);
            }

            var client = new SupabaseClient(url, anon);
            AuthGateway authGateway = new SupabaseAuthGateway(client);
            var loginVM = new LoginViewModel();
            var loginPresenter = new LoginPresenter(loginVM);
            LoginInputBoundary loginUC = new LoginInteractor(authGateway, loginPresenter);
            var loginController = new LoginController(loginUC, loginPresenter);
            loginController.login(loginView.getEmail(), loginView.getPassword());
            if (loginVM.isLoggedIn()){
                showMainApp();
            }
            else {
                showLogin();
            }

        });

        primaryStage.setScene(loginView.getScene());
        primaryStage.setTitle("HealthZ - Log In");
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
    private void setupLoginNavigation(LandingView landingView) {
        // Connect Sign Up button
        landingView.getSignUpButton().setOnAction(e -> {
            System.out.println("📝 Navigating to Sign Up...");
            showSignup();
        });

        // Connect Log In button
        landingView.getLogInButton().setOnAction(e -> {
            System.out.println("🔐 Logging in...");
            // TODO: show actual login form or validate credentials
            // For now, just go to main app
            showLogin();
        });
    }
}
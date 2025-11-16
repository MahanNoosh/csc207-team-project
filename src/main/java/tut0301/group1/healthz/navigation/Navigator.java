package tut0301.group1.healthz.navigation;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseAuthGateway;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseClient;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseUserDataGateway;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginController;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginPresenter;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginViewModel;
import tut0301.group1.healthz.interfaceadapter.auth.mapping.SignupProfileMapper;
import tut0301.group1.healthz.usecase.auth.AuthGateway;
import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInteractor;
import tut0301.group1.healthz.view.auth.LandingView;
import tut0301.group1.healthz.view.auth.LoginView;
import tut0301.group1.healthz.view.auth.SignupView;
import tut0301.group1.healthz.view.auth.signuppanels.EmailVerificationView;
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

    public void showEmailVerification(SignupView.SignupData signupData) {
        EmailVerificationView view = new EmailVerificationView(signupData);

        // When user clicks "I've verified my email"
        view.getContinueButton().setOnAction(e -> {
            System.out.println("✅ User claims email is verified. Trying login + profile save...");

            String url  = System.getenv("SUPABASE_URL");
            String anon = System.getenv("SUPABASE_ANON_KEY");
            if (url == null || anon == null) {
                System.err.println("Set SUPABASE_URL and SUPABASE_ANON_KEY");
                showError("Supabase not configured. Please set SUPABASE_URL and SUPABASE_ANON_KEY.");
                return;
            }

            // 1) ONE shared client
            SupabaseClient client = new SupabaseClient(url, anon);

            // 2) Auth gateway + login use case on SAME client
            AuthGateway authGateway = new SupabaseAuthGateway(client);
            LoginViewModel loginVM = new LoginViewModel();
            LoginPresenter loginPresenter = new LoginPresenter(loginVM);
            LoginInputBoundary loginUC = new LoginInteractor(authGateway, loginPresenter);
            LoginController loginController = new LoginController(loginUC, loginPresenter);

            // ⚠️ IMPORTANT: use the email & password from the *verification view* (i.e. from signup)
            String email = view.getEmail();       // from SignupData
            String password = view.getPassword(); // from SignupData

            loginController.login(email, password);

            // 3) Check if login succeeded
            if (!loginVM.isLoggedIn()) {
                System.out.println("Not logged in. accessToken or userId is null.");
                showError("Could not log you in.\n" +
                        "Make sure you clicked the verification link in your email first.");
                return;
            }

            try {
                // 4) We now have a logged-in user
                String userId = loginVM.getUserId(); // or client.getUserId() if wired

                // 5) Map signup data -> Profile
                var profile = SignupProfileMapper.toProfile(userId, view.getSignupData());

                // 6) Save profile using SAME client
                SupabaseUserDataGateway userDataGateway = new SupabaseUserDataGateway(client);
                userDataGateway.upsertProfile(profile);

                // 7) Navigate to main app
                showMainApp();

            } catch (Exception ex) {
                System.err.println("Login / profile save failed: " + ex.getMessage());
                showError("Failed to save your profile: " + ex.getMessage());
            }
        });

        // When user clicks "Back to Log in"
        view.getBackToLoginButton().setOnAction(e -> {
            System.out.println("↩ Back to login from email verification");
            showLogin();
        });

        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("HealthZ - Verify your email");
    }

    // helper:
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
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
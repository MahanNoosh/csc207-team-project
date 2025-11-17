package tut0301.group1.healthz.navigation;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tut0301.group1.healthz.dataaccess.API.FatSecretMacroDetailGateway;
import tut0301.group1.healthz.dataaccess.API.FatSecretMacroSearchGateway;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseAuthGateway;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseClient;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseUserDataGateway;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginController;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginPresenter;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginViewModel;
import tut0301.group1.healthz.interfaceadapter.auth.mapping.SignupProfileMapper;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailPresenter;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailViewModel;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchPresenter;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchViewModel;
import tut0301.group1.healthz.usecase.auth.AuthGateway;
import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInteractor;
import tut0301.group1.healthz.usecase.macrosearch.MacroDetailGateway;
import tut0301.group1.healthz.usecase.macrosearch.MacroDetailInputBoundary;
import tut0301.group1.healthz.usecase.macrosearch.MacroDetailInteractor;
import tut0301.group1.healthz.usecase.macrosearch.MacroSearchGateway;
import tut0301.group1.healthz.usecase.macrosearch.MacroSearchInputBoundary;
import tut0301.group1.healthz.usecase.macrosearch.MacroSearchInteractor;
import tut0301.group1.healthz.view.auth.LandingView;
import tut0301.group1.healthz.view.auth.LoginView;
import tut0301.group1.healthz.view.auth.SignupView;
import tut0301.group1.healthz.view.auth.signuppanels.EmailVerificationView;
import tut0301.group1.healthz.view.macro.SingleMacroPage;
import tut0301.group1.healthz.view.macro.MacroSearchView;
import tut0301.group1.healthz.view.settings.SettingsView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Navigator - Handles all navigation between views
 * Part of the Presentation layer in Clean Architecture
 */

public class Navigator {

    private static Navigator instance;

    private Stage primaryStage;
    private SignupView.SignupData pendingSignupData;
    private Timeline emailCheckTimeline;

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
        MacroSearchViewModel macroSearchViewModel = new MacroSearchViewModel();
        MacroSearchPresenter presenter = new MacroSearchPresenter(macroSearchViewModel);
        MacroSearchGateway gateway = new FatSecretMacroSearchGateway();
        MacroSearchInputBoundary interactor = new MacroSearchInteractor(gateway, presenter);
        MacroSearchController controller = new MacroSearchController(interactor, presenter);

        MacroSearchView macroSearchView = new MacroSearchView(controller, macroSearchViewModel);

        // Switch to macro search scene
        primaryStage.setScene(macroSearchView.getScene());
        primaryStage.setTitle("HealthZ - Nutrition Lookup");
    }

    /**
     * Navigate to a single macro detail page for a selected food id.
     */
    public void showMacroDetails(long foodId) {
        MacroDetailViewModel detailViewModel = new MacroDetailViewModel();
        MacroDetailPresenter presenter = new MacroDetailPresenter(detailViewModel);
        MacroDetailGateway gateway = new FatSecretMacroDetailGateway();
        MacroDetailInputBoundary interactor = new MacroDetailInteractor(gateway, presenter);
        MacroDetailController controller = new MacroDetailController(interactor, presenter);

        controller.fetch(foodId);

        SingleMacroPage detailView = new SingleMacroPage(controller, detailViewModel, this);

        primaryStage.setScene(detailView.getScene());
        primaryStage.setTitle("HealthZ - Food Details");
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
        // Remember signup data so retry helper can use it
        this.pendingSignupData = signupData;

        EmailVerificationView view = new EmailVerificationView(signupData);
        view.setStatusText("Waiting for verification… We’ll detect it automatically.");

        // Back to login -> stop retry and go back
        view.getBackToLoginButton().setOnAction(e -> {
            System.out.println("↩ Back to login from email verification");
            if (emailCheckTimeline != null) {
                emailCheckTimeline.stop();
                emailCheckTimeline = null;
            }
            showLogin();
        });

        // Resend button:
        //  - sends a new email
        //  - restarts the 3-minute login retry window
        //  - applies a ~2min cooldown
        view.getResendButton().setOnAction(e -> {
            System.out.println("🔁 User clicked: Resend verification email");
            resendVerificationEmail(signupData, view);

            // restart 3-minute auto-login window (your existing helper)
            startEmailCheckTimeline();

            // NEW: start the visible resend cooldown for ~2 minutes
            view.startResendCooldown(120);
        });


        // Start the initial 3-minute auto-login window
        startEmailCheckTimeline();

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
            System.out.println("🔐 Logging in with " + loginView.getEmail());

            String url  = System.getenv("SUPABASE_URL");
            String anon = System.getenv("SUPABASE_ANON_KEY");
            if (url == null || anon == null) {
                System.err.println("Set SUPABASE_URL and SUPABASE_ANON_KEY");
                showError("Supabase not configured. Please set SUPABASE_URL and SUPABASE_ANON_KEY.");
                return;
            }

            SupabaseClient client = new SupabaseClient(url, anon);
            AuthGateway authGateway = new SupabaseAuthGateway(client);
            LoginViewModel loginVM = new LoginViewModel();
            LoginPresenter loginPresenter = new LoginPresenter(loginVM);
            LoginInputBoundary loginUC = new LoginInteractor(authGateway, loginPresenter);
            LoginController loginController = new LoginController(loginUC, loginPresenter);

            // 1) Try login
            loginController.login(loginView.getEmail(), loginView.getPassword());

            if (loginVM.isLoggedIn()) {
                System.out.println("✅ Login successful, ensuring profile row exists...");

                try {
                    // 2) Make sure user_data row exists (create blank if missing)
                    SupabaseUserDataGateway userDataGateway = new SupabaseUserDataGateway(client);
                    userDataGateway.createBlankForCurrentUserIfMissing();
                    System.out.println("💾 user_data row present/created.");
                } catch (Exception ex) {
                    System.err.println("Failed to init user_data row: " + ex.getMessage());
                    // optional: showError("Logged in, but could not initialize your profile data.");
                }

                // 3) Continue to main app
                showMainApp();
            } else {
                System.out.println("❌ Login failed.");
                showError("Login failed. Please check your email and password, or verify your email.");
                // stay on the same login screen
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

    private void tryLoginAndSaveProfileOnce(boolean silent) {
        if (pendingSignupData == null) {
            if (!silent) {
                showError("No signup in progress. Please sign up again.");
            }
            return;
        }

        SignupView.SignupData signupData = pendingSignupData;

        String url  = System.getenv("SUPABASE_URL");
        String anon = System.getenv("SUPABASE_ANON_KEY");
        if (url == null || anon == null) {
            if (!silent) {
                showError("Supabase not configured. Please set SUPABASE_URL and SUPABASE_ANON_KEY.");
            }
            return;
        }

        SupabaseClient client = new SupabaseClient(url, anon);

        AuthGateway authGateway = new SupabaseAuthGateway(client);
        LoginViewModel loginVM = new LoginViewModel();
        LoginPresenter loginPresenter = new LoginPresenter(loginVM);
        LoginInputBoundary loginUC = new LoginInteractor(authGateway, loginPresenter);
        LoginController loginController = new LoginController(loginUC, loginPresenter);

        System.out.println("Attempting login for " + signupData.getEmail());
        loginController.login(signupData.getEmail(), signupData.getPassword());

        if (!loginVM.isLoggedIn()) {
            System.out.println("Still not logged in (email probably not verified yet).");
            if (!silent) {
                showError(
                        "We couldn't log you in yet.\n" +
                                "Please make sure you clicked the verification link in your email,\n" +
                                "then try again."
                );
            }
            return;
        }

        try {
            String userId = loginVM.getUserId();
            System.out.println("🔐 Login succeeded. userId = " + userId);

            // Map signup data -> Profile
            var profile = SignupProfileMapper.toProfile(userId, signupData);

            // Save profile
            SupabaseUserDataGateway userDataGateway = new SupabaseUserDataGateway(client);
            userDataGateway.upsertProfile(profile);

            System.out.println("💾 Profile saved successfully. Navigating to main app...");

            // Stop if running
            if (emailCheckTimeline != null) {
                emailCheckTimeline.stop();
                emailCheckTimeline = null;
            }

            showMainApp();

        } catch (Exception ex) {
            System.err.println("Login / profile save failed: " + ex.getMessage());
            if (!silent) {
                showError("Failed to save your profile: " + ex.getMessage());
            }
        }
    }

    private void resendVerificationEmail(SignupView.SignupData signupData, EmailVerificationView view) {
        String url  = System.getenv("SUPABASE_URL");
        String anon = System.getenv("SUPABASE_ANON_KEY");
        if (url == null || anon == null) {
            System.err.println("Supabase not configured; cannot resend verification email.");
            view.setStatusText("Could not resend email (server not configured).");
            return;
        }

        try {
            SupabaseClient client = new SupabaseClient(url, anon);
            client.resendSignupVerification(signupData.getEmail());

            System.out.println("📧 Resent verification email to " + signupData.getEmail());
            view.setStatusText("Verification email resent to " + signupData.getEmail() + ". Waiting for verification…");
        } catch (Exception ex) {
            System.err.println("Failed to resend verification email: " + ex.getMessage());
            view.setStatusText("Failed to resend email. Please check your inbox or try again later.");
        }
    }

    private void startEmailCheckTimeline() {
        // Stop old one if it exists
        if (emailCheckTimeline != null) {
            emailCheckTimeline.stop();
        }

        final int maxAttempts = 18; // 18 * 10s = ~3 minutes
        emailCheckTimeline = new Timeline();
        emailCheckTimeline.setCycleCount(maxAttempts);

        emailCheckTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(10), ev -> {
                    System.out.println("⏳ Auto-checking email verification...");
                    tryLoginAndSaveProfileOnce(true); // silent mode
                })
        );

        emailCheckTimeline.play();
    }





}
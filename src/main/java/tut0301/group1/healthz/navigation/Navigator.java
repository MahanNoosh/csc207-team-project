package tut0301.group1.healthz.navigation;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tut0301.group1.healthz.dataaccess.API.FatSecret.FatSecretFoodDetailDataAccessObject;
import tut0301.group1.healthz.dataaccess.API.FatSecret.FatSecretFoodSearchDataAccessObject;
//import tut0301.group1.healthz.dataaccess.API.FatSecretMacroSearchGateway;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseAuthDataAccessObject;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseClient;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseUserDataDataAccessObject;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginController;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginPresenter;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginViewModel;
import tut0301.group1.healthz.interfaceadapter.auth.mapping.SignupProfileMapper;
import tut0301.group1.healthz.interfaceadapter.food.FoodDetailPresenter;
import tut0301.group1.healthz.interfaceadapter.food.FoodSearchPresenter;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailViewModel;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchViewModel;
import tut0301.group1.healthz.usecase.auth.AuthGateway;
import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInteractor;
import tut0301.group1.healthz.usecase.food.detail.FoodDetailGateway;
import tut0301.group1.healthz.usecase.food.detail.GetFoodDetailInputBoundary;
import tut0301.group1.healthz.usecase.food.detail.GetFoodDetailInteractor;
import tut0301.group1.healthz.usecase.food.search.FoodSearchDataAccessInterface;
import tut0301.group1.healthz.usecase.food.search.SearchFoodInputBoundary;
import tut0301.group1.healthz.usecase.food.search.SearchFoodInteractor;
import tut0301.group1.healthz.usecase.food.search.SearchFoodOutputBoundary;
//import tut0301.group1.healthz.usecase.macrosearch.MacroDetailGateway;
//import tut0301.group1.healthz.usecase.macrosearch.MacroDetailInputBoundary;
//import tut0301.group1.healthz.usecase.macrosearch.MacroDetailInteractor;
//import tut0301.group1.healthz.usecase.macrosearch.MacroSearchGateway;
//import tut0301.group1.healthz.usecase.macrosearch.MacroSearchInputBoundary;
//import tut0301.group1.healthz.usecase.macrosearch.MacroSearchInteractor;
import tut0301.group1.healthz.view.auth.LandingView;
import tut0301.group1.healthz.view.auth.LoginView;
import tut0301.group1.healthz.view.auth.SignupView;
import tut0301.group1.healthz.view.auth.signuppanels.EmailVerificationView;
import tut0301.group1.healthz.view.macro.SingleMacroPage;
import tut0301.group1.healthz.view.macro.MacroSearchView;
import tut0301.group1.healthz.view.recipe.RecipeDetailView;
import tut0301.group1.healthz.view.settings.SettingsView;
import tut0301.group1.healthz.view.dashboard.DashboardView;
import tut0301.group1.healthz.view.recipe.RecipeSearchView;
import tut0301.group1.healthz.view.recipe.FavoriteRecipeView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.List;

/**
 * Navigator - Handles all navigation between views
 * Part of the Presentation layer in Clean Architecture
 */

public class Navigator {

    private static Navigator instance;

    private Stage primaryStage;
    private SignupView.SignupData pendingSignupData;
    private Timeline emailCheckTimeline;

    private SupabaseClient authenticatedClient;

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
        // Clean Architecture Layer Setup:
        // 1. ViewModel (Interface Adapter)
        MacroSearchViewModel macroSearchViewModel = new MacroSearchViewModel();

        // 2. Presenter (Interface Adapter) - implements SearchFoodOutputBoundary
        SearchFoodOutputBoundary presenter = new FoodSearchPresenter(macroSearchViewModel);

        // 3. Gateway (Data Access) - implements FoodSearchGateway
        FoodSearchDataAccessInterface gateway = new FatSecretFoodSearchDataAccessObject();

        // 4. Interactor (Use Case) - depends on gateway and outputBoundary interfaces
        SearchFoodInputBoundary interactor = new SearchFoodInteractor(gateway, presenter);

        // 5. Controller (Interface Adapter) - only knows about interactor
        MacroSearchController controller = new MacroSearchController(interactor);

        // Set initial state in ViewModel before creating view
        macroSearchViewModel.setLoading(false);
        macroSearchViewModel.setMessage(null);
        macroSearchViewModel.setResults(java.util.List.of());

        // 6. View - observes ViewModel
        MacroSearchView macroSearchView = new MacroSearchView(controller, macroSearchViewModel, this);

        // Switch to macro search scene
        primaryStage.setScene(macroSearchView.getScene());
        primaryStage.setTitle("HealthZ - Nutrition Lookup");
    }

    /**
     * Navigate to a single macro detail page for a selected food id.
     */
    public void showMacroDetails(long foodId) {
        // Clean Architecture Layer Setup:
        // 1. ViewModel (Interface Adapter)
        MacroDetailViewModel detailViewModel = new MacroDetailViewModel();

        // 2. Presenter (Interface Adapter) - implements OutputBoundary
        FoodDetailPresenter presenter = new FoodDetailPresenter(detailViewModel);

        // 3. Gateway (Interface Adapter) - implements gateway interface
        FoodDetailGateway gateway = new FatSecretFoodDetailDataAccessObject();

        // 4. Interactor (Use Case) - depends on gateway and outputBoundary interfaces
        GetFoodDetailInputBoundary interactor = new GetFoodDetailInteractor(gateway, presenter);

        // 5. Controller (Interface Adapter) - only knows about interactor
        MacroDetailController controller = new MacroDetailController(interactor);

        // Set initial loading state in ViewModel before calling controller
        detailViewModel.setLoading(true);
        detailViewModel.setMessage(null);
        detailViewModel.setDetails(null);

        // Controller calls interactor, which will call presenter, which updates viewModel
        controller.fetch(foodId);

        // 6. View - observes ViewModel
        SingleMacroPage detailView = new SingleMacroPage(controller, detailViewModel, this);

        primaryStage.setScene(detailView.getScene());
        primaryStage.setTitle("HealthZ - Food Details");
    }

    /**
     * Navigate to Settings page
     */
    public void showRecipeSearch() {
        RecipeSearchView recipeSearchView = new RecipeSearchView(this);
        setupRecipeNavigation(recipeSearchView);

        primaryStage.setScene(recipeSearchView.getScene());
        primaryStage.setTitle("HealthZ - Recipe Search");
    }

    /**
     * Navigate to Favorite Recipe page
     */
    public void showFavoriteRecipes() {
        String userName = getUserDisplayName();
        FavoriteRecipeView favoriteRecipeView = new FavoriteRecipeView(userName, this);
        setupFavoriteRecipes(favoriteRecipeView);
        primaryStage.setScene(favoriteRecipeView.getScene());
        primaryStage.setTitle("HealthZ - Favorite Recipes");
    }

    /**
     * Navigate to Recipe Detail page
     */
    public void showRecipeDetail(String recipeName, String imageUrl,
                                 Double calories, Double protein, Double carbs, Double fats,
                                 String servingSize, List<String> dietaryTags,
                                 List<String> ingredients, List<String> instructions) {

        RecipeDetailView detailView = new RecipeDetailView(
                recipeName, imageUrl, calories, protein, carbs, fats,
                servingSize, dietaryTags, ingredients, instructions
        );

        // Setup back button navigation
        detailView.getBackButton().setOnAction(e -> {
            System.out.println("Going back from recipe detail...");
            // TODO: Track which page user came from and go back there
            showRecipeSearch(); // Default: go back to search
        });

        primaryStage.setScene(detailView.getScene());
        primaryStage.setTitle("HealthZ - " + recipeName);
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
     * Navigate to Dashboard page
     */
    public void showDashboard() {
        String userName = getUserDisplayName();
        DashboardView dashboardView = new DashboardView(userName);
        setupDashboardNavigation(dashboardView);
        primaryStage.setScene(dashboardView.getScene());
        primaryStage.setTitle("HealthZ - Dashboard");
    }

    private String getUserDisplayName() {
        // ✅ Use the authenticated client if available
        if (authenticatedClient != null) {
            String displayName = authenticatedClient.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                return displayName;
            }

            try {
                String email = authenticatedClient.getUserEmail();
                if (email != null && email.contains("@")) {
                    String firstName = email.split("@")[0].split("\\.")[0];
                    return firstName.substring(0, 1).toUpperCase() +
                            firstName.substring(1).toLowerCase();
                }
            } catch (Exception e) {
                System.err.println("Could not get email: " + e.getMessage());
            }
        }

        return "User";
    }

    /**
     * Navigate to Main App/Dashboard (after successful login/signup)
     */
    public void showMainApp() {
        // TODO: create DashboardView
        // For now, show macro search as placeholder
        System.out.println("✅ Login/Signup successful! Navigating to main app...");
        showDashboard();
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
            AuthGateway authGateway = new SupabaseAuthDataAccessObject(client);
            LoginViewModel loginVM = new LoginViewModel();
            LoginPresenter loginPresenter = new LoginPresenter(loginVM);
            LoginInputBoundary loginUC = new LoginInteractor(authGateway, loginPresenter);
            LoginController loginController = new LoginController(loginUC, loginPresenter);

            // 1) Try login
            loginController.login(loginView.getEmail(), loginView.getPassword());

            if (loginVM.isLoggedIn()) {
                System.out.println("✅ Login successful, ensuring profile row exists...");

                this.authenticatedClient = client;

                try {
                    // 2) Make sure user_data row exists (create blank if missing)
                    SupabaseUserDataDataAccessObject userDataGateway = new SupabaseUserDataDataAccessObject(client);
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

        AuthGateway authGateway = new SupabaseAuthDataAccessObject(client);
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

            this.authenticatedClient = client;

            // Map signup data -> Profile
            var profile = SignupProfileMapper.toProfile(userId, signupData);

            // Save profile
            SupabaseUserDataDataAccessObject userDataGateway = new SupabaseUserDataDataAccessObject(client);
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

    /**
     * Setup navigation for Dashboard page
     */
    private void setupDashboardNavigation(DashboardView dashboardView) {
        // Settings button
        dashboardView.getSettingsButton().setOnAction(e -> {
            System.out.println("Navigating to Settings...");
            showSettings();
        });

        // Recipes button
        dashboardView.getRecipesButton().setOnAction(e -> {
            System.out.println("Navigating to Recipes...");
            showRecipeSearch();
        });

        // Macros button
        dashboardView.getMacrosButton().setOnAction(e -> {
            System.out.println("Navigating to Macro Search...");
            showMacroSearch();
        });

        // Food Log button
        dashboardView.getFoodLogButton().setOnAction(e -> {
            System.out.println("Navigating to Food Log...");
            // TODO: showMealTracker();
        });

        // Activity Log button
        dashboardView.getActivityLogButton().setOnAction(e -> {
            System.out.println("Navigating to Activity Log...");
            // TODO: showActivityTracker();
        });
    }

    /**
     * Setup navigation for Recipe Search page
     */
    private void setupRecipeNavigation(RecipeSearchView recipeSearchView) {
        recipeSearchView.getFavoriteRecipesButton().setOnAction(e -> {
            System.out.println("Navigating to favorite recipes page...");
            showFavoriteRecipes();
        });
    }

    /**
     * Setup navigation for Favorite Recipe page
     */
    private void setupFavoriteRecipes(FavoriteRecipeView favoriteRecipeView) {
        favoriteRecipeView.getBackButton().setOnAction(e -> {
            System.out.println("Navigating to Back button...");
            showRecipeSearch();
        });
    }

}
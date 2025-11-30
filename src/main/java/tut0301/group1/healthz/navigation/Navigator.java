package tut0301.group1.healthz.navigation;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tut0301.group1.healthz.dataaccess.API.FatSecret.FatSecretFoodDetailDataAccessObject;
import tut0301.group1.healthz.dataaccess.API.FatSecret.FatSecretFoodSearchDataAccessObject;
//import tut0301.group1.healthz.dataaccess.API.FatSecretMacroSearchGateway;
import tut0301.group1.healthz.dataaccess.API.FatSecretRecipeDetailDataAccessObject;
import tut0301.group1.healthz.dataaccess.API.FatSecretRecipeSearchDataAccessObject;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseAuthDataAccessObject;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseClient;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseFavoriteRecipeDataAccessObject;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseUserDataDataAccessObject;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginController;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginPresenter;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginViewModel;
import tut0301.group1.healthz.interfaceadapter.auth.mapping.SignupProfileMapper;
import tut0301.group1.healthz.interfaceadapter.favoriterecipe.AddFavoriteController;
import tut0301.group1.healthz.interfaceadapter.food.FoodDetailPresenter;
import tut0301.group1.healthz.interfaceadapter.food.FoodSearchPresenter;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailViewModel;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchViewModel;
import tut0301.group1.healthz.interfaceadapter.recipe.*;
import tut0301.group1.healthz.interfaceadapter.favoriterecipe.FavoriteRecipeController;
import tut0301.group1.healthz.interfaceadapter.favoriterecipe.FavoriteRecipePresenter;
import tut0301.group1.healthz.interfaceadapter.favoriterecipe.FavoriteRecipeViewModel;
import tut0301.group1.healthz.usecase.auth.AuthGateway;
import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInteractor;
import tut0301.group1.healthz.usecase.favoriterecipe.*;
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
import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchGateway;
import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchInputBoundary;
import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchInteractor;
import tut0301.group1.healthz.usecase.recipesearch.detailed.RecipeDetailGateway;
import tut0301.group1.healthz.usecase.recipesearch.detailed.RecipeDetailInputBoundary;
import tut0301.group1.healthz.usecase.recipesearch.detailed.RecipeDetailInteractor;
import tut0301.group1.healthz.view.auth.LandingView;
import tut0301.group1.healthz.view.auth.LoginView;
import tut0301.group1.healthz.view.auth.SignupView;
import tut0301.group1.healthz.view.auth.LogoutView;
import tut0301.group1.healthz.view.auth.signuppanels.EmailVerificationView;
import tut0301.group1.healthz.view.macro.SingleMacroPage;
import tut0301.group1.healthz.view.macro.MacroSearchView;
import tut0301.group1.healthz.view.recipe.RecipeDetailView;
import tut0301.group1.healthz.view.settings.SettingsView;
import tut0301.group1.healthz.view.dashboard.DashboardView;
import tut0301.group1.healthz.view.recipe.RecipeSearchView;
import tut0301.group1.healthz.view.recipe.FavoriteRecipeView;
import tut0301.group1.healthz.view.nutrition.FoodLogView;
import tut0301.group1.healthz.view.activity.ActivityLogView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeController;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakePresenter;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeViewModel;
import tut0301.group1.healthz.usecase.food.logging.LogFoodIntakeInputBoundary;
import tut0301.group1.healthz.usecase.food.logging.LogFoodIntakeInteractor;
import tut0301.group1.healthz.usecase.food.logging.FoodLogGateway;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseFoodLogGateway;
import javafx.concurrent.Task;

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
        // 1. Get the current user ID
        String currentUserId = getCurrentUserId();
        if (currentUserId == null) {
            System.err.println("Warning: Showing details but user is not logged in.");
            // Optional: Handle guest login logic
        }

        // 2. Clean Architecture Layer Setup: Macro Detail
        // ViewModel (Interface Adapter)
        MacroDetailViewModel detailViewModel = new MacroDetailViewModel();

        // Presenter (Interface Adapter) - implements OutputBoundary
        FoodDetailPresenter presenter = new FoodDetailPresenter(detailViewModel);

        // Gateway (Interface Adapter) - implements gateway interface
        FoodDetailGateway gateway = new FatSecretFoodDetailDataAccessObject();

        // Interactor (Use Case) - depends on gateway and outputBoundary interfaces
        GetFoodDetailInputBoundary interactor = new GetFoodDetailInteractor(gateway, presenter);

        // Controller (Interface Adapter) - only knows about interactor
        MacroDetailController controller = new MacroDetailController(interactor);

        // 3. Clean Architecture Layer Setup: Log Food Intake (Add Button)
        LogFoodIntakeViewModel logViewModel = new LogFoodIntakeViewModel();
        LogFoodIntakePresenter logPresenter = new LogFoodIntakePresenter(logViewModel);

        // Use the existing authenticated Supabase client for logging
        FoodLogGateway logGateway = new SupabaseFoodLogGateway(authenticatedClient);

        LogFoodIntakeInputBoundary logInteractor = new LogFoodIntakeInteractor(logGateway, logPresenter);
        LogFoodIntakeController logController = new LogFoodIntakeController(logInteractor);

        // 4. Initialize State
        detailViewModel.setLoading(true);
        detailViewModel.setMessage(null);
        detailViewModel.setDetails(null);

        // 5. Fetch Data
        controller.fetch(foodId);

        // 6. Create View with injected dependencies
        SingleMacroPage detailView = new SingleMacroPage(
                controller,
                detailViewModel,
                logController,  // Injected Log Controller
                logViewModel,   // Injected Log ViewModel
                currentUserId,  // Injected User ID
                this
        );

        primaryStage.setScene(detailView.getScene());
        primaryStage.setTitle("HealthZ - Food Details");
    }

    /**
     * Navigate to Recipe Search page
     */
    public void showRecipeSearch() {
        // Recipe Search setup
        RecipeSearchViewModel recipeSearchViewModel = new RecipeSearchViewModel();
        RecipeSearchPresenter recipeSearchPresenter = new RecipeSearchPresenter(recipeSearchViewModel);
        RecipeSearchGateway recipeSearchGateway = new FatSecretRecipeSearchDataAccessObject();
        RecipeSearchInputBoundary recipeSearchInteractor = new RecipeSearchInteractor(recipeSearchGateway, recipeSearchPresenter);
        RecipeSearchController recipeSearchController = new RecipeSearchController(recipeSearchInteractor, recipeSearchPresenter);

        String userId = getCurrentUserId();
        String oauthToken = getFatSecretToken();

        FavoriteRecipeGateway favoriteGateway = new SupabaseFavoriteRecipeDataAccessObject(
                authenticatedClient,
                oauthToken
        );

        AddFavoriteInputBoundary addFavoriteInteractor = new AddFavoriteInteractor(favoriteGateway);
        AddFavoriteController addFavoriteController = new AddFavoriteController(addFavoriteInteractor);

        RecipeSearchView recipeSearchView = new RecipeSearchView(
                recipeSearchController,
                recipeSearchViewModel,
                this,
                addFavoriteController,
                userId
        );

        setupRecipeNavigation(recipeSearchView);

        primaryStage.setScene(recipeSearchView.getScene());
        primaryStage.setTitle("HealthZ - Recipe Search");
    }

    public void showFavoriteRecipes() {
        String userName = getUserDisplayName();
        String userId = getCurrentUserId();
        String oauthToken = getFatSecretToken();

        FavoriteRecipeViewModel viewModel = new FavoriteRecipeViewModel();
        FavoriteRecipePresenter presenter = new FavoriteRecipePresenter(viewModel);

        FavoriteRecipeGateway gateway = new SupabaseFavoriteRecipeDataAccessObject(
                authenticatedClient,
                oauthToken
        );

        LoadFavoritesInputBoundary loadInteractor = new LoadFavoritesInteractor(gateway, presenter);
        DeleteFavoriteInputBoundary deleteInteractor = new DeleteFavoriteInteractor(gateway, presenter);

        FavoriteRecipeController controller = new FavoriteRecipeController(loadInteractor, deleteInteractor, presenter);

        FavoriteRecipeView view = new FavoriteRecipeView(userName, userId, controller, viewModel, this);

        setupFavoriteRecipesNavigation(view);

        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("HealthZ - Favorite Recipes");
    }

    private String getFatSecretToken() {
        try {
            String clientId = System.getenv("FATSECRET_CLIENT_ID");
            String clientSecret = System.getenv("FATSECRET_CLIENT_SECRET");

            if (clientId == null || clientSecret == null) {
                System.err.println("FatSecret credentials not set");
                return null;
            }

            // Use your partner's OAuth class
            tut0301.group1.healthz.dataaccess.API.OAuth.OAuth fetcher =
                    new tut0301.group1.healthz.dataaccess.API.OAuth.OAuth(clientId, clientSecret);

            String jsonResponse = fetcher.getAccessTokenRaw("basic");
            return tut0301.group1.healthz.dataaccess.API.OAuth.OAuthDataAccessObject.extractAccessToken(jsonResponse);

        } catch (Exception e) {
            System.err.println("Failed to get FatSecret token: " + e.getMessage());
            return null;
        }
    }

    private String getCurrentUserId() {
        if (authenticatedClient != null) {
            try {
                return authenticatedClient.getUserId();
            } catch (Exception e) {
                System.err.println("Could not get user ID: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * Navigate to Recipe Detail page
     */
    public void showRecipeDetail(long recipeId) {
        System.out.println("🧭 Navigator: Showing recipe detail for ID: " + recipeId);

        // Create ViewModel
        RecipeDetailViewModel viewModel = new RecipeDetailViewModel();

        // Create Presenter
        RecipeDetailPresenter presenter = new RecipeDetailPresenter(viewModel);

        RecipeDetailGateway gateway = new FatSecretRecipeDetailDataAccessObject();

        // Create Interactor
        RecipeDetailInputBoundary interactor = new RecipeDetailInteractor(gateway, presenter);

        // Create Controller
        RecipeDetailController controller = new RecipeDetailController(interactor, presenter);

        String userId = getCurrentUserId();
        String oauthToken = getFatSecretToken();

        FavoriteRecipeGateway favoriteGateway = new SupabaseFavoriteRecipeDataAccessObject(
                authenticatedClient,
                oauthToken
        );

        AddFavoriteInputBoundary addFavoriteInteractor = new AddFavoriteInteractor(favoriteGateway);
        AddFavoriteController addFavoriteController = new AddFavoriteController(addFavoriteInteractor);

        // Create View
        RecipeDetailView detailView = new RecipeDetailView(
                recipeId,
                controller,
                viewModel,
                this,
                addFavoriteController,
                userId );

        // Setup back button
        detailView.getBackButton().setOnAction(e -> {
            System.out.println("Going back from recipe detail...");
            showRecipeSearch();
        });

        primaryStage.setScene(detailView.getScene());
        primaryStage.setTitle("HealthZ - Recipe Details");
    }

    /**
     * Navigate to Settings page
     */
    public void showSettings() {
        SettingsView settingsView = new SettingsView(this);

        // Switch to settings scene
        primaryStage.setScene(settingsView.getScene());
        primaryStage.setTitle("HealthZ - Settings");
    }

    /**
     * Navigate to Dashboard page
     */
    public void showDashboard() {
        String userName = getUserDisplayName();
        String userId = getCurrentUserId();

        tut0301.group1.healthz.interfaceadapter.dailysummary.GetDailySummaryViewModel summaryViewModel =
                new tut0301.group1.healthz.interfaceadapter.dailysummary.GetDailySummaryViewModel();

        tut0301.group1.healthz.interfaceadapter.dailysummary.GetDailySummaryPresenter summaryPresenter =
                new tut0301.group1.healthz.interfaceadapter.dailysummary.GetDailySummaryPresenter(summaryViewModel);

        tut0301.group1.healthz.usecase.food.logging.FoodLogGateway foodLogGateway =
                new tut0301.group1.healthz.dataaccess.supabase.SupabaseFoodLogGateway(authenticatedClient);

        tut0301.group1.healthz.usecase.dailysummary.GetDailySummaryInputBoundary summaryInteractor =
                new tut0301.group1.healthz.usecase.dailysummary.GetDailySummaryInteractor(foodLogGateway, summaryPresenter);

        tut0301.group1.healthz.interfaceadapter.dailysummary.GetDailySummaryController summaryController =
                new tut0301.group1.healthz.interfaceadapter.dailysummary.GetDailySummaryController(summaryInteractor);

        DashboardView dashboardView = new DashboardView(userName, summaryController, summaryViewModel, userId);

        setupDashboardNavigation(dashboardView);
        primaryStage.setScene(dashboardView.getScene());
        primaryStage.setTitle("HealthZ - Dashboard");

        if (userId != null) {
            Task<Void> loadTask = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    // Background thread: Fetch data from Supabase
                    summaryController.executeToday(userId);
                    return null;
                }
                @Override
                protected void failed() {
                    getException().printStackTrace();
                    System.err.println("Failed to load daily summary: " + getException().getMessage());
                }
            };
            new Thread(loadTask).start();
        }
    }

    private String getUserDisplayName() {
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
        System.out.println("✅ Login/Signup successful! Navigating to main app...");
        showDashboard();
    }

    /**
     * Navigate to Email Verification
     */
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

    /**
     * Navigate to Log In Page
     */
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
                    System.out.println("user_data row present/created.");
                } catch (Exception ex) {
                    System.err.println("Failed to init user_data row: " + ex.getMessage());
                    // optional: showError("Logged in, but could not initialize your profile data.");
                }

                // 3) Continue to main app
                showMainApp();
            } else {
                System.out.println("Login failed.");
                showError("Login failed. Please check your email and password, or verify your email.");
                // stay on the same login screen
            }
        });

        primaryStage.setScene(loginView.getScene());
        primaryStage.setTitle("HealthZ - Log In");
    }

    /**
     * Navigate to Log Out Page
     */
    public void showLogout() {
        LogoutView logoutView = new LogoutView();

        setupLogoutNavigation(logoutView);

        primaryStage.setScene(logoutView.getScene());
        primaryStage.setTitle("HealthZ - Log Out");
    }

    /**
     * Navigate to Food Log Page
     */
    public void showFoodLog() {
        // Food Search dependencies (for searching foods)
        MacroSearchViewModel macroSearchViewModel = new MacroSearchViewModel();
        SearchFoodOutputBoundary searchPresenter = new FoodSearchPresenter(macroSearchViewModel);
        FoodSearchDataAccessInterface searchGateway = new FatSecretFoodSearchDataAccessObject();
        SearchFoodInputBoundary searchInteractor = new SearchFoodInteractor(searchGateway, searchPresenter);
        MacroSearchController macroSearchController = new MacroSearchController(searchInteractor);

        macroSearchViewModel.setLoading(false);
        macroSearchViewModel.setMessage(null);
        macroSearchViewModel.setResults(java.util.List.of());

        // LogFoodIntake dependencies (for logging foods)
        tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeViewModel logFoodViewModel =
            new tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeViewModel();
        tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakePresenter logFoodPresenter =
            new tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakePresenter(logFoodViewModel);
        tut0301.group1.healthz.usecase.food.logging.FoodLogGateway foodLogGateway =
            new tut0301.group1.healthz.dataaccess.supabase.SupabaseFoodLogGateway(authenticatedClient);
        tut0301.group1.healthz.usecase.food.logging.LogFoodIntakeInputBoundary logFoodInteractor =
            new tut0301.group1.healthz.usecase.food.logging.LogFoodIntakeInteractor(foodLogGateway, logFoodPresenter);
        tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeController logFoodController =
            new tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeController(logFoodInteractor);

        // Get Food Detail dependencies (for getting full food details when logging)
        MacroDetailViewModel detailViewModel = new MacroDetailViewModel();
        FoodDetailPresenter detailPresenter = new FoodDetailPresenter(detailViewModel);
        FoodDetailGateway detailGateway = new FatSecretFoodDetailDataAccessObject();
        GetFoodDetailInputBoundary detailInteractor = new GetFoodDetailInteractor(detailGateway, detailPresenter);
        MacroDetailController macroDetailController = new MacroDetailController(detailInteractor);

        // Get current user ID
        String userId = getCurrentUserId();
        if (userId == null) {
            System.err.println("Warning: User not logged in, using default user ID");
            userId = "guest-user";
        }

        FoodLogView foodLogView = new FoodLogView(
            this,
            macroSearchController,
            macroSearchViewModel,
            logFoodController,
            logFoodViewModel,
            macroDetailController,
            detailViewModel,
            userId
        );

        primaryStage.setScene(foodLogView.getScene());
        primaryStage.setTitle("HealthZ - Food Log");
    }

    /**
     * Navigate to Activity Log Page
     */
    public void showActivityLog() {
        ActivityLogView activityLogView = new ActivityLogView(this);

        primaryStage.setScene(activityLogView.getScene());
        primaryStage.setTitle("HealthZ - Activity Log");
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
            showFoodLog();
        });

        // Activity Log button
        dashboardView.getActivityLogButton().setOnAction(e -> {
            System.out.println("Navigating to Activity Log...");
            showActivityLog();
        });

        // Log out Button
        dashboardView.getLogOutButton().setOnAction(e -> {
            System.out.println("Navigating to Log out...");
            showLogout();
        });
    }

    /**
     * Setup navigation for Log Out page
     */
    private void setupLogoutNavigation(LogoutView logoutView) {
        logoutView.getLogoutButton().setOnAction(e -> {
            System.out.println("Logging Out...");
            // TODO: implement log out
        });

        logoutView.getCancelButton().setOnAction(e -> {
            System.out.println("Returning to Dashboard...");
            showDashboard();
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

        recipeSearchView.getHealthzButton().setOnAction(e -> {
            System.out.println("Navigating to Dashboard...");
            showDashboard();
        });
    }

    /**
     * Setup navigation for Recipe Details page
     */
    private void setupRecipeDetailNavigation(RecipeDetailView recipeDetailView) {
        recipeDetailView.getBackButton().setOnAction(e -> {
            System.out.println("Going back from recipe detail...");
            showRecipeSearch();
        });

        recipeDetailView.getFavoriteButton().setOnAction(e -> {
            System.out.println("Navigating to favorite recipe page...");
            showFavoriteRecipes();
        });
    }


    /**
     * Setup navigation for Favorite Recipe page
     */
    private void setupFavoriteRecipesNavigation(FavoriteRecipeView favoriteRecipeView) {
        favoriteRecipeView.getBackButton().setOnAction(e -> {
            System.out.println("Navigating to Back button...");
            showRecipeSearch();
        });
    }


}
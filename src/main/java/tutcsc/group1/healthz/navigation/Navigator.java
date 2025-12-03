package tutcsc.group1.healthz.navigation;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tutcsc.group1.healthz.data_access.api.fat_secret.FatSecretFoodDetailDataAccessObject;
import tutcsc.group1.healthz.data_access.api.fat_secret.FatSecretFoodSearchDataAccessObject;
import tutcsc.group1.healthz.data_access.api.FatSecretRecipeDetailDataAccessObject;
import tutcsc.group1.healthz.data_access.api.FatSecretRecipeSearchDataAccessObject;
import tutcsc.group1.healthz.data_access.supabase.*;
import tutcsc.group1.healthz.data_access.supabase.food.SupabaseFoodLogGateway;
import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.interface_adapter.activity.*;
import tutcsc.group1.healthz.data_access.supabase.SupabaseAuthDataAccessObject;
import tutcsc.group1.healthz.data_access.supabase.SupabaseClient;
import tutcsc.group1.healthz.data_access.supabase.SupabaseFavoriteRecipeDataAccessObject;
import tutcsc.group1.healthz.data_access.supabase.SupabaseUserDataDataAccessObject;
import tutcsc.group1.healthz.data_access.supabase.SupabaseActivityLogDataAccessObject;
import tutcsc.group1.healthz.interface_adapter.auth.log_in.LoginController;
import tutcsc.group1.healthz.interface_adapter.auth.log_in.LoginPresenter;
import tutcsc.group1.healthz.interface_adapter.auth.log_in.LoginViewModel;
import tutcsc.group1.healthz.interface_adapter.auth.mapping.SignupProfileMapper;
import tutcsc.group1.healthz.interface_adapter.dashboard.*;
import tutcsc.group1.healthz.interface_adapter.dashboard.DashboardController;
import tutcsc.group1.healthz.interface_adapter.dashboard.DashboardPresenter;
import tutcsc.group1.healthz.interface_adapter.dashboard.DashboardViewModel;
import tutcsc.group1.healthz.interface_adapter.favorite_recipe.AddFavoriteController;
import tutcsc.group1.healthz.interface_adapter.food.FoodDetailPresenter;
import tutcsc.group1.healthz.interface_adapter.food.FoodSearchPresenter;
import tutcsc.group1.healthz.interface_adapter.food.LogFoodIntakeViewModel;
import tutcsc.group1.healthz.interface_adapter.food.LogFoodIntakePresenter;
import tutcsc.group1.healthz.interface_adapter.food.LogFoodIntakeController;
import tutcsc.group1.healthz.interface_adapter.food_log.GetFoodLogHistoryViewModel;
import tutcsc.group1.healthz.interface_adapter.food_log.GetFoodLogHistoryPresenter;
import tutcsc.group1.healthz.interface_adapter.food_log.GetFoodLogHistoryController;
import tutcsc.group1.healthz.interface_adapter.macro_summary.GetDailyMacroSummaryViewModel;
import tutcsc.group1.healthz.interface_adapter.macro_summary.GetDailyMacroSummaryPresenter;
import tutcsc.group1.healthz.interface_adapter.macro_summary.GetDailyMacroSummaryController;
import tutcsc.group1.healthz.interface_adapter.macro.MacroDetailController;
import tutcsc.group1.healthz.interface_adapter.macro.MacroDetailViewModel;
import tutcsc.group1.healthz.interface_adapter.macro.MacroSearchController;
import tutcsc.group1.healthz.interface_adapter.macro.MacroSearchViewModel;
import tutcsc.group1.healthz.interface_adapter.recipe.*;
import tutcsc.group1.healthz.interface_adapter.favorite_recipe.FavoriteRecipeController;
import tutcsc.group1.healthz.interface_adapter.favorite_recipe.FavoriteRecipePresenter;
import tutcsc.group1.healthz.interface_adapter.favorite_recipe.FavoriteRecipeViewModel;
import tutcsc.group1.healthz.interface_adapter.setting.UpdateUserController;
import tutcsc.group1.healthz.interface_adapter.setting.UpdateUserPresenter;
import tutcsc.group1.healthz.interface_adapter.setting.UpdateUserViewModel;
import tutcsc.group1.healthz.use_case.activity.activity_log.*;
import tutcsc.group1.healthz.use_case.activity.calorie_calculator.CalorieCalculatorInputBoundary;
import tutcsc.group1.healthz.use_case.activity.calorie_calculator.CalorieCalculatorInteractor;
import tutcsc.group1.healthz.use_case.activity.calorie_calculator.CalorieCalculatorOutputBoundary;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseDataAccessInterface;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseFinderInputBoundary;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseFinderInteractor;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseFinderOutputBoundary;
import tutcsc.group1.healthz.use_case.activity.recent.RecentActivityInputBoundary;
import tutcsc.group1.healthz.use_case.activity.recent.RecentActivityInteractor;
import tutcsc.group1.healthz.use_case.activity.recent.RecentActivityOutputBoundary;
import tutcsc.group1.healthz.use_case.activity.weekly_summary.WeeklySummaryInputBoundary;
import tutcsc.group1.healthz.use_case.activity.weekly_summary.WeeklySummaryInteractor;
import tutcsc.group1.healthz.use_case.activity.weekly_summary.WeeklySummaryOutputBoundary;
import tutcsc.group1.healthz.use_case.auth.AuthGateway;
import tutcsc.group1.healthz.use_case.auth.log_in.LoginInputBoundary;
import tutcsc.group1.healthz.use_case.auth.log_in.LoginInteractor;
import tutcsc.group1.healthz.use_case.dashboard.DashboardInputBoundary;
import tutcsc.group1.healthz.use_case.dashboard.DashboardInteractor;
import tutcsc.group1.healthz.use_case.dashboard.UserDataDataAccessInterface;
import tutcsc.group1.healthz.use_case.favorite_recipe.*;
import tutcsc.group1.healthz.use_case.food.detail.FoodDetailGateway;
import tutcsc.group1.healthz.use_case.food.detail.GetFoodDetailInputBoundary;
import tutcsc.group1.healthz.use_case.food.detail.GetFoodDetailInteractor;
import tutcsc.group1.healthz.use_case.food.search.FoodSearchDataAccessInterface;
import tutcsc.group1.healthz.use_case.food.search.SearchFoodInputBoundary;
import tutcsc.group1.healthz.use_case.food.search.SearchFoodInteractor;
import tutcsc.group1.healthz.use_case.food.search.SearchFoodOutputBoundary;
import tutcsc.group1.healthz.use_case.food.logging.FoodLogGateway;
import tutcsc.group1.healthz.use_case.food.logging.LogFoodIntakeInputBoundary;
import tutcsc.group1.healthz.use_case.food.logging.LogFoodIntakeInteractor;
import tutcsc.group1.healthz.use_case.food.foodloghistory.GetFoodLogHistoryInputBoundary;
import tutcsc.group1.healthz.use_case.food.foodloghistory.GetFoodLogHistoryInteractor;
import tutcsc.group1.healthz.use_case.macro_summary.GetDailyCalorieSummaryInputBoundary;
import tutcsc.group1.healthz.use_case.macro_summary.GetDailyCalorieSummaryInteractor;
//import tut0301.group1.healthz.use_case.macrosearch.MacroDetailGateway;
//import tut0301.group1.healthz.use_case.macrosearch.MacroDetailInputBoundary;
//import tut0301.group1.healthz.use_case.macrosearch.MacroDetailInteractor;
//import tut0301.group1.healthz.use_case.macrosearch.MacroSearchGateway;
//import tut0301.group1.healthz.use_case.macrosearch.MacroSearchInputBoundary;
//import tut0301.group1.healthz.use_case.macrosearch.MacroSearchInteractor;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchGateway;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchInputBoundary;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchInteractor;
import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailGateway;
import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailInputBoundary;
import tutcsc.group1.healthz.view.activity.ActivityView;
import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailInteractor;
import tutcsc.group1.healthz.use_case.setting.UpdateUserInteractor;
import tutcsc.group1.healthz.view.auth.LandingView;
import tutcsc.group1.healthz.view.auth.LoginView;
import tutcsc.group1.healthz.view.auth.SignupView;
import tutcsc.group1.healthz.view.auth.LogoutView;
import tutcsc.group1.healthz.view.auth.signuppanels.EmailVerificationView;
import tutcsc.group1.healthz.view.macro.SingleMacroPage;
import tutcsc.group1.healthz.view.macro.MacroSearchView;
import tutcsc.group1.healthz.view.recipe.RecipeDetailView;
import tutcsc.group1.healthz.view.settings.SettingsView;
import tutcsc.group1.healthz.view.dashboard.DashboardView;
import tutcsc.group1.healthz.view.recipe.RecipeSearchView;
import tutcsc.group1.healthz.view.recipe.FavoriteRecipeView;
import tutcsc.group1.healthz.view.nutrition.FoodLogView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Optional;

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
        String userId = getCurrentUserId();

        MacroDetailViewModel detailViewModel = new MacroDetailViewModel();

        FoodDetailPresenter presenter = new FoodDetailPresenter(detailViewModel);

        FoodDetailGateway gateway = new FatSecretFoodDetailDataAccessObject();

        GetFoodDetailInputBoundary interactor = new GetFoodDetailInteractor(gateway, presenter);

        MacroDetailController controller = new MacroDetailController(interactor);

        detailViewModel.setLoading(true);
        detailViewModel.setMessage(null);
        detailViewModel.setDetails(null);

        controller.fetch(foodId);

        LogFoodIntakeViewModel logViewModel = new LogFoodIntakeViewModel();
        LogFoodIntakePresenter logPresenter = new LogFoodIntakePresenter(logViewModel);
        FoodLogGateway logGateway = new SupabaseFoodLogGateway(authenticatedClient);
        LogFoodIntakeInputBoundary logInteractor = new LogFoodIntakeInteractor(logGateway, logPresenter);
        LogFoodIntakeController logController = new LogFoodIntakeController(logInteractor);

        // 6. View - observes ViewModel
        SingleMacroPage detailView = new SingleMacroPage(
                controller,
                detailViewModel,
                logController,
                logViewModel,
                userId,
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
            tutcsc.group1.healthz.data_access.api.oauth.OAuth fetcher =
                    new tutcsc.group1.healthz.data_access.api.oauth.OAuth(clientId, clientSecret);

            String jsonResponse = fetcher.getAccessTokenRaw("basic");
            return tutcsc.group1.healthz.data_access.api.oauth.OAuthDataAccessObject.extractAccessToken(jsonResponse);

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
        System.out.println("Navigator: Showing recipe detail for ID: " + recipeId);

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

        // Create View
        RecipeDetailView detailView = new RecipeDetailView(
                recipeId,
                controller,
                viewModel,
                this,
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
        if (authenticatedClient == null) {
            showError("You must be logged in to view Settings.");
            showLogin();
            return;
        }

        // 1. DAO for user_data
        SupabaseUserDataDataAccessObject userDao =
                new SupabaseUserDataDataAccessObject(authenticatedClient);

        // 2. Load or create profile
        Profile currentProfile;
        try {
            currentProfile = userDao.loadCurrentUserProfile()
                    .orElseGet(() -> {
                        try {
                            return userDao.createBlankForCurrentUserIfMissing();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not load your settings: " + e.getMessage());
            return;
        }

        // 3. Build use-case stack
        UpdateUserViewModel settingsViewModel = new UpdateUserViewModel();
        UpdateUserPresenter settingsPresenter = new UpdateUserPresenter(settingsViewModel);
        UpdateUserInteractor settingsInteractor = new UpdateUserInteractor(userDao, settingsPresenter);
        UpdateUserController settingsController = new UpdateUserController(settingsInteractor, settingsPresenter);

        // 4. Get name + email (conceptually coming from LoginOutputData)
        String displayName = getUserDisplayName();
        String email = getCurrentUserEmail();

        // 5. Build view
        SettingsView settingsView =
                new SettingsView(this, settingsController, currentProfile, displayName, email);

        primaryStage.setScene(settingsView.getScene());
        primaryStage.setTitle("HealthZ - Settings");
    }

    private String getCurrentUserEmail() {
        if (authenticatedClient != null) {
            try {
                return authenticatedClient.getUserEmail();
            } catch (Exception e) {
                System.err.println("Could not get email: " + e.getMessage());
            }
        }
        return "";
    }


    public void showActivityTracker(){

        ExerciseDataAccessInterface exerciseDAO = new SupabaseExerciseDataAccessObject(authenticatedClient);
        ActivityLogDataAccessInterface activityLogDAO = new SupabaseActivityLogDataAccessObject(authenticatedClient);

        ExerciseListViewModel exerciseListVM = new ExerciseListViewModel();
        ActivityHistoryViewModel historyVM = new ActivityHistoryViewModel();

        ExerciseFinderOutputBoundary exercisePresenter = new ExerciseFinderPresenter(exerciseListVM);
        ExerciseFinderInputBoundary exerciseFinder = new ExerciseFinderInteractor(exerciseDAO, exercisePresenter);
        CalorieCalculatorOutputBoundary caloriePresenter = new CalorieCalculatorPresenter(exerciseListVM);
        ActivityLogSaveOutputBoundary activityLogSavePresenter = new ActivityLogSavePresenter(historyVM);
        ActivityLogLoadOutputBoundary activityLogLoadPresenter = new ActivityLogLoadPresenter(historyVM,exerciseFinder);

        CalorieCalculatorInputBoundary calorieCalculator = new CalorieCalculatorInteractor(exerciseFinder, caloriePresenter);
        ActivityLogSaveInputBoundary activitySaveLog = new ActivityLogSaveInteractor(
                activityLogDAO,
                exerciseFinder,
                activityLogSavePresenter
        );
        ActivityLogLoadInputBoundary activityLogLoad = new ActivityLogLoadInteractor(
                activityLogDAO,
                activityLogLoadPresenter
        );
        ActivityPageController controller = new ActivityPageController(exerciseFinder, calorieCalculator,
                activitySaveLog, activityLogLoad);
        SupabaseUserDataDataAccessObject userDataDAO = new SupabaseUserDataDataAccessObject(authenticatedClient);
        try{
            Optional<Profile> maybeProfile = userDataDAO.loadCurrentUserProfile();
            Profile currentProfile;

            if (maybeProfile.isPresent()) {
                currentProfile = maybeProfile.get();

                ActivityView view = new ActivityView(controller, exerciseListVM, historyVM, currentProfile, this);
                primaryStage.setScene(view.getScene());
                primaryStage.setTitle("HealthZ - Activity Tracker");

            } else {
                System.out.println("No profile found for current user.");

            }

        } catch (Exception e) {
            System.err.println("Failed to load user profile: " + e.getMessage());
            showError("Could not load your profile data. Using defaults.");
        }
    }

    /**
     * Navigate to Dashboard page
     */
    public void showDashboard() throws Exception {
        String userId = getCurrentUserId();
        String userName = getUserDisplayName();
        ExerciseDataAccessInterface exerciseDAO = new SupabaseExerciseDataAccessObject(authenticatedClient);
        ExerciseListViewModel exerciseListVM = new ExerciseListViewModel();
        ExerciseFinderOutputBoundary exerciseFinderPresenter = new ExerciseFinderPresenter(exerciseListVM);
        final ExerciseFinderInputBoundary exerciseFinder = new ExerciseFinderInteractor(exerciseDAO,
                exerciseFinderPresenter);
        final ActivityLogDataAccessInterface activityLogDAO = new SupabaseActivityLogDataAccessObject(authenticatedClient);
        final WeeklySummaryViewModel activitySummaryViewModel = new WeeklySummaryViewModel();
        WeeklySummaryOutputBoundary activitySummaryPresenter = new WeeklySummaryPresenter(activitySummaryViewModel);
        WeeklySummaryInputBoundary weeklySummaryInteractor = new WeeklySummaryInteractor(activityLogDAO,
                activitySummaryPresenter);
        WeeklySummaryController weeklySummaryController= new WeeklySummaryController(weeklySummaryInteractor);
        RecentActivityViewModel recentActivityViewModel = new RecentActivityViewModel();
        RecentActivityOutputBoundary recentActivityPresenter = new RecentActivityPresenter(recentActivityViewModel,
                exerciseFinder);
        RecentActivityInputBoundary recentActivityInteractor = new RecentActivityInteractor(activityLogDAO,
                recentActivityPresenter);
        RecentActivityController recentActivityController = new RecentActivityController(recentActivityInteractor);

        if (userId == null) {
            System.err.println("Cannot show dashboard: No user logged in");
            showLogin();
            return;
        }

        System.out.println("Navigator: Showing dashboard for user " + userId);

        // Dashboard ViewModel and Presenter (old, for profile data)
        DashboardViewModel viewModel = new DashboardViewModel();
        DashboardPresenter presenter = new DashboardPresenter(viewModel);

        UserDataDataAccessInterface userDataAccess =
                new SupabaseUserDataDataAccessObject(authenticatedClient);

        // Food log gateway for calorie consumption data
        FoodLogGateway foodLogGateway = new SupabaseFoodLogGateway(authenticatedClient);

        // Activity data access
        ActivityLogDataAccessInterface activityLogDataAccess = new SupabaseActivityLogDataAccessObject(authenticatedClient);

        // DashboardInteractor with food log and activity integration
        DashboardInputBoundary interactor = new DashboardInteractor(
                userDataAccess,
                foodLogGateway,
                activityLogDataAccess,
                presenter
        );
        DashboardController controller = new DashboardController(interactor);

        // GetDailyCalorieSummary - NEW for calorie/macro display
        GetDailyMacroSummaryViewModel summaryViewModel = new GetDailyMacroSummaryViewModel();
        GetDailyMacroSummaryPresenter summaryPresenter = new GetDailyMacroSummaryPresenter(summaryViewModel);

        // Summary interactor
        GetDailyCalorieSummaryInputBoundary summaryInteractor = new GetDailyCalorieSummaryInteractor(
                foodLogGateway,
                activityLogDataAccess,
                userDataAccess,
                summaryPresenter
        );

        // Controller
        GetDailyMacroSummaryController summaryController = new GetDailyMacroSummaryController(summaryInteractor);

        // Create view with Clean Architecture components
        DashboardView dashboardView =  new DashboardView(userName,
                activitySummaryViewModel, weeklySummaryController,
                recentActivityController, recentActivityViewModel,
                controller,
                viewModel,
                summaryController,
                summaryViewModel,
                userId);


        setupDashboardNavigation(dashboardView);

        primaryStage.setScene(dashboardView.getScene());
        primaryStage.setTitle("HealthZ - Dashboard");
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
    public void showMainApp() throws Exception {
        System.out.println("Login/Signup successful! Navigating to main app...");
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
            System.out.println("User clicked: Resend verification email");
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
            System.out.println("Navigating to Sign Up...");
            showSignup();
        });

        // Continue button -> perform login, then go to main app
        loginView.getLoginButton().setOnAction(e -> {
            System.out.println("Logging in with " + loginView.getEmail());

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
                System.out.println("Login successful, ensuring profile row exists...");

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
                try {
                    showMainApp();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
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
        String userId = getCurrentUserId();

        // MacroSearch setup
        MacroSearchViewModel macroSearchViewModel = new MacroSearchViewModel();
        SearchFoodOutputBoundary searchPresenter = new FoodSearchPresenter(macroSearchViewModel);
        FoodSearchDataAccessInterface searchGateway = new FatSecretFoodSearchDataAccessObject();
        SearchFoodInputBoundary searchInteractor = new SearchFoodInteractor(searchGateway, searchPresenter);
        MacroSearchController macroSearchController = new MacroSearchController(searchInteractor);

        macroSearchViewModel.setLoading(false);
        macroSearchViewModel.setMessage(null);
        macroSearchViewModel.setResults(java.util.List.of());

        // LogFoodIntake setup
        LogFoodIntakeViewModel logFoodIntakeViewModel = new LogFoodIntakeViewModel();
        LogFoodIntakePresenter logFoodIntakePresenter = new LogFoodIntakePresenter(logFoodIntakeViewModel);
        FoodLogGateway foodLogGateway = new SupabaseFoodLogGateway(authenticatedClient);
        LogFoodIntakeInputBoundary logFoodIntakeInteractor = new LogFoodIntakeInteractor(foodLogGateway, logFoodIntakePresenter);
        LogFoodIntakeController logFoodIntakeController = new LogFoodIntakeController(logFoodIntakeInteractor);

        // MacroDetail setup
        MacroDetailViewModel macroDetailViewModel = new MacroDetailViewModel();
        FoodDetailPresenter detailPresenter = new FoodDetailPresenter(macroDetailViewModel);
        FoodDetailGateway detailGateway = new FatSecretFoodDetailDataAccessObject();
        GetFoodDetailInputBoundary detailInteractor = new GetFoodDetailInteractor(detailGateway, detailPresenter);
        MacroDetailController macroDetailController = new MacroDetailController(detailInteractor);

        // GetFoodLogHistory setup
        GetFoodLogHistoryViewModel foodLogHistoryViewModel = new GetFoodLogHistoryViewModel();
        GetFoodLogHistoryPresenter foodLogHistoryPresenter = new GetFoodLogHistoryPresenter(foodLogHistoryViewModel);
        // Reuse the same foodLogGateway instance from above
        GetFoodLogHistoryInputBoundary foodLogHistoryInteractor = new GetFoodLogHistoryInteractor(
                foodLogGateway,
                foodLogHistoryPresenter);
        GetFoodLogHistoryController foodLogHistoryController = new GetFoodLogHistoryController(foodLogHistoryInteractor);

        FoodLogView foodLogView = new FoodLogView(
                this,
                macroSearchController,
                macroSearchViewModel,
                logFoodIntakeController,
                logFoodIntakeViewModel,
                macroDetailController,
                macroDetailViewModel,
                foodLogHistoryController,
                foodLogHistoryViewModel,
                userId
        );

        primaryStage.setScene(foodLogView.getScene());
        primaryStage.setTitle("HealthZ - Food Log");
    }

    /**
     * Navigate to Activity Log Page
     */

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
            System.out.println("Navigating to Sign Up...");
            showSignup();
        });

        // Connect Log In button
        landingView.getLogInButton().setOnAction(e -> {
            System.out.println("Logging in...");
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
            System.out.println("Login succeeded. userId = " + userId);

            this.authenticatedClient = client;

            // Map signup data -> Profile
            var profile = SignupProfileMapper.toProfile(userId, signupData);

            // Save profile
            SupabaseUserDataDataAccessObject userDataGateway = new SupabaseUserDataDataAccessObject(client);
            userDataGateway.upsertProfile(profile);

            System.out.println("Profile saved successfully. Navigating to main app...");

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

            System.out.println("Resent verification email to " + signupData.getEmail());
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
            showActivityTracker();
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
            try {
                showDashboard();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
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
            try {
                showDashboard();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
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
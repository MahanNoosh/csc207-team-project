package tut0301.group1.healthz.navigation;

import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import tut0301.group1.healthz.dataaccess.API.FatSecret.FatSecretFoodDetailDataAccessObject;
import tut0301.group1.healthz.dataaccess.API.FatSecret.FatSecretFoodSearchDataAccessObject;
import tut0301.group1.healthz.dataaccess.API.FatSecretRecipeDetailDataAccessObject;
import tut0301.group1.healthz.dataaccess.API.FatSecretRecipeSearchDataAccessObject;
import tut0301.group1.healthz.dataaccess.API.OAuth.OAuth;
import tut0301.group1.healthz.dataaccess.API.OAuth.OAuthDataAccessObject;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseActivityLogDataAccessObject;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseAuthDataAccessObject;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseClient;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseExerciseDataAccessObject;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseFavoriteRecipeDataAccessObject;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseUserDataDataAccessObject;
import tut0301.group1.healthz.dataaccess.supabase.food.SupabaseFoodLogDataAccess;
import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.interfaceadapter.activity.ActivityHistoryViewModel;
import tut0301.group1.healthz.interfaceadapter.activity.ActivityLogLoadPresenter;
import tut0301.group1.healthz.interfaceadapter.activity.ActivityLogSavePresenter;
import tut0301.group1.healthz.interfaceadapter.activity.ActivityPageController;
import tut0301.group1.healthz.interfaceadapter.activity.CalorieCalculatorPresenter;
import tut0301.group1.healthz.interfaceadapter.activity.ExerciseFinderPresenter;
import tut0301.group1.healthz.interfaceadapter.activity.ExerciseListViewModel;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginController;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginPresenter;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginViewModel;
import tut0301.group1.healthz.interfaceadapter.auth.mapping.SignupProfileMapper;
import tut0301.group1.healthz.interfaceadapter.dashboard.DashboardController;
import tut0301.group1.healthz.interfaceadapter.dashboard.DashboardPresenter;
import tut0301.group1.healthz.interfaceadapter.dashboard.DashboardViewModel;
import tut0301.group1.healthz.interfaceadapter.dashboard.RecentActivityController;
import tut0301.group1.healthz.interfaceadapter.dashboard.RecentActivityPresenter;
import tut0301.group1.healthz.interfaceadapter.dashboard.RecentActivityViewModel;
import tut0301.group1.healthz.interfaceadapter.dashboard.WeeklySummaryController;
import tut0301.group1.healthz.interfaceadapter.dashboard.WeeklySummaryPresenter;
import tut0301.group1.healthz.interfaceadapter.dashboard.WeeklySummaryViewModel;
import tut0301.group1.healthz.interfaceadapter.favoriterecipe.AddFavoriteController;
import tut0301.group1.healthz.interfaceadapter.favoriterecipe.FavoriteRecipeController;
import tut0301.group1.healthz.interfaceadapter.favoriterecipe.FavoriteRecipePresenter;
import tut0301.group1.healthz.interfaceadapter.favoriterecipe.FavoriteRecipeViewModel;
import tut0301.group1.healthz.interfaceadapter.food.FoodDetailPresenter;
import tut0301.group1.healthz.interfaceadapter.food.FoodSearchPresenter;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeController;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakePresenter;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeViewModel;
import tut0301.group1.healthz.interfaceadapter.foodlog.GetFoodLogHistoryController;
import tut0301.group1.healthz.interfaceadapter.foodlog.GetFoodLogHistoryPresenter;
import tut0301.group1.healthz.interfaceadapter.foodlog.GetFoodLogHistoryViewModel;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailViewModel;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchViewModel;
import tut0301.group1.healthz.interfaceadapter.macrosummary.GetDailyMacroSummaryController;
import tut0301.group1.healthz.interfaceadapter.macrosummary.GetDailyMacroSummaryPresenter;
import tut0301.group1.healthz.interfaceadapter.macrosummary.GetDailyMacroSummaryViewModel;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeDetailController;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeDetailPresenter;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeDetailViewModel;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeSearchController;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeSearchPresenter;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeSearchViewModel;
import tut0301.group1.healthz.interfaceadapter.setting.UpdateUserController;
import tut0301.group1.healthz.interfaceadapter.setting.UpdateUserPresenter;
import tut0301.group1.healthz.interfaceadapter.setting.UpdateUserViewModel;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogDataAccessInterface;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogInputBoundary;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogInteractor;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogLoadOutputBoundary;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogSaveOutputBoundary;
import tut0301.group1.healthz.usecase.activity.caloriecalculator.CalorieCalculatorInputBoundary;
import tut0301.group1.healthz.usecase.activity.caloriecalculator.CalorieCalculatorInteractor;
import tut0301.group1.healthz.usecase.activity.caloriecalculator.CalorieCalculatorOutputBoundary;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseDataAccessInterface;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseFinderInputBoundary;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseFinderInteractor;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseFinderOutputBoundary;
import tut0301.group1.healthz.usecase.activity.recent.RecentActivityInputBoundary;
import tut0301.group1.healthz.usecase.activity.recent.RecentActivityInteractor;
import tut0301.group1.healthz.usecase.activity.recent.RecentActivityOutputBoundary;
import tut0301.group1.healthz.usecase.activity.weeklysummary.WeeklySummaryInputBoundary;
import tut0301.group1.healthz.usecase.activity.weeklysummary.WeeklySummaryInteractor;
import tut0301.group1.healthz.usecase.activity.weeklysummary.WeeklySummaryOutputBoundary;
import tut0301.group1.healthz.usecase.auth.AuthGateway;
import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInteractor;
import tut0301.group1.healthz.usecase.dashboard.DashboardInputBoundary;
import tut0301.group1.healthz.usecase.dashboard.DashboardInteractor;
import tut0301.group1.healthz.usecase.dashboard.UserDataDataAccessInterface;
import tut0301.group1.healthz.usecase.favoriterecipe.AddFavoriteInputBoundary;
import tut0301.group1.healthz.usecase.favoriterecipe.AddFavoriteInteractor;
import tut0301.group1.healthz.usecase.favoriterecipe.DeleteFavoriteInputBoundary;
import tut0301.group1.healthz.usecase.favoriterecipe.DeleteFavoriteInteractor;
import tut0301.group1.healthz.usecase.favoriterecipe.FavoriteRecipeGateway;
import tut0301.group1.healthz.usecase.favoriterecipe.LoadFavoritesInputBoundary;
import tut0301.group1.healthz.usecase.favoriterecipe.LoadFavoritesInteractor;
import tut0301.group1.healthz.usecase.food.detail.FoodDetailGateway;
import tut0301.group1.healthz.usecase.food.detail.GetFoodDetailInputBoundary;
import tut0301.group1.healthz.usecase.food.detail.GetFoodDetailInteractor;
import tut0301.group1.healthz.usecase.food.foodloghistory.GetFoodLogHistoryInputBoundary;
import tut0301.group1.healthz.usecase.food.foodloghistory.GetFoodLogHistoryInteractor;
import tut0301.group1.healthz.usecase.food.logging.FoodLogDataAccessInterface;
import tut0301.group1.healthz.usecase.food.logging.LogFoodIntakeInputBoundary;
import tut0301.group1.healthz.usecase.food.logging.LogFoodIntakeInteractor;
import tut0301.group1.healthz.usecase.food.search.FoodSearchDataAccessInterface;
import tut0301.group1.healthz.usecase.food.search.SearchFoodInputBoundary;
import tut0301.group1.healthz.usecase.food.search.SearchFoodInteractor;
import tut0301.group1.healthz.usecase.food.search.SearchFoodOutputBoundary;
import tut0301.group1.healthz.usecase.macrosummary.GetDailyMacroSummaryInputBoundary;
import tut0301.group1.healthz.usecase.macrosummary.GetDailyMacroSummaryInteractor;
import tut0301.group1.healthz.usecase.recipesearch.detailed.RecipeDetailGateway;
import tut0301.group1.healthz.usecase.recipesearch.detailed.RecipeDetailInputBoundary;
import tut0301.group1.healthz.usecase.recipesearch.detailed.RecipeDetailInteractor;
import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchGateway;
import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchInputBoundary;
import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchInteractor;
import tut0301.group1.healthz.usecase.setting.UpdateUserInteractor;
import tut0301.group1.healthz.view.activity.ActivityView;
import tut0301.group1.healthz.view.auth.LandingView;
import tut0301.group1.healthz.view.auth.LoginView;
import tut0301.group1.healthz.view.auth.LogoutView;
import tut0301.group1.healthz.view.auth.SignupView;
import tut0301.group1.healthz.view.auth.signuppanels.EmailVerificationView;
import tut0301.group1.healthz.view.dashboard.DashboardView;
import tut0301.group1.healthz.view.macro.MacroSearchView;
import tut0301.group1.healthz.view.macro.SingleMacroPage;
import tut0301.group1.healthz.view.nutrition.FoodLogView;
import tut0301.group1.healthz.view.recipe.FavoriteRecipeView;
import tut0301.group1.healthz.view.recipe.RecipeDetailView;
import tut0301.group1.healthz.view.recipe.RecipeSearchView;
import tut0301.group1.healthz.view.settings.SettingsView;

/**
 * Navigator - Handles all navigation between views.
 * Part of the Presentation layer in Clean Architecture.
 * Note: This class has high coupling due to its role as the central navigation hub.
 * CheckStyle warnings for ClassDataAbstractionCoupling and ClassFanOutComplexity are suppressed.
 */
@SuppressWarnings({"checkstyle:ClassDataAbstractionCoupling", "checkstyle:ClassFanOutComplexity"})
public final class Navigator {
    private static final String SUPABASE_URL_ENV = "SUPABASE_URL";
    private static final String SUPABASE_ANON_KEY_ENV = "SUPABASE_ANON_KEY";
    private static final int EMAIL_CHECK_MAX_ATTEMPTS = 18;
    private static final int EMAIL_CHECK_INTERVAL_SECONDS = 10;
    private static final int RESEND_COOLDOWN_SECONDS = 120;

    private static Navigator instance;

    private Stage primaryStage;
    private SignupView.SignupData pendingSignupData;
    private Timeline emailCheckTimeline;
    private SupabaseClient authenticatedClient;

    private Navigator() {
    }

    /**
     * Get the singleton instance.
     *
     * @return the Navigator instance
     */
    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }

    /**
     * Initialize the navigator with the primary stage.
     * Call this once at application startup.
     *
     * @param stage the primary JavaFX stage
     */
    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Navigate to Login/Landing page.
     */
    public void showLanding() {
        final LandingView landingView = new LandingView();
        setupLoginNavigation(landingView);
        primaryStage.setScene(landingView.getScene());
        primaryStage.setTitle("HealthZ - Welcome");
    }

    /**
     * Navigate to Signup page.
     */
    public void showSignup() {
        final SignupView signupView = new SignupView();

        signupView.getLoginLinkButton().setOnAction(event -> {
            System.out.println("Already have an account -> back to login");
            showLogin();
        });

        primaryStage.setScene(signupView.getScene());
        primaryStage.setTitle("HealthZ - Sign Up");
    }

    /**
     * Navigate to Macro Search page.
     */
    public void showMacroSearch() {
        final MacroSearchViewModel macroSearchViewModel = new MacroSearchViewModel();
        final SearchFoodOutputBoundary presenter = new FoodSearchPresenter(macroSearchViewModel);
        final FoodSearchDataAccessInterface gateway = new FatSecretFoodSearchDataAccessObject();
        final SearchFoodInputBoundary interactor = new SearchFoodInteractor(gateway, presenter);
        final MacroSearchController controller = new MacroSearchController(interactor);

        macroSearchViewModel.setLoading(false);
        macroSearchViewModel.setMessage(null);
        macroSearchViewModel.setResults(java.util.List.of());

        final MacroSearchView macroSearchView = new MacroSearchView(controller, macroSearchViewModel, this);

        primaryStage.setScene(macroSearchView.getScene());
        primaryStage.setTitle("HealthZ - Nutrition Lookup");
    }

    /**
     * Navigate to a single macro detail page for a selected food id.
     *
     * @param foodId the ID of the food to display
     */
    public void showMacroDetails(long foodId) {
        final String userId = getCurrentUserId();

        final MacroDetailViewModel detailViewModel = new MacroDetailViewModel();
        final FoodDetailPresenter presenter = new FoodDetailPresenter(detailViewModel);
        final FoodDetailGateway gateway = new FatSecretFoodDetailDataAccessObject();
        final GetFoodDetailInputBoundary interactor = new GetFoodDetailInteractor(gateway, presenter);
        final MacroDetailController controller = new MacroDetailController(interactor);

        detailViewModel.setLoading(true);
        detailViewModel.setMessage(null);
        detailViewModel.setDetails(null);

        controller.fetch(foodId);

        final LogFoodIntakeViewModel logViewModel = new LogFoodIntakeViewModel();
        final LogFoodIntakePresenter logPresenter = new LogFoodIntakePresenter(logViewModel);
        final SupabaseFoodLogDataAccess logGateway = new SupabaseFoodLogDataAccess(authenticatedClient);
        final LogFoodIntakeInputBoundary logInteractor = new LogFoodIntakeInteractor(logGateway, logPresenter);
        final LogFoodIntakeController logController = new LogFoodIntakeController(logInteractor);

        final SingleMacroPage detailView = new SingleMacroPage(
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
     * Navigate to Recipe Search page.
     */
    public void showRecipeSearch() {
        final RecipeSearchViewModel recipeSearchViewModel = new RecipeSearchViewModel();
        final RecipeSearchPresenter recipeSearchPresenter = new RecipeSearchPresenter(recipeSearchViewModel);
        final RecipeSearchGateway recipeSearchGateway = new FatSecretRecipeSearchDataAccessObject();
        final RecipeSearchInputBoundary recipeSearchInteractor = new RecipeSearchInteractor(
                recipeSearchGateway,
                recipeSearchPresenter
        );
        final RecipeSearchController recipeSearchController = new RecipeSearchController(
                recipeSearchInteractor,
                recipeSearchPresenter
        );

        final String userId = getCurrentUserId();
        final String oauthToken = getFatSecretToken();

        final FavoriteRecipeGateway favoriteGateway = new SupabaseFavoriteRecipeDataAccessObject(
                authenticatedClient,
                oauthToken
        );

        final AddFavoriteInputBoundary addFavoriteInteractor = new AddFavoriteInteractor(favoriteGateway);
        final AddFavoriteController addFavoriteController = new AddFavoriteController(addFavoriteInteractor);

        final RecipeSearchView recipeSearchView = new RecipeSearchView(
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

    /**
     * Navigate to Favorite Recipes page.
     */
    public void showFavoriteRecipes() {
        final String userName = getUserDisplayName();
        final String userId = getCurrentUserId();
        final String oauthToken = getFatSecretToken();

        final FavoriteRecipeViewModel viewModel = new FavoriteRecipeViewModel();
        final FavoriteRecipePresenter presenter = new FavoriteRecipePresenter(viewModel);

        final FavoriteRecipeGateway gateway = new SupabaseFavoriteRecipeDataAccessObject(
                authenticatedClient,
                oauthToken
        );

        final LoadFavoritesInputBoundary loadInteractor = new LoadFavoritesInteractor(gateway, presenter);
        final DeleteFavoriteInputBoundary deleteInteractor = new DeleteFavoriteInteractor(gateway, presenter);

        final FavoriteRecipeController controller = new FavoriteRecipeController(
                loadInteractor,
                deleteInteractor,
                presenter
        );

        final FavoriteRecipeView view = new FavoriteRecipeView(userName, userId, controller, viewModel, this);

        setupFavoriteRecipesNavigation(view);

        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("HealthZ - Favorite Recipes");
    }

    // -@cs[IllegalCatch] Need to catch generic exceptions from OAuth API
    private String getFatSecretToken() {
        final String result;
        try {
            final String clientId = System.getenv("FATSECRET_CLIENT_ID");
            final String clientSecret = System.getenv("FATSECRET_CLIENT_SECRET");

            if (clientId == null || clientSecret == null) {
                System.err.println("FatSecret credentials not set");
                result = null;
            }
            else {
                final OAuth fetcher = new OAuth(clientId, clientSecret);
                final String jsonResponse = fetcher.getAccessTokenRaw("basic");
                result = OAuthDataAccessObject.extractAccessToken(jsonResponse);
            }
        }
        catch (Exception ex) {
            System.err.println("Failed to get FatSecret token: " + ex.getMessage());
            return null;
        }
        return result;
    }

    // -@cs[IllegalCatch] Need to catch generic exceptions from client
    private String getCurrentUserId() {
        if (authenticatedClient != null) {
            try {
                return authenticatedClient.getUserId();
            }
            catch (Exception ex) {
                System.err.println("Could not get user ID: " + ex.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * Navigate to Recipe Detail page.
     *
     * @param recipeId the ID of the recipe to display
     */
    public void showRecipeDetail(long recipeId) {
        System.out.println("Navigator: Showing recipe detail for ID: " + recipeId);

        final RecipeDetailViewModel viewModel = new RecipeDetailViewModel();
        final RecipeDetailPresenter presenter = new RecipeDetailPresenter(viewModel);
        final RecipeDetailGateway gateway = new FatSecretRecipeDetailDataAccessObject();
        final RecipeDetailInputBoundary interactor = new RecipeDetailInteractor(gateway, presenter);
        final RecipeDetailController controller = new RecipeDetailController(interactor, presenter);

        final String userId = getCurrentUserId();
        final String oauthToken = getFatSecretToken();

        final RecipeDetailView detailView = new RecipeDetailView(
                recipeId,
                controller,
                viewModel,
                this,
                userId
        );

        detailView.getBackButton().setOnAction(event -> {
            System.out.println("Going back from recipe detail...");
            showRecipeSearch();
        });

        primaryStage.setScene(detailView.getScene());
        primaryStage.setTitle("HealthZ - Recipe Details");
    }

    /**
     * Navigate to Settings page.
     */
    // -@cs[IllegalCatch] Need to catch generic exceptions from profile loading
    public void showSettings() {
        if (authenticatedClient == null) {
            showError("You must be logged in to view Settings.");
            showLogin();
        }
        else {
            final SupabaseUserDataDataAccessObject userDao =
                    new SupabaseUserDataDataAccessObject(authenticatedClient);

            Profile currentProfile = null;
            try {
                currentProfile = userDao.loadCurrentUserProfile()
                        .orElseGet(() -> {
                            try {
                                return userDao.createBlankForCurrentUserIfMissing();
                            }
                            catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        });
            }
            catch (Exception ex) {
                ex.printStackTrace();
                showError("Could not load your settings: " + ex.getMessage());
            }

            if (currentProfile != null) {
                final UpdateUserViewModel settingsViewModel = new UpdateUserViewModel();
                final UpdateUserPresenter settingsPresenter = new UpdateUserPresenter(settingsViewModel);
                final UpdateUserInteractor settingsInteractor = new UpdateUserInteractor(
                        userDao,
                        settingsPresenter
                );
                final UpdateUserController settingsController = new UpdateUserController(
                        settingsInteractor,
                        settingsPresenter
                );

                final String displayName = getUserDisplayName();
                final String email = getCurrentUserEmail();

                final SettingsView settingsView =
                        new SettingsView(this, settingsController, currentProfile, displayName, email);

                primaryStage.setScene(settingsView.getScene());
                primaryStage.setTitle("HealthZ - Settings");
            }
        }
    }

    // -@cs[IllegalCatch] Need to catch generic exceptions from client
    private String getCurrentUserEmail() {
        if (authenticatedClient != null) {
            try {
                return authenticatedClient.getUserEmail();
            }
            catch (Exception ex) {
                System.err.println("Could not get email: " + ex.getMessage());
            }
        }
        return "";
    }

    /**
     * Navigate to Activity Tracker page.
     */
    // -@cs[IllegalCatch] Need to catch generic exceptions from profile loading
    @SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:ExecutableStatementCount"})
    public void showActivityTracker() {
        final ExerciseDataAccessInterface exerciseDao = new SupabaseExerciseDataAccessObject(authenticatedClient);
        final ActivityLogDataAccessInterface activityLogDao = new SupabaseActivityLogDataAccessObject(
                authenticatedClient
        );

        final ExerciseListViewModel exerciseListVm = new ExerciseListViewModel();
        final ActivityHistoryViewModel historyVm = new ActivityHistoryViewModel();

        final ExerciseFinderOutputBoundary exercisePresenter = new ExerciseFinderPresenter(exerciseListVm);
        final ExerciseFinderInputBoundary exerciseFinder = new ExerciseFinderInteractor(
                exerciseDao,
                exercisePresenter
        );
        final CalorieCalculatorOutputBoundary caloriePresenter = new CalorieCalculatorPresenter(exerciseListVm);
        final ActivityLogSaveOutputBoundary activityLogSavePresenter = new ActivityLogSavePresenter(historyVm);
        final ActivityLogLoadOutputBoundary activityLogLoadPresenter = new ActivityLogLoadPresenter(
                historyVm,
                exerciseFinder
        );

        final String displayName = getUserDisplayName();
        final String email = getCurrentUserEmail();

        final CalorieCalculatorInputBoundary calorieCalculator = new CalorieCalculatorInteractor(
                exerciseFinder,
                caloriePresenter
        );
        final ActivityLogInputBoundary activityLog = new ActivityLogInteractor(
                activityLogDao,
                exerciseFinder,
                activityLogSavePresenter,
                activityLogLoadPresenter
        );
        final ActivityPageController controller = new ActivityPageController(
                exerciseFinder,
                calorieCalculator,
                activityLog
        );
        final SupabaseUserDataDataAccessObject userDataDao = new SupabaseUserDataDataAccessObject(
                authenticatedClient
        );
        try {
            final Optional<Profile> maybeProfile = userDataDao.loadCurrentUserProfile();
            Profile currentProfile = null;

            if (maybeProfile.isPresent()) {
                currentProfile = maybeProfile.get();

                final ActivityView view = new ActivityView(
                        controller,
                        exerciseListVm,
                        historyVm,
                        currentProfile,
                        this,
                        displayName,
                        email
                );
                primaryStage.setScene(view.getScene());
                primaryStage.setTitle("HealthZ - Activity Tracker");
            }
            else {
                System.out.println("No profile found for current user.");
            }
        }
        catch (Exception ex) {
            System.err.println("Failed to load user profile: " + ex.getMessage());
            showError("Could not load your profile data. Using defaults.");
        }
    }

    /**
     * Navigate to Dashboard page.
     */
    @SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:ExecutableStatementCount"})
    public void showDashboard() {
        final String userId = getCurrentUserId();
        final String userName = getUserDisplayName();
        final ExerciseDataAccessInterface exerciseDao = new SupabaseExerciseDataAccessObject(authenticatedClient);
        final ExerciseListViewModel exerciseListVm = new ExerciseListViewModel();
        final ExerciseFinderOutputBoundary exerciseFinderPresenter = new ExerciseFinderPresenter(exerciseListVm);
        final ExerciseFinderInputBoundary exerciseFinder = new ExerciseFinderInteractor(
                exerciseDao,
                exerciseFinderPresenter
        );
        final ActivityLogDataAccessInterface activityLogDao = new SupabaseActivityLogDataAccessObject(
                authenticatedClient
        );
        final WeeklySummaryViewModel activitySummaryViewModel = new WeeklySummaryViewModel();
        final WeeklySummaryOutputBoundary activitySummaryPresenter = new WeeklySummaryPresenter(
                activitySummaryViewModel
        );
        final WeeklySummaryInputBoundary weeklySummaryInteractor = new WeeklySummaryInteractor(
                activityLogDao,
                activitySummaryPresenter
        );
        final WeeklySummaryController weeklySummaryController = new WeeklySummaryController(weeklySummaryInteractor);
        final RecentActivityViewModel recentActivityViewModel = new RecentActivityViewModel();
        final RecentActivityOutputBoundary recentActivityPresenter = new RecentActivityPresenter(
                recentActivityViewModel,
                exerciseFinder
        );
        final RecentActivityInputBoundary recentActivityInteractor = new RecentActivityInteractor(
                activityLogDao,
                recentActivityPresenter
        );
        final RecentActivityController recentActivityController = new RecentActivityController(
                recentActivityInteractor
        );

        if (userId == null) {
            System.err.println("Cannot show dashboard: No user logged in");
            showLogin();
        }
        else {
            System.out.println("Navigator: Showing dashboard for user " + userId);

            final DashboardViewModel viewModel = new DashboardViewModel();
            final DashboardPresenter presenter = new DashboardPresenter(viewModel);

            final UserDataDataAccessInterface userDataAccess =
                    new SupabaseUserDataDataAccessObject(authenticatedClient);

            final  FoodLogDataAccessInterface foodLogGateway = new SupabaseFoodLogDataAccess(authenticatedClient);

            final ActivityLogDataAccessInterface activityLogDataAccess =
                    new SupabaseActivityLogDataAccessObject(authenticatedClient);

            final DashboardInputBoundary interactor = new DashboardInteractor(
                    userDataAccess,
                    foodLogGateway,
                    activityLogDataAccess,
                    presenter
            );
            final DashboardController controller = new DashboardController(interactor);

            final GetDailyMacroSummaryViewModel summaryViewModel = new GetDailyMacroSummaryViewModel();
            final GetDailyMacroSummaryPresenter summaryPresenter = new GetDailyMacroSummaryPresenter(summaryViewModel);

            GetDailyMacroSummaryInputBoundary summaryInteractor = new GetDailyMacroSummaryInteractor(
                    foodLogGateway,
                    summaryPresenter
            );

            final GetDailyMacroSummaryController summaryController = new GetDailyMacroSummaryController(
                    summaryInteractor
            );

            final DashboardView dashboardView = new DashboardView(
                    userName,
                    activitySummaryViewModel,
                    weeklySummaryController,
                    recentActivityController,
                    recentActivityViewModel,
                    controller,
                    viewModel,
                    summaryController,
                    summaryViewModel,
                    userId
            );

            setupDashboardNavigation(dashboardView);

            primaryStage.setScene(dashboardView.getScene());
            primaryStage.setTitle("HealthZ - Dashboard");
        }
    }

    // -@cs[IllegalCatch] Need to catch generic exceptions from client
    private String getUserDisplayName() {
        String result = "User";
        if (authenticatedClient != null) {
            final String displayName = authenticatedClient.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                result = displayName;
            }
            else {
                try {
                    final String email = authenticatedClient.getUserEmail();
                    if (email != null && email.contains("@")) {
                        final String firstName = email.split("@")[0].split("\\.")[0];
                        result = firstName.substring(0, 1).toUpperCase()
                                + firstName.substring(1).toLowerCase();
                    }
                }
                catch (Exception ex) {
                    System.err.println("Could not get email: " + ex.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * Navigate to Main App/Dashboard (after successful login/signup).
     */
    public void showMainApp() {
        System.out.println("Login/Signup successful! Navigating to main app...");
        showDashboard();
    }

    /**
     * Navigate to Email Verification.
     *
     * @param signupData the signup data for verification
     */
    public void showEmailVerification(SignupView.SignupData signupData) {
        this.pendingSignupData = signupData;

        final EmailVerificationView view = new EmailVerificationView(signupData);
        view.setStatusText("Waiting for verification… We'll detect it automatically.");

        view.getBackToLoginButton().setOnAction(event -> {
            System.out.println("Back to login from email verification");
            if (emailCheckTimeline != null) {
                emailCheckTimeline.stop();
                emailCheckTimeline = null;
            }
            showLogin();
        });

        view.getResendButton().setOnAction(event -> {
            System.out.println("User clicked: Resend verification email");
            resendVerificationEmail(signupData, view);
            startEmailCheckTimeline();
            view.startResendCooldown(RESEND_COOLDOWN_SECONDS);
        });

        startEmailCheckTimeline();

        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("HealthZ - Verify your email");
    }

    private void showError(String msg) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Navigate to Log In Page.
     */
    // -@cs[IllegalCatch] Need to catch generic exceptions from authentication
    public void showLogin() {
        final LoginView loginView = new LoginView();

        loginView.getSignUpButton().setOnAction(event -> {
            System.out.println("Navigating to Sign Up...");
            showSignup();
        });

        loginView.getLoginButton().setOnAction(event -> {
            this.performLogin(loginView);
        });

        primaryStage.setScene(loginView.getScene());
        primaryStage.setTitle("HealthZ - Log In");
    }

    // -@cs[IllegalCatch] Need to catch generic exceptions from authentication
    @SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:LambdaBodyLength"})
    private void performLogin(LoginView loginView) {
        System.out.println("Logging in with " + loginView.getEmail());

        final String url = System.getenv(SUPABASE_URL_ENV);
        final String anon = System.getenv(SUPABASE_ANON_KEY_ENV);
        if (url == null || anon == null) {
            System.err.println("Set SUPABASE_URL and SUPABASE_ANON_KEY");
            showError("Supabase not configured. Please set SUPABASE_URL and SUPABASE_ANON_KEY.");
        }
        else {
            try {
                final SupabaseClient client = new SupabaseClient(url, anon);
                final AuthGateway authGateway = new SupabaseAuthDataAccessObject(client);
                final LoginViewModel loginViewModel = new LoginViewModel();
                final LoginPresenter loginPresenter = new LoginPresenter(loginViewModel);
                final LoginInputBoundary loginUseCase = new LoginInteractor(authGateway, loginPresenter);
                final LoginController loginController = new LoginController(loginUseCase, loginPresenter);

                // Try login (can throw Exception)
                loginController.login(loginView.getEmail(), loginView.getPassword());

                if (loginViewModel.isLoggedIn()) {
                    System.out.println("Login successful, ensuring profile row exists...");

                    authenticatedClient = client;

                    // Make sure user_data row exists (can throw Exception)
                    final SupabaseUserDataDataAccessObject userDataGateway =
                            new SupabaseUserDataDataAccessObject(client);
                    userDataGateway.createBlankForCurrentUserIfMissing();
                    System.out.println("user_data row present/created.");

                    // Continue to main app
                    showMainApp();
                }
                else {
                    System.out.println("Login failed.");
                    showError("Login failed. Please check your email and password, or verify your email.");
                }
            }
            catch (Exception ex) {
                System.err.println("Login error: " + ex.getMessage());
                showError("Login error: " + ex.getMessage());
            }
        }
    }

    /**
     * Navigate to Log Out Page.
     */
    public void showLogout() {
        final LogoutView logoutView = new LogoutView();

        setupLogoutNavigation(logoutView);

        primaryStage.setScene(logoutView.getScene());
        primaryStage.setTitle("HealthZ - Log Out");
    }

    /**
     * Navigate to Food Log Page.
     */
    @SuppressWarnings("checkstyle:ExecutableStatementCount")
    public void showFoodLog() {
        final String userId = getCurrentUserId();

        final MacroSearchViewModel macroSearchViewModel = new MacroSearchViewModel();
        final SearchFoodOutputBoundary searchPresenter = new FoodSearchPresenter(macroSearchViewModel);
        final FoodSearchDataAccessInterface searchGateway = new FatSecretFoodSearchDataAccessObject();
        final SearchFoodInputBoundary searchInteractor = new SearchFoodInteractor(searchGateway, searchPresenter);
        final MacroSearchController macroSearchController = new MacroSearchController(searchInteractor);

        macroSearchViewModel.setLoading(false);
        macroSearchViewModel.setMessage(null);
        macroSearchViewModel.setResults(java.util.List.of());

        final LogFoodIntakeViewModel logFoodIntakeViewModel = new LogFoodIntakeViewModel();
        final LogFoodIntakePresenter logFoodIntakePresenter = new LogFoodIntakePresenter(logFoodIntakeViewModel);
        final FoodLogDataAccessInterface foodLogGateway = new SupabaseFoodLogDataAccess(authenticatedClient);
        final LogFoodIntakeInputBoundary logFoodIntakeInteractor = new LogFoodIntakeInteractor(
                foodLogGateway,
                logFoodIntakePresenter
        );
        final LogFoodIntakeController logFoodIntakeController = new LogFoodIntakeController(logFoodIntakeInteractor);

        final MacroDetailViewModel macroDetailViewModel = new MacroDetailViewModel();
        final FoodDetailPresenter detailPresenter = new FoodDetailPresenter(macroDetailViewModel);
        final FoodDetailGateway detailGateway = new FatSecretFoodDetailDataAccessObject();
        final GetFoodDetailInputBoundary detailInteractor = new GetFoodDetailInteractor(
                detailGateway,
                detailPresenter
        );
        final MacroDetailController macroDetailController = new MacroDetailController(detailInteractor);

        final GetFoodLogHistoryViewModel foodLogHistoryViewModel = new GetFoodLogHistoryViewModel();
        final GetFoodLogHistoryPresenter foodLogHistoryPresenter = new GetFoodLogHistoryPresenter(
                foodLogHistoryViewModel
        );
        final GetFoodLogHistoryInputBoundary foodLogHistoryInteractor = new GetFoodLogHistoryInteractor(
                foodLogGateway,
                foodLogHistoryPresenter
        );
        final GetFoodLogHistoryController foodLogHistoryController = new GetFoodLogHistoryController(
                foodLogHistoryInteractor
        );

        final String displayName = getUserDisplayName();
        final String email = getCurrentUserEmail();

        final FoodLogView foodLogView = new FoodLogView(
                this,
                macroSearchController,
                macroSearchViewModel,
                logFoodIntakeController,
                logFoodIntakeViewModel,
                macroDetailController,
                macroDetailViewModel,
                foodLogHistoryController,
                foodLogHistoryViewModel,
                userId,
                displayName,
                email
        );

        primaryStage.setScene(foodLogView.getScene());
        primaryStage.setTitle("HealthZ - Food Log");
    }

    /**
     * Setup navigation for Login page.
     * Connects Sign Up and Log In buttons to navigation.
     *
     * @param landingView the landing view to setup
     */
    private void setupLoginNavigation(LandingView landingView) {
        landingView.getSignUpButton().setOnAction(event -> {
            System.out.println("Navigating to Sign Up...");
            showSignup();
        });

        landingView.getLogInButton().setOnAction(event -> {
            System.out.println("Logging in...");
            showLogin();
        });
    }

    // -@cs[IllegalCatch] Need to catch generic exceptions from authentication
    @SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:CyclomaticComplexity",
            "checkstyle:NPathComplexity", "checkstyle:ExecutableStatementCount"})
    private void tryLoginAndSaveProfileOnce(final boolean silent) {
        if (pendingSignupData == null) {
            if (!silent) {
                showError("No signup in progress. Please sign up again.");
            }
        }
        else {
            final SignupView.SignupData signupData = pendingSignupData;

            final String url = System.getenv("SUPABASE_URL");
            final String anon = System.getenv("SUPABASE_ANON_KEY");
            if (url == null || anon == null) {
                if (!silent) {
                    showError("Supabase not configured. Please set SUPABASE_URL and SUPABASE_ANON_KEY.");
                }
                return;
            }

            try {
                final SupabaseClient client = new SupabaseClient(url, anon);
                final AuthGateway authGateway = new SupabaseAuthDataAccessObject(client);
                final LoginViewModel loginViewModel = new LoginViewModel();
                final LoginPresenter loginPresenter = new LoginPresenter(loginViewModel);
                final LoginInputBoundary loginUseCase = new LoginInteractor(authGateway, loginPresenter);
                final LoginController loginController = new LoginController(loginUseCase, loginPresenter);

                System.out.println("Attempting login for " + signupData.getEmail());
                loginController.login(signupData.getEmail(), signupData.getPassword());

                if (!loginViewModel.isLoggedIn()) {
                    System.out.println("Still not logged in (email probably not verified yet).");
                    if (!silent) {
                        showError(
                                "We couldn't log you in yet.\n"
                                        + "Please make sure you clicked the verification link in your email,\n"
                                        + "then try again."
                        );
                    }
                    return;
                }

                final String userId = loginViewModel.getUserId();
                System.out.println("Login succeeded. userId = " + userId);

                authenticatedClient = client;

                // Map signup data -> Profile
                final Profile profile = SignupProfileMapper.toProfile(userId, signupData);

                // Save profile (also can throw Exception)
                final SupabaseUserDataDataAccessObject userDataGateway =
                        new SupabaseUserDataDataAccessObject(client);
                userDataGateway.upsertProfile(profile);

                System.out.println("Profile saved successfully. Navigating to main app...");

                if (emailCheckTimeline != null) {
                    emailCheckTimeline.stop();
                    emailCheckTimeline = null;
                }

                showMainApp();
            }
            catch (Exception ex) {
                System.err.println("Login / profile save failed: " + ex.getMessage());
                if (!silent) {
                    showError("Failed to save your profile: " + ex.getMessage());
                }
            }
        }
    }


    private void resendVerificationEmail(SignupView.SignupData signupData, EmailVerificationView view) {
        final String url = System.getenv(SUPABASE_URL_ENV);
        final String anon = System.getenv(SUPABASE_ANON_KEY_ENV);
        if (url == null || anon == null) {
            System.err.println("Supabase not configured; cannot resend verification email.");
            view.setStatusText("Could not resend email (server not configured).");
        }
        else {
            try {
                final SupabaseClient client = new SupabaseClient(url, anon);
                client.resendSignupVerification(signupData.getEmail());

                System.out.println("Resent verification email to " + signupData.getEmail());
                view.setStatusText("Verification email resent to "
                        + signupData.getEmail()
                        + ". Waiting for verification…");
            }
            catch (Exception ex) {
                System.err.println("Failed to resend verification email: " + ex.getMessage());
                view.setStatusText("Failed to resend email. Please check your inbox or try again later.");
            }
        }
    }

    private void startEmailCheckTimeline() {
        if (emailCheckTimeline != null) {
            emailCheckTimeline.stop();
        }

        emailCheckTimeline = new Timeline();
        emailCheckTimeline.setCycleCount(EMAIL_CHECK_MAX_ATTEMPTS);

        emailCheckTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(EMAIL_CHECK_INTERVAL_SECONDS), event -> {
                    System.out.println("Auto-checking email verification...");
                    tryLoginAndSaveProfileOnce(true);
                })
        );

        emailCheckTimeline.play();
    }

    /**
     * Setup navigation for Dashboard page.
     *
     * @param dashboardView the dashboard view to setup
     */
    private void setupDashboardNavigation(DashboardView dashboardView) {
        dashboardView.getSettingsButton().setOnAction(event -> {
            System.out.println("Navigating to Settings...");
            showSettings();
        });

        dashboardView.getRecipesButton().setOnAction(event -> {
            System.out.println("Navigating to Recipes...");
            showRecipeSearch();
        });

        dashboardView.getMacrosButton().setOnAction(event -> {
            System.out.println("Navigating to Macro Search...");
            showMacroSearch();
        });

        dashboardView.getFoodLogButton().setOnAction(event -> {
            System.out.println("Navigating to Food Log...");
            showFoodLog();
        });

        dashboardView.getActivityLogButton().setOnAction(event -> {
            System.out.println("Navigating to Activity Log...");
            showActivityTracker();
        });

        dashboardView.getLogOutButton().setOnAction(event -> {
            System.out.println("Navigating to Log out...");
            showLogout();
        });
    }

    /**
     * Setup navigation for Log Out page.
     *
     * @param logoutView the logout view to setup
     */
    private void setupLogoutNavigation(LogoutView logoutView) {
        logoutView.getLogoutButton().setOnAction(event -> {
            System.out.println("Logging Out...");
        });

        logoutView.getCancelButton().setOnAction(event -> {
            System.out.println("Returning to Dashboard...");
            showDashboard();
        });
    }

    /**
     * Setup navigation for Recipe Search page.
     *
     * @param recipeSearchView the recipe search view to setup
     */
    private void setupRecipeNavigation(RecipeSearchView recipeSearchView) {
        recipeSearchView.getFavoriteRecipesButton().setOnAction(event -> {
            System.out.println("Navigating to favorite recipes page...");
            showFavoriteRecipes();
        });

        recipeSearchView.getHealthzButton().setOnAction(event -> {
            System.out.println("Navigating to Dashboard...");
            showDashboard();
        });
    }

    /**
     * Setup navigation for Recipe Details page.
     *
     * @param recipeDetailView the recipe detail view to setup
     */
    private void setupRecipeDetailNavigation(RecipeDetailView recipeDetailView) {
        recipeDetailView.getBackButton().setOnAction(event -> {
            System.out.println("Going back from recipe detail...");
            showRecipeSearch();
        });
    }

    /**
     * Setup navigation for Favorite Recipe page.
     *
     * @param favoriteRecipeView the favorite recipe view to setup
     */
    private void setupFavoriteRecipesNavigation(FavoriteRecipeView favoriteRecipeView) {
        favoriteRecipeView.getBackButton().setOnAction(event -> {
            System.out.println("Navigating to Back button...");
            showRecipeSearch();
        });
    }
}

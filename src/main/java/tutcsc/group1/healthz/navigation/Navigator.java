package tutcsc.group1.healthz.navigation;

import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import tutcsc.group1.healthz.data_access.api.fat_secret.FatSecretFoodDetailDataAccessObject;
import tutcsc.group1.healthz.data_access.api.fat_secret.FatSecretFoodSearchDataAccessObject;
import tutcsc.group1.healthz.data_access.api.FatSecretRecipeDetailDataAccessObject;
import tutcsc.group1.healthz.data_access.api.FatSecretRecipeSearchDataAccessObject;
import tutcsc.group1.healthz.data_access.api.oauth.OAuth;
import tutcsc.group1.healthz.data_access.api.oauth.OAuthDataAccessObject;
import tutcsc.group1.healthz.data_access.supabase.SupabaseActivityLogDataAccessObject;
import tutcsc.group1.healthz.data_access.supabase.SupabaseAuthDataAccessObject;
import tutcsc.group1.healthz.data_access.supabase.SupabaseClient;
import tutcsc.group1.healthz.data_access.supabase.SupabaseExerciseDataAccessObject;
import tutcsc.group1.healthz.data_access.supabase.SupabaseFavoriteRecipeDataAccessObject;
import tutcsc.group1.healthz.data_access.supabase.SupabaseUserDataDataAccessObject;
import tutcsc.group1.healthz.data_access.supabase.food.SupabaseFoodLogGateway;
import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.interface_adapter.activity.ActivityHistoryViewModel;
import tutcsc.group1.healthz.interface_adapter.activity.ActivityLogLoadPresenter;
import tutcsc.group1.healthz.interface_adapter.activity.ActivityLogSavePresenter;
import tutcsc.group1.healthz.interface_adapter.activity.ActivityPageController;
import tutcsc.group1.healthz.interface_adapter.activity.CalorieCalculatorPresenter;
import tutcsc.group1.healthz.interface_adapter.activity.ExerciseFinderPresenter;
import tutcsc.group1.healthz.interface_adapter.activity.ExerciseListViewModel;
import tutcsc.group1.healthz.interface_adapter.auth.log_in.LoginController;
import tutcsc.group1.healthz.interface_adapter.auth.log_in.LoginPresenter;
import tutcsc.group1.healthz.interface_adapter.auth.log_in.LoginViewModel;
import tutcsc.group1.healthz.interface_adapter.auth.mapping.SignupProfileMapper;
import tutcsc.group1.healthz.interface_adapter.dashboard.DashboardController;
import tutcsc.group1.healthz.interface_adapter.dashboard.DashboardPresenter;
import tutcsc.group1.healthz.interface_adapter.dashboard.DashboardViewModel;
import tutcsc.group1.healthz.interface_adapter.dashboard.RecentActivityController;
import tutcsc.group1.healthz.interface_adapter.dashboard.RecentActivityPresenter;
import tutcsc.group1.healthz.interface_adapter.dashboard.RecentActivityViewModel;
import tutcsc.group1.healthz.interface_adapter.dashboard.WeeklySummaryController;
import tutcsc.group1.healthz.interface_adapter.dashboard.WeeklySummaryPresenter;
import tutcsc.group1.healthz.interface_adapter.dashboard.WeeklySummaryViewModel;
import tutcsc.group1.healthz.interface_adapter.favorite_recipe.AddFavoriteController;
import tutcsc.group1.healthz.interface_adapter.favorite_recipe.FavoriteRecipeController;
import tutcsc.group1.healthz.interface_adapter.favorite_recipe.FavoriteRecipePresenter;
import tutcsc.group1.healthz.interface_adapter.favorite_recipe.FavoriteRecipeViewModel;
import tutcsc.group1.healthz.interface_adapter.food.FoodDetailPresenter;
import tutcsc.group1.healthz.interface_adapter.food.FoodSearchPresenter;
import tutcsc.group1.healthz.interface_adapter.food.LogFoodIntakeController;
import tutcsc.group1.healthz.interface_adapter.food.LogFoodIntakePresenter;
import tutcsc.group1.healthz.interface_adapter.food.LogFoodIntakeViewModel;
import tutcsc.group1.healthz.interface_adapter.food_log.GetFoodLogHistoryController;
import tutcsc.group1.healthz.interface_adapter.food_log.GetFoodLogHistoryPresenter;
import tutcsc.group1.healthz.interface_adapter.food_log.GetFoodLogHistoryViewModel;
import tutcsc.group1.healthz.interface_adapter.macro.MacroDetailController;
import tutcsc.group1.healthz.interface_adapter.macro.MacroDetailViewModel;
import tutcsc.group1.healthz.interface_adapter.macro.MacroSearchController;
import tutcsc.group1.healthz.interface_adapter.macro.MacroSearchViewModel;
import tutcsc.group1.healthz.interface_adapter.macro_summary.GetDailyMacroSummaryController;
import tutcsc.group1.healthz.interface_adapter.macro_summary.GetDailyMacroSummaryPresenter;
import tutcsc.group1.healthz.interface_adapter.macro_summary.GetDailyMacroSummaryViewModel;
import tutcsc.group1.healthz.interface_adapter.recipe.RecipeDetailController;
import tutcsc.group1.healthz.interface_adapter.recipe.RecipeDetailPresenter;
import tutcsc.group1.healthz.interface_adapter.recipe.RecipeDetailViewModel;
import tutcsc.group1.healthz.interface_adapter.recipe.RecipeSearchController;
import tutcsc.group1.healthz.interface_adapter.recipe.RecipeSearchPresenter;
import tutcsc.group1.healthz.interface_adapter.recipe.RecipeSearchViewModel;
import tutcsc.group1.healthz.interface_adapter.setting.UpdateUserController;
import tutcsc.group1.healthz.interface_adapter.setting.UpdateUserPresenter;
import tutcsc.group1.healthz.interface_adapter.setting.UpdateUserViewModel;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogDataAccessInterface;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogSaveInputBoundary;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogLoadInputBoundary;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogSaveInteractor;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogLoadInteractor;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogLoadOutputBoundary;
import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogSaveOutputBoundary;
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
import tutcsc.group1.healthz.use_case.favorite_recipe.AddFavoriteInputBoundary;
import tutcsc.group1.healthz.use_case.favorite_recipe.AddFavoriteInteractor;
import tutcsc.group1.healthz.use_case.favorite_recipe.DeleteFavoriteInputBoundary;
import tutcsc.group1.healthz.use_case.favorite_recipe.DeleteFavoriteInteractor;
import tutcsc.group1.healthz.use_case.favorite_recipe.FavoriteRecipeGateway;
import tutcsc.group1.healthz.use_case.favorite_recipe.LoadFavoritesInputBoundary;
import tutcsc.group1.healthz.use_case.favorite_recipe.LoadFavoritesInteractor;
import tutcsc.group1.healthz.use_case.food.detail.FoodDetailGateway;
import tutcsc.group1.healthz.use_case.food.detail.GetFoodDetailInputBoundary;
import tutcsc.group1.healthz.use_case.food.detail.GetFoodDetailInteractor;
import tutcsc.group1.healthz.use_case.food.foodloghistory.GetFoodLogHistoryInputBoundary;
import tutcsc.group1.healthz.use_case.food.foodloghistory.GetFoodLogHistoryInteractor;
import tutcsc.group1.healthz.use_case.food.logging.FoodLogGateway;
import tutcsc.group1.healthz.use_case.food.logging.LogFoodIntakeInputBoundary;
import tutcsc.group1.healthz.use_case.food.logging.LogFoodIntakeInteractor;
import tutcsc.group1.healthz.use_case.food.search.FoodSearchDataAccessInterface;
import tutcsc.group1.healthz.use_case.food.search.SearchFoodInputBoundary;
import tutcsc.group1.healthz.use_case.food.search.SearchFoodInteractor;
import tutcsc.group1.healthz.use_case.food.search.SearchFoodOutputBoundary;
import tutcsc.group1.healthz.use_case.macro_summary.GetDailyCalorieSummaryInputBoundary;
import tutcsc.group1.healthz.use_case.macro_summary.GetDailyCalorieSummaryInteractor;
import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailGateway;
import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailInputBoundary;
import tutcsc.group1.healthz.use_case.recipe_search.detailed.RecipeDetailInteractor;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchGateway;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchInputBoundary;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchInteractor;
import tutcsc.group1.healthz.use_case.setting.UpdateUserInteractor;
import tutcsc.group1.healthz.view.activity.ActivityView;
import tutcsc.group1.healthz.view.auth.LandingView;
import tutcsc.group1.healthz.view.auth.LoginView;
import tutcsc.group1.healthz.view.auth.LogoutView;
import tutcsc.group1.healthz.view.auth.SignupView;
import tutcsc.group1.healthz.view.auth.signuppanels.EmailVerificationView;
import tutcsc.group1.healthz.view.dashboard.DashboardView;
import tutcsc.group1.healthz.view.macro.MacroSearchView;
import tutcsc.group1.healthz.view.macro.SingleMacroPage;
import tutcsc.group1.healthz.view.nutrition.FoodLogView;
import tutcsc.group1.healthz.view.recipe.FavoriteRecipeView;
import tutcsc.group1.healthz.view.recipe.RecipeDetailView;
import tutcsc.group1.healthz.view.recipe.RecipeSearchView;
import tutcsc.group1.healthz.view.settings.SettingsView;

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
        final FoodLogGateway logGateway = new SupabaseFoodLogGateway(authenticatedClient);
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
        final ActivityLogDataAccessInterface activityLogDataAccessObject = new SupabaseActivityLogDataAccessObject(
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
        final ActivityLogSaveInputBoundary activitySaveLog = new ActivityLogSaveInteractor(
                activityLogDataAccessObject,
                exerciseFinder,
                activityLogSavePresenter
        );
        final ActivityLogLoadInputBoundary activityLogLoad = new ActivityLogLoadInteractor(
                activityLogDataAccessObject,
                activityLogLoadPresenter
        );
        final ActivityPageController controller = new ActivityPageController(exerciseFinder, calorieCalculator,
                activitySaveLog, activityLogLoad);
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
    public void showDashboard() throws Exception {
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

            final FoodLogGateway foodLogGateway = new SupabaseFoodLogGateway(authenticatedClient);

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

            final GetDailyCalorieSummaryInputBoundary summaryInteractor = new GetDailyCalorieSummaryInteractor(
                    foodLogGateway,
                    activityLogDataAccess,
                    userDataAccess,
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
    public void showMainApp() throws Exception {
        System.out.println("Login/Signup successful! Navigating to main app...");
        showDashboard();
    }

    /**
     * Navigate to Email Verification.
     *
     * @param signupData the signup data for verification
     */

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

            // start the visible resend cooldown for ~2 minutes
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
        final LoginView loginView = new LoginView();

        // Sign up link -> go to signup
        loginView.getSignUpButton().setOnAction(e -> {
            System.out.println("Navigating to Sign Up...");
            showSignup();
        });

        // Continue button -> perform login, then go to main app
        loginView.getLoginButton().setOnAction(e -> {
            System.out.println("Logging in with " + loginView.getEmail());

            final String url = System.getenv("SUPABASE_URL");
            final String anon = System.getenv("SUPABASE_ANON_KEY");
            if (url == null || anon == null) {
                System.err.println("Set SUPABASE_URL and SUPABASE_ANON_KEY");
                showError("Supabase not configured. Please set SUPABASE_URL and SUPABASE_ANON_KEY.");
                return;
            }

            try {
                final tutcsc.group1.healthz.data_access.supabase.SupabaseClient client = new tutcsc.group1.healthz.data_access.supabase.SupabaseClient(url, anon);
                final AuthGateway authGateway = new SupabaseAuthDataAccessObject(client);
                final LoginViewModel loginViewModel = new LoginViewModel();
                final LoginPresenter loginPresenter = new LoginPresenter(loginViewModel);
                final LoginInputBoundary loginUseCase = new LoginInteractor(authGateway, loginPresenter);
                final LoginController loginController = new LoginController(loginUseCase, loginPresenter);

                // 1) Try login (can throw Exception)
                loginController.login(loginView.getEmail(), loginView.getPassword());

                if (loginViewModel.isLoggedIn()) {
                    System.out.println("Login successful, ensuring profile row exists...");

                    authenticatedClient = client;

                    // 2) Make sure user_data row exists (can throw Exception)
                    final tutcsc.group1.healthz.data_access.supabase.SupabaseUserDataDataAccessObject userDataGateway =
                            new tutcsc.group1.healthz.data_access.supabase.SupabaseUserDataDataAccessObject(client);
                    userDataGateway.createBlankForCurrentUserIfMissing();
                    System.out.println("user_data row present/created.");

                    // 3) Continue to main app
                    showMainApp();
                } else {
                    System.out.println("Login failed.");
                    showError("Login failed. Please check your email and password, or verify your email.");
                }
            } catch (Exception ex) {
                System.err.println("Login error: " + ex.getMessage());
                showError("Login error: " + ex.getMessage());
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

        // user info needed for sidebar
        String displayName = getUserDisplayName();
        String email = getCurrentUserEmail();

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
                userId,
                displayName,
                email
        );

        primaryStage.setScene(foodLogView.getScene());
        primaryStage.setTitle("HealthZ - Food Log");
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
            showLogin();
        });
    }

    private void tryLoginAndSaveProfileOnce(final boolean silent) {
        if (pendingSignupData == null) {
            if (!silent) {
                showError("No signup in progress. Please sign up again.");
            }
            return;
        }

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
            final tutcsc.group1.healthz.data_access.supabase.SupabaseClient client = new tutcsc.group1.healthz.data_access.supabase.SupabaseClient(url, anon);
            final AuthGateway authGateway = new SupabaseAuthDataAccessObject(client);
            final LoginViewModel loginViewModel = new LoginViewModel();
            final LoginPresenter loginPresenter = new LoginPresenter(loginViewModel);
            final LoginInputBoundary loginUseCase = new LoginInteractor(authGateway, loginPresenter);
            final LoginController loginController = new LoginController(loginUseCase, loginPresenter);

            System.out.println("Attempting login for " + signupData.getEmail());
            // <-- this can throw Exception, we now catch it
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
            final Profile profile = tutcsc.group1.healthz.interface_adapter.auth.mapping.SignupProfileMapper.toProfile(userId, signupData);

            // Save profile (also can throw Exception)
            final tutcsc.group1.healthz.data_access.supabase.SupabaseUserDataDataAccessObject userDataGateway =
                    new tutcsc.group1.healthz.data_access.supabase.SupabaseUserDataDataAccessObject(client);
            userDataGateway.upsertProfile(profile);

            System.out.println("Profile saved successfully. Navigating to main app...");

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

    /**
     * Resend verification email to the user.
     *
     * @param signupData the signup data containing the user's email
     * @param view the email verification view to update with status messages
     */
    // -@cs[IllegalCatch] Need to catch generic exceptions from client
    private void resendVerificationEmail(SignupView.SignupData signupData, EmailVerificationView view) {
        String url  = System.getenv("SUPABASE_URL");
        String anon = System.getenv("SUPABASE_ANON_KEY");
        if (url == null || anon == null) {
            System.err.println("Supabase not configured; cannot resend verification email.");
            view.setStatusText("Could not resend email (server not configured).");
            return;
        }

        try {
            tutcsc.group1.healthz.data_access.supabase.SupabaseClient client = new tutcsc.group1.healthz.data_access.supabase.SupabaseClient(url, anon);
            client.resendSignupVerification(signupData.getEmail());

            System.out.println("Resent verification email to " + signupData.getEmail());
            view.setStatusText("Verification email resent to " + signupData.getEmail() + ". Waiting for verification…");
        } catch (Exception ex) {
            System.err.println("Failed to resend verification email: " + ex.getMessage());
            view.setStatusText("Failed to resend email. Please check your inbox or try again later.");
        }
    }

    /**
     * Start a timeline that automatically checks for email verification every 10 seconds.
     */
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
     * Setup navigation for Log Out page.
     *
     * @param logoutView the logout view to setup
     */
    private void setupLogoutNavigation(LogoutView logoutView) {
        logoutView.getLogoutButton().setOnAction(event -> {
            System.out.println("Logging Out...");
            // TODO: implement log out
        });

        logoutView.getCancelButton().setOnAction(event -> {
            System.out.println("Returning to Dashboard...");
            try {
                showDashboard();
            }
            catch (Exception ex) {
                System.err.println("Failed to show dashboard: " + ex.getMessage());
                showError("Could not navigate to dashboard.");
            }
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
            try {
                showFavoriteRecipes();
            }
            catch (Exception ex) {
                System.err.println("Failed to show favorite recipes: " + ex.getMessage());
                showError("Could not navigate to favorite recipes.");
            }
        });

        recipeSearchView.getHealthzButton().setOnAction(event -> {
            System.out.println("Navigating to Dashboard...");
            try {
                showDashboard();
            }
            catch (Exception ex) {
                System.err.println("Failed to show dashboard: " + ex.getMessage());
                showError("Could not navigate to dashboard.");
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
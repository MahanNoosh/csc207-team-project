package tut0301.group1.healthz.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

/**
 * Reusable Sidebar Component
 * Can be shared across multiple views (Dashboard, Settings, etc.)
 */
public class SidebarComponent {

    // Profile info
    private String userName = "Bob Dylan";
    private String userEmail = "bob.dylan@gmail.com";
    private String activeTab = "Settings";  // which tab is currently active

    // Navigation buttons (exposed for external navigation logic)
    private Button dashboardBtn;
    private Button mealTrackerBtn;
    private Button activityTrackerBtn;
    private Button settingsBtn;
    private Button notificationsBtn;
    private Button logoutBtn;

    // Callbacks for navigation (set externally)
    private Runnable onDashboardClick;
    private Runnable onMealTrackerClick;
    private Runnable onActivityTrackerClick;
    private Runnable onSettingsClick;
    private Runnable onNotificationsClick;
    private Runnable onLogoutClick;

    /**
     * Create sidebar with default settings
     */
    public VBox createSidebar() {
        VBox sidebar = new VBox(0);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(220);

        // Profile section at top
        VBox profileSection = createProfileSection();

        // Navigation menu
        VBox navigation = createNavigation();

        // Log out button at bottom
        logoutBtn = new Button("Log Out");
        logoutBtn.getStyleClass().addAll("nav-item", "logout-button");
        logoutBtn.setMaxWidth(Double.MAX_VALUE);
        logoutBtn.setOnAction(e -> handleLogout());

        // Spacer to push logout to bottom
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(profileSection, navigation, spacer, logoutBtn);
        return sidebar;
    }

    /**
     * Profile section at the top of the left sidebar
     */
    private VBox createProfileSection() {
        VBox profileBox = new VBox(8);
        profileBox.getStyleClass().add("profile-section");
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setPadding(new Insets(30, 20, 20, 20));

        // Profile picture (circle with initials)
        Circle profilePic = new Circle(30);
        profilePic.setFill(Color.web("#CCCCCC"));
        profilePic.getStyleClass().add("profile-picture");

        // Name
        Label nameLabel = new Label(userName);
        nameLabel.getStyleClass().add("profile-name");

        // Email
        Label emailLabel = new Label(userEmail);
        emailLabel.getStyleClass().add("profile-email");

        profileBox.getChildren().addAll(profilePic, nameLabel, emailLabel);
        return profileBox;
    }

    /**
     * Navigation menu
     */
    private VBox createNavigation() {
        VBox navBox = new VBox(0);
        navBox.getStyleClass().add("navigation");

        dashboardBtn = createNavButton("Dashboard", "📊", "Dashboard".equals(activeTab));
        mealTrackerBtn = createNavButton("Meal Tracker", "🍴", "Meal Tracker".equals(activeTab));
        activityTrackerBtn = createNavButton("Activity Tracker", "🏃", "Activity Tracker".equals(activeTab));
        settingsBtn = createNavButton("Settings", "⚙", "Settings".equals(activeTab));
        notificationsBtn = createNavButton("Notifications", "🔔", "Notifications".equals(activeTab));

        // Set up click handlers
        dashboardBtn.setOnAction(e -> {
            if (onDashboardClick != null) onDashboardClick.run();
        });

        mealTrackerBtn.setOnAction(e -> {
            if (onMealTrackerClick != null) onMealTrackerClick.run();
        });

        activityTrackerBtn.setOnAction(e -> {
            if (onActivityTrackerClick != null) onActivityTrackerClick.run();
        });

        settingsBtn.setOnAction(e -> {
            if (onSettingsClick != null) onSettingsClick.run();
        });

        notificationsBtn.setOnAction(e -> {
            if (onNotificationsClick != null) onNotificationsClick.run();
        });

        navBox.getChildren().addAll(
                dashboardBtn,
                mealTrackerBtn,
                activityTrackerBtn,
                settingsBtn,
                notificationsBtn
        );

        return navBox;
    }

    /**
     * Navigation button
     */
    private Button createNavButton(String text, String icon, boolean isActive) {
        Button button = new Button(icon + "  " + text);
        button.getStyleClass().add("nav-item");
        if (isActive) {
            button.getStyleClass().add("nav-item-active");
        }
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        return button;
    }

    /**
     * Handle logout
     */
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("You will need to log in again to access your account.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (onLogoutClick != null) {
                    onLogoutClick.run();
                } else {
                    System.out.println("Logging out...");
                }
            }
        });
    }

    // ========== SETTERS FOR CONFIGURATION ==========

    /**
     * Set user profile information
     */
    public SidebarComponent setUserProfile(String name, String email) {
        this.userName = name;
        this.userEmail = email;
        return this;  // For method chaining
    }

    /**
     * Set which tab is currently active
     */
    public SidebarComponent setActiveTab(String tabName) {
        this.activeTab = tabName;
        return this;
    }

    // ========== SETTERS FOR NAVIGATION CALLBACKS ==========

    public SidebarComponent setOnDashboardClick(Runnable handler) {
        this.onDashboardClick = handler;
        return this;
    }

    public SidebarComponent setOnMealTrackerClick(Runnable handler) {
        this.onMealTrackerClick = handler;
        return this;
    }

    public SidebarComponent setOnActivityTrackerClick(Runnable handler) {
        this.onActivityTrackerClick = handler;
        return this;
    }

    public SidebarComponent setOnSettingsClick(Runnable handler) {
        this.onSettingsClick = handler;
        return this;
    }

    public SidebarComponent setOnNotificationsClick(Runnable handler) {
        this.onNotificationsClick = handler;
        return this;
    }

    public SidebarComponent setOnLogoutClick(Runnable handler) {
        this.onLogoutClick = handler;
        return this;
    }

    // ========== GETTERS FOR BUTTONS (Alternative Approach) ==========

    public Button getDashboardButton() {
        return dashboardBtn;
    }

    public Button getMealTrackerButton() {
        return mealTrackerBtn;
    }

    public Button getActivityTrackerButton() {
        return activityTrackerBtn;
    }

    public Button getSettingsButton() {
        return settingsBtn;
    }

    public Button getNotificationsButton() {
        return notificationsBtn;
    }

    public Button getLogoutButton() {
        return logoutBtn;
    }
}
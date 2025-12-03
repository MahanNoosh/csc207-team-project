package tutcsc.group1.healthz.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tutcsc.group1.healthz.navigation.Navigator;

import java.util.HashMap;
import java.util.Map;

/**
 * Reusable sidebar component for navigation across the app
 */
public class Sidebar extends VBox {

    private final Navigator navigator;
    private final String currentPage;

    private final Map<String, Button> navButtons = new HashMap<>();
    private Button logoutButton;

    private Label nameLabel;
    private Label emailLabel;
    private Circle profilePic;

    /**
     * Constructor
     * @param navigator The Navigator instance for navigation
     * @param currentPage The current page name (e.g., "dashboard")
     */
    public Sidebar(Navigator navigator, String currentPage) {
        this.navigator = navigator;
        this.currentPage = currentPage;

        setupSidebar();
    }

    /**
     * Constructor with user info
     * @param navigator The Navigator instance
     * @param currentPage The current page name
     * @param userName User's full name
     * @param userEmail User's email
     */
    public Sidebar(Navigator navigator, String currentPage, String userName, String userEmail) {
        this.navigator = navigator;
        this.currentPage = currentPage;

        setupSidebar();
        setUserInfo(userName, userEmail);
    }

    private void setupSidebar() {
        this.setSpacing(0);
        this.setPrefWidth(240);
        this.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #E5E7EB; " +
                        "-fx-border-width: 0 1 0 0;"
        );

        // Profile section
        VBox profileSection = createProfileSection();

        // Navigation menu
        VBox navigation = createNavigation();

        // Logout button at bottom
        logoutButton = new Button("Log Out");
        logoutButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #DC2626; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 15px 20px; " +
                        "-fx-alignment: center-left; " +
                        "-fx-cursor: hand;"
        );
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setOnAction(e -> handleLogout());

        // Hover effect for logout
        logoutButton.setOnMouseEntered(e ->
                logoutButton.setStyle(
                        "-fx-background-color: #FEE2E2; " +
                                "-fx-text-fill: #DC2626; " +
                                "-fx-font-size: 15px; " +
                                "-fx-font-weight: 600; " +
                                "-fx-padding: 15px 20px; " +
                                "-fx-alignment: center-left; " +
                                "-fx-cursor: hand;"
                )
        );

        logoutButton.setOnMouseExited(e ->
                logoutButton.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-text-fill: #DC2626; " +
                                "-fx-font-size: 15px; " +
                                "-fx-font-weight: 600; " +
                                "-fx-padding: 15px 20px; " +
                                "-fx-alignment: center-left; " +
                                "-fx-cursor: hand;"
                )
        );

        // Spacer to push logout to bottom
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(profileSection, navigation, spacer, logoutButton);
    }

    private VBox createProfileSection() {
        VBox profileBox = new VBox(8);
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setPadding(new Insets(30, 20, 20, 20));
        profileBox.setStyle("-fx-border-color: #E5E7EB; -fx-border-width: 0 0 1 0;");

        // Profile picture (circle)
        profilePic = new Circle(35);
        profilePic.setFill(Color.web("#D1D5DB"));
        profilePic.setStroke(Color.web("#9CA3AF"));
        profilePic.setStrokeWidth(2);

        // Name
        nameLabel = new Label("User Name");
        nameLabel.setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #111827;"
        );

        // Email
        emailLabel = new Label("user@email.com");
        emailLabel.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-text-fill: #6B7280;"
        );
        emailLabel.setMaxWidth(200);
        emailLabel.setWrapText(false);

        profileBox.getChildren().addAll(profilePic, nameLabel, emailLabel);
        return profileBox;
    }

    private VBox createNavigation() {
        VBox navBox = new VBox(0);
        navBox.setPadding(new Insets(10, 0, 10, 0));

        // Create navigation buttons
        Button dashboardBtn = createNavButton("dashboard", "📊", "dashboard");
        Button activityBtn = createNavButton("Activity Log", "🔍", "Activity Log");
        Button foodBtn = createNavButton("Food Log", "♥️", "Food Log");
        Button settingsBtn = createNavButton("Settings", "⚙️", "Settings");

        // Store buttons for external access
        navButtons.put("dashboard", dashboardBtn);
        navButtons.put("Activity Log", activityBtn);
        navButtons.put("Food Log", foodBtn);
        navButtons.put("Settings", settingsBtn);

        navBox.getChildren().addAll(
                dashboardBtn,
                activityBtn,
                foodBtn,
                settingsBtn
        );

        return navBox;
    }

    private Button createNavButton(String text, String icon, String pageName) {
        Button button = new Button(icon + "  " + text);
        boolean isActive = pageName.equals(currentPage);

        // Base style
        String baseStyle =
                "-fx-font-size: 15px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 15px 20px; " +
                        "-fx-alignment: center-left; " +
                        "-fx-cursor: hand; ";

        // Active style
        String activeStyle = baseStyle +
                "-fx-background-color: #F0F7F3; " +
                "-fx-text-fill: #27692A; " +
                "-fx-border-color: #27692A; " +
                "-fx-border-width: 0 0 0 4;";

        // Inactive style
        String inactiveStyle = baseStyle +
                "-fx-background-color: transparent; " +
                "-fx-text-fill: #374151; " +
                "-fx-border-width: 0;";

        button.setStyle(isActive ? activeStyle : inactiveStyle);
        button.setMaxWidth(Double.MAX_VALUE);

        // Hover effects (only for inactive buttons)
        if (!isActive) {
            button.setOnMouseEntered(e ->
                    button.setStyle(baseStyle +
                            "-fx-background-color: #F9FAFB; " +
                            "-fx-text-fill: #111827; " +
                            "-fx-border-width: 0;"
                    )
            );

            button.setOnMouseExited(e ->
                    button.setStyle(inactiveStyle)
            );
        }

        // Navigation action
        button.setOnAction(e -> navigate(pageName));

        return button;
    }

    private void navigate(String pageName) {
        if (pageName.equals(currentPage)) {
            return;
        }

        switch (pageName) {
            case "dashboard":
                navigator.showMainApp();
                break;
            case "Activity Log":
                navigator.showActivityTracker();
                break;
            case "Food Log":
                navigator.showFoodLog();
                break;
            case "Settings":
                navigator.showSettings();
                break;
        }
    }

    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("You will need to log in again to access your account.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                navigator.showLanding();
            }
        });
    }

    // PUBLIC METHODS

    /**
     * Set user information in the profile section
     */
    public void setUserInfo(String name, String email) {
        nameLabel.setText(name);
        emailLabel.setText(email);
    }

    /**
     * Get a specific navigation button
     */
    public Button getNavButton(String pageName) {
        return navButtons.get(pageName);
    }

    /**
     * Get the logout button
     */
    public Button getLogoutButton() {
        return logoutButton;
    }
}
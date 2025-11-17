package tut0301.group1.healthz.view.nutrition;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.view.macro.SingleMacroPage.FoodItem;
import tut0301.group1.healthz.view.settings.SettingsView.*;

/**
 * Food Log page that allows user to log Breakfast, Lunch, Dinner, Snacks, and Water.
 */
public class FoodLogView {
    Scene scene;

    // input fields
    private String mealType;
    private TextField foodName;
    private TextField servingSizeField;
    private TextField servingsCountField;

    /**
     * Constructor
     */
    public FoodLogView() {
        BorderPane root = createMainLayout();
        scene = new Scene(root, 1200, 800);
    }

    /**
     * Create main layout
     */
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FBF5;");
        root.setPadding(new Insets(40, 60, 40, 60));

        // left sidebar
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        // main content area




        // wrap content in ScrollPane
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #F8FBF5;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        root.setCenter(scrollPane);

        return root;
    }

    /**
     * Navigation sidebar
     */
    private VBox createSidebar() {
        VBox sidebar = new VBox(0);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(220);

        // Profile section at top
        VBox profileSection = createProfileSection();

        // Navigation menu
        VBox navigation = createNavigation();

        // Log out button at bottom
        Button logoutButton = new Button("Log Out");
        logoutButton.getStyleClass().addAll("nav-item", "logout-button");
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setOnAction(e -> handleLogout());

        // Spacer to push logout to bottom
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(profileSection, navigation, spacer, logoutButton);
        return sidebar;
    }

    /**
     * Profile Section
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
        Label nameLabel = new Label("Bob Dylan");
        nameLabel.getStyleClass().add("profile-name");

        // Email
        Label emailLabel = new Label("bob.dylan@gmail.com");
        emailLabel.getStyleClass().add("profile-email");

        profileBox.getChildren().addAll(profilePic, nameLabel, emailLabel);
        return profileBox;
    }

}
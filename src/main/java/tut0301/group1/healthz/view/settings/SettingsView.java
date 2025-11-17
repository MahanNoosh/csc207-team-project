package tut0301.group1.healthz.view.settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.view.components.SidebarComponent;

/**
 * Settings View includes:
 * - Sidebar navigation
 * - Profile header
 * - Personal details section
 * - Biometrics section
 * - Goals section
 * - Delete account button
 */

public class SettingsView {
    private Scene scene;

    // Personal details fields
    private TextField fullNameField;
    private TextField emailField;
    private TextField phoneField;
    private TextField billingAddressField;

    // Biometrics fields
    private TextField ageField;
    private TextField heightField;
    private TextField weightField;
    private ComboBox<String> genderCombo;
    private ComboBox<String> activityLevelCombo;
    private TextField dietField;

    // Goals fields
    private TextField targetWeightField;
    private TextField dailyCalorieField;
    private ComboBox<String> goalCombo;
    private TextField dailyActivityField;

    // for navigation logic
    private SidebarComponent sidebar;

    public SettingsView() {
        BorderPane root = createMainLayout();
        scene = new Scene(root, 1200, 800);

        // Load CSS
        scene.getStylesheets()
                .add(getClass().getResource("/styles/settings.css").toExternalForm());
    }

    // main layout = left sidebar + content area
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.getStylesheets().add("root");

        // left sidebar
        sidebar = new SidebarComponent();
        sidebar.setActiveTab("Settings")
                .setUserProfile("Bob Dylan", "bob.dylan@gmail.com");

        VBox sidebarVBox = sidebar.createSidebar();
        root.setLeft(sidebarVBox);

        // main content area
        ScrollPane contentScroll = new ScrollPane(createContentArea());
        contentScroll.setFitToWidth(true);
        contentScroll.getStylesheets().add("content-scroll");
        root.setCenter(contentScroll);

        return root;
    }


    // content area = header + 3 sections + delete account button
    private VBox createContentArea() {
        VBox content = new VBox(30);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(40, 60, 40, 60));

        // Header
        Label headerTitle = new Label("View or Update your personal details, biometrics and dietary goals.");
        headerTitle.getStyleClass().add("content-header");
        headerTitle.setWrapText(true);

        // Three main sections
        VBox personalDetailsSection = createPersonalDetailsSection();
        VBox biometricsSection = createBiometricsSection();
        VBox goalsSection = createGoalsSection();

        // Delete Account button
        Button deleteAccountBtn = new Button("Delete Account");
        deleteAccountBtn.getStyleClass().add("delete-account-button");
        deleteAccountBtn.setOnAction(e -> handleDeleteAccount());

        HBox deleteBox = new HBox();
        deleteBox.setAlignment(Pos.CENTER);
        deleteBox.setPadding(new Insets(20, 0, 0, 0));
        deleteBox.getChildren().add(deleteAccountBtn);

        content.getChildren().addAll(
                headerTitle,
                personalDetailsSection,
                biometricsSection,
                goalsSection,
                deleteBox
        );

        return content;
    }

    // personal details section
    private VBox createPersonalDetailsSection() {
        VBox section = new VBox(20);
        section.getStyleClass().add("form-section");

        Label sectionTitle = new Label("Personal Details");
        sectionTitle.getStyleClass().add("section-title");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("form-grid");
        grid.setHgap(20);
        grid.setVgap(15);

        // Row 1: Full Name and Email
        Label fullNameLabel = new Label("Full Name");
        fullNameLabel.getStyleClass().add("field-label");
        fullNameField = new TextField("Bob Dylan");
        fullNameField.getStyleClass().add("form-field");

        Label emailLabel = new Label("Email");
        emailLabel.getStyleClass().add("field-label");
        emailField = new TextField("bob.dylan@gmail.com");
        emailField.getStyleClass().addAll("form-field", "disabled-field");
        emailField.setDisable(true);

        // Row 2: Phone Number and Billing Address
        Label phoneLabel = new Label("Phone Number");
        phoneLabel.getStyleClass().add("field-label");
        phoneField = new TextField("+1 477 466 3344");
        phoneField.getStyleClass().add("form-field");

        Label billingLabel = new Label("Billing Address");
        billingLabel.getStyleClass().add("field-label");
        billingAddressField = new TextField("st. Fake Address");
        billingAddressField.getStyleClass().add("form-field");

        // Add to grid
        grid.add(fullNameLabel, 0, 0);
        grid.add(fullNameField, 0, 1);
        grid.add(emailLabel, 1, 0);
        grid.add(emailField, 1, 1);
        grid.add(phoneLabel, 0, 2);
        grid.add(phoneField, 0, 3);
        grid.add(billingLabel, 1, 2);
        grid.add(billingAddressField, 1, 3);

        // Make columns equal width
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        section.getChildren().addAll(sectionTitle, grid);

        return section;
    }

    // Biometrics section
    private VBox createBiometricsSection() {
        VBox section = new VBox(20);
        section.getStyleClass().add("form-section");

        Label sectionTitle = new Label("Biometrics");
        sectionTitle.getStyleClass().add("section-title");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("form-grid");
        grid.setHgap(20);
        grid.setVgap(15);

        // Row 1: Age and Height
        Label ageLabel = new Label("Age");
        ageLabel.getStyleClass().add("field-label");
        ageField = new TextField("24");
        ageField.getStyleClass().add("form-field");

        Label heightLabel = new Label("Height (cm)");
        heightLabel.getStyleClass().add("field-label");
        heightField = new TextField("175");
        heightField.getStyleClass().add("form-field");

        // Row 2: Weight and Gender
        Label weightLabel = new Label("Weight (kg)");
        weightLabel.getStyleClass().add("field-label");
        weightField = new TextField("82");
        weightField.getStyleClass().add("form-field");

        Label genderLabel = new Label("Gender");
        genderLabel.getStyleClass().add("field-label");
        genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Male", "Female", "Other", "Prefer not to say");
        genderCombo.setValue("Male");
        genderCombo.getStyleClass().add("form-combo");
        genderCombo.setMaxWidth(Double.MAX_VALUE);

        // Row 3: Activity Level and Diet
        Label activityLabel = new Label("Activity Level");
        activityLabel.getStyleClass().add("field-label");
        activityLevelCombo = new ComboBox<>();
        activityLevelCombo.getItems().addAll(
                "Sedentary",
                "Lightly Active",
                "Moderately Active",
                "Very Active",
                "Extremely Active"
        );
        activityLevelCombo.setValue("Moderately Active");
        activityLevelCombo.getStyleClass().add("form-combo");
        activityLevelCombo.setMaxWidth(Double.MAX_VALUE);

        Label dietLabel = new Label("Diet");
        dietLabel.getStyleClass().add("field-label");
        dietField = new TextField("Vegan");
        dietField.getStyleClass().add("form-field");

        // Add to grid
        grid.add(ageLabel, 0, 0);
        grid.add(ageField, 0, 1);
        grid.add(heightLabel, 1, 0);
        grid.add(heightField, 1, 1);
        grid.add(weightLabel, 0, 2);
        grid.add(weightField, 0, 3);
        grid.add(genderLabel, 1, 2);
        grid.add(genderCombo, 1, 3);
        grid.add(activityLabel, 0, 4);
        grid.add(activityLevelCombo, 0, 5);
        grid.add(dietLabel, 1, 4);
        grid.add(dietField, 1, 5);

        // Make columns equal width
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        section.getChildren().addAll(sectionTitle, grid);
        return section;
    }

    // goals section
    private VBox createGoalsSection() {
        VBox section = new VBox(20);
        section.getStyleClass().add("form-section");

        Label sectionTitle = new Label("Goals");
        sectionTitle.getStyleClass().add("section-title");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("form-grid");
        grid.setHgap(20);
        grid.setVgap(15);

        // Row 1: Target Weight and Daily Calorie Target
        Label targetWeightLabel = new Label("Target Weight");
        targetWeightLabel.getStyleClass().add("field-label");

        HBox targetWeightBox = new HBox(10);
        targetWeightField = new TextField("82");
        targetWeightField.getStyleClass().add("form-field");
        Label kgLabel = new Label("kg");
        kgLabel.getStyleClass().add("unit-label");
        targetWeightBox.getChildren().addAll(targetWeightField, kgLabel);
        HBox.setHgrow(targetWeightField, Priority.ALWAYS);

        Label dailyCalorieLabel = new Label("Daily Calorie Target");
        dailyCalorieLabel.getStyleClass().add("field-label");
        dailyCalorieField = new TextField("2200");
        dailyCalorieField.getStyleClass().add("form-field");

        // Row 2: Goal and Daily Activity Target
        Label goalLabel = new Label("Goal");
        goalLabel.getStyleClass().add("field-label");
        goalCombo = new ComboBox<>();
        goalCombo.getItems().addAll(
                "Lose Weight",
                "Maintain Weight",
                "Gain Weight",
                "Build Muscle"
        );
        goalCombo.setValue("Maintain Weight");
        goalCombo.getStyleClass().add("form-combo");
        goalCombo.setMaxWidth(Double.MAX_VALUE);

        Label dailyActivityLabel = new Label("Daily Activity Target");
        dailyActivityLabel.getStyleClass().add("field-label");
        dailyActivityField = new TextField("2 hours");
        dailyActivityField.getStyleClass().add("form-field");

        // Add to grid
        grid.add(targetWeightLabel, 0, 0);
        grid.add(targetWeightBox, 0, 1);
        grid.add(dailyCalorieLabel, 1, 0);
        grid.add(dailyCalorieField, 1, 1);
        grid.add(goalLabel, 0, 2);
        grid.add(goalCombo, 0, 3);
        grid.add(dailyActivityLabel, 1, 2);
        grid.add(dailyActivityField, 1, 3);

        // Make columns equal width
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        section.getChildren().addAll(sectionTitle, grid);
        return section;
    }

    // EVENT HANDLERS
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("You will need to log in again to access your account.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // TODO: Navigate to login screen
                System.out.println("Logging out...");
            }
        });
    }

    private void handleDeleteAccount() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete Account");
        alert.setHeaderText("This action cannot be undone!");
        alert.setContentText("Are you absolutely sure you want to delete your account? All your data will be permanently deleted.");

        ButtonType deleteButton = new ButtonType("Delete Account", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(deleteButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == deleteButton) {
                // TODO: Call delete account use case
                System.out.println("Deleting account...");
            }
        });
    }

    /**
     * Returns the scene for this view
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Public method to get form values (for saving)
     */
    public void saveSettings() {
        // TODO: Collect all values and call UpdateUserSettingsUseCase
        String fullName = fullNameField.getText();
        String phone = phoneField.getText();
        String billingAddress = billingAddressField.getText();

        int age = Integer.parseInt(ageField.getText());
        int height = Integer.parseInt(heightField.getText());
        int weight = Integer.parseInt(weightField.getText());
        String gender = genderCombo.getValue();
        String activityLevel = activityLevelCombo.getValue();
        String diet = dietField.getText();

        int targetWeight = Integer.parseInt(targetWeightField.getText());
        int dailyCalorie = Integer.parseInt(dailyCalorieField.getText());
        String goal = goalCombo.getValue();
        String dailyActivity = dailyActivityField.getText();

        System.out.println("Saving settings...");
        // updateSettingsUseCase.execute(new UpdateSettingsRequest(...));
    }

    /**
     * Get the sidebar component (for navigation logic)
     */
    public SidebarComponent getSidebar() {
        return sidebar;
    }
}

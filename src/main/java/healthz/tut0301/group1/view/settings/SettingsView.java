package healthz.tut0301.group1.view.settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import healthz.tut0301.group1.entities.Dashboard.Profile;
import healthz.tut0301.group1.entities.Dashboard.Sex;
import healthz.tut0301.group1.entities.Dashboard.Goal;
import healthz.tut0301.group1.entities.Dashboard.HealthCondition;
import healthz.tut0301.group1.entities.Dashboard.DietPreference;

import healthz.tut0301.group1.interfaceadapter.setting.UpdateUserController;
import healthz.tut0301.group1.navigation.Navigator;
import healthz.tut0301.group1.view.components.Sidebar;

import java.util.Optional;

/**
 * Settings View includes:
 * - Sidebar navigation
 * - Personal details section
 * - Biometrics section
 * - Goals section
 * - Delete account button
 */
public class SettingsView {

    private final Navigator navigator;
    private final UpdateUserController updateUserController;
    private final Profile currentProfile;

    // User identity (from login)
    private final String displayName;
    private final String email;

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
    private ComboBox<String> dietCombo;

    // Goals fields
    private TextField targetWeightField;
    private TextField dailyCalorieField;
    private ComboBox<String> goalCombo;
    private TextField dailyActivityField;

    // save settings button
    private Button saveButton;

    public SettingsView(Navigator navigator,
                        UpdateUserController updateUserController,
                        Profile currentProfile,
                        String displayName,
                        String email) {
        this.navigator = navigator;
        this.updateUserController = updateUserController;
        this.currentProfile = currentProfile;
        this.displayName = (displayName != null && !displayName.isEmpty()) ? displayName : "User";
        this.email = email != null ? email : "";

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

        // left sidebar with real user info
        Sidebar sidebar = new Sidebar(navigator, "Settings", displayName, email);
        root.setLeft(sidebar);

        // main content area
        ScrollPane contentScroll = new ScrollPane(createContentArea());
        contentScroll.setFitToWidth(true);
        contentScroll.getStylesheets().add("content-scroll");
        root.setCenter(contentScroll);

        return root;
    }

    // content area = header + 3 sections + delete account button + save
    private VBox createContentArea() {
        VBox content = new VBox(30);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(40, 60, 40, 60));

        // Header
        Label headerTitle = new Label("View or Update your personal details, biometrics and dietary goals.");
        headerTitle.getStyleClass().add("content-header");
        headerTitle.setWrapText(true);

        // Sections
        VBox personalDetailsSection = createPersonalDetailsSection();
        VBox biometricsSection = createBiometricsSection();
        VBox goalsSection = createGoalsSection();

        // Delete Account button
        Button deleteAccountBtn = new Button("Delete Account");
        deleteAccountBtn.getStyleClass().add("delete-account-button");
        deleteAccountBtn.setOnAction(e -> handleDeleteAccount());

        // Save Settings button
        saveButton = new Button("Save");
        saveButton.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        saveButton.setTextFill(Color.web("white"));
        saveButton.setStyle("-fx-background-color: #27692A;" +
                "    -fx-padding: 14px 40px;" +
                "    -fx-background-radius: 25px;" +
                "    -fx-cursor: hand;" +
                "    -fx-effect: dropshadow(gaussian, rgba(220, 38, 38, 0.3), 10, 0, 0, 4);");
        saveButton.setOnMouseClicked(e -> {
                saveButton.setStyle("-fx-background-color: #205425;" +
                        "    -fx-padding: 14px 40px;" +
                        "    -fx-background-radius: 25px;" +
                        "    -fx-cursor: hand;" +
                        "    -fx-effect: dropshadow(gaussian, rgba(220, 38, 38, 0.4), 15, 0, 0, 6);"
                );
        });
        saveButton.setOnMouseEntered(e -> {
            saveButton.setStyle("-fx-background-color: #205425;" +
                    "    -fx-padding: 14px 40px;" +
                    "    -fx-background-radius: 25px;" +
                    "    -fx-cursor: hand;" +
                    "    -fx-effect: dropshadow(gaussian, rgba(220, 38, 38, 0.4), 15, 0, 0, 6);"
            );
        });
        saveButton.setOnMouseExited(e -> {
            saveButton.setStyle("-fx-background-color: #27692A;" +
                    "    -fx-padding: 14px 40px;" +
                    "    -fx-background-radius: 25px;" +
                    "    -fx-cursor: hand;" +
                    "    -fx-effect: dropshadow(gaussian, rgba(220, 38, 38, 0.4), 15, 0, 0, 6);"
            );
        });

        saveButton.setOnAction(e -> saveSettings());

        HBox buttonArea = new HBox(20);
        buttonArea.setAlignment(Pos.CENTER);
        buttonArea.setPadding(new Insets(20, 20, 0, 0));
        buttonArea.getChildren().addAll(deleteAccountBtn, saveButton);

        content.getChildren().addAll(
                headerTitle,
                personalDetailsSection,
                biometricsSection,
                goalsSection,
                buttonArea
        );

        return content;
    }

    // Personal details section (uses displayName + email)
    private VBox createPersonalDetailsSection() {
        VBox section = new VBox(20);
        section.getStyleClass().add("form-section");

        Label sectionTitle = new Label("Personal Details");
        sectionTitle.getStyleClass().add("section-title");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("form-grid");
        grid.setHgap(20);
        grid.setVgap(15);

        Label fullNameLabel = new Label("Full Name");
        fullNameLabel.getStyleClass().add("field-label");
        fullNameField = new TextField(displayName);
        fullNameField.getStyleClass().add("form-field");

        Label emailLabel = new Label("Email");
        emailLabel.getStyleClass().add("field-label");
        emailField = new TextField(email);
        emailField.getStyleClass().addAll("form-field", "disabled-field");
        emailField.setDisable(true);

        Label phoneLabel = new Label("Phone Number");
        phoneLabel.getStyleClass().add("field-label");
        phoneField = new TextField("");
        phoneField.setPromptText("Enter your phone number");
        phoneField.getStyleClass().add("form-field");

        Label billingLabel = new Label("Billing Address");
        billingLabel.getStyleClass().add("field-label");
        billingAddressField = new TextField();
        billingAddressField.setPromptText("Enter your billing address");
        billingAddressField.getStyleClass().add("form-field");

        grid.add(fullNameLabel, 0, 0);
        grid.add(fullNameField, 0, 1);
        grid.add(emailLabel, 1, 0);
        grid.add(emailField, 1, 1);
        grid.add(phoneLabel, 0, 2);
        grid.add(phoneField, 0, 3);
        grid.add(billingLabel, 1, 2);
        grid.add(billingAddressField, 1, 3);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        section.getChildren().addAll(sectionTitle, grid);
        return section;
    }

    // Biometrics section (pre-filled from currentProfile if available)
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
        Integer ageVal = (currentProfile != null) ? currentProfile.getAgeYears() : null;
        ageField = new TextField(ageVal != null ? String.valueOf(ageVal) : "24");
        ageField.getStyleClass().add("form-field");

        Label heightLabel = new Label("Height (cm)");
        heightLabel.getStyleClass().add("field-label");
        Double heightVal = (currentProfile != null) ? currentProfile.getHeightCm() : null;
        heightField = new TextField(heightVal != null ? String.valueOf(heightVal) : "175");
        heightField.getStyleClass().add("form-field");

        // Row 2: Weight and Gender
        Label weightLabel = new Label("Weight (kg)");
        weightLabel.getStyleClass().add("field-label");
        Double weightVal = (currentProfile != null) ? currentProfile.getWeightKg() : null;
        weightField = new TextField(weightVal != null ? String.valueOf(weightVal) : "82");
        weightField.getStyleClass().add("form-field");

        Label genderLabel = new Label("Gender");
        genderLabel.getStyleClass().add("field-label");
        genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Male", "Female", "Other", "Prefer not to say");
        genderCombo.setValue(
                currentProfile != null ? mapSexToCombo(currentProfile.getSex()) : "Male"
        );
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

        Double metVal = null;
        if (currentProfile != null) {
            // getActivityLevelMET() is a Double (nullable)
            metVal = currentProfile.getActivityLevelMET();
        }
        String activityInitial = mapMETToActivityLevel(metVal);
        activityLevelCombo.setValue(activityInitial);
        activityLevelCombo.getStyleClass().add("form-combo");
        activityLevelCombo.setMaxWidth(Double.MAX_VALUE);

        Label dietLabel = new Label("Diet");
        dietLabel.getStyleClass().add("field-label");

        dietCombo = new ComboBox<>();
        dietCombo.getItems().addAll(
                "None",
                "Vegetarian",
                "Vegan",
                "Pescetarian",
                "Gluten Free",
                "Dairy Free",
                "Halal",
                "Kosher"
        );

        DietPreference dietPref = (currentProfile != null) ? currentProfile.getDietPreference() : null;
        dietCombo.setValue(mapDietPreferenceToString(dietPref));
        dietCombo.getStyleClass().add("form-combo");
        dietCombo.setMaxWidth(Double.MAX_VALUE);

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
        grid.add(dietCombo, 1, 5);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        section.getChildren().addAll(sectionTitle, grid);
        return section;
    }

    /**
     * Map DietPreference enum to user-friendly string
     */
    private String mapDietPreferenceToString(DietPreference pref) {
        if (pref == null) return "None";

        switch (pref) {
            case VEGETARIAN: return "Vegetarian";
            case VEGAN: return "Vegan";
            case PESCETARIAN: return "Pescetarian";
            case GLUTEN_FREE: return "Gluten Free";
            case DAIRY_FREE: return "Dairy Free";
            case HALAL: return "Halal";
            case KOSHER: return "Kosher";
            case NONE:
            default:
                return "None";
        }
    }

    // Goals section (pre-filled from currentProfile if available)
    private VBox createGoalsSection() {
        VBox section = new VBox(20);
        section.getStyleClass().add("form-section");

        Label sectionTitle = new Label("Goals");
        sectionTitle.getStyleClass().add("section-title");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("form-grid");
        grid.setHgap(20);
        grid.setVgap(15);

        Label targetWeightLabel = new Label("Target Weight");
        targetWeightLabel.getStyleClass().add("field-label");

        HBox targetWeightBox = new HBox(10);
        Double targetVal = (currentProfile != null) ? currentProfile.getTargetWeightKg() : null;
        targetWeightField = new TextField(
                targetVal != null ? String.valueOf(targetVal) : "82"
        );
        targetWeightField.getStyleClass().add("form-field");
        Label kgLabel = new Label("kg");
        kgLabel.getStyleClass().add("unit-label");
        targetWeightBox.getChildren().addAll(targetWeightField, kgLabel);
        HBox.setHgrow(targetWeightField, Priority.ALWAYS);

        Label dailyCalorieLabel = new Label("Daily Calorie Target");
        dailyCalorieLabel.getStyleClass().add("field-label");

        Optional<Double> caloriesOpt =
                (currentProfile != null && currentProfile.getDailyCalorieTarget() != null)
                        ? currentProfile.getDailyCalorieTarget()
                        : Optional.empty();
        Double caloriesVal = caloriesOpt.orElse(null);

        dailyCalorieField = new TextField(
                caloriesVal != null ? String.valueOf(caloriesVal) : "2200"
        );
        dailyCalorieField.getStyleClass().add("form-field");

        Label goalLabel = new Label("Goal");
        goalLabel.getStyleClass().add("field-label");
        goalCombo = new ComboBox<>();
        goalCombo.getItems().addAll(
                "Lose Weight",
                "Maintain Weight",
                "Gain Weight",
                "Build Muscle"
        );
        goalCombo.setValue(
                currentProfile != null
                        ? mapGoalEnumToString(currentProfile.getGoal())
                        : "Maintain Weight"
        );
        goalCombo.getStyleClass().add("form-combo");
        goalCombo.setMaxWidth(Double.MAX_VALUE);

        Label dailyActivityLabel = new Label("Daily Activity Target");
        dailyActivityLabel.getStyleClass().add("field-label");
        dailyActivityField = new TextField("2 hours");
        dailyActivityField.getStyleClass().add("form-field");

        grid.add(targetWeightLabel, 0, 0);
        grid.add(targetWeightBox, 0, 1);
        grid.add(dailyCalorieLabel, 1, 0);
        grid.add(dailyCalorieField, 1, 1);
        grid.add(goalLabel, 0, 2);
        grid.add(goalCombo, 0, 3);
        grid.add(dailyActivityLabel, 1, 2);
        grid.add(dailyActivityField, 1, 3);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        section.getChildren().addAll(sectionTitle, grid);
        return section;
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

    public Scene getScene() {
        return scene;
    }

    // --- mapping helpers ---

    private String mapSexToCombo(Sex sex) {
        if (sex == null) return "Other";
        switch (sex) {
            case MALE:
                return "Male";
            case FEMALE:
                return "Female";
            case OTHER:
            default:
                return "Other";
        }
    }

    private String mapMETToActivityLevel(Double met) {
        if (met == null) {
            return "Moderately Active"; // default for blank profiles
        }
        if (met <= 1.2) return "Sedentary";
        if (met <= 1.375) return "Lightly Active";
        if (met <= 1.55) return "Moderately Active";
        if (met <= 1.725) return "Very Active";
        return "Extremely Active";
    }

    private String mapGoalEnumToString(Goal goal) {
        if (goal == null) return "Maintain Weight";
        switch (goal) {
            case WEIGHT_LOSS:
                return "Lose Weight";
            case WEIGHT_GAIN:
                return "Gain Weight";
            case MUSCLE_GAIN:
                return "Build Muscle";
            case GENERAL_HEALTH:
            default:
                return "Maintain Weight";
        }
    }

    private Sex mapGenderToSex(String gender) {
        if (gender == null) return Sex.OTHER;
        switch (gender.toLowerCase()) {
            case "male":
                return Sex.MALE;
            case "female":
                return Sex.FEMALE;
            case "other":
            case "prefer not to say":
            default:
                return Sex.OTHER;
        }
    }

    private Goal mapGoalStringToEnum(String goalText) {
        if (goalText == null) return Goal.GENERAL_HEALTH;
        switch (goalText.toLowerCase()) {
            case "lose weight":
                return Goal.WEIGHT_LOSS;
            case "gain weight":
                return Goal.WEIGHT_GAIN;
            case "build muscle":
                return Goal.MUSCLE_GAIN;
            case "maintain weight":
            default:
                return Goal.GENERAL_HEALTH;
        }
    }

    private DietPreference mapDietToDietPreference(String dietText) {
        if (dietText == null) return DietPreference.NONE;
        switch (dietText.trim().toLowerCase()) {
            case "none":          return DietPreference.NONE;
            case "vegetarian":    return DietPreference.VEGETARIAN;
            case "vegan":         return DietPreference.VEGAN;
            case "pescetarian":   return DietPreference.PESCETARIAN;
            case "gluten free":   return DietPreference.GLUTEN_FREE;
            case "dairy free":    return DietPreference.DAIRY_FREE;
            case "halal":         return DietPreference.HALAL;
            case "kosher":        return DietPreference.KOSHER;
            default:
                System.out.println("Unknown diet preference: " + dietText);
                return DietPreference.NONE;
        }
    }

    private double mapActivityLevelToMET(String level) {
        if (level == null) return 1.2;
        switch (level) {
            case "Sedentary":
                return 1.2;
            case "Lightly Active":
                return 1.375;
            case "Moderately Active":
                return 1.55;
            case "Very Active":
                return 1.725;
            case "Extremely Active":
                return 1.9;
            default:
                return 1.2;
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Collect form values and call UpdateUser use case via controller.
     */
    private void saveSettings() {
        try {
            // Read text fields
            int age = Integer.parseInt(ageField.getText());
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            double targetWeight = Double.parseDouble(targetWeightField.getText());
            double dailyCalories = Double.parseDouble(dailyCalorieField.getText());

            // Read dropdowns
            Sex sex = mapGenderToSex(genderCombo.getValue());
            DietPreference dietPref = mapDietToDietPreference(dietCombo.getValue());
            Goal goal = mapGoalStringToEnum(goalCombo.getValue());
            double activityLevel = mapActivityLevelToMET(activityLevelCombo.getValue());

            // Create updated profile
            Profile updatedProfile = new Profile(
                    null,                // userId
                    weight,              // weightKg
                    height,              // heightCm
                    age,                 // ageYears
                    sex,                 // sex
                    goal,                // goal
                    activityLevel,         // activityLevelMET
                    targetWeight,        // targetWeightKg
                    Optional.of(dailyCalories),     // dailyCalorieTarget
                    HealthCondition.NONE,     // healthCondition   (10)
                    dietPref       // dietPreference    (11)
            );

            // Pass to controller
            updateUserController.updateUser(updatedProfile);

            // Notify user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Profile Updated");
            alert.setContentText("Your settings have been saved.");
            alert.show();

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save settings");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }

}

package tut0301.group1.healthz.view.settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SettingsView {
    private Scene scene;
    private TextField displayNameField;
    private ComboBox<String> measurementSystemCombo;
    private ComboBox<String> themeCombo;
    private CheckBox goalReminderCheckBox;
    private CheckBox mealReminderCheckBox;
    private CheckBox insightAlertCheckBox;

    public SettingsView() {
        // main layout
        VBox root = createLayout();

        scene = new Scene(root, 600, 700);

        scene.getStylesheets().add(getClass().getResource("/styles/settings.css")).toExternalForm());
    }

    private VBox createLayout() {
        VBox root = new VBox(20); // spacing between children
        root.setPadding(new Insets(30)) // padding around edges
        root.setAlignment(Pos.TOP_CENTER);

        // adding all sections
        root.getChildren().addAll(
                createHeader(),
                createDivider(),
                createProfileSection(),
                createDivider(),
                createPreferencesSection(),
                createDivider(),
                createNotificationSection(),
                createDivider(),
                createButtonSection()
        );

        return root;
    }

    // Header Section
    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);

        Label title = new Label("Settings");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));

        Label subtitle = new Label("View or Update your personal details, biometrics, and dietary goals.");
        subtitle.setStyle("-fx-text-fill: #666666;");

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    // Profile Details Section
    private VBox createProfileSection() {

    }

    // Biometrics Section
    private VBox createBiometricsSection() {

    }

    // Notification Section
    private VBox createNotificationSection() {

    }

    // Button Section
    private HBox createButtonSection() {

    }

    // Helper Methods
    private Label createSectionTitle(String text) {

    }

    private Separator createDivider() {
        Separator separator = new Separator();
    }

    private void styleCheckBox(CheckBox checkBox) {

    }

    // Event Handlers
    private void HandleSave() {

    }

    private void HandleCancel() {

    }

    // Getter for the scene
    public Scene getScene() {
        return scene;
    }

}

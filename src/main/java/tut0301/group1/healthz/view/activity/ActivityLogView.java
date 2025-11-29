package tut0301.group1.healthz.view.activity;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.view.components.Sidebar;
import tut0301.group1.healthz.navigation.Navigator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogView {
    private Scene scene;
    private final Navigator navigator;

    // Form field
    private ComboBox<String> activityDropdown;
    private TextField durationField;
    private TextField caloriesField;
    private VBox historyContainer;
    private LocalDate currentDate = LocalDate.now();

    public ActivityLogView(Navigator navigator) {
        this.navigator = navigator;
        BorderPane root = createMainLayout();
        scene = new Scene(root, 1280, 900);
    }

    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        // Sidebar
        Sidebar sidebar = new Sidebar(navigator, "Activity Tracker", "Bob Dylan", "bob.dylan@gmail.com");
        root.setLeft(sidebar);

        // Main content
        VBox content = createContentArea();

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F5F5F5;");

        root.setCenter(scrollPane);

        return root;
    }

    private VBox createContentArea() {
        VBox content = new VBox(30);
        content.setPadding(new Insets(40, 60, 40, 60));
        content.setStyle("-fx-background-color: #F5F5F5;");

        // Header
        HBox header = createHeader();

        // Activity form
        VBox activityForm = createActivityForm();

        // History section
        VBox historySection = createHistorySection();

        content.getChildren().addAll(header, activityForm, historySection);

        return content;
    }

    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        // Title section
        VBox titleBox = new VBox(5);

        Label title = new Label("Activity Tracker");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#111827"));

        Label dateLabel = new Label(formatDate(currentDate));
        dateLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 20));
        dateLabel.setTextFill(Color.web("#27692A"));

        titleBox.getChildren().addAll(title, dateLabel);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // HealthZ logo
        Label healthzLabel = new Label("HealthZ");
        healthzLabel.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        healthzLabel.setTextFill(Color.web("#27692A"));

        header.getChildren().addAll(titleBox, spacer, healthzLabel);
        return header;
    }

    private VBox createActivityForm() {
        VBox formContainer = new VBox(25);
        formContainer.setPadding(new Insets(30));
        formContainer.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );

        // Header row with title and add button
        HBox headerRow = new HBox(20);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        Label formTitle = new Label("Activity");
        formTitle.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        formTitle.setTextFill(Color.web("#111827"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addButton = new Button("+ Activity");
        addButton.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        addButton.setTextFill(Color.WHITE);
        addButton.setPrefHeight(45);
        addButton.setPadding(new Insets(0, 25, 0, 25));
        addButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );
        addButton.setOnAction(e -> handleAddActivity());

        Label dropdown = new Label("▼");
        dropdown.setFont(Font.font(16));
        dropdown.setTextFill(Color.web("#6B7280"));

        headerRow.getChildren().addAll(formTitle, spacer, addButton, dropdown);

        // Form fields
        VBox fieldsBox = new VBox(20);
        fieldsBox.setPadding(new Insets(20, 0, 0, 0));

        // Select Activity
        VBox activityBox = createLabeledComboBox("Select Activity", List.of(
                "HIIT", "Running", "Walking", "Cycling", "Swimming",
                "Yoga", "Pilates", "Weight Training", "Basketball", "Badminton"
        ));
        activityDropdown = (ComboBox<String>) ((VBox) activityBox.getChildren().get(1)).getChildren().get(0);
        activityDropdown.setOnAction(e -> handleActivitySelection());

        // Duration field
        VBox durationBox = createLabeledField("Duration", "Minutes");
        durationField = (TextField) ((VBox) durationBox.getChildren().get(1)).getChildren().get(0);
        durationField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                durationField.setText(oldVal);
            }
        });

        // Calories burned (auto-calculated or editable)
        VBox caloriesBox = createLabeledField("Calories burned", "Calculated automatically");
        caloriesField = (TextField) ((VBox) caloriesBox.getChildren().get(1)).getChildren().get(0);
        caloriesField.setStyle(
                caloriesField.getStyle() +
                        "-fx-text-fill: #27692A; " +
                        "-fx-font-weight: bold;"
        );
        caloriesField.setEditable(false);

        fieldsBox.getChildren().addAll(activityBox, durationBox, caloriesBox);

        formContainer.getChildren().addAll(headerRow, fieldsBox);

        return formContainer;
    }

    private VBox createHistorySection() {
        VBox historySection = new VBox(20);

        Label historyTitle = new Label("History");
        historyTitle.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        historyTitle.setTextFill(Color.web("#111827"));

        historyContainer = new VBox(0);
        historyContainer.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );

        // Load sample history
        loadSampleHistory();

        historySection.getChildren().addAll(historyTitle, historyContainer);
        return historySection;
    }

    private void loadSampleHistory() {
        // Sample data
        List<ActivityEntry> activities = List.of(
                new ActivityEntry("Bicycling, 20 kph", 15, "Yesterday"),
                new ActivityEntry("Pilates", 90, "6 Nov"),
                new ActivityEntry("Walking, uphill", 45, "2 Nov"),
                new ActivityEntry("Badminton", 120, "28 Oct"),
                new ActivityEntry("Pilates", 90, "25 Oct")
        );

        for (ActivityEntry activity : activities) {
            historyContainer.getChildren().add(createHistoryItem(activity));
        }
    }

    private HBox createHistoryItem(ActivityEntry activity) {
        HBox item = new HBox(20);
        item.setPadding(new Insets(25, 30, 25, 30));
        item.setAlignment(Pos.CENTER_LEFT);
        item.setStyle(
                "-fx-border-color: #E5E7EB; " +
                        "-fx-border-width: 0 0 1 0;"
        );

        // Left: Activity name and duration
        VBox leftBox = new VBox(5);
        HBox.setHgrow(leftBox, Priority.ALWAYS);

        Label nameLabel = new Label(activity.name);
        nameLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 18));
        nameLabel.setTextFill(Color.web("#111827"));

        Label durationLabel = new Label(activity.duration + " minutes");
        durationLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        durationLabel.setTextFill(Color.web("#27692A"));

        leftBox.getChildren().addAll(nameLabel, durationLabel);

        // Right: Date
        Label dateLabel = new Label(activity.date);
        dateLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        dateLabel.setTextFill(Color.web("#6B7280"));

        item.getChildren().addAll(leftBox, dateLabel);

        // Hover effect
        item.setOnMouseEntered(e ->
                item.setStyle(
                        "-fx-border-color: #E5E7EB; " +
                                "-fx-border-width: 0 0 1 0; " +
                                "-fx-background-color: #F9FAFB; " +
                                "-fx-cursor: hand;"
                )
        );

        item.setOnMouseExited(e ->
                item.setStyle(
                        "-fx-border-color: #E5E7EB; " +
                                "-fx-border-width: 0 0 1 0;"
                )
        );

        return item;
    }

    private VBox createLabeledField(String labelText, String promptText) {
        VBox fieldBox = new VBox(8);

        Label label = new Label(labelText);
        label.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        label.setTextFill(Color.web("#374151"));

        VBox inputWrapper = new VBox();
        TextField field = new TextField();
        field.setPromptText(promptText);
        field.setPrefHeight(45);
        field.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 15px; " +
                        "-fx-padding: 10px 15px;"
        );

        field.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                field.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: #27692A; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-font-size: 15px; " +
                                "-fx-padding: 10px 15px;"
                );
            } else {
                field.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: #D1D5DB; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-font-size: 15px; " +
                                "-fx-padding: 10px 15px;"
                );
            }
        });

        inputWrapper.getChildren().add(field);
        fieldBox.getChildren().addAll(label, inputWrapper);

        return fieldBox;
    }

    private VBox createLabeledComboBox(String labelText, List<String> options) {
        VBox fieldBox = new VBox(8);

        Label label = new Label(labelText);
        label.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        label.setTextFill(Color.web("#374151"));

        VBox inputWrapper = new VBox();
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(options);
        comboBox.setValue(options.get(0));
        comboBox.setPrefHeight(45);
        comboBox.setMaxWidth(Double.MAX_VALUE);
        comboBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 15px;"
        );

        inputWrapper.getChildren().add(comboBox);
        fieldBox.getChildren().addAll(label, inputWrapper);

        return fieldBox;
    }

    private void handleActivitySelection() {
        String activity = activityDropdown.getValue();
        String duration = durationField.getText();

        if (duration != null && !duration.isEmpty()) {
            calculateCalories(activity, Integer.parseInt(duration));
        }
    }

    private void calculateCalories(String activity, int durationMinutes) {
        // Sample MET values (you should replace with actual data)
        double met = switch (activity) {
            case "HIIT" -> 8.0;
            case "Running" -> 9.0;
            case "Walking" -> 3.5;
            case "Cycling" -> 7.0;
            case "Swimming" -> 6.0;
            case "Yoga" -> 2.5;
            case "Pilates" -> 3.0;
            case "Weight Training" -> 5.0;
            case "Basketball" -> 6.5;
            case "Badminton" -> 5.5;
            default -> 3.0;
        };

        // Assuming 70kg body weight (should come from user profile)
        double bodyWeight = 70.0;
        int calories = (int) (met * bodyWeight * (durationMinutes / 60.0));

        caloriesField.setText(String.valueOf(calories));
    }

    private void handleAddActivity() {
        String activity = activityDropdown.getValue();
        String duration = durationField.getText();
        String calories = caloriesField.getText();

        if (duration.isEmpty() || calories.isEmpty()) {
            showAlert("Missing Information", "Please fill in all fields.");
            return;
        }

        System.out.println("📝 Logging activity:");
        System.out.println("   Activity: " + activity);
        System.out.println("   Duration: " + duration + " minutes");
        System.out.println("   Calories: " + calories);

        // TODO: Call use case to log activity

        // Add to history (temporarily)
        ActivityEntry newEntry = new ActivityEntry(activity, Integer.parseInt(duration), "Today");
        historyContainer.getChildren().add(0, createHistoryItem(newEntry));

        // Clear form
        durationField.clear();
        caloriesField.clear();

        showAlert("Activity Logged", activity + " has been logged successfully!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM");
        return date.format(formatter);
    }

    public Scene getScene() {
        return scene;
    }

    // Helper class for activity entries
    private static class ActivityEntry {
        String name;
        int duration;
        String date;

        ActivityEntry(String name, int duration, String date) {
            this.name = name;
            this.duration = duration;
            this.date = date;
        }
    }
}

package healthz.tut0301.group1.view.activity;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import healthz.tut0301.group1.entities.Dashboard.Profile;
import healthz.tut0301.group1.interfaceadapter.activity.ActivityHistoryViewModel;
import healthz.tut0301.group1.interfaceadapter.activity.ActivityItem;
import healthz.tut0301.group1.interfaceadapter.activity.ActivityPageController;
import healthz.tut0301.group1.interfaceadapter.activity.ExerciseListViewModel;
import healthz.tut0301.group1.navigation.Navigator;
import healthz.tut0301.group1.view.components.Sidebar;

import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * ActivityView
 * - UI styled like teammate's design (rounded white cards, green accents, large headers)
 * - Keeps YOUR logic intact:
 *    - controller.onSearchQuery(...)
 *    - controller.onDurationOrActivityChange(...)
 *    - controller.logActivity(...)
 *    - calories pulled from ExerciseListViewModel
 *    - history bound to ActivityHistoryViewModel
 */
public class ActivityView {

    private Scene scene;
    private final ActivityPageController controller;
    private final ExerciseListViewModel exerciseListViewModel;
    private final ActivityHistoryViewModel historyViewModel;
    private final Profile currentProfile;

    // Inputs
    private String selectedActivity;
    private TextField searchField;
    private ListView<String> exerciseListView;
    private TextField durationField;
    private TextField caloriesField;
    private final Navigator navigator;
    private LocalDate currentDate = LocalDate.now();


    // History
    private ListView<ActivityItem> historyListView;

    public ActivityView(ActivityPageController controller,
                        ExerciseListViewModel exerciseListViewModel,
                        ActivityHistoryViewModel historyViewModel,
                        Profile currentProfile, Navigator navigator) {
        this.controller = controller;
        this.exerciseListViewModel = exerciseListViewModel;
        this.historyViewModel = historyViewModel;
        this.currentProfile = currentProfile;
        this.navigator = navigator;

        BorderPane root = createMainLayout();
        scene = new Scene(root, 1280, 900);
        historyViewModel.addPropertyChangeListener(this::onHistoryChanged);
        controller.loadActivityHistory();
    }

    public Scene getScene() {
        return scene;
    }

    // ------------------------------------------------------------
    // MAIN LAYOUT
    // ------------------------------------------------------------
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

    // ------------------------------------------------------------
    // CONTENT AREA
    // ------------------------------------------------------------


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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM");

        Label dateLabel = new Label(currentDate.format(formatter));
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

        headerRow.getChildren().addAll(formTitle, spacer, addButton);

        // --- Form fields container ---
        VBox fieldsBox = new VBox(20);
        fieldsBox.setPadding(new Insets(20, 0, 0, 0));

        // -------------------------------------
        // Select Activity (replaces ComboBox)
        // -------------------------------------
        Label activityLabel = new Label("Select Activity");
        activityLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        activityLabel.setTextFill(Color.web("#374151"));

        // Search field
        searchField = new TextField();
        searchField.setPromptText("Search activities...");
        searchField.setPrefHeight(45);
        searchField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 15px; " +
                        "-fx-padding: 10px 15px;"
        );

        // Search result list (hidden until clicked)
        exerciseListView = new ListView<>();
        exerciseListView.setPrefHeight(200);
        exerciseListView.setVisible(false);
        exerciseListView.setManaged(false); // ⬅️ prevents empty space when hidden
        exerciseListView.setItems(exerciseListViewModel.getExerciseList());
        exerciseListView.setStyle("-fx-border-color: #E5E7EB; -fx-background-radius: 8;");
//      keyboard navigation


        // Load all exercises from Supabase
        controller.loadAllExercises();

        // Show list when user clicks
        searchField.setOnMouseClicked(e -> {
            exerciseListView.setVisible(true);
            exerciseListView.setManaged(true);
        });


        // Filter dynamically
        searchField.textProperty().addListener((obs, oldVal, newVal) -> controller.onSearchQuery(newVal));

        // On activity selection
        exerciseListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedActivity = newVal;
            if (selectedActivity != null) {
                searchField.setText(selectedActivity);
                exerciseListView.setVisible(false);
                exerciseListView.setManaged(false);

                // If duration exists, recalc calories
                String dur = durationField != null ? durationField.getText() : "";
                if (dur != null && !dur.isBlank()) {
                    try {
                        controller.onDurationOrActivityChange(selectedActivity, dur, currentProfile);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    caloriesField.setText(String.format("%.1f", exerciseListViewModel.getCurrentCalories()));
                }
            }
        });

        VBox activityBox = new VBox(8, searchField, exerciseListView);
        fieldsBox.getChildren().addAll(activityLabel, activityBox);

        // -------------------------------------
        // Duration
        // -------------------------------------
        Label durationLabel = new Label("Duration (minutes)");
        durationLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        durationLabel.setTextFill(Color.web("#374151"));

        durationField = new TextField();
        durationField.setPromptText("e.g., 60");
        durationField.setPrefHeight(45);
        durationField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 15px; " +
                        "-fx-padding: 10px 15px;"
        );
        durationField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (selectedActivity != null && !newVal.isBlank()) {
                try {
                    controller.onDurationOrActivityChange(selectedActivity, newVal, currentProfile);
                    caloriesField.setText(String.format("%.1f", exerciseListViewModel.getCurrentCalories()));
                } catch (Exception ex) {
                    caloriesField.setText("0");
                }
            } else {
                caloriesField.setText("0");
            }
        });

        fieldsBox.getChildren().addAll(durationLabel, durationField);

        // -------------------------------------
        // Calories burned (auto-calculated)
        // -------------------------------------
        Label caloriesLabel = new Label("Calories burned");
        caloriesLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        caloriesLabel.setTextFill(Color.web("#374151"));

        caloriesField = new TextField("0");
        caloriesField.setEditable(false);
        caloriesField.setPrefHeight(45);
        caloriesField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 15px; " +
                        "-fx-padding: 10px 15px; " +
                        "-fx-text-fill: #27692A; " +
                        "-fx-font-weight: bold;"
        );

        fieldsBox.getChildren().addAll(caloriesLabel, caloriesField);

        formContainer.getChildren().addAll(headerRow, fieldsBox);

        return formContainer;
    }


    private VBox createHistorySection() {
        VBox historySection = new VBox(20);
        historySection.setPadding(new Insets(10, 0, 0, 0));

        // Title
        Label historyTitle = new Label("History");
        historyTitle.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        historyTitle.setTextFill(Color.web("#111827"));

        // White card container
        VBox historyCard = new VBox();
        historyCard.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );
        historyCard.setPadding(new Insets(20, 25, 25, 25));

        // Your dynamic ListView of ActivityItem (from ViewModel)
        historyListView = new ListView<>(historyViewModel.getHistory());
        historyListView.setPrefHeight(400);
        historyListView.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent;"
        );

        // Your custom cell renderer (keeps nice layout and hover effects)
        historyListView.setCellFactory(list -> new ActivityHistoryCell());

        historyCard.getChildren().add(historyListView);

        historySection.getChildren().addAll(historyTitle, historyCard);
        return historySection;
    }

    private void handleAddActivity() {
        if (selectedActivity == null || selectedActivity.isBlank()) {
            info("Missing Information", "Please select an activity first!");
            return;
        }
        if (durationField.getText().isBlank()) {
            info("Missing Information", "Please enter a duration.");
            return;
        }

        try {
            // Your use-case call: logs to DB and presenter updates historyViewModel
            controller.logActivity(selectedActivity, durationField.getText(), currentProfile);

            // UX niceties
            exerciseListView.setVisible(false);
            durationField.clear();
            caloriesField.setText("0");

            info("Activity Logged", selectedActivity + " has been logged successfully!");
        } catch (Exception e) {
            error("Failed to save activity: " + e.getMessage());
        }
    }

    private void onHistoryChanged(PropertyChangeEvent evt) {
        if ("history".equals(evt.getPropertyName())) {
            historyListView.setItems(historyViewModel.getHistory());
        } else if ("error".equals(evt.getPropertyName())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, evt.getNewValue().toString());
            alert.showAndWait();
        }
    }

    // ------------------------------------------------------------
    // SMALL UI HELPERS
    // ------------------------------------------------------------
    private Label label(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        l.setTextFill(Color.web("#374151"));
        return l;
    }

    private TextField input(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefHeight(45);
        tf.setStyle("""
            -fx-background-color: white;
            -fx-border-color: #D1D5DB;
            -fx-border-width: 2px;
            -fx-border-radius: 8px;
            -fx-background-radius: 8px;
            -fx-font-size: 15px;
            -fx-padding: 10px 15px;
        """);
        tf.focusedProperty().addListener((obs, was, isNow) -> {
            if (isNow) {
                tf.setStyle("""
                    -fx-background-color: white;
                    -fx-border-color: #27692A;
                    -fx-border-width: 2px;
                    -fx-border-radius: 8px;
                    -fx-background-radius: 8px;
                    -fx-font-size: 15px;
                    -fx-padding: 10px 15px;
                """);
            } else {
                tf.setStyle("""
                    -fx-background-color: white;
                    -fx-border-color: #D1D5DB;
                    -fx-border-width: 2px;
                    -fx-border-radius: 8px;
                    -fx-background-radius: 8px;
                    -fx-font-size: 15px;
                    -fx-padding: 10px 15px;
                """);
            }
        });
        return tf;
    }

    private void info(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void error(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // ------------------------------------------------------------
    // HISTORY CELL (pretty row for ActivityItem)
    // ------------------------------------------------------------
    private static class ActivityHistoryCell extends ListCell<ActivityItem> {
        @Override
        protected void updateItem(ActivityItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                return;
            }

            // Left: name + duration
            Label name = new Label(item.getName());
            name.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 18));
            name.setTextFill(Color.web("#111827"));

            Label duration = new Label(item.getDuration());
            duration.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            duration.setTextFill(Color.web("#27692A"));

            Label calories = new Label(item.getCalories() + " cal");
            calories.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            calories.setTextFill(Color.web("#27692A")); // soft green tone

            VBox left = new VBox(4, name, new HBox(10, duration, calories));
            HBox.setHgrow(left, Priority.ALWAYS);


            // Right: date
            Label date = new Label(item.getDate());
            date.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            date.setTextFill(Color.web("#6B7280"));

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            HBox row = new HBox(20, left, spacer, date);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(18, 10, 18, 10));
            row.setStyle("""
                -fx-border-color: #E5E7EB;
                -fx-border-width: 0 0 1 0;
            """);

            row.setOnMouseEntered(e -> row.setStyle("""
                -fx-border-color: #E5E7EB;
                -fx-border-width: 0 0 1 0;
                -fx-background-color: #F9FAFB;
                -fx-cursor: hand;
            """));
            row.setOnMouseExited(e -> row.setStyle("""
                -fx-border-color: #E5E7EB;
                -fx-border-width: 0 0 1 0;
            """));

            setGraphic(row);
        }
    }
}

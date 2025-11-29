package tut0301.group1.healthz.view.activity;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.interfaceadapter.activity.ActivityHistoryViewModel;
import tut0301.group1.healthz.interfaceadapter.activity.ActivityItem;
import tut0301.group1.healthz.interfaceadapter.activity.ActivityPageController;
import tut0301.group1.healthz.interfaceadapter.activity.ExerciseListViewModel;



public class ActivityView {

    private static Scene scene;
    private final ActivityPageController controller;
    private final ExerciseListViewModel exerciseListViewModel;
    private final ActivityHistoryViewModel historyViewModel;

    // Add Activity Fields
    private String selectedActivity;
    private TextField durationField;
    private TextField caloriesField;

    // History list
    private ListView<ActivityItem> historyListView;
    private ListView<String> exerciseListView;


    private final Profile currentProfile;


    public ActivityView(ActivityPageController controller, ExerciseListViewModel exerciseListViewModel, ActivityHistoryViewModel historyViewModel, Profile currentProfile) {
        this.controller = controller;
        this.exerciseListViewModel = exerciseListViewModel;
        this.historyViewModel = historyViewModel;
        this.currentProfile = currentProfile;
        BorderPane root = createMainLayout();
        scene = new Scene(root, 1200, 800);

        // Load CSS
        scene.getStylesheets().add(getClass().getResource("/styles/settings.css").toExternalForm());
    }

    public static Scene getScene() {
        return scene;
    }

    // ---------------------------------------------------------------------------------------------
    // MAIN LAYOUT
    // ---------------------------------------------------------------------------------------------
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();

        // Sidebar
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        // Content
        ScrollPane scrollPane = new ScrollPane(createContentArea());
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("content-scroll");

        root.setCenter(scrollPane);
        return root;
    }

    // ---------------------------------------------------------------------------------------------
    // SIDEBAR + PROFILE SECTION
    // ---------------------------------------------------------------------------------------------
    private VBox createSidebar() {
        VBox sidebar = new VBox(0);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(220);

        VBox profileSection = createProfileSection();
        VBox navigation = createNavigation();

        Button logoutButton = new Button("Log Out");
        logoutButton.getStyleClass().addAll("nav-item", "logout-button");
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(profileSection, navigation, spacer, logoutButton);
        return sidebar;
    }

    private VBox createProfileSection() {
        VBox profileBox = new VBox(8);
        profileBox.getStyleClass().add("profile-section");
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setPadding(new Insets(30, 20, 20, 20));

        Circle profilePic = new Circle(30);
        profilePic.setFill(Color.web("#CCCCCC"));

        Label nameLabel = new Label("Bob Dylan");
        nameLabel.getStyleClass().add("profile-name");

        Label emailLabel = new Label("bob.dylan@gmail.com");
        emailLabel.getStyleClass().add("profile-email");

        profileBox.getChildren().addAll(profilePic, nameLabel, emailLabel);
        return profileBox;
    }

    private VBox createNavigation() {
        VBox nav = new VBox(0);
        nav.getStyleClass().add("navigation");

        nav.getChildren().addAll(
                createNavButton("Dashboard", "📊", false),
                createNavButton("Meal Tracker", "🍴", false),
                createNavButton("Activity Tracker", "🏃", true), // ACTIVE
                createNavButton("Settings", "⚙", false),
                createNavButton("Notifications", "🔔", false)
        );

        return nav;
    }

    private Button createNavButton(String text, String icon, boolean active) {
        Button b = new Button(icon + "  " + text);
        b.getStyleClass().add("nav-item");
        if (active) b.getStyleClass().add("nav-item-active");
        b.setAlignment(Pos.CENTER_LEFT);
        b.setMaxWidth(Double.MAX_VALUE);
        return b;
    }

    // ---------------------------------------------------------------------------------------------
    // CONTENT AREA
    // ---------------------------------------------------------------------------------------------
    private VBox createContentArea() {
        VBox content = new VBox(30);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(40, 60, 40, 60));

        Label headerTitle = new Label("Activity Tracker");
        headerTitle.getStyleClass().add("content-header");

        VBox activityCard = createActivityCard();
        VBox historySection = createHistorySection();

        content.getChildren().addAll(headerTitle, activityCard, historySection);
        return content;
    }

    // ---------------------------------------------------------------------------------------------
    // ADD ACTIVITY CARD
    // ---------------------------------------------------------------------------------------------
    private VBox createActivityCard() {
        VBox card = new VBox(20);
        card.getStyleClass().add("activity-card");
        card.setPadding(new Insets(25));

        // Card Title with "+ Activity" button
        HBox cardHeader = new HBox();
        Label title = new Label("Activity");
        title.getStyleClass().add("section-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addButton = new Button("+ Activity");
        addButton.getStyleClass().add("add-activity-button");
        addButton.setOnAction(e -> handleAddActivity());

        cardHeader.getChildren().addAll(title, spacer, addButton);

        // FORM
        GridPane form = new GridPane();
        form.setHgap(20);
        form.setVgap(15);

        // Select Activity
        Label activityLabel = new Label("Select Activity");
        activityLabel.getStyleClass().add("field-label");

        // Search field
        TextField searchField = new TextField();
        searchField.setPromptText("Search activities...");

        controller.loadAllExercises();

        // Scrollable list view
        exerciseListView = new ListView<>();
        exerciseListView.setPrefHeight(200);
        exerciseListView.setItems(exerciseListViewModel.getExerciseList());
        exerciseListView.getStyleClass().add("activity-list");

        exerciseListView.setVisible(false); // hidden by default

        searchField.setOnMouseClicked(e -> exerciseListView.setVisible(true));

        // When user types, ask controller to update results
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.onSearchQuery(newVal);
        });

        VBox activitySelector = new VBox(8, searchField, exerciseListView);
        activitySelector.setAlignment(Pos.CENTER_LEFT);

        form.add(activityLabel, 0, 0);
        form.add(activitySelector, 0, 1);

        // Duration
        Label durationLabel = new Label("Duration (min)");
        durationLabel.getStyleClass().add("field-label");

        durationField = new TextField();
        durationField.setPromptText("e.g., 60");
        durationField.getStyleClass().add("form-field");

        // Calories
        Label calLabel = new Label("Calories burned");
        calLabel.getStyleClass().add("field-label");

        caloriesField = new TextField("0");
        caloriesField.setEditable(false);
        caloriesField.getStyleClass().add("form-field");

        // Add to grid

        form.add(durationLabel, 1, 0);
        form.add(durationField, 1, 1);

        form.add(calLabel, 2, 0);
        form.add(caloriesField, 2, 1);

        // When user selects an activity, update calories immediately
        exerciseListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedActivity = newVal;
            if (selectedActivity != null && !durationField.getText().isEmpty()) {
                try {
                    searchField.setText(newVal);
                    controller.onDurationOrActivityChange(selectedActivity, durationField.getText(), currentProfile);
                    caloriesField.setText(String.format("%.1f", exerciseListViewModel.getCurrentCalories()));
                } catch (Exception e) {
                    caloriesField.setText("0");
                }
            }
        });

// Duration field — updates calories when user types a number
        durationField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (selectedActivity != null && !newVal.isEmpty()) {
                try {
                    controller.onDurationOrActivityChange(selectedActivity, durationField.getText(), currentProfile);
                    caloriesField.setText(String.format("%.1f", exerciseListViewModel.getCurrentCalories()));
                } catch (Exception e) {
                    caloriesField.setText("0");
                }
            } else {
                caloriesField.setText("0");
            }
        });
        card.getChildren().addAll(cardHeader, form);

        controller.loadAllExercises();

        return card;
    }

    // ---------------------------------------------------------------------------------------------
    // HISTORY SECTION
    // ---------------------------------------------------------------------------------------------
    private VBox createHistorySection() {
        VBox section = new VBox(15);
        section.getStyleClass().add("history-section");

        Label title = new Label("History");
        title.getStyleClass().add("section-title");

        historyListView = new ListView<>(historyViewModel.getHistory());
        historyListView.getStyleClass().add("history-list");

        // Custom cell
        historyListView.setCellFactory(list -> new ActivityHistoryCell());


        section.getChildren().add(historyListView);
        return section;
    }

    // ---------------------------------------------------------------------------------------------
    // HANDLE ADD ACTIVITY
    // ---------------------------------------------------------------------------------------------
    private void handleAddActivity() {
        if (selectedActivity == null || selectedActivity.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an activity first!");
            alert.showAndWait();
            return;
        }

        if (durationField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a duration.");
            alert.showAndWait();
            return;
        }
        try {
            // now safe to proceed
            String name = selectedActivity;
            String duration = durationField.getText();
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMM"));
            controller.logActivity(selectedActivity, duration, currentProfile);
            exerciseListView.setVisible(false);
            durationField.clear();
            caloriesField.setText("0");
        }
        catch (Exception e) {
            System.err.println("Error adding activity: " + e.getMessage());

        }
    }
}


package tut0301.group1.healthz.view.dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import tut0301.group1.healthz.interfaceadapter.macrosummary.GetDailyMacroSummaryController;
import tut0301.group1.healthz.interfaceadapter.dashboard.DashboardController;
import tut0301.group1.healthz.interfaceadapter.dashboard.DashboardViewModel;
import tut0301.group1.healthz.interfaceadapter.activity.ActivityHistoryViewModel;
import tut0301.group1.healthz.interfaceadapter.activity.ActivityItem;
import tut0301.group1.healthz.interfaceadapter.macrosummary.GetDailyMacroSummaryViewModel;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Dashboard View - daily summary, calories, macros, activity tracker, and recent entries
 */
public class DashboardView {

    private Scene scene;
    private String userName;
    private String userId;

    // Clean Architecture components
    private final DashboardViewModel viewModel;
    private final DashboardController controller;
    private final ActivityHistoryViewModel activityHistoryViewModel;

    // GetDailyCalorieSummary components
    private final GetDailyMacroSummaryController summaryController;
    private final GetDailyMacroSummaryViewModel summaryViewModel;

    // Store references for updating
    private Label caloriesValueLabel;
    private Canvas caloriesCanvas;
    private ListView<ActivityItem> activityHistoryListView;

    // Macro labels for dynamic updates
    private Label carbsPercentLabel;
    private Label carbsGramsLabel;
    private Label fatPercentLabel;
    private Label fatGramsLabel;
    private Label proteinPercentLabel;
    private Label proteinGramsLabel;

    // Navigation buttons
    private Button settingsButton;
    private Button homeButton;
    private Button recipesButton;
    private Button macrosButton;
    private Button foodLogButton;
    private Button activityLogButton;
    private Button logOutButton;

    /**
     * Constructor with Clean Architecture
     */
    public DashboardView(DashboardController controller,
                         DashboardViewModel viewModel,
                         ActivityHistoryViewModel activityHistoryViewModel,
                         GetDailyMacroSummaryController summaryController,
                         GetDailyMacroSummaryViewModel summaryViewModel,
                         String userId,
                         String userName) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.activityHistoryViewModel = activityHistoryViewModel;
        this.summaryController = summaryController;
        this.summaryViewModel = summaryViewModel;
        this.userId = userId;
        this.userName = userName != null ? userName : "User";

        // Load dashboard profile data
        System.out.println("DashboardView: Loading profile data...");
        controller.loadDashboard(userId);

        // Wait for profile data to load
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // ignore
        }

        System.out.println("   DashboardView: Profile data loaded");
        System.out.println("   Daily Goal: " + viewModel.getDailyCalorieGoal());
        System.out.println("   Remaining: " + viewModel.getCaloriesRemaining());

        BorderPane root = createMainLayout();
        scene = new Scene(root, 1280, 1200);

        // Set up property listeners for reactive UI updates
        setupPropertyListeners();

        // Load calorie summary data for today
        System.out.println("DashboardView: Loading calorie summary for today...");
        summaryController.executeToday(userId);
    }

    /**
     * Set up property listeners for reactive UI updates.
     * When summaryViewModel data changes, the UI will automatically update.
     */
    private void setupPropertyListeners() {
        // Listen to totalMacro changes
        summaryViewModel.totalMacroProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("DashboardView: Macro data updated - " + newValue);
            updateMacrosDisplay();
            updateCaloriesChart();
        });
    }


    /**
     * Update macros display based on summaryViewModel data
     */
    private void updateMacrosDisplay() {
        if (carbsPercentLabel == null || carbsGramsLabel == null ||
            fatPercentLabel == null || fatGramsLabel == null ||
            proteinPercentLabel == null || proteinGramsLabel == null) {
            return; // Labels not yet created
        }

        double carbs = summaryViewModel.getTotalCarbs();
        double fat = summaryViewModel.getTotalFat();
        double protein = summaryViewModel.getTotalProtein();
        double totalMacroGrams = carbs + fat + protein;

        // Update carbs
        if (totalMacroGrams > 0) {
            double carbsPercent = (carbs / totalMacroGrams) * 100;
            carbsPercentLabel.setText(String.format("%.0f%%", carbsPercent));
        } else {
            carbsPercentLabel.setText("0%");
        }
        carbsGramsLabel.setText(String.format("%.1fg", carbs));

        // Update fat
        if (totalMacroGrams > 0) {
            double fatPercent = (fat / totalMacroGrams) * 100;
            fatPercentLabel.setText(String.format("%.0f%%", fatPercent));
        } else {
            fatPercentLabel.setText("0%");
        }
        fatGramsLabel.setText(String.format("%.1fg", fat));

        // Update protein
        if (totalMacroGrams > 0) {
            double proteinPercent = (protein / totalMacroGrams) * 100;
            proteinPercentLabel.setText(String.format("%.0f%%", proteinPercent));
        } else {
            proteinPercentLabel.setText("0%");
        }
        proteinGramsLabel.setText(String.format("%.1fg", protein));
    }

    /**
     * Create main layout
     */
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FBF5;");

        VBox header = createHeader();
        root.setTop(header);

        VBox content = createDashboardContent();

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
     * Create header with welcome message and navigation
     */
    private VBox createHeader() {
        VBox headerBox = new VBox(10);
        headerBox.setStyle("-fx-background-color: white;");
        headerBox.setPadding(new Insets(20, 60, 20, 60));

        // Top row: Profile, Welcome, Settings, HealthZ logo, Step goal
        HBox topRow = new HBox(20);
        topRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(topRow, Priority.ALWAYS);

        // Profile circle
        Circle profileCircle = new Circle(30);
        profileCircle.setFill(Color.web("#D1D5DB"));
        profileCircle.setStroke(Color.web("#9CA3AF"));
        profileCircle.setStrokeWidth(2);

        // Welcome message
        VBox welcomeBox = new VBox(2);
        String formattedName = Arrays.stream(userName.split(" "))
                .map(word -> word.isEmpty()
                        ? word
                        : word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
        Label welcomeLabel = new Label("Welcome Back, " + formattedName);
        welcomeLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
        welcomeLabel.setTextFill(Color.web("#2E7D32"));

        Label summaryLabel = new Label("Your Daily Summary");
        summaryLabel.setFont(Font.font("Inter", FontWeight.BOLD, 42));
        summaryLabel.setTextFill(Color.web("black"));

        welcomeBox.getChildren().addAll(welcomeLabel, summaryLabel);

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        // Settings icon button
        settingsButton = createIconButton("⚙", 30);

        // HealthZ logo
        Label logoLabel = new Label("HealthZ");
        logoLabel.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        logoLabel.setTextFill(Color.web("#27692A"));

        // External link icon
        logOutButton = createIconButton("↗", 20);
        logOutButton.setOnAction(e -> System.out.println("Log Out clicked"));

        // Step goal notification with bell
        HBox stepGoalBox = new HBox(10);
        stepGoalBox.setAlignment(Pos.CENTER);
        stepGoalBox.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-background-radius: 20px; " +
                        "-fx-padding: 12px 24px;"
        );

        // Bell icon - Using Unicode
        Label bellIcon = new Label("🔔");
        bellIcon.setFont(Font.font(20));

        Label stepGoalText = new Label("30 more minutes until\nyour daily step goal!");
        stepGoalText.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        stepGoalText.setTextFill(Color.WHITE);
        stepGoalText.setTextAlignment(TextAlignment.CENTER);

        stepGoalBox.getChildren().addAll(bellIcon, stepGoalText);

        topRow.getChildren().addAll(profileCircle, welcomeBox, spacer1,
                settingsButton, logoLabel, logOutButton, stepGoalBox);

        // Bottom row: Navigation bar
        HBox navBar = new HBox(40);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(10, 0, 0, 0));

        homeButton = createNavTab("HOME", true);
        recipesButton = createNavTab("RECIPES", false);
        macrosButton = createNavTab("MACROS", false);

        navBar.getChildren().addAll(homeButton, recipesButton, macrosButton);

        headerBox.getChildren().addAll(topRow, navBar);
        return headerBox;
    }

    /**
     * Create navigation tab button
     */
    private Button createNavTab(String text, boolean active) {
        Button tab = new Button(text);
        tab.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        tab.setPrefHeight(40);

        if (active) {
            tab.setStyle(
                    "-fx-background-color: #8FBF9C; " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 5; " +
                            "-fx-padding: 8px 20px;"
            );
        } else {
            tab.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-text-fill: #27692A; " +
                            "-fx-padding: 8px 20px;"
            );

            tab.setOnMouseEntered(e ->
                    tab.setStyle(
                            "-fx-background-color: #8FBF9C; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-padding: 8px 20px;"
                    )
            );

            tab.setOnMouseExited(e ->
                    tab.setStyle(
                            "-fx-background-color: transparent; " +
                                    "-fx-text-fill: #27692A; " +
                                    "-fx-padding: 8px 20px;"
                    )
            );
        }

        return tab;
    }

    /**
     * Create icon button
     */
    private Button createIconButton(String iconText, int size) {
        Button btn = new Button();

        Label icon = new Label(iconText);
        icon.setFont(Font.font(size));
        icon.setTextFill(Color.web("#6B7280"));

        btn.setGraphic(icon);
        btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        return btn;
    }

    /**
     * Create dashboard content with all widgets
     */
    private VBox createDashboardContent() {
        VBox content = new VBox(30);
        content.setPadding(new Insets(40, 60, 40, 60));

        // First row: Calories, Macros, Daily Quote
        HBox row1 = new HBox(30);
        row1.setAlignment(Pos.TOP_LEFT);

        VBox caloriesWidget = createCaloriesWidget();
        VBox macrosWidget = createMacrosWidget();
        VBox quoteWidget = createDailyQuoteWidget();

        HBox.setHgrow(caloriesWidget, Priority.NEVER);
        HBox.setHgrow(macrosWidget, Priority.ALWAYS);
        HBox.setHgrow(quoteWidget, Priority.NEVER);

        row1.getChildren().addAll(caloriesWidget, macrosWidget, quoteWidget);

        // Second row: Activity History and Quick Add
        HBox row2 = new HBox(30);
        row2.setAlignment(Pos.TOP_LEFT);

        VBox activityHistoryWidget = createActivityHistoryWidget();
        VBox quickAddWidget = createQuickAddWidget();

        HBox.setHgrow(activityHistoryWidget, Priority.ALWAYS);
        HBox.setHgrow(quickAddWidget, Priority.NEVER);

        row2.getChildren().addAll(activityHistoryWidget, quickAddWidget);

        content.getChildren().addAll(row1, row2);
        return content;
    }

    /**
     * Create calories widget with circular progress
     */
    private VBox createCaloriesWidget() {
        VBox widget = createWidgetBox();
        widget.setPrefWidth(300);
        widget.setMinWidth(300);

        // Header
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Calories");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));

        Label arrow = new Label("▶");
        arrow.setFont(Font.font(16));
        arrow.setTextFill(Color.web("#6B7280"));

        header.getChildren().addAll(title, arrow);

        // Circular progress chart
        StackPane chartStack = new StackPane();
        chartStack.setPrefSize(200, 200);
        chartStack.setPadding(new Insets(20, 0, 20, 0));

        // Create canvas and store reference
        caloriesCanvas = new Canvas(200, 200);
        updateCaloriesChart();

        // Center text
        VBox centerText = new VBox(2);
        centerText.setAlignment(Pos.CENTER);

        // Use ViewModel data
        caloriesValueLabel = new Label(String.valueOf(viewModel.getCaloriesRemaining()));
        caloriesValueLabel.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        caloriesValueLabel.setTextFill(Color.web("#111827"));

        Label remainingLabel = new Label("remaining");
        remainingLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        remainingLabel.setTextFill(Color.web("#6B7280"));

        centerText.getChildren().addAll(caloriesValueLabel, remainingLabel);

        chartStack.getChildren().addAll(caloriesCanvas, centerText);

        widget.getChildren().addAll(header, chartStack);
        return widget;
    }

    /**
     * Update calories chart based on viewModel data
     */
    private void updateCaloriesChart() {
        if (caloriesCanvas == null) {
            return; // Canvas not yet created
        }

        GraphicsContext gc = caloriesCanvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, 200, 200);

        // Get values from viewModel (uses Profile settings)
        double remaining = viewModel.getCaloriesRemaining();
        double total = viewModel.getDailyCalorieGoal();

        if (total == 0) {
            total = 2000.0; // Fallback
        }

        // Update calorie value label
        if (caloriesValueLabel != null) {
            caloriesValueLabel.setText(String.valueOf(Math.max(0, (int) remaining)));
        }

        // Calculate progress
        double progress = remaining / total;
        double angle = progress * 360;

        // Draw background circle
        gc.setStroke(Color.web("#E5E7EB"));
        gc.setLineWidth(25);
        gc.strokeArc(25, 25, 150, 150, 0, 360, javafx.scene.shape.ArcType.OPEN);

        // Draw progress circle
        gc.setStroke(Color.web("#27692A"));
        gc.setLineWidth(25);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.strokeArc(25, 25, 150, 150, 90, -angle, javafx.scene.shape.ArcType.OPEN);
    }

    /**
     * Create macros widget showing percentages
     */
    private VBox createMacrosWidget() {
        VBox widget = createWidgetBox();

        HBox macrosRow = new HBox(60);
        macrosRow.setAlignment(Pos.CENTER);
        macrosRow.setPadding(new Insets(30, 40, 30, 40));

        // Create macro columns and store label references
        // Carbs
        VBox carbsBox = new VBox(5);
        carbsBox.setAlignment(Pos.CENTER);
        carbsPercentLabel = new Label("0%");
        carbsPercentLabel.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        carbsPercentLabel.setTextFill(Color.web("#B91C1C"));
        carbsGramsLabel = new Label("0.0g");
        carbsGramsLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        carbsGramsLabel.setTextFill(Color.web("#6B7280"));
        Label carbsLabel = new Label("Carbs");
        carbsLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        carbsLabel.setTextFill(Color.web("#374151"));
        carbsBox.getChildren().addAll(carbsPercentLabel, carbsGramsLabel, carbsLabel);

        // Fat
        VBox fatBox = new VBox(5);
        fatBox.setAlignment(Pos.CENTER);
        fatPercentLabel = new Label("0%");
        fatPercentLabel.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        fatPercentLabel.setTextFill(Color.web("#B26B00"));
        fatGramsLabel = new Label("0.0g");
        fatGramsLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        fatGramsLabel.setTextFill(Color.web("#6B7280"));
        Label fatLabel = new Label("Fat");
        fatLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        fatLabel.setTextFill(Color.web("#374151"));
        fatBox.getChildren().addAll(fatPercentLabel, fatGramsLabel, fatLabel);

        // Protein
        VBox proteinBox = new VBox(5);
        proteinBox.setAlignment(Pos.CENTER);
        proteinPercentLabel = new Label("0%");
        proteinPercentLabel.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        proteinPercentLabel.setTextFill(Color.web("#1B9DBB"));
        proteinGramsLabel = new Label("0.0g");
        proteinGramsLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        proteinGramsLabel.setTextFill(Color.web("#6B7280"));
        Label proteinLabel = new Label("Protein");
        proteinLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        proteinLabel.setTextFill(Color.web("#374151"));
        proteinBox.getChildren().addAll(proteinPercentLabel, proteinGramsLabel, proteinLabel);

        macrosRow.getChildren().addAll(carbsBox, fatBox, proteinBox);
        widget.getChildren().add(macrosRow);
        return widget;
    }

    /**
     * Create daily quote widget
     */
    private VBox createDailyQuoteWidget() {
        VBox widget = createWidgetBox();
        widget.setPrefWidth(280);
        widget.setMinWidth(280);
        widget.setAlignment(Pos.CENTER);
        widget.setPadding(new Insets(30, 20, 30, 20));

        Label title = new Label("Daily Quote:");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#111827"));

        Label quote = new Label("\"Slow Progress is\nbetter than No\nProgress.\"");
        quote.setFont(Font.font("Inter", FontWeight.NORMAL, 18));
        quote.setTextFill(Color.web("#27692A"));
        quote.setTextAlignment(TextAlignment.CENTER);
        quote.setWrapText(true);
        quote.setPadding(new Insets(15, 0, 0, 0));

        widget.getChildren().addAll(title, quote);
        return widget;
    }

    /**
     * Create activity history widget with real data from ViewModel
     */
    private VBox createActivityHistoryWidget() {
        VBox widget = createWidgetBox();
        widget.setPrefHeight(400);

        // Header
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 15, 0));

        Label title = new Label("Activity History");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label arrow = new Label("▶");
        arrow.setFont(Font.font(16));
        arrow.setTextFill(Color.web("#6B7280"));
        arrow.setStyle("-fx-cursor: hand;");

        // Make arrow clickable to navigate to full activity tracker
        arrow.setOnMouseClicked(e -> {
            if (activityLogButton != null) {
                activityLogButton.fire();
            }
        });

        header.getChildren().addAll(title, spacer, arrow);

        // ✅ Activity history ListView bound to ViewModel
        activityHistoryListView = new ListView<>(activityHistoryViewModel.getHistory());
        activityHistoryListView.setPrefHeight(300);
        activityHistoryListView.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent;"
        );

        // ✅ Custom cell renderer matching ActivityView style
        activityHistoryListView.setCellFactory(list -> new ActivityHistoryCell());

        widget.getChildren().addAll(header, activityHistoryListView);
        return widget;
    }

    /**
     * Custom cell for activity history items
     */
    private static class ActivityHistoryCell extends ListCell<ActivityItem> {
        @Override
        protected void updateItem(ActivityItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                return;
            }

            // Left side: name + duration + calories
            Label nameLabel = new Label(item.getName());
            nameLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 18));
            nameLabel.setTextFill(Color.web("#111827"));

            Label durationLabel = new Label(item.getDuration());
            durationLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            durationLabel.setTextFill(Color.web("#27692A"));

            Label caloriesLabel = new Label(item.getCalories() + " cal");
            caloriesLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            caloriesLabel.setTextFill(Color.web("#27692A"));

            HBox details = new HBox(10, durationLabel, caloriesLabel);
            VBox left = new VBox(4, nameLabel, details);
            HBox.setHgrow(left, Priority.ALWAYS);

            // Right side: date
            Label dateLabel = new Label(item.getDate());
            dateLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            dateLabel.setTextFill(Color.web("#6B7280"));

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            // Complete row
            HBox row = new HBox(20, left, spacer, dateLabel);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(18, 10, 18, 10));
            row.setStyle(
                    "-fx-border-color: #E5E7EB; " +
                            "-fx-border-width: 0 0 1 0;"
            );

            // Hover effect
            row.setOnMouseEntered(e -> row.setStyle(
                    "-fx-border-color: #E5E7EB; " +
                            "-fx-border-width: 0 0 1 0; " +
                            "-fx-background-color: #F9FAFB; " +
                            "-fx-cursor: hand;"
            ));
            row.setOnMouseExited(e -> row.setStyle(
                    "-fx-border-color: #E5E7EB; " +
                            "-fx-border-width: 0 0 1 0;"
            ));

            setGraphic(row);
        }
    }

    /**
     * Create activity tracker widget with bar chart
     */
    private VBox createActivityTrackerWidget() {
        VBox widget = createWidgetBox();
        widget.setPrefHeight(250);

        Label title = new Label("Activity Tracker");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setPadding(new Insets(0, 0, 20, 0));

        // Bar chart
        HBox chartBox = new HBox(20);
        chartBox.setAlignment(Pos.BOTTOM_CENTER);
        chartBox.setPadding(new Insets(20, 40, 20, 40));
        chartBox.setPrefHeight(150);

        // TODO: use actual activity data
        String[] days = {"Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"};
        double[] heights = {0.7, 0.65, 0.45, 0.5, 0.35, 0.75, 0.85};

        for (int i = 0; i < days.length; i++) {
            VBox barContainer = new VBox(5);
            barContainer.setAlignment(Pos.BOTTOM_CENTER);
            HBox.setHgrow(barContainer, Priority.ALWAYS);

            // Bar
            Region bar = new Region();
            bar.setPrefWidth(40);
            bar.setPrefHeight(120 * heights[i]);
            bar.setStyle("-fx-background-color: #27692A; -fx-background-radius: 4px 4px 0 0;");

            // Day label
            Label dayLabel = new Label(days[i]);
            dayLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
            dayLabel.setTextFill(Color.web("#6B7280"));

            barContainer.getChildren().addAll(bar, dayLabel);
            chartBox.getChildren().add(barContainer);
        }

        widget.getChildren().addAll(title, chartBox);
        return widget;
    }

    /**
     * Create start a habit widget
     */
    private VBox createStartHabitWidget() {
        VBox widget = createWidgetBox();
        widget.setPrefWidth(280);
        widget.setMinWidth(280);
        widget.setPrefHeight(250);
        widget.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("Start a habit");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));

        Label description = new Label("Big goals start\nwith small\nhabits.");
        description.setFont(Font.font("Inter", FontWeight.NORMAL, 18));
        description.setTextFill(Color.web("#6B7280"));
        description.setWrapText(true);
        description.setPadding(new Insets(15, 0, 0, 0));

        // Spacer
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Plus button
        Button plusButton = new Button("+");
        plusButton.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        plusButton.setTextFill(Color.WHITE);
        plusButton.setPrefSize(60, 60);
        plusButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-background-radius: 30px; " +
                        "-fx-cursor: hand;"
        );

        HBox buttonBox = new HBox(plusButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        widget.getChildren().addAll(title, description, spacer, buttonBox);
        return widget;
    }

    /**
     * Create recent entries widget
     */
    private VBox createRecentEntriesWidget() {
        VBox widget = createWidgetBox();

        // Header
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Recent Entries");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label arrow = new Label("▶");
        arrow.setFont(Font.font(16));
        arrow.setTextFill(Color.web("#6B7280"));

        header.getChildren().addAll(title, spacer, arrow);

        // Entry 1
        VBox entry1 = createEntryRow("Bicycling, 20 kph", "15 minutes", "2 hr");

        // Separator
        Region separator1 = new Region();
        separator1.setPrefHeight(1);
        separator1.setStyle("-fx-background-color: #E5E7EB;");
        separator1.setPadding(new Insets(5, 0, 5, 0));

        // Entry 2
        VBox entry2 = createEntryRow("Turkey Sandwich", "Breakfast", "4 hr");

        widget.getChildren().addAll(header, entry1, separator1, entry2);
        return widget;
    }

    /**
     * Create a single entry row
     */
    private VBox createEntryRow(String title, String subtitle, String time) {
        VBox row = new VBox(5);
        row.setPadding(new Insets(10, 0, 10, 0));

        HBox mainRow = new HBox();
        mainRow.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Inter", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.web("#111827"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label timeLabel = new Label(time);
        timeLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        timeLabel.setTextFill(Color.web("#27692A"));

        mainRow.getChildren().addAll(titleLabel, spacer, timeLabel);

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        subtitleLabel.setTextFill(Color.web("#27692A"));

        row.getChildren().addAll(mainRow, subtitleLabel);
        return row;
    }

    /**
     * Create quick add widget
     */
    private VBox createQuickAddWidget() {
        VBox widget = createWidgetBox();
        widget.setPrefWidth(340);
        widget.setMinWidth(340);
        widget.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Quick Add");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setPadding(new Insets(0, 0, 20, 0));

        // Log Meal button
        foodLogButton = createQuickAddButton("+ Log Meal");

        // Log Activity button
        activityLogButton = createQuickAddButton("+ Log Activity");
        activityLogButton.setOnAction(e -> System.out.println("Log Activity clicked"));

        widget.getChildren().addAll(title, foodLogButton, activityLogButton);
        return widget;
    }

    /**
     * Create quick add button
     */
    private Button createQuickAddButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Inter", FontWeight.BOLD, 20));
        btn.setTextFill(Color.WHITE);
        btn.setPrefWidth(280);
        btn.setPrefHeight(60);
        btn.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-cursor: hand;"
        );

        btn.setOnMouseEntered(e ->
                btn.setStyle(
                        "-fx-background-color: #205425; " +
                                "-fx-background-radius: 15px; " +
                                "-fx-cursor: hand;"
                )
        );

        btn.setOnMouseExited(e ->
                btn.setStyle(
                        "-fx-background-color: #27692A; " +
                                "-fx-background-radius: 15px; " +
                                "-fx-cursor: hand;"
                )
        );

        VBox.setMargin(btn, new Insets(0, 0, 20, 0));
        return btn;
    }

    /**
     * Create a standard widget box
     */
    private VBox createWidgetBox() {
        VBox box = new VBox(15);
        box.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );
        box.setPadding(new Insets(25, 25, 25, 25));
        return box;
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Get the Settings button (for navigation logic)
     */
    public Button getSettingsButton() { return settingsButton; }

    /**
     * Get the Home button (for navigation logic)
     */
    public Button getHomeButton() { return homeButton; }

    /**
     * Get the Recipes button (for navigation logic)
     */
    public Button getRecipesButton() { return recipesButton; }

    /**
     * Get the Macros button (for navigation logic)
     */
    public Button getMacrosButton() { return macrosButton; }

    /**
     * Get the Food Log button (for navigation logic)
     */
    public Button getFoodLogButton() { return foodLogButton; }

    /**
     * Get the Activity Log button (for navigation logic)
     */
    public Button getActivityLogButton() { return activityLogButton; }

    /**
     * Get the Log Out button (for navigation logic)
     */
    public Button getLogOutButton() { return logOutButton; }

}
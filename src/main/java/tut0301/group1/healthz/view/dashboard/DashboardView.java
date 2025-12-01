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
import tut0301.group1.healthz.interfaceadapter.activity.ActivityItem;
import tut0301.group1.healthz.interfaceadapter.dashboard.RecentActivityController;
import tut0301.group1.healthz.interfaceadapter.dashboard.RecentActivityViewModel;
import tut0301.group1.healthz.interfaceadapter.dashboard.WeeklySummaryController;
import tut0301.group1.healthz.interfaceadapter.dashboard.WeeklySummaryViewModel;

import java.beans.PropertyChangeEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Dashboard View - daily summary, calories, macros, activity tracker, and recent entries
 */
public class DashboardView {

    private Scene scene;
    private String userName; // TODO: Get from user profile
    private final RecentActivityController recentActivityController;
    private final RecentActivityViewModel recentActivityViewModel;
    private final WeeklySummaryViewModel weeklySummaryViewModel;

    // TODO: Get from actual data sources)
    private int caloriesRemaining = 425;
    private int caloriesTotal = 2000;
    private double carbsPercent = 42;
    private double carbsGrams = 32.6;
    private double fatPercent = 15;
    private double fatGrams = 12.4;
    private double proteinPercent = 20;
    private double proteinGrams = 22.5;

    // for navigation logic
    private Button settingsButton;
    private Button homeButton;
    private Button recipesButton;
    private Button macrosButton;
    private Button foodLogButton;
    private Button activityLogButton;
    private Button logOutButton;
    private HBox chartBox;

    private VBox recentEntriesContainer;


    /**
     * Constructor
     */
    public DashboardView(String userName, WeeklySummaryViewModel weeklySummaryViewModel, WeeklySummaryController weeklySummaryController, RecentActivityController recentActivityController, RecentActivityViewModel recentActivityViewModel) {
        this.userName = userName != null ? userName : "User";
        this.weeklySummaryViewModel = weeklySummaryViewModel;
        this.recentActivityController = recentActivityController;
        this.recentActivityViewModel = recentActivityViewModel;

        BorderPane root = createMainLayout();
        weeklySummaryViewModel.addPropertyChangeListener(this::onSummaryChanged);
        weeklySummaryController.loadSummary();

        recentActivityViewModel.addPropertyChangeListener(this::onRecentActivitiesChanged);
        recentActivityController.loadRecentActivities();

        scene = new Scene(root, 1280, 1200);

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

        // Second row: Activity Tracker, Start a Habit
        HBox row2 = new HBox(30);
        row2.setAlignment(Pos.TOP_LEFT);

        VBox activityWidget = createActivityTrackerWidget();
        VBox habitWidget = createStartHabitWidget();

        HBox.setHgrow(activityWidget, Priority.ALWAYS);
        HBox.setHgrow(habitWidget, Priority.NEVER);

        row2.getChildren().addAll(activityWidget, habitWidget);

        // Third row: Recent Entries, Quick Add
        HBox row3 = new HBox(30);
        row3.setAlignment(Pos.TOP_LEFT);

        VBox recentWidget = createRecentActivityWidget();
        VBox quickAddWidget = createQuickAddWidget();

        HBox.setHgrow(recentWidget, Priority.ALWAYS);
        HBox.setHgrow(quickAddWidget, Priority.NEVER);

        row3.getChildren().addAll(recentWidget, quickAddWidget);

        content.getChildren().addAll(row1, row2, row3);
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

        Canvas canvas = new Canvas(200, 200);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Calculate progress
        double progress = (double) caloriesRemaining / caloriesTotal;
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

        // Center text
        VBox centerText = new VBox(2);
        centerText.setAlignment(Pos.CENTER);

        Label caloriesValue = new Label(String.valueOf(caloriesRemaining));
        caloriesValue.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        caloriesValue.setTextFill(Color.web("#111827"));

        Label remainingLabel = new Label("remaining");
        remainingLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        remainingLabel.setTextFill(Color.web("#6B7280"));

        centerText.getChildren().addAll(caloriesValue, remainingLabel);

        chartStack.getChildren().addAll(canvas, centerText);

        widget.getChildren().addAll(header, chartStack);
        return widget;
    }

    /**
     * Create macros widget showing percentages
     */
    private VBox createMacrosWidget() {
        VBox widget = createWidgetBox();

        HBox macrosRow = new HBox(60);
        macrosRow.setAlignment(Pos.CENTER);
        macrosRow.setPadding(new Insets(30, 40, 30, 40));

        // Carbs
        VBox carbsBox = createMacroColumn(
                String.format("%.0f%%", carbsPercent),
                String.format("%.1fg", carbsGrams),
                "Carbs",
                "#B91C1C"
        );

        // Fat
        VBox fatBox = createMacroColumn(
                String.format("%.0f%%", fatPercent),
                String.format("%.1fg", fatGrams),
                "Fat",
                "#B26B00"
        );

        // Protein
        VBox proteinBox = createMacroColumn(
                String.format("%.0f%%", proteinPercent),
                String.format("%.1fg", proteinGrams),
                "Protein",
                "#1B9DBB"
        );

        macrosRow.getChildren().addAll(carbsBox, fatBox, proteinBox);
        widget.getChildren().add(macrosRow);
        return widget;
    }

    /**
     * Create a single macro column
     */
    private VBox createMacroColumn(String percent, String grams, String label, String color) {
        VBox column = new VBox(5);
        column.setAlignment(Pos.CENTER);

        Label percentLabel = new Label(percent);
        percentLabel.setFont(Font.font("Inter", FontWeight.BOLD, 40));
        percentLabel.setTextFill(Color.web(color));

        Label gramsLabel = new Label(grams);
        gramsLabel.setFont(Font.font("Inter", FontWeight.BOLD, 20));
        gramsLabel.setTextFill(Color.web("#111827"));

        Label nameLabel = new Label(label);
        nameLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 18));
        nameLabel.setTextFill(Color.web("#6B7280"));

        column.getChildren().addAll(percentLabel, gramsLabel, nameLabel);
        return column;
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

//    ----------------------------
public VBox createActivityTrackerWidget() {
    VBox widget = new VBox(10);
    widget.setPadding(new Insets(20));
    widget.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 12px;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);
        """);
    chartBox = new HBox(20);

    Label title = new Label("Activity Tracker");
    title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
    title.setTextFill(Color.web("#111827"));

    chartBox.setAlignment(Pos.BOTTOM_CENTER);
    chartBox.setPadding(new Insets(20, 40, 20, 40));
    chartBox.setPrefHeight(150);

    widget.getChildren().addAll(title, chartBox);

    // Initialize empty chart
    updateActivityChart(weeklySummaryViewModel.getSummary());

    return widget;
}

    /**
     * Called automatically when WeeklySummaryViewModel updates.
     */
    private void onSummaryChanged(PropertyChangeEvent evt) {
        if (WeeklySummaryViewModel.SUMMARY_PROPERTY.equals(evt.getPropertyName())) {
            @SuppressWarnings("unchecked")
            Map<String, Double> newSummary = (Map<String, Double>) evt.getNewValue();
            updateActivityChart(newSummary);
        }
    }

    /**
     * Updates the bar chart UI.
     */
    private void updateActivityChart(Map<String, Double> summary) {
        chartBox.getChildren().clear();

        if (summary == null || summary.isEmpty()) {
            Label placeholder = new Label("No activity logged this week");
            placeholder.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
            placeholder.setTextFill(Color.web("#9CA3AF"));
            chartBox.getChildren().add(placeholder);
            return;
        }

        // Compute max for scaling bars
        double max = summary.values().stream().mapToDouble(v -> v).max().orElse(1);
        double scale = 140 / max;

        String[] orderedDays = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        Map<String, Double> ordered = new LinkedHashMap<>();
        for (String d : orderedDays) {
            // Try case-insensitive match
            double value = summary.entrySet().stream()
                    .filter(e -> e.getKey().equalsIgnoreCase(d))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElse(0.0);
            ordered.put(d, value);
        }


        for (Map.Entry<String, Double> entry : ordered.entrySet()) {
            VBox barContainer = new VBox(8);
            HBox.setHgrow(barContainer, Priority.ALWAYS);

            barContainer.setAlignment(Pos.BOTTOM_CENTER);

            Region bar = new Region();
            bar.setPrefWidth(40);
            bar.setPrefHeight(entry.getValue() * scale);
            bar.setStyle("-fx-background-color: #27692A; -fx-background-radius: 4px 4px 0 0;");
            Tooltip tooltip = new Tooltip(String.format("%.0f minutes", entry.getValue()));
            tooltip.setStyle("""
            -fx-font-size: 14px;
            -fx-font-family: 'Inter';
            -fx-background-color: white;
            -fx-text-fill: #27692A;
            -fx-border-color: #A5D6A7;
            -fx-border-radius: 6px;
            -fx-background-radius: 6px;
            -fx-padding: 6px;
        """);
            tooltip.setShowDelay(javafx.util.Duration.millis(100));

            Tooltip.install(bar, tooltip);

            bar.setOnMouseEntered(e -> bar.setStyle("""
            -fx-background-color: linear-gradient(to top, #2e7d32, #66bb6a);
            -fx-background-radius: 6px 6px 0 0;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);
        """));
            bar.setOnMouseExited(e -> bar.setStyle("""
            -fx-background-color: linear-gradient(to top, #1b5e20, #388e3c);
            -fx-background-radius: 6px 6px 0 0;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);
        """));

            Label dayLabel = new Label(entry.getKey());
            dayLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            dayLabel.setTextFill(Color.web("#6B7280"));

            barContainer.getChildren().addAll(bar, dayLabel);
            chartBox.getChildren().add(barContainer);
        }
    }
//    -----------------------------
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

//    ==========================================mine
    private VBox createRecentActivityWidget() {
        VBox widget = createWidgetBox();
        widget.setPrefHeight(300);

        Label title = new Label("Recent Activities");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));

        recentEntriesContainer = new VBox(10);
        recentEntriesContainer.setPadding(new Insets(10, 0, 0, 0));

        ScrollPane scrollPane = new ScrollPane(recentEntriesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setPrefHeight(220);

        widget.getChildren().addAll(title, scrollPane);
        return widget;
    }
    private void onRecentActivitiesChanged(PropertyChangeEvent evt) {
        if (RecentActivityViewModel.RECENT_PROPERTY.equals(evt.getPropertyName())) {
            @SuppressWarnings("unchecked")
            List<ActivityItem> activities = (List<ActivityItem>) evt.getNewValue();
            updateRecentActivityList(activities);
        }
    }
    private void updateRecentActivityList(List<ActivityItem> logs) {
        recentEntriesContainer.getChildren().clear();

        if (logs == null || logs.isEmpty()) {
            Label placeholder = new Label("No recent activity logged.");
            placeholder.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            placeholder.setTextFill(Color.web("#9CA3AF"));
            recentEntriesContainer.getChildren().add(placeholder);
            return;
        }

        for (ActivityItem log : logs) {
            VBox entryBox = new VBox(5);
            Label title = new Label("Activity: " + log.getName());
            title.setFont(Font.font("Inter", FontWeight.BOLD, 18));

            Label details = new Label(String.format(log.getDuration() + " min · " + log.getCalories() + " cal"));

            details.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            details.setTextFill(Color.web("#27692A"));

            Label date = new Label(log.getDate());
            date.setTextFill(Color.web("#6B7280"));

            entryBox.getChildren().addAll(title, details, date);
            recentEntriesContainer.getChildren().add(entryBox);

            Region divider = new Region();
            divider.setPrefHeight(1);
            divider.setStyle("-fx-background-color: #E5E7EB;");
            recentEntriesContainer.getChildren().add(divider);
        }
    }

//    ==================================
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
     * Get the Log-Out button (for navigation logic)
     */
    public Button getLogOutButton() { return logOutButton; }

}
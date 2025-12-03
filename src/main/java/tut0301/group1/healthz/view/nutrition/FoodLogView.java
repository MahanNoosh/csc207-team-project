package tut0301.group1.healthz.view.nutrition;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeController;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeViewModel;
import tut0301.group1.healthz.interfaceadapter.foodlog.GetFoodLogHistoryController;
import tut0301.group1.healthz.interfaceadapter.foodlog.GetFoodLogHistoryViewModel;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailViewModel;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchViewModel;
import tut0301.group1.healthz.navigation.Navigator;
import tut0301.group1.healthz.view.components.Sidebar;

/**
 * View class for the Food Log and Meal Tracker.
 * Displays daily meals and nutrition history.
 */
public class FoodLogView {
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 900;
    private static final int CONTENT_SPACING = 30;
    private static final int PADDING_VERTICAL = 40;
    private static final int PADDING_HORIZONTAL = 60;
    private static final int HEADER_SPACING = 20;
    private static final int TITLE_FONT_SIZE = 48;
    private static final int DATE_FONT_SIZE = 20;
    private static final int LOGO_FONT_SIZE = 32;
    private static final int NAV_BOX_SPACING = 15;
    private static final int NAV_BUTTON_SIZE = 50;
    private static final int MEAL_HEADER_PADDING_H = 30;
    private static final int MEAL_HEADER_PADDING_V = 25;
    private static final int MEAL_FONT_SIZE = 28;
    private static final int BTN_FONT_SIZE = 15;
    private static final int BTN_HEIGHT = 45;
    private static final int BTN_PADDING = 25;
    private static final int HISTORY_SPACING = 20;
    private static final int HISTORY_FONT_SIZE = 24;
    private static final int HISTORY_ITEM_SPACING = 15;
    private static final int MAX_LOG_HISTORY = 20;

    private final Scene scene;
    private final Navigator navigator;

    private final MacroSearchController macroSearchController;
    private final MacroSearchViewModel macroSearchViewModel;

    private final LogFoodIntakeController logFoodIntakeController;
    private final LogFoodIntakeViewModel logFoodIntakeViewModel;

    private final MacroDetailController macroDetailController;
    private final MacroDetailViewModel macroDetailViewModel;

    private final GetFoodLogHistoryController summaryController;
    private final GetFoodLogHistoryViewModel summaryViewModel;

    // needed info for sidebar
    private final String userId;
    private final String displayName;
    private final String email;

    private LocalDate currentDate = LocalDate.now();
    private Label dateLabel;
    private VBox foodHistoryContainer;

    /**
     * Constructs a new FoodLogView.
     *
     * @param navigator               The navigation controller.
     * @param macroSearchController   Controller for searching macros.
     * @param macroSearchViewModel    ViewModel for searching macros.
     * @param logFoodIntakeController Controller for logging food.
     * @param logFoodIntakeViewModel  ViewModel for logging food.
     * @param macroDetailController   Controller for macro details.
     * @param macroDetailViewModel    ViewModel for macro details.
     * @param summaryController       Controller for food log history.
     * @param summaryViewModel        ViewModel for food log history.
     * @param userId                  The current user's ID.
     * @param displayName             The current user's display name.
     * @param email                   The current user's email.
     */
    public FoodLogView(Navigator navigator,
                       MacroSearchController macroSearchController,
                       MacroSearchViewModel macroSearchViewModel,
                       LogFoodIntakeController logFoodIntakeController,
                       LogFoodIntakeViewModel logFoodIntakeViewModel,
                       MacroDetailController macroDetailController,
                       MacroDetailViewModel macroDetailViewModel,
                       GetFoodLogHistoryController summaryController,
                       GetFoodLogHistoryViewModel summaryViewModel,
                       String userId,
                       String displayName,
                       String email) {
        this.navigator = navigator;
        this.macroSearchController = macroSearchController;
        this.macroSearchViewModel = macroSearchViewModel;
        this.logFoodIntakeController = logFoodIntakeController;
        this.logFoodIntakeViewModel = logFoodIntakeViewModel;
        this.macroDetailController = macroDetailController;
        this.macroDetailViewModel = macroDetailViewModel;
        this.summaryController = summaryController;
        this.summaryViewModel = summaryViewModel;
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;

        BorderPane root = createMainLayout();
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        setupDataBinding();

        // Load initial food logs for today
        refreshDataForCurrentDate();
    }

    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        // Sidebar
        // TODO: use actual user credentials
        Sidebar sidebar = new Sidebar(navigator, "Meal Tracker", displayName, email);
        root.setLeft(sidebar);

        // Main content area
        VBox content = createContentArea();

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F5F5F5;");

        root.setCenter(scrollPane);

        return root;
    }

    private VBox createContentArea() {
        VBox content = new VBox(CONTENT_SPACING);
        content.setPadding(new Insets(PADDING_VERTICAL, PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL));
        content.setStyle("-fx-background-color: #F5F5F5;");

        // Header
        HBox header = createHeader();

        // Meal sections
        VBox breakfastSection = createMealSection("Breakfast");
        VBox lunchSection = createMealSection("Lunch");
        VBox dinnerSection = createMealSection("Dinner");
        VBox snacksSection = createMealSection("Snacks");

        // Food History section
        VBox foodHistorySection = createFoodHistorySection();

        content.getChildren().addAll(
                header,
                breakfastSection,
                lunchSection,
                dinnerSection,
                snacksSection,
                foodHistorySection
        );

        return content;
    }

    private HBox createHeader() {
        HBox header = new HBox(HEADER_SPACING);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, HEADER_SPACING, 0));

        // Title section
        VBox titleBox = new VBox(5);

        Label title = new Label("Meal Tracker");
        title.setFont(Font.font("Inter", FontWeight.BOLD, TITLE_FONT_SIZE));
        title.setTextFill(Color.web("#111827"));

        dateLabel = new Label(formatDate(currentDate));
        dateLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, DATE_FONT_SIZE));
        dateLabel.setTextFill(Color.web("#27692A"));

        titleBox.getChildren().addAll(title, dateLabel);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // HealthZ logo
        Label healthzLabel = new Label("HealthZ");
        healthzLabel.setFont(Font.font("Inter", FontWeight.BOLD, LOGO_FONT_SIZE));
        healthzLabel.setTextFill(Color.web("#27692A"));

        // Date navigation
        HBox dateNav = createDateNavigation();

        header.getChildren().addAll(titleBox, spacer, healthzLabel, dateNav);
        return header;
    }

    private HBox createDateNavigation() {
        HBox navBox = new HBox(NAV_BOX_SPACING);
        navBox.setAlignment(Pos.CENTER);

        Button prevBtn = createNavButton("◀");
        prevBtn.setOnAction(event -> changeDate(-1));

        Button calendarBtn = createNavButton("📅");
        calendarBtn.setOnAction(event -> showDatePicker());

        Button nextBtn = createNavButton("▶");
        nextBtn.setOnAction(event -> changeDate(1));

        navBox.getChildren().addAll(prevBtn, calendarBtn, nextBtn);
        return navBox;
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(NAV_BUTTON_SIZE, NAV_BUTTON_SIZE);
        button.setStyle(
                "-fx-background-color: white; "
                        + "-fx-border-color: #E5E7EB; "
                        + "-fx-border-width: 2px; "
                        + "-fx-border-radius: 10px; "
                        + "-fx-background-radius: 10px; "
                        + "-fx-font-size: 18px; "
                        + "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(event ->
                button.setStyle(
                        "-fx-background-color: #F9FAFB; "
                                + "-fx-border-color: #27692A; "
                                + "-fx-border-width: 2px; "
                                + "-fx-border-radius: 10px; "
                                + "-fx-background-radius: 10px; "
                                + "-fx-font-size: 18px; "
                                + "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(event ->
                button.setStyle(
                        "-fx-background-color: white; "
                                + "-fx-border-color: #E5E7EB; "
                                + "-fx-border-width: 2px; "
                                + "-fx-border-radius: 10px; "
                                + "-fx-background-radius: 10px; "
                                + "-fx-font-size: 18px; "
                                + "-fx-cursor: hand;"
                )
        );

        return button;
    }

    private VBox createMealSection(String mealName) {
        VBox section = new VBox(0);
        section.setStyle(
                "-fx-background-color: white; "
                        + "-fx-background-radius: 15px; "
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );

        HBox sectionHeader = new HBox(HEADER_SPACING);
        sectionHeader.setPadding(new Insets(MEAL_HEADER_PADDING_V, MEAL_HEADER_PADDING_H,
                MEAL_HEADER_PADDING_V, MEAL_HEADER_PADDING_H));
        sectionHeader.setAlignment(Pos.CENTER_LEFT);

        Label mealLabel = new Label(mealName);
        mealLabel.setFont(Font.font("Inter", FontWeight.BOLD, MEAL_FONT_SIZE));
        mealLabel.setTextFill(Color.web("#111827"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addButton = new Button("+ Add Food");
        addButton.setFont(Font.font("Inter", FontWeight.BOLD, BTN_FONT_SIZE));
        addButton.setTextFill(Color.WHITE);
        addButton.setPrefHeight(BTN_HEIGHT);
        addButton.setPadding(new Insets(0, BTN_PADDING, 0, BTN_PADDING));
        addButton.setStyle(
                "-fx-background-color: #27692A; "
                        + "-fx-background-radius: 10px; "
                        + "-fx-cursor: hand;"
        );

        addButton.setOnMouseEntered(event ->
                addButton.setStyle(
                        "-fx-background-color: #1F5621; "
                                + "-fx-background-radius: 10px; "
                                + "-fx-cursor: hand;"
                )
        );

        addButton.setOnMouseExited(event ->
                addButton.setStyle(
                        "-fx-background-color: #27692A; "
                                + "-fx-background-radius: 10px; "
                                + "-fx-cursor: hand;"
                )
        );

        addButton.setOnAction(event -> navigator.showMacroSearch());

        sectionHeader.getChildren().addAll(mealLabel, spacer, addButton);
        section.getChildren().add(sectionHeader);

        return section;
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM");
        return date.format(formatter);
    }

    private void changeDate(int days) {
        currentDate = currentDate.plusDays(days);
        dateLabel.setText(formatDate(currentDate));
        System.out.println("📅 Date changed to: " + currentDate);

        refreshDataForCurrentDate();
    }

    private void showDatePicker() {
        DatePicker datePicker = new DatePicker(currentDate);

        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Select Date");
        dialog.setHeaderText("Choose a date to view meals");

        VBox content = new VBox(NAV_BOX_SPACING);
        content.setPadding(new Insets(HEADER_SPACING));
        content.getChildren().add(datePicker);

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return datePicker.getValue();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(date -> {
            currentDate = date;
            dateLabel.setText(formatDate(currentDate));

            refreshDataForCurrentDate();
        });
    }

    /**
     * Setup data binding to listen for food logs changes.
     */
    private void setupDataBinding() {
        summaryViewModel.foodLogsProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(this::updateFoodHistory);
        });
    }

    /**
     * Create the Food History section.
     */
    private VBox createFoodHistorySection() {
        VBox section = new VBox(HISTORY_SPACING);
        section.setStyle(
                "-fx-background-color: white; "
                        + "-fx-background-radius: 15px; "
                        + "-fx-padding: 25px; "
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );

        Label title = new Label("Food History");
        title.setFont(Font.font("Inter", FontWeight.BOLD, HISTORY_FONT_SIZE));
        title.setTextFill(Color.web("#111827"));

        // Container for food items
        foodHistoryContainer = new VBox(10);
        foodHistoryContainer.setStyle("-fx-padding: 10px 0 0 0;");

        section.getChildren().addAll(title, foodHistoryContainer);
        return section;
    }

    /**
     * Update the food history display with current food logs.
     */
    private void updateFoodHistory() {
        foodHistoryContainer.getChildren().clear();

        List<FoodLog> foodLogs = summaryViewModel.getFoodLogs();

        if (foodLogs == null || foodLogs.isEmpty()) {
            Label emptyLabel = new Label("No food logged for this date");
            emptyLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            emptyLabel.setTextFill(Color.web("#6B7280"));
            emptyLabel.setStyle("-fx-padding: 20px;");
            foodHistoryContainer.getChildren().add(emptyLabel);
            return;
        }

        List<FoodLog> sortedLogs = foodLogs.stream()
                .sorted((itemA, itemB) -> itemB.getLoggedAt().compareTo(itemA.getLoggedAt()))
                .limit(MAX_LOG_HISTORY)  // Only show first 20 items
                .collect(Collectors.toList());

        for (FoodLog foodLog : sortedLogs) {
            HBox foodItem = createFoodHistoryItem(foodLog);
            foodHistoryContainer.getChildren().add(foodItem);
        }
    }

    /**
     * Create a single food history item.
     * @param foodLog foodLog
     */
    private HBox createFoodHistoryItem(FoodLog foodLog) {
        final HBox item = new HBox(HISTORY_ITEM_SPACING);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setStyle(
                "-fx-background-color: #F9FAFB; "
                        + "-fx-background-radius: 10px; "
                        + "-fx-padding: 15px 20px;"
        );

        VBox timeInfo = new VBox(2);
        timeInfo.setPrefWidth(120);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd");
        Label timeLabel = new Label(foodLog.getLoggedAt().format(dateFormatter));
        timeLabel.setFont(Font.font("Inter", FontWeight.BOLD, 14));
        timeLabel.setTextFill(Color.web("#111827"));

        Label mealLabel = new Label(foodLog.getMeal());
        mealLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 12));
        mealLabel.setTextFill(Color.web("#27692A"));

        timeInfo.getChildren().addAll(timeLabel, mealLabel);

        VBox foodInfo = new VBox(2);
        HBox.setHgrow(foodInfo, Priority.ALWAYS);

        Label nameLabel = new Label(foodLog.getFood().getName());
        nameLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 16));
        nameLabel.setTextFill(Color.web("#111827"));

        final double servingSize = foodLog.getActualServingSize();
        final String servingUnit = foodLog.getServingUnit();
        final Label servingLabel = new Label(String.format("%.1f %s", servingSize, servingUnit));
        servingLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        servingLabel.setTextFill(Color.web("#6B7280"));

        foodInfo.getChildren().addAll(nameLabel, servingLabel);

        final HBox nutritionInfo = new HBox(HISTORY_SPACING);
        nutritionInfo.setAlignment(Pos.CENTER_RIGHT);

        Macro macro = foodLog.getActualMacro();

        final VBox caloriesBox = createNutritionBox("Calories",
                String.format("%.0f", macro.calories()), "#EF4444");
        final VBox proteinBox = createNutritionBox("Protein",
                String.format("%.1fg", macro.proteinG()), "#3B82F6");
        final VBox carbsBox = createNutritionBox("Carbs",
                String.format("%.1fg", macro.carbsG()), "#F59E0B");
        final VBox fatBox = createNutritionBox("Fat",
                String.format("%.1fg", macro.fatG()), "#10B981");

        nutritionInfo.getChildren().addAll(caloriesBox, proteinBox, carbsBox, fatBox);

        item.getChildren().addAll(timeInfo, foodInfo, nutritionInfo);
        return item;
    }

    /**
     * Create a nutrition info box.
     */
    private VBox createNutritionBox(String label, String value, String color) {
        VBox box = new VBox(2);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(80);

        final Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        valueLabel.setTextFill(Color.web(color));

        final Label nameLabel = new Label(label);
        nameLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 12));
        nameLabel.setTextFill(Color.web("#6B7280"));

        box.getChildren().addAll(valueLabel, nameLabel);
        return box;
    }

    /**
     * Refresh data when date changes.
     */
    private void refreshDataForCurrentDate() {
        summaryController.execute(userId, currentDate);
    }

    /**
     * Gets the current scene.
     *
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }
}
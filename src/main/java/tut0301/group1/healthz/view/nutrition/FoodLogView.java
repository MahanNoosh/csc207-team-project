package tut0301.group1.healthz.view.nutrition;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.entities.nutrition.BasicFood;
import tut0301.group1.healthz.entities.nutrition.Macro;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchViewModel;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailViewModel;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeController;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeViewModel;
import tut0301.group1.healthz.interfaceadapter.foodlog.GetFoodLogHistoryController;
import tut0301.group1.healthz.interfaceadapter.foodlog.GetFoodLogHistoryViewModel;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.view.components.Sidebar;
import tut0301.group1.healthz.navigation.Navigator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class FoodLogView {
    private Scene scene;
    private final Navigator navigator;

    private final MacroSearchController macroSearchController;
    private final MacroSearchViewModel macroSearchViewModel;

    private final LogFoodIntakeController logFoodIntakeController;
    private final LogFoodIntakeViewModel logFoodIntakeViewModel;

    private final MacroDetailController macroDetailController;
    private final MacroDetailViewModel macroDetailViewModel;

    private final GetFoodLogHistoryController summaryController;
    private final GetFoodLogHistoryViewModel summaryViewModel;

    private final String userId;

    private LocalDate currentDate = LocalDate.now();
    private Label dateLabel;
    private VBox foodHistoryContainer;

    public FoodLogView(Navigator navigator,
                       MacroSearchController macroSearchController,
                       MacroSearchViewModel macroSearchViewModel,
                       LogFoodIntakeController logFoodIntakeController,
                       LogFoodIntakeViewModel logFoodIntakeViewModel,
                       MacroDetailController macroDetailController,
                       MacroDetailViewModel macroDetailViewModel,
                       GetFoodLogHistoryController summaryController,
                       GetFoodLogHistoryViewModel summaryViewModel,
                       String userId) {
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

        BorderPane root = createMainLayout();
        scene = new Scene(root, 1280, 900);
        setupDataBinding();

        // Load initial food logs for today
        refreshDataForCurrentDate();
    }

    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        // Sidebar
        // TODO: use actual user credentials
        Sidebar sidebar = new Sidebar(navigator, "Meal Tracker", "Bob Dylan", "bob.dylan@gmail.com");
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
        VBox content = new VBox(30);
        content.setPadding(new Insets(40, 60, 40, 60));
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
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        // Title section
        VBox titleBox = new VBox(5);

        Label title = new Label("Meal Tracker");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#111827"));

        dateLabel = new Label(formatDate(currentDate));
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

        // Date navigation
        HBox dateNav = createDateNavigation();

        header.getChildren().addAll(titleBox, spacer, healthzLabel, dateNav);
        return header;
    }

    private HBox createDateNavigation() {
        HBox navBox = new HBox(15);
        navBox.setAlignment(Pos.CENTER);

        Button prevBtn = createNavButton("◀");
        prevBtn.setOnAction(e -> changeDate(-1));

        Button calendarBtn = createNavButton("📅");
        calendarBtn.setOnAction(e -> showDatePicker());

        Button nextBtn = createNavButton("▶");
        nextBtn.setOnAction(e -> changeDate(1));

        navBox.getChildren().addAll(prevBtn, calendarBtn, nextBtn);
        return navBox;
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(50, 50);
        button.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #E5E7EB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-font-size: 18px; " +
                        "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #F9FAFB; " +
                                "-fx-border-color: #27692A; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 10px; " +
                                "-fx-background-radius: 10px; " +
                                "-fx-font-size: 18px; " +
                                "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: #E5E7EB; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 10px; " +
                                "-fx-background-radius: 10px; " +
                                "-fx-font-size: 18px; " +
                                "-fx-cursor: hand;"
                )
        );

        return button;
    }

    private VBox createMealSection(String mealName) {
        VBox section = new VBox(0);
        section.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );

        HBox sectionHeader = new HBox(20);
        sectionHeader.setPadding(new Insets(25, 30, 25, 30));
        sectionHeader.setAlignment(Pos.CENTER_LEFT);

        Label mealLabel = new Label(mealName);
        mealLabel.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        mealLabel.setTextFill(Color.web("#111827"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addButton = new Button("+ Add Food");
        addButton.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        addButton.setTextFill(Color.WHITE);
        addButton.setPrefHeight(45);
        addButton.setPadding(new Insets(0, 25, 0, 25));
        addButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        addButton.setOnMouseEntered(e ->
                addButton.setStyle(
                        "-fx-background-color: #1F5621; " +
                                "-fx-background-radius: 10px; " +
                                "-fx-cursor: hand;"
                )
        );

        addButton.setOnMouseExited(e ->
                addButton.setStyle(
                        "-fx-background-color: #27692A; " +
                                "-fx-background-radius: 10px; " +
                                "-fx-cursor: hand;"
                )
        );

        addButton.setOnAction(e -> navigator.showMacroSearch());

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

        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
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
     * Setup data binding to listen for food logs changes
     */
    private void setupDataBinding() {
        summaryViewModel.foodLogsProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(this::updateFoodHistory);
        });
    }

    /**
     * Create the Food History section
     */
    private VBox createFoodHistorySection() {
        VBox section = new VBox(20);
        section.setStyle(
                "-fx-background-color: white; " +
                "-fx-background-radius: 15px; " +
                "-fx-padding: 25px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );

        Label title = new Label("Food History");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#111827"));

        // Container for food items
        foodHistoryContainer = new VBox(10);
        foodHistoryContainer.setStyle("-fx-padding: 10px 0 0 0;");

        section.getChildren().addAll(title, foodHistoryContainer);
        return section;
    }

    /**
     * Update the food history display with current food logs
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
                .sorted((a, b) -> b.getLoggedAt().compareTo(a.getLoggedAt()))
                .limit(20)  // Only show first 20 items
                .collect(Collectors.toList());

        for (FoodLog foodLog : sortedLogs) {
            HBox foodItem = createFoodHistoryItem(foodLog);
            foodHistoryContainer.getChildren().add(foodItem);
        }
    }

    /**
     * Create a single food history item
     */
    private HBox createFoodHistoryItem(FoodLog foodLog) {
        HBox item = new HBox(15);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setStyle(
                "-fx-background-color: #F9FAFB; " +
                "-fx-background-radius: 10px; " +
                "-fx-padding: 15px 20px;"
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

        Label nameLabel = new Label(foodLog.getFood().name);
        nameLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 16));
        nameLabel.setTextFill(Color.web("#111827"));

        double servingSize = foodLog.getActualServingSize();
        String servingUnit = foodLog.getServingUnit();
        Label servingLabel = new Label(String.format("%.1f %s", servingSize, servingUnit));
        servingLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        servingLabel.setTextFill(Color.web("#6B7280"));

        foodInfo.getChildren().addAll(nameLabel, servingLabel);

        HBox nutritionInfo = new HBox(20);
        nutritionInfo.setAlignment(Pos.CENTER_RIGHT);

        Macro macro = foodLog.getActualMacro();

        VBox caloriesBox = createNutritionBox("Calories", String.format("%.0f", macro.calories()), "#EF4444");
        VBox proteinBox = createNutritionBox("Protein", String.format("%.1fg", macro.proteinG()), "#3B82F6");
        VBox carbsBox = createNutritionBox("Carbs", String.format("%.1fg", macro.carbsG()), "#F59E0B");
        VBox fatBox = createNutritionBox("Fat", String.format("%.1fg", macro.fatG()), "#10B981");

        nutritionInfo.getChildren().addAll(caloriesBox, proteinBox, carbsBox, fatBox);

        item.getChildren().addAll(timeInfo, foodInfo, nutritionInfo);
        return item;
    }

    /**
     * Create a nutrition info box
     */
    private VBox createNutritionBox(String label, String value, String color) {
        VBox box = new VBox(2);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(80);

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        valueLabel.setTextFill(Color.web(color));

        Label nameLabel = new Label(label);
        nameLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 12));
        nameLabel.setTextFill(Color.web("#6B7280"));

        box.getChildren().addAll(valueLabel, nameLabel);
        return box;
    }

    /**
     * Refresh data when date changes
     */
    private void refreshDataForCurrentDate() {
        summaryController.execute(userId, currentDate);
    }

    public Scene getScene() {
        return scene;
    }
}